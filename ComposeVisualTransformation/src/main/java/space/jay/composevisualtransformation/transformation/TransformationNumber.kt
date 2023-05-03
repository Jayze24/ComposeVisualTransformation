package space.jay.composevisualtransformation.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.math.BigInteger
import java.text.NumberFormat

internal class TransformationNumber : VisualTransformation {

    override fun filter(text : AnnotatedString) : TransformedText {
        val result = if (text.text.isEmpty()) "" else NumberFormat.getNumberInstance().format(BigInteger(text.text))
        return TransformedText(
            AnnotatedString(result),
            object : OffsetMapping {
                override fun originalToTransformed(offset : Int) : Int {
                    val commaCountAll = result.length / 4
                    val offsetBehindCount = text.text.length - offset
                    val commaCountBehindCorrectionOffset = offsetBehindCount / 3
                    val commaCountFrontCorrectionOffset = (commaCountAll - commaCountBehindCorrectionOffset).coerceAtLeast(0)
                    val sum = offset + commaCountFrontCorrectionOffset
                    return if (sum > 0 && result[sum - 1] == ',') sum + 1 else sum
                }

                override fun transformedToOriginal(offset : Int) : Int {
                    return if (offset > 0) {
                        val correctionOffset = if (result[offset - 1] == ',') offset + 1 else offset
                        val commaCountAll = result.length / 4
                        val commaCountBehindCorrectionOffset = (result.length - correctionOffset) / 4
                        val commaCountFrontCorrectionOffset = commaCountAll - commaCountBehindCorrectionOffset
                        return correctionOffset - commaCountFrontCorrectionOffset
                    } else {
                        offset
                    }
                }
            }
        )
    }
}