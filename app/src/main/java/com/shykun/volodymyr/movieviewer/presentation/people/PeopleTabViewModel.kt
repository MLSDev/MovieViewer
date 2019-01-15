package com.shykun.volodymyr.movieviewer.presentation.people

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.domain.GetPeopleUseCase

class PeopleTabViewModel(private val getPeopleUseCase: GetPeopleUseCase) : ViewModel() {

    private val peopleMutableLiveData = MutableLiveData<List<Person>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val peopleLiveData: LiveData<List<Person>> = peopleMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun onViewLoaded() {
        getPeople(1)
    }

    fun getPeople(page: Int) = getPeopleUseCase.execute(page)
            .doOnSuccess {
                peopleMutableLiveData.value = it
            }
            .doOnError {
                loadingErrorMutableLiveData.value = it.message
            }
            .subscribe()
}