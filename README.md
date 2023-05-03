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
There are two build types. Custom builds can create any format they want by setting the digitFormat and separator. For numeric builds, a comma is added for every thousand.   
Custom build example) Create a build after entering " - " in the separator with digitFormat "lisOf(2,1,5)" in the build. Then when I type "12345678901" into the TextField, the result is "12 - 3 - 45678 - 90 - 1".    
#### Example of number format TextField
```kotlin
TextField(
    ....
    value = value,
    onValueChange = { v ->
        val text = v.filter { c -> c.isDigit() }
        value = if (text.isEmpty()) "" else BigInteger(text).toString()
    },
    visualTransformation = ComposeVisualTransformation.Builder.Number().build()
)
```
#### Example of KOREAN phone number format TextField
```kotlin
TextField(
    ....
    value = value,
    onValueChange = { value = it.take(11) },
    visualTransformation = ComposeVisualTransformation.Builder
        .Custom()
        .setDigitFormat(listOf(3, 4, 4))
        .setSeparator("-")
        .build()
)
```

