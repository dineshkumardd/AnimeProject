package com.example.myapplication.models

import java.io.Serializable

data class Data(
    //val aired: Aired,
    //val airing: Boolean,
    //val approved: Boolean,
    //val background: String?,  // Made it nullable to handle empty background
    //val broadcast: Broadcast,
    //val demographics: List<Demographic>,
    //val duration: String?,
    val episodes: Int,
    //val explicit_genres: List<Any?>,  // Keep as is if uncertain, but refine if needed
    //val favorites: Int,
    val genres: MutableList<Genre>,
    val images: Images?,
    //val licensors: List<Licensor>,
    val mal_id: Int,
    //val members: Int,
    //val popularity: Int,
    //val producers: List<Producer>,
    //val rank: Int,
    val rating: String?,
    //val score: Double,
    //val scored_by: Int,
    //val season: String?,
    //val source: String?,
   // val status: String?,
    //val studios: List<Studio>,
    val synopsis: String?,
    //val themes: List<Theme>,
    val title: String?,
    //val title_english: String?,
    //val title_japanese: String?,
    //val title_synonyms: List<String>,
    //val titles: List<Title>,
    val trailer: Trailer,
    val type: String?,
    val url: String?,
    //val year: Int
):Serializable