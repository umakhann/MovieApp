package com.example.movieapp.data.network.repositories

import android.util.Log
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.SearchObject
import com.example.movieapp.data.network.ResponseState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {

    private val TAG = "baserepositorytag"

    protected fun <T> getResponseState(response: Response<T>?): ResponseState {

        return try {
            if (response?.isSuccessful == true) {
                Log.d(TAG, "getResponseState: right here in succesfull")
                if (response.body() != null) {
                    val data = response.body()
                    Log.d(TAG, "stateeee: ${data}")
                    when (data) {
                        is SearchObject -> {
                            when ((data as SearchObject).response) {
                                "True" -> {
                                    ResponseState.Success(data.search)

                                }
                                else -> ResponseState.BadRequest(data.error)
                            }
                        }

                        is Movie -> {
                            when ((data as Movie).response) {
                                "True" -> {
                                    ResponseState.Success(data)
                                }
                                else -> ResponseState.BadRequest("error")
                            }
                        }

                        else -> ResponseState.InvalidData
                    }
                } else {
                    ResponseState.InvalidData
                }
            } else {
                var errorResponse: SearchObject? = null

                try {
                    val gson = Gson()
                    val type = object : TypeToken<SearchObject>() {}.type
                    errorResponse = gson.fromJson(response?.errorBody()?.charStream(), type)
                } catch (e: Exception) {
                }
                when (response?.code()) {

                    else -> ResponseState.NetworkException(response?.message())
                }
            }
        } catch (e: IOException) {
            ResponseState.NetworkException(e.message!!)
        }
    }


}