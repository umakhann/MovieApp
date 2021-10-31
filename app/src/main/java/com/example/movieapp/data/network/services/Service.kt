package com.example.movieapp.data.network.services

import com.example.movieapp.BuildConfig
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.SearchObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("?apikey=${BuildConfig.API_KEY}&plot=full")
    suspend fun getMovie(@Query("i") i: String): Response<Movie>

    @GET("?apikey=${BuildConfig.API_KEY}")
    suspend fun getBySearch(@Query("s") title: String,
                            @Query("page") page: Int): Response<SearchObject>


}