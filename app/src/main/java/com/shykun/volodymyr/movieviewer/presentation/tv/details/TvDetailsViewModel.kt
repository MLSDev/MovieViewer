package com.shykun.volodymyr.movieviewer.presentation.tv.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.network.response.TvDetailsResponse
import com.shykun.volodymyr.movieviewer.domain.TvDetailsUseCase

class TvDetailsViewModel(private val gtTvDetailsUseCase: TvDetailsUseCase) : ViewModel() {

    private val tvDetailsMutableLiveData = MutableLiveData<TvDetailsResponse>()
    private val recommendedTvMutableLiveData = MutableLiveData<List<Tv>>()
    private val tvReviewsMutableLiveData = MutableLiveData<List<Review>>()
    private val tvCastMutableLiveData = MutableLiveData<List<Actor>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val tvDetailsLiveData: LiveData<TvDetailsResponse> = tvDetailsMutableLiveData
    val recommendedTvLiveData: LiveData<List<Tv>> = recommendedTvMutableLiveData
    val tvReviewsLiveData: LiveData<List<Review>> = tvReviewsMutableLiveData
    val tvCastLiveData: LiveData<List<Actor>> = tvCastMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun onViewLoaded(tvId: Int) {
        getTvDetails(tvId)
        getRecommendedTv(tvId)
        getTvReviews(tvId)
        getTvCast(tvId)
    }

    private fun getTvDetails(tvId: Int) = gtTvDetailsUseCase.getTvDetails(tvId)
            .doOnSuccess {
                tvDetailsMutableLiveData.value = it
            }
            .doOnError {
                loadingErrorMutableLiveData.value = it.message
            }
            .subscribe()

    private fun getRecommendedTv(tvId: Int) = gtTvDetailsUseCase.getRecommendedTv(tvId)
            .doOnSuccess {
                recommendedTvMutableLiveData.value = it
            }
            .doOnError {
                loadingErrorMutableLiveData.value = it.message
            }
            .subscribe()

    private fun getTvReviews(tvId: Int) = gtTvDetailsUseCase.getTvReviews(tvId)
            .doOnSuccess {
                tvReviewsMutableLiveData.value = it
            }
            .doOnError {
                loadingErrorMutableLiveData.value = it.message
            }
            .subscribe()

    private fun getTvCast(tvId: Int) = gtTvDetailsUseCase.getTvCast(tvId)
            .doOnSuccess {
                tvCastMutableLiveData.value = it
            }
            .doOnError {
                loadingErrorMutableLiveData.value = it.message
            }
            .subscribe()
}