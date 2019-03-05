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
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.data.entity.Video
import com.shykun.volodymyr.movieviewer.data.network.YOUTUBE_API_KEY
import com.shykun.volodymyr.movieviewer.data.network.response.ItemAccountStateResponse
import com.shykun.volodymyr.movieviewer.data.network.response.MovieDetailsResponse
import com.shykun.volodymyr.movieviewer.data.network.response.PostResponse
import com.shykun.volodymyr.movieviewer.databinding.FragmentMovieDetailsBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.adapters.HorizontalListAdapter
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.common.adapters.ReviewAdapter
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalListItem
import com.shykun.volodymyr.movieviewer.presentation.people.details.PERSON_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.profile.LOGIN_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.profile.SESSION_ID_KEY
import kotlinx.android.synthetic.main.fragment_movie_details.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val MOVIE_DETAILS_FRAGMENT_KEY = "movie_details_fragment_key"
private const val MOVIE_ID_KEY = "movie_id_key"
private const val RATE_DIALOG_TAG = "rate_dialog_tag"
private const val rateActionId = 0
private const val deleteRatingActionId = 1
private const val addToWatchlistActionId = 2
private const val deleteFromWatchlistActionId = 3
private const val markAsFavoriteActionId = 4
private const val deleteFromFavoritesActionsId = 5

class MovieDetailsFragment : Fragment(), BackButtonListener {

    private var movieId = -1
    private var viewWasLoaded = false

    private lateinit var viewModel: MovieDetailsViewModel
    private var binding: FragmentMovieDetailsBinding? = null
    private lateinit var actorsAdapter: HorizontalListAdapter
    private lateinit var recommendedMoviesAdapter: HorizontalListAdapter
    private lateinit var reviewsAdapter: ReviewAdapter

