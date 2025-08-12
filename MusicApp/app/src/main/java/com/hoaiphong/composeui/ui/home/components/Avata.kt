package com.hoaiphong.composeui.ui.home.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.comon.UserSession
import com.hoaiphong.composeui.ui.navigation.MyInformationScreen

@Preview(showBackground = true, backgroundColor = 0xF000000)
@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    onNavigate: (Any) -> Unit = {},
    avatarUri: Uri? = null,
) {
    val context = LocalContext.current
    val username = remember { UserSession(context).getSavedUsername() ?: "Guest" }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            verticalAlignment = Alignment.Companion.CenterVertically,
        ) {
            Box(
                modifier = Modifier.Companion
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp)),
            ) {
                if (avatarUri != null) {
                    AsyncImage(
                        model = avatarUri,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .matchParentSize()
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .clickable { onNavigate(MyInformationScreen) },
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.song1),
                        error = painterResource(id = R.drawable.song1),
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.song1),
                        contentDescription = "Avatar",
                        modifier = Modifier.Companion
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .clickable { onNavigate(MyInformationScreen) },
                        contentScale = ContentScale.Companion.Crop,
                    )
                }
            }

            Spacer(modifier = Modifier.Companion.width(8.dp))

            Column {
                Text(
                    "Welcome back !",
                    color = Color.Companion.White,
                    fontSize = 20.sp,
                )
                Text(
                    username,
                    color = Color.Companion.Gray,
                    fontSize = 14.sp,
                )
            }

            Spacer(modifier = Modifier.Companion.weight(1f))

            IconButton(onClick = { onNavigate(MyInformationScreen) }) {
                Image(
                    painter = painterResource(id = MyInformationScreen.iconRes),
                    contentDescription = "My Info",
                )
            }
        }

        Spacer(modifier = Modifier.Companion.height(16.dp))

        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            verticalAlignment = Alignment.Companion.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_crown),
                contentDescription = "Crown",
                modifier = Modifier.Companion.size(28.dp),
                tint = Color.Companion.Unspecified,
            )

            Spacer(modifier = Modifier.Companion.width(8.dp))

            Text(
                "Rankings",
                color = Color(0xFF00C2CB),
                fontSize = 28.sp,
            )
        }
    }
}
