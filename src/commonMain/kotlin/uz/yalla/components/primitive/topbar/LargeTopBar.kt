package uz.yalla.components.primitive.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
 * Large top bar with prominent title below navigation row.
 *
 * Use for screens where the title is the primary focus.
 *
 * ## Usage
 *
 * ```kotlin
 * LargeTopBar(
 *     title = "Order History",
 *     onNavigationClick = onBack,
 * )
 * ```
 *
 * @param title Screen title.
 * @param modifier Applied to top bar.
 * @param onNavigationClick If provided, shows back button.
 * @param colors Color configuration.
 * @param actions Optional action buttons.
 *
 * @see TopBar for standard variant
 * @see LargeTopBarDefaults for default values
 */
@Composable
fun LargeTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationClick: (() -> Unit)? = null,
    colors: LargeTopBarDefaults.Colors = LargeTopBarDefaults.colors(),
    actions: @Composable (RowScope.() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(LargeTopBarDefaults.ContentPadding),
    ) {
        // Navigation row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (onNavigationClick != null) {
                NavigationButton(onClick = onNavigationClick)
            } else {
                Spacer(Modifier.width(LargeTopBarDefaults.NavigationButtonSize))
            }

            if (actions != null) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    actions()
                }
            }
        }

        // Title
        Spacer(Modifier.height(LargeTopBarDefaults.TitleTopSpacing))

        Text(
            text = title,
            style = System.font.title.large,
            color = colors.title,
        )
    }
}

/**
 * Default values for [LargeTopBar].
 */
object LargeTopBarDefaults {

    val ContentPadding: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 8.dp,
    )
    val NavigationButtonSize: Dp = 40.dp
    val TitleTopSpacing: Dp = 20.dp

    /**
     * Color configuration for [LargeTopBar].
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
private fun LargeTopBarPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        LargeTopBar(
            title = "Order History",
            onNavigationClick = {},
        )
    }
}
