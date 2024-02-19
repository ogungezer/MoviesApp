package com.example.moviesapp.domain.use_case.get_movie_detail

import com.example.moviesapp.data.remote.dto.toMovieDetail
import com.example.moviesapp.domain.model.MovieDetails
import com.example.moviesapp.domain.repository.MovieRepository
import com.example.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: MovieRepository) {

    fun executeGetMovieDetails(imdbId : String) : Flow<Resource<MovieDetails>> = flow{
        try {
            emit(Resource.Loading())
            val movieDetails = repository.getMovieDetails(imdbId = imdbId)
            emit(Resource.Success(movieDetails.toMovieDetail()))
        }catch (e : IOException){
            emit(Resource.Error(message = "Film Detayları Bulunamadı!"))
        }
    }
}