package com.shykun.volodymyr.movieviewer.presentation.discover

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.shykun.volodymyr.movieviewer.data.entity.Genre
import com.shykun.volodymyr.movieviewer.domain.DiscoverUseCase
import com.shykun.volodymyr.movieviewer.presentation.model.VerticalItemList
import com.shykun.volodymyr.movieviewer.presentation.utils.movieResponseToVerticalItemList
import com.shykun.volodymyr.movieviewer.presentation.utils.tvResponseToVerticalItemList
import io.reactivex.Scheduler

const val MOVIE_TYPE = 0
const val TV_TYPE = 1

class DiscoverViewModel(
        private val discoverUseCase: DiscoverUseCase,
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler) : ViewModel() {

    val genreNames = ObservableField<String>("All genres")

    val genres = ObservableField<ArrayList<Genre>>(ArrayList())
    var year = ObservableField(-1)
    var rating = ObservableField(-1)
    var type = ObservableField(MOVIE_TYPE)

    private var discoveredMoviesMutableLiveData = MutableLiveData<VerticalItemList>()
    private var discoveredTvMutableLiveData = MutableLiveData<VerticalItemList>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val discoveredMovieLiveData: LiveData<VerticalItemList> = discoveredMoviesMutableLiveData
    val discoveredTvLiveData: LiveData<VerticalItemList> = discoveredTvMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    var nextPage = 1
    var totalPageCount = 1

    fun load() {
        if (nextPage <= totalPageCount) {
            if (type.get() == MOVIE_TYPE)
                discoverMovies(nextPage)
            else
                discoverTv(nextPage)

            nextPage++
        }
    }

    fun clearFilters() {
        genres.set(ArrayList())
        year.set(-1)
        rating.set(-1)
        genreNames.set("All genres")
        nextPage = 1
    }

    fun clearResults() {
        discoveredMoviesMutableLiveData.value = null
        discoveredTvMutableLiveData.value = null
        nextPage = 1

    }

    private fun discoverMovies(page: Int) {

        val tmpYear = if (year.get() == -1) null else year.get()
        val tmpRating = if (rating.get() == -1) null else rating.get()
        val tmpGenres = if (genres.get()!!.isEmpty()) null else genres.get()!!.joinToString(separator = ",") { it.id.toString() }

        discoverUseCase
                .discoverMovies(tmpYear, tmpRating, tmpGenres, page)
                .map { movieResponseToVerticalItemList(it) }
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe(
                        { response ->
                            discoveredMoviesMutableLiveData.value = response
                            totalPageCount = response.totalItemsCount
                        },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    private fun discoverTv(page: Int) {
        val tmpYear = if (year.get() == -1) null else "${year.get()}-01-01"
        val tmpRating = if (rating.get() == -1) null else rating.get()
        val tmpGenres = if (genres.get()!!.isEmpty()) null else genres.get()!!.joinToString(separator = ",") { it.id.toString() }

        discoverUseCase
                .discoverTv(tmpYear, tmpRating, tmpGenres, page)
                .map { tvResponseToVerticalItemList(it) }
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe(
                        { response ->
                            discoveredTvMutableLiveData.value = response
                            totalPageCount = response.totalItemsCount
                        },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }
}



