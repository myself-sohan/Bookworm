package com.example.bookworm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BookRating(
    score: Double = 4.5
)
{
    Surface(
        modifier = Modifier.Companion
            .height(70.dp)
            .width(70.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(
            topStartPercent = 30,
            topEndPercent = 30,
            bottomStartPercent = 30,
            bottomEndPercent = 30
        ),
        shadowElevation = 6.dp,
        color = Color.Companion.White
    )
    {
        Column(
            modifier = Modifier.Companion
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        )
        {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                modifier = Modifier.Companion
                    .padding(3.dp)
            )
            Text(
                text = score.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}