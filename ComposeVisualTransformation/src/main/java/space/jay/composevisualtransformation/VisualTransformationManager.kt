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

            fun setDigitFormat(format : List<Int>) = apply { digitFormat = format }

            fun setSeparator(separator : String) = apply { this.separator = separator }

            fun build() = VisualTransformationManager(
                visualTransformation = TransformationCustom(
                    digitFormat = digitFormat,
                    separator = separator
                )
            ).visualTransformation
        }

        class Number() {

            fun build() = VisualTransformationManager(
                visualTransformation = TransformationNumber()
            ).visualTransformation
        }
    }

}