package com.hoaiphong.composeui.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.api.response.AlbumArtist
import com.hoaiphong.composeui.api.response.AlbumImage
import com.hoaiphong.composeui.api.response.AlbumObjectResponse

@Composable
fun TopAlbums(
    albums: List<AlbumObjectResponse>,
    modifier: Modifier = Modifier,
    onSeeAllAlbumClick: () -> Unit = {},
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Top Albums",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )
            Text(
                text = "See all",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF00C2CB),
                modifier = Modifier.clickable { onSeeAllAlbumClick() },
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(200.dp),
        ) {
            items(albums) { album ->
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF1A1A1A)),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF1A1A1A)),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier.size(60.dp),
                        ) {
                            AsyncImage(
                                model = album.image.lastOrNull()?.url.takeIf
                                { !it.isNullOrEmpty() } ?: R.drawable.song1,
                                contentDescription = album.name,
                                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop,
                                placeholder = null,
                                error = null,
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = album.name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = album.artist.name,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewTopAlbums() {
    val sampleAlbums = listOf(
        AlbumObjectResponse(
            name = "Sample Album 1",
            artist = AlbumArtist(name = "Artist 1"),
            image = listOf(
                AlbumImage(url = "", size = "medium"),
            ),
        ),
        AlbumObjectResponse(
            name = "Sample Album 2",
            artist = AlbumArtist(name = "Artist 2"),
            image = listOf(
                AlbumImage(url = "", size = "medium"),
            ),
        ),
        AlbumObjectResponse(
            name = "Sample Album 2",
            artist = AlbumArtist(name = "Artist 2"),
            image = listOf(
                AlbumImage(url = "", size = "medium"),
            ),
        ),
        AlbumObjectResponse(
            name = "Sample Album 2",
            artist = AlbumArtist(name = "Artist 2"),
            image = listOf(
                AlbumImage(url = "", size = "medium"),
            ),
        ),
        AlbumObjectResponse(
            name = "Sample Album 2",
            artist = AlbumArtist(name = "Artist 2"),
            image = listOf(
                AlbumImage(url = "", size = "medium"),
            ),
        ),
        AlbumObjectResponse(
            name = "Sample Album 2",
            artist = AlbumArtist(name = "Artist 2"),
            image = listOf(
                AlbumImage(url = "", size = "medium"),
            ),
        ),
    )

    TopAlbums(
        albums = sampleAlbums,
        modifier = Modifier.fillMaxWidth(),
        onSeeAllAlbumClick = { },
    )
}
