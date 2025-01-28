package com.example.apicatdog.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import androidx.navigation.NavController
import com.example.apicatdog.ui.state.DogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogImageScreen(navController: NavController, dogViewModel: DogViewModel = viewModel(factory = DogViewModel.Factory)) {
    val dogImages by dogViewModel.dogImages.collectAsState()

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
        content = {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp), // Usar GridCells.Adaptive
                modifier = Modifier.padding(it) // Padding to avoid overlap with TopAppBar
            ) {
                items(dogImages) { dogImage ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .padding(8.dp)
                            .border(2.dp, Color.Black) // Añadir un borde gris
                            .background(Color.Gray) // Añadir un fondo blanco
                    ) {
                        Image(
                            painter = rememberImagePainter(dogImage.url),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
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
    )
}
