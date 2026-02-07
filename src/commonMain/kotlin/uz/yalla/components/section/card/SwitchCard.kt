package uz.yalla.components.section.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Card with toggle switch for enabling/disabling features.
 *
 * Use for settings or feature toggles within cards.
 *
 * ## Usage
 *
 * ```kotlin
 * SwitchCard(
 *     checked = bonusEnabled,
 *     onCheckedChange = { viewModel.toggleBonus(it) },
 *     title = "Pay with bonus",
 *     subtitle = "Balance: 50,000 sum",
 *     leadingIcon = { Icon(painterResource(Res.drawable.ic_coin), null) },
 * )
 * ```
 *
 * @param checked Whether switch is checked
 * @param onCheckedChange Called when switch value changes
 * @param title Primary title text
 * @param modifier Applied to card
 * @param subtitle Optional secondary text
 * @param leadingIcon Optional icon before content
 * @param enabled Whether card and switch are enabled
 * @param shape Card corner shape
 * @param colors Color configuration
 *
 * @see SwitchCardDefaults for default values
 */
@Composable
fun SwitchCard(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    shape: Shape = SwitchCardDefaults.Shape,
    colors: SwitchCardDefaults.Colors = SwitchCardDefaults.colors(),
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = colors.container,
            disabledContainerColor = colors.container,
        ),
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = SwitchCardDefaults.HorizontalPadding,
                vertical = SwitchCardDefaults.VerticalPadding,
            ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (leadingIcon != null) {
                Box(
                    modifier = Modifier
                        .size(SwitchCardDefaults.IconContainerSize)
                        .clip(SwitchCardDefaults.IconShape)
                        .background(colors.iconBackground)
                        .padding(SwitchCardDefaults.IconPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    leadingIcon()
                }
                Spacer(Modifier.width(SwitchCardDefaults.ContentSpacing))
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = title,
                    style = System.font.body.base.bold,
                    color = colors.title,
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = System.font.body.small.medium,
                        color = colors.subtitle,
                    )
                }
            }

            Spacer(Modifier.width(SwitchCardDefaults.ContentSpacing))

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colors.switchThumb,
                    uncheckedThumbColor = colors.switchThumb,
                    checkedTrackColor = colors.switchTrackChecked,
                    uncheckedTrackColor = colors.switchTrackUnchecked,
                    checkedBorderColor = colors.switchTrackChecked,
                    uncheckedBorderColor = colors.switchTrackUnchecked,
                ),
            )
        }
    }
}

/**
 * Default values for [SwitchCard].
 */
object SwitchCardDefaults {

    val Shape: Shape = RoundedCornerShape(0.dp)
    val IconShape: Shape = RoundedCornerShape(10.dp)
    val IconContainerSize: Dp = 44.dp
    val IconPadding: Dp = 10.dp
    val HorizontalPadding: Dp = 16.dp
    val VerticalPadding: Dp = 10.dp
    val ContentSpacing: Dp = 16.dp

    /**
     * Color configuration for [SwitchCard].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val iconBackground: Color,
        val title: Color,
        val subtitle: Color,
        val switchThumb: Color,
        val switchTrackChecked: Color,
        val switchTrackUnchecked: Color,
    )

    @Composable
    fun colors(
        container: Color = Color.Transparent,
        iconBackground: Color = System.color.backgroundSecondary,
        title: Color = System.color.textBase,
        subtitle: Color = System.color.textBase,
        switchThumb: Color = System.color.iconWhite,
        switchTrackChecked: Color = System.color.buttonActive,
        switchTrackUnchecked: Color = System.color.iconDisabled,
    ): Colors = Colors(
        container = container,
        iconBackground = iconBackground,
        title = title,
        subtitle = subtitle,
        switchThumb = switchThumb,
        switchTrackChecked = switchTrackChecked,
        switchTrackUnchecked = switchTrackUnchecked,
    )
}

@Preview
@Composable
private fun SwitchCardPreview() {
    SwitchCard(
        checked = true,
        onCheckedChange = {},
        title = "Pay with bonus",
        subtitle = "Balance: 50,000 sum",
    )
}

