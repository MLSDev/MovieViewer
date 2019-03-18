package com.shykun.volodymyr.movieviewer.presentation.tv.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.view.View
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.data.network.response.ItemAccountStateResponse
import com.shykun.volodymyr.movieviewer.data.network.response.PostResponse
import com.shykun.volodymyr.movieviewer.data.network.response.TvDetailsResponse
import com.shykun.volodymyr.movieviewer.domain.TvDetailsUseCase
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalItem
import io.reactivex.Scheduler

class TvDetailsViewModel(
        private val tvDetailsUseCase: TvDetailsUseCase,
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler) : ViewModel() {

    private val tvDetailsMutableLiveData = MutableLiveData<TvDetailsResponse>()
    private val recommendedTvMutableLiveData = MutableLiveData<List<HorizontalItem>>()
    private val tvReviewsMutableLiveData = MutableLiveData<List<Review>>()
    private val tvActorsMutableLiveData = MutableLiveData<List<HorizontalItem>>()

    private val rateTvMutableLiveData = MutableLiveData<PostResponse>()
    private val addToWatchListMutableLiveData = MutableLiveData<PostResponse>()
    private val markAsFavoriteMutableLiveData = MutableLiveData<PostResponse>()

    private val tvAccountStatesMutableLiveData = MutableLiveData<ItemAccountStateResponse>()

    private val loadingErrorMutableLiveData = MutableLiveData<String>()


    val tvDetailsLiveData: LiveData<TvDetailsResponse> = tvDetailsMutableLiveData
    val recommendedTvLiveData: LiveData<List<HorizontalItem>> = recommendedTvMutableLiveData
    val tvReviewsLiveData: LiveData<List<Review>> = tvReviewsMutableLiveData
    val tvActorsLiveData: LiveData<List<HorizontalItem>> = tvActorsMutableLiveData

    val rateTvLiveData: LiveData<PostResponse> = rateTvMutableLiveData
    val addToWatchListLiveData: LiveData<PostResponse> = addToWatchListMutableLiveData
    val markAsFavoriteLiveData: LiveData<PostResponse> = markAsFavoriteMutableLiveData

    val tvAccountStatesLiveData: LiveData<ItemAccountStateResponse> = tvAccountStatesMutableLiveData

    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    val castTitleVisibility = ObservableField<Int>(View.GONE)
    val recommendedTvTitleVisibility = ObservableField<Int>(View.GONE)
    val reviewsTitleVisibility = ObservableField<Int>(View.GONE)
    val trailerTitleVisibility = ObservableField<Int>(View.VISIBLE)

    val castCount = ObservableField<String>("")
    val recommendedTvCount = ObservableField<String>("")
    val reviewsCount = ObservableField<String>("")

    fun onViewLoaded(tvId: Int) {
        getTvDetails(tvId)
        getRecommendedTv(tvId)
        getTvReviews(tvId)
        getTvCast(tvId)
    }

    fun getTvDetails(tvId: Int) = tvDetailsUseCase.getTvDetails(tvId)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> tvDetailsMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getRecommendedTv(tvId: Int) = tvDetailsUseCase.getRecommendedTv(tvId)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response ->
                        recommendedTvMutableLiveData.value = response
                        if (response.isNotEmpty()) {
                            recommendedTvTitleVisibility.set(View.VISIBLE)
                            recommendedTvCount.set(response.size.toString())
                        }
                    },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getTvReviews(tvId: Int) = tvDetailsUseCase.getTvReviews(tvId)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response ->
                        tvReviewsMutableLiveData.value = response
                        if (response.isNotEmpty()) {
                            reviewsTitleVisibility.set(View.VISIBLE)
                            reviewsCount.set(response.size.toString())
                        }
                    },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getTvCast(tvId: Int) = tvDetailsUseCase.getTvCast(tvId)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response ->
                        tvActorsMutableLiveData.value = response
                        if (response.isNotEmpty()) {
                            castTitleVisibility.set(View.VISIBLE)
                            castCount.set(response.size.toString())
                        }
                    },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun rateTv(tvId: Int, rating: Float, sessionId: String) = tvDetailsUseCase.rateTv(tvId, rating, sessionId)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> rateTvMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )


    fun deleteTvRating(tvId: Int, sessionId: String) = tvDetailsUseCase.deleteTvRating(tvId, sessionId)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> rateTvMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun addToWatchlist(tvId: Int, sessionId: String) = tvDetailsUseCase.addToWatchlist(tvId, sessionId)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> addToWatchListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun deleteFromWatchlist(tvId: Int, sessionId: String) = tvDetailsUseCase.deleteFromWatchlist(tvId, sessionId)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> addToWatchListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun addToFavorites(tvId: Int, sessionId: String) = tvDetailsUseCase.addToFavorites(tvId, sessionId)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> markAsFavoriteMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun deleteFromFavorites(tvId: Int, sessionId: String) = tvDetailsUseCase.deleteFromFavorites(tvId, sessionId)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> markAsFavoriteMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getTvAccountStates(tvId: Int, sessionId: String) = tvDetailsUseCase.getTvAccountStates(tvId, sessionId)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> tvAccountStatesMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}