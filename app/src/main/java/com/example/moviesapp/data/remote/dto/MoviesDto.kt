package com.example.moviesapp.data.remote.dto

import com.example.moviesapp.domain.model.Movie

data class MoviesDto(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)


//Mapper
fun MoviesDto.toMovieList() : List<Movie>{
    return Search.map { search -> Movie(search.Poster, search.Title, search.Type, search.Year, search.imdbID) }
}


