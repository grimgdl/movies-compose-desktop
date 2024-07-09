package ui

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val CustomPrimary = Color(0xff1b66f1)
val CustomPrimaryVariant = Color(0xFF3700B3)
val CustomSecondary = Color(0xFF03DAC6)
val CustomSecondaryVariant = Color(0xFF018786)
val CustomBackground = Color(0xFF121212)
val CustomSurface = Color(0xFF121212)
val CustomError = Color(0xFFCF6679)
val CustomOnPrimary = Color.White
val CustomOnSecondary = Color.Black
val CustomOnBackground = Color.White
val CustomOnSurface = Color.White
val CustomOnError = Color.Black





private val LightColorPalette = lightColors()


private val DarkColorPalette = darkColors(
    primary = CustomPrimary,
    primaryVariant = CustomPrimaryVariant,
    secondary = CustomSecondary,
    secondaryVariant = CustomSecondaryVariant,
    background = CustomBackground,
    surface = CustomSurface,
    error = CustomError,
    onPrimary = CustomOnPrimary,
    onSecondary = CustomOnSecondary,
    onBackground = CustomOnBackground,
    onSurface = CustomOnSurface,
    onError = CustomOnError
)




@Composable
fun CustomTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {


    val colors: Colors = if (darkTheme) {
        DarkColorPalette
    }else {
        LightColorPalette
    }


    MaterialTheme(
        colors = colors,
        content = content
    )

}

