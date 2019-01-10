package com.shykun.volodymyr.movieviewer.presentation.movies.list


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment

import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType

const val MOVIE_LIST_FRAGMENT_KEY = "movie_list_fragment_key"
private const val MOVIE_TYPE_KEY = "movie_type"

class MovieListFragment : MvpAppCompatFragment(), MovieListVew {

    private lateinit var moviesType: MoviesType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesType = arguments?.getSerializable(MOVIE_TYPE_KEY) as MoviesType
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun showMovies(movieList: ArrayList<Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance(moviesType: MoviesType): MovieListFragment {
            val movieListFragment = MovieListFragment()
            val args = Bundle()
            args.putSerializable(MOVIE_TYPE_KEY, moviesType)
            movieListFragment.arguments = args

            return movieListFragment
        }
    }
}
