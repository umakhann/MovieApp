package com.example.movieapp.ui.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMainBinding
import com.example.movieapp.ui.fragments.adapters.MovieLoadStateAdapter
import com.example.movieapp.ui.fragments.adapters.PagingAdapter
import com.example.movieapp.ui.fragments.adapters.SearchResultAdapter
import com.example.movieapp.ui.fragments.base.BaseFragment
import com.example.movieapp.ui.fragments.details.DbViewModel
import com.example.movieapp.util.Constants.Companion.LIGHT
import com.example.movieapp.util.Constants.Companion.NIGHT
import com.example.movieapp.util.PreferencesUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main), PagingAdapter.ClickEvent {

    val TAG = "mainfragmenttag"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainViewModel: MainViewModel by viewModels()
        val dbViewModel: DbViewModel by viewModels()
        val binding = FragmentMainBinding.bind(view)

        mainViewModel.getMovie("tt1285016").observe(viewLifecycleOwner) { movie ->
            Log.d(TAG, "${movie.director}")
        }

        val adapter = PagingAdapter(this@MainFragment)
        var adapterForDb: SearchResultAdapter? = null

        binding.apply {
            dbViewModel.getBookmarks().observe(this@MainFragment){ movies ->
                adapterForDb = SearchResultAdapter(movies, this@MainFragment)
                booksRec.adapter = adapterForDb
            }

            progressBar.visibility = View.GONE
            scrollview.visibility = View.VISIBLE

            searchCard.setOnClickListener {
                searchBar.requestFocus()
            }

            if(PreferencesUtil.nightMode == NIGHT) modeSwitch.isChecked = true
            modeSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    PreferencesUtil.nightMode = NIGHT
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    PreferencesUtil.nightMode = LIGHT
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

            searchBar.doAfterTextChanged {
                if(searchBar.text.length > 2) {
                    Log.d(TAG, "Text size is over 2")
                    mainViewModel.movies = mainViewModel.getBySearch(searchBar.text.toString(), 1)
                    Log.d(TAG, "${mainViewModel.movies}")
                    lifecycleScope.launchWhenCreated {
                        mainViewModel.movies.buffer().collect {
                            booksRec.adapter = adapter
                                .withLoadStateHeaderAndFooter(
                                    footer = MovieLoadStateAdapter { adapter.retry() },
                                    header = MovieLoadStateAdapter { adapter.retry() }
                                )

                            adapter.submitData(lifecycle, it)
                        }
                    }
                } else
                    booksRec.adapter = adapterForDb
            }
        }
    }

    override fun clicked(imdbId: String) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment3(imdbId)
        findNavController().navigate(action)
    }
}