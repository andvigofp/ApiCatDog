package com.example.apicatdog.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import androidx.navigation.NavController
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.example.apicatdog.ui.state.CatViewModel
import com.example.apicatdog.ui.state.GameUiStateCat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatImageScreen(navController: NavController, catViewModel: CatViewModel = viewModel(factory = CatViewModel.Factory)) {
    val catViewState by catViewModel.catViewState.collectAsState()

    LaunchedEffect(Unit) {
        catViewModel.fetchCatImages()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Imágenes de Gatos") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        content = { paddingValues: PaddingValues ->
            when (val state = catViewState.uiState) {
                is GameUiStateCat.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is GameUiStateCat.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.message)
                    }
                }
                is GameUiStateCat.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 150.dp),
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        items(state.images) { catImage ->
                            Box(
                                modifier = Modifier
                                    .size(150.dp)
                                    .padding(8.dp)
                                    .border(2.dp, Color.Black)
                                    .background(Color.Gray)
                                    .clickable {
                                        navController.navigate("cat_detail/${catImage.id}")
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = catImage.url,
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
                                catViewModel.fetchCatImages()
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
