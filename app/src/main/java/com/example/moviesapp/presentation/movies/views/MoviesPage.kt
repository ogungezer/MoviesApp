package com.example.moviesapp.presentation.movies.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviesapp.R
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.presentation.Screens
import com.example.moviesapp.presentation.movies.MoviesViewModel
import kotlin.math.ceil

@Composable
fun MoviesPage(
    navController: NavController,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState()
    var currentSearch by rememberSaveable { mutableStateOf(state.value.search)}

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF1A1A1A))){
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()){
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp, bottom = 8.dp),
                hint = "Hangi filmi bulmak istiyorsun?"
            ){
                state.value.currentPage = 1
                viewModel.getMovies(search = it , page = state.value.currentPage)
                currentSearch = it
            }
            Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Box(contentAlignment = Alignment.Center){
                    BackButton(
                        onBackButtonClicked = {
                            viewModel.pageNumDec(
                                currentPage = state.value.currentPage,
                                search = currentSearch
                            )
                        },
                        currentPage = state.value.currentPage
                    )
                }
                Text(text = "${state.value.currentPage}/${ceil((state.value.totalResult).toFloat() / 10).toInt()} ", color = Color.White, textAlign = TextAlign.Center)
                Box(contentAlignment = Alignment.Center){
                    NextButton(
                        onNextButtonClicked = {
                            viewModel.pageNumInc(
                                currentPage = state.value.currentPage,
                                search = currentSearch
                            )
                        },
                        totalPage = if(state.value.totalResult != 0) {ceil((state.value.totalResult).toFloat() / 10).toInt()} else 0  ,
                        currentPage = state.value.currentPage
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(horizontal = 30.dp))
            Spacer(modifier = Modifier.height(20.dp))
            if (state.value.errorMessage.isEmpty()) {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()) {
                    items(state.value.movieList) { movie ->
                        MovieCard(
                            movie,
                            onItemClick = {
                                navController.navigate(Screens.MovieDetailPage.route + "/${movie.imdbID}")
                            }
                        )
                    }
                }
            } else{
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                    Text(text = state.value.errorMessage, textAlign = TextAlign.Center, fontSize = 24.sp, color = Color.White, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier,
    hint : String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("")}
    var isHintDisplayed by remember { mutableStateOf(hint != "")}
    var tintColor by remember { mutableLongStateOf(0xFFFFFFFF) }

    Box(modifier = modifier.background(color = Color.Transparent, shape = RoundedCornerShape(20.dp)), contentAlignment = Alignment.CenterStart){
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .shadow(
                    elevation = 16.dp, RoundedCornerShape(20.dp), spotColor = Color(
                        0xFF0724E4
                    )
                )
                .border(
                    1.dp, color = Color(
                        0x66FFFFFF
                    ), shape = RoundedCornerShape(20.dp)
                )
                .fillMaxWidth()
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused && text.isEmpty()
                    tintColor = if (!it.isFocused) {
                        0xFFF44336
                    } else {
                        0xFFFFFFFF
                    }
                },
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFF303030),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTrailingIconColor = Color.Transparent,
                cursorColor = Color(0x46020202),
                textColor = Color.White
            ),
            trailingIcon = {
               Icon(
                   imageVector = Icons.Filled.Search,
                   contentDescription = "search",
                   modifier = Modifier
                       .clickable { onSearch(text) }
                       .size(25.dp),
                   tint = Color(tintColor)
               )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {onSearch(text)})
        )
        if(isHintDisplayed){
            Text(
                text = hint,
                color = Color(0xA4FFFFFF),
                modifier = Modifier.padding(start = 12.dp),
                fontStyle = FontStyle.Italic,

            )
        }
    }
}

@Composable
fun MovieCard(movie : Movie, onItemClick : () -> Unit) {
    Card(modifier = Modifier
        .padding(horizontal = 20.dp)
        .padding(bottom = 20.dp)
        .fillMaxWidth()
        .clickable { onItemClick() },
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)){
                AsyncImage(
                    model = movie.Poster,
                    contentDescription = movie.Title,
                    placeholder = painterResource(id = R.drawable.movie_placeholder),
                    error = painterResource(id = R.drawable.movie_placeholder),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(150.dp,220.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)) {
                Text(text = movie.Title, color = Color.White , fontSize = 24.sp)
                Text(text = movie.Year, color = Color.White)
            }
        }
    }
}

@Composable
fun NextButton(onNextButtonClicked : () -> Unit = {}, totalPage: Int, currentPage: Int) {
    Button(
        onClick = onNextButtonClicked,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2C2C2C),
            disabledContainerColor = Color(0xFF111111),
            disabledContentColor = Color.Red,
            contentColor = Color.White
        ),
        enabled = currentPage != totalPage && totalPage != 0
    ) {
        Text(text = "Next ->", textAlign = TextAlign.Center)
    }
}
@Composable
fun BackButton(onBackButtonClicked: () -> Unit = {}, currentPage: Int) {
    Button(
        onClick = onBackButtonClicked,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2C2C2C),
            disabledContainerColor = Color(0xFF111111),
            disabledContentColor = Color.Red,
            contentColor = Color.White
        ),
        enabled = currentPage != 1 && currentPage != 0
    ) {
        Text(text = "<- Back", textAlign = TextAlign.Center)
    }
}