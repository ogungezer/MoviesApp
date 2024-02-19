package com.example.moviesapp.presentation.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.use_case.get_movie_detail.GetMovieDetailsUseCase
import com.example.moviesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
  private val useCase: GetMovieDetailsUseCase,
  stateHandle: SavedStateHandle //navhostta attığımız imdbId parametresini viewmodelde kullanmak için
) : ViewModel() {

  private val _uiState = MutableStateFlow(MovieDetailState())
  val uiState : StateFlow<MovieDetailState> = _uiState.asStateFlow()

  init {
      stateHandle.get<String>(key = "imdbID")?.let{
        getMovie(it)
      }
  }

  private fun getMovie(imdbID : String){
    useCase.executeGetMovieDetails(imdbId = imdbID).onEach {
      when(it){
        is Resource.Success -> {
          _uiState.value = MovieDetailState(movieDetails = it.data)
        }
        is Resource.Loading -> {
          _uiState.value = MovieDetailState(isLoading = true)
        }
        is Resource.Error -> {
          _uiState.value = MovieDetailState(errorMessage = it.message ?: "Error")
        }
      }
    }.launchIn(viewModelScope)

  }

}