package com.example.apicatdog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apicatdog.ui.screen.CatDetailScreen
import com.example.apicatdog.ui.screen.CatImageScreen
import com.example.apicatdog.ui.screen.DogDetailScreen
import com.example.apicatdog.ui.screen.DogImageScreen
import com.example.apicatdog.ui.screen.HomeScreen
import com.example.apicatdog.ui.state.CatViewModel
import com.example.apicatdog.ui.state.DogViewModel

@Composable
fun NavGraph(startDestination: String = "home") {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = "home") { HomeScreen(navController) }
        composable(route = "cats") { CatImageScreen(navController) }
        composable(route = "dogs") { DogImageScreen(navController) }
        composable(route = "cat_detail/{catId}") { backStackEntry ->
            val catId = backStackEntry.arguments?.getString("catId") ?: ""
            val factory = CatViewModel.Factory
            val catViewModel: CatViewModel = viewModel(factory = factory)
            CatDetailScreen(navController, catId = catId, catViewModel = catViewModel)
        }
        composable(route = "dog_detail/{dogId}") { backStackEntry ->
            val dogId = backStackEntry.arguments?.getString("dogId") ?: ""
            val factory = DogViewModel.Factory
            val dogViewModel: DogViewModel = viewModel(factory = factory)
            DogDetailScreen(navController, dogId = dogId, dogViewModel = dogViewModel)
        }
    }
}







