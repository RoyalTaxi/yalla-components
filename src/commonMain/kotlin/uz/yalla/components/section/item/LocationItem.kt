package uz.yalla.components.section.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Location selector item showing multiple locations in flow layout.
 *
 * Displays locations with arrows between them or placeholder when empty.
 *
 * ## Usage
 *
 * ```kotlin
 * LocationItem(
 *     locations = listOf("Home", "Office"),
 *     placeholder = "Where to?",
 *     onClick = { openLocationPicker() },
 *     leadingContent = { LocationDot(color = System.color.buttonActive) },
 *     trailingContent = { AddButton(onClick = { addStop() }) },
 * )
 * ```
 *
 * @param locations List of location names.
 * @param placeholder Text when locations is empty.
 * @param onClick Called when item is clicked.
 * @param modifier Applied to item.
 * @param leadingContent Optional leading slot.
 * @param trailingContent Optional trailing slot.
 * @param shape Item shape.
 * @param colors Color configuration.
 *
 * @see LocationItemDefaults for default values
 */
@Composable
fun LocationItem(
    locations: List<String>,
    placeholder: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    shape: Shape = LocationItemDefaults.Shape,
    colors: LocationItemDefaults.Colors = LocationItemDefaults.colors(),
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = colors.container,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LocationItemDefaults.ContentSpacing),
            modifier = Modifier
                .heightIn(min = LocationItemDefaults.MinHeight)
                .padding(start = 16.dp, end = 8.dp),
        ) {
            leadingContent?.invoke()

            if (locations.isEmpty()) {
                Text(
                    text = placeholder,
                    color = colors.placeholder,
                    style = System.font.body.base.bold,
                    modifier = Modifier.weight(1f),
                )
            } else {
                FlowRow(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    itemVerticalAlignment = Alignment.CenterVertically,
                ) {
                    locations.forEachIndexed { index, location ->
                        Text(
                            text = location,
                            color = colors.location,
                            style = System.font.body.base.bold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )

                        if (index != locations.lastIndex) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                                contentDescription = null,
                                tint = colors.arrow,
                            )
                        }
                    }
                }
            }

            trailingContent?.invoke()
        }
    }
}

/**
 * Colored dot indicator for location items.
 */
@Composable
fun LocationDot(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(LocationItemDefaults.DotSize)
            .background(
                color = System.color.iconWhite,
                shape = CircleShape,
            )
            .border(
                width = LocationItemDefaults.DotBorderWidth,
                color = color,
                shape = CircleShape,
            ),
    )
}

/**
 * Default values for [LocationItem].
 */
object LocationItemDefaults {

    val Shape: Shape = RectangleShape
    val MinHeight: Dp = 60.dp
    val ContentSpacing: Dp = 12.dp
    val DotSize: Dp = 14.dp
    val DotBorderWidth: Dp = 4.dp

    /**
     * Color configuration for [LocationItem].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val placeholder: Color,
        val location: Color,
        val arrow: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundSecondary,
        placeholder: Color = System.color.textSubtle,
        location: Color = System.color.textBase,
        arrow: Color = System.color.iconSubtle,
    ): Colors = Colors(
        container = container,
        placeholder = placeholder,
        location = location,
        arrow = arrow,
    )
}

@Preview
@Composable
private fun LocationItemPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        LocationItem(
            locations = listOf("Home", "Office"),
            placeholder = "Where to?",
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun LocationItemEmptyPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        LocationItem(
            locations = emptyList(),
            placeholder = "Where to?",
            onClick = {},
        )
    }
}
