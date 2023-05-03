package space.jay.composevisualtransformation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.VisualTransformation
import space.jay.composevisualtransformation.transformation.TransformationCustom
import space.jay.composevisualtransformation.transformation.TransformationNumber

@Immutable
class ComposeVisualTransformation private constructor(
    val visualTransformation : VisualTransformation
) {

    class Builder() {

        class Custom() {
            private var digitFormat : List<Int> = listOf(4)
            private var separator : String = " - "

            fun setDigitFormat(format : List<Int>) = apply { digitFormat = format }

            fun setSeparator(separator : String) = apply { this.separator = separator }

            @Stable
            fun build() = ComposeVisualTransformation(
                visualTransformation = TransformationCustom(
                    digitFormat = digitFormat,
                    separator = separator
                )
            ).visualTransformation
        }

        class Number() {

            @Stable
            fun build() = ComposeVisualTransformation(
                visualTransformation = TransformationNumber()
            ).visualTransformation
        }
    }

}