package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import com.shykun.volodymyr.movieviewer.presentation.model.VerticalItemList
import com.shykun.volodymyr.movieviewer.presentation.utils.ioMainSubscribe
import com.shykun.volodymyr.movieviewer.presentation.utils.movieResponseToVerticalItemList
import io.reactivex.android.schedulers.AndroidSchedulers

class MovieListViewModel(private val moviesUseCase: MoviesUseCase,
                         private val profileUseCase: ProfileUseCase,
                         private val searchUseCase: SearchUseCase) : ViewModel() {

    private val moviesMutableLiveData = MutableLiveData<VerticalItemList>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val moviesLiveData: LiveData<VerticalItemList> = moviesMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun getMovies(page: Int, moviesType: MoviesType) = moviesUseCase.getMovies(moviesType, page)
            .map { movieResponseToVerticalItemList(it) }
            .ioMainSubscribe(
                    { response -> moviesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun searchMovie(query: String, page: Int) = searchUseCase.searchMovies(query, page)
            .map { movieResponseToVerticalItemList(it) }
            .ioMainSubscribe(
                    { response -> moviesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getRatedMovies(sessionId: String, page: Int) = profileUseCase.getRatedMovies(sessionId, page)
            .map { movieResponseToVerticalItemList(it) }
            .ioMainSubscribe(
                    { response -> moviesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getMovieWatchlist(sessionId: String, page: Int) = profileUseCase.getMovieWatchList(sessionId, page)
            .map { movieResponseToVerticalItemList(it) }
            .ioMainSubscribe(
                    { response -> moviesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getFavoriteMovies(sessionId: String, page: Int) = profileUseCase.getFavoriteMovies(sessionId, page)
            .map { movieResponseToVerticalItemList(it) }
            .ioMainSubscribe(
                    { response -> moviesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}