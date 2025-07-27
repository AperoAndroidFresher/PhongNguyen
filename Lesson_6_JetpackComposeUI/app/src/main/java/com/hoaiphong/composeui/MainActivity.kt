package com.hoaiphong.composeui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.hoaiphong.composeui.ui.navigation.AppNavGraph
import com.hoaiphong.composeui.ui.screen.login.LoginScreen
import com.hoaiphong.composeui.ui.screen.login.SignUpScreen
import com.hoaiphong.composeui.ui.screen.myinfo.MyInformation
import com.hoaiphong.composeui.ui.screen.mysong.PlaylistSong

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}

