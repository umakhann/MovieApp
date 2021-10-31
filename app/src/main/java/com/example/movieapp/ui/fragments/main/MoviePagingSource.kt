package com.example.movieapp.ui.fragments.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.network.services.Service
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MoviePagingSource constructor(val service: Service, val query: String) : PagingSource<Int, Movie>() {

private val TAG = "photopagingsource"
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1

        return try{
            val movies = service.getBySearch(query, position).body()
            if(movies?.search == null) movies?.search = listOf<Movie>()
            LoadResult.Page(
                data = movies!!.search,
                prevKey = if(position == 1) null else (position-1),
                nextKey = if(movies.search.isEmpty()) null else (position+1)
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}