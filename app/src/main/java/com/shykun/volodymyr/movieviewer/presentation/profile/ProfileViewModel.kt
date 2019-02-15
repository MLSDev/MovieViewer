package com.shykun.volodymyr.movieviewer.presentation.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.network.response.AccountDetailsResponse
import com.shykun.volodymyr.movieviewer.data.network.response.RequestTokenResponse
import com.shykun.volodymyr.movieviewer.data.network.response.SessionIdResponse
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase

class ProfileViewModel(private val profileUseCase: ProfileUseCase) : ViewModel() {

    private val requestTokenMutableLiveData = MutableLiveData<RequestTokenResponse>()
    private val sessionIdMutableLiveData = MutableLiveData<SessionIdResponse>()
    private val accountDetailsMutableLiveData = MutableLiveData<AccountDetailsResponse>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val requestTokenLiveData: LiveData<RequestTokenResponse> = requestTokenMutableLiveData
    val sessionIdLiveData: LiveData<SessionIdResponse> = sessionIdMutableLiveData
    val accountDetailsLiveData: LiveData<AccountDetailsResponse> = accountDetailsMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun getRequestToken() = profileUseCase.getRequestToken()
            .subscribe(
                    { response -> requestTokenMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun createSessionId(requestToken: String) = profileUseCase.createSessionId(requestToken)
            .subscribe(
                    { response -> sessionIdMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
    fun getAccountDetails(sessionId: String) = profileUseCase.getAccountDetails(sessionId)
            .subscribe(
                    {response -> accountDetailsMutableLiveData.value = response },
                    {error -> loadingErrorMutableLiveData.value = error.message}
            )
}