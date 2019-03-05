package com.shykun.volodymyr.movieviewer.presentation.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import com.shykun.volodymyr.movieviewer.presentation.model.ItemType
import com.shykun.volodymyr.movieviewer.presentation.model.SearchListItem
import com.shykun.volodymyr.movieviewer.presentation.utils.movieToSearchListItem
import com.shykun.volodymyr.movieviewer.presentation.utils.personToSearchListItem
import com.shykun.volodymyr.movieviewer.presentation.utils.tvToSearchListItem

class SearchViewModel(private val searchUseCase: SearchUseCase) : ViewModel() {

    private val searchResultsMutableLiveData = MutableLiveData<List<SearchListItem>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val searchResultsLiveData: LiveData<List<SearchListItem>> = searchResultsMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun search(query: String, itemType: ItemType) = when (itemType) {
        ItemType.MOVIE -> searchMovies(query)
        ItemType.TV -> searchTv(query)
        ItemType.PERSON -> searchPeople(query)
    }

    fun searchMovies(query: String) = searchUseCase.searchMovies(query)
            .subscribe(
                    { response -> searchResultsMutableLiveData.value = response.results.map { movieToSearchListItem(it) } },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun searchTv(query: String) = searchUseCase.searchTv(query)
            .subscribe(
                    { response -> searchResultsMutableLiveData.value = response.results.map { tvToSearchListItem(it) } },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun searchPeople(query: String) = searchUseCase.searchPeople(query)
            .subscribe(
                    { response -> searchResultsMutableLiveData.value = response.map { personToSearchListItem(it) } },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}