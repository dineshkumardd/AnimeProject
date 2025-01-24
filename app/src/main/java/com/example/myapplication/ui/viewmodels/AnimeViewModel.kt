package com.example.myapplication.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Anime
import com.example.myapplication.models.AnimeData
import com.example.myapplication.repository.AnimeRepository
import com.example.myapplication.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class AnimeViewModel(app:Application, val animeRepository:AnimeRepository) :AndroidViewModel(app) {

    val animeList:MutableLiveData<Resource<Anime>> = MutableLiveData()

    val animeDetail:MutableLiveData<Resource<AnimeData>> = MutableLiveData()

    init {
        getAllAnime()
    }

    fun getAllAnime()=viewModelScope.launch {
        allAnimeCall()
    }

    fun getAnimeDetail(id:Int)=viewModelScope.launch {
        animeDetailCall(id)
    }

    suspend fun allAnimeCall(){
        animeList.postValue(Resource.Loading())
        try {
            val response = animeRepository.getAllAnime()
            animeList.postValue(handleAnimeListResponse(response))
        }catch (t:Throwable){
            when(t){
                is IOException ->animeList.postValue(Resource.Error("Network Failure"))
            }
        }
    }

    suspend fun animeDetailCall(id: Int) {
        animeDetail.postValue(Resource.Loading())
        try {
            val response = animeRepository.getSingleAnime(id)
            animeDetail.postValue(handleAnimeDetailResponse(response))
        }catch (t:Throwable){
            when(t){
                is IOException ->animeDetail.postValue(Resource.Error("Network Failure"))
            }
        }
    }

    private fun handleAnimeListResponse(response:Response<Anime>):Resource<Anime>{
        if (response.isSuccessful){
            response.body()?.let {resultResponse->

                return Resource.Success(resultResponse)

            }
        }
        return Resource.Error(response.message())
    }

    private fun handleAnimeDetailResponse(response:Response<AnimeData>):Resource<AnimeData>{
        if (response.isSuccessful){
            response.body()?.let {resultResponse->
                return Resource.Success(resultResponse)

            }
        }
        return Resource.Error(response.message())
    }
}