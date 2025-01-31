package com.example.apicatdog.ui.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import com.example.apicatdog.data.CatImage
import com.example.apicatdog.network.CatApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.apicatdog.model.ApiCatDog
import kotlinx.coroutines.flow.asStateFlow


sealed interface GameUiStateCat {
    data object Loading : GameUiStateCat
    data class Error(val message: String) : GameUiStateCat
    data class Success(val images: List<CatImage>) : GameUiStateCat
}

data class CatViewState(
    val uiState: GameUiStateCat = GameUiStateCat.Loading,
    val images: List<CatImage> = emptyList(),
    val errorMessage: String? = null
)


class CatViewModel(private val catApiService: CatApiService) : ViewModel() {
    private val _catViewState = MutableStateFlow(CatViewState(uiState = GameUiStateCat.Loading))
    val catViewState: StateFlow<CatViewState> = _catViewState.asStateFlow()

    private var currentPage = 0
    private var isLoadingMore = false

    fun fetchCatImages() {
        viewModelScope.launch {
            if (isLoadingMore) return@launch
            isLoadingMore = true
            try {
                val newImages = catApiService.getCatImages(limit = 50, page = currentPage)
                val allImages = _catViewState.value.images + newImages
                _catViewState.value = CatViewState(
                    uiState = GameUiStateCat.Success(allImages),
                    images = allImages
                )
                Log.d("CatViewModel", "Fetched ${newImages.size} new images, total images: ${allImages.size}")
                currentPage++
            } catch (e: Exception) {
                _catViewState.value = CatViewState(
                    uiState = GameUiStateCat.Error("Error al cargar las imágenes"),
                    errorMessage = e.message
                )
                Log.e("CatViewModel", "Error fetching images", e)
            } finally {
                isLoadingMore = false
            }
        }
    }

    fun initialFetchCatImages() {
        viewModelScope.launch {
            _catViewState.value = CatViewState(uiState = GameUiStateCat.Loading)
            try {
                val initialImages = mutableListOf<CatImage>()
                var newImages: List<CatImage>
                do {
                    newImages = catApiService.getCatImages(limit = 50, page = currentPage)
                    initialImages.addAll(newImages)
                    currentPage++
                } while (newImages.isNotEmpty() && initialImages.size < 100)  // Cargamos lotes hasta tener al menos 100 imágenes o no haya más imágenes
                _catViewState.value = CatViewState(
                    uiState = GameUiStateCat.Success(initialImages),
                    images = initialImages
                )
                Log.d("CatViewModel", "Fetched ${initialImages.size} initial images")
            } catch (e: Exception) {
                _catViewState.value = CatViewState(
                    uiState = GameUiStateCat.Error("Error al cargar las imágenes"),
                    errorMessage = e.message
                )
                Log.e("CatViewModel", "Error fetching initial images", e)
            }
        }
    }

    fun fetchCatDetails(catId: String) {
        viewModelScope.launch {
            _catViewState.value = CatViewState(uiState = GameUiStateCat.Loading)
            try {
                val catDetail = catApiService.getCatDetails(catId)
                _catViewState.value = CatViewState(
                    uiState = GameUiStateCat.Success(listOf(catDetail)),
                    images = listOf(catDetail)
                )
                Log.d("CatViewModel", "Fetched cat details: $catDetail")
            } catch (e: Exception) {
                _catViewState.value = CatViewState(
                    uiState = GameUiStateCat.Error("Error al cargar los detalles del gato"),
                    errorMessage = e.message
                )
                Log.e("CatViewModel", "Error fetching cat details", e)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ApiCatDog
                val catApiService = application.catApiService
                CatViewModel(catApiService)
            }
        }
    }
}
