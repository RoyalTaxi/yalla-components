package uz.yalla.components.foundation.location

/**
 * Represents a geographic location with latitude and longitude coordinates.
 *
 * @property latitude Latitude in degrees (-90 to 90)
 * @property longitude Longitude in degrees (-180 to 180)
 */
data class MapPoint(
    val latitude: Double,
    val longitude: Double,
) {
    companion object {
        /** Default location (Tashkent, Uzbekistan). */
        val DEFAULT = MapPoint(latitude = 41.2995, longitude = 69.2401)

        /** Creates MapPoint from coordinate pair. */
        fun from(lat: Double, lng: Double) = MapPoint(lat, lng)
    }
}

/**
 * Extended location data with additional metadata.
 *
 * @property latitude Latitude coordinate
 * @property longitude Longitude coordinate
 * @property accuracy Location accuracy in meters
 * @property altitude Altitude in meters (if available)
 * @property speed Speed in m/s (if available)
 * @property bearing Bearing in degrees (if available)
 * @property timestamp Timestamp when location was captured
 */
data class ExtendedLocation(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float? = null,
    val altitude: Double? = null,
    val speed: Float? = null,
    val bearing: Float? = null,
    val timestamp: Long = 0L,
) {
    /** Converts to simple [MapPoint]. */
    fun toMapPoint(): MapPoint = MapPoint(latitude, longitude)
}

/**
 * Location permission state.
 */
enum class LocationPermissionState {
    /** Permission not yet requested. */
    NOT_DETERMINED,

    /** Permission granted. */
    GRANTED,

    /** Permission denied. */
    DENIED,

    /** Permission denied permanently (user selected "Don't ask again"). */
    DENIED_PERMANENTLY,
}
