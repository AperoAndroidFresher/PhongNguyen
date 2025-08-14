package com.hoaiphong.composeui.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.log_in),
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00BCD4), contentColor = Color.Companion.White
        ),
        shape = CircleShape
    ) {
        Text(text = text)
    }
}
