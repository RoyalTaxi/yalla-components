package uz.yalla.components.model.location

/**
 * Geographic coordinate point.
 *
 * @property lat Latitude
 * @property lng Longitude
 */
data class MapPoint(
    val lat: Double,
    val lng: Double
) {
    companion object {
        val Zero = MapPoint(0.0, 0.0)
    }
}
