package com.example.bookworm.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bookworm.model.Item
import com.example.bookworm.navigation.ReaderScreens

import com.example.bookworm.screens.search.BooksSearchViewModel

@Composable
fun BookList(
    navController: NavController,
    viewModel: BooksSearchViewModel
)
{
    val listOfBooks = viewModel.list
    if(viewModel.isLoading)
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
    else
    {
        LazyColumn(
            modifier = Modifier.Companion
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(items = listOfBooks) { book ->
                BookRow(
                    book = book,
                    navController = navController
                )
            }
        }
    }
}
@Composable
fun BookRow(
    book: Item,
    navController: NavController
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(100.dp, 150.dp)
            .padding(6.dp)
            .clickable {
                navController.navigate(ReaderScreens.DetailScreen.name + "/${book.id}")
            },
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(7.dp)
    )
    {
        Row(
            modifier = Modifier
                .padding(5.dp),
            verticalAlignment = Alignment.Top
        )
        {
            val imageUrl =
                if(book.volumeInfo.imageLinks.smallThumbnail.isEmpty())
                    "https://cdn-icons-png.flaticon.com/512/4154/4154438.png"
                else
                    book.volumeInfo.imageLinks.smallThumbnail
            GlideImageLoader(
                url = imageUrl,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .width(100.dp)
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                //horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = book.volumeInfo.title,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Author: ${book.volumeInfo.authors}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Date: ${book.volumeInfo.publishedDate}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "${book.volumeInfo.categories}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}