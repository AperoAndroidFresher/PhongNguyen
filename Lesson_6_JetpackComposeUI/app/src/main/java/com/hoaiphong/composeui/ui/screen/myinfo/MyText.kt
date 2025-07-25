package com.hoaiphong.composeui.ui.screen.myinfo

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, name = "Text")
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