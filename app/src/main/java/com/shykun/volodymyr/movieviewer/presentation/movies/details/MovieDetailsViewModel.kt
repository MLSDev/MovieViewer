package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.data.network.response.GetMovieDetailsResponse
import com.shykun.volodymyr.movieviewer.domain.GetMovieDetailsUseCase

class MovieDetailsViewModel(
        private val getMovieDetailsUseCase: GetMovieDetailsUseCase) : ViewModel() {

    private val movieDetailsMutableLiveData = MutableLiveData<GetMovieDetailsResponse>()
    private val movieCastMutableLiveData = MutableLiveData<List<Actor>>()
    private val movieReviewsMutableLiveData = MutableLiveData<List<Review>>()
    private val recommendedMoviesMutableLiveData = MutableLiveData<List<Movie>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val movieDetailsLiveData: LiveData<GetMovieDetailsResponse> = movieDetailsMutableLiveData
    val movieCastLiveData: LiveData<List<Actor>> = movieCastMutableLiveData
    val movieReviewLiveData: LiveData<List<Review>> = movieReviewsMutableLiveData
    val recommendedMoviesLiveData: LiveData<List<Movie>> = recommendedMoviesMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData


    fun onViewLoaded(movieId: Int) {
        getMovieDetails(movieId)
        getMovieCast(movieId)
        getMovieReviews(movieId)
        getRecommendedMovies(movieId)
    }

    fun getMovieDetails(movieId: Int) {
        getMovieDetailsUseCase.getMovieDetails(movieId)
                .doOnSuccess {
                    movieDetailsMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }

    fun getMovieCast(movieId: Int) {
        getMovieDetailsUseCase.getMoviCast(movieId)
                .doOnSuccess {
                    movieCastMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }

    fun getMovieReviews(movieId: Int) {
        getMovieDetailsUseCase.getMovieReviews(movieId)
                .doOnSuccess {
                    movieReviewsMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }

    fun getRecommendedMovies(movieId: Int) {
        getMovieDetailsUseCase.getRecommendedMovies(movieId)
                .doOnSuccess {
                    recommendedMoviesMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }
}