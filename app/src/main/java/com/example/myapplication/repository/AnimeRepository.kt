package com.example.myapplication.repository

import com.example.myapplication.api.RetrofitInstance

class AnimeRepository {

    suspend fun getAllAnime() = RetrofitInstance.api.getAnimeList()

    suspend fun getSingleAnime(animeId:Int) = RetrofitInstance.api.getAnimeDetails(animeId)
}