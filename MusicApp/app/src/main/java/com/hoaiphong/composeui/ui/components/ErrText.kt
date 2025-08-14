package com.hoaiphong.composeui.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoaiphong.composeui.R

@Composable
fun ErrText(
    message: String = stringResource(R.string.invalid_format),
) {
    Text(
        text = message,
        color = Color.Companion.Red,
        fontSize = 12.sp,
        modifier = Modifier.Companion.padding(start = 8.dp, top = 4.dp)
    )
}

@Preview(showBackground = true, name = "Error Text")
@Composable
fun PreviewErrText() {
    ErrText(message = stringResource(R.string.invalid_format))
}
