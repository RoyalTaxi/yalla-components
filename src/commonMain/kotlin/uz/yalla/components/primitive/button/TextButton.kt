package uz.yalla.components.primitive.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Text-only button for low-emphasis actions.
 *
 * Use for tertiary actions, links, or actions that should be less prominent.
 *
 * ## Usage
 *
 * ```kotlin
 * TextButton(
 *     text = "Learn More",
 *     onClick = onLearnMore,
 * )
 * ```
 *
 * @param text Button label.
 * @param onClick Invoked on click.
 * @param modifier Applied to button.
 * @param enabled When false, button is disabled.
 * @param loading When true, shows spinner.
 * @param size Button size variant.
 * @param colors Color configuration.
 * @param leadingIcon Optional icon before text.
 * @param trailingIcon Optional icon after text.
 *
 * @see PrimaryButton for primary actions
 * @see TextButtonDefaults for default values
 */
@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    size: ButtonSize = ButtonSize.Medium,
    colors: TextButtonDefaults.Colors = TextButtonDefaults.colors(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val isInteractive = enabled && !loading

    Surface(
        onClick = onClick,
        modifier = modifier.defaultMinSize(
            minWidth = TextButtonDefaults.minWidth(size),
            minHeight = TextButtonDefaults.minHeight(size),
        ),
        enabled = isInteractive,
        color = Color.Transparent,
        contentColor = colors.contentColor(isInteractive),
    ) {
        Row(
            modifier = Modifier.padding(TextButtonDefaults.contentPadding(size)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(TextButtonDefaults.IconSize),
                    strokeWidth = 2.dp,
                    color = LocalContentColor.current,
                )
            } else {
                leadingIcon?.let { icon ->
                    Box(Modifier.size(TextButtonDefaults.IconSize), Alignment.Center) { icon() }
                    Spacer(Modifier.width(TextButtonDefaults.IconSpacing))
                }

                Text(text = text, style = TextButtonDefaults.textStyle(size))

                trailingIcon?.let { icon ->
                    Spacer(Modifier.width(TextButtonDefaults.IconSpacing))
                    Box(Modifier.size(TextButtonDefaults.IconSize), Alignment.Center) { icon() }
                }
            }
        }
    }
}

/**
 * Default values for [TextButton].
 */
object TextButtonDefaults {

    val IconSize: Dp = 20.dp
    val IconSpacing: Dp = 8.dp

    fun minWidth(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 60.dp
        ButtonSize.Medium -> 80.dp
        ButtonSize.Large -> 100.dp
    }

    fun minHeight(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 36.dp
        ButtonSize.Medium -> 44.dp
        ButtonSize.Large -> 52.dp
    }

    fun contentPadding(size: ButtonSize): PaddingValues = when (size) {
        ButtonSize.Small -> PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        ButtonSize.Medium -> PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ButtonSize.Large -> PaddingValues(horizontal = 20.dp, vertical = 14.dp)
    }

    @Composable
    fun textStyle(size: ButtonSize): TextStyle = when (size) {
        ButtonSize.Small -> System.font.body.small.medium
        ButtonSize.Medium -> System.font.body.base.medium
        ButtonSize.Large -> System.font.body.large.medium
    }

    /**
     * Color configuration for [TextButton].
     */
    @Immutable
    data class Colors(
        val content: Color,
        val disabledContent: Color,
    ) {
        fun contentColor(enabled: Boolean): Color =
            if (enabled) content else disabledContent
    }

    @Composable
    fun colors(
        content: Color = System.color.textLink,
        disabledContent: Color = System.color.textSubtle,
    ): Colors = Colors(
        content = content,
        disabledContent = disabledContent,
    )
}

@Preview
@Composable
private fun TextButtonPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        TextButton(
            text = "Learn More",
            onClick = {},
        )
    }
}
