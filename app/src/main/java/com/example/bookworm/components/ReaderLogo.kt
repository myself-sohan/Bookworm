package com.example.bookworm.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.bookworm.R

@Composable
fun ReaderLogo(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = "BookWorm",
        style = MaterialTheme.typography.displayMedium.copy(
            fontFamily = FontFamily(
                Font(R.font.rubik_gemstones)
            ),
            fontWeight = FontWeight.Bold,
        ),
        color = Color.Companion.Red.copy(alpha = 0.7f)
    )
}