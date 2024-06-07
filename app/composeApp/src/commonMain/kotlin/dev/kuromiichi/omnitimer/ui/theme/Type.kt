package dev.kuromiichi.omnitimer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import omnitimer.composeapp.generated.resources.Res
import omnitimer.composeapp.generated.resources.rubik_black
import omnitimer.composeapp.generated.resources.rubik_blackitalic
import omnitimer.composeapp.generated.resources.rubik_bold
import omnitimer.composeapp.generated.resources.rubik_bolditalic
import omnitimer.composeapp.generated.resources.rubik_extrabold
import omnitimer.composeapp.generated.resources.rubik_extrabolditalic
import omnitimer.composeapp.generated.resources.rubik_italic
import omnitimer.composeapp.generated.resources.rubik_light
import omnitimer.composeapp.generated.resources.rubik_lightitalic
import omnitimer.composeapp.generated.resources.rubik_medium
import omnitimer.composeapp.generated.resources.rubik_mediumitalic
import omnitimer.composeapp.generated.resources.rubik_regular
import omnitimer.composeapp.generated.resources.rubik_semibold
import omnitimer.composeapp.generated.resources.rubik_semibolditalic
import org.jetbrains.compose.resources.Font

@Composable
fun rubikFontFamily() = FontFamily(
    Font(Res.font.rubik_light, FontWeight.Light, FontStyle.Normal),
    Font(Res.font.rubik_regular, FontWeight.Normal, FontStyle.Normal),
    Font(Res.font.rubik_medium, FontWeight.Medium, FontStyle.Normal),
    Font(Res.font.rubik_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(Res.font.rubik_bold, FontWeight.Bold, FontStyle.Normal),
    Font(Res.font.rubik_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(Res.font.rubik_black, FontWeight.Black, FontStyle.Normal),
    Font(Res.font.rubik_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.rubik_italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.rubik_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.rubik_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(Res.font.rubik_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.rubik_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(Res.font.rubik_blackitalic, FontWeight.Black, FontStyle.Italic)
)

@Composable
fun rubikTypography() = Typography().run {
    val fontFamily = rubikFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily =  fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}
