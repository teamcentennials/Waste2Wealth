package app.waste2wealth.com.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val backGround = Color(0xFF21c998)

val isDarkThemeEnabled: Boolean
    @Composable
    get() = isSystemInDarkTheme()

val appBackground: Color
    @Composable
    get() = if (isDarkThemeEnabled) Color(0xFFFFFFFF) else Color(0xFFFFFFFF)

val CardColor: Color
    @Composable
    get() = if (isDarkThemeEnabled) Color(0xFFCFDCFE) else Color(0xFFCFDCFE)

val textColor: Color
    @Composable
    get() = if (isDarkThemeEnabled) Color(0xFF3549A5) else Color(0xFF3549A5)