package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.common.adapters.VerticalListAdapter
import com.shykun.volodymyr.movieviewer.presentation.discover.tab.GENRES_KEY
import com.shykun.volodymyr.movieviewer.presentation.discover.tab.RATING_KEY
import com.shykun.volodymyr.movieviewer.presentation.discover.tab.RELEASE_YEAR_KEY
import com.shykun.volodymyr.movieviewer.presentation.model.VerticalItemList
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MOVIE_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.profile.SESSION_ID_KEY
import com.shykun.volodymyr.movieviewer.presentation.utils.ScrollObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movie_list.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val MOVIE_LIST_FRAGMENT_KEY = "movie_list_fragment_key"
const val MOVIE_TYPE_KEY = "movie_type"
const val SEARCH_QUERY_KEY = "query_search_key"

class MovieListFragment : Fragment(), BackButtonListener {

    private lateinit var moviesType: MoviesType
    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieListAdapter: VerticalListAdapter

    @Inject
    lateinit var viewModelFactory: MovieListViewModelFactory
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)
        moviesType = arguments?.getSerializable(MOVIE_TYPE_KEY) as MoviesType
        movieListAdapter = VerticalListAdapter()
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MovieListViewModel::class.java)

        subscribeViewModel()
        setupMovieClick()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle()
        subscribeScrollObervable()
        setupBackButton()
        setupAdapter()
    }

    private fun setToolbarTitle() {
        movieListToolbar.title = when (moviesType) {
            MoviesType.TOP_RATED -> getString(R.string.top_rated_movies)
            MoviesType.POPULAR -> getString(R.string.popular_movies)
            MoviesType.UPCOMING -> getString(R.string.upcoming_movies)
            MoviesType.SEARCHED -> getString(R.string.results)
            MoviesType.RATED -> getString(R.string.rated_movies)
            MoviesType.FAVORITE -> getString(R.string.favorite_movies)
            MoviesType.WATCHLIST -> getString(R.string.movie_watchlist)
            MoviesType.DISCOVERED -> getString(R.string.results)
        }
    }

    private fun setupBackButton() {
        movieListToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        movieListToolbar.setNavigationOnClickListener { onBackClicked() }
    }

    private fun setupMovieClick() {
        movieListAdapter.clickEvent.subscribe {
            router.navigateTo(MOVIE_DETAILS_FRAGMENT_KEY, it)
        }
    }

    private fun subscribeViewModel() {
        viewModel.moviesLiveData.observe(this, Observer<VerticalItemList> { showMovies(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showError(it) })

    }

    private fun setupAdapter() {
        movieList.apply {
            layoutManager = LinearLayoutManager(this@MovieListFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false)
            adapter = movieListAdapter
        }
    }

    private fun subscribeScrollObervable() {
        val sessionId = prefs.getString(SESSION_ID_KEY, null)

        ScrollObservable.from(movieList, 20)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    when (moviesType) {
                        MoviesType.TOP_RATED, MoviesType.POPULAR, MoviesType.UPCOMING -> {
                            viewModel.getMovies(moviesType, movieListAdapter.nextPage)
                        }
                        MoviesType.SEARCHED -> {
                            val query = arguments?.getString(SEARCH_QUERY_KEY)
                            viewModel.searchMovie(query!!, movieListAdapter.nextPage)
                        }
                        MoviesType.RATED -> {
                            viewModel.getRatedMovies(sessionId, movieListAdapter.nextPage)
                        }
                        MoviesType.FAVORITE -> {
                            viewModel.getFavoriteMovies(sessionId, movieListAdapter.nextPage)
                        }
                        MoviesType.WATCHLIST -> {
                            viewModel.getMovieWatchlist(sessionId, movieListAdapter.nextPage)
                        }
                        MoviesType.DISCOVERED -> {
                            val year = arguments?.getInt(RELEASE_YEAR_KEY)
                            val rating = arguments?.getInt(RATING_KEY)
                            val genres = arguments?.getString(GENRES_KEY)
                            viewModel.discoverMovies(year, rating, genres, movieListAdapter.nextPage)
                        }
                    }
                    movieListAdapter.nextPage++
                }
                .subscribe()
    }


    fun showMovies(movies: VerticalItemList?) {
        if (movies != null) {
            if (movies.totalItemsCount > 0) {
                movieListAdapter.totalItemsCount = movies.totalItemsCount
                movieListAdapter.addItems(movies.items)
            } else {
                movieList.visibility = View.GONE
                emptyState.visibility = View.VISIBLE
            }
        }
    }

    fun showError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()

        with(movieListAdapter) {
            clearItems()
            totalItemsCount = -1
            nextPage = 1
        }
    }

    override fun onBackClicked(): Boolean {
        router.exit()

        return true
    }

    companion object {
        fun newInstance(args: Bundle): MovieListFragment {
            val movieListFragment = MovieListFragment()
            movieListFragment.arguments = args

            return movieListFragment
        }
    }
}
