package com.shykun.volodymyr.movieviewer.presentation.people.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.shykun.volodymyr.movieviewer.data.entity.Role
import com.shykun.volodymyr.movieviewer.data.network.response.PersonDetailsResponse
import com.shykun.volodymyr.movieviewer.domain.PersonDetailsUseCase

class PersonDetailsViewModel(private val personDetailsUseCase: PersonDetailsUseCase) : ViewModel() {

    private val personDetailsMutableLiveData = MutableLiveData<PersonDetailsResponse>()
    private val personCastMutableLiveData = MutableLiveData<List<Role>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val personDetailsLiveData: LiveData<PersonDetailsResponse> = personDetailsMutableLiveData
    val personCastLiveData: LiveData<List<Role>> = personCastMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    val castCount = ObservableField<String>("")

    fun onViewLoaded(personId: Int) {
        getPersonDetails(personId)
        getPersonCast(personId)
    }

    private fun getPersonDetails(personId: Int) = personDetailsUseCase.getPersonDetails(personId)
            .subscribe(
                    { response -> personDetailsMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    private fun getPersonCast(personId: Int) = personDetailsUseCase.getPersonCast(personId)
            .subscribe(
                    { response ->
                        personCastMutableLiveData.value = response
                        if (response.isNotEmpty())
                            castCount.set(response.size.toString())
                    },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}