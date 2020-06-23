package com.shykun.volodymyr.movieviewer.presentation.tv.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.domain.DiscoverUseCase
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import com.shykun.volodymyr.movieviewer.domain.TvUseCase
import com.shykun.volodymyr.movieviewer.presentation.model.VerticalItemList
import com.shykun.volodymyr.movieviewer.presentation.utils.tvResponseToVerticalItemList
import io.reactivex.Scheduler

class TvListViewModel(private val tvUseCase: TvUseCase,
                      private val profileUseCase: ProfileUseCase,
                      private val searchUseCase: SearchUseCase,
                      private val discoverUseCase: DiscoverUseCase,
                      private val backgroundScheduler: Scheduler,
                      private val mainScheduler: Scheduler) : ViewModel() {

    private val tvListMutableLiveData = MutableLiveData<VerticalItemList>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val tvListLiveData: LiveData<VerticalItemList> = tvListMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun getTvList(tvType: TvType, page: Int) = tvUseCase
            .getTv(tvType, page)
            .map { tvResponseToVerticalItemList(it) }
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> tvListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun searchTv(query: String, page: Int) = searchUseCase
            .searchTv(query, page)
            .map { tvResponseToVerticalItemList(it) }
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> tvListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun discoverTv(airDate: String?, rating: Int?, genres: String?, page: Int) =
            discoverUseCase.discoverTv(airDate, rating, genres, page)
                    .map { tvResponseToVerticalItemList(it) }
                    .subscribeOn(backgroundScheduler)
                    .observeOn(mainScheduler)
                    .subscribe(
                            { response -> tvListMutableLiveData.value = response },
                            { error -> loadingErrorMutableLiveData.value = error.message }
                    )

    fun getRatedTv(sessionId: String, page: Int) = profileUseCase
            .getRatedTv(sessionId, page)
            .map { tvResponseToVerticalItemList(it) }
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> tvListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getTvWatchlist(sessionId: String, page: Int) = profileUseCase
            .getTvWatchList(sessionId, page)
            .map { tvResponseToVerticalItemList(it) }
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> tvListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getFavoriteTv(sessionId: String, page: Int) = profileUseCase
            .getFavoriteTv(sessionId, page)
            .map { tvResponseToVerticalItemList(it) }
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> tvListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}