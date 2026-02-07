package uz.yalla.components.section.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * Vehicle license plate display.
 *
 * Shows car number in standard plate format.
 *
 * ## Usage
 *
 * ```kotlin
 * VehiclePlate(
 *     plateNumber = "01 A 123 AA",
 * )
 * ```
 *
 * ## With Region
 *
 * ```kotlin
 * VehiclePlate(
 *     plateNumber = "A 123 AA",
 *     region = "01",
 * )
 * ```
 *
 * @param plateNumber Main plate number.
 * @param modifier Applied to component.
 * @param region Optional region code.
 * @param shape Plate corner shape.
 * @param colors Color configuration.
 *
 * @see VehiclePlateDefaults for default values
 */
@Composable
fun VehiclePlate(
    plateNumber: String,
    modifier: Modifier = Modifier,
    region: String? = null,
    shape: Shape = VehiclePlateDefaults.Shape,
    colors: VehiclePlateDefaults.Colors = VehiclePlateDefaults.colors(),
) {
    Row(
        modifier = modifier
            .height(VehiclePlateDefaults.Height)
            .border(
                border = BorderStroke(
                    width = VehiclePlateDefaults.BorderWidth,
                    color = colors.border,
                ),
                shape = shape,
            )
            .background(
                color = colors.container,
                shape = shape,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        if (region != null) {
            Box(
                modifier = Modifier
                    .padding(
                        start = VehiclePlateDefaults.RegionHorizontalPadding,
                        end = VehiclePlateDefaults.RegionHorizontalPadding,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = region,
                    style = System.font.body.small.bold,
                    color = colors.text,
                )
            }

            Box(
                modifier = Modifier
                    .width(VehiclePlateDefaults.DividerWidth)
                    .height(VehiclePlateDefaults.Height - VehiclePlateDefaults.DividerVerticalPadding * 2)
                    .background(colors.divider),
            )
        }

        Text(
            text = plateNumber,
            style = System.font.body.base.bold,
            color = colors.text,
            modifier = Modifier.padding(
                horizontal = VehiclePlateDefaults.ContentHorizontalPadding,
            ),
        )
    }
}

/**
 * Default values for [VehiclePlate].
 */
object VehiclePlateDefaults {

    val Shape: Shape = RoundedCornerShape(4.dp)
    val Height: Dp = 32.dp
    val BorderWidth: Dp = 1.dp
    val RegionHorizontalPadding: Dp = 6.dp
    val ContentHorizontalPadding: Dp = 8.dp
    val DividerWidth: Dp = 1.dp
    val DividerVerticalPadding: Dp = 4.dp

    /**
     * Color configuration for [VehiclePlate].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val text: Color,
        val border: Color,
        val divider: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundBase,
        text: Color = System.color.textBase,
        border: Color = System.color.borderDisabled,
        divider: Color = System.color.borderDisabled,
    ): Colors = Colors(
        container = container,
        text = text,
        border = border,
        divider = divider,
    )
}

@Preview
@Composable
private fun VehiclePlatePreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        VehiclePlate(
            plateNumber = "A 123 AA",
            region = "01",
        )
    }
}
