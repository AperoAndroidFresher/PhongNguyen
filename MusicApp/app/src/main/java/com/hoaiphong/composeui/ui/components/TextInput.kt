package com.hoaiphong.composeui.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R

@Composable
fun TextInput(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    leadingIcon: ImageVector = Icons.Default.Person,
    placeholderText: String = stringResource(R.string.username),
    visualTransformation: VisualTransformation = VisualTransformation.Companion.None,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholderText, color = Color.Companion.Gray) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon, contentDescription = null, tint = Color.Companion.Gray
            )
        },
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Companion.Gray,
            unfocusedBorderColor = Color.Companion.Gray,
            cursorColor = Color.Companion.Cyan,
            focusedLeadingIconColor = Color.Companion.Gray,
            unfocusedLeadingIconColor = Color.Companion.Gray,
            focusedPlaceholderColor = Color.Companion.Gray,
            unfocusedPlaceholderColor = Color.Companion.Gray,
            unfocusedTextColor = Color.Companion.White,
            focusedTextColor = Color.Companion.White,
            disabledTextColor = Color.Companion.LightGray,
            disabledBorderColor = Color.Companion.DarkGray
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(56.dp)
            .background(
                Color(0xFF1A1A1A),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )
    )
}
