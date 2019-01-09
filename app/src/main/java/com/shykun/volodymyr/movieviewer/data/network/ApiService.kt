package com.shykun.volodymyr.movieviewer.data.network

import com.shykun.volodymyr.movieviewer.data.network.response.GetMovieResponse
import com.shykun.volodymyr.movieviewer.data.network.response.GetPeopleResponse
import com.shykun.volodymyr.movieviewer.data.network.response.GetTVResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "a44576f6889f55561580856f29b6fe14"

interface ApiService {

    @GET("movie/{movies_type}")
    fun getMovies(@Path("movies_type") moviesType: String,
                  @Query("page") page: Int,
                  @Query("api_key") api_key: String = API_KEY): Single<GetMovieResponse>

    @GET("tv/{tv_type}")
    fun getTV(@Path("tv_type") tvType: String,
              @Query("page") page: Int,
              @Query("api_key") api_key: String = API_KEY): Single<GetTVResponse>

    @GET("person/popular")
    fun getPopularPeople(
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<GetPeopleResponse>
}