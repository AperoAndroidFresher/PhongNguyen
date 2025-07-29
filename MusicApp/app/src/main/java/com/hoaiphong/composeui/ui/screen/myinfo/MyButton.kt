package com.hoaiphong.composeui.ui.screen.myinfo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, name = "Button")
@Composable
fun MyButton(
    text: String = "Submit",
    modifier: Modifier = Modifier.Companion,
    onClick: () -> Unit = {}

) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(150.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceTint),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.surfaceTint)
    ) {
        Text(text, color = MaterialTheme.colorScheme.onSecondary)
    }
}