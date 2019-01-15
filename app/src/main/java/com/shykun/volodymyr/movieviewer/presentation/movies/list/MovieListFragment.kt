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
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.base.ScrollObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movie_list.*

const val MOVIE_LIST_FRAGMENT_KEY = "movie_list_fragment_key"
private const val MOVIE_TYPE_KEY = "movie_type"
private lateinit var movieListAdapter: MovieListAdapter

class MovieListFragment : Fragment() {

    private lateinit var moviesType: MoviesType
    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesType = arguments?.getSerializable(MOVIE_TYPE_KEY) as MoviesType
        viewModel = ViewModelProviders
                .of(this, (activity as AppActivity).appComponent.getMovieListViewModelFactory())
                .get(MovieListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        subscribeViewModel()
        subscribeScrollObervable()
        viewModel.onViewLoaded(moviesType)
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
                    viewModel.getMovies(movieListAdapter.lastLoadedPage + 1)
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
