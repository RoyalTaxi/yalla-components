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
import androidx.compose.runtime.Immutable
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
 *     date = selectedDate,
 *     placeholder = "Select date",
 *     onClick = { showPicker = true },
 * )
 * ```
 *
 * @param date Currently selected date, or null if none.
 * @param onClick Invoked when field is clicked (open picker).
 * @param modifier Applied to field container.
 * @param placeholder Text shown when no date selected.
 * @param enabled When false, field is not clickable.
 * @param borderStroke Optional border around field.
 * @param colors Color configuration.
 *
 * @see DateFieldDefaults for default values
 */
@Composable
fun DateField(
    date: LocalDate?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "DD.MM.YYYY",
    enabled: Boolean = true,
    borderStroke: BorderStroke? = null,
    colors: DateFieldDefaults.Colors = DateFieldDefaults.colors(),
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = DateFieldDefaults.MinHeight),
        enabled = enabled,
        shape = DateFieldDefaults.Shape,
        color = colors.container,
        border = borderStroke,
    ) {
        Row(
            modifier = Modifier.padding(DateFieldDefaults.ContentPadding),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.size(DateFieldDefaults.IconSize),
                tint = colors.icon,
            )

            Spacer(Modifier.width(DateFieldDefaults.IconSpacing))

            Text(
                text = date?.formatDisplay() ?: placeholder,
                style = System.font.body.base.regular,
                color = if (date != null) colors.text else colors.placeholder,
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
 */
object DateFieldDefaults {

    val MinHeight: Dp = 56.dp
    val CornerRadius: Dp = 12.dp
    val Shape: Shape = RoundedCornerShape(CornerRadius)
    val IconSize: Dp = 24.dp
    val IconSpacing: Dp = 12.dp
    val ContentPadding: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 16.dp,
    )

    /**
     * Color configuration for [DateField].
     */
    @Immutable
    data class Colors(
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
    ): Colors = Colors(
        container = container,
        text = text,
        placeholder = placeholder,
        icon = icon,
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
            date = null,
            onClick = {},
        )
    }
}
