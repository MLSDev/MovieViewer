package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_holder_horizontal_list.view.*

class GeneralMovieTabViewHolder(
        itemView: View,
        private val seeAllClickSubject: PublishSubject<Int>,
        private val movieClickSubject: PublishSubject<Int>)
    : BaseViewHolder<ArrayList<Movie>>(itemView) {

    private val title: TextView = itemView.horizontalListTitle
    private val list: RecyclerView = itemView.horizontalList
    private val progressBar: ProgressBar = itemView.horizontalListProgressBar
    private val seeAll: TextView = itemView.seeAll

    override fun bind(item: ArrayList<Movie>, position: Int) {
        super.bind(item, position)

        if (item.isEmpty()) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
            title.text = when (position) {
                POPULAR_MOVIES -> itemView.context.getString(R.string.popular_movies)
                TOP_RATED_MOVIES -> itemView.context.getString(R.string.top_rated_movies)
                UPCOMING_MOVIES -> itemView.context.getString(R.string.upcoming_movies)
                else -> ""
            }

            list.apply {
                layoutManager = LinearLayoutManager(horizontalList.context, LinearLayout.HORIZONTAL, false)
                val moviesAdapter = MovieTabAdapter(item)
                moviesAdapter.movieClickEvent.subscribe { movieClickSubject.onNext(it) }
                moviesAdapter.type = position
                adapter = moviesAdapter

            }
        }

        seeAll.setOnClickListener { seeAllClickSubject.onNext(position) }
    }
}