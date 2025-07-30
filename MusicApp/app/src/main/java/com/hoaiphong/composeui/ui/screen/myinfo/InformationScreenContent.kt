package com.hoaiphong.composeui.ui.screen.myinfo

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hoaiphong.composeui.R

@Composable
fun InformationScreenContent(
    state: MyInfoState,
    onIntent: (MyInfoIntent) -> Unit,
    onPickImage: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = if (state.isDarkMode) R.drawable.ic_lightmode else R.drawable.ic_darkmode),
                    contentDescription = "Toggle Theme Icon",
                    modifier = Modifier.clickable {
                        onIntent(MyInfoIntent.ToggleTheme)
                    },
                )
                Text(
                    "MY INFORMATION",
                    fontSize = 25.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
                if (!state.isEditing) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Edit Icon",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                onIntent(MyInfoIntent.NameChanged(state.name))
                                onIntent(MyInfoIntent.PhoneChanged(state.phone))
                                onIntent(MyInfoIntent.UniversityChanged(state.university))
                                onIntent(MyInfoIntent.DescriptionChanged(state.description))
                                onIntent(MyInfoIntent.ToggleEditing(true))
                            }
                    )
                }
            }

            // Avatar
            Image(
                painter = state.avatarUri?.let { rememberAsyncImagePainter(it) }
                    ?: painterResource(id = R.drawable.avata),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable(enabled = state.isEditing) {
                        onPickImage()
                    },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                ) {
                    MyInput(
                        value = state.name,
                        onValueChange = { onIntent(MyInfoIntent.NameChanged(it)) },
                        label = "Name",
                        placeholder = "Enter your name...",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Words
                        ),
                        enabled = state.isEditing
                    )
                    if (!state.isNameValid) {
                        ErrText()
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                ) {
                    MyInput(
                        value = state.phone,
                        onValueChange = { onIntent(MyInfoIntent.PhoneChanged(it)) },
                        label = "Phone number",
                        placeholder = "Your phone number...",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = state.isEditing
                    )
                    if (!state.isPhoneValid) {
                        ErrText()
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                MyInput(
                    value = state.university,
                    onValueChange = { onIntent(MyInfoIntent.UniversityChanged(it)) },
                    label = "University name",
                    placeholder = "Your university name...",
                    enabled = state.isEditing
                )
                if (!state.isUniversityValid) {
                    ErrText()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            MyInput(
                value = state.description,
                onValueChange = { onIntent(MyInfoIntent.DescriptionChanged(it)) },
                label = "Describe yourself",
                placeholder = "Enter a description about yourself...",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier.height(150.dp),
                enabled = state.isEditing
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isEditing) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    MyButton(text = "Submit") {
                        onIntent(MyInfoIntent.Submit)
                    }
                }
            }
        }
    }
}
