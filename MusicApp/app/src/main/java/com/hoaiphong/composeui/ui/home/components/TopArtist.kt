package com.hoaiphong.composeui.ui.home.components

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.api.response.ArtistImage
import com.hoaiphong.composeui.api.response.ArtistObjectResponse

@Composable
fun TopArtist(
    artists: List<ArtistObjectResponse>,
    modifier: Modifier = Modifier,
    onSeeAllArtistClick: () -> Unit = {},
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.top_artists),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )
            Text(
                text = stringResource(R.string.see_all),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF00C2CB),  
                modifier = Modifier.clickable { onSeeAllArtistClick() },
            )
        }
        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
            items(artists) { artist ->
                ArtistItem(
                    name = artist.name,
                    imageUrl = artist.image.lastOrNull()?.url,
                )
            }
        }
    }
}

@Composable
fun ArtistItem(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String?,
) {
    Box(
        modifier = modifier
            .padding(end = 8.dp)
            .width(140.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        AsyncImage(
            model = imageUrl.takeIf { !it.isNullOrEmpty() } ?: R.drawable.song1,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(8.dp)),
            placeholder = null,
            error = null,
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.4f)),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 12.dp, top = 8.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopArtistPreview() {
    val sampleArtists = listOf(
        ArtistObjectResponse(
            name = "Sabrina Carpenter",
            image = listOf(
                ArtistImage("", ""),
                ArtistImage("", "https://example.com/image1.jpg"),
            ),
        ),
        ArtistObjectResponse(
            name = "Olivia Rodrigo",
            image = listOf(
                ArtistImage("", ""),
                ArtistImage("", "https://example.com/image2.jpg"),
            ),
        ),
    )
    TopArtist(
        artists = sampleArtists,
        onSeeAllArtistClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun ArtistItemPreview() {
    ArtistItem(
        name = "Sabrina Carpenter",
        imageUrl = "https://example.com/image1.jpg",
    )
}
