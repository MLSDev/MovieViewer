package com.shykun.volodymyr.movieviewer.presentation.movies.search

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.databinding.ItemSearchMovieBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MovieSearchAdapter(movies: ArrayList<Movie>) : BaseRecyclerViewAdapter<Movie, MovieSearchViewHolder>(movies) {

    private val clickSubject = PublishSubject.create<Int>()
    val clickObservable: Observable<Int> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSearchMovieBinding>(
                inflater,
                R.layout.item_search_movie,
                parent,
                false)

        return MovieSearchViewHolder(binding, clickSubject)
    }

    fun setItems(movies: List<Movie>) {
        this.items.clear()
        this.items.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }
}