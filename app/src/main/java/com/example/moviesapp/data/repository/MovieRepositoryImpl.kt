package com.example.moviesapp.data.repository

import com.example.moviesapp.data.remote.MoviesAPI
import com.example.moviesapp.data.remote.dto.MovieDetailDto
import com.example.moviesapp.data.remote.dto.MoviesDto
import com.example.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api : MoviesAPI) : MovieRepository {

    override suspend fun getMovies(search: String, page: Int): MoviesDto {
        return api.getMovies(searchString = search, page = page)
    }

    override suspend fun getMovieDetails(imdbId: String): MovieDetailDto {
        return api.getMovie(imdbId = imdbId)
    }

}