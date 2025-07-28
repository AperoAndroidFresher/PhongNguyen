package com.hoaiphong.composeui.ui.screen.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ContentRed(
    title: String,
    modifier: Modifier = Modifier,
    onNext: (() -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
) = ContentBase(
    title = title,
    modifier = modifier.background(color = Color.Red),
    onNext = onNext,
    content = content
)


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContentBase(
    title: String,
    modifier: Modifier = Modifier,
    onNext: (() -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .clip(RoundedCornerShape(48.dp))
    ) {
        Title(title)
        if (content != null) content()
        if (onNext != null) {
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onNext
            ) {
                Text("Next")
            }
        }
    }
}
@Composable
fun ColumnScope.Title(title: String) {
    Text(
        modifier = Modifier
            .padding(24.dp)
            .align(Alignment.CenterHorizontally),
        fontWeight = FontWeight.Bold,
        text = title
    )
}
