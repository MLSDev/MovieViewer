package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalItem
import com.shykun.volodymyr.movieviewer.presentation.utils.ioMainSubscribe
import com.shykun.volodymyr.movieviewer.presentation.utils.popularMovieToHorizontalListItem
import com.shykun.volodymyr.movieviewer.presentation.utils.topRatedMovieToHorizontalListItem
import com.shykun.volodymyr.movieviewer.presentation.utils.upcomingMovieToHorizontalListItem

class MovieTabViewModel(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    private val popularMoviesMutableLiveData = MutableLiveData<List<HorizontalItem>>()
    private val topRatedMoviesMutableLiveData = MutableLiveData<List<HorizontalItem>>()
    private val upcomingMoviesMutableLiveData = MutableLiveData<List<HorizontalItem>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val popularMoviesLiveData: LiveData<List<HorizontalItem>> = popularMoviesMutableLiveData
    val topRatedMoviesLiveData: LiveData<List<HorizontalItem>> = topRatedMoviesMutableLiveData
    val upcomingMoviesLiveData: LiveData<List<HorizontalItem>> = upcomingMoviesMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData


    fun onViewLoaded() {
        getPopularMovies(1)
        getTopRatedMovies(1)
        getUpcomingMovies(1)
    }

    private fun getPopularMovies(page: Int) {
        moviesUseCase.getMovies(MoviesType.POPULAR, page)
                .map { it.results.mapIndexed { position, movie -> popularMovieToHorizontalListItem(movie, position) } }
                .ioMainSubscribe(
                        { response -> popularMoviesMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    private fun getTopRatedMovies(page: Int) {
        moviesUseCase.getMovies(MoviesType.TOP_RATED, page)
                .map { it.results.map { topRatedMovieToHorizontalListItem(it) } }
                .ioMainSubscribe(
                        { response -> topRatedMoviesMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    private fun getUpcomingMovies(page: Int) {
        moviesUseCase.getMovies(MoviesType.UPCOMING, page)
                .map { it.results.map { upcomingMovieToHorizontalListItem(it) } }
                .ioMainSubscribe(
                        { response -> upcomingMoviesMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }
}