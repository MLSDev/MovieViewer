package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.data.entity.Video
import com.shykun.volodymyr.movieviewer.data.network.YOUTUBE_API_KEY
import com.shykun.volodymyr.movieviewer.data.network.response.MovieDetailsResponse
import com.shykun.volodymyr.movieviewer.data.network.response.PostResponse
import com.shykun.volodymyr.movieviewer.databinding.FragmentMovieDetailsBinding
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.people.details.PERSON_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.profile.SESSION_ID_KEY
import com.shykun.volodymyr.movieviewer.presentation.utils.NavigationKeys
import kotlinx.android.synthetic.main.fragment_movie_details.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val MOVIE_DETAILS_FRAGMENT_KEY = "movie_details_fragment_key"
private const val MOVIE_ID_KEY = "movie_id_key"
private const val RATE_DIALOG_TAG = "rate_dialog_tag"

class MovieDetailsFragment : Fragment(), BackButtonListener {

    private var movieId = -1
    private var viewWasLoaded = false
    private var sessionId: String? = null

    private lateinit var viewModel: MovieDetailsViewModel
    private var binding: FragmentMovieDetailsBinding? = null
    private lateinit var castAdapter: CastAdapter
    private lateinit var reviewsAdapter: ReviewAdapter
    private lateinit var recommendedMoviesAdapter: RecommendedMoviesAdapter

    @Inject
    lateinit var viewModelFactory: MovieDetailsViewModelFactory
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)

        castAdapter = CastAdapter(ArrayList())
        reviewsAdapter = ReviewAdapter(ArrayList())
        recommendedMoviesAdapter = RecommendedMoviesAdapter(ArrayList())

        movieId = arguments?.getInt(MOVIE_ID_KEY)!!
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MovieDetailsViewModel::class.java)

        subscribeViewModel()
        setupRecommendedMovieClick()
        setupActorClick()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
            binding!!.viewModel = viewModel
        }

        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupCastAdapter()
        setupReviewsAdapter()
        setupRecommendedMoviesAdapter()

        if (savedInstanceState != null)
            viewWasLoaded = true

        if (!viewWasLoaded) {
            viewModel.onViewLoaded(movieId, sessionId)
            viewWasLoaded = true
        }
    }

    private fun initTrailer(trailer: Video) {
        val youtubePlayerFragment = YouTubePlayerSupportFragment()
        youtubePlayerFragment.initialize(YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, player: YouTubePlayer, p2: Boolean) {
                if (!p2)
                    player.cueVideo(trailer.key)
                player.fullscreenControlFlags = YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.movieTrailerContainer, youtubePlayerFragment)
        fragmentTransaction.commit()
    }

    private fun setupToolbar() {
        if (movieDetailsToolbar.menu.size() == 0)
            movieDetailsToolbar.inflateMenu(R.menu.menu_movie_details)
        movieDetailsToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_rate -> rateMovie()
                R.id.action_addToWatchlist -> addToWatchList()
                R.id.action_markAsFavorite -> markAsFavorite()
                else -> false
            }
        }

        movieDetailsToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        movieDetailsToolbar.setNavigationOnClickListener {
            onBackClicked()
        }
    }

    private fun setupCastAdapter() {
        movieCast.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }
    }

    private fun setupReviewsAdapter() {
        movieReviews.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
            adapter = reviewsAdapter
        }
    }

    private fun setupRecommendedMoviesAdapter() {
        recommendedMovies.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendedMoviesAdapter
        }
    }

    private fun setupRecommendedMovieClick() {
        recommendedMoviesAdapter.clickObservable.subscribe { router.navigateTo(MOVIE_DETAILS_FRAGMENT_KEY, it) }
    }

    private fun setupActorClick() {
        castAdapter.clickObservable.subscribe { router.navigateTo(PERSON_DETAILS_FRAGMENT_KEY, it) }
    }

    private fun rateMovie(): Boolean {
        val sessionId = prefs.getString(SESSION_ID_KEY, null)
        if (sessionId == null) {
            openLoginSnackBar()
        } else {
            MovieRateDialogFragment.newInstance(movieId, sessionId).show(childFragmentManager, RATE_DIALOG_TAG)
        }
        return true
    }

    private fun addToWatchList(): Boolean {
        val sessionId = prefs.getString(SESSION_ID_KEY, null)
        if (sessionId == null) {
            openLoginSnackBar()
        } else {
            viewModel.addToWatchList(movieId, sessionId)
        }
        return true
    }

    private fun markAsFavorite(): Boolean {
        val sessionId = prefs.getString(SESSION_ID_KEY, null)
        if (sessionId == null) {
            openLoginSnackBar()
        } else {
            viewModel.markAsFavorite(movieId, sessionId)
        }

        return true
    }

    private fun subscribeViewModel() {
        viewModel.movieDetailsLiveData.observe(this, Observer { showMovieDetails(it) })
        viewModel.movieCastLiveData.observe(this, Observer { showMovieCast(it) })
        viewModel.movieReviewLiveData.observe(this, Observer { showReviews(it) })
        viewModel.recommendedMoviesLiveData.observe(this, Observer { showRecommendedMovies(it) })

        viewModel.rateMovieLiveData.observe(this, Observer { handleRateResponse(it) })
        viewModel.addToWatchListLiveData.observe(this, Observer { handleAddToWatchlistReponse(it) })
        viewModel.markAsFavoriteLiveData.observe(this, Observer { handleMarkAsFavoriteResponse(it) })

        viewModel.loadingErrorLiveData.observe(this, Observer { showLoadingError(it) })
    }

    private fun showMovieDetails(movieDetails: MovieDetailsResponse?) {
        binding?.movieDetails = movieDetails
        if (movieDetails != null) {
            val trailers = movieDetails.videos.results.filter { it.type == "Trailer" }
            if (trailers.isNotEmpty())
                initTrailer(trailers.first())
            else
                viewModel.trailerTitleVisibility.set(View.GONE)
        }
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

    private fun showSnackbar(message: String, buttonText: String, action: (view: View) -> Unit) {
        Snackbar.make(movieDetailsCoordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction(buttonText, action)
                .setActionTextColor(ContextCompat.getColor(this.context!!, R.color.colorAccent))
                .show()
    }


    private fun openLoginSnackBar() = showSnackbar("You are not authorized", "Login") {
        (activity as AppActivity).cicerone.router.replaceScreen(NavigationKeys.PROFILE_NAVIGATION_KEY)
    }

    private fun handleRateResponse(response: PostResponse?) {
        if (response?.statusCode == 1 || response?.statusCode == 12) {
            showSnackbar("Rated", "Undo") {
                val sessionId = prefs.getString(SESSION_ID_KEY, null)
                viewModel.deleteMovieRating(movieId, sessionId)
            }
        }
    }

    private fun handleAddToWatchlistReponse(response: PostResponse?) {
        if (response?.statusCode == 1 || response?.statusCode == 12) {
            showSnackbar("Added to watchlist", "Undo") {
                val sessionId = prefs.getString(SESSION_ID_KEY, null)
                viewModel.removeFromWatchList(movieId, sessionId)
            }
        }
    }

    private fun handleMarkAsFavoriteResponse(response: PostResponse?) {
        if (response?.statusCode == 1 || response?.statusCode == 12) {
            showSnackbar("Marked as favorite", "Undo") {
                val sessionId = prefs.getString(SESSION_ID_KEY, null)
                viewModel.removeFromFavorites(movieId, sessionId)
            }
        }
    }

    private fun showLoadingError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackClicked(): Boolean {
        router.exit()

        return true
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