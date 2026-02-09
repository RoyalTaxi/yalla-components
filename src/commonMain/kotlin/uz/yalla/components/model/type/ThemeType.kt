package uz.yalla.components.model.type

/**
 * App theme options.
 */
enum class ThemeType {
    LIGHT,
    DARK,
    SYSTEM;

    companion object {
        fun fromValue(value: String?): ThemeType =
            when (value) {
                LIGHT.name -> LIGHT
                DARK.name -> DARK
                else -> SYSTEM
            }
    }
}
