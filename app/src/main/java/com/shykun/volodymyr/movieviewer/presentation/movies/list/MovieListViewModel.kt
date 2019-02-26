package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.network.response.MoviesResponse
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase

class MovieListViewModel(private val moviesUseCase: MoviesUseCase,
                         private val profileUseCase: ProfileUseCase) : ViewModel() {

    private val moviesMutableLiveData = MutableLiveData<MoviesResponse>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val moviesLiveData: LiveData<MoviesResponse> = moviesMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun getMovies(page: Int, moviesType: MoviesType) = moviesUseCase.getMovies(moviesType, page)
            .subscribe(
                    { response -> moviesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun searchMovie(query: String, page: Int) = moviesUseCase.searchMovies(query, page)
            .subscribe(
                    { response -> moviesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getRatedMovies(sessionId: String, page: Int) = profileUseCase.getRatedMovies(sessionId, page)
            .subscribe(
                    { response -> moviesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getMovieWatchlist(sessionId: String, page: Int) = profileUseCase.getMovieWatchList(sessionId, page)
            .subscribe(
                    { response -> moviesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getFavoriteMovies(sessionId: String, page: Int) = profileUseCase.getFavoriteMovies(sessionId, page)
            .subscribe(
                    { response -> moviesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}