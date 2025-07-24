package com.hoaiphong.composeui.ui.theme

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hoaiphong.composeui.R

class PlaylistSongActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_playlist_song)

    }
}



@Composable
fun MyListItem(
    song: Song,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
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
            Text(song.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(song.author, color = Color.Gray, fontSize = 12.sp)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
            horizontalAlignment = Alignment.End
        ) {
            Row {
                Text(song.time, fontSize = 16.sp)

                Spacer(Modifier.padding(5.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_about),
                    contentDescription = "",
                    modifier = Modifier.size(19.dp),
                    colorFilter = ColorFilter.tint(Color.Black)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MyColumnItem(
    modifier: Modifier = Modifier,
    name: String ="Name of Song",
    author: String ="Name of Author",
    time: String = "Time of Song",
    id: Int= R.drawable.song1,
    onIconClick: () -> Unit ={}
) {
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
                painter = painterResource(id = id),
                contentDescription = "",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = onIconClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.Black.copy(alpha = 0.6f), shape = CircleShape)
                    .size(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_about),
                    contentDescription = "",
                    modifier = Modifier.size(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(author, color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(time, color = Color.LightGray, fontSize = 12.sp)
    }
}

data class Song(
    val name: String = "Name of Song",
    val author: String = "Name of Author",
    val time: String = "Time of Song",
    val imageResId: Int = R.drawable.song1
)

val songList = listOf(
    Song("Song #1", "Author A", "3:10", R.drawable.song1),
    Song("Song #2", "Author B", "3:15", R.drawable.song2),
    Song("Song #3", "Author C", "4:05", R.drawable.song3),
    Song("Song #4", "Author D", "5:20", R.drawable.song4),
    Song("Song #5", "Author E", "4:45", R.drawable.song5),
    Song("Song #6", "Author F", "3:33", R.drawable.song1),
    Song("Song #7", "Author G", "5:12", R.drawable.song2),
    Song("Song #8", "Author H", "4:25", R.drawable.song3),
    Song("Song #9", "Author I", "3:50", R.drawable.song4),
    Song("Song #10", "Author J", "4:10", R.drawable.song5),
    Song("Song #11", "Author K", "3:18", R.drawable.song1),
    Song("Song #12", "Author L", "5:00", R.drawable.song2),
    Song("Song #13", "Author M", "4:35", R.drawable.song3),
    Song("Song #14", "Author N", "3:22", R.drawable.song4),
    Song("Song #15", "Author O", "5:45", R.drawable.song5),
    Song("Song #16", "Author P", "4:55", R.drawable.song1),
    Song("Song #17", "Author Q", "3:40", R.drawable.song2),
    Song("Song #18", "Author R", "4:15", R.drawable.song3),
    Song("Song #19", "Author S", "5:05", R.drawable.song4),
    Song("Song #20", "Author T", "4:42", R.drawable.song5)
)


@Preview(showBackground = true)
@Composable
fun PlaylistSong(modifier: Modifier = Modifier){

    val songs = songList

    Box(
        modifier = modifier
            .background(Color(0xFFF5FAFC))
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
        ) {
            Spacer(Modifier.padding(20.dp))
            Text(
                text = "My Playlist",
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
            )

            // Icons aligned to end (right)
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_column),
                    contentDescription = "Column Icon",
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier.size(25.dp)
                )
                Spacer(Modifier.padding(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_sort_up),
                    contentDescription = "Sort Icon",
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


    }
}