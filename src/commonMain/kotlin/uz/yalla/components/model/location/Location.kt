package uz.yalla.components.model.location

/**
 * A location with optional name and coordinates.
 *
 * @property id Location identifier
 * @property name Display name
 * @property point Geographic coordinates
 */
data class Location(
    val id: Int?,
    val name: String?,
    val point: MapPoint?
)

/**
 * A found location from search results.
 *
 * @property id Location identifier
 * @property name Display name
 * @property address Full address
 * @property point Geographic coordinates
 * @property type Place category
 */
data class FoundLocation(
    val id: Int?,
    val name: String?,
    val address: String?,
    val point: MapPoint?,
    val type: PlaceType?
) {
    fun toLocation() = Location(
        id = this.id,
        name = this.name,
        point = this.point
    )
}
