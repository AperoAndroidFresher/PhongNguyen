package com.hoaiphong.composeui.ui.settinglanguage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.utils.LanguageManager
import com.hoaiphong.composeui.utils.getCurrentLanguageCode
import com.hoaiphong.composeui.utils.getLanguageNameFromCode

@Preview(showBackground = true)
@Composable
fun LanguageSettingsScreen(
    currentLanguageCode: String = getCurrentLanguageCode(LocalContext.current),
    onBack: () -> Unit = {},
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedCode by remember { mutableStateOf(currentLanguageCode) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(16.dp),
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onBack() }
            )

            Text(
                text = stringResource(R.string.settings),
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            if (selectedCode != currentLanguageCode) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_tick),
                    contentDescription = "Save",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            LanguageManager.setLocale(context, selectedCode)
                            onBack()
                        }
                )
            } else {
                Spacer(modifier = Modifier.size(20.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(vertical = 12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_language),
                contentDescription = "Language",
                tint = Color.White,
                modifier = Modifier.size(24.dp),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(R.string.language),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = getLanguageNameFromCode(selectedCode),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.DarkGray)
            ) {
                listOf(
                    "English" to "en",
                    "Korean" to "ko",
                    "French" to "fr",
                    "Vietnamese" to "vi"
                ).forEach { (name, code) ->
                    DropdownMenuItem(
                        text = { Text(name, color = Color.White) },
                        onClick = {
                            selectedCode = code
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


