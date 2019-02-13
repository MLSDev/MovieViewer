package com.shykun.volodymyr.movieviewer.presentation.profile

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(
        private val profileUseCase: ProfileUseCase): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(profileUseCase) as T
    }
}