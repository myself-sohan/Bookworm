package com.example.bookworm.screens.details

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTargetMarker
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookworm.components.GlideImageLoader
import com.example.bookworm.components.ReaderAppBar
import com.example.bookworm.components.RoundedButton
import com.example.bookworm.data.Resource
import com.example.bookworm.model.Item
import com.example.bookworm.model.MBook
import com.example.bookworm.model.VolumeInfo
import com.example.bookworm.navigation.ReaderScreens
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore

@Composable
fun DetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel()
)
{
    // Trigger the data fetching only once when the screen is first composed
    LaunchedEffect(bookId) {
        viewModel.getBookInfo(bookId)
    }
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Book Details",
                icon = Icons.AutoMirrored.Default.ArrowBack,
                showProfile = false,
                navController = navController,
            )
            {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }
    )
    {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        )
        {
            Column(
                modifier = Modifier
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            )
            {
                val bookInfo = viewModel.bookInfo.value
                Log.d("Details","${bookInfo.data?.volumeInfo}")
                if(bookInfo.data == null)
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        LinearProgressIndicator(
                            color = Color(224, 139, 139, 255),
                            trackColor = Color(202, 100, 220, 255),
                            strokeCap = StrokeCap.Companion.Round
                        )
                        Text(
                            text = "loading..",
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                }
                else
                {
                    ShowBookDetails(
                        bookInfo = bookInfo,
                        navController = navController,
                        googleBookId = bookId
                    )
                }
            }
        }
    }
}

@Composable
fun ShowBookDetails(
    bookInfo: Resource<Item>,
    navController: NavController,
    googleBookId: String
)
{
    val context = LocalContext.current
    val bookData = bookInfo.data!!.volumeInfo
    Card(
        modifier = Modifier
            .padding(34.dp),
        shape = RoundedCornerShape(29.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    )
    {
        GlideImageLoader(
            url = bookData.imageLinks.thumbnail,
            modifier = Modifier
                .padding(5.dp)
                .width(90.dp)
                .height(90.dp),
            imageScale = ImageView.ScaleType.FIT_CENTER
        )
    }
    Text(
        text = bookData.title,
        style = MaterialTheme.typography.titleLarge,
        overflow = TextOverflow.Ellipsis,
        maxLines = 19
    )
    Text(text = "Authors: ${bookData.authors}")
    Text(text = "Page Count: ${bookData.pageCount}")
    Text(text = "Categories: ${bookData.categories}",
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis)
    Text(text = "Published: ${bookData.publishedDate}",
        style = MaterialTheme.typography.bodyMedium)
    Spacer(
        modifier = Modifier
            .padding(5.dp)
    )
    val cleanDescription = HtmlCompat.fromHtml(
        bookData.description,
        HtmlCompat.FROM_HTML_MODE_LEGACY
    ).toString()
    val localDims = LocalContext.current.resources.displayMetrics
    Surface(
        modifier = Modifier
            .height(localDims.heightPixels.dp.times(0.15f))
            .padding(4.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            width = 2.dp,
            color = Color.DarkGray
        ),
        shadowElevation = 5.dp
    )
    {
        LazyColumn(
            modifier = Modifier
                .padding(2.dp)
        )
        {
            item {
                Text(
                    text = cleanDescription,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(14.dp)
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .padding(top = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        RoundedButton(
            label = "Save",
        )
        {
            val book = MBook(
                title = bookData.title,
                authors = bookData.authors.toString(),
                description = bookData.description,
                categories = bookData.categories.toString(),
                notes = "",
                photoUrl = bookData.imageLinks.thumbnail,
                publishedDate = bookData.publishedDate,
                pageCount = bookData.pageCount.toString(),
                rating = 0.0,
                googleBookId = googleBookId,
                userId = Firebase.auth.currentUser?.uid.toString()
            )
            // save to firestore database
            saveToFirebase(
                book = book,
                navController =navController,
                context = context
            )
        }
        RoundedButton(
            label = "Cancel",
        )
        {
            navController.popBackStack()
        }
    }
}
fun saveToFirebase(
    book: MBook,
    navController: NavController,
    context: Context
)
{
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")
            // Alternate Declarations
//    val db = Firebase.firestore
//    val db = com.google.firebase.ktx.Firebase.firestore
//    if(book.toString().isNotEmpty())
//    {
//        dbCollection
//            .add(book)
//            .addOnSuccessListener { documentRef ->
//                val docId = documentRef.id
//                dbCollection.document(docId)
//                    .update(hashMapOf("id" to docId) as Map<String, Any>)
//                    .addOnCompleteListener { task ->
//                        Log.d("saveToFirebase", "Book Added")
//                        if (task.isSuccessful)
//                            navController.popBackStack()
//                    }
//                    .addOnFailureListener {
//                        Log.d("saveToFirebase", "Book Not Added")
//
//                    }
//            }
//    }
//    else{}
    val docId = "${book.userId}_${book.googleBookId}"

    dbCollection.document(docId)
        .get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                // 🚨 Book already exists → Show Toast & navigate back
                Toast.makeText(context, "Book already saved!", Toast.LENGTH_LONG).show()
                navController.popBackStack()//Goes back to previous screen but does not recompose
            } else {
                // ✅ Book does NOT exist, save it using the unique doc ID
                dbCollection.document(docId)
                    .set(book)
                    .addOnSuccessListener {
                        dbCollection.document(docId)
                        .update("id",docId)
                        Toast.makeText(context, "Book Saved!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                    .addOnFailureListener {
                        Log.e("saveToFirebase", "Failed to save book")
                    }
            }
        }
        .addOnFailureListener {
            Log.e("saveToFirebase", "Error checking Firestore for existing book")
        }
}