    @Inject
    lateinit var viewModelFactory: MovieDetailsViewModelFactory
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)

        actorsAdapter = HorizontalListAdapter()
        recommendedMoviesAdapter = HorizontalListAdapter()
        reviewsAdapter = ReviewAdapter()

        movieId = arguments?.getInt(MOVIE_ID_KEY)!!
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MovieDetailsViewModel::class.java)

        subscribeViewModel()
        setupRecommendedMovieClick()
        setupActorClick()

        if (savedInstanceState != null)
            viewWasLoaded = true

        if (!viewWasLoaded) {
            viewModel.onViewLoaded(movieId)
            viewWasLoaded = true
        }
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
        getMovieAccountStates()
    }

    private fun getMovieAccountStates() {
        val sessionId = prefs.getString(SESSION_ID_KEY, null)
        if (sessionId != null)
            viewModel.getMovieAccountStates(movieId, sessionId)
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
        viewModel.movieAccountLiveData.observe(this, Observer { handleMovieAccountStatesResponse(it) })

        movieDetailsToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { onBackClicked() }

            inflateMenu(R.menu.menu_details)
            setOnMenuItemClickListener {
                openLoginSnackBar()
                true
            }
        }

    }

    private fun setupCastAdapter() {
        movieCast.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = actorsAdapter
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
        recommendedMoviesAdapter.clickObservable.subscribe { router.navigateTo(MOVIE_DETAILS_FRAGMENT_KEY, it.id) }
    }

    private fun setupActorClick() {
        actorsAdapter.clickObservable.subscribe { router.navigateTo(PERSON_DETAILS_FRAGMENT_KEY, it.id) }
    }

    private fun performMenuAction(action: (sessionId: String) -> Unit): Boolean {
        val sessionId = prefs.getString(SESSION_ID_KEY, null)
        if (sessionId == null) {
            openLoginSnackBar()
        } else {
            action.invoke(sessionId)
        }
        return true
    }

    private fun rateMovie() = performMenuAction { sessionId ->
        MovieRateDialogFragment.newInstance(movieId, sessionId).show(childFragmentManager, RATE_DIALOG_TAG)
    }

    private fun deleteRatig() = performMenuAction { sessionId ->
        viewModel.deleteMovieRating(movieId, sessionId)
    }

    private fun addToWatchList() = performMenuAction { sessionId ->
        viewModel.addToWatchList(movieId, sessionId)
    }

    private fun deleteFromWatchList() = performMenuAction { sessionId ->
        viewModel.deleteFromWatchList(movieId, sessionId)
    }

    private fun markAsFavorite() = performMenuAction { sessionId ->
        viewModel.markAsFavorite(movieId, sessionId)
    }

    private fun deleteFromFavorites() = performMenuAction { sessionId ->
        viewModel.deleteFromFavorites(movieId, sessionId)
    }

    private fun subscribeViewModel() {
        viewModel.movieDetailsLiveData.observe(this, Observer { showMovieDetails(it) })
        viewModel.movieActorsLiveData.observe(this, Observer { showMovieActors(it) })
        viewModel.movieReviewLiveData.observe(this, Observer { showReviews(it) })
        viewModel.recommendedMoviesLiveData.observe(this, Observer { showRecommendedMovies(it) })

        viewModel.rateMovieLiveData.observe(this, Observer { handleRateResponse(it) })
        viewModel.addToWatchListLiveData.observe(this, Observer { handleAddToWatchlistResponse(it) })
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

    private fun showMovieActors(actors: List<HorizontalListItem>?) {
        if (actors != null) {
            actorsAdapter.addItems(actors)
        }
    }

    private fun showReviews(reviews: List<Review>?) {
        if (reviews != null) {
            reviewsAdapter.addItems(reviews)
        }
    }

    private fun showRecommendedMovies(movies: List<HorizontalListItem>?) {
        if (movies != null) {
            recommendedMoviesAdapter.addItems(movies)
        }
    }

    private fun handleMovieAccountStatesResponse(accountStates: ItemAccountStateResponse?) {

        val menu = movieDetailsToolbar.menu
        menu.clear()

        accountStates?.let {
            if (it.rated)
                menu.add(Menu.NONE, deleteRatingActionId, 0, "Delete rating")
            else
                menu.add(Menu.NONE, rateActionId, 0, "Rate")

            if (it.watchlist)
                menu.add(Menu.NONE, deleteFromWatchlistActionId, 1, "Remove from watchlist")
            else
                menu.add(Menu.NONE, addToWatchlistActionId, 1, "Add to watchlist")

            if (it.favorite)
                menu.add(Menu.NONE, deleteFromFavoritesActionsId, 2, "Delete from favorites")
            else
                menu.add(Menu.NONE, markAsFavoriteActionId, 2, "Mark as favorite")

        }

        movieDetailsToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                rateActionId -> rateMovie()
                deleteRatingActionId -> deleteRatig()
                addToWatchlistActionId -> addToWatchList()
                deleteFromWatchlistActionId -> deleteFromWatchList()
                markAsFavoriteActionId -> markAsFavorite()
                deleteFromFavoritesActionsId -> deleteFromFavorites()
                else -> false
            }
        }
    }

    private fun showSnackbar(message: String, buttonText: String, action: ((view: View) -> Unit) = {}) {
        Snackbar.make(movieDetailsCoordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction(buttonText, action)
                .setActionTextColor(ContextCompat.getColor(this.context!!, R.color.colorAccent))
                .show()
    }


    private fun openLoginSnackBar() = showSnackbar("You are not authorized", "Login") {
        router.navigateTo(LOGIN_FRAGMENT_KEY)
    }

    private fun handleRateResponse(response: PostResponse?) {
        val message = if (response?.statusCode == 1 || response?.statusCode == 12) {
            movieDetailsToolbar.apply {
                menu.removeItem(rateActionId)
                menu.add(Menu.NONE, deleteRatingActionId, 0, "Delete rating")
            }
            "Rated"
        } else {
            movieDetailsToolbar.apply {
                menu.removeItem(deleteRatingActionId)
                menu.add(Menu.NONE, rateActionId, 0, "Rate")
            }
            "Rating deleted"
        }
        showSnackbar(message, "OK")
    }

    private fun handleAddToWatchlistResponse(response: PostResponse?) {
        val message = if (response?.statusCode == 1 || response?.statusCode == 12) {
            movieDetailsToolbar.apply {
                menu.removeItem(addToWatchlistActionId)
                menu.add(Menu.NONE, deleteFromWatchlistActionId, 1, "Delete from watchlist")
            }
            "Added to watchList"
        } else {
            movieDetailsToolbar.apply {
                menu.removeItem(deleteFromWatchlistActionId)
                menu.add(Menu.NONE, addToWatchlistActionId, 1, "Add to watchlist")
            }
            "Deleted from watchlist"
        }

        showSnackbar(message, "OK")
    }

    private fun handleMarkAsFavoriteResponse(response: PostResponse?) {
        val message = if (response?.statusCode == 1 || response?.statusCode == 12) {
            movieDetailsToolbar.apply {
                menu.removeItem(markAsFavoriteActionId)
                menu.add(Menu.NONE, deleteFromFavoritesActionsId, 2, "Delete from favorites")
            }
            "Marked as favorite"
        } else {
            movieDetailsToolbar.apply {
                menu.removeItem(deleteFromFavoritesActionsId)
                menu.add(Menu.NONE, markAsFavoriteActionId, 2, "Mark as favorite")
            }
            "Deleted from favorites"
        }

        showSnackbar(message, "OK")
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