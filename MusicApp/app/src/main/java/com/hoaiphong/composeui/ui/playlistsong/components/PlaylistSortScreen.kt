package com.hoaiphong.composeui.ui.playlistsong.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.ui.navigation.PlaylistSongs
import com.hoaiphong.composeui.ui.playlistsong.PlaylistIntent
import com.hoaiphong.composeui.ui.playlistsong.PlaylistViewModel
import com.hoaiphong.composeui.utils.toDurationFormatted

@Composable
fun PlaylistSortScreen(
    entry: PlaylistSongs,
    modifier: Modifier = Modifier,
    viewModel: PlaylistViewModel = viewModel(),
    onClose: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    val playlistId = entry.playlistId
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(playlistId) {
        viewModel.loadPlaylistById(playlistId)
    }
    
    var draggingIndex by remember { mutableStateOf<Int?>(null) }
    var dragOffsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .background(Color(0xFF121212))
            .fillMaxSize(),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onClose() },
                )

                Text(
                    text = "Sorting",
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_tick),
                    contentDescription = "Confirm",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            viewModel.dispatch(PlaylistIntent.ConfirmSort(entry.playlistId))
                            onConfirm() 
                        },
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = modifier.fillMaxSize()) {
                itemsIndexed(state.songs) { index, song ->

                    val isDragging = index == draggingIndex
                    val animatedOffsetY by animateFloatAsState(targetValue = if (isDragging) dragOffsetY else 0f)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .graphicsLayer {
                                translationY = animatedOffsetY
                            }
                            .pointerInput(state.songs) {
                                detectDragGestures(
                                    onDragStart = {
                                        draggingIndex = index
                                    },
                                    onDragEnd = {
                                        draggingIndex = null
                                        dragOffsetY = 0f
                                    },
                                    onDragCancel = {
                                        draggingIndex = null
                                        dragOffsetY = 0f
                                    },
                                    onDrag = { change, dragAmount ->
                                        change.consume()
                                        dragOffsetY += dragAmount.y

                                        // Kiểm tra di chuyển vượt ngưỡng
                                        val targetIndex = when {
                                            dragOffsetY > 200 && index < state.songs.size - 1 -> index + 1
                                            dragOffsetY < -200 && index > 0 -> index - 1
                                            else -> null
                                        }

                                        if (targetIndex != null && targetIndex != index) {
                                            viewModel.dispatch(PlaylistIntent.MoveSong(index, targetIndex))
                                            draggingIndex = targetIndex
                                            dragOffsetY = 0f
                                        }
                                    }
                                )
                            }
                            .background(if (isDragging) Color.DarkGray else Color.Transparent),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = song.song.image ?: R.drawable.song1,
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(70.dp)
                                .padding(5.dp)
                                .clip(RoundedCornerShape(10.dp)),
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp)
                                .clickable { viewModel.dispatch(PlaylistIntent.PlaySong(index)) }
                        ) {
                            Text(
                                text = song.song.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = song.song.author,
                                color = Color.LightGray,
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = song.song.duration.toDurationFormatted(),
                                fontSize = 20.sp,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )

                            IconButton(
                                onClick = { viewModel.dispatch(PlaylistIntent.ToggleDropdown(index)) },
                                modifier = Modifier.size(24.dp),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_hamburger_menu),
                                    contentDescription = "More Options",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
