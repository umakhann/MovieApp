package com.example.movieapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movies")
@Parcelize
data class Movie(@SerializedName("Title") val title: String? = null,
                 @SerializedName("Year") val year: String? = null,
                 @SerializedName("Released") val released: String? = null,
                 @SerializedName("Runtime") val runtime: String? = null,
                 @SerializedName("Genre") val genre: String? = null,
                 @SerializedName("Director") val director: String? = null,
                 @SerializedName("Writer") val writer: String? = null,
                 @SerializedName("Actors") val actors: String? = null,
                 @SerializedName("Plot") val plot: String? = null,
                 @SerializedName("Language") val language: String? = null,
                 @SerializedName("Country") val country: String? = null,
                 @SerializedName("Awards") val awards: String? = null,
                 @SerializedName("Poster") val poster: String? = null,
                 val imdbRating: String? = null,
                 @PrimaryKey val imdbID: String,
                 @SerializedName("BoxOffice") val boxOffice: String? = null,
                 @SerializedName("Response") val response: String? = null,
                 @SerializedName("Type") val type: String? = null,

                 ): Parcelable