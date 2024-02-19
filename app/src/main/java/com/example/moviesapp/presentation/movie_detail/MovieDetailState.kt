package com.example.moviesapp.presentation.movie_detail

import com.example.moviesapp.domain.model.MovieDetails

data class MovieDetailState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val movieDetails: MovieDetails? = null
)
