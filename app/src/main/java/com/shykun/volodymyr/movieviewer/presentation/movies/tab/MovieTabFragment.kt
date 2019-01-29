package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MOVIE_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_LIST_FRAGMENT_KEY
import kotlinx.android.synthetic.main.fragment_movies.*
import ru.terrakok.cicerone.Router
import java.lang.Exception
import javax.inject.Inject

const val MOVIE_TAB_FRAGMENT_KEY = "movie_tab_fragment_key"

const val POPULAR_MOVIES = 0
const val TOP_RATED_MOVIES = 1
const val UPCOMING_MOVIES = 2

class MovieTabFragment : Fragment(), BackButtonListener {

    private lateinit var generalMovieTabAdapter: GeneralMovieTabAdapter
    private lateinit var viewModel: MovieTabViewModel

    @Inject
    lateinit var viewModelFactory: MovieTabViewModelFactory
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (parentFragment as TabNavigationFragment).component?.inject(this)

        generalMovieTabAdapter = GeneralMovieTabAdapter(ArrayList(3))
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MovieTabViewModel::class.java)
        subscribeViewModel()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupSeeAllClick()
        setupMovieClick()
        if (viewModel.popularMoviesLiveData.value == null)
            viewModel.onViewLoaded()
    }

    private fun setupSeeAllClick() {
        generalMovieTabAdapter.seeAllClickEvent.subscribe {
            val moviesType = when (it) {
                POPULAR_MOVIES -> MoviesType.POPULAR
                TOP_RATED_MOVIES -> MoviesType.TOP_RATED
                UPCOMING_MOVIES -> MoviesType.UPCOMING
                else -> throw Exception("Undefined movies type")
            }
            router.navigateTo(MOVIE_LIST_FRAGMENT_KEY, moviesType)
        }
    }

    private fun setupMovieClick() {
        generalMovieTabAdapter.movieClickEvent.subscribe {
            router.navigateTo(MOVIE_DETAILS_FRAGMENT_KEY, it)
        }
    }

    private fun setupAdapter() {
        movieCategoryList.apply {
            layoutManager = LinearLayoutManager(this@MovieTabFragment.context, LinearLayoutManager.VERTICAL, false)
            adapter = generalMovieTabAdapter
        }
    }

    private fun subscribeViewModel() {
        viewModel.popularMoviesLiveData.observe(this, Observer { showPopularMovies(it) })
        viewModel.topRatedMoviesLiveData.observe(this, Observer { showTopRatedMovies(it) })
        viewModel.upcomingMoviesLiveData.observe(this, Observer { showUpcompingMovies(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showError(it) })
    }

    private fun showPopularMovies(movies: List<Movie>?) {
        if (movies != null) {
            generalMovieTabAdapter.addMovies(movies, POPULAR_MOVIES)
        }
    }

    private fun showTopRatedMovies(movies: List<Movie>?) {
        if (movies != null) {
            generalMovieTabAdapter.addMovies(movies, TOP_RATED_MOVIES)
        }
    }

    private fun showUpcompingMovies(movies: List<Movie>?) {
        if (movies != null) {
            generalMovieTabAdapter.addMovies(movies, UPCOMING_MOVIES)
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackClicked(): Boolean {
        router.exit()

        return true
    }
}
