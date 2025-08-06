package com.hoaiphong.composeui.ui.myinfo.components

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

@Preview(showBackground = true, name = "My Text")
@Composable
fun PreviewMyText() {
    MyText(label = "Name")
}

@Composable
fun MyText(
    modifier: Modifier = Modifier.Companion,
    name: String = "",
    label: String = "Input",
) {
    Text(
        text = label.uppercase(),
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 13.sp,
        fontStyle = FontStyle.Companion.Normal,
        fontWeight = FontWeight.Companion.Normal,
        fontFamily = FontFamily.Companion.Default,
        letterSpacing = 0.5.sp,
        textDecoration = TextDecoration.Companion.None,
        textAlign = TextAlign.Companion.Start,
        lineHeight = 20.sp,
        overflow = TextOverflow.Companion.Clip,
        softWrap = true,
        maxLines = Int.MAX_VALUE,
        minLines = 1,
        onTextLayout = {},
        style = TextStyle.Companion.Default
    )
}
