package com.hoaiphong.composeui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.platform.LocalFocusManager
import com.hoaiphong.composeui.ui.theme.ComposeUITheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeUITheme {
                MyInformation()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ErrText(
    message: String = "Invalid format"
) {
    Text(
        text = message,
        color = Color.Red,
        fontSize = 12.sp,
        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
    )
}

@Composable
fun SuccessPopupContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Success",
                tint = Color(0xFF25AE88),
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Success!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp,
                    lineHeight = 36.sp,
                    letterSpacing = (36.sp * 0.055f),
                    color = Color(0xFF25AE88)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your information has\nbeen updated!",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    letterSpacing = (20.sp * 0.055f),
                    color = Color.Black
                )
            )
        }
    }
}
@Preview(showBackground = true, name = "Button")
@Composable
fun MyButton(
    text: String = "Submit",
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(150.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Text(text, color = Color.White)
    }
}
@Preview(showBackground = true, name = "Text")
@Composable
fun MyText(
    name: String = "",
    label: String = "Input",
    modifier: Modifier = Modifier
) {
    Text(
        text = label.uppercase(),//Nội dung hiển thị (String)
        modifier = modifier,//	Chỉnh sửa layout (kích thước, padding, v.v)
        color = Color(0x99000000),//Màu chữ (ví dụ: Color.Black)
        fontSize = 13.sp,//Kích thước chữ (ví dụ: 16.sp)
        fontStyle = FontStyle.Normal,//	Kiểu chữ (ví dụ: FontStyle.Italic, Normal)
        fontWeight = FontWeight.Normal,//	Độ đậm chữ (ví dụ: FontWeight.Bold, Normal)
        fontFamily = FontFamily.Default,//	Bộ font (ví dụ: FontFamily.Serif, SansSerif, Default)
        letterSpacing = 0.5.sp,//	Khoảng cách giữa các chữ
        textDecoration = TextDecoration.None,//	Gạch chân, gạch ngang (ví dụ: TextDecoration.Underline)
        textAlign = TextAlign.Start,//	Căn lề (ví dụ: TextAlign.Center, Start)
        lineHeight = 20.sp,//Chiều cao dòng
        overflow = TextOverflow.Clip,//	Cách xử lý chữ bị tràn (ví dụ: TextOverflow.Ellipsis)
        softWrap = true,//	Có ngắt dòng khi vượt chiều rộng không?
        maxLines = Int.MAX_VALUE,//	Số dòng tối đa hiển thị
        minLines = 1,//	Số dòng tối thiểu
        onTextLayout = {}, //	Callback khi layout text xong (ít dùng trong preview)
        style = TextStyle.Default//	Style tổng hợp (nếu dùng TextStyle(...))
    )
}

@Composable
fun MyInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        MyText(label = label)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(placeholder, fontSize = 13.sp) },
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(15.dp),
            isError = isError,
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                errorBorderColor = Color.Red
            )
        )
    }
}

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
                )}
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