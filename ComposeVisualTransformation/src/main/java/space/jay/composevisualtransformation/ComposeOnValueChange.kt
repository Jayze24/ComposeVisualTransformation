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
        isChangingEmptyStringToZero : Boolean = false,
        onValueChange : (TextFieldValue) -> Unit
    ) {
        var selectionCount = 0
        val text = value.text
            .filter { c ->
                if (c.isDigit()) {
                    true
                } else {
                    selectionCount++
                    false
                }
            }
            .take(maxLength)
        onValueChange(
            if (text.isEmpty()) {
                if (isChangingEmptyStringToZero) {
                    TextFieldValue(
                        text = "0",
                        selection = TextRange(1)
                    )
                } else {
                    TextFieldValue("")
                }
            } else {
                TextFieldValue(
                    text = BigInteger(text).toString(),
                    selection = TextRange((value.selection.start - selectionCount).coerceAtLeast(0))
                )
            }
        )
    }
}