package uz.yalla.components.section.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * Animated modal bottom sheet wrapper.
 *
 * Handles visibility animation and provides callbacks for sheet state.
 *
 * ## Usage
 *
 * ```kotlin
 * Sheet(
 *     isVisible = showSheet,
 *     onDismissRequest = { showSheet = false },
 * ) {
 *     Column(modifier = Modifier.padding(16.dp)) {
 *         Text("Sheet content")
 *     }
 * }
 * ```
 *
 * @param isVisible Whether sheet is visible.
 * @param onDismissRequest Called when sheet is dismissed.
 * @param modifier Applied to sheet.
 * @param sheetState State for sheet behavior.
 * @param shape Sheet corner shape.
 * @param colors Color configuration.
 * @param dragHandle Optional drag handle composable.
 * @param onFullyExpanded Called when sheet fully expands.
 * @param content Sheet content.
 *
 * @see SheetDefaults for default values
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    shape: Shape = SheetDefaults.Shape,
    colors: SheetDefaults.Colors = SheetDefaults.colors(),
    dragHandle: @Composable (() -> Unit)? = { SheetDragHandle() },
    contentWindowInsets: @Composable () -> WindowInsets = { WindowInsets.ime.union(WindowInsets.navigationBars) },
    onFullyExpanded: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    var shouldShow by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            shouldShow = true
        } else if (shouldShow) {
            sheetState.hide()
            shouldShow = false
        }
    }

    LaunchedEffect(sheetState, onFullyExpanded) {
        if (onFullyExpanded == null) return@LaunchedEffect
        snapshotFlow { sheetState.currentValue to sheetState.targetValue }
            .distinctUntilChanged()
            .filter { (current, target) -> current == SheetValue.Expanded && current == target }
            .collect { onFullyExpanded() }
    }

    if (shouldShow) {
        ModalBottomSheet(
            onDismissRequest = {
                shouldShow = false
                onDismissRequest()
            },
            modifier = modifier,
            sheetState = sheetState,
            sheetMaxWidth = SheetDefaults.MaxWidth,
            shape = shape,
            containerColor = colors.container,
            contentColor = contentColorFor(colors.container),
            tonalElevation = 0.dp,
            scrimColor = colors.scrim,
            dragHandle = dragHandle,
            contentWindowInsets = contentWindowInsets,
            properties = ModalBottomSheetDefaults.properties,
            content = content,
        )
    }
}

/**
 * Standard drag handle for sheets.
 */
@Composable
fun SheetDragHandle(
    modifier: Modifier = Modifier,
    color: Color = System.color.backgroundTertiary,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(
            width = SheetDefaults.DragHandleContainerWidth,
            height = SheetDefaults.DragHandleContainerHeight,
        ),
    ) {
        Box(
            modifier = Modifier
                .background(shape = CircleShape, color = color)
                .size(
                    width = SheetDefaults.DragHandleWidth,
                    height = SheetDefaults.DragHandleHeight,
                ),
        )
    }
}

/**
 * Default values for [Sheet].
 */
object SheetDefaults {

    val Shape: Shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    val MaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth
    val DragHandleWidth: Dp = 36.dp
    val DragHandleHeight: Dp = 5.dp
    val DragHandleContainerWidth: Dp = 36.dp
    val DragHandleContainerHeight: Dp = 16.dp

    /**
     * Color configuration for [Sheet].
     */
    @Immutable
    data class Colors(
        val container: Color,
        val scrim: Color,
    )

    @Composable
    fun colors(
        container: Color = System.color.backgroundBase,
        scrim: Color = BottomSheetDefaults.ScrimColor,
    ): Colors = Colors(
        container = container,
        scrim = scrim,
    )
}

@Preview
@Composable
private fun SheetDragHandlePreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        SheetDragHandle()
    }
}
