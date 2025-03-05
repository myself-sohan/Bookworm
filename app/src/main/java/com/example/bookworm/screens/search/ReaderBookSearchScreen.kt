package com.example.bookworm.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookworm.components.BookList
import com.example.bookworm.components.GlideImageLoader
import com.example.bookworm.components.ReaderAppBar
import com.example.bookworm.components.SearchForm
import com.example.bookworm.model.Item
import com.example.bookworm.navigation.ReaderScreens

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: BooksSearchViewModel = hiltViewModel()
)
{
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Search Books",
                icon = Icons.AutoMirrored.Default.ArrowBack,
                navController = navController,
                showProfile = false
            )
            {
                navController.navigate(ReaderScreens.HomeScreen.name)
            }
        }
    )
    {
        Surface(
            modifier = Modifier
                .padding(it)
        )
        {
            Column()
            {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    viewModel = viewModel
                )
                {
                    viewModel.searchBooks(it)
                }
                Spacer(
                    modifier = Modifier.height(13.dp)
                )
                BookList(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}



