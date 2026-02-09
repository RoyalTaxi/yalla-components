package uz.yalla.components.model.location

/**
 * Types of saved places.
 *
 * @property typeName The type identifier
 */
sealed class PlaceType(val typeName: String) {
    data object OTHER : PlaceType("other")
    data object HOME : PlaceType("home")
    data object WORK : PlaceType("work")

    companion object {
        fun fromType(typeName: String?): PlaceType =
            when (typeName?.lowercase()) {
                HOME.typeName -> HOME
                WORK.typeName -> WORK
                else -> OTHER
            }
    }
}
