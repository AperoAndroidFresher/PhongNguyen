package com.hoaiphong.composeui.ui.screen.mysong

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoaiphong.composeui.R

@Preview(showBackground = true)
@Composable
fun sortingTitle(
    modifier: Modifier = Modifier.Companion,
) {
    Column(
        modifier = modifier.fillMaxWidth().background(Color.Companion.Black)
    ) {
        Spacer(modifier = Modifier.Companion.height(32.dp))

        Box(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Spacer(Modifier.Companion.padding(20.dp))
            Row(
                modifier = Modifier.Companion.align(Alignment.Companion.CenterStart),
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Sort Icon",
                    colorFilter = ColorFilter.Companion.tint(Color.Companion.White),
                )
            }
            Text(
                text = "Sorting",
                fontSize = 25.sp,
                color = Color.Companion.White,
                textAlign = TextAlign.Companion.Center,
                modifier = Modifier.Companion.align(Alignment.Companion.Center)
            )

            Row(
                modifier = Modifier.Companion.align(Alignment.Companion.CenterEnd),
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_tick),
                    contentDescription = "Sort Icon",
                    colorFilter = ColorFilter.Companion.tint(Color.Companion.White),
                )
            }
        }
    }
}