package com.example.myapplication.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.AnimeRepository

class AnimeViewModelProviderFactory(val app:Application, val animeRepository: AnimeRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnimeViewModel(app,animeRepository) as T
    }
}