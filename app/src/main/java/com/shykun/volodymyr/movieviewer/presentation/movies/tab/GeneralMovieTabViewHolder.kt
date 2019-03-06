package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalMovieListBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.common.adapters.HorizontalListAdapter
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalItem
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_horizontal_movie_list.view.*

class GeneralMovieTabViewHolder(
        private val binding: ItemHorizontalMovieListBinding,
        private val seeAllClickSubject: PublishSubject<Int>,
        private val movieClickSubject: PublishSubject<Int>) : BaseViewHolder<ArrayList<HorizontalItem>>(binding) {

    private val movieList: RecyclerView = itemView.horizontalMovieList
    private val seeAllMovie: TextView = itemView.seeAllMovie

    lateinit var title: String
    lateinit var moviesType: MoviesType
    var progressBarVisibility = View.VISIBLE

    override fun bind(item: ArrayList<HorizontalItem>?) {
        super.bind(item)

        when (adapterPosition) {
            POPULAR_MOVIES -> {
                title = itemView.context.getString(R.string.popular_movies)
                moviesType = MoviesType.POPULAR
            }
            TOP_RATED_MOVIES -> {
                title = itemView.context.getString(R.string.top_rated_movies)
                moviesType = MoviesType.TOP_RATED
            }
            UPCOMING_MOVIES -> {
                title = itemView.context.getString(R.string.upcoming_movies)
                moviesType = MoviesType.UPCOMING
            }
        }

        if (item != null && item.isNotEmpty()) {
            progressBarVisibility = View.GONE

            movieList.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayout.HORIZONTAL, false)
                val moviesAdapter = HorizontalListAdapter()
                moviesAdapter.addItems(item)
                moviesAdapter.clickObservable.subscribe { movieClickSubject.onNext(it.id) }
                adapter = moviesAdapter
            }
        }

        seeAllMovie.setOnClickListener { seeAllClickSubject.onNext(adapterPosition) }

        executeBinding()
    }

    private fun executeBinding() {
        binding.generalMovieTabViewHolder = this
        binding.executePendingBindings()
    }
}