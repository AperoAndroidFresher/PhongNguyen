package com.hoaiphong.composeui.ui.screen.mysong

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.data.model.Song

@Composable
fun SongDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.wrapContentSize(Alignment.TopStart)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = modifier.width(290.dp)
                .background(Color.Black.copy(alpha = 0.8f)),
            offset = DpOffset(x = -30.dp, y = 40.dp)
        ) {
            DropdownMenuItem(
                text = {
                    Text("Remove from playlist", fontWeight = FontWeight.Bold, color = Color.White)
                },
                onClick = {
                    onDismissRequest()
                    onRemoveClick()
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            )
            DropdownMenuItem(
                text = {
                    Text("Share (coming soon)", color = Color.Gray)
                },
                onClick = { },
                enabled = false,
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_about),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            )
        }
    }
}


@Composable
fun MyListItem(
    song: Song,
    modifier: Modifier = Modifier,
    onRemoveClick: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = song.imageResId),
            contentDescription = "",
            modifier = Modifier
                .size(70.dp)
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        ) {
            Text(
                song.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                song.author,
                color = Color.LightGray,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = song.time,
                fontSize = 20.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(
                onClick = { expanded = true },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_about),
                    contentDescription = "More Options",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            SongDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                onRemoveClick = onRemoveClick,
            )
        }
    }
}
@Composable
fun MyColumnItem(
    song: Song = Song(),
    modifier: Modifier = Modifier,
    onRemoveClick: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(135.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painterResource(id = song.imageResId),
                contentDescription = "",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.6f), shape = CircleShape)
                        .size(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_about),
                        contentDescription = "",
                        modifier = Modifier.size(12.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }

                SongDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    onRemoveClick = onRemoveClick,
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            song.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            song.author,
            color = Color.Gray,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            song.time,
            color = Color.White,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
