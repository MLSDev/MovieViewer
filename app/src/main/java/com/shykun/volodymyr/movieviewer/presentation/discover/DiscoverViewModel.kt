package com.shykun.volodymyr.movieviewer.presentation.discover

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.shykun.volodymyr.movieviewer.data.entity.Genre
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.domain.DiscoverUseCase
import io.reactivex.disposables.Disposable

const val MOVIE_TYPE = 0
const val TV_TYPE = 1

class DiscoverViewModel(private val discoverUseCase: DiscoverUseCase) : ViewModel() {

    val genreNames = ObservableField<String>("All genres")

    val genres = ObservableField<ArrayList<Genre>>(ArrayList())
    var year = ObservableField(-1)
    var rating = ObservableField(-1)
    var type = ObservableField(MOVIE_TYPE)

    private var discoveredMoviesMutableLiveData = MutableLiveData<List<Movie>>()
    private var discoveredTvMutableLiveData = MutableLiveData<List<Tv>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val discoveredMovieLiveData: LiveData<List<Movie>> = discoveredMoviesMutableLiveData
    val discoveredTvLiveData: LiveData<List<Tv>> = discoveredTvMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    var nextPage = 1

    fun load() {
        if (type.get() == MOVIE_TYPE)
            discoverMovies(nextPage)
        else
            discoverTv(nextPage)

        nextPage++
    }

    fun clearFilters() {
        genres.set(ArrayList())
        year.set(-1)
        rating.set(-1)
        genreNames.set("All genres")
        nextPage = 1
    }

    private fun discoverMovies(page: Int): Disposable? {

        val y = if (year.get() == -1) null else year.get()
        val r = if (rating.get() == -1) null else rating.get()
        val g = if (genres.get()!!.isEmpty()) null else genres.get()!!.joinToString { it.id.toString() }

        return discoverUseCase
                .discoverMovies(y, r, g, page)
                .doOnSuccess {
                    discoveredMoviesMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }

    private fun discoverTv(page: Int) {
        val y = if (year.get() == -1) null else year.get()
        val r = if (rating.get() == -1) null else rating.get()
        val g = if (genres.get()!!.isEmpty()) null else genres.get()!!.joinToString { it.id.toString() }

        discoverUseCase
                .discoverTv("$y-01-01", r, g, page)
                .doOnSuccess {
                    discoveredTvMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }
}



