package com.shykun.volodymyr.movieviewer.data.network

import com.shykun.volodymyr.movieviewer.data.network.response.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "a44576f6889f55561580856f29b6fe14"

interface ApiService {

    //movie

    @GET("movie/{movies_type}")
    fun getMovies(
            @Path("movies_type") moviesType: String,
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
            @Path("movie_id") movieId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<MovieDetailsResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
            @Path("movie_id") movieId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<PictureCreditsResponse>

    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(
            @Path("movie_id") movieId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<ReviewsResponse>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendedMovies(
            @Path("movie_id") movieId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<MoviesResponse>

    //tv

    @GET("tv/{tv_type}")
    fun getTV(
            @Path("tv_type") tvType: String,
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<TVResponse>

    @GET("tv/{tv_id}")
    fun getTvDetails(
            @Path("tv_id") tvId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<TvDetailsResponse>

    @GET("tv/{tv_id}/credits")
    fun getTvCredits(
            @Path("tv_id") tvId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<PictureCreditsResponse>

    @GET("tv/{tv_id}/recommendations")
    fun getRecommendedTv(
            @Path("tv_id") tvId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<TVResponse>

    @GET("tv/{tv_id}/reviews")
    fun getTvReviews(
            @Path("tv_id") tvId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<ReviewsResponse>

    //people

    @GET("person/popular")
    fun getPopularPeople(
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<PeopleResponse>

    @GET("person/{person_id}")
    fun getPersonDetails(
            @Path("person_id") personId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<PersonDetailsResponse>

    @GET("person/{person_id}/combined_credits")
    fun getPersonCombinedCredits(
            @Path("person_id") personId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<PersonCreditsResponse>
}