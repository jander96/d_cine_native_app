package com.devj.dcine.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.devj.dcine.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.outfit_black, weight = FontWeight.Black),
            Font(R.font.outfit_bold, weight = FontWeight.Bold),
            Font(R.font.outfit_extra_bold, weight = FontWeight.ExtraBold),
            Font(R.font.outfit_extra_light, weight = FontWeight.ExtraLight),
            Font(R.font.outfit_light, weight = FontWeight.Light),
            Font(R.font.outfit_medium, weight = FontWeight.Medium),
            Font(R.font.outfit_regular, weight = FontWeight.W400),
            Font(R.font.outfit_semi_bold, weight = FontWeight.SemiBold),
            Font(R.font.outfit_thin, weight = FontWeight.Thin),
        ),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

