package com.example.bookworm.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookworm.model.MBook


@Composable
fun ListCard(
    book: MBook,
    onPressDetails: (String) -> Unit = {}
)
{
    val displayMetrics = LocalContext.current.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp
    val scrolled = rememberScrollState()
    Card(
        modifier = Modifier.Companion
            .clickable {
                onPressDetails(book.title.toString())
            }
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .verticalScroll(scrolled),
        shape = RoundedCornerShape(29.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Companion.White
        )
    )
    {
        Column(
            modifier = Modifier.Companion.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Companion.Start
        )
        {
            Row(
                horizontalArrangement = Arrangement.Center
            )
            {
                GlideImageLoader(
                    url = book.photoUrl.toString(),
                    modifier = Modifier.Companion
                        .width(100.dp)
                        .height(140.dp)
                        .padding(14.dp)
                )
                Spacer(
                    modifier = Modifier.Companion.width(50.dp)
                )
                Column(
                    modifier = Modifier.Companion.padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Companion.CenterHorizontally
                )
                {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Fav Icon",
                        modifier = Modifier.Companion
                            .padding(bottom = 1.dp),
                    )
                    BookRating(score = book.rating!!)
                }
            }
            Text(
                text = book.title.toString(),
                modifier = Modifier.Companion
                    .padding(4.dp),
                fontWeight = FontWeight.Companion.Bold,
                maxLines = 2,
                overflow = TextOverflow.Companion.Ellipsis
            )
            Text(
                text = book.authors.toString(),
                modifier = Modifier.Companion
                    .padding(4.dp),
                style = MaterialTheme.typography.bodySmall
            )
            val isStartedReading = remember {
                mutableStateOf(false)
            }
            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Companion.Bottom
            )
            {
                isStartedReading.value = book.startedReading != null
                RoundedButton(
                    label = if(isStartedReading.value)
                                "Reading"
                            else
                                "Begin",
                    radius = 70
                )
            }
        }
    }
}