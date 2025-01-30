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
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.apicatdog.data.CatImage
import com.example.apicatdog.ui.state.CatViewModel
import com.example.apicatdog.ui.state.GameUiStateCat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatDetailScreen(navController: NavController, catId: String, catViewModel: CatViewModel) {
    val catViewState by catViewModel.catViewState.collectAsState()
    var catDetail by remember { mutableStateOf<CatImage?>(null) }
    var isImageExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(catId) {
        catViewModel.fetchCatDetails(catId)
    }

    val state = catViewState.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Gato") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        content = { paddingValues: PaddingValues ->
            when (state) {
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
                    catDetail = state.images.firstOrNull()
                    catDetail?.let { cat ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            if (isImageExpanded) {
                                Image(
                                    painter = rememberImagePainter(cat.url),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable { isImageExpanded = false }
                                )
                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp, vertical = 8.dp), // Ajustar padding
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top // Colocar elementos en la parte superior
                                ) {
                                    Image(
                                        painter = rememberImagePainter(cat.url),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(200.dp)
                                            .clip(CircleShape)
                                            .clickable { isImageExpanded = true }
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = cat.breeds.firstOrNull()?.name ?: "Sin nombre",
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                    Text(
                                        text = cat.breeds.firstOrNull()?.description ?: cat.description ?: "Sin descripción",
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
