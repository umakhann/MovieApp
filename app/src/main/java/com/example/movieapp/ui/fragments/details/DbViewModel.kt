package com.example.movieapp.ui.fragments.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.network.repositories.DbRepository
import com.example.movieapp.ui.fragments.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DbViewModel @Inject constructor(val dbRepository: DbRepository, application: Application): BaseViewModel(application){

    fun insert(movie: Movie){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.insert(movie)
        }
    }

    fun delete(movie: Movie){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.delete(movie)
        }
    }

    fun getBookmarks() = dbRepository.getBookmarks()

    fun movieExists(imdbId: String) = dbRepository.movieExists(imdbId)
}