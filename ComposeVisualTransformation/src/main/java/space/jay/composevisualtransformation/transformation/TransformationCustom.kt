package space.jay.composevisualtransformation.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

internal class TransformationCustom(
    private var digitFormat : List<Int>,
    private var separator : String,
    private var maxLength : Int = Int.MAX_VALUE
) : VisualTransformation {

    private val listTransformedSize = digitFormat.map { it + separator.length }

    override fun filter(text : AnnotatedString) : TransformedText {
        val originalInputText = text.text.take(maxLength)
        val builderOutText = StringBuilder()
        var indexStringStart = 0
        loop@ while (true) {
            for (digit in digitFormat) {
                val indexStringEnd = (indexStringStart + digit).coerceAtMost(originalInputText.length)
                builderOutText.append(originalInputText.subSequence(indexStringStart, indexStringEnd))
                if (indexStringEnd == originalInputText.length) {
                    break@loop
                } else {
                    builderOutText.append(separator)
                    indexStringStart = indexStringEnd
                }
            }
        }
        val result = builderOutText.toString()
        return TransformedText(
            AnnotatedString(result),
            object : OffsetMapping {
                override fun originalToTransformed(offset : Int) : Int {
                    var separatorCount = 0
                    var sumOfDigit = 0
                    loop@ while (true) {
                        for (formatSize in digitFormat) {
                            sumOfDigit += formatSize
                            when {
                                offset <= sumOfDigit -> break@loop
                                else -> separatorCount += separator.length
                            }
                        }
                    }

                    return offset + separatorCount
                }

                override fun transformedToOriginal(offset : Int) : Int {
                    var separatorCount = 0
                    var sumOfDigit = 0
                    loop@ while (true) {
                        for (transformedSize in listTransformedSize) {
                            sumOfDigit += transformedSize
                            when {
                                offset < sumOfDigit -> break@loop
                                else -> separatorCount += separator.length
                            }
                        }
                    }
                    return offset - separatorCount
                }
            }
        )
    }
}