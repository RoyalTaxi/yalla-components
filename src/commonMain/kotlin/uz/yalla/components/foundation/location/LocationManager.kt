package uz.yalla.components.foundation.location

import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import uz.yalla.core.geo.GeoPoint

/**
 * Manages device location tracking and permission state.
 *
 * Provides reactive location updates with extended metadata (accuracy, speed, bearing)
 * and handles permission state tracking. Uses [SupervisorJob] for independent lifecycle management.
 *
 * ## Usage
 *
 * ```kotlin
 * val locationManager = LocationManager(locationTracker)
 *
 * // Start tracking
 * locationManager.startTracking()
 *
 * // Observe location updates
 * locationManager.currentLocation.collect { location ->
 *     location?.let { updateMap(it) }
 * }
 *
 * // Stop when done
 * locationManager.stopTracking()
 * ```
 *
 * @param locationTracker Platform-specific location tracker from moko-geo
 */
class LocationManager(
    val locationTracker: LocationTracker,
) {
    private val scope = CoroutineScope(SupervisorJob())

    private val _currentLocation = MutableStateFlow<ExtendedLocation?>(null)

    /** Current device location with extended metadata. Null if location unavailable or tracking stopped. */
    val currentLocation: StateFlow<ExtendedLocation?> = _currentLocation.asStateFlow()

    private val _isTracking = MutableStateFlow(false)

    /** Whether location tracking is currently active. */
    val isTracking: StateFlow<Boolean> = _isTracking.asStateFlow()

    private val _permissionState = MutableStateFlow<LocationPermissionState?>(null)

    /** Current location permission state. Null if not yet checked. */
    val permissionState: StateFlow<LocationPermissionState?> = _permissionState.asStateFlow()

    /** Starts location tracking. Requires location permission. */
    fun startTracking() {
        if (_isTracking.value) return

        scope.launch {
            runCatching {
                locationTracker.startTracking()
                _isTracking.value = true

                locationTracker.getExtendedLocationsFlow()
                    .distinctUntilChanged()
                    .collect { extLoc ->
                        _currentLocation.value = ExtendedLocation(
                            latitude = extLoc.location.coordinates.latitude,
                            longitude = extLoc.location.coordinates.longitude,
                            accuracy = extLoc.location.coordinatesAccuracyMeters.toFloat(),
                            altitude = extLoc.altitude.altitudeMeters,
                            speed = extLoc.speed.speedMps.toFloat(),
                            bearing = extLoc.azimuth.azimuthDegrees.toFloat(),
                            timestamp = extLoc.timestampMs,
                        )
                    }
            }.onFailure {
                _isTracking.value = false
            }
        }
    }

    /** Stops location tracking. */
    fun stopTracking() {
        if (!_isTracking.value) return

        scope.launch {
            runCatching {
                locationTracker.stopTracking()
                _isTracking.value = false
            }
        }
    }

    /**
     * Updates the permission state.
     *
     * @param state New permission state, or null if unknown
     */
    fun updatePermissionState(state: LocationPermissionState?) {
        _permissionState.value = state
    }

    /**
     * Returns current location as [GeoPoint], or null if unavailable.
     */
    fun getCurrentLocationAsGeoPoint(): GeoPoint? =
        _currentLocation.value?.toGeoPoint()

    /**
     * Returns current location as [GeoPoint], or default location if unavailable.
     */
    fun getCurrentLocationOrDefault(): GeoPoint =
        getCurrentLocationAsGeoPoint() ?: GeoPoint(41.2995, 69.2401)
}
