package com.hoaiphong.composeui.ui.playlistsong.components

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.comon.SongItem
import com.hoaiphong.composeui.utils.rememberDragDropListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun ColumnSongList(
    songs: List<SongItem>,
    onRemoveClick: (Int) -> Unit,
    onDropdownToggle: (Int) -> Unit,
    onDismissDropdown: (Int) -> Unit,
    onPlayClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onMove: (Int, Int) -> Unit,
    isDragEnabled: Boolean,
) {
    val scope = rememberCoroutineScope()
    var overScrollJob by remember { mutableStateOf<Job?>(null) }
    val dragDropListState = rememberDragDropListState(onMove = onMove)
    var lazyColumnModifier = modifier.fillMaxSize().padding(top = 10.dp, start = 10.dp, end = 10.dp)
    if(isDragEnabled){
        lazyColumnModifier = modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consumeAllChanges()
                        dragDropListState.onDrag(offset = offset)

                        if (overScrollJob?.isActive == true)
                            return@detectDragGesturesAfterLongPress

                        dragDropListState
                            .checkForOverScroll()
                            .takeIf { it != 0f }
                            ?.let {
                                overScrollJob = scope.launch {
                                    dragDropListState.lazyListState.scrollBy(it)
                                }
                            } ?: kotlin.run { overScrollJob?.cancel() }
                    },
                    onDragStart = { offset -> dragDropListState.onDragStart(offset) },
                    onDragEnd = { dragDropListState.onDragInterrupted() },
                    onDragCancel = { dragDropListState.onDragInterrupted() }
                )
            }
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
    }
    LazyColumn(
        modifier = lazyColumnModifier,
        state = dragDropListState.lazyListState
    ) {
        items(songs.size) { index ->
            SongListItem(
                song = songs[index],
                onRemoveClick = { onRemoveClick(index) },
                onDropdownToggle = { onDropdownToggle(index) },
                onDismissDropdown = { onDismissDropdown(index) },
                onPlayClick = { onPlayClick(index) },
                isDragEnabled = isDragEnabled,
                modifier = Modifier.composed {
                    val offsetOrNull = dragDropListState.elementDisplacement.takeIf {
                        index == dragDropListState.currentIndexOfDraggedItem
                    }
                    Modifier.graphicsLayer {
                        translationY = offsetOrNull ?: 0f
                    }
                }
            )
        }
    }
}
