package com.example.apicatdog.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import androidx.navigation.NavController
import com.example.apicatdog.ui.state.DogViewModel
import com.example.apicatdog.ui.state.GameUiStateDog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogImageScreen(navController: NavController, dogViewModel: DogViewModel = viewModel(factory = DogViewModel.Factory)) {
    val dogViewState by dogViewModel.dogViewState.collectAsState()

    LaunchedEffect(Unit) {
        dogViewModel.fetchDogImages()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Imágenes de Perros") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        content = { paddingValues: PaddingValues ->
            when (val state = dogViewState.uiState) {
                is GameUiStateDog.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is GameUiStateDog.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.message)
                    }
                }
                is GameUiStateDog.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 150.dp),
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        items(state.images) { dogImage ->
                            Box(
                                modifier = Modifier
                                    .size(150.dp)
                                    .padding(8.dp)
                                    .border(2.dp, Color.Black)
                                    .background(Color.Gray)
                                    .clickable {
                                        navController.navigate("dog_detail/${dogImage.id}")
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = dogImage.url,
                                        builder = {
                                            crossfade(true)
                                        }
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape)
                                )
                            }
                        }
                        item {
                            LaunchedEffect(Unit) {
                                dogViewModel.fetchDogImages()
                            }
                        }
                    }
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Estado desconocido")
                    }
                }
            }
        }
    )
}
