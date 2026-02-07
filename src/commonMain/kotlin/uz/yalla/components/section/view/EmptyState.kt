package uz.yalla.components.section.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * UI state for [EmptyState].
 *
 * @param title Empty state title.
 * @param description Optional description text.
 */
data class EmptyStateContent(
    val title: String,
    val description: String? = null,
)

/**
 * Empty state view for lists with no content.
 *
 * Shows illustration, title, and optional description.
 *
 * ## Usage
 *
 * ```kotlin
 * EmptyState(
 *     image = painterResource(Res.drawable.img_empty_history),
 *     content = EmptyStateContent(
 *         title = "No rides yet",
 *         description = "Your ride history will appear here",
 *     ),
 * )
 * ```
 *
 * ## With Action
 *
 * ```kotlin
 * EmptyState(
 *     image = painterResource(Res.drawable.img_empty_notifications),
 *     content = EmptyStateContent(title = "No notifications"),
 *     action = {
 *         TextButton(onClick = { refresh() }) {
 *             Text("Refresh")
 *         }
 *     },
 * )
 * ```
 *
 * @param image Illustration image.
 * @param content Text content.
 * @param modifier Applied to component.
 * @param action Optional action composable.
 * @param colors Color configuration.
 *
 * @see EmptyStateDefaults for default values
 */
@Composable
fun EmptyState(
    image: Painter,
    content: EmptyStateContent,
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null,
    colors: EmptyStateDefaults.Colors = EmptyStateDefaults.colors(),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(EmptyStateDefaults.ContentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(EmptyStateDefaults.ImageHeight),
        )

        Spacer(Modifier.height(EmptyStateDefaults.ImageTitleSpacing))

        Text(
            text = content.title,
            style = System.font.title.base,
            color = colors.title,
            textAlign = TextAlign.Center,
        )

        if (content.description != null) {
            Spacer(Modifier.height(EmptyStateDefaults.TitleDescriptionSpacing))

            Text(
                text = content.description,
                style = System.font.body.base.medium,
                color = colors.description,
                textAlign = TextAlign.Center,
            )
        }

        if (action != null) {
            Spacer(Modifier.height(EmptyStateDefaults.DescriptionActionSpacing))
            action()
        }
    }
}

/**
 * Default values for [EmptyState].
 */
object EmptyStateDefaults {

    val ContentPadding: PaddingValues = PaddingValues(
        horizontal = 32.dp,
        vertical = 48.dp,
    )
    val ImageHeight: Dp = 180.dp
    val ImageTitleSpacing: Dp = 24.dp
    val TitleDescriptionSpacing: Dp = 8.dp
    val DescriptionActionSpacing: Dp = 24.dp

    /**
     * Color configuration for [EmptyState].
     */
    @Immutable
    data class Colors(
        val title: Color,
        val description: Color,
    )

    @Composable
    fun colors(
        title: Color = System.color.textBase,
        description: Color = System.color.textSubtle,
    ): Colors = Colors(
        title = title,
        description = description,
    )
}

@Preview
@Composable
private fun EmptyStatePreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp),
        ) {
            Text(
                text = "No rides yet",
                style = System.font.title.base,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Your ride history will appear here",
                style = System.font.body.base.medium,
                textAlign = TextAlign.Center,
            )
        }
    }
}
