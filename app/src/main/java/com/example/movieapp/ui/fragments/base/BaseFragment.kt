package com.example.movieapp.ui.fragments.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.movieapp.ui.activities.MainActivity
import com.example.movieapp.util.PreferencesUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
abstract class BaseFragment(layout: Int): Fragment(layout) {

    open fun configureObservers(baseViewModel: BaseViewModel) {
        baseViewModel.apply {
            error.observe(viewLifecycleOwner) {
                it?.apply {
                    Log.d("basefragment", "configureObservers: ")
                    }
                }
            }
        }
}
