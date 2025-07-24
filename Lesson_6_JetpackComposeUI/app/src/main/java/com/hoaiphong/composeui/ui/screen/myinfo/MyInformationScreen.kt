package com.hoaiphong.composeui.ui.screen.myinfo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hoaiphong.composeui.R
import kotlinx.coroutines.delay

@Preview(showBackground = true, name = "Form")
@Composable
fun MyInformation(modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current

    var name by rememberSaveable { mutableStateOf("") }
    var phone  by rememberSaveable { mutableStateOf("") }
    var university  by rememberSaveable { mutableStateOf("") }
    var description  by rememberSaveable { mutableStateOf("") }
    var isEditing  by rememberSaveable { mutableStateOf(false) }
    var showPopup by rememberSaveable { mutableStateOf(false) }

    // Regex
    val nameRegex = Regex("^[a-zA-ZÀ-ỹ\\s]*$")
    val phoneRegex = Regex("^\\d{0,15}$")
    var isNameValid by remember { mutableStateOf(true) }
    var isPhoneValid by remember { mutableStateOf(true) }
    var isUniversityValid by remember { mutableStateOf(true) }

    LaunchedEffect(showPopup) {
        if (showPopup) {
            delay(2000)
            showPopup = false
        }
    }

    Box(
        modifier = modifier
            .background(Color(0xFFF5FAFC))
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "MY INFORMATION",
                    fontSize = 25.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
                if (!isEditing) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Edit Icon",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { isEditing = true }
                    )
                }
            }

            // Avatar
            Image(
                painter = painterResource(id = R.drawable.avata),
                contentDescription = "",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Name and Phone
            Row {
                Column(modifier = Modifier.weight(1f).padding(end = 4.dp)) {
                    MyInput(
                        value = name,
                        onValueChange = { name = it },
                        label = "Name",
                        placeholder = "Enter your name...",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Words
                        ),
                        enabled = isEditing
                    )
                    if (!isNameValid) {
                        ErrText()
                    }
                }

                Column(modifier = Modifier.weight(1f).padding(start = 4.dp)) {
                    MyInput(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "Phone number",
                        placeholder = "Your phone number...",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        enabled = isEditing,
                    )
                    if (!isPhoneValid ) {
                        ErrText()
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // University
            Column {
                MyInput(
                    value = university,
                    onValueChange = { university = it },
                    label = "University name",
                    placeholder = "Your university name...",
                    enabled = isEditing
                )
                if (!isUniversityValid) {
                    ErrText()
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Description
            MyInput(
                value = description,
                onValueChange = { description = it },
                label = "Describe yourself",
                placeholder = "Enter a description about yourself...",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                modifier = Modifier.height(300.dp),
                enabled = isEditing
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Submit button
            if (isEditing) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    MyButton(text = "Submit") {
                        isNameValid = name.matches(nameRegex) && !name.isEmpty()
                        isPhoneValid = phone.matches(phoneRegex) && !phone.isEmpty()
                        isUniversityValid = university.matches(nameRegex) && !university.isEmpty()

                        if (isNameValid && isPhoneValid && isUniversityValid) {
                            showPopup = true
                            isEditing = false
                        }
                    }
                }
            }

            if (showPopup) {
                Dialog(
                    onDismissRequest = { showPopup = false },
                    properties = DialogProperties(
                        dismissOnClickOutside = false,
                        usePlatformDefaultWidth = false
                    )
                ) {
                    AnimatedVisibility(
                        visible = showPopup,
                        enter = fadeIn() + scaleIn(initialScale = 0.8f),
                        exit = fadeOut() + scaleOut(targetScale = 0.8f)
                    ) {
                        SuccessPopupContent()
                    }
                }
            }
        }

    }
}