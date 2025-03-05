package com.example.bookworm.screens.update

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookworm.components.GlideImageLoader
import com.example.bookworm.components.InputField
import com.example.bookworm.components.ReaderAppBar
import com.example.bookworm.data.DataOrException
import com.example.bookworm.model.MBook
import com.example.bookworm.screens.home.HomeScreenViewModel
import com.example.bookworm.components.RatingBar
import com.example.bookworm.components.RoundedButton
import com.example.bookworm.components.showToast
import com.example.bookworm.utils.formatDate
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import com.example.bookworm.R
import com.example.bookworm.components.AlertMessage
import com.example.bookworm.components.ProgressIndicatorLinear
import com.example.bookworm.navigation.ReaderScreens

@Composable
fun UpdateScreen(
    navController: NavController,
    bookItemId: String,
    viewModel: HomeScreenViewModel = hiltViewModel()
)
{
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Update Book",
                icon = Icons.AutoMirrored.Default.ArrowBack,
                navController = navController,
                showProfile = false
            )
            {
                navController.popBackStack()
            }
        }
    )
    {
//        val bookInfo = produceState<DataOrException<List<MBook>,
//                Boolean,
//                Exception>>(initialValue = DataOrException(data = emptyList(),
//            true, Exception(""))){
//            value = viewModel.data.value
//        }.value
        val bookInfo = viewModel.data.value
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        )
        {
            Log.d("Info", "UpdateScreen: ${bookInfo.data.toString()}")
            Log.d("VInfo", "UpdateScreen: ${viewModel.data.value.data.toString()}")
            if(bookInfo.loading == true)
            {
                ProgressIndicatorLinear()
                bookInfo.loading = false
            }
            else
            {
                Column(
                    modifier = Modifier
                        .padding(top = 3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                )
                {
                    Surface(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth(),
                        shape = CircleShape,
                        shadowElevation = 4.dp
                    )
                    {
                        ShowBookUpdate(
                            bookInfo = bookInfo,
                            navController = navController,
                            bookItemId = bookItemId
                        )
                    }
                    ShowSimpleForm(
                        book = bookInfo.data?.first{mBook ->
                            mBook.googleBookId == bookItemId
                        }!!,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun ShowSimpleForm(book: MBook, navController: NavController)
{
    val notesText = rememberSaveable{ mutableStateOf("") }
    val isStartedReading = rememberSaveable{ mutableStateOf(false) }
    val isFinishedReading = remember { mutableStateOf(false) }
    val ratingVal = remember { mutableIntStateOf(book.rating?.toInt() ?: 0) }
    val context = LocalContext.current
    SimpleForm(
        defaultValue =
            if(book.notes.toString().isNotEmpty())
            {
                book.notes.toString()
            }
            else
            {
                "No Thoughts Available."
            }
    )
    {
        notesText.value = it
    }
    Row(
        modifier = Modifier
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    )
    {
        TextButton(
            onClick = {isStartedReading.value = true},
            enabled = book.startedReading == null && !isFinishedReading.value
        )
        {
            if(book.startedReading == null)
            {
                if(!isStartedReading.value)
                {
                    Text(
                        text = "Start Reading"
                    )
                }
                else
                {
                    Text(
                        text = "Started Reading",
                        modifier = Modifier
                            .alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                }
            }
            else
            {
                Text(
                    text = "Started on: ${formatDate(book.startedReading!!)}",
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(4.dp)
        )
        TextButton(
            onClick = {isFinishedReading.value = true},
            enabled = book.finishedReading == null && isStartedReading.value == false
        )
        {
            if(book.finishedReading == null)
            {
                if(!isFinishedReading.value) {
                    Text(
                        text = "Mark as Read"
                    )
                }
                else
                {
                    Text(
                        text = "Finished Reading",
                        modifier = Modifier
                            .alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                }
            }
            else
            {
                Text(
                    text = "Finished on: ${formatDate(book.finishedReading!!)}",
                )
            }
        }
    }
    Text(
        text = "Rating",
        modifier = Modifier
            .padding(3.dp),
        style = MaterialTheme.typography.titleLarge
    )
    book.rating?.toInt().let {
        RatingBar(rating = it!!) { rating ->
            ratingVal.intValue = rating
            Log.d("TAG", "ShowSimpleForm: ${ratingVal.intValue}")
        }
    }
    Spacer(
        modifier = Modifier
            .padding(bottom = 15.dp)
    )
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {

//        val bookToUpdate = mutableMapOf<String, Any>()
//
//        if (notesText.value.isNotEmpty()) bookToUpdate["notes"] = notesText.value
//        if (ratingVal.intValue != book.rating?.toInt()) bookToUpdate["rating"] = ratingVal.intValue
//        if (isStartedReading.value) bookToUpdate["started_reading_at"] = Timestamp.now()
//        if (isFinishedReading.value) bookToUpdate["finished_reading_at"] = Timestamp.now()
        val bookToUpdate = mutableMapOf<String, Any>()

        notesText.value.let { if (it.isNotEmpty()) bookToUpdate["notes"] = it }
        ratingVal.intValue.takeIf { it != book.rating?.toInt() }?.let { bookToUpdate["rating"] = it }
        if (isStartedReading.value) bookToUpdate["started_reading_at"] = Timestamp.now()
        if (isFinishedReading.value) bookToUpdate["finished_reading_at"] = Timestamp.now()

        RoundedButton(
            label = "Update"
        )
        {
            if(bookToUpdate.isNotEmpty())
            {
                try {
                    Firebase.firestore.collection("books")
                        .document(book.id!!)
                        .update(bookToUpdate)
                        .addOnCompleteListener {
                            showToast(context, "Book Updated Successfully!!")
                            navController.popBackStack()
                            Log.d("Show", "Update successful")
                        }
                        .addOnFailureListener {
                            Log.e("Error", "Error updating Firestore", it)
                        }
                } catch (e: Exception) {
                    Log.e("CriticalError", "Unexpected error during update", e)
                }
            }
            else
            {
                showToast(context, "Nothing to update!!")
                //navController.popBackStack()
                return@RoundedButton
            }
        }
        Spacer(
            modifier = Modifier
                .width(100.dp)
        )
        val openDialog = remember { mutableStateOf(false) }
        if(openDialog.value)
        {
            AlertMessage(
                showDialog = openDialog,
                labelText = stringResource(id = R.string.delete_book_message) + "\n" +
                stringResource(id = R.string.action),
                leftButtonText = "YES",
                rightButtonText = "NO",
                onLeftButtonClick = {
                    Firebase.firestore
                        .collection("books")
                        .document(book.id!!)
                        .delete()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                openDialog.value = false
                                showToast(context, "Book Deleted Successfully!!")
                                navController.navigate(ReaderScreens.HomeScreen.name)
                            }
                        }
                },
            )
        }
        RoundedButton(
            label = "Delete"
        )
        {
            openDialog.value = true
        }
    }
}

@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    defaultValue: String = "Great Book",
    onSearch: (String) -> Unit = {}
)
{
    Column {
        val textFieldValue = rememberSaveable{ mutableStateOf(defaultValue) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = rememberSaveable(textFieldValue.value)
        {
            textFieldValue.value.trim().isNotEmpty()
        }
        InputField(
            modifier = Modifier
                .height(140.dp)
                .padding(3.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(
                        topStart = 15.dp,
                        topEnd = 15.dp,
                        bottomStart = 15.dp,
                        bottomEnd = 15.dp
                    )
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                ),
            valueState = textFieldValue,
            labelId = "Enter your thoughts",
            enabled = true,
            onAction = KeyboardActions{
                if(!valid) return@KeyboardActions
                onSearch(textFieldValue.value.trim())
                //textFieldValue.value = ""
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun ShowBookUpdate(
    bookInfo: DataOrException<List<MBook>, Boolean, Exception>,
    navController: NavController,
    bookItemId: String
)
{
    Row(
        horizontalArrangement = Arrangement.Center
    )
    {
        if(bookInfo.data != null)
        {
            Column(
                modifier = Modifier
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
            )
            {
                CardListItem(
                    book = bookInfo.data!!.first{
                        it.googleBookId == bookItemId
                    },
                    onPressDetails = {}
                )
            }
        }
    }
}

@Composable
fun CardListItem(
    book: MBook,
    onPressDetails: () -> Unit
)
{
    Card(
        modifier = Modifier
            .padding(
                start = 4.dp,
                end = 4.dp,
                top = 4.dp,
                bottom = 8.dp
            )
            .clip(
                RoundedCornerShape(20.dp)
            )
            .clickable {},
        elevation = CardDefaults.cardElevation(8.dp)
    )
    {
        Row {
            GlideImageLoader(
                url = book.photoUrl.toString(),
                modifier = Modifier
                    .height(144.dp)
                    .width(120.dp)
                    .padding(4.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 120.dp,
                            topEnd = 20.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
            )
            Column() {
                Text(
                    text = book.title.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp
                        )
                        .width(120.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.authors.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            top = 2.dp,
                            bottom = 0.dp
                        )
                )
                Text(
                    text = book.publishedDate.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            top = 0.dp,
                            bottom = 8.dp
                        )
                )
            }
        }
    }
}
