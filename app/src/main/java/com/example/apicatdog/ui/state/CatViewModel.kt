package com.example.apicatdog.ui.state

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

class CatViewModel(private val catApiService: CatApiService) : ViewModel() {
    private val _catImages = MutableStateFlow<List<CatImage>>(emptyList())
    val catImages: StateFlow<List<CatImage>> = _catImages

    private var currentPage = 0

    init {
        fetchCatImages()
    }

    fun fetchCatImages() {
        viewModelScope.launch {
            try {
                val newImages = catApiService.getCatImages(limit = 50, page = currentPage)
                _catImages.value = _catImages.value + newImages
                currentPage++
            } catch (e: Exception) {
                // Manejar la excepción aquí
                e.printStackTrace()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ApiCatDog)
                val catApiService = application.catApiService
                CatViewModel(catApiService)
            }
        }
    }
}




