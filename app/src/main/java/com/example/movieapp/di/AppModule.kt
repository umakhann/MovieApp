package com.example.movieapp.di

import android.app.Application
import androidx.room.Room
import com.example.movieapp.BuildConfig
import com.example.movieapp.data.database.MovieDatabase
import com.example.movieapp.data.network.services.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesClient(): Service =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Service::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: MovieDatabase.Callback
    ) = Room.databaseBuilder(app, MovieDatabase::class.java, "movies")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideDao(db: MovieDatabase) = db.dao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())


}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope