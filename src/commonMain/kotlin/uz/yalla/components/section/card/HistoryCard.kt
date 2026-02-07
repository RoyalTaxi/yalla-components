package uz.yalla.components.section.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Status for [HistoryCard].
 */
enum class HistoryCardStatus(val label: String) {
    Completed("Completed"),
    Canceled("Canceled"),
}

/**
 * UI state for [HistoryCard].
 *
 * @param time Display time.
 * @param price Formatted price.
 * @param status Order status.
 */
data class HistoryCardState(
    val time: String,
    val price: String,
    val status: HistoryCardStatus,
)

/**
 * Default configuration for [HistoryCard].
 */
object HistoryCardDefaults {

    /**
     * Color configuration for [HistoryCard].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val time: Color,
        val price: Color,
        val statusCompleted: Color,
        val statusCanceled: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundSecondary,
        time: Color = System.color.textBase,
        price: Color = System.color.textBase,
        statusCompleted: Color = System.color.textLink,
        statusCanceled: Color = System.color.textRed,
    ) = Colors(
        container = container,
        time = time,
        price = price,
        statusCompleted = statusCompleted,
        statusCanceled = statusCanceled,
    )

    /**
     * Dimension configuration for [HistoryCard].
     */
    @Immutable
    data class Dimens(
        val radius: Dp,
        val contentPadding: Dp,
        val spacingSmall: Dp,
        val spacingMedium: Dp,
    )

    @Composable
    fun dimens(
        radius: Dp = 16.dp,
        contentPadding: Dp = 16.dp,
        spacingSmall: Dp = 8.dp,
        spacingMedium: Dp = 16.dp,
    ) = Dimens(
        radius = radius,
        contentPadding = contentPadding,
        spacingSmall = spacingSmall,
        spacingMedium = spacingMedium,
    )
}

/**
 * History item card for ride/order history.
 *
 * Displays route, time, status, price, and optional image.
 *
 * @param state Current UI state.
 * @param onClick Called when card is clicked.
 * @param modifier Applied to card.
 * @param route Composable route display.
 * @param image Optional right-side image.
 * @param colors Color configuration.
 * @param dimens Dimension configuration.
 */
@Composable
fun HistoryCard(
    state: HistoryCardState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    route: @Composable () -> Unit = {},
    image: (@Composable () -> Unit)? = null,
    colors: HistoryCardDefaults.Colors = HistoryCardDefaults.colors(),
    dimens: HistoryCardDefaults.Dimens = HistoryCardDefaults.dimens(),
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(dimens.radius),
        colors = CardDefaults.cardColors(containerColor = colors.container),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(dimens.contentPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
            ) {
                route()

                Spacer(Modifier.height(dimens.spacingSmall))
                Spacer(Modifier.weight(1f))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = state.time,
                        color = colors.time,
                        style = System.font.body.caption,
                    )

                    Spacer(Modifier.width(dimens.spacingSmall))

                    Text(
                        text = state.status.label,
                        color = when (state.status) {
                            HistoryCardStatus.Completed -> colors.statusCompleted
                            HistoryCardStatus.Canceled -> colors.statusCanceled
                        },
                        style = System.font.body.caption,
                    )
                }
            }

            Spacer(Modifier.width(dimens.spacingMedium))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = state.price,
                    color = colors.price,
                    style = System.font.body.base.bold,
                )

                image?.invoke()
            }
        }
    }
}

@Preview
@Composable
private fun HistoryCardPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        HistoryCard(
            state = HistoryCardState(
                time = "10:30 AM",
                price = "25,000 sum",
                status = HistoryCardStatus.Completed,
            ),
            onClick = {},
        )
    }
}
