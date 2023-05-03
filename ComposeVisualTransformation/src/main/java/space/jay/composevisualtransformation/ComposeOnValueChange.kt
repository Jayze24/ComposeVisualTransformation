package space.jay.composevisualtransformation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import java.math.BigInteger

object ComposeOnValueChange {

    inline fun numberFilter(value : String, maxLength : Int = Int.MAX_VALUE, onValueChange : (String) -> Unit) {
        val text = value.filter { c -> c.isDigit() }.take(maxLength)
        onValueChange(if (text.isEmpty()) "" else BigInteger(text).toString())
    }

    inline fun numberFilter(
        value : TextFieldValue,
        maxLength : Int = Int.MAX_VALUE,
        isEmptyStringToZero : Boolean = false,
        onValueChange : (TextFieldValue) -> Unit
    ) {
        var selectionCount = 0
        val text = value.text
            .filterIndexed { index, c ->
                if (c.isDigit()) {
                    true
                } else {
                    if (index < value.selection.start) selectionCount++
                    false
                }
            }
            .take(maxLength)
        onValueChange(
            if (text.isEmpty()) {
                if (isEmptyStringToZero) {
                    TextFieldValue(
                        text = "0",
                        selection = TextRange(1)
                    )
                } else {
                    TextFieldValue(text)
                }
            } else {
                val selection = if (text.length > 1 && text.startsWith("0")) {
                    value.selection.start - selectionCount - 1
                } else {
                    value.selection.start - selectionCount
                }
                TextFieldValue(
                    text = BigInteger(text).toString(),
                    selection = TextRange(selection.coerceAtLeast(0))
                )
            }
        )
    }
}