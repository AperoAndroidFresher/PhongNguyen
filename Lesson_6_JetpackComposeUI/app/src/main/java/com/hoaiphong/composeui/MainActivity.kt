package com.hoaiphong.composeui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoaiphong.composeui.ui.theme.ComposeUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeUITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeUITheme {
        Greeting("Android")
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
@Preview(showBackground = true, name = "Input")
@Composable
fun MyInput(
    name: String = "",
    placeholder: String ="Input1",
    label: String = "Input",
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf(name) }
    Box(
        modifier = modifier
            .fillMaxWidth()
    ){

        MyText(
            label = label,
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(placeholder, fontSize = 13.sp) },
            modifier = modifier.fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(15.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            colors = OutlinedTextFieldDefaults.colors(
                // focusedBorderColor = Color.Blue, //Màu viền khi input được focus (đang gõ)
                unfocusedBorderColor = Color.Gray,//Màu viền khi chưa được focus
                //  focusedLabelColor = Color.Blue,// Màu chữ label khi input đang được chọn
                unfocusedLabelColor = Color.Gray, //Màu chữ label khi input không được chọn
                // cursorColor = Color.Blue,//Màu của con trỏ nhập văn bản
                // disabledBorderColor = Color.LightGray,//Màu viền khi input bị disable
                errorBorderColor = Color.Red//Màu viền khi input có lỗi (isError = true)
            )
        )
    }

}

@Preview(showBackground = true, name = "Form")
@Composable
fun MyInformation(name: String = "", modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .background(color = Color(0xFFF5FAFC))
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp), // thêm padding 2 bên nếu muốn
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "MY INFORMATION",
                fontSize = 25.sp,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Image(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Edit Icon",
                modifier = Modifier.size(30.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.avata),
            contentDescription = "",
            modifier = modifier.size(150.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,

            )
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            MyInput(
                modifier = Modifier.weight(1f).padding(end = 4.dp),
                label = "Name",
                placeholder = "Enter your name..."

            )
            MyInput(
                modifier = Modifier.weight(1f).padding(start = 4.dp),
                label = "Phone number",
                placeholder = "Your phone number..."
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        MyInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            label = "University name",
            placeholder = "Your university name..."

        )

        MyInput(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 8.dp),
            label = "describe yourself",
            placeholder = "Enter a description about yourself..."
        )

        Box (
            modifier = modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            MyButton(
                text = "Submit"
            )
        }

    }

}



