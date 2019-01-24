package com.shykun.volodymyr.movieviewer.presentation.discover

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.shykun.volodymyr.movieviewer.data.entity.Genre
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.domain.DiscoverUseCase

const val MOVIE_TYPE = 0
const val TV_TYPE = 1

class DiscoverViewModel(private val discoverUseCase: DiscoverUseCase) : ViewModel() {

    val genreNames =  ObservableField<String>("All genres")

    val genres = ObservableField<ArrayList<Genre>>()
    var year = ObservableField(-1)
    var rating = ObservableField(-1)
    var type = ObservableField(MOVIE_TYPE)

    private var discoveredMoviesMutableLiveData = MutableLiveData<List<Movie>>()
    private var discoveredTvMutableLiveData = MutableLiveData<List<Tv>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val discoveredMovieLiveData: LiveData<List<Movie>> = discoveredMoviesMutableLiveData
    val discoveredTvLiveData: LiveData<List<Tv>> = discoveredTvMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun onViewLoaded() {
        if (type.get() == MOVIE_TYPE)
            discoverMovies()
        else
            discoverTv()
    }

    fun clearFilters() {
        genres.set(ArrayList())
        year.set(-1)
        rating.set(-1)
        genreNames.set("All genres")
    }

    private fun discoverMovies() = discoverUseCase
            .discoverMovies(year.get(), rating.get(), genres.get()?.joinToString { it.id.toString() })
            .doOnSuccess {
                discoveredMoviesMutableLiveData.value = it
            }
            .doOnError {
                loadingErrorMutableLiveData.value = it.message
            }
            .subscribe()

    private fun discoverTv() = discoverUseCase
            .discoverTv("$year-01-01", rating.get(), genres.get()?.joinToString { it.id.toString() })
            .doOnSuccess {
                discoveredTvMutableLiveData.value = it
            }
            .doOnError {
                loadingErrorMutableLiveData.value = it.message
            }
            .subscribe()
}



