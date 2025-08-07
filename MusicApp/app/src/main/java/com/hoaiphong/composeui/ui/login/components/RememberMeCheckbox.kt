package com.hoaiphong.composeui.ui.login.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RememberMeCheckbox(
    checked: Boolean, 
    onCheckedChange: (Boolean) -> Unit, 
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.Companion.CenterVertically, modifier = modifier,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.Companion
                .padding(end = 8.dp)
                .size(20.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF00BCD4),
                checkmarkColor = Color.Companion.Black,
                uncheckedColor = Color(0xFF00BCD4),
            ),
        )
        Text(
            text = "Remember me",
            color = Color.Companion.White,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
