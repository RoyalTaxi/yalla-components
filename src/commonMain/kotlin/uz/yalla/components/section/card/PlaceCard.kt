package uz.yalla.components.section.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * UI state for [PlaceCard].
 *
 * @param name Place display name.
 * @param address Optional address text.
 * @param isEmpty Whether place is not yet configured.
 */
data class PlaceCardState(
    val name: String,
    val address: String? = null,
    val isEmpty: Boolean = address == null,
)

/**
 * Default configuration for [PlaceCard].
 */
object PlaceCardDefaults {

    /**
     * Color configuration for [PlaceCard].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val name: Color,
        val address: Color,
        val hint: Color,
        val icon: Color,
        val iconBackground: Color,
        val iconEmpty: Color,
        val iconBackgroundEmpty: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundSecondary,
        name: Color = System.color.textBase,
        address: Color = System.color.textBase,
        hint: Color = System.color.textSubtle,
        icon: Color = System.color.backgroundBrandBase,
        iconBackground: Color = System.color.backgroundBrandBase.copy(alpha = 0.15f),
        iconEmpty: Color = System.color.iconSubtle,
        iconBackgroundEmpty: Color = System.color.backgroundTertiary,
    ) = Colors(
        container = container,
        name = name,
        address = address,
        hint = hint,
        icon = icon,
        iconBackground = iconBackground,
        iconEmpty = iconEmpty,
        iconBackgroundEmpty = iconBackgroundEmpty,
    )

    /**
     * Dimension configuration for [PlaceCard].
     */
    @Immutable
    data class Dimens(
        val radius: Dp,
        val iconRadius: Dp,
        val height: Dp,
        val iconPadding: Dp,
        val contentPadding: PaddingValues,
    )

    @Composable
    fun dimens(
        radius: Dp = 20.dp,
        iconRadius: Dp = 14.dp,
        height: Dp = 120.dp,
        iconPadding: Dp = 10.dp,
        contentPadding: PaddingValues = PaddingValues(
            top = 10.dp,
            end = 10.dp,
            bottom = 8.dp,
            start = 16.dp,
        ),
    ) = Dimens(
        radius = radius,
        iconRadius = iconRadius,
        height = height,
        iconPadding = iconPadding,
        contentPadding = contentPadding,
    )
}

/**
 * Saved place card displaying name, address, and icon.
 *
 * Shows different states for configured and empty places.
 *
 * @param state Current UI state.
 * @param icon Place icon.
 * @param onClick Called when card is clicked.
 * @param modifier Applied to card.
 * @param emptyHint Hint text for empty state.
 * @param colors Color configuration.
 * @param dimens Dimension configuration.
 */
@Composable
fun PlaceCard(
    state: PlaceCardState,
    icon: Painter?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    emptyHint: String = "Tap to add",
    colors: PlaceCardDefaults.Colors = PlaceCardDefaults.colors(),
    dimens: PlaceCardDefaults.Dimens = PlaceCardDefaults.dimens(),
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(dimens.height),
        shape = RoundedCornerShape(dimens.radius),
        colors = CardDefaults.cardColors(containerColor = colors.container),
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .padding(dimens.contentPadding),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = state.name,
                    style = System.font.title.base,
                    color = colors.name,
                )

                if (icon != null) {
                    Surface(
                        shape = RoundedCornerShape(dimens.iconRadius),
                        color = if (state.isEmpty) colors.iconBackgroundEmpty else colors.iconBackground,
                    ) {
                        Icon(
                            painter = icon,
                            contentDescription = null,
                            tint = if (state.isEmpty) colors.iconEmpty else colors.icon,
                            modifier = Modifier.padding(dimens.iconPadding),
                        )
                    }
                }
            }

            Text(
                text = if (state.isEmpty) emptyHint else (state.address ?: ""),
                style = System.font.body.caption,
                maxLines = 2,
                color = if (state.isEmpty) colors.hint else colors.address,
            )
        }
    }
}

@Preview
@Composable
private fun PlaceCardPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        PlaceCard(
            state = PlaceCardState(
                name = "Home",
                address = "123 Main Street, Apartment 4B",
            ),
            icon = null,
            onClick = {},
        )
    }
}
