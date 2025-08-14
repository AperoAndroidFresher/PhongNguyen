package com.hoaiphong.composeui.ui.library.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R

@Composable
fun NoInternetConnectionContent(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_no_internet),
            contentDescription = null,
            tint = Color.Companion.White,
            modifier = Modifier.Companion.size(120.dp)
        )
        Spacer(modifier = Modifier.Companion.height(16.dp))
        Text(
            stringResource(R.string.no_internet_connection_please_check_your_connection_again),
            textAlign = TextAlign.Companion.Center,
            color = Color.Companion.White,
        )
        Spacer(modifier = Modifier.Companion.height(16.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C2CB))
        ) {
            Text(stringResource(R.string.try_again))
        }
    }
}
