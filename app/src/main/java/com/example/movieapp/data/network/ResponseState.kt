package com.example.movieapp.data.network

sealed class ResponseState {
    data class Success<T>(val data: T) : ResponseState()
    object InvalidData : ResponseState()
    data class BadRequest(val error: String?, val exception: String? = null) :
        ResponseState()

    data class NetworkException(val error: String?, val status: String? = null) : ResponseState()
    data class ValidationError(
        val status: String?,
        val error: Map<String, String>?,
        val exceptionData: String?
    ): ResponseState()


}