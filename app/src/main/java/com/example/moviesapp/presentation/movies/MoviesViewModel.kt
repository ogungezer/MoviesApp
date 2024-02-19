package com.example.moviesapp.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.use_case.get_movies.GetMoviesUseCase
import com.example.moviesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val useCase: GetMoviesUseCase): ViewModel() {

    private val _uiState = MutableStateFlow(MoviesState())
    val uiState : StateFlow<MoviesState> = _uiState.asStateFlow()

    private var job : Job? = null

    init {
        getMovies(search = _uiState.value.search , page = 1)
    }

    fun getMovies(search : String, page : Int) {
        job?.cancel()
        job = useCase.executeGetMovies(search = search, page = page).onEach {//flowları toplamak için onEach kullanıldı
            when(it){
                is Resource.Success -> {
                    println("succes kısmı girildi")
                    _uiState.value = MoviesState(movieList = it.data ?: emptyList(), currentPage =  page , totalResult = useCase.totalResults)

                }
                is Resource.Error -> {
                    println("error kısmı girildi")
                    _uiState.value = MoviesState(errorMessage = it.message ?: "Error" ,currentPage = 0)
                }
                is Resource.Loading -> {
                    println("loading kısmı girildi")
                    _uiState.value = MoviesState(isLoading = true, currentPage = page, totalResult = useCase.totalResults)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun pageNumInc(currentPage : Int, search : String){
        _uiState.value = MoviesState(currentPage = currentPage + 1)
        getMovies(search, uiState.value.currentPage)
    }
    fun pageNumDec(currentPage : Int, search : String){
        _uiState.value = MoviesState(currentPage = currentPage - 1)
        getMovies(search, uiState.value.currentPage)
    }

}