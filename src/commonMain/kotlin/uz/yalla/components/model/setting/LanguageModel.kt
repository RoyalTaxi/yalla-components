package uz.yalla.components.model.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import uz.yalla.components.model.common.SelectableItemModel
import uz.yalla.components.model.type.LocaleType
import uz.yalla.resources.Res
import uz.yalla.resources.ic_flag_en
import uz.yalla.resources.ic_flag_ru
import uz.yalla.resources.ic_flag_uz
import uz.yalla.resources.language_english
import uz.yalla.resources.language_russian
import uz.yalla.resources.language_uzbek_cyrillic
import uz.yalla.resources.language_uzbek_latin

sealed class LanguageModel(
    val icon: DrawableResource,
    val name: StringResource,
    val localeType: LocaleType
) {
    data object Uzbek : LanguageModel(
        icon = Res.drawable.ic_flag_uz,
        name = Res.string.language_uzbek_latin,
        localeType = LocaleType.UZ
    )

    data object UzbekCyrillic : LanguageModel(
        icon = Res.drawable.ic_flag_uz,
        name = Res.string.language_uzbek_cyrillic,
        localeType = LocaleType.UZ_CYRILLIC
    )

    data object Russian : LanguageModel(
        icon = Res.drawable.ic_flag_ru,
        name = Res.string.language_russian,
        localeType = LocaleType.RU
    )

    data object English : LanguageModel(
        icon = Res.drawable.ic_flag_en,
        name = Res.string.language_english,
        localeType = LocaleType.EN
    )

    @Composable
    fun toSelectableItemModel() =
        SelectableItemModel(
            item = this,
            title = stringResource(name),
            icon = painterResource(icon),
            iconColor = Color.Unspecified
        )

    companion object {
        val LANGUAGES = listOf(Uzbek, Russian)

        fun fromLocaleType(localeType: LocaleType): LanguageModel =
            when (localeType) {
                LocaleType.UZ -> Uzbek
                LocaleType.UZ_CYRILLIC -> UzbekCyrillic
                LocaleType.RU -> Russian
                LocaleType.EN -> English
            }
    }
}
