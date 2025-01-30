package com.example.apicatdog.ui.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import com.example.apicatdog.data.DogImage
import com.example.apicatdog.network.DogApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.apicatdog.model.ApiCatDog
import kotlinx.coroutines.flow.asStateFlow

sealed interface GameUiStateDog {
    data object Loading : GameUiStateDog
    data class Error(val message: String) : GameUiStateDog
    data class Success(val images: List<DogImage>) : GameUiStateDog
}

data class DogViewState(
    val uiState: GameUiStateDog = GameUiStateDog.Loading,
    val images: List<DogImage> = emptyList(),
    val errorMessage: String? = null
)


class DogViewModel(private val dogApiService: DogApiService) : ViewModel() {
    private val _dogViewState = MutableStateFlow(DogViewState(uiState = GameUiStateDog.Loading))
    val dogViewState: StateFlow<DogViewState> = _dogViewState.asStateFlow()

    private var currentPage = 0
    private var isLoadingMore = false

    fun fetchDogImages() {
        viewModelScope.launch {
            if (isLoadingMore) return@launch
            isLoadingMore = true
            try {
                val newImages = dogApiService.getDogImages(limit = 50, page = currentPage)
                val allImages = _dogViewState.value.images + newImages
                _dogViewState.value = DogViewState(
                    uiState = GameUiStateDog.Success(allImages),
                    images = allImages
                )
                Log.d(
                    "DogViewModel",
                    "Fetched ${newImages.size} new images, total images: ${allImages.size}"
                )
                currentPage++
            } catch (e: Exception) {
                _dogViewState.value = DogViewState(
                    uiState = GameUiStateDog.Error("Error al cargar las imágenes"),
                    errorMessage = e.message
                )
                Log.e("DogViewModel", "Error fetching images", e)
            } finally {
                isLoadingMore = false
            }
        }
    }

    fun fetchDogDetails(dogId: String) {
        viewModelScope.launch {
            _dogViewState.value = DogViewState(uiState = GameUiStateDog.Loading)
            try {
                val dogDetail = dogApiService.getDogDetails(dogId)
                _dogViewState.value = DogViewState(
                    uiState = GameUiStateDog.Success(listOf(dogDetail)),
                    images = listOf(dogDetail)
                )
                Log.d("DogViewModel", "Fetched dog details: $dogDetail")
            } catch (e: Exception) {
                _dogViewState.value = DogViewState(
                    uiState = GameUiStateDog.Error("Error al cargar los detalles del perro"),
                    errorMessage = e.message
                )
                Log.e("DogViewModel", "Error fetching dog details", e)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ApiCatDog
                val dogApiService = application.dogApiService
                DogViewModel(dogApiService)
            }
        }

    }
}