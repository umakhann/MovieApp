package com.example.movieapp.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.ItemBookBinding
import com.squareup.picasso.Picasso

class PagingAdapter(private val listener: ClickEvent): PagingDataAdapter<Movie, PagingAdapter.ViewHolder>(comparator) {

    val TAG = "adaptertag"

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let{
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)

        return ViewHolder(binding)
    }



    inner class ViewHolder(private val binding: ItemBookBinding):
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener{

        fun bind(item: Movie){
            binding.apply {
                Picasso.get()
                    .load(item.poster)
                    .fit()
                    .centerCrop()
                    .into(imageOfBook)

                titleOfBook.text = item.title

            }
            itemView.setOnClickListener(this@ViewHolder)
        }

        override fun onClick(p0: View?) {
            listener.clicked(getItem(adapterPosition)!!.imdbID!!)
        }
    }


    companion object{
        val comparator = object: DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface ClickEvent {
        fun clicked(imdbId: String)
    }


}