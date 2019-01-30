package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase

class MovieListViewModel(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    private val moviesMutableLiveData = MutableLiveData<List<Movie>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val moviesLiveData: LiveData<List<Movie>> = moviesMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun getMovies(page: Int, moviesType: MoviesType) = moviesUseCase.execute(moviesType, page)
            .subscribe(
                    { response -> moviesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

}