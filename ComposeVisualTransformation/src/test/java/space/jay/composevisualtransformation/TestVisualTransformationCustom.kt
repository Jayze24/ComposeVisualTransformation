package space.jay.composevisualtransformation

import androidx.compose.ui.text.AnnotatedString
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.text.NumberFormat

class TestVisualTransformationCustom {

    @Test
    fun format_change_string() {
        val custom = VisualTransformationManager.Builder.Custom().build()

        listOf(
            "" to "",
            "1" to "1",
            "1234" to "1234",
            "12345" to "1234 - 5",
            "1234567" to "1234 - 567",
            "12345678" to "1234 - 5678",
            "123456789" to "1234 - 5678 - 9",
        ).forEach { fake ->
            val formedText = custom.filter(AnnotatedString(fake.first))
            assertThat(formedText.text.text).isEqualTo(fake.second)
        }
    }

    @Test
    fun format_changed_format() {
        val custom = VisualTransformationManager.Builder
            .Custom()
            .setDigitFormat(listOf(1,2,3))
            .setSeparator("*")
            .build()

        listOf(
            "" to "",
            "1" to "1",
            "12" to "1*2",
            "123" to "1*23",
            "1234" to "1*23*4",
            "12345" to "1*23*45",
            "123456" to "1*23*456",
            "1234567" to "1*23*456*7",
            "12345678" to "1*23*456*7*8",
            "123456789" to "1*23*456*7*89",
            "1234567890" to "1*23*456*7*89*0",
        ).forEach { fake ->
            val formedText = custom.filter(AnnotatedString(fake.first))
            assertThat(formedText.text.text).isEqualTo(fake.second)
        }
    }

    @Test
    fun format_change_cursor() {
        val custom = VisualTransformationManager.Builder.Custom().build()
        val formedText = custom.filter(AnnotatedString("123456789012345"))
        assertThat(formedText.text.text).isEqualTo("1234 - 5678 - 9012 - 345")
        listOf(
            0 to 0,
            4 to 4,
            5 to 8,
            6 to 9,
            8 to 11,
            9 to 15,
            12 to 18,
            13 to 22
        ).forEach { fake ->
            val cursor = formedText.offsetMapping.originalToTransformed(fake.first)
            assertThat(cursor).isEqualTo(fake.second)
        }
    }

    @Test
    fun format_changed_format_change_cursor() {
        val custom = VisualTransformationManager.Builder
            .Custom()
            .setDigitFormat(listOf(2,5,1))
            .setSeparator(" ")
            .build()
        val formedText = custom.filter(AnnotatedString("123456789012345"))
        assertThat(formedText.text.text).isEqualTo("12 34567 8 90 12345")
        listOf(
            0 to 0,
            3 to 4,
            5 to 6,
            7 to 8,
            8 to 10,
            9 to 12,
            10 to 13,
            11 to 15,
            15 to 19
        ).forEach { fake ->
            val cursor = formedText.offsetMapping.originalToTransformed(fake.first)
            assertThat(cursor).isEqualTo(fake.second)
        }
    }

    @Test
    fun format_cursor_selection_to_original() {
        val custom = VisualTransformationManager.Builder.Custom().build()
        val formedText = custom.filter(AnnotatedString("123456789012345"))
        assertThat(formedText.text.text).isEqualTo("1234 - 5678 - 9012 - 345")
        listOf(
            0 to 0,
            4 to 4,
            5 to 5,
            8 to 5,
            9 to 6,
            11 to 8,
            14 to 8,
            15 to 9,
            24 to 15
        ).forEach { fake ->
            val cursor = formedText.offsetMapping.transformedToOriginal(fake.first)
            assertThat(cursor).isEqualTo(fake.second)
        }
    }

    @Test
    fun format_changed_format_cursor_selection_to_original() {
        val custom = VisualTransformationManager.Builder
            .Custom()
            .setDigitFormat(listOf(2,5,1))
            .setSeparator(" ")
            .build()
        val formedText = custom.filter(AnnotatedString("123456789012345"))
        assertThat(formedText.text.text).isEqualTo("12 34567 8 90 12345")
        listOf(
            0 to 0,
            4 to 3,
            5 to 4,
            8 to 7,
            10 to 8,
            12 to 9,
        ).forEach { fake ->
            val cursor = formedText.offsetMapping.transformedToOriginal(fake.first)
            assertThat(cursor).isEqualTo(fake.second)
        }
    }
}