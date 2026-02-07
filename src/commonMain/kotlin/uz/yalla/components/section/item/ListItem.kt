package uz.yalla.components.section.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Standard list item with title, subtitle, and action slots.
 *
 * Versatile list item for settings, menus, and general lists.
 *
 * ## Usage
 *
 * ```kotlin
 * ListItem(
 *     title = "Profile Settings",
 *     subtitle = "Update your personal info",
 *     leadingContent = { Icon(Icons.Default.Person, null) },
 *     trailingContent = { Icon(Icons.Default.ChevronRight, null) },
 *     onClick = { navigateToProfile() },
 * )
 * ```
 *
 * @param title Primary text.
 * @param modifier Applied to item.
 * @param subtitle Optional secondary text.
 * @param leadingContent Optional leading slot.
 * @param trailingContent Optional trailing slot.
 * @param onClick Optional click handler.
 * @param enabled Whether item is enabled.
 * @param shape Item shape.
 * @param colors Color configuration.
 *
 * @see ListItemDefaults for default values
 */
@Composable
fun ListItem(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    shape: Shape = ListItemDefaults.Shape,
    colors: ListItemDefaults.Colors = ListItemDefaults.colors(),
) {
    val content: @Composable () -> Unit = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ListItemDefaults.ContentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(ListItemDefaults.ContentSpacing),
        ) {
            leadingContent?.invoke()

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.TitleSubtitleSpacing),
            ) {
                Text(
                    text = title,
                    style = System.font.body.base.bold,
                    color = if (enabled) colors.title else colors.disabledTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = System.font.body.small.medium,
                        color = if (enabled) colors.subtitle else colors.disabledSubtitle,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            trailingContent?.invoke()
        }
    }

    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = colors.container,
                disabledContainerColor = colors.disabledContainer,
            ),
        ) {
            content()
        }
    } else {
        Card(
            modifier = modifier,
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = colors.container,
            ),
        ) {
            content()
        }
    }
}

/**
 * Default values for [ListItem].
 */
object ListItemDefaults {

    val Shape: Shape = RectangleShape
    val ContentPadding: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 12.dp,
    )
    val ContentSpacing: Dp = 12.dp
    val TitleSubtitleSpacing: Dp = 4.dp

    /**
     * Color configuration for [ListItem].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val title: Color,
        val subtitle: Color,
        val disabledContainer: Color,
        val disabledTitle: Color,
        val disabledSubtitle: Color,
    )

    @Composable
    fun colors(
        container: Color = Color.Transparent,
        title: Color = System.color.textBase,
        subtitle: Color = System.color.textSubtle,
        disabledContainer: Color = Color.Transparent,
        disabledTitle: Color = System.color.textDisabled,
        disabledSubtitle: Color = System.color.textDisabled,
    ): Colors = Colors(
        container = container,
        title = title,
        subtitle = subtitle,
        disabledContainer = disabledContainer,
        disabledTitle = disabledTitle,
        disabledSubtitle = disabledSubtitle,
    )
}

@Preview
@Composable
private fun ListItemPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        ListItem(
            title = "Profile Settings",
            subtitle = "Update your personal info",
            onClick = {},
        )
    }
}
