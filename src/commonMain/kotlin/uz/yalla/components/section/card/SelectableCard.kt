package uz.yalla.components.section.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import uz.yalla.design.theme.System
import uz.yalla.resources.Res
import uz.yalla.resources.ic_checkbox
import uz.yalla.resources.ic_checkbox_border
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text

/**
 * Selectable card with checkbox indicator.
 *
 * Use for single or multi-selection lists.
 *
 * ## Usage
 *
 * ```kotlin
 * SelectableCard(
 *     selected = isSelected,
 *     onClick = { onSelect() },
 *     leadingIcon = { Icon(painterResource(Res.drawable.ic_cash), null) },
 *     content = { Text("Cash Payment") },
 * )
 * ```
 *
 * @param selected Whether card is selected
 * @param onClick Called when card is clicked
 * @param modifier Applied to card
 * @param leadingIcon Optional icon in leading position
 * @param leadingIconTint Tint for leading icon
 * @param enabled Whether card is enabled
 * @param shape Card corner shape
 * @param colors Color configuration
 * @param content Card content
 *
 * @see SelectableCardDefaults for default values
 */
@Composable
fun SelectableCard(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    leadingIconTint: Color = Color.Unspecified,
    enabled: Boolean = true,
    shape: Shape = SelectableCardDefaults.Shape,
    colors: SelectableCardDefaults.Colors = SelectableCardDefaults.colors(),
    content: @Composable () -> Unit,
) {
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
        Row(
            modifier = Modifier.padding(
                horizontal = SelectableCardDefaults.HorizontalPadding,
                vertical = SelectableCardDefaults.VerticalPadding,
            ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (leadingIcon != null) {
                Box(
                    modifier = Modifier
                        .size(SelectableCardDefaults.IconContainerSize)
                        .clip(SelectableCardDefaults.IconShape)
                        .background(colors.iconBackground)
                        .padding(SelectableCardDefaults.IconPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    leadingIcon()
                }
                Spacer(Modifier.width(SelectableCardDefaults.ContentSpacing))
            }

            Box(
                modifier = Modifier.weight(1f),
            ) {
                content()
            }

            Spacer(Modifier.width(SelectableCardDefaults.ContentSpacing))

            Icon(
                painter = painterResource(
                    if (selected) Res.drawable.ic_checkbox else Res.drawable.ic_checkbox_border
                ),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }
}

/**
 * Default values for [SelectableCard].
 */
object SelectableCardDefaults {

    val Shape: Shape = RoundedCornerShape(0.dp)
    val IconShape: Shape = RoundedCornerShape(10.dp)
    val IconContainerSize: Dp = 44.dp
    val IconPadding: Dp = 10.dp
    val HorizontalPadding: Dp = 16.dp
    val VerticalPadding: Dp = 10.dp
    val ContentSpacing: Dp = 16.dp

    /**
     * Color configuration for [SelectableCard].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val iconBackground: Color,
        val disabledContainer: Color,
    )

    @Composable
    fun colors(
        container: Color = Color.Transparent,
        iconBackground: Color = System.color.backgroundSecondary,
        disabledContainer: Color = Color.Transparent,
    ): Colors = Colors(
        container = container,
        iconBackground = iconBackground,
        disabledContainer = disabledContainer,
    )
}

@Preview
@Composable
private fun SelectableCardPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        SelectableCard(
            selected = true,
            onClick = {},
            content = { Text("Cash Payment") },
        )
    }
}
