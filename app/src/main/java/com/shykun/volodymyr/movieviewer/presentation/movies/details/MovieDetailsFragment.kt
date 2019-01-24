package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.data.network.response.MovieDetailsResponse
import com.shykun.volodymyr.movieviewer.databinding.FragmentMovieDetailsBinding
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_person_details.*

const val MOVIE_DETAILS_FRAGMENT_KEY = "movie_details_fragment_key"
private const val MOVIE_ID_KEY = "movie_id_key"

class MovieDetailsFragment : Fragment() {

    private var movieId = -1
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var castAdapter: CastAdapter
    private lateinit var reviewsAdapter: ReviewAdapter
    private lateinit var recommendedMoviesAdapter: RecommendedMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieId = arguments?.getInt(MOVIE_ID_KEY)!!
        viewModel = ViewModelProviders
                .of(this, (activity as AppActivity).appComponent.getMovieDetailsViewModelFactory())
                .get(MovieDetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBackButton()
        subscribeViewModel()
        setupCastAdapter()
        setupReviewsAdapter()
        setupRecommendedMoviesAdapter()
        viewModel.onViewLoaded(movieId)
    }

    private fun setupBackButton() {
        movieDetailsToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        movieDetailsToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun subscribeViewModel() {
        viewModel.movieDetailsLiveData.observe(this, Observer { showMovieDetails(it) })
        viewModel.movieCastLiveData.observe(this, Observer { showMovieCast(it) })
        viewModel.movieReviewLiveData.observe(this, Observer { showReviews(it) })
        viewModel.recommendedMoviesLiveData.observe(this, Observer { showRecommendedMovies(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showLoadingError(it) })
    }

    private fun showMovieDetails(movieDetails: MovieDetailsResponse?) {
        binding.movieDetails = movieDetails
    }

    private fun showMovieCast(movieCast: List<Actor>?) {
        if (movieCast != null) {
            castAdapter.addCast(movieCast)
        }
    }

    private fun showReviews(reviews: List<Review>?) {
        if (reviews != null) {
            reviewsAdapter.addReviews(reviews)
        }
    }

    private fun showRecommendedMovies(movies: List<Movie>?) {
        if (movies != null) {
            recommendedMoviesAdapter.addMovies(movies)
        }
    }

    private fun showLoadingError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupCastAdapter() {
        castAdapter = CastAdapter(ArrayList())
        movieCast.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }
    }

    private fun setupReviewsAdapter() {
        reviewsAdapter = ReviewAdapter(ArrayList())
        movieReviews.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
            adapter = reviewsAdapter
        }
    }

    private fun setupRecommendedMoviesAdapter() {
        recommendedMoviesAdapter = RecommendedMoviesAdapter(ArrayList())
        recommendedMovies.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendedMoviesAdapter
        }
    }

    companion object {
        fun newInstance(movieId: Int): MovieDetailsFragment {
            val movieDetailsFragment = MovieDetailsFragment()
            val args = Bundle()
            args.putInt(MOVIE_ID_KEY, movieId)
            movieDetailsFragment.arguments = args

            return movieDetailsFragment
        }
    }
}