package com.hoaiphong.composeui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.compose.AppTheme
import com.hoaiphong.composeui.ui.screen.myinfo.MyInformation
import com.hoaiphong.composeui.ui.screen.mysong.PlaylistSong

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MyInformation()
            }
        }
    }
}

