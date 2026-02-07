package uz.yalla.components.section.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * UI state for [PromotionCard].
 *
 * @param title Primary promotional text.
 * @param subtitle Secondary description.
 */
data class PromotionCardState(
    val title: String,
    val subtitle: String,
)

/**
 * Promotional CTA card for special offers.
 *
 * Use for "Become a driver" or similar promotional actions.
 *
 * ## Usage
 *
 * ```kotlin
 * PromotionCard(
 *     state = PromotionCardState(
 *         title = "Become a Driver",
 *         subtitle = "Start earning today",
 *     ),
 *     onClick = { navigateToDriverSignup() },
 * )
 * ```
 *
 * @param state Current UI state.
 * @param onClick Called when card is clicked.
 * @param modifier Applied to card.
 * @param trailingContent Optional right-side content (icon/image).
 * @param shape Card corner shape.
 * @param colors Color configuration.
 *
 * @see PromotionCardDefaults for default values
 */
@Composable
fun PromotionCard(
    state: PromotionCardState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingContent: (@Composable () -> Unit)? = null,
    shape: Shape = PromotionCardDefaults.Shape,
    colors: PromotionCardDefaults.Colors = PromotionCardDefaults.colors(),
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(PromotionCardDefaults.Height),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = colors.container,
        ),
    ) {
        Row(
            modifier = Modifier.padding(PromotionCardDefaults.ContentPadding),
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = state.title,
                    style = System.font.title.base,
                    color = colors.title,
                )

                Spacer(Modifier.height(PromotionCardDefaults.TitleSubtitleSpacing))

                Text(
                    text = state.subtitle,
                    style = System.font.body.small.medium,
                    color = colors.subtitle,
                )
            }

            if (trailingContent != null) {
                trailingContent()
            }
        }
    }
}

/**
 * Default values for [PromotionCard].
 */
object PromotionCardDefaults {

    val Shape: Shape = RoundedCornerShape(16.dp)
    val Height: Dp = 100.dp
    val ContentPadding: PaddingValues = PaddingValues(16.dp)
    val TitleSubtitleSpacing: Dp = 4.dp

    /**
     * Color configuration for [PromotionCard].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val title: Color,
        val subtitle: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundSecondary,
        title: Color = System.color.textBase,
        subtitle: Color = System.color.textSubtle,
    ): Colors = Colors(
        container = container,
        title = title,
        subtitle = subtitle,
    )
}

@Preview
@Composable
private fun PromotionCardPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        PromotionCard(
            state = PromotionCardState(
                title = "Become a Driver",
                subtitle = "Start earning today",
            ),
            onClick = {},
        )
    }
}
