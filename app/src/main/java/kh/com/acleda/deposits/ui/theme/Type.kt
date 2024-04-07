package kh.com.acleda.deposits.ui.theme

import android.os.LocaleList
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kh.com.acleda.deposits.R
import java.util.Locale

val currentLanguage: Locale = LocaleList.getDefault().get(0)

fun getFontFamilyLocal(locale: Locale): FontFamily {
    return when (locale.language) {
        "en" -> SFProText
        "km" -> NotoSerifKhmer
        else -> SFProText // default
    }
}

private val SFProText = FontFamily(
    Font(R.font.sf_pro_text_light, FontWeight.Light),
    Font(R.font.sf_pro_text_regular, FontWeight.Normal),
    Font(R.font.sf_pro_text_medium, FontWeight.Medium),
    Font(R.font.sf_pro_text_semibold, FontWeight.SemiBold),
    Font(R.font.sf_pro_text_bold, FontWeight.Bold),
    Font(R.font.sf_pro_text_heavy, FontWeight.Black)
)

private val NotoSerifKhmer = FontFamily(
    Font(R.font.noto_serif_khmer_thin, FontWeight.Thin),
    Font(R.font.noto_serif_khmer_extra_light, FontWeight.ExtraLight),
    Font(R.font.noto_serif_khmer_light, FontWeight.Light),
    Font(R.font.noto_serif_khmer_regular, FontWeight.Normal),
    Font(R.font.noto_serif_khmer_medium, FontWeight.Medium),
    Font(R.font.noto_serif_khmer_semibold, FontWeight.SemiBold),
    Font(R.font.noto_serif_khmer_bold, FontWeight.Bold),
    Font(R.font.noto_serif_khmer_extra_bold, FontWeight.ExtraBold),
    Font(R.font.noto_serif_khmer_black, FontWeight.Black)
)

val Typography = Typography(
    /*displayLarge = TextStyle(),
    displayMedium = TextStyle(),
    displaySmall = TextStyle(),*/
    headlineLarge = TextStyle(
        fontFamily = getFontFamilyLocal(currentLanguage),
        fontSize = 32.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 40.sp,
        letterSpacing = (1).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = getFontFamilyLocal(currentLanguage),
        fontSize = 28.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 36.sp,
        letterSpacing = (1).sp
    ),
    headlineSmall = TextStyle(
        fontFamily = getFontFamilyLocal(currentLanguage),
        fontSize = 24.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 32.sp,
        letterSpacing = (1).sp
    ),
    titleLarge = TextStyle(
        fontFamily = getFontFamilyLocal(currentLanguage),
        fontSize = 22.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 28.sp,
        letterSpacing = (1).sp
    ),
    titleMedium = TextStyle(
        fontFamily = getFontFamilyLocal(currentLanguage),
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp,
        letterSpacing = (1).sp
    ),
    titleSmall = TextStyle(
        fontFamily = getFontFamilyLocal(currentLanguage),
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = (1).sp
    ),
    bodyLarge = TextStyle(
        fontFamily = getFontFamilyLocal(currentLanguage),
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        letterSpacing = (1).sp
    ),
    bodyMedium = TextStyle(
        fontFamily = getFontFamilyLocal(currentLanguage),
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        letterSpacing = (1).sp
    ),
    bodySmall = TextStyle(
        fontFamily = getFontFamilyLocal(currentLanguage),
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        letterSpacing = (1).sp
    ),
    labelLarge = TextStyle(
        fontFamily = getFontFamilyLocal(currentLanguage),
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = (1).sp
    ),
    labelMedium = TextStyle(
        fontFamily = getFontFamilyLocal(currentLanguage),
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        letterSpacing = (1).sp
    ),
    labelSmall = TextStyle(
        fontFamily = getFontFamilyLocal(currentLanguage),
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        letterSpacing = (1).sp
    ),
)

// Set of Material typography styles to start with
/*val Typography = Typography(bodyLarge = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp)*/   /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    )*/