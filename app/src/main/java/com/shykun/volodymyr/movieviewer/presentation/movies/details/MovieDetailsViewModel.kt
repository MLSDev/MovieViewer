package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.view.View
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.data.network.response.ItemAccountStateResponse
import com.shykun.volodymyr.movieviewer.data.network.response.MovieDetailsResponse
import com.shykun.volodymyr.movieviewer.data.network.response.PostResponse
import com.shykun.volodymyr.movieviewer.domain.MovieDetailsUseCase
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalListItem

class MovieDetailsViewModel(
        private val movieDetailsUseCase: MovieDetailsUseCase,
        private val profileUseCase: ProfileUseCase) : ViewModel() {

    private val movieDetailsMutableLiveData = MutableLiveData<MovieDetailsResponse>()
    private val movieActorsMutableLiveData = MutableLiveData<List<HorizontalListItem>>()
    private val movieReviewsMutableLiveData = MutableLiveData<List<Review>>()
    private val recommendedMoviesMutableLiveData = MutableLiveData<List<HorizontalListItem>>()

    private val rateMovieMutableLiveData = MutableLiveData<PostResponse>()
    private val addToWatchlistMutableLiveData = MutableLiveData<PostResponse>()
    private val markAsFavoriteMutableLiveData = MutableLiveData<PostResponse>()

    private val movieAccountStatesMutableLiveData = MutableLiveData<ItemAccountStateResponse>()

    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val movieDetailsLiveData: LiveData<MovieDetailsResponse> = movieDetailsMutableLiveData
    val movieActorsLiveData: LiveData<List<HorizontalListItem>> = movieActorsMutableLiveData
    val movieReviewLiveData: LiveData<List<Review>> = movieReviewsMutableLiveData
    val recommendedMoviesLiveData: LiveData<List<HorizontalListItem>> = recommendedMoviesMutableLiveData

    val rateMovieLiveData: LiveData<PostResponse> = rateMovieMutableLiveData
    val addToWatchListLiveData: LiveData<PostResponse> = addToWatchlistMutableLiveData
    val markAsFavoriteLiveData: LiveData<PostResponse> = markAsFavoriteMutableLiveData

    val movieAccountLiveData: LiveData<ItemAccountStateResponse> = movieAccountStatesMutableLiveData

    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    val castTitleVisibility = ObservableField<Int>(View.GONE)
    val recommendedMoviesTitleVisibility = ObservableField<Int>(View.GONE)
    val reviewsTitleVisibility = ObservableField<Int>(View.GONE)
    val trailerTitleVisibility = ObservableField<Int>(View.VISIBLE)

    val castCount = ObservableField<String>("")
    val recommendedMoviesCount = ObservableField<String>("")
    val reviewsCount = ObservableField<String>("")

    fun onViewLoaded(movieId: Int) {
        getMovieDetails(movieId)
        getMovieCast(movieId)
        getMovieReviews(movieId)
        getRecommendedMovies(movieId)
    }

    fun getMovieDetails(movieId: Int) = movieDetailsUseCase.getMovieDetails(movieId)
            .subscribe(
                    { response -> movieDetailsMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )


    fun getMovieCast(movieId: Int) = movieDetailsUseCase.getMovieCredits(movieId)
            .subscribe(
                    { response ->
                        movieActorsMutableLiveData.value = response
                        if (response.isNotEmpty()) {
                            castTitleVisibility.set(View.VISIBLE)
                            castCount.set(response.size.toString())
                        }
                    },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getMovieReviews(movieId: Int) = movieDetailsUseCase.getMovieReviews(movieId)
            .subscribe(
                    { response ->
                        movieReviewsMutableLiveData.value = response
                        if (response.isNotEmpty()) {
                            reviewsTitleVisibility.set(View.VISIBLE)
                            reviewsCount.set(response.size.toString())
                        }
                    },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getRecommendedMovies(movieId: Int) = movieDetailsUseCase.getRecommendedMovies(movieId)
            .subscribe(
                    { response ->
                        recommendedMoviesMutableLiveData.value = response
                        if (response.isNotEmpty()) {
                            recommendedMoviesTitleVisibility.set(View.VISIBLE)
                            recommendedMoviesCount.set(response.size.toString())
                        }
                    },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun rateMovie(movieId: Int, rating: Float, sessionId: String) = movieDetailsUseCase.rateMovie(movieId, rating, sessionId)
            .subscribe(
                    { response -> rateMovieMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun deleteMovieRating(movieId: Int, sessionId: String) = movieDetailsUseCase.deleteMovieRating(movieId, sessionId)
            .subscribe(
                    { response -> rateMovieMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun addToWatchList(movieId: Int, sessionId: String) = movieDetailsUseCase.addToWatchlist(movieId, sessionId)
            .subscribe(
                    { response -> addToWatchlistMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun deleteFromWatchList(movieId: Int, sessionId: String) = movieDetailsUseCase.removeFromWatchList(movieId, sessionId)
            .subscribe(
                    { response -> addToWatchlistMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun markAsFavorite(movieId: Int, sessionId: String) = movieDetailsUseCase.markAsFavorite(movieId, sessionId)
            .subscribe(
                    { response -> markAsFavoriteMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun deleteFromFavorites(movieId: Int, sessionId: String) = movieDetailsUseCase.removeFromFavorites(movieId, sessionId)
            .subscribe(
                    { response -> markAsFavoriteMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getMovieAccountStates(movieId: Int, sessionId: String) = movieDetailsUseCase.getMovieAccountStates(movieId, sessionId)
            .subscribe(
                    { response -> movieAccountStatesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}