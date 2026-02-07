package uz.yalla.components.section.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Location point display with icon and label.
 *
 * Use for displaying origin/destination points in route views.
 *
 * ## Usage
 *
 * ```kotlin
 * LocationPoint(
 *     icon = painterResource(Res.drawable.ic_origin),
 *     label = "123 Main Street",
 *     style = LocationPointDefaults.originStyle(),
 * )
 * ```
 *
 * @param icon Location marker icon.
 * @param label Address or place name.
 * @param modifier Applied to component.
 * @param style Text and color styling.
 *
 * @see LocationPointDefaults for default values
 */
@Composable
fun LocationPoint(
    icon: Painter,
    label: String,
    modifier: Modifier = Modifier,
    style: LocationPointDefaults.Style = LocationPointDefaults.originStyle(),
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = icon,
            contentDescription = null,
        )

        Spacer(Modifier.width(LocationPointDefaults.IconLabelSpacing))

        Text(
            text = label,
            style = style.font,
            color = style.color,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

/**
 * Default values for [LocationPoint].
 */
object LocationPointDefaults {

    val IconLabelSpacing: Dp = 8.dp

    /**
     * Style configuration for [LocationPoint].
     */
    @Immutable
    data class Style(
        val font: TextStyle,
        val color: Color,
    )

    @Composable
    fun originStyle(): Style = Style(
        font = System.font.body.small.bold,
        color = System.color.textBase,
    )

    @Composable
    fun destinationStyle(): Style = Style(
        font = System.font.body.caption,
        color = System.color.textSubtle,
    )
}

@Preview
@Composable
private fun LocationPointPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "123 Main Street",
            style = System.font.body.small.bold,
        )
    }
}
