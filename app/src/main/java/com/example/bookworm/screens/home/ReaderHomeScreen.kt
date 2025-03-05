package com.example.bookworm.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookworm.components.FABContent
import com.example.bookworm.components.ListCard
import com.example.bookworm.components.ProgressIndicatorLinear
import com.example.bookworm.components.ReaderAppBar
import com.example.bookworm.components.TitleSection
import com.example.bookworm.model.MBook
import com.example.bookworm.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
)
{
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "BookWorm",
                navController = navController
            )
        },
        floatingActionButton = {
            FABContent {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }
    )
    {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
        {
            LaunchedEffect(viewModel.data.value.data)
            {
                    viewModel.getAllBooksFromDatabase()
            }
            HomeContent(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun ReadingRightNowArea(
    listOfBooks: List<MBook>,
    navController: NavController
)
{
    val readingNowList = listOfBooks.filter {
        it.startedReading != null && it.finishedReading == null
    }
    Log.d("ReadingRightNowArea", "BookListArea: ${readingNowList.size}")
    HorizontalScrollableComponent(readingNowList)
    {
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
        Log.d("TAG", "BookListArea: $it")
    }
}

@Composable
fun HomeContent(
    navController: NavController,
    viewModel: HomeScreenViewModel
)
{
//    var listOfBooks = emptyList<MBook>()
//    val currentUser = FirebaseAuth.getInstance().currentUser
//    Log.d("Books", listOfBooks.toString())
//    LaunchedEffect(viewModel.data.value.data)
//    {
//            viewModel.getAllBooksFromDatabase()
//    }
//    if(!viewModel.data.value.data.isNullOrEmpty())
//        listOfBooks = viewModel.data.value.data!!.toList().filter { mBook ->
//            mBook.userId == currentUser?.uid.toString()
//        }
    // Use collectAsState to observe the changes in the data
    val dataState = viewModel.data.collectAsState()
    val listOfBooks = dataState.value.data?.filter { mBook ->
        mBook.userId == FirebaseAuth.getInstance().currentUser?.uid
    } ?: emptyList()
    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if(!email.isNullOrEmpty())
    {
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    }
    else
    {
        "N/A"
    }
    Column(
        modifier = Modifier
            .padding(2.dp),
        verticalArrangement = Arrangement.Top
    )
    {
        Row(
            modifier = Modifier
                .align(Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            TitleSection(
                label = "Your Reading \n " + "activity right now.."
            )
            Spacer(
                modifier = Modifier.fillMaxWidth(0.7f)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .padding(2.dp)
                        .size(45.dp)
                        .clickable {
                            navController.navigate(ReaderScreens.StatsScreen.name)
                        },
                    tint = Color.Magenta.copy(0.1f)
                )
//                Card(
//                    modifier = Modifier
//                        .padding(2.dp)
//                        .fillMaxWidth(0.5f),
//                    shape = RoundedCornerShape(3.dp),
//                    elevation = CardDefaults.cardElevation(10.dp),
//                    border = BorderStroke(
//                        width = 1.dp,
//                        color = Color.LightGray.copy(0.5f)
//                    )
//                )
//                {
                    Text(
                        text = currentUserName!!,
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
//                }
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp),
            color = Color.LightGray.copy(0.3f)
        )
        ReadingRightNowArea(
            listOfBooks = listOfBooks,
            navController = navController
        )
        TitleSection(
            label = "Reading List"
        )
        BookListArea(
            listOfBooks = listOfBooks,
            navController = navController
        )
    }
}

@Composable
fun BookListArea(
    listOfBooks: List<MBook>,
    navController: NavController
)
{
    val bookListAreaList = listOfBooks.filter {
        it.startedReading == null && it.finishedReading == null
    }
    Log.d("BookListArea", "${bookListAreaList.size}")
    HorizontalScrollableComponent(bookListAreaList)
    {
        Log.d("TAG", "BookListArea: $it")
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<MBook>,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onCardPressed: (String) -> Unit = {}
)
{
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    )
    {
        if(viewModel.data.value.loading == true)
        {
            ProgressIndicatorLinear()
            Log.d("List", listOfBooks.toString())
        }
        else
        {
            if(listOfBooks.isEmpty())
            {
                Surface(
                    modifier = Modifier
                        .padding(23.dp)
                )
                {
                    Text(
                        text = "No books found. Add a book",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Red.copy(0.4f),
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
            else
            {
                for(book in listOfBooks)
                {
                    ListCard(book)
                    {
                        onCardPressed(book.googleBookId.toString())
                    }
                }
            }
        }
    }
}

