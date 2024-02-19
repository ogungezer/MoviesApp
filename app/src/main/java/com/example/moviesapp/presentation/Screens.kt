package com.example.moviesapp.presentation

sealed class Screens(val route : String){
    object MoviesPage : Screens(route = "MoviePage")
    object MovieDetailPage : Screens(route = "MovieDetailPage")
}
