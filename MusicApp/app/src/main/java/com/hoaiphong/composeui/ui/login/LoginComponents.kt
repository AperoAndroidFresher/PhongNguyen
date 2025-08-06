package com.hoaiphong.composeui.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R


@Composable
fun LoginButton(
    text: String = "Log in", modifier: Modifier = Modifier, onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00BCD4), contentColor = Color.White
        ),
        shape = CircleShape
    ) {
        Text(text = text)
    }
}


@Composable
fun TextInput(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    leadingIcon: ImageVector = Icons.Default.Person,
    placeholderText: String = "Username",
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholderText, color = Color.Gray) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon, contentDescription = null, tint = Color.Gray
            )
        },
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.Cyan,
            focusedLeadingIconColor = Color.Gray,
            unfocusedLeadingIconColor = Color.Gray,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            disabledTextColor = Color.LightGray,
            disabledBorderColor = Color.DarkGray
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(12.dp))
    )
}

@Composable
fun PasswordInput(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    labelText: String = "Password"
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(labelText, color = Color.Gray) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Password Icon",
                tint = Color.Gray
            )
        },
        trailingIcon = {
            val iconResId = if (passwordVisible) R.drawable.ic_visible
            else R.drawable.ic_enable

            IconButton(onClick = onPasswordVisibilityChange) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = Color.Gray
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.Cyan,
            focusedLeadingIconColor = Color.Gray,
            unfocusedLeadingIconColor = Color.Gray,
            focusedTrailingIconColor = Color.Gray,
            unfocusedTrailingIconColor = Color.Gray,
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(12.dp))
    )
}

@Composable
fun RememberMeCheckbox(
    checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(20.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF00BCD4),
                checkmarkColor = Color.Black,
                uncheckedColor = Color(0xFF00BCD4)
            )
        )
        Text(
            text = "Remember me", color = Color.White, style = MaterialTheme.typography.bodySmall
        )
    }
}
