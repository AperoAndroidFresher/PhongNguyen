package com.hoaiphong.composeui.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R

@Composable
fun PasswordInput(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    labelText: String = stringResource(R.string.password),
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(labelText, color = Color.Companion.Gray) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Password Icon",
                tint = Color.Companion.Gray
            )
        },
        trailingIcon = {
            val iconResId = if (passwordVisible) R.drawable.ic_visible
            else R.drawable.ic_enable

            IconButton(onClick = onPasswordVisibilityChange) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = Color.Companion.Gray
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.Companion.None else PasswordVisualTransformation(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Companion.Gray,
            unfocusedBorderColor = Color.Companion.Gray,
            cursorColor = Color.Companion.Cyan,
            focusedLeadingIconColor = Color.Companion.Gray,
            unfocusedLeadingIconColor = Color.Companion.Gray,
            focusedTrailingIconColor = Color.Companion.Gray,
            unfocusedTrailingIconColor = Color.Companion.Gray,
            unfocusedTextColor = Color.Companion.White,
            focusedTextColor = Color.Companion.White,
            focusedLabelColor = Color.Companion.Gray,
            unfocusedLabelColor = Color.Companion.Gray
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
