package com.hoaiphong.composeui.ui.information.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.ui.components.ErrText
import com.hoaiphong.composeui.ui.information.MyInfoIntent
import com.hoaiphong.composeui.ui.information.MyInfoState
import com.hoaiphong.composeui.ui.theme.darkMode
import com.hoaiphong.composeui.ui.theme.lightMode


@Preview(showBackground = true, name = "Information Screen Content")
@Composable
fun PreviewInformationScreenContentDarkTheme() {
    val state = MyInfoState(
        avatarUri = null,
        name = "Nguyễn Văn A",
        phone = "0123456789",
        university = "Đại học Bách Khoa",
        description = "Tôi là một lập trình viên Android thích Jetpack Compose.",
        isNameValid = true,
        isPhoneValid = true,
        isUniversityValid = true,
        isEditing = true,
        isDarkMode = true
    )

    MaterialTheme(
        colorScheme = darkMode.color,
        typography = darkMode.typography,
        shapes = darkMode.shapes
    ) {
        InformationScreenContent(
            state = state,
            onIntent = {},
            onPickImage = {}
        )
    }
}

@Preview(showBackground = true, name = "Information Screen Content")
@Composable
fun PreviewInformationScreenContentLightTheme() {
    val state = MyInfoState(
        avatarUri = null,
        name = "Nguyễn Văn A",
        phone = "0123456789",
        university = "Đại học Bách Khoa",
        description = "Tôi là một lập trình viên Android thích Jetpack Compose.",
        isNameValid = true,
        isPhoneValid = true,
        isUniversityValid = true,
        isEditing = true,
        isDarkMode = false
    )

    MaterialTheme(
        colorScheme = lightMode.color, typography = lightMode.typography, shapes = lightMode.shapes
    ) {
        InformationScreenContent(state = state, onIntent = {}, onPickImage = {})
    }
}

@Composable
fun InformationScreenContent(
    state: MyInfoState, onIntent: (MyInfoIntent) -> Unit, onPickImage: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).data(state.avatarUri ?: R.drawable.avata).build()
    )

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(8.dp)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {

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
                    stringResource(R.string.my_information),
                    fontSize = 25.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
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
                            })
                }
            }

            // Avatar
            Image(
                painter = painter,
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
                    InformationInput(
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
                    InformationInput(
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
                InformationInput(
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

            InformationInput(
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
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    InformationButton(text = stringResource(R.string.submit)) {
                        onIntent(MyInfoIntent.Submit)
                    }
                }
            }else {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    LogoutButton(
                        text = stringResource(R.string.logout),
                        iconResId = R.drawable.ic_logout,
                        onClick = { onIntent(MyInfoIntent.Logout) },
                    )
                }
            }
            
        }
    }
}
