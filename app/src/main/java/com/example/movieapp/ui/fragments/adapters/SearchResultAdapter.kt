package com.example.movieapp.ui.fragments.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.ItemBookBinding
import com.squareup.picasso.Picasso

class SearchResultAdapter(private val list: List<Movie>, val listener: PagingAdapter.ClickEvent): RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultAdapter.ViewHolder {
        val view = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultAdapter.ViewHolder, position: Int) {
        val movie = list[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root),
                                                            View.OnClickListener{

        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie){
            binding.apply {
                movie.poster?.let {
                    Log.d("viewholdertag", "bind: $it")
                    Picasso.get().load(it).fit().centerCrop().into(imageOfBook)
                }
                movie.title?.let {
                    Log.d("viewholdertag", "bind: $it")

                    if(movie.year?.length != 5)
                    titleOfBook.text = "$it (${movie.year})"
                    else titleOfBook.text = "$it (${movie.year}present)" }

                itemView.setOnClickListener(this@ViewHolder)
                }
            }

        override fun onClick(p0: View?) {
            listener.clicked(list[adapterPosition].imdbID)
        }
    }
    }