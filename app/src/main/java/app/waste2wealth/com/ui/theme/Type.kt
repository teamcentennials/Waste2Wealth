package app.waste2wealth.com.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.waste2wealth.com.R

// Set of Material typography styles to start with

val monteNormal = FontFamily(
    Font(R.font.monteserrat)
)
val monteExtraBold = FontFamily(
    Font(R.font.monteeb)
)
val monteBold = FontFamily(
    Font(R.font.monteeb)
)

val monteSB = FontFamily(
    Font(R.font.montesb)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)