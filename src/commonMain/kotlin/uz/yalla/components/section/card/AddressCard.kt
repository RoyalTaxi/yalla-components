package uz.yalla.components.section.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * UI state for [AddressCard].
 *
 * @param name Place name (Home, Work, custom).
 * @param address Street address.
 * @param metadata Optional footer text (e.g., duration).
 */
data class AddressCardState(
    val name: String,
    val address: String,
    val metadata: String? = null,
)

/**
 * Compact address card with name, address, and optional metadata.
 *
 * Use for horizontal scrolling address lists.
 *
 * ## Usage
 *
 * ```kotlin
 * AddressCard(
 *     state = AddressCardState(
 *         name = "Home",
 *         address = "123 Main Street",
 *         metadata = "5 min",
 *     ),
 *     icon = painterResource(Res.drawable.ic_circle_point),
 *     onClick = { selectAddress(address) },
 * )
 * ```
 *
 * @param state Current UI state.
 * @param onClick Called when card is clicked.
 * @param modifier Applied to card.
 * @param icon Optional leading icon.
 * @param shape Card corner shape.
 * @param colors Color configuration.
 *
 * @see AddressCardDefaults for default values
 */
@Composable
fun AddressCard(
    state: AddressCardState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    shape: Shape = AddressCardDefaults.Shape,
    colors: AddressCardDefaults.Colors = AddressCardDefaults.colors(),
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .widthIn(max = AddressCardDefaults.MaxWidth)
            .height(AddressCardDefaults.Height),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = colors.container,
        ),
    ) {
        Column(
            modifier = Modifier.padding(AddressCardDefaults.ContentPadding),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                    Spacer(Modifier.width(AddressCardDefaults.IconSpacing))
                }

                Text(
                    text = state.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colors.name,
                    style = System.font.body.base.bold,
                )
            }

            Spacer(Modifier.height(AddressCardDefaults.ContentSpacing))

            Text(
                text = state.address,
                color = colors.address,
                style = System.font.body.small.medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(Modifier.weight(1f))

            if (state.metadata != null) {
                Text(
                    text = state.metadata,
                    color = colors.metadata,
                    style = System.font.body.base.bold,
                )
            }
        }
    }
}

/**
 * Default values for [AddressCard].
 */
object AddressCardDefaults {

    val Shape: Shape = RoundedCornerShape(20.dp)
    val MaxWidth: Dp = 248.dp
    val Height: Dp = 120.dp
    val IconSpacing: Dp = 8.dp
    val ContentSpacing: Dp = 8.dp
    val ContentPadding: PaddingValues = PaddingValues(
        vertical = 16.dp,
        horizontal = 20.dp,
    )

    /**
     * Color configuration for [AddressCard].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val name: Color,
        val address: Color,
        val metadata: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundSecondary,
        name: Color = System.color.textBase,
        address: Color = System.color.textBase,
        metadata: Color = System.color.textBase,
    ): Colors = Colors(
        container = container,
        name = name,
        address = address,
        metadata = metadata,
    )
}

@Preview
@Composable
private fun AddressCardPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        AddressCard(
            state = AddressCardState(
                name = "Home",
                address = "123 Main Street",
                metadata = "5 min",
            ),
            onClick = {},
        )
    }
}
