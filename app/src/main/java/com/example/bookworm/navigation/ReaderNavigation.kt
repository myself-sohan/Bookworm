package com.example.bookworm.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookworm.screens.SplashScreen
import com.example.bookworm.screens.details.DetailsScreen
import com.example.bookworm.screens.home.HomeScreen
import com.example.bookworm.screens.home.HomeScreenViewModel
import com.example.bookworm.screens.login.LoginScreen
import com.example.bookworm.screens.search.BooksSearchViewModel
import com.example.bookworm.screens.search.SearchScreen
import com.example.bookworm.screens.stats.StatsScreen
import com.example.bookworm.screens.update.UpdateScreen
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun ReaderNavigation()
{
    val navController = rememberNavController()
    NavHost (
        navController = navController,
        startDestination =  ReaderScreens.SplashScreen.name
    )
    {
        composable(ReaderScreens.SplashScreen.name)
        {
            SplashScreen(
                navController = navController
            )
        }
        composable(ReaderScreens.HomeScreen.name)
        {
            val homeViewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(
                navController = navController,
                viewModel = homeViewModel
            )
        }
        composable(ReaderScreens.LoginScreen.name)
        {
            LoginScreen(
                navController = navController
            )
        }
        val detailName = ReaderScreens.DetailScreen.name
        composable(
            route = "$detailName/{bookId}",
            arguments = listOf(
                navArgument("bookId")
                {
                    type = NavType.StringType
                }
            )
        )
        {
            it.arguments?.getString("bookId").let {
                DetailsScreen(
                    navController = navController,
                    bookId = it.toString()
                )
            }
        }
        composable(ReaderScreens.CreateAccountScreen.name)
        {
            LoginScreen(
                navController = navController
            )
        }
        composable(ReaderScreens.SearchScreen.name)
        {
            val searchViewModel = hiltViewModel<BooksSearchViewModel>()
            SearchScreen(
                navController = navController,
                viewModel = searchViewModel
            )
        }
        composable(ReaderScreens.StatsScreen.name)
        {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            StatsScreen(
                navController = navController,
                viewModel = homeScreenViewModel
            )
        }
        val updateName = ReaderScreens.UpdateScreen.name
        composable(
            route = "$updateName/{bookItemId}",
            arguments = listOf(
                navArgument("bookItemId")
                {
                    type = NavType.StringType
                }
            )
        )
        {
            UpdateScreen(
                navController = navController,
                bookItemId = it.arguments?.getString("bookItemId").toString()
            )
        }
    }
}