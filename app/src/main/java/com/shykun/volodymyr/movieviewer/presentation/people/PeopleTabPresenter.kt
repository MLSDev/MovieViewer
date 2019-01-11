package com.shykun.volodymyr.movieviewer.presentation.people

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shykun.volodymyr.movieviewer.domain.GetPeopleUseCase
import javax.inject.Inject

@InjectViewState
class PeopleTabPresenter @Inject constructor(private val getPeopleUseCase: GetPeopleUseCase) : MvpPresenter<PeopleTabView>() {

    fun onViewLoaded() {
        getPeople(1)
    }

    fun getPeople(page: Int) = getPeopleUseCase.execute(page)
            .doOnSuccess {
                viewState.showPeople(it)
            }
            .doOnError {
                viewState.showError()
            }
            .subscribe()
}