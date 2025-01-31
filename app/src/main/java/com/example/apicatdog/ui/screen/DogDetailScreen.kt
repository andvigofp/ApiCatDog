package com.example.apicatdog.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.apicatdog.data.DogImage
import com.example.apicatdog.ui.state.DogViewModel
import com.example.apicatdog.ui.state.GameUiStateDog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogDetailScreen(navController: NavController, dogId: String, dogViewModel: DogViewModel = viewModel(factory = DogViewModel.Factory)) {
    val dogViewState by dogViewModel.dogViewState.collectAsState()
    var dogDetail by remember { mutableStateOf<DogImage?>(null) }
    var isImageExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(dogId) {
        dogViewModel.fetchDogDetails(dogId)
    }

    val state = dogViewState.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Perro") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        content = { paddingValues: PaddingValues ->
            when (state) {
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
                    dogDetail = state.images.firstOrNull()
                    dogDetail?.let { dog ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            if (isImageExpanded) {
                                Image(
                                    painter = rememberImagePainter(dog.url),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable { isImageExpanded = false }
                                )
                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Image(
                                        painter = rememberImagePainter(dog.url),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(200.dp)
                                            .clip(CircleShape)
                                            .clickable { isImageExpanded = true }
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = dog.breeds.firstOrNull()?.name ?: "Sin nombre",
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                    Text(
                                        text = dog.breeds.firstOrNull()?.bred_for ?: "Sin temperamento",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                    Text(
                                        text = dog.breeds.firstOrNull()?.breed_group ?: "Sin temperamento",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                    Text(
                                        text = dog.breeds.firstOrNull()?.life_span ?: "Sin temperamento",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                    Text(
                                        text = dog.breeds.firstOrNull()?.temperament ?: "Sin temperamento",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                else -> {
                    // Asegurarse de manejar todos los casos
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Estado desconocido")
                    }
                }
            }
        }
    )
}
