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
import androidx.compose.runtime.Immutable
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
 *     text = "Delete Account",
 *     onConfirm = viewModel::deleteAccount,
 * )
 * ```
 *
 * @param text Button label.
 * @param onConfirm Invoked when hold duration completes.
 * @param modifier Applied to button.
 * @param enabled When false, button is disabled.
 * @param holdDurationMs Time to hold in milliseconds.
 * @param colors Color configuration.
 *
 * @see SensitiveButtonDefaults for default values
 */
@Composable
fun SensitiveButton(
    text: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    holdDurationMs: Int = SensitiveButtonDefaults.HoldDurationMs,
    colors: SensitiveButtonDefaults.Colors = SensitiveButtonDefaults.colors(),
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
            .defaultMinSize(minHeight = SensitiveButtonDefaults.MinHeight)
            .fillMaxWidth()
            .clip(SensitiveButtonDefaults.Shape)
            .background(colors.containerColor(enabled))
            .pointerInput(enabled) {
                if (!enabled) return@pointerInput

                detectTapGestures(
                    onPress = {
                        isPressed = true
                        scope.launch {
                            progress.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(
                                    durationMillis = holdDurationMs,
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
                .clip(SensitiveButtonDefaults.Shape)
                .background(
                    color = colors.progress.copy(
                        alpha = progress.value * 0.3f
                    )
                ),
        )

        // Text
        Text(
            text = text,
            style = System.font.body.base.medium,
            color = colors.textColor(enabled),
            modifier = Modifier.padding(SensitiveButtonDefaults.ContentPadding),
        )
    }
}

/**
 * Default values for [SensitiveButton].
 */
object SensitiveButtonDefaults {

    val MinHeight: Dp = 52.dp
    val CornerRadius: Dp = 16.dp
    val Shape: Shape = RoundedCornerShape(CornerRadius)
    val ContentPadding: PaddingValues = PaddingValues(
        horizontal = 24.dp,
        vertical = 14.dp,
    )
    const val HoldDurationMs: Int = 3000

    /**
     * Color configuration for [SensitiveButton].
     */
    @Immutable
    data class Colors(
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
    ): Colors = Colors(
        container = container,
        disabledContainer = disabledContainer,
        text = text,
        disabledText = disabledText,
        progress = progress,
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
            text = "Delete Account",
            onConfirm = {},
        )
    }
}
