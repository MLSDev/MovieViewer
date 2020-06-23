package com.shykun.volodymyr.movieviewer.presentation.people.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.shykun.volodymyr.movieviewer.data.network.response.PersonDetailsResponse
import com.shykun.volodymyr.movieviewer.domain.PersonDetailsUseCase
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalItem
import com.shykun.volodymyr.movieviewer.presentation.utils.roleToHorizontalListItem
import io.reactivex.Scheduler

class PersonDetailsViewModel(
        private val personDetailsUseCase: PersonDetailsUseCase,
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler) : ViewModel() {

    private val personDetailsMutableLiveData = MutableLiveData<PersonDetailsResponse>()
    private val personCastMutableLiveData = MutableLiveData<List<HorizontalItem>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val personDetailsLiveData: LiveData<PersonDetailsResponse> = personDetailsMutableLiveData
    val personCastLiveData: LiveData<List<HorizontalItem>> = personCastMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    val castCount = ObservableField<String>("")

    fun onViewLoaded(personId: Int) {
        getPersonDetails(personId)
        getPersonCast(personId)
    }

    fun getPersonDetails(personId: Int) = personDetailsUseCase.getPersonDetails(personId)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response -> personDetailsMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getPersonCast(personId: Int) = personDetailsUseCase.getPersonCast(personId)
            .map { it.cast.map { roleToHorizontalListItem(it) } }
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                    { response ->
                        personCastMutableLiveData.value = response
                        if (response.isNotEmpty())
                            castCount.set(response.size.toString())
                    },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}