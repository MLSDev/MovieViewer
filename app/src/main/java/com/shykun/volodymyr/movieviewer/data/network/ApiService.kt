package com.shykun.volodymyr.movieviewer.data.network

import com.google.gson.JsonElement
import com.shykun.volodymyr.movieviewer.data.network.body.AddToWatchlistBody
import com.shykun.volodymyr.movieviewer.data.network.body.MarkAsFavoriteBody
import com.shykun.volodymyr.movieviewer.data.network.body.RateBody
import com.shykun.volodymyr.movieviewer.data.network.body.RequestTokenBody
import com.shykun.volodymyr.movieviewer.data.network.response.*
import io.reactivex.Single
import retrofit2.http.*

private const val API_KEY = "a44576f6889f55561580856f29b6fe14"
const val YOUTUBE_API_KEY = "AIzaSyDJwSz4nPegaQDLv4jAMtaPLxrWotRpuHo"

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
            @Query("api_key") api_key: String = API_KEY,
            @Query("append_to_response") appendToResponse: String = "videos"): Single<MovieDetailsResponse>

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

    @GET("search/movie")
    fun searchMovies(
            @Query("query") query: String,
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<MoviesResponse>

    @POST("movie/{movie_id}/rating")
    fun rateMovie(
            @Path("movie_id") movieId: Int,
            @Body rateBody: RateBody,
            @Query("session_id") sessionId: String,
            @Query("api_key") api_key: String = API_KEY): Single<PostResponse>

    @DELETE("movie/{movie_id}/rating")
    fun deleteMovieRating(
            @Path("movie_id") movieId: Int,
            @Query("session_id") sessionId: String,
            @Query("api_key") api_key: String = API_KEY): Single<PostResponse>

    @GET("movie/{movie_id}/account_states")
    fun getMovieAccountStates(
            @Path("movie_id") movieId: Int,
            @Query("session_id") sessionId: String,
            @Query("api_key") api_key: String = API_KEY): Single<JsonElement>

    //tv

    @GET("tv/{tv_type}")
    fun getTV(
            @Path("tv_type") tvType: String,
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<TvResponse>

    @GET("tv/{tv_id}")
    fun getTvDetails(
            @Path("tv_id") tvId: Int,
            @Query("api_key") api_key: String = API_KEY,
            @Query("append_to_response") appendToResponse: String = "videos"): Single<TvDetailsResponse>

    @GET("tv/{tv_id}/credits")
    fun getTvCredits(
            @Path("tv_id") tvId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<PictureCreditsResponse>

    @GET("tv/{tv_id}/recommendations")
    fun getRecommendedTv(
            @Path("tv_id") tvId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<TvResponse>

    @GET("tv/{tv_id}/reviews")
    fun getTvReviews(
            @Path("tv_id") tvId: Int,
            @Query("api_key") api_key: String = API_KEY): Single<ReviewsResponse>

    @GET("search/tv")
    fun searchTv(
            @Query("query") query: String,
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<TvResponse>

    @POST("tv/{tv_id}/rating")
    fun rateTv(
            @Path("tv_id") tvId: Int,
            @Body rateBody: RateBody,
            @Query("session_id") sessionId: String,
            @Query("api_key") api_key: String = API_KEY): Single<PostResponse>

    @DELETE("tv/{tv_id}/rating")
    fun deleteTvRating(
            @Path("tv_id") tvId: Int,
            @Query("session_id") sessionId: String,
            @Query("api_key") api_key: String = API_KEY): Single<PostResponse>

    @GET("tv/{tv_id}/account_states")
    fun getTvAccountStates(
            @Path("tv_id") tvId: Int,
            @Query("session_id") sessionId: String,
            @Query("api_key") api_key: String = API_KEY): Single<JsonElement>

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

    @GET("search/person")
    fun searchPeople(
            @Query("query") query: String,
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<PeopleResponse>


    //discover

    @GET("discover/movie")
    fun discoverMovies(
            @Query("page") page: Int,
            @Query("release_date.gte") year: Int?,
            @Query("vote_average.gte") voteAverage: Int?,
            @Query("with_genres") genres: String?,
            @Query("api_key") api_key: String = API_KEY): Single<MoviesResponse>

    @GET("discover/tv")
    fun discoverTV(
            @Query("page") page: Int,
            @Query("first_air_date.gte") airDate: String?,
            @Query("vote_average.gte") voteAverage: Int?,
            @Query("with_genres") genres: String?,
            @Query("api_key") api_key: String = API_KEY): Single<TvResponse>

    //profile

    @GET("authentication/token/new")
    fun getRequestToken(
            @Query("api_key") api_key: String = API_KEY): Single<RequestTokenResponse>

    @POST("authentication/session/new")
    fun createSessionId(
            @Body requestTokenBody: RequestTokenBody,
            @Query("api_key") api_key: String = API_KEY): Single<SessionIdResponse>

    @GET("account")
    fun getAccountDetails(
            @Query("session_id") sessionId: String,
            @Query("api_key") api_key: String = API_KEY): Single<AccountDetailsResponse>

    @POST("account/{account_id}/favorite")
    fun markAsFavorite(
            @Body markAsFavoriteBody: MarkAsFavoriteBody,
            @Query("session_id") sessionId: String,
            @Query("api_key") api_key: String = API_KEY): Single<PostResponse>

    @POST("account/{account_id}/watchlist")
    fun addToWatchlist(
            @Body addToWatchlistBody: AddToWatchlistBody,
            @Query("session_id") sessionId: String,
            @Query("api_key") api_key: String = API_KEY): Single<PostResponse>

    @GET("account/{account_id}/favorite/movies")
    fun getFavoriteMovies(
            @Query("session_id") sessionId: String,
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<MoviesResponse>

    @GET("account/{account_id}/favorite/tv")
    fun getFavoriteTv(
            @Query("session_id") sessionId: String,
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<TvResponse>

    @GET("account/{account_id}/watchlist/movies")
    fun getMovieWatchList(
            @Query("session_id") sessionId: String,
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<MoviesResponse>

    @GET("account/{account_id}/watchlist/tv")
    fun getTvWatchList(
            @Query("session_id") sessionId: String,
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<TvResponse>

    @GET("account/{account_id}/rated/movies")
    fun getRatedMovies(
            @Query("session_id") sessionId: String,
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<MoviesResponse>

    @GET("account/{account_id}/rated/tv")
    fun getRatedTv(
            @Query("session_id") sessionId: String,
            @Query("page") page: Int,
            @Query("api_key") api_key: String = API_KEY): Single<TvResponse>

}