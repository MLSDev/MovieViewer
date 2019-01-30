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
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.ScrollObservable
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MOVIE_DETAILS_FRAGMENT_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movie_list.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val MOVIE_LIST_FRAGMENT_KEY = "movie_list_fragment_key"
private const val MOVIE_TYPE_KEY = "movie_type"

class MovieListFragment : Fragment(), BackButtonListener {

    private lateinit var moviesType: MoviesType
    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieListAdapter: MovieListAdapter

    @Inject
    lateinit var viewModelFactory: MovieListViewModelFactory
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)
        moviesType = arguments?.getSerializable(MOVIE_TYPE_KEY) as MoviesType
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MovieListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackButton()
        setupAdapter()
        setupMovieClick()
        subscribeViewModel()
        subscribeScrollObervable()
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
        viewModel.movies.observe(this, Observer<List<Movie>> { showMovies(it) })
        viewModel.loadingError.observe(this, Observer { showError(it) })

    }

    private fun setupAdapter() {
        movieListAdapter = MovieListAdapter(ArrayList(), moviesType)
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
                    viewModel.getMovies(movieListAdapter.lastLoadedPage + 1, moviesType)
                    movieListAdapter.lastLoadedPage++
                }
                .subscribe()
    }

    fun showMovies(movieList: List<Movie>?) {
        if (movieList != null) {
            movieListAdapter.addMovies(movieList)
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
        fun newInstance(moviesType: MoviesType): MovieListFragment {
            val movieListFragment = MovieListFragment()
            val args = Bundle()
            args.putSerializable(MOVIE_TYPE_KEY, moviesType)
            movieListFragment.arguments = args

            return movieListFragment
        }
    }
}
