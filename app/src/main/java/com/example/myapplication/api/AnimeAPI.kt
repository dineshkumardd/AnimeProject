package com.example.myapplication.api

import com.example.myapplication.models.Anime
import com.example.myapplication.models.AnimeData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeAPI {

    @GET("top/anime")
    suspend fun getAnimeList():Response<Anime>

    @GET("anime/{anime_id}")
    suspend fun getAnimeDetails(
        @Path("anime_id") animeId:Int = 1
    ):Response<AnimeData>
}