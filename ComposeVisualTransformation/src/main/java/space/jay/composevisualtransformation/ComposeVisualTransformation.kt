package space.jay.composevisualtransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class ComposeVisualTransformation(
    private val digitFormat : List<Int> = listOf(4),
    private val appendString : String = " - ",
    private val isReversed : Boolean = false,
    private val maxLength : Int = Int.MAX_VALUE
) : VisualTransformation {

    private val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset : Int) : Int {
            var padding = 0
            var sumOfDigit = 0
            loop@ while (true) {
                for (it in digitFormat) {
                    sumOfDigit += it
                    if (offset <= sumOfDigit || sumOfDigit >= maxLength) {
                        break@loop
                    } else {
                        padding += appendString.length
                    }
                }
            }

            return if (offset >= maxLength) {
                maxLength + padding
            } else {
                offset + padding
            }
        }

        override fun transformedToOriginal(offset : Int) : Int {
            var searching = true
            var result = 0
            var sumOfDigit = 0
            while (searching) {
                digitFormat.forEach {
                    sumOfDigit += it + appendString.length
                    if (offset < sumOfDigit) {
                        searching = false
                    } else {
                        result += appendString.length
                    }
                }
            }
            return offset - result
        }
    }

    override fun filter(text : AnnotatedString) : TransformedText {
        var value = if (text.text.length > maxLength) text.text.substring(0 until maxLength) else text.text
        val out = StringBuilder()
        var indexDigit = 0
        var count = 0
        if (isReversed) {
            value = value.reversed()
        }
        value.forEach { c ->
            if (count == digitFormat[indexDigit]) {
                out.append(appendString)
                indexDigit = if (digitFormat.size - 1 > indexDigit) {
                    indexDigit + 1
                } else {
                    0
                }
                count = 0
            }
            count++
            out.append(c)
        }
        val valueFormatted = if (isReversed) {
            out.toString().reversed()
        } else {
            out.toString()
        }

        return TransformedText(AnnotatedString(valueFormatted), creditCardOffsetTranslator)
    }
}