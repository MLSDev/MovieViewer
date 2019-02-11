package com.shykun.volodymyr.movieviewer.presentation.movies.list


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
import com.shykun.volodymyr.movieviewer.data.network.response.MoviesResponse
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.ScrollObservable
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MOVIE_DETAILS_FRAGMENT_KEY
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
    private lateinit var movieListAdapter: MovieListAdapter
    private var searchQuery: String? = null

    @Inject
    lateinit var viewModelFactory: MovieListViewModelFactory
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)

        moviesType = arguments?.getSerializable(MOVIE_TYPE_KEY) as MoviesType
        movieListAdapter = MovieListAdapter(ArrayList(), moviesType)
        searchQuery = arguments?.getString(SEARCH_QUERY_KEY, null)
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
        movieListToolbar.title = when(moviesType) {
            MoviesType.TOP_RATED -> getString(R.string.top_rated_movies)
            MoviesType.POPULAR -> getString(R.string.popular_movies)
            MoviesType.UPCOMING -> getString(R.string.upcoming_movies)
            MoviesType.SEARCHED -> getString(R.string.results)
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
        viewModel.moviesLiveData.observe(this, Observer<MoviesResponse> { showMovies(it) })
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
        ScrollObservable.from(movieList, 10)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    if (moviesType != MoviesType.SEARCHED)
                        viewModel.getMovies(movieListAdapter.lastLoadedPage + 1, moviesType)
                    else
                        viewModel.searchMovie(searchQuery!!, movieListAdapter.lastLoadedPage + 1)
                    movieListAdapter.lastLoadedPage++
                }
                .subscribe()
    }

    fun showMovies(moviesResponse: MoviesResponse?) {
        if (moviesResponse != null) {
            movieListAdapter.addMovies(moviesResponse.results)
            movieListAdapter.totalItemsCount = moviesResponse.totalResults
        }
    }

    fun showError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
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
