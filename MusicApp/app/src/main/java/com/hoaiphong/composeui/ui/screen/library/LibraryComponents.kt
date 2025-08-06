package com.hoaiphong.composeui.ui.screen.library

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.db.entity.relations.PlaylistWithSongs


@Composable
fun LottieAnimationLoading() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("lottie/lottie_remote_item_loading.json"))
    val progress by animateLottieCompositionAsState(
        composition, iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition, progress = { progress }, modifier = Modifier.size(100.dp)
    )
}

@Composable
fun LibraryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier.width(150.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceTint),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.surfaceTint)
    ) {
        Text(text, color = MaterialTheme.colorScheme.onSecondary)
    }
}

@Composable
fun ChoosePlaylistDialog(
    playlists: List<PlaylistWithSongs>,
    onDismiss: () -> Unit,
    onAddClick: () -> Unit,
    onPlaylistSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss, containerColor = Color(0xFF1E1E1E), title = {
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Choose playlist",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }, text = {
            if (playlists.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "You don't have any \nplaylists. Click the \n“+” button to add",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        IconButton(
                            onClick = onAddClick,
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color.Transparent, shape = RoundedCornerShape(12.dp))
                                .border(1.dp, Color.White, shape = RoundedCornerShape(12.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add playlist",
                                tint = Color.White
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(playlists) { playlistWithSongs ->
                        ChoosePlaylistItem(
                            playlistWithSongs = playlistWithSongs,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onPlaylistSelected(playlistWithSongs.playlist.playlistId)
                                })
                    }
                }
            }
        }, confirmButton = {}, modifier = modifier
    )
}


@Composable
fun ChoosePlaylistItem(
    playlistWithSongs: PlaylistWithSongs,
    modifier: Modifier = Modifier,
) {
    val playlist = playlistWithSongs.playlist
    val songs = playlistWithSongs.songs

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.DarkGray)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = songs.firstOrNull()?.image ?: R.drawable.ic_add_to_playlist
                ),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            playlist.name?.let {
                Text(
                    it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                "${songs.size} songs", color = Color.Gray, fontSize = 14.sp
            )
        }
    }
}

@Composable
fun NoInternetConnectionContent(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_no_internet),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "No internet connection,\nplease check your \nconnection again",
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C2CB))
        ) {
            Text("Try again")
        }
    }
}