package com.example.movieapp.ui.fragments.details

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.ui.fragments.base.BaseFragment
import com.example.movieapp.ui.fragments.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment: BaseFragment(R.layout.fragment_details) {

    var exists = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)
        val mainViewModel: MainViewModel by viewModels()
        val dbViewModel: DbViewModel by viewModels()



        binding.apply {
            mainViewModel.getMovie(imdbId = arguments?.getString("imdbId")!!).observe(this@DetailsFragment){ movie ->
                movie.poster?.let { Picasso.get().load(it).fit().centerCrop().into(moreBookImage)}
                movie.director?.let { director.text = "by $it" }
                movie.plot?.let { description.text = it
                                    descriptionRecHeader.visibility = View.VISIBLE}
                movie.title?.let { moreTitle.text = it }
                movie.released?.let { date.text = "Release Date:\n$it" }
                movie.runtime?.let { runningTime.text = "Running Time:\n$it" }
                movie.genre?.let { genre.text = "Genre:\n$it" }
                movie.boxOffice?.let { boxOffice.text = "Box Office:\n$it" }
                movie.imdbRating?.let { imdb.text = "$it/10"
                                    charactersList.visibility = View.VISIBLE}
                movie.actors?.let { actors.text = "Starring: $it" }
                movie.writer?.let { writers.text = "Writers: $it" }
                movie.awards?.let {
                    if(it.isEmpty())
                    awards.text = "Awards: No award"
                    else awards.text = "Awards: $it"}

                dbViewModel.movieExists(movie.imdbID).observe(this@DetailsFragment){
                    exists = it
                }

                bookmark.setOnClickListener{
                    if(!exists){
                        dbViewModel.insert(movie)
                        Snackbar.make(binding.root, "Successfully added", 1000).show()
                    }
                    else {
                        AlertDialog.Builder(context)
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes) { _, _ ->
                                dbViewModel.delete(movie)
                            }
                            .setNegativeButton(android.R.string.no, null)
//                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }
                }

                detailsBack.setOnClickListener{
                    findNavController().navigateUp()
                }


                progressBar.visibility = View.GONE
            }
        }
    }
}