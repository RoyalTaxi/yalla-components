package uz.yalla.components.model.type

/**
 * Supported app locales.
 *
 * @property raw The ISO 639-1 language code
 */
enum class LocaleType(val raw: String) {
    UZ("uz"),
    UZ_CYRILLIC("be"),
    RU("ru"),
    EN("en");

    companion object {
        fun fromValue(value: String?): LocaleType =
            when (value) {
                UZ.raw -> UZ
                UZ_CYRILLIC.raw -> UZ_CYRILLIC
                RU.raw -> RU
                EN.raw -> EN
                else -> UZ
            }
    }
}
