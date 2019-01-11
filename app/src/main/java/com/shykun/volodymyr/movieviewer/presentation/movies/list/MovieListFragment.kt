package com.shykun.volodymyr.movieviewer.presentation.movies.list


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.base.ScrollObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.fragment_people_tab.*

const val MOVIE_LIST_FRAGMENT_KEY = "movie_list_fragment_key"
private const val MOVIE_TYPE_KEY = "movie_type"
private lateinit var movieListAdapter: MovieListAdapter

class MovieListFragment : MvpAppCompatFragment(), MovieListVew {

    private lateinit var moviesType: MoviesType
    @InjectPresenter
    lateinit var presenter: MovieListPresenter

    @ProvidePresenter
    fun providePresenter() = (activity as AppActivity).appComponent.getMovieListPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesType = arguments?.getSerializable(MOVIE_TYPE_KEY) as MoviesType
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        subscribeScrollObervable()
        presenter.onViewLoaded(moviesType)
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
                    presenter.getMovies(movieListAdapter.lastLoadedPage + 1)
                    movieListAdapter.lastLoadedPage++
                }
                .subscribe()
    }

    override fun showMovies(movieList: ArrayList<Movie>) {
        movieListAdapter.addMovies(movieList)
    }

    override fun showError() {
        Toast.makeText(this.context, "Error", Toast.LENGTH_SHORT).show()
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
