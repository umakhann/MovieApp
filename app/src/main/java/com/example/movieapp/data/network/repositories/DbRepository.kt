package com.example.movieapp.data.network.repositories

import androidx.lifecycle.LiveData
import com.example.movieapp.data.database.Dao
import com.example.movieapp.data.model.Movie
import javax.inject.Inject

class DbRepository @Inject constructor(val dao: Dao) {

    suspend fun insert(movie: Movie){
        dao.insert(movie)
    }

    suspend fun delete(movie: Movie){
        dao.delete(movie)
    }

    fun getBookmarks(): LiveData<List<Movie>> = dao.getBookmarks()

    fun movieExists(imdbId: String) = dao.movieExists(imdbId)
}