package com.hoaiphong.composeui.ui.screen.myinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SuccessPopupContent(modifier: Modifier = Modifier.Companion) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .background(Color.Companion.White, shape = RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.Companion.align(Alignment.Companion.Center),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Success",
                tint = Color(0xFF25AE88),
                modifier = Modifier.Companion.size(100.dp)
            )
            Spacer(modifier = Modifier.Companion.height(16.dp))
            Text(
                text = "Success!",
                modifier = Modifier.Companion.fillMaxWidth(),
                textAlign = TextAlign.Companion.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Companion.SemiBold,
                    fontSize = 36.sp,
                    lineHeight = 36.sp,
                    letterSpacing = (36.sp * 0.055f),
                    color = Color(0xFF25AE88)
                )
            )
            Spacer(modifier = Modifier.Companion.height(16.dp))
            Text(
                text = "Your information has\nbeen updated!",
                textAlign = TextAlign.Companion.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Companion.Normal,
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    letterSpacing = (20.sp * 0.055f),
                    color = Color.Companion.Black
                )
            )
        }
    }
}