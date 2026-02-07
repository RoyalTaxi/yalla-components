package uz.yalla.components.primitive.indicator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Circular loading indicator.
 *
 * ## Usage
 *
 * ```kotlin
 * if (isLoading) {
 *     LoadingIndicator()
 * }
 * ```
 *
 * @param modifier Applied to indicator.
 * @param size Indicator size variant.
 * @param colors Color configuration.
 *
 * @see LoadingIndicatorDefaults for default values
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    size: LoadingIndicatorSize = LoadingIndicatorSize.Medium,
    colors: LoadingIndicatorDefaults.Colors = LoadingIndicatorDefaults.colors(),
) {
    CircularProgressIndicator(
        modifier = modifier.size(LoadingIndicatorDefaults.size(size)),
        color = colors.indicator,
        trackColor = colors.track,
        strokeWidth = LoadingIndicatorDefaults.strokeWidth(size),
    )
}

/**
 * Size variants for [LoadingIndicator].
 */
enum class LoadingIndicatorSize {
    Small,
    Medium,
    Large,
}

/**
 * Default values for [LoadingIndicator].
 */
object LoadingIndicatorDefaults {

    fun size(size: LoadingIndicatorSize): Dp = when (size) {
        LoadingIndicatorSize.Small -> 20.dp
        LoadingIndicatorSize.Medium -> 36.dp
        LoadingIndicatorSize.Large -> 48.dp
    }

    fun strokeWidth(size: LoadingIndicatorSize): Dp = when (size) {
        LoadingIndicatorSize.Small -> 2.dp
        LoadingIndicatorSize.Medium -> 3.dp
        LoadingIndicatorSize.Large -> 4.dp
    }

    /**
     * Color configuration for [LoadingIndicator].
     */
    @Immutable
    data class Colors(
        val indicator: Color,
        val track: Color,
    )

    @Composable
    fun colors(
        indicator: Color = System.color.buttonActive,
        track: Color = System.color.backgroundTertiary,
    ): Colors = Colors(
        indicator = indicator,
        track = track,
    )
}


@Preview
@Composable
private fun LoadingIndicatorPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        LoadingIndicator()
    }
}
