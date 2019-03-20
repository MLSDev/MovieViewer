package com.shykun.volodymyr.movieviewer.presentation.people.tab

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.domain.PeopleUseCase
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import io.reactivex.Scheduler

class PeopleTabViewModel(
        private val peopleUseCase: PeopleUseCase,
        private val searchUseCase: SearchUseCase,
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler) : ViewModel() {

    private val peopleMutableLiveData = MutableLiveData<List<Person>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val peopleLiveData: LiveData<List<Person>> = peopleMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun getPeople(page: Int) = peopleUseCase.getPopularPeople(page)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> peopleMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun searchPeople(query: String, page: Int) = searchUseCase.searchPeople(query, page)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> peopleMutableLiveData.value = response.results },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}