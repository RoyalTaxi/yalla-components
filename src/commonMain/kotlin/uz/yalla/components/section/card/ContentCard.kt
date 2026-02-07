package uz.yalla.components.section.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.yalla.design.theme.System

/**
 * General purpose content card with customizable content.
 *
 * Flexible container for grouped content sections.
 *
 * ## Usage
 *
 * ```kotlin
 * ContentCard {
 *     Text("Card Title", style = System.font.title.base)
 *     Spacer(Modifier.height(8.dp))
 *     Text("Card description goes here")
 * }
 * ```
 *
 * ## Clickable
 *
 * ```kotlin
 * ContentCard(onClick = { navigateToDetails() }) {
 *     ListItem(headlineText = "Settings")
 * }
 * ```
 *
 * @param modifier Applied to card
 * @param onClick Optional click handler
 * @param enabled Whether card is enabled when clickable
 * @param shape Card corner shape
 * @param colors Color configuration
 * @param content Card content
 *
 * @see ContentCardDefaults for default values
 */
@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    shape: Shape = ContentCardDefaults.Shape,
    colors: ContentCardDefaults.Colors = ContentCardDefaults.colors(),
    content: @Composable ColumnScope.() -> Unit,
) {
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = colors.container,
                disabledContainerColor = colors.disabledContainer,
            ),
        ) {
            Column(
                modifier = Modifier.padding(ContentCardDefaults.ContentPadding),
                content = content,
            )
        }
    } else {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = colors.container,
            ),
        ) {
            Column(
                modifier = Modifier.padding(ContentCardDefaults.ContentPadding),
                content = content,
            )
        }
    }
}

/**
 * Default values for [ContentCard].
 */
object ContentCardDefaults {

    val Shape: Shape = RoundedCornerShape(16.dp)
    val ContentPadding: Dp = 16.dp

    /**
     * Color configuration for [ContentCard].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val disabledContainer: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundSecondary,
        disabledContainer: Color = System.color.backgroundSecondary.copy(alpha = 0.6f),
    ): Colors = Colors(
        container = container,
        disabledContainer = disabledContainer,
    )
}

