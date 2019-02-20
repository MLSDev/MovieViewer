package com.shykun.volodymyr.movieviewer.presentation.tv.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.view.View
import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.network.response.PostResponse
import com.shykun.volodymyr.movieviewer.data.network.response.TvDetailsResponse
import com.shykun.volodymyr.movieviewer.data.network.response.TvResponse
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import com.shykun.volodymyr.movieviewer.domain.TvDetailsUseCase

class TvDetailsViewModel(
        private val tvDetailsUseCase: TvDetailsUseCase,
        private val profileUseCase: ProfileUseCase) : ViewModel() {

    private val tvDetailsMutableLiveData = MutableLiveData<TvDetailsResponse>()
    private val recommendedTvMutableLiveData = MutableLiveData<List<Tv>>()
    private val tvReviewsMutableLiveData = MutableLiveData<List<Review>>()
    private val tvCastMutableLiveData = MutableLiveData<List<Actor>>()

    private val rateTvMutableLiveData = MutableLiveData<PostResponse>()
    private val addToWatchListMutableLiveData = MutableLiveData<PostResponse>()
    private val markAsFavoriteMutableLiveData = MutableLiveData<PostResponse>()

    private val ratedTvMutableLiveData = MutableLiveData<TvResponse>()
    private val tvWatchlistMutableLiveData = MutableLiveData<TvResponse>()
    private val favoriteTvMutableLiveData = MutableLiveData<TvResponse>()

    private val loadingErrorMutableLiveData = MutableLiveData<String>()


    val tvDetailsLiveData: LiveData<TvDetailsResponse> = tvDetailsMutableLiveData
    val recommendedTvLiveData: LiveData<List<Tv>> = recommendedTvMutableLiveData
    val tvReviewsLiveData: LiveData<List<Review>> = tvReviewsMutableLiveData
    val tvCastLiveData: LiveData<List<Actor>> = tvCastMutableLiveData

    val rateTvLiveData: LiveData<PostResponse> = rateTvMutableLiveData
    val addToWatchListLiveData: LiveData<PostResponse> = addToWatchListMutableLiveData
    val markAsFavoriteLiveData: LiveData<PostResponse> = markAsFavoriteMutableLiveData

    val ratedTvLiveData: LiveData<TvResponse> = ratedTvMutableLiveData
    val tvWatchlistLiveData: LiveData<TvResponse> = tvWatchlistMutableLiveData
    val favoriteTvLiveData: LiveData<TvResponse> = favoriteTvMutableLiveData

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

    private fun getTvDetails(tvId: Int) = tvDetailsUseCase.getTvDetails(tvId)
            .subscribe(
                    { response -> tvDetailsMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    private fun getRecommendedTv(tvId: Int) = tvDetailsUseCase.getRecommendedTv(tvId)
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

    private fun getTvReviews(tvId: Int) = tvDetailsUseCase.getTvReviews(tvId)
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

    private fun getTvCast(tvId: Int) = tvDetailsUseCase.getTvCast(tvId)
            .subscribe(
                    { response ->
                        tvCastMutableLiveData.value = response
                        if (response.isNotEmpty()) {
                            castTitleVisibility.set(View.VISIBLE)
                            castCount.set(response.size.toString())
                        }
                    },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun rateTv(tvId: Int, rating: Float, sessionId: String) = tvDetailsUseCase.rateTv(tvId, rating, sessionId)
            .subscribe(
                    { response -> rateTvMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )


    fun deleteTvRating(tvId: Int, sessionId: String) = tvDetailsUseCase.deleteTvRating(tvId, sessionId)
            .subscribe(
                    { response -> rateTvMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun addToWatchlist(tvId: Int, sessionId: String) = tvDetailsUseCase.addToWatchlist(tvId, sessionId)
            .subscribe(
                    { response -> addToWatchListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun removeFromWatchlist(tvId: Int, sessionId: String) = tvDetailsUseCase.removeFromWatchlist(tvId, sessionId)
            .subscribe(
                    { response -> addToWatchListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun markAsFavorite(tvId: Int, sessionId: String) = tvDetailsUseCase.markAsFavorite(tvId, sessionId)
            .subscribe(
                    { response -> markAsFavoriteMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun removeFromFavorites(tvId: Int, sessionId: String) = tvDetailsUseCase.removeFromFavorites(tvId, sessionId)
            .subscribe(
                    { response -> markAsFavoriteMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}