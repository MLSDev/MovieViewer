package com.shykun.volodymyr.movieviewer.presentation.tv.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.domain.GetTvUseCase
import javax.inject.Inject

@InjectViewState
class TvListPresenter @Inject constructor(private val getTvUseCase: GetTvUseCase) : MvpPresenter<TvListView>() {

    lateinit var tvType: TvType

    fun onViewLoaded(tvType: TvType) {
        this.tvType = tvType
        getTvList(1)
    }

    fun getTvList(page: Int) = getTvUseCase.execute(tvType, page)
            .doOnSuccess {
                viewState.showTvList(it)
            }
            .doOnError {
                viewState.showError()
            }
            .subscribe()
}