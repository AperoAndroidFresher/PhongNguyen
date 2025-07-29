package com.hoaiphong.composeui.ui.screen.myinfo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Companion.Default,
    enabled: Boolean = true,
    modifier: Modifier = Modifier.Companion
) {
    Box(modifier = modifier.fillMaxWidth()) {
        MyText(label = label)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    placeholder,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(15.dp),
            isError = isError,
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = MaterialTheme.colorScheme.onSecondary,
                errorBorderColor = Color.Companion.Red
            )
        )
    }
}