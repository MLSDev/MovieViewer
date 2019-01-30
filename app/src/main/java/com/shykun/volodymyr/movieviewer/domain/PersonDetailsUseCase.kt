package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import javax.inject.Inject

class PersonDetailsUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun getPersonDetails(personId: Int) = apiClient.getPersonDetails(personId)

    fun getPersonCast(personId: Int) = apiClient.getPersonCombinedCredits(personId).map { it.cast }
}