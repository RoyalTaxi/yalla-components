package uz.yalla.components.section.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * UI state for [BonusCard].
 *
 * @param title Card title.
 * @param subtitle Descriptive subtitle.
 * @param balance Formatted balance amount.
 */
data class BonusCardState(
    val title: String,
    val subtitle: String,
    val balance: String,
)

/**
 * Promotional bonus card with background image.
 *
 * Displays bonus balance with title and subtitle over styled background.
 *
 * ## Usage
 *
 * ```kotlin
 * BonusCard(
 *     state = BonusCardState(
 *         title = "Your Bonuses",
 *         subtitle = "1 ride = 5% cashback",
 *         balance = "50,000",
 *     ),
 *     background = painterResource(Res.drawable.ic_bonuses_background),
 * )
 * ```
 *
 * @param state Current UI state.
 * @param background Background image painter.
 * @param modifier Applied to card.
 * @param shape Card corner shape.
 * @param colors Color configuration.
 *
 * @see BonusCardDefaults for default values
 */
@Composable
fun BonusCard(
    state: BonusCardState,
    background: Painter,
    modifier: Modifier = Modifier,
    shape: Shape = BonusCardDefaults.Shape,
    colors: BonusCardDefaults.Colors = BonusCardDefaults.colors(),
) {
    Card(
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
            .height(BonusCardDefaults.Height),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = background,
                    contentScale = ContentScale.Crop,
                )
                .padding(BonusCardDefaults.ContentPadding),
        ) {
            Text(
                text = state.title,
                style = System.font.body.base.medium,
                color = colors.title,
            )

            Text(
                text = state.subtitle,
                style = System.font.body.small.medium,
                color = colors.subtitle,
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = state.balance,
                style = System.font.title.xLarge,
                color = colors.balance,
            )
        }
    }
}

/**
 * Default values for [BonusCard].
 */
object BonusCardDefaults {

    val Shape: Shape = RoundedCornerShape(16.dp)
    val Height: Dp = 148.dp
    val ContentPadding: PaddingValues = PaddingValues(
        vertical = 16.dp,
        horizontal = 20.dp,
    )

    /**
     * Color configuration for [BonusCard].
     */
    @Immutable
    data class Colors(
        val title: Color,
        val subtitle: Color,
        val balance: Color,
    )

    @Composable
    fun colors(
        title: Color = System.color.textWhite,
        subtitle: Color = System.color.textWhite,
        balance: Color = System.color.textWhite,
    ): Colors = Colors(
        title = title,
        subtitle = subtitle,
        balance = balance,
    )
}

@Preview
@Composable
private fun BonusCardPreview() {
    Box(
        modifier = Modifier
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(148.dp)
                .background(Color.Blue.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
                .padding(16.dp),
        ) {
            Text(
                text = "Your Bonuses",
                style = System.font.body.base.medium,
                color = Color.White,
            )
            Text(
                text = "1 ride = 5% cashback",
                style = System.font.body.small.medium,
                color = Color.White,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "50,000",
                style = System.font.title.xLarge,
                color = Color.White,
            )
        }
    }
}
