package com.example.myapplication.models

import java.io.Serializable

data class Anime(
    val `data`: MutableList<Data>,
    val pagination: Pagination
):Serializable