package com.shykun.volodymyr.movieviewer.presentation.people.details

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.PersonDetailsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PersonDetailsViewModelFactory @Inject constructor(private val personDetailsUseCase: PersonDetailsUseCase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PersonDetailsViewModel(personDetailsUseCase, Schedulers.io(), AndroidSchedulers.mainThread()) as T
    }
}