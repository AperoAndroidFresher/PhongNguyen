package com.hoaiphong.composeui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.hoaiphong.composeui.ui.theme.AppTheme
import com.hoaiphong.composeui.ui.navigation.AppNavGraph
import com.hoaiphong.composeui.utils.LanguageManager

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        LanguageManager.applySavedLocale(this)
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppNavGraph()
            }
        }
    }
}
