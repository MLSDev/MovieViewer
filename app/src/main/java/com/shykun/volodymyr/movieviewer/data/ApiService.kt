package com.shykun.volodymyr.movieviewer.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "a44576f6889f55561580856f29b6fe14"

interface ApiService {
    @GET("movie/popular")
    fun getPopulatMovies(@Query("api_key") api_key: String = API_KEY): Single<GetMovieResponse>
}