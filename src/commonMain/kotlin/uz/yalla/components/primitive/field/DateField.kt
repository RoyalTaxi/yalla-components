package uz.yalla.components.primitive.field

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import androidx.compose.ui.tooling.preview.Preview
import uz.yalla.design.theme.System

/**
 * State for [DateField] component.
 *
 * @property date Currently selected date, or null if none.
 * @property placeholder Text shown when no date selected.
 * @property enabled When false, field is not clickable.
 * @property borderStroke Optional border around field.
 */
data class DateFieldState(
    val date: LocalDate?,
    val placeholder: String = "DD.MM.YYYY",
    val enabled: Boolean = true,
    val borderStroke: BorderStroke? = null
)

/**
 * Read-only date field that opens a date picker on click.
 *
 * Displays formatted date or placeholder when no date selected.
 *
 * ## Usage
 *
 * ```kotlin
 * var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
 * var showPicker by remember { mutableStateOf(false) }
 *
 * DateField(
 *     state = DateFieldState(
 *         date = selectedDate,
 *         placeholder = "Select date"
 *     ),
 *     onClick = { showPicker = true },
 * )
 * ```
 *
 * @param state Field state containing date, placeholder, enabled, and borderStroke.
 * @param onClick Invoked when field is clicked (open picker).
 * @param modifier Applied to field container.
 * @param colors Color configuration, defaults to [DateFieldDefaults.colors].
 * @param dimens Dimension configuration, defaults to [DateFieldDefaults.dimens].
 *
 * @see DateFieldDefaults for default values
 */
@Composable
fun DateField(
    state: DateFieldState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: DateFieldDefaults.DateFieldColors = DateFieldDefaults.colors(),
    dimens: DateFieldDefaults.DateFieldDimens = DateFieldDefaults.dimens(),
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = dimens.minHeight),
        enabled = state.enabled,
        shape = dimens.shape,
        color = colors.container,
        border = state.borderStroke,
    ) {
        Row(
            modifier = Modifier.padding(dimens.contentPadding),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.size(dimens.iconSize),
                tint = colors.icon,
            )

            Spacer(Modifier.width(dimens.iconSpacing))

            Text(
                text = state.date?.formatDisplay() ?: state.placeholder,
                style = System.font.body.base.regular,
                color = if (state.date != null) colors.text else colors.placeholder,
            )
        }
    }
}

/**
 * Formats LocalDate for display.
 */
private fun LocalDate.formatDisplay(): String =
    "${dayOfMonth.toString().padStart(2, '0')}." +
        "${monthNumber.toString().padStart(2, '0')}." +
        "$year"

/**
 * Default values for [DateField].
 *
 * Provides theme-aware defaults for [colors] and [dimens] that can be overridden.
 */
object DateFieldDefaults {

    /**
     * Color configuration for [DateField].
     *
     * @param container Background color.
     * @param text Selected date text color.
     * @param placeholder Placeholder text color.
     * @param icon Calendar icon color.
     */
    data class DateFieldColors(
        val container: Color,
        val text: Color,
        val placeholder: Color,
        val icon: Color,
    )

    @Composable
    fun colors(
        container: Color = Color.Transparent,
        text: Color = System.color.textBase,
        placeholder: Color = System.color.textSubtle,
        icon: Color = System.color.iconSecondary,
    ): DateFieldColors = DateFieldColors(
        container = container,
        text = text,
        placeholder = placeholder,
        icon = icon,
    )

    /**
     * Dimension configuration for [DateField].
     *
     * @param minHeight Minimum height of the field.
     * @param shape Corner shape of the field.
     * @param iconSize Size of the calendar icon.
     * @param iconSpacing Spacing between icon and text.
     * @param contentPadding Padding inside the field.
     */
    data class DateFieldDimens(
        val minHeight: Dp,
        val shape: Shape,
        val iconSize: Dp,
        val iconSpacing: Dp,
        val contentPadding: PaddingValues,
    )

    @Composable
    fun dimens(
        minHeight: Dp = 56.dp,
        shape: Shape = RoundedCornerShape(12.dp),
        iconSize: Dp = 24.dp,
        iconSpacing: Dp = 12.dp,
        contentPadding: PaddingValues = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp,
        ),
    ): DateFieldDimens = DateFieldDimens(
        minHeight = minHeight,
        shape = shape,
        iconSize = iconSize,
        iconSpacing = iconSpacing,
        contentPadding = contentPadding,
    )
}

@Preview
@Composable
private fun DateFieldPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        DateField(
            state = DateFieldState(date = null),
            onClick = {},
        )
    }
}
