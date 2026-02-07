package uz.yalla.components.primitive.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.yalla.design.theme.System

/**
 * Default values and factory functions for [PrimaryButton].
 *
 * Use these when customizing button appearance:
 *
 * ```kotlin
 * PrimaryButton(
 *     text = "Custom",
 *     onClick = {},
 *     colors = PrimaryButtonDefaults.colors(
 *         container = CustomColor,
 *     ),
 * )
 * ```
 */
object PrimaryButtonDefaults {

    /** Default button corner radius. */
    val CornerRadius: Dp = 16.dp

    /** Icon dimensions for leading/trailing slots. */
    val IconSize: Dp = 20.dp

    /** Spacing between icon and text. */
    val IconSpacing: Dp = 8.dp

    /** Button shape with rounded corners. */
    val Shape: Shape = RoundedCornerShape(CornerRadius)

    /** Minimum width for each size variant. */
    fun minWidth(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 80.dp
        ButtonSize.Medium -> 120.dp
        ButtonSize.Large -> 160.dp
    }

    /** Minimum height for each size variant. */
    fun minHeight(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 40.dp
        ButtonSize.Medium -> 52.dp
        ButtonSize.Large -> 60.dp
    }

    /** Content padding for each size variant. */
    fun contentPadding(size: ButtonSize): PaddingValues = when (size) {
        ButtonSize.Small -> PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ButtonSize.Medium -> PaddingValues(horizontal = 24.dp, vertical = 14.dp)
        ButtonSize.Large -> PaddingValues(horizontal = 32.dp, vertical = 18.dp)
    }

    /** Text style for each size variant. */
    @Composable
    fun textStyle(size: ButtonSize): TextStyle = when (size) {
        ButtonSize.Small -> System.font.body.small.medium
        ButtonSize.Medium -> System.font.body.base.medium
        ButtonSize.Large -> System.font.body.large.medium
    }

    /**
     * Color configuration for [PrimaryButton].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val content: Color,
        val disabledContainer: Color,
        val disabledContent: Color,
    ) {
        /** Resolves container color based on enabled state. */
        fun containerColor(enabled: Boolean): Color =
            if (enabled) container else disabledContainer

        /** Resolves content color based on enabled state. */
        fun contentColor(enabled: Boolean): Color =
            if (enabled) content else disabledContent
    }

    /**
     * Creates color configuration for [PrimaryButton].
     *
     * @param container Background color when enabled.
     * @param content Text and icon color when enabled.
     * @param disabledContainer Background color when disabled.
     * @param disabledContent Text and icon color when disabled.
     */
    @Composable
    fun colors(
        container: Color = System.color.buttonActive,
        content: Color = System.color.textWhite,
        disabledContainer: Color = System.color.buttonDisabled,
        disabledContent: Color = System.color.textWhite,
    ): Colors = Colors(
        container = container,
        content = content,
        disabledContainer = disabledContainer,
        disabledContent = disabledContent,
    )
}

