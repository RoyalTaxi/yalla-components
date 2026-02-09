package uz.yalla.components.primitive.button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.launch
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * State for [SensitiveButton] component.
 *
 * @property text Button label.
 * @property enabled When false, button is disabled.
 */
data class SensitiveButtonState(
    val text: String,
    val enabled: Boolean = true
)

/**
 * Countdown confirmation button for destructive or sensitive actions.
 *
 * Requires user to hold the button for a duration before action executes.
 * Shows animated progress during hold. Cancels if released early.
 *
 * Use for actions like "Delete Account", "Cancel Order", or "Logout".
 *
 * ## Usage
 *
 * ```kotlin
 * SensitiveButton(
 *     state = SensitiveButtonState(text = "Delete Account"),
 *     onConfirm = viewModel::deleteAccount,
 * )
 * ```
 *
 * @param state Button state containing text and enabled.
 * @param onConfirm Invoked when hold duration completes.
 * @param modifier Applied to button.
 * @param colors Color configuration, defaults to [SensitiveButtonDefaults.colors].
 * @param style Text style configuration, defaults to [SensitiveButtonDefaults.style].
 * @param dimens Dimension configuration, defaults to [SensitiveButtonDefaults.dimens].
 *
 * @see SensitiveButtonDefaults for default values
 */
@Composable
fun SensitiveButton(
    state: SensitiveButtonState,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    colors: SensitiveButtonDefaults.SensitiveButtonColors = SensitiveButtonDefaults.colors(),
    style: SensitiveButtonDefaults.SensitiveButtonStyle = SensitiveButtonDefaults.style(),
    dimens: SensitiveButtonDefaults.SensitiveButtonDimens = SensitiveButtonDefaults.dimens(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    val progress = remember { Animatable(0f) }
    var isPressed by remember { mutableStateOf(false) }

    // Reset progress when lifecycle pauses
    LaunchedEffect(lifecycleOwner) {
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED).not()) {
            progress.snapTo(0f)
        }
    }

    Box(
        modifier = modifier
            .defaultMinSize(minHeight = dimens.minHeight)
            .fillMaxWidth()
            .clip(dimens.shape)
            .background(colors.containerColor(state.enabled))
            .pointerInput(state.enabled) {
                if (!state.enabled) return@pointerInput

                detectTapGestures(
                    onPress = {
                        isPressed = true
                        scope.launch {
                            progress.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(
                                    durationMillis = dimens.holdDurationMs,
                                    easing = LinearEasing,
                                ),
                            )
                            if (progress.value >= 1f) {
                                onConfirm()
                            }
                        }
                        tryAwaitRelease()
                        isPressed = false
                        progress.snapTo(0f)
                    },
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        // Progress overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(dimens.shape)
                .background(
                    color = colors.progress.copy(
                        alpha = progress.value * 0.3f
                    )
                ),
        )

        // Text
        Text(
            text = state.text,
            style = style.text,
            color = colors.textColor(state.enabled),
            modifier = Modifier.padding(dimens.contentPadding),
        )
    }
}

/**
 * Default configuration values for [SensitiveButton].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object SensitiveButtonDefaults {

    /**
     * Color configuration for [SensitiveButton].
     *
     * @param container Background color when enabled.
     * @param disabledContainer Background color when disabled.
     * @param text Text color when enabled.
     * @param disabledText Text color when disabled.
     * @param progress Progress overlay color.
     */
    data class SensitiveButtonColors(
        val container: Color,
        val disabledContainer: Color,
        val text: Color,
        val disabledText: Color,
        val progress: Color,
    ) {
        fun containerColor(enabled: Boolean): Color =
            if (enabled) container else disabledContainer

        fun textColor(enabled: Boolean): Color =
            if (enabled) text else disabledText
    }

    @Composable
    fun colors(
        container: Color = System.color.textRed.copy(alpha = 0.1f),
        disabledContainer: Color = System.color.backgroundTertiary,
        text: Color = System.color.textRed,
        disabledText: Color = System.color.textSubtle,
        progress: Color = System.color.textRed,
    ) = SensitiveButtonColors(
        container = container,
        disabledContainer = disabledContainer,
        text = text,
        disabledText = disabledText,
        progress = progress,
    )

    /**
     * Text style configuration for [SensitiveButton].
     *
     * @param text Button text style.
     */
    data class SensitiveButtonStyle(
        val text: TextStyle,
    )

    @Composable
    fun style(
        text: TextStyle = System.font.body.base.medium,
    ) = SensitiveButtonStyle(
        text = text,
    )

    /**
     * Dimension configuration for [SensitiveButton].
     *
     * @param minHeight Minimum button height.
     * @param shape Button shape.
     * @param contentPadding Padding inside the button.
     * @param holdDurationMs Time to hold in milliseconds.
     */
    data class SensitiveButtonDimens(
        val minHeight: Dp,
        val shape: Shape,
        val contentPadding: PaddingValues,
        val holdDurationMs: Int,
    )

    @Composable
    fun dimens(
        minHeight: Dp = 52.dp,
        shape: Shape = RoundedCornerShape(16.dp),
        contentPadding: PaddingValues = PaddingValues(
            horizontal = 24.dp,
            vertical = 14.dp,
        ),
        holdDurationMs: Int = 3000,
    ) = SensitiveButtonDimens(
        minHeight = minHeight,
        shape = shape,
        contentPadding = contentPadding,
        holdDurationMs = holdDurationMs,
    )
}

@Preview
@Composable
private fun SensitiveButtonPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        SensitiveButton(
            state = SensitiveButtonState(text = "Delete Account"),
            onConfirm = {},
        )
    }
}
