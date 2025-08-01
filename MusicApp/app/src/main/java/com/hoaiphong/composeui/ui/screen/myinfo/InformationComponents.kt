package com.hoaiphong.composeui.ui.screen.myinfo


import android.content.Context
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun ErrText(
    message: String = "Invalid format"
) {
    Text(
        text = message,
        color = Color.Companion.Red,
        fontSize = 12.sp,
        modifier = Modifier.Companion.padding(start = 8.dp, top = 4.dp)
    )
}

@Preview(showBackground = true, name = "Button")
@Composable
fun MyButton(
    text: String = "Submit",
    modifier: Modifier = Modifier.Companion,
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

@Composable
fun MyText(
    name: String = "",
    label: String = "Input",
    modifier: Modifier = Modifier.Companion
) {
    Text(
        text = label.uppercase(),//Nội dung hiển thị (String)
        modifier = modifier,//	Chỉnh sửa layout (kích thước, padding, v.v)
        color = MaterialTheme.colorScheme.primary,//Màu chữ (ví dụ: Color.Black)
        fontSize = 13.sp,//Kích thước chữ (ví dụ: 16.sp)
        fontStyle = FontStyle.Companion.Normal,//	Kiểu chữ (ví dụ: FontStyle.Italic, Normal)
        fontWeight = FontWeight.Companion.Normal,//	Độ đậm chữ (ví dụ: FontWeight.Bold, Normal)
        fontFamily = FontFamily.Companion.Default,//	Bộ font (ví dụ: FontFamily.Serif, SansSerif, Default)
        letterSpacing = 0.5.sp,//	Khoảng cách giữa các chữ
        textDecoration = TextDecoration.Companion.None,//	Gạch chân, gạch ngang (ví dụ: TextDecoration.Underline)
        textAlign = TextAlign.Companion.Start,//	Căn lề (ví dụ: TextAlign.Center, Start)
        lineHeight = 20.sp,//Chiều cao dòng
        overflow = TextOverflow.Companion.Clip,//	Cách xử lý chữ bị tràn (ví dụ: TextOverflow.Ellipsis)
        softWrap = true,//	Có ngắt dòng khi vượt chiều rộng không?
        maxLines = Int.MAX_VALUE,//	Số dòng tối đa hiển thị
        minLines = 1,//	Số dòng tối thiểu
        onTextLayout = {}, //	Callback khi layout text xong (ít dùng trong preview)
        style = TextStyle.Companion.Default//	Style tổng hợp (nếu dùng TextStyle(...))
    )
}


@Composable
fun SuccessPopupContent(modifier: Modifier = Modifier.Companion) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .background(Color.Companion.White, shape = RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.Companion.align(Alignment.Companion.Center),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Success",
                tint = Color(0xFF25AE88),
                modifier = Modifier.Companion.size(100.dp)
            )
            Spacer(modifier = Modifier.Companion.height(16.dp))
            Text(
                text = "Success!",
                modifier = Modifier.Companion.fillMaxWidth(),
                textAlign = TextAlign.Companion.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Companion.SemiBold,
                    fontSize = 36.sp,
                    lineHeight = 36.sp,
                    letterSpacing = (36.sp * 0.055f),
                    color = Color(0xFF25AE88)
                )
            )
            Spacer(modifier = Modifier.Companion.height(16.dp))
            Text(
                text = "Your information has\nbeen updated!",
                textAlign = TextAlign.Companion.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Companion.Normal,
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    letterSpacing = (20.sp * 0.055f),
                    color = Color.Companion.Black
                )
            )
        }
    }
}

@Composable
fun rememberImagePicker(
    context: Context,
    onImagePicked: (Uri?) -> Unit
): ManagedActivityResultLauncher<String, Uri?> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = onImagePicked
    )
}