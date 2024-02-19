package com.example.moviesapp.data.remote

import com.example.moviesapp.data.remote.dto.MovieDetailDto
import com.example.moviesapp.data.remote.dto.MoviesDto
import com.example.moviesapp.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {
    @GET(".")
    suspend fun getMovies(
        @Query("s") searchString : String,
        @Query("apikey") apiKey : String = API_KEY,
        @Query("page") page : Int
    ) : MoviesDto

    @GET(".")
    suspend fun getMovie(
        @Query("i") imdbId : String,
        @Query("apikey") apiKey: String = API_KEY
    ) : MovieDetailDto
}