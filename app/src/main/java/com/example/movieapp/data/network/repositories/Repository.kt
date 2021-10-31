package com.example.movieapp.data.network.repositories

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.SearchObject
import com.example.movieapp.data.network.ResponseState
import com.example.movieapp.data.network.services.Service
import com.example.movieapp.ui.fragments.main.MoviePagingSource
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(val service: Service): BaseRepository(){

    val TAG = "repositorytag"

    suspend fun getMovie(imdbId: String): ResponseState {
        return try {
            getResponseState(service.getMovie(imdbId))
        } catch (e: Exception){
            ResponseState.NetworkException(e.message)
        }

//        val a = service.getMovie(imdbId)
//        Log.d(TAG, "getMovie: ${a.director}")
//        return a
    }

    fun getBySearch(text: String, page: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),

            pagingSourceFactory = { MoviePagingSource(service, text) }
        ).flow
}