package com.example.moviesapp.presentation.movie_detail.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviesapp.R
import com.example.moviesapp.presentation.movie_detail.MovieDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailPage(
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBar(navController = navController)
        }
    ) {innerPading ->
        MovieShowDetails(viewModel = viewModel, padding = innerPading)
    }

}

@Composable
fun MovieShowDetails(
    viewModel: MovieDetailViewModel,
    padding: PaddingValues
) {
    val movieDetails = viewModel.uiState.collectAsState()
    Box {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = Color(0xFF1A1A1A))
                .padding(bottom = 12.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            movieDetails.value.movieDetails?.let {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .padding(top = 40.dp)
                        .shadow(elevation = 20.dp, ambientColor = Color.White, spotColor = Color.Blue)
                ) {
                    AsyncImage(
                        model = it.Poster,
                        error = painterResource(id = R.drawable.movie_placeholder),
                        contentDescription = it.Title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(250.dp, 350.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Divider(
                    thickness = 1.dp,
                    color = Color(0xFF8F8F8F),
                    modifier = Modifier.padding(horizontal = 80.dp)
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = it.Title ?: "",
                    color = Color.White,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Yayınlanma Tarihi: ${it.Year}", color = Color.White, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(32.dp))
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .align(alignment = Alignment.Start),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Oyuncular: ${it.Actors}",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = "Yönetmen: ${it.Director}",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = "Yazar: ${it.Writer}",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = "IMDB: ${it.imdbRating} ",
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Left
                    )
                }
            }
        }

        if (movieDetails.value.errorMessage.isNotEmpty()) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.align(Alignment.Center)){
                Text(text = "Error: ${movieDetails.value.errorMessage}", fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 16.sp)
            }
        }

        if (movieDetails.value.isLoading) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.align(Alignment.Center)) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.White)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Film Detayları alınıyor...",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(text = "Film Detayları", color = Color.White, fontSize = 24.sp)
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "geri dön",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF090909),
        ),
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(state = rememberTopAppBarState())
    )
}
