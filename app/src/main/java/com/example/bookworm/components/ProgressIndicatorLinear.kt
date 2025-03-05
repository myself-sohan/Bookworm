package com.example.bookworm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun ProgressIndicatorLinear()
{
    Column(
        modifier = Modifier.Companion
            .padding(top = 3.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Surface(
            modifier = Modifier.Companion
                .fillMaxSize()
                .wrapContentSize(Alignment.Companion.Center)
        )
        {
            LinearProgressIndicator(
                color = Color(224, 139, 139, 255),
                trackColor = Color(202, 100, 220, 255),
                strokeCap = StrokeCap.Companion.Round
            )
        }
    }
}