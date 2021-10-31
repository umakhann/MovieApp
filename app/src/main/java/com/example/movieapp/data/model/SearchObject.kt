package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class SearchObject(val totalResults: String? = null,
                        @SerializedName("Error") val error: String? = null,
                        @SerializedName("Response") val response: String,
                        @SerializedName("Search") var search: List<Movie> = listOf<Movie>()
)