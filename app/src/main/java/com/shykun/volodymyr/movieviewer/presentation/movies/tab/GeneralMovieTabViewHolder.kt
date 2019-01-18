package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderHorizontalMovieListBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_holder_horizontal_movie_list.view.*

class GeneralMovieTabViewHolder(
        private val binding: ViewHolderHorizontalMovieListBinding,
        private val seeAllClickSubject: PublishSubject<Int>,
        private val movieClickSubject: PublishSubject<Int>) : BaseViewHolder<ArrayList<Movie>>(binding) {

    private val movieList: RecyclerView = itemView.horizontalMovieList
    private val seeAllMovie: TextView = itemView.seeAllMovie

    lateinit var title: String
    var progressBarVisibility = View.VISIBLE

    override fun bind(item: ArrayList<Movie>, position: Int) {
        super.bind(item, position)

        if (item.isNotEmpty()) {
            progressBarVisibility = View.GONE
            title = when (position) {
                POPULAR_MOVIES -> itemView.context.getString(R.string.popular_movies)
                TOP_RATED_MOVIES -> itemView.context.getString(R.string.top_rated_movies)
                UPCOMING_MOVIES -> itemView.context.getString(R.string.upcoming_movies)
                else -> ""
            }

            movieList.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayout.HORIZONTAL, false)
                val moviesAdapter = MovieTabAdapter(item)
                moviesAdapter.movieClickEvent.subscribe { movieClickSubject.onNext(it) }
                moviesAdapter.type = position
                adapter = moviesAdapter
            }
        }

        seeAllMovie.setOnClickListener { seeAllClickSubject.onNext(position) }

        executeBinding()
    }

    private fun executeBinding() {
        binding.generalMovieTabViewHolder = this
        binding.executePendingBindings()
    }
}