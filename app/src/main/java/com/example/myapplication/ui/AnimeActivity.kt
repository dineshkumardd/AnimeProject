package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.repository.AnimeRepository
import com.example.myapplication.ui.viewmodels.AnimeViewModel
import com.example.myapplication.ui.viewmodels.AnimeViewModelProviderFactory

class AnimeActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    lateinit var viewModel:AnimeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animeRepository = AnimeRepository()
        val viewModelProviderFactory = AnimeViewModelProviderFactory(application,animeRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(AnimeViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}