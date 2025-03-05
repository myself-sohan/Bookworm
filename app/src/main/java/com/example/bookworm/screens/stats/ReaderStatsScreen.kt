package com.example.bookworm.screens.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbUpAlt
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookworm.components.GlideImageLoader
import com.example.bookworm.components.ProgressIndicatorLinear
import com.example.bookworm.components.ReaderAppBar
import com.example.bookworm.model.Item
import com.example.bookworm.model.MBook
import com.example.bookworm.navigation.ReaderScreens
import com.example.bookworm.screens.home.HomeScreenViewModel
import com.example.bookworm.utils.formatDate
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@Composable
fun StatsScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
)
{
    var books: List<MBook>
    val currentUser = Firebase.auth.currentUser
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Book Stats",
                icon = Icons.AutoMirrored.Default.ArrowBack,
                showProfile = false,
                navController = navController
            )
            {
                navController.popBackStack()
            }
        }
    )
    {
        Surface(
            modifier = Modifier
                .padding(it)
        )
        {
            val dataState = viewModel.data.collectAsState()
            books = dataState.value.data?.filter { mBook ->
                mBook.userId == currentUser?.uid
            } ?: emptyList()
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Image(
                            imageVector = Icons.Sharp.Person,
                            contentDescription = "icon",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(
                        text = "Hi ${currentUser?.email.toString().split("@")[0]}"
                            .uppercase(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(5.dp)
                )
                {
                    val readBookList: List<MBook>
                    = if(dataState.value.data.isNullOrEmpty())
                        emptyList()
                    else
                    {
                        books.filter { mBook ->
                            (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                        }
                    }
                    val readingBookList = books.filter { mBook ->
                        (mBook.startedReading != null) && (mBook.finishedReading == null)
                    }
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 25.dp,
                                top = 4.dp,
                                bottom = 4.dp
                            ),
                        horizontalAlignment = Alignment.Start
                    )
                    {
                        Text(
                            text = "Your Stats",
                            style = MaterialTheme.typography.titleLarge
                        )
                        HorizontalDivider()
                        Text(
                            text = "You're reading: ${readingBookList.size} books"
                        )
                        Text(
                            text = "You've read: ${readBookList.size} books"
                        )
                    }
                }
                if(dataState.value.loading == true)
                {
                    ProgressIndicatorLinear()
                }
                else
                {
                    HorizontalDivider()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentPadding = PaddingValues(16.dp)
                    )
                    {
                        //filter books by finished ones
                        val readBookList: List<MBook>
                                = if(dataState.value.data.isNullOrEmpty())
                            emptyList()
                        else
                        {
                            books.filter { mBook ->
                                (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                            }
                        }
                        items(items = readBookList)
                        {
                            book ->
                            BookRowStatsScreen(
                                book = book
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun BookRowStatsScreen(
    book: MBook
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(100.dp, 150.dp)
            .padding(6.dp),
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
                if(book.photoUrl.toString().isEmpty())
                    "https://cdn-icons-png.flaticon.com/512/4154/4154438.png"
                else
                    book.photoUrl.toString()
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
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text(
                        text = book.title.toString(),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    if(book.rating!! >= 4)
                    {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Thumbs Up",
                            tint = Color.Green.copy(alpha = 0.5f)
                        )
                    }
                    else
                        Box{}
                }
                Text(
                    text = "Started: ${formatDate(book.startedReading!!)}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Date: ${book.publishedDate}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Finished: ${formatDate(book.finishedReading!!)}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}