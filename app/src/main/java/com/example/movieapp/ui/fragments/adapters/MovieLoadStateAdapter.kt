package com.example.movieapp.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.LoadStateBinding

class MovieLoadStateAdapter(val retry: () -> Unit) :
            LoadStateAdapter<MovieLoadStateAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = LoadStateBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)

        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }



    inner class ViewHolder(private val binding: LoadStateBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
        }


        fun bind(loadState: LoadState){
            binding.apply{
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState !is LoadState.Loading
                noResult.isVisible = loadState !is LoadState.Loading

            }
        }
    }
}