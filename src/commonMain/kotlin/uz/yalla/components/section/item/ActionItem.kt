package uz.yalla.components.section.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * List item with icon, text, and action button.
 *
 * Use for items with a specific action like delete.
 *
 * ## Usage
 *
 * ```kotlin
 * ActionItem(
 *     title = "Humo •••• 1234",
 *     leadingIcon = { Icon(painterResource(Res.drawable.ic_humo), null) },
 *     action = { Icon(painterResource(Res.drawable.ic_trash), null) },
 *     onActionClick = { deleteCard(cardId) },
 * )
 * ```
 *
 * @param title Item text.
 * @param action Action button content.
 * @param onActionClick Called when action is clicked.
 * @param modifier Applied to item.
 * @param leadingIcon Optional icon before title.
 * @param colors Color configuration.
 *
 * @see ActionItemDefaults for default values
 */
@Composable
fun ActionItem(
    title: String,
    action: @Composable () -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    colors: ActionItemDefaults.Colors = ActionItemDefaults.colors(),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(ActionItemDefaults.ContentPadding),
    ) {
        if (leadingIcon != null) {
            Box(
                modifier = Modifier
                    .size(ActionItemDefaults.IconContainerSize)
                    .background(
                        color = colors.iconBackground,
                        shape = ActionItemDefaults.IconShape,
                    )
                    .padding(ActionItemDefaults.IconPadding),
                contentAlignment = Alignment.Center,
            ) {
                leadingIcon()
            }
            Spacer(Modifier.width(ActionItemDefaults.ContentSpacing))
        }

        Text(
            text = title,
            style = System.font.body.base.bold,
            color = colors.title,
            modifier = Modifier.weight(1f),
        )

        Spacer(Modifier.width(ActionItemDefaults.ContentSpacing))

        IconButton(onClick = onActionClick) {
            action()
        }
    }
}

/**
 * Default values for [ActionItem].
 */
object ActionItemDefaults {

    val IconShape: Shape = RoundedCornerShape(10.dp)
    val IconContainerSize: Dp = 44.dp
    val IconPadding: Dp = 10.dp
    val ContentPadding: PaddingValues = PaddingValues(vertical = 10.dp)
    val ContentSpacing: Dp = 16.dp

    /**
     * Color configuration for [ActionItem].
     */
    @Immutable
    data class Colors(
        val iconBackground: Color,
        val title: Color,
    )

    @Composable
    fun colors(
        iconBackground: Color = System.color.backgroundSecondary,
        title: Color = System.color.textBase,
    ): Colors = Colors(
        iconBackground = iconBackground,
        title = title,
    )
}

@Preview
@Composable
private fun ActionItemPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        ActionItem(
            title = "Humo •••• 1234",
            action = { Text("X") },
            onActionClick = {},
        )
    }
}
