package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.domain.GetMoviesUseCase

class MovieListViewModel(private val getMoviesUseCase: GetMoviesUseCase) : ViewModel() {

    lateinit var moviesType: MoviesType
    private val moviesLiveData = MutableLiveData<List<Movie>>()
    private val loadingErrorLiveData = MutableLiveData<String>()

    val movies: LiveData<List<Movie>> = moviesLiveData
    val loadingError: LiveData<String> = loadingErrorLiveData

    fun onViewLoaded(moviesType: MoviesType) {
        this.moviesType = moviesType
        getMovies(1)
    }

    fun getMovies(page: Int) = getMoviesUseCase.execute(moviesType, page)
            .doOnSuccess {
                moviesLiveData.value = it
            }
            .doOnError {
                loadingErrorLiveData.value = it.message
            }
            .subscribe()

}