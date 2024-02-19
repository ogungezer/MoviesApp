package com.example.moviesapp.domain.repository

import com.example.moviesapp.data.remote.dto.MovieDetailDto
import com.example.moviesapp.data.remote.dto.MoviesDto

interface MovieRepository {
    suspend fun getMovies(search : String, page : Int) : MoviesDto
    suspend fun getMovieDetails(imdbId : String) : MovieDetailDto
}