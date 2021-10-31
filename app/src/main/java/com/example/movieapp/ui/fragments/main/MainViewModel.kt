package com.example.movieapp.ui.fragments.main

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.network.repositories.Repository
import com.example.movieapp.ui.fragments.base.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository, application: Application): BaseViewModel(application) {

    private var movie: MutableLiveData<Movie> = MutableLiveData()
    var movies: Flow<PagingData<Movie>> = MutableSharedFlow()
    override val TAG = "mainviewmodeltag"


    @RequiresApi(Build.VERSION_CODES.M)
    fun getMovie(imdbId: String): LiveData<Movie> {
        Log.d(TAG, "getMovie: going to request")
        makeRequest(
            sentRequest = { repository.getMovie(imdbId) },
            onSuccess = {
                val data = it.data as Movie
                Log.d(TAG, "${it.data.actors}")
                movie.value = data
                Log.d(TAG, "${movie.value?.boxOffice}")
            },
            onFailure = { error, exception ->
                Snackbar.make(getApplication(), "No network", 1000).show()
            }
        )

        return movie
    }



    fun getBySearch(text: String, page: Int): Flow<PagingData<Movie>> {
            movies = repository.getBySearch(text.replace(" ", "+"), 0).cachedIn(viewModelScope)

        Log.d(TAG, "getBySearch:")
        return movies
    }
}