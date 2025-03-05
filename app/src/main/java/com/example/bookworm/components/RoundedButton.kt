package com.example.bookworm.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoundedButton(
    label: String = "Reading",
    radius: Int = 29,
    onPress: () -> Unit = {}
)
{
    Surface(
        modifier = Modifier.Companion
            .padding(4.dp)
            .clip(
                RoundedCornerShape(
                    bottomEndPercent = radius,
                    topStartPercent = radius
                )
            ),
        color = Color(0xFF92CBDF)
    )
    {
        Column(
            modifier = Modifier.Companion
                .width(90.dp)
                .heightIn(40.dp)
                .clickable {
                    onPress()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        )
        {
            Text(
                text = label,
                fontSize = 15.sp,
                fontWeight = FontWeight.Companion.Bold,
                color = Color.Companion.White
            )
        }
    }
}