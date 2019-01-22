package com.shykun.volodymyr.movieviewer.presentation.discover

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

class DiscoverViewModel : ViewModel() {

    val genres = ObservableField<String>()
    val year = ObservableField<Int>()
    val rating = ObservableField<Int>()
    val type = ObservableField<Int>()

}