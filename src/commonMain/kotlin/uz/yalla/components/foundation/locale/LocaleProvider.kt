package uz.yalla.components.foundation.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Supported languages in the app.
 *
 * @property code ISO 639-1 language code
 * @property displayName Human-readable language name
 * @property nativeName Language name in its own language
 */
enum class Language(
    val code: String,
    val displayName: String,
    val nativeName: String,
) {
    UZBEK("uz", "Uzbek", "O'zbek"),
    RUSSIAN("ru", "Russian", "Русский"),
    ENGLISH("en", "English", "English"),
    ;

    companion object {
        /** Gets Language by code, defaulting to UZBEK if not found. */
        fun fromCode(code: String): Language =
            entries.find { it.code == code } ?: UZBEK
    }
}

/**
 * Current app language state.
 *
 * @property language Currently selected language
 * @property onLanguageChange Callback when language should change
 */
data class LocaleState(
    val language: Language,
    val onLanguageChange: (Language) -> Unit,
)

/**
 * CompositionLocal for locale state.
 */
val LocalLocaleState = staticCompositionLocalOf<LocaleState> {
    error("LocaleState not provided. Wrap content with LocaleProvider.")
}

/**
 * Provides locale state to composition tree.
 *
 * @param state Current locale state
 * @param content Child composables
 */
@Composable
fun LocaleProvider(
    state: LocaleState,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalLocaleState provides state,
        content = content,
    )
}

/**
 * Retrieves current locale state from composition.
 */
@Composable
fun rememberLocaleState(): LocaleState = LocalLocaleState.current
