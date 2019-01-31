package com.shykun.volodymyr.movieviewer.presentation.people.tab

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.domain.PeopleUseCase

class PeopleTabViewModel(
        private val peopleUseCase: PeopleUseCase) : ViewModel() {

    private val peopleMutableLiveData = MutableLiveData<List<Person>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val peopleLiveData: LiveData<List<Person>> = peopleMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun onViewLoaded() {
        getPeople(1)
    }

    fun getPeople(page: Int) = peopleUseCase.getPopularPeople(page)
            .subscribe(
                    { response -> peopleMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun searchPeople(query: String, page: Int) = peopleUseCase.searchPeople(query, page)
            .subscribe(
                    { response -> peopleMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}