package space.jay.sample.composevisualtransformation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import space.jay.composevisualtransformation.ComposeOnValueChange
import space.jay.composevisualtransformation.ComposeVisualTransformation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                PhoneNumber()
                Spacer(modifier = Modifier.height(8.dp))
                Price()
                Spacer(modifier = Modifier.height(8.dp))
                PriceWithChangeEmptyString()
                Spacer(modifier = Modifier.height(8.dp))
                Card()
            }
        }
    }
}

@Composable
fun PhoneNumber() {
    var value by remember { mutableStateOf("") }
    TextField(
        label = { Text(text = "KOREAN PHONE NUMBER FORMAT", color = Color.Gray) },
        placeholder = { Text(text = "ex) xxx-xxxx-xxxx", color = Color.Gray) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        value = value,
        onValueChange = { value = it.take(11) },
        visualTransformation = ComposeVisualTransformation.Builder
            .Custom()
            .setDigitFormat(listOf(3, 4, 4))
            .setSeparator("-")
            .build()
    )
}

@Composable
fun Price() {
    var value by remember { mutableStateOf("") }
    TextField(
        label = { Text(text = "NUMBER FORMAT", color = Color.Gray) },
        placeholder = { Text(text = "ex) x,xxx", color = Color.Gray) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        value = value,
        onValueChange = { ComposeOnValueChange.numberFilter(it, 15) { v -> value = v } },
        visualTransformation = ComposeVisualTransformation.Builder
            .Number()
            .build()
    )
}

@Composable
fun PriceWithChangeEmptyString() {
    var value by remember { mutableStateOf(TextFieldValue("0")) }
    TextField(
        label = { Text(text = "NUMBER WITH ZERO FORMAT", color = Color.Gray) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        value = value,
        onValueChange = { ComposeOnValueChange.numberFilter(it, 15, true) { v -> value = v } },
        visualTransformation = ComposeVisualTransformation.Builder
            .Number()
            .build()
    )
}

@Composable
fun Card() {
    var value by remember { mutableStateOf("") }
    TextField(
        label = { Text(text = "CARD NUMBER FORMAT", color = Color.Gray) },
        placeholder = { Text(text = "ex) xxxx - xxxx - xxxx - xxxx", color = Color.Gray) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        value = value,
        onValueChange = { value = it.take(16) },
        visualTransformation = ComposeVisualTransformation.Builder
            .Custom()
            .setDigitFormat(listOf(4))
            .build()
    )
}