package com.shykun.volodymyr.movieviewer.presentation.profile

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(
        private val profileUseCase: ProfileUseCase,
        private val prefs: SharedPreferences): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(profileUseCase, prefs) as T
    }
}