package com.example.movieapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.util.Constants.Companion.LIGHT
import com.example.movieapp.util.Constants.Companion.NIGHT
import com.example.movieapp.util.PreferencesUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.findNavController()

        AppCompatDelegate.setDefaultNightMode(
            when (PreferencesUtil.nightMode) {
                NIGHT -> {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
                LIGHT -> {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                else -> {
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
            }
        )
//        window.setWindowAnimations(R.style.RecreateStyle)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }
}