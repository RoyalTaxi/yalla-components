package uz.yalla.components.primitive.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.components.primitive.button.NavigationButton
import uz.yalla.design.theme.System

/**
 * Standard top bar with optional navigation, title, and actions.
 *
 * ## Usage
 *
 * ```kotlin
 * TopBar(
 *     title = "Settings",
 *     onNavigationClick = onBack,
 * )
 * ```
 *
 * ## With Actions
 *
 * ```kotlin
 * TopBar(
 *     title = "Profile",
 *     onNavigationClick = onBack,
 *     actions = {
 *         IconButton(onClick = onEdit) {
 *             Icon(Icons.Default.Edit, null)
 *         }
 *     }
 * )
 * ```
 *
 * @param modifier Applied to top bar.
 * @param title Optional title text.
 * @param onNavigationClick If provided, shows back button.
 * @param colors Color configuration.
 * @param actions Optional action buttons on the right.
 *
 * @see LargeTopBar for large title variant
 * @see TopBarDefaults for default values
 */
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    onNavigationClick: (() -> Unit)? = null,
    colors: TopBarDefaults.Colors = TopBarDefaults.colors(),
    actions: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(TopBarDefaults.ContentPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Navigation button
        if (onNavigationClick != null) {
            NavigationButton(onClick = onNavigationClick)
        } else {
            Spacer(Modifier.width(TopBarDefaults.NavigationButtonSize))
        }

        // Title
        if (title != null) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = title,
                    style = System.font.title.base,
                    color = colors.title,
                )
            }
        } else {
            Spacer(Modifier.weight(1f))
        }

        // Actions
        if (actions != null) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                actions()
            }
        } else {
            Spacer(Modifier.width(TopBarDefaults.NavigationButtonSize))
        }
    }
}

/**
 * Default values for [TopBar].
 */
object TopBarDefaults {

    val ContentPadding: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 8.dp,
    )
    val NavigationButtonSize: Dp = 40.dp
    val TitleSpacing: Dp = 16.dp

    /**
     * Color configuration for [TopBar].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val title: Color,
    )

    @Composable
    fun colors(
        container: Color = Color.Transparent,
        title: Color = System.color.textBase,
    ): Colors = Colors(
        container = container,
        title = title,
    )
}

@Preview
@Composable
private fun TopBarPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        TopBar(
            title = "Settings",
            onNavigationClick = {},
        )
    }
}
