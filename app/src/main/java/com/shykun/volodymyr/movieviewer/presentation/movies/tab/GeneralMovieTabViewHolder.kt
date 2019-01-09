package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_holder_horizontal_list.view.*

class GeneralMovieTabViewHolder(itemView: View, clickSubject: PublishSubject<ArrayList<Movie>>)
    : BaseViewHolder<ArrayList<Movie>>(itemView, clickSubject) {

    private val title: TextView = itemView.horizontalListTitle
    private val list: RecyclerView = itemView.horizontalList
    private val progressBar: ProgressBar = itemView.horizontalListProgressBar

    override fun bind(item: ArrayList<Movie>, position: Int) {
        super.bind(item, position)

        if (item.isEmpty()) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
            title.text = when(position) {
                POPULAR_MOVIES -> "Popular movies"
                TOP_RATED_MOVIES -> "Top rated movies"
                UPCOMING_MOVIES -> "Upcoming movies"
                else -> ""
            }
            list.apply {
                layoutManager = LinearLayoutManager(horizontalList.context, LinearLayout.HORIZONTAL, false)
                val moviesAdapter = MovieTabAdapter(item)
                moviesAdapter.type = position
                adapter = moviesAdapter

            }
        }
    }
}