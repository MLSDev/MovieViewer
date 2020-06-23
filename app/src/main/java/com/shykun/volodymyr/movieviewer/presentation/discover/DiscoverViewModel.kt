package com.shykun.volodymyr.movieviewer.presentation.discover

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.shykun.volodymyr.movieviewer.data.entity.Genre

const val MOVIE_TYPE = 0
const val TV_TYPE = 1

class DiscoverViewModel : ViewModel() {

    val genreNames = ObservableField<String>("All genres")

    val genres = ObservableField<ArrayList<Genre>>(ArrayList())
    var year = ObservableField(-1)
    var rating = ObservableField(-1)
    var type = ObservableField(MOVIE_TYPE)

    fun clearFilters() {
        genres.set(ArrayList())
        year.set(-1)
        rating.set(-1)
        genreNames.set("All genres")
    }
}



