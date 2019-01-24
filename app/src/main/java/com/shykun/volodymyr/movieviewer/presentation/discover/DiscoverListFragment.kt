package com.shykun.volodymyr.movieviewer.presentation.discover

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
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.base.ScrollObservable
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MovieListAdapter
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TvListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_filter_list.*
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

const val DISCOVER_LIST_FRAGMENT_KEY = "discover_list_fragment_key"

class DiscoverListFragment : Fragment() {

    private lateinit var viewModel: DiscoverViewModel
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var tvListAdapter: TvListAdapter

    @Inject
    lateinit var viewModelFactory: DiscoverViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppActivity).appComponent.inject(this)

        viewModel = ViewModelProviders.of(activity as AppActivity, viewModelFactory)
                .get(DiscoverViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBackButton()
        subscribeViewModel()
        subscribeScrollObervable()
        if (viewModel.type.get() == MOVIE_TYPE)
            setupMovieListAdapter()
        else
            setupTvListAdapter()

        viewModel.load()
    }

    private fun setupBackButton() {
        movieListToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        movieListToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun subscribeViewModel() {
        viewModel.discoveredMovieLiveData.observe(this, Observer { showMovieList(it) })
        viewModel.discoveredTvLiveData.observe(this, Observer { showTvList(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showLoadingError(it) })
    }

    private fun subscribeScrollObervable() {
        ScrollObservable.from(movieList, 10)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { viewModel.load() }
                .subscribe()
    }

    private fun showMovieList(movieList: List<Movie>?) {
        if (movieList != null) {
            movieListAdapter.addMovies(movieList)
        }
    }

    private fun showTvList(tvList: List<Tv>?) {
        if (tvList != null) {
            tvListAdapter.addTvList(tvList)
        }
    }

    private fun showLoadingError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupMovieListAdapter() {
        movieListAdapter = MovieListAdapter(ArrayList(), MoviesType.TOP_RATED)
        movieList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = movieListAdapter
        }
    }

    private fun setupTvListAdapter() {
        tvListAdapter = TvListAdapter(ArrayList(), TvType.POPULAR)
        movieList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = tvListAdapter
        }
    }
}
