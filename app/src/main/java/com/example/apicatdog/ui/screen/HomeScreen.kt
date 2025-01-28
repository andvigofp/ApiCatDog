package com.example.apicatdog.ui.screen

import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apicatdog.ui.screen.CatImageScreen
import com.example.apicatdog.ui.screen.DogImageScreen



@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("cats") }) {
            Text(text = "Ver imágenes de gatos")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("dogs") }) {
            Text(text = "Ver imágenes de perros")
        }
    }
}
