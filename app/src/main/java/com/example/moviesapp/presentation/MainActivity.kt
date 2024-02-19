package com.example.moviesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesapp.presentation.movie_detail.views.MovieDetailPage
import com.example.moviesapp.presentation.movies.views.MoviesPage
import com.example.moviesapp.presentation.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF1A1A1A)
                ) {
                    val navController = rememberNavController()
                    MoviesApp(navController = navController)
                }
            }
        }
    }
}

@Composable
fun MoviesApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.MoviesPage.route){
        composable(route = Screens.MoviesPage.route){
            MoviesPage(navController)
        }
        composable(
            route = Screens.MovieDetailPage.route+"/{imdbID}",
            arguments = listOf(
                navArgument(name = "imdbID") {
                    NavType.StringType
                }
            )
        ){
            MovieDetailPage(navController = navController)
        }
    }
}
