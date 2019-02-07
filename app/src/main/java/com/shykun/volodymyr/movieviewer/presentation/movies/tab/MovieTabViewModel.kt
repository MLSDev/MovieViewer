package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.network.response.MoviesResponse
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase

class MovieTabViewModel(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    private val popularMoviesMutableLiveData = MutableLiveData<MoviesResponse>()
    private val topRatedMoviesMutableLiveData = MutableLiveData<MoviesResponse>()
    private val upcomingMoviesMutableLiveData = MutableLiveData<MoviesResponse>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val popularMoviesLiveData: LiveData<MoviesResponse> = popularMoviesMutableLiveData
    val topRatedMoviesLiveData: LiveData<MoviesResponse> = topRatedMoviesMutableLiveData
    val upcomingMoviesLiveData: LiveData<MoviesResponse> = upcomingMoviesMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData


    fun onViewLoaded() {
        getPopularMovies(1)
        getTopRatedMovies(1)
        getUpcomingMovies(1)
    }

    private fun getPopularMovies(page: Int) {
        moviesUseCase.getMovies(MoviesType.POPULAR, page)
                .subscribe(
                        { response -> popularMoviesMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    private fun getTopRatedMovies(page: Int) {
        moviesUseCase.getMovies(MoviesType.TOP_RATED, page)
                .subscribe(
                        { response -> topRatedMoviesMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    private fun getUpcomingMovies(page: Int) {
        moviesUseCase.getMovies(MoviesType.UPCOMING, page)
                .subscribe(
                        { response -> upcomingMoviesMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }
}