package com.shykun.volodymyr.movieviewer.presentation.people.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.domain.PeopleUseCase

class PeopleSearchViewModel(private val peopleUseCase: PeopleUseCase) : ViewModel() {

    private val searchedPeopleMutableLiveData = MutableLiveData<List<Person>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val searchedPeopleLiveData: LiveData<List<Person>> = searchedPeopleMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun searchPeople(query: String) = peopleUseCase.searchPeople(query)
            .subscribe(
                    { response -> searchedPeopleMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}