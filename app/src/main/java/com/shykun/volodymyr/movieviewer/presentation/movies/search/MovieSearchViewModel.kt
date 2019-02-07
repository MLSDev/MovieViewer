package com.shykun.volodymyr.movieviewer.presentation.movies.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.network.response.MoviesResponse
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase

class MovieSearchViewModel(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    private val searchedMoviesMutableLiveData = MutableLiveData<MoviesResponse>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val searchedMoviesLiveData: LiveData<MoviesResponse> = searchedMoviesMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun searchMovies(query: String) = moviesUseCase.searchMovies(query)
            .subscribe(
                    {response -> searchedMoviesMutableLiveData.value = response},
                    {error -> loadingErrorMutableLiveData.value = error.message}
            )


}