package uz.yalla.components.foundation.reactive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import uz.yalla.components.foundation.locale.Language
import uz.yalla.components.foundation.locale.getCurrentLanguage

/**
 * Remembers and observes the current locale, triggering recomposition on change.
 *
 * @return Current [Language]
 */
@Composable
fun rememberCurrentLanguage(): Language {
    var language by remember { mutableStateOf(Language.fromCode(getCurrentLanguage())) }

    LaunchedEffect(Unit) {
        language = Language.fromCode(getCurrentLanguage())
    }

    return language
}
