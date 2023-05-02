package space.jay.composevisualtransformation

import androidx.compose.ui.text.input.VisualTransformation
import space.jay.composevisualtransformation.transformation.TransformationCustom
import space.jay.composevisualtransformation.transformation.TransformationNumber

class VisualTransformationManager private constructor(
    val visualTransformation : VisualTransformation
) {

    class Builder() {

        class Custom() {
            private var digitFormat : List<Int> = listOf(4)
            private var separator : String = " - "
            private var maxLength : Int = Int.MAX_VALUE

            fun setDigitFormat(format : List<Int>) = apply { digitFormat = format }

            fun setSeparator(separator : String) = apply { this.separator = separator }

            fun setMaxLength(length : Int) = apply { maxLength = length }

            fun build() = VisualTransformationManager(
                visualTransformation = TransformationCustom(
                    digitFormat = digitFormat,
                    separator = separator,
                    maxLength = maxLength
                )
            ).visualTransformation
        }

        class Number() {

            private var maxLength : Int = Int.MAX_VALUE

            fun setMaxLength(length : Int) = apply { maxLength = length }

            fun build() = VisualTransformationManager(
                visualTransformation = TransformationNumber(
                    maxLength = maxLength
                )
            ).visualTransformation
        }
    }

}