package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.data.network.response.MovieDetailsResponse
import com.shykun.volodymyr.movieviewer.domain.MovieDetailsUseCase

class MovieDetailsViewModel(
        private val movieDetailsUseCase: MovieDetailsUseCase) : ViewModel() {

    private val movieDetailsMutableLiveData = MutableLiveData<MovieDetailsResponse>()
    private val movieCastMutableLiveData = MutableLiveData<List<Actor>>()
    private val movieReviewsMutableLiveData = MutableLiveData<List<Review>>()
    private val recommendedMoviesMutableLiveData = MutableLiveData<List<Movie>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val movieDetailsLiveData: LiveData<MovieDetailsResponse> = movieDetailsMutableLiveData
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
        movieDetailsUseCase.getMovieDetails(movieId)
                .subscribe(
                        { response -> movieDetailsMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    fun getMovieCast(movieId: Int) {
        movieDetailsUseCase.getMovieCredits(movieId)
                .subscribe(
                        { response -> movieCastMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    fun getMovieReviews(movieId: Int) {
        movieDetailsUseCase.getMovieReviews(movieId)
                .subscribe(
                        { response -> movieReviewsMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    fun getRecommendedMovies(movieId: Int) {
        movieDetailsUseCase.getRecommendedMovies(movieId)
                .subscribe(
                        { response -> recommendedMoviesMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }
}