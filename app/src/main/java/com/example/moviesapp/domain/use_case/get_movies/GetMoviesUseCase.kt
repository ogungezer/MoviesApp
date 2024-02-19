package com.example.moviesapp.domain.use_case.get_movies

import com.example.moviesapp.data.remote.dto.toMovieList
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MovieRepository
import com.example.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

    fun executeGetMovies(search : String, page : Int) : Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val movieList = repository.getMovies(search = search, page = page)
            if(movieList.Response.equals("True")){
                totalResults = movieList.totalResults.toInt() // gelen toplam film sayısını temsil eder. Pagination için kullanılacaktır.
                emit(Resource.Success(movieList.toMovieList()))
            } else {
                emit(Resource.Error(message = "Film Bulunamadı!"))
            }
        } catch (e: IOException){
            emit(Resource.Error(message = "İnternet Bağlantısı Yok"))
            println("IOException geldi.")
        }
    }

    var totalResults = 0
}