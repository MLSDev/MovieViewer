package com.shykun.volodymyr.movieviewer.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "your_api_key"

interface ApiService {
    @GET("movie/popular")
    fun getPopulatMovies(@Query("api_key") api_key: String = API_KEY): Single<GetMovieResponse>
}