package uz.yalla.components.section.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * UI state for [TariffItem].
 *
 * @param name Tariff name.
 * @param price Formatted price.
 * @param selected Whether tariff is selected.
 */
data class TariffItemState(
    val name: String,
    val price: String,
    val selected: Boolean,
)

/**
 * Tariff selection card with name, price, and optional image.
 *
 * Shows selected state with gradient border.
 *
 * ## Usage
 *
 * ```kotlin
 * TariffItem(
 *     state = TariffItemState(
 *         name = "Comfort",
 *         price = "25,000 sum",
 *         selected = selectedTariff == tariff,
 *     ),
 *     onClick = { selectTariff(tariff) },
 *     image = {
 *         AsyncImage(
 *             model = tariff.iconUrl,
 *             contentDescription = null,
 *         )
 *     },
 * )
 * ```
 *
 * @param state Current UI state.
 * @param onClick Called when item is clicked.
 * @param modifier Applied to item.
 * @param image Optional bottom image slot.
 * @param shape Item corner shape.
 * @param colors Color configuration.
 *
 * @see TariffItemDefaults for default values
 */
@Composable
fun TariffItem(
    state: TariffItemState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    image: (@Composable () -> Unit)? = null,
    shape: Shape = TariffItemDefaults.Shape,
    colors: TariffItemDefaults.Colors = TariffItemDefaults.colors(),
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .height(TariffItemDefaults.Height)
            .widthIn(min = TariffItemDefaults.MinWidth),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = if (state.selected) {
                colors.selectedContainer
            } else {
                colors.container
            },
        ),
        border = if (state.selected) {
            BorderStroke(
                width = TariffItemDefaults.SelectedBorderWidth,
                brush = colors.selectedBorder,
            )
        } else {
            null
        },
    ) {
        Column(modifier = Modifier.padding(TariffItemDefaults.ContentPadding)) {
            Text(
                text = state.name,
                color = colors.name,
                style = System.font.body.base.bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(Modifier.height(TariffItemDefaults.NamePriceSpacing))

            Text(
                text = state.price,
                color = colors.price,
                style = System.font.body.base.bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            if (image != null) {
                Spacer(Modifier.height(TariffItemDefaults.PriceImageSpacing))
                image()
            }
        }
    }
}

/**
 * Default values for [TariffItem].
 */
object TariffItemDefaults {

    val Shape: Shape = RoundedCornerShape(20.dp)
    val Height: Dp = 120.dp
    val MinWidth: Dp = 140.dp
    val ContentPadding: Dp = 12.dp
    val SelectedBorderWidth: Dp = 2.dp
    val NamePriceSpacing: Dp = 6.dp
    val PriceImageSpacing: Dp = 10.dp

    /**
     * Color configuration for [TariffItem].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val selectedContainer: Color,
        val name: Color,
        val price: Color,
        val selectedBorder: Brush,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundSecondary,
        selectedContainer: Color = System.color.backgroundBase,
        name: Color = System.color.textBase,
        price: Color = System.color.textBase,
        selectedBorder: Brush = System.color.sunsetNight,
    ): Colors = Colors(
        container = container,
        selectedContainer = selectedContainer,
        name = name,
        price = price,
        selectedBorder = selectedBorder,
    )
}

@Preview
@Composable
private fun TariffItemPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        TariffItem(
            state = TariffItemState(
                name = "Comfort",
                price = "25,000 sum",
                selected = true,
            ),
            onClick = {},
        )
    }
}
