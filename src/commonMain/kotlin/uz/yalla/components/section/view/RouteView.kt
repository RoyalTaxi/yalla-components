package uz.yalla.components.section.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Data class representing a location in a route.
 */
@Immutable
data class RouteLocation(
    val name: String,
    val isOrigin: Boolean = false,
)

/**
 * Route view showing origin and destination points.
 *
 * Displays a vertical list of locations with appropriate styling.
 *
 * ## Usage
 *
 * ```kotlin
 * RouteView(
 *     locations = listOf(
 *         RouteLocation("123 Main Street", isOrigin = true),
 *         RouteLocation("456 Oak Avenue"),
 *     ),
 *     originIcon = painterResource(Res.drawable.ic_origin),
 *     destinationIcon = painterResource(Res.drawable.ic_destination),
 * )
 * ```
 *
 * @param locations List of route locations.
 * @param originIcon Icon for origin point.
 * @param destinationIcon Icon for destination points.
 * @param modifier Applied to component.
 *
 * @see RouteViewDefaults for default values
 */
@Composable
fun RouteView(
    locations: List<RouteLocation>,
    originIcon: Painter,
    destinationIcon: Painter,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(RouteViewDefaults.ItemSpacing),
    ) {
        locations.forEachIndexed { index, location ->
            val isOrigin = index == 0 || location.isOrigin

            LocationPoint(
                icon = if (isOrigin) originIcon else destinationIcon,
                label = location.name,
                style = if (isOrigin) {
                    LocationPointDefaults.originStyle()
                } else {
                    LocationPointDefaults.destinationStyle()
                },
            )
        }
    }
}

/**
 * Default values for [RouteView].
 */
object RouteViewDefaults {

    val ItemSpacing: Dp = 8.dp
}

@Preview
@Composable
private fun RouteViewPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "123 Main Street",
                style = System.font.body.small.bold,
            )
            Text(
                text = "456 Oak Avenue",
                style = System.font.body.caption,
            )
        }
    }
}
