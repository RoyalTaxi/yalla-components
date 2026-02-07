package uz.yalla.components.foundation.location

import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Manages device location tracking and permission state.
 *
 * Provides reactive location updates and handles permission state tracking.
 * Uses [SupervisorJob] for independent lifecycle management.
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

    /** Current device location. Null if location unavailable or tracking stopped. */
    val currentLocation: StateFlow<ExtendedLocation?> = _currentLocation.asStateFlow()

    private val _isTracking = MutableStateFlow(false)

    /** Whether location tracking is currently active. */
    val isTracking: StateFlow<Boolean> = _isTracking.asStateFlow()

    private val _permissionState = MutableStateFlow<LocationPermissionState?>(null)

    /** Current location permission state. Null if not yet checked. */
    val permissionState: StateFlow<LocationPermissionState?> = _permissionState.asStateFlow()

    init {
        observeLocationUpdates()
    }

    private fun observeLocationUpdates() {
        scope.launch {
            locationTracker.getLocationsFlow()
                .distinctUntilChanged()
                .collect { location ->
                    _currentLocation.value = ExtendedLocation(
                        latitude = location.latitude,
                        longitude = location.longitude,
                    )
                }
        }
    }

    /** Starts location tracking. Requires location permission. */
    fun startTracking() {
        if (_isTracking.value) return
        scope.launch {
            locationTracker.startTracking()
        }
        _isTracking.value = true
    }

    /** Stops location tracking and clears current location. */
    fun stopTracking() {
        if (!_isTracking.value) return
        locationTracker.stopTracking()
        _isTracking.value = false
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
     * Returns current location as [MapPoint], or null if unavailable.
     */
    fun getCurrentLocationAsMapPoint(): MapPoint? =
        _currentLocation.value?.toMapPoint()

    /**
     * Returns current location as [MapPoint], or default location if unavailable.
     */
    fun getCurrentLocationOrDefault(): MapPoint =
        getCurrentLocationAsMapPoint() ?: MapPoint.DEFAULT
}
