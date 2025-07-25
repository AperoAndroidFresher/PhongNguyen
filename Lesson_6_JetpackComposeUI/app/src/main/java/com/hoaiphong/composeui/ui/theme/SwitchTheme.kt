package com.hoaiphong.composeui.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.ui.unit.dp
import com.example.compose.darkScheme
import com.example.compose.lightScheme
import com.example.ui.theme.AppTypography

data class ThemeData(
    val type: ThemeType,
    val color: ColorScheme,
    var typography: Typography,
    val shapes: Shapes
)

enum class ThemeType{
    LightMode,DarkMode
}

var lightMode = ThemeData(
    type = ThemeType.LightMode,
    color = lightScheme,
    typography = AppTypography,
    shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(10.dp)
    )
)

var darkMode = ThemeData(
    type = ThemeType.DarkMode,
    color = darkScheme,
    typography = AppTypography,
    shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(10.dp)
    )
)