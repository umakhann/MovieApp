package com.example.movieapp.ui.fragments.base

import android.app.Application
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.movieapp.data.network.ResponseState
import com.example.movieapp.util.PreferencesUtil
import com.example.movieapp.util.hasConnection
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    open val TAG = "baseviewmodeltag"

    protected val _error = MutableLiveData<Pair<String, String>?>()
    val error: LiveData<Pair<String, String>?> = _error

    val handler = CoroutineExceptionHandler { _, throwable ->
        val unexpectedErrorMessage = "Unexpected error"
        _error.value = "dialog_error" to (throwable.message
            ?: unexpectedErrorMessage)
        _error.value = null

        Log.d(TAG, "exc: ${throwable.message}")
    }



    @RequiresApi(Build.VERSION_CODES.M)
    fun makeRequest(
        sentRequest: suspend () -> ResponseState,
        onSuccess: suspend (ResponseState.Success<*>) -> Unit,
        onFailure: suspend (String, String?) -> Unit = { _, _ -> },
        showLoadingDialog: Boolean = true
    ) {
        viewModelScope.launch(handler) {
            Log.d(TAG, "makeRequest: in launch")
            withContext(Dispatchers.IO) {
                if (hasConnection(getApplication())) {
                    Log.d(TAG, "makeRequest: in hasconnection")


                    when (val response = sentRequest()) {

                        is ResponseState.Success<*> -> {
                            withContext(Dispatchers.Main) {
                                delay(500)
                                onSuccess(response)
                                Log.d(TAG, "place itself")

                            }
                        }

                        // Api key is incorrect or another error
                        is ResponseState.BadRequest -> {
                            Log.d(TAG, "in badrequest, unfortunately")

                            withContext(Dispatchers.Main) {
                                onFailure(
                                    "Exception",
                                    response.exception
                                )
                            }
                        }

                        is ResponseState.NetworkException -> {
                            Log.d(TAG, "makeRequest: in networkexception")

                            Snackbar.make(getApplication(), "No network", 1000).show()
                        }
                    }
                } else {
//                    withContext(Dispatchers.Main) {
//                        onFailure(getString(R.string.msg_no_internet_connection), null)
//                    }
//                    handleMessage(DIALOG_ERROR to getString(R.string.msg_no_internet_connection))
                }
            }
        }
    }
}