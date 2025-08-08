package com.hoaiphong.composeui.ui.information.components

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

@Composable
fun InformationButton(
    modifier: Modifier = Modifier,
    text: String = "Submit",
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

@Preview(showBackground = true, name = "My Button")
@Composable
fun PreviewMyButton() {
    InformationButton(text = "Submit") {}
}
