package com.example.basemvvmcleanandroid.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.basemvvmcleanandroid.R



// Set of Material typography styles to start with

val BahijSansArabic = FontFamily(
    Font(R.font.bahij_thesansarabic_plain, FontWeight.Normal),
    Font(R.font.bahij_thesansarabic_bold,  FontWeight.Bold)
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = BahijSansArabic,
        fontWeight  = FontWeight.Normal,
        fontSize    = 16.sp,
        color       = Color.Black,
        lineHeight  = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = BahijSansArabic,
        fontWeight  = FontWeight.Bold,
        fontSize    = 12.sp,
        color       = Color.DarkGray
    ),
    /* add other styles—titleLarge, headlineMedium, etc.—as you need them */
)