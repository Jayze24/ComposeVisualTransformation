package space.jay.composevisualtransformation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TestComposeOnValueChange {

    @Test
    fun numberFilter_string() {
        listOf(
            "" to "",
            "0" to "0",
            "123456789" to "123456789",
            "abcd1234efg567!@#$%^&890" to "1234567890",
            "mnbx" to "",
            "0789" to "789",
            "123456789123456789123456789123456789" to "123456789123456789123456789123456789"
        ).forEach { fake ->
            ComposeOnValueChange.numberFilter(fake.first) {
                assertThat(it).isEqualTo(fake.second)
            }
        }
    }

    @Test
    fun numberFilter_string_with_maxLength() {
        listOf(
            "123456789123456789123456789123456789" to "12345678912345678912",
            "abcdefghijklmnopqrstuvwxyz123456789012345678901234567890" to "12345678901234567890"
        ).forEach { fake ->
            ComposeOnValueChange.numberFilter(fake.first, 20) {
                assertThat(it).isEqualTo(fake.second)
            }
        }
    }

    @Test
    fun numberFilter_textFieldValue() {
        listOf(
            TextFieldValue("", selection = TextRange(0)) to TextFieldValue("", selection = TextRange(0)),
            TextFieldValue("0", selection = TextRange(1)) to TextFieldValue("0", selection = TextRange(1)),
            TextFieldValue("123456789", selection = TextRange(5)) to TextFieldValue("123456789", selection = TextRange(5)),
            TextFieldValue("abcd1234efg567!@#$%^&890", selection = TextRange(23)) to TextFieldValue("1234567890", selection = TextRange(9)),
            TextFieldValue("abcd1234efg567!@#$%^&890", selection = TextRange(16)) to TextFieldValue("1234567890", selection = TextRange(7)),
            TextFieldValue("abcd1234efg567!@#$%^&890", selection = TextRange(17)) to TextFieldValue("1234567890", selection = TextRange(7)),
            TextFieldValue("mnbx", selection = TextRange(4)) to TextFieldValue("", selection = TextRange(0)),
            TextFieldValue("0789", selection = TextRange(4)) to TextFieldValue("789", selection = TextRange(3)),
            TextFieldValue("0789", selection = TextRange(1)) to TextFieldValue("789", selection = TextRange(0)),
            TextFieldValue("123456789123456789123456789123456789", selection = TextRange(25)) to TextFieldValue("123456789123456789123456789123456789", selection = TextRange(25))
        ).forEach { fake ->
            ComposeOnValueChange.numberFilter(fake.first) {
                assertThat(it.text).isEqualTo(fake.second.text)
                assertThat(it.selection).isEqualTo(fake.second.selection)
            }
        }
    }

    @Test
    fun numberFilter_textFieldValue_with_maxLength() {
        listOf(
            TextFieldValue("123456789123456789123456789123456789", selection = TextRange(25)) to TextFieldValue("12345678912345678912", selection = TextRange(20)),
            TextFieldValue("abc123456789123456789123456789123456789", selection = TextRange(20)) to TextFieldValue("12345678912345678912", selection = TextRange(17))
        ).forEach { fake ->
            ComposeOnValueChange.numberFilter(fake.first, 20, false) {
                assertThat(it.text).isEqualTo(fake.second.text)
                assertThat(it.selection).isEqualTo(fake.second.selection)
            }
        }
    }

    @Test
    fun numberFilter_textFieldValue_with_zero() {
        listOf(
            TextFieldValue("", selection = TextRange(0)) to TextFieldValue("0", selection = TextRange(1)),
            TextFieldValue("0", selection = TextRange(0)) to TextFieldValue("0", selection = TextRange(0)),
        ).forEach { fake ->
            ComposeOnValueChange.numberFilter(fake.first, isEmptyStringToZero = true) {
                assertThat(it.text).isEqualTo(fake.second.text)
                assertThat(it.selection).isEqualTo(fake.second.selection)
            }
        }
    }
}