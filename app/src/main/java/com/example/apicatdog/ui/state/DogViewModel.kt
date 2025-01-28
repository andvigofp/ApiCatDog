package com.example.apicatdog.ui.state

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

class DogViewModel(private val dogApiService: DogApiService) : ViewModel() {
    private val _dogImages = MutableStateFlow<List<DogImage>>(emptyList())
    val dogImages: StateFlow<List<DogImage>> = _dogImages

    private var currentPage = 0

    init {
        fetchDogImages()
    }

    fun fetchDogImages() {
        viewModelScope.launch {
            try {
                val newImages = dogApiService.getDogImages(limit = 50, page = currentPage)
                _dogImages.value = _dogImages.value + newImages
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
                val dogApiService = application.dogApiService
                DogViewModel(dogApiService)
            }
        }
    }
}
