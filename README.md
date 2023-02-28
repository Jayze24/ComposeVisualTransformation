# ComposeVisualTransformation
[![License](https://img.shields.io/badge/License-Apache_2.0-orange.svg)](https://opensource.org/licenses/Apache-2.0)
[![API](https://img.shields.io/badge/API-23%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=23)
[![](https://jitpack.io/v/Jayze24/ComposeVisualTransformation.svg)](https://jitpack.io/#Jayze24/ComposeVisualTransformation)

### Using this you can easily define a format in Compose's TextField.


## Dependency
Add it in your project `build.gradle` 
```gradle
allprojects {
    repositories {
        ....
        maven { url 'https://jitpack.io' }
    }
}
```
Add it in your module `build.gradle`    
[![](https://jitpack.io/v/Jayze24/ComposeVisualTransformation.svg)](https://jitpack.io/#Jayze24/ComposeVisualTransformation)
```gradle
dependencies {
    implementation 'com.github.Jayze24:ComposeVisualTransformation:x.y.z'
}
```
## Usage
String format rules. For example, if you enter "lisOf(3)" in digitFormat and "," in appendString, the character with "," appended to every third character is returned. If you put "lisOf(2,1,5)" in digitFormat, " - " in appendString, and put "12345678901" in TextField, the result is "12 - 3 - 45678 - 90 - 1".   
Setting isReversed to "true" will cause the counter to be applied behind the scenes.    
When the length of a character becomes larger than the size set in "maxLength", it is no longer displayed in the TextField. However, pressing backspace erases the invisible but typed character, so you need to use it with a length limit in "onValueChange" as shown in the example below.
```kotlin
ComposeVisualTransformation(digitFormat(List<Int>), appendString(String), isReversed(Boolean), maxLength(Int))
```
#### Example of price TextField
```kotlin
TextField(
    ....
    value = value,
    onValueChange = { value = it },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    visualTransformation = ComposeVisualTransformation(
        digitFormat = listOf(3),
        appendString = ",",
        isReversed = true
    )
)
```
#### Example of card TextField
```kotlin
TextField(
    ....
    value = value,
    onValueChange = { value = it.take(16) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    visualTransformation = ComposeVisualTransformation(
        digitFormat = listOf(4),
        appendString = " - "
    )
)
```

