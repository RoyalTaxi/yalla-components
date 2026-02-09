package uz.yalla.components.model.setting

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import uz.yalla.components.model.common.SelectableItemModel
import uz.yalla.components.model.type.ThemeType
import uz.yalla.design.theme.System
import uz.yalla.resources.Res
import uz.yalla.resources.ic_moon
import uz.yalla.resources.ic_setting
import uz.yalla.resources.ic_sun
import uz.yalla.resources.settings_theme_dark
import uz.yalla.resources.settings_theme_light
import uz.yalla.resources.settings_theme_system

sealed class ThemeModel(
    val icon: DrawableResource,
    val name: StringResource,
    val themeType: ThemeType
) {
    data object LIGHT : ThemeModel(
        icon = Res.drawable.ic_sun,
        name = Res.string.settings_theme_light,
        themeType = ThemeType.LIGHT
    )

    data object DARK : ThemeModel(
        icon = Res.drawable.ic_moon,
        name = Res.string.settings_theme_dark,
        themeType = ThemeType.DARK
    )

    data object SYSTEM : ThemeModel(
        icon = Res.drawable.ic_setting,
        name = Res.string.settings_theme_system,
        themeType = ThemeType.SYSTEM
    )

    @Composable
    fun toSelectableItemModel() =
        SelectableItemModel(
            item = this,
            title = stringResource(name),
            icon = painterResource(icon),
            iconColor = System.color.iconBase
        )

    companion object {
        val THEMES = listOf(LIGHT, DARK, SYSTEM)

        fun fromThemeType(themeType: ThemeType): ThemeModel =
            when (themeType) {
                ThemeType.LIGHT -> LIGHT
                ThemeType.DARK -> DARK
                ThemeType.SYSTEM -> SYSTEM
            }
    }
}
