package com.example.moviesapp.presentation.movies

import com.example.moviesapp.domain.model.Movie

data class MoviesState(
    val isLoading : Boolean = false,
    var movieList : List<Movie> = emptyList(),
    val errorMessage : String = "",
    val search : String = "Kolpaçino",
    var currentPage : Int = 0,
    val totalResult : Int = 0
)