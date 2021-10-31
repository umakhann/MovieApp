package com.example.movieapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.movieapp.data.model.Movie

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("select * from movies")
    fun getBookmarks() : LiveData<List<Movie>>

    @Query("SELECT EXISTS(SELECT * FROM movies WHERE imdbID = :id)")
    fun movieExists(id: String): LiveData<Boolean>

}