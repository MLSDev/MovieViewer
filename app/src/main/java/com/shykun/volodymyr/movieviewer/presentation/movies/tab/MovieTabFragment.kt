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
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.network.response.MoviesResponse
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.model.ItemType
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MOVIE_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_TYPE_KEY
import com.shykun.volodymyr.movieviewer.presentation.search.SEARCH_FRAGMENT_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movies_tab.*
import ru.terrakok.cicerone.Router
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

        generalMovieTabAdapter = GeneralMovieTabAdapter()
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MovieTabViewModel::class.java)

        subscribeViewModel()
        setupSeeAllClick()
        setupMovieClick()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupAdapter()
        if (viewModel.popularMoviesLiveData.value == null)
            viewModel.onViewLoaded()
    }


    private fun setupToolbar() {
        moviesToolbar.inflateMenu(R.menu.manu_app)
        moviesToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> router.navigateTo(SEARCH_FRAGMENT_KEY, ItemType.MOVIE)
            }
            true
        }
    }


    private fun setupSeeAllClick() {
        generalMovieTabAdapter.seeAllClickEvent.subscribe {
            val moviesType = when (it) {
                POPULAR_MOVIES -> MoviesType.POPULAR
                TOP_RATED_MOVIES -> MoviesType.TOP_RATED
                UPCOMING_MOVIES -> MoviesType.UPCOMING
                else -> throw Exception("Undefined moviesLiveData type")
            }
            val args = Bundle()
            args.putSerializable(MOVIE_TYPE_KEY, moviesType)
            router.navigateTo(MOVIE_LIST_FRAGMENT_KEY, args)
        }
    }

    private fun setupMovieClick() {
        generalMovieTabAdapter.movieClickEvent
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    router.navigateTo(MOVIE_DETAILS_FRAGMENT_KEY, it)
                },
                        { error -> Toast.makeText(this.context, error.message, Toast.LENGTH_LONG).show() })
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
        viewModel.upcomingMoviesLiveData.observe(this, Observer { showUpcomingMovies(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showError(it) })
    }

    private fun showPopularMovies(moviesResponse: MoviesResponse?) {
        if (moviesResponse != null) {
            generalMovieTabAdapter.addMovies(moviesResponse.results, POPULAR_MOVIES)
        }
    }

    private fun showTopRatedMovies(moviesResponse: MoviesResponse?) {
        if (moviesResponse != null) {
            generalMovieTabAdapter.addMovies(moviesResponse.results, TOP_RATED_MOVIES)
        }
    }

    private fun showUpcomingMovies(moviesResponse: MoviesResponse?) {
        if (moviesResponse != null) {
            generalMovieTabAdapter.addMovies(moviesResponse.results, UPCOMING_MOVIES)
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackClicked(): Boolean {

        return false
    }
}
