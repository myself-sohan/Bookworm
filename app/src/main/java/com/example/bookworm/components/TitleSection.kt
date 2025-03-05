package com.example.bookworm.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TitleSection(
    modifier: Modifier = Modifier.Companion,
    label : String
)
{
    Surface(
        modifier = modifier
            .padding(start = 5.dp, top = 1.dp)
    )
    {
        Column {
            Text(
                text = label,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontStyle = FontStyle.Companion.Normal,
                textAlign = TextAlign.Companion.Left,
            )
        }
    }
}