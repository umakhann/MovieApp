package com.example.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.model.Movie
import com.example.movieapp.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun dao(): Dao

    class Callback @Inject constructor(
        private val database: Provider<MovieDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}