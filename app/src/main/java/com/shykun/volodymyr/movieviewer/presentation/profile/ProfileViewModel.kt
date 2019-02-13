package com.shykun.volodymyr.movieviewer.presentation.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.network.response.RequestTokenResponse
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase

class ProfileViewModel(private val profileUseCase: ProfileUseCase) : ViewModel() {

    private val requestTokenMutableLiveData = MutableLiveData<RequestTokenResponse>()
    private val loaingErrorMutableLiveData = MutableLiveData<String>()

    val requestTokenLiveData: LiveData<RequestTokenResponse> = requestTokenMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loaingErrorMutableLiveData

    fun getRequestToken() = profileUseCase.getRequestToken()
            .subscribe(
                    { response -> requestTokenMutableLiveData.value = response },
                    { error -> loaingErrorMutableLiveData.value = error.message }
            )
}