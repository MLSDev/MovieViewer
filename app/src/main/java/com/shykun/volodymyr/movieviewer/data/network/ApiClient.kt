package com.shykun.volodymyr.movieviewer.data.network

import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.data.network.body.AddToWatchlistBody
import com.shykun.volodymyr.movieviewer.data.network.body.MarkAsFavoriteBody
import com.shykun.volodymyr.movieviewer.data.network.body.RateBody
import com.shykun.volodymyr.movieviewer.data.network.body.RequestTokenBody
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor() {
    private val apiService: ApiService

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    //movie

    fun getMovies(moviesType: MoviesType, page: Int) = apiService.getMovies(moviesType.path, page)

    fun getMovieDetails(movieId: Int) = apiService.getMovieDetails(movieId)

    fun getMovieCredits(movieId: Int) = apiService.getMovieCredits(movieId)

    fun getMovieReviews(movieId: Int) = apiService.getMovieReviews(movieId)

    fun getRecommendedMovies(movieId: Int) = apiService.getRecommendedMovies(movieId)

    fun searchMovies(query: String, page: Int) = apiService.searchMovies(query, page)

    fun rateMovie(movieId: Int, rateBody: RateBody, sessionId: String) = apiService.rateMovie(movieId, rateBody, sessionId)

    fun deleteMovieRating(movieId: Int, sessionId: String) = apiService.deleteMovieRating(movieId, sessionId)

    fun getMovieAccountStates(movieId: Int, sessionId: String) = apiService.getMovieAccountStates(movieId, sessionId)

    //tv

    fun getTV(tvType: TvType, page: Int) = apiService.getTV(tvType.path, page)

    fun getTvDetails(tvId: Int) = apiService.getTvDetails(tvId)

    fun getTvCredits(tvId: Int) = apiService.getTvCredits(tvId)

    fun getRecommedndedTv(tvId: Int) = apiService.getRecommendedTv(tvId)

    fun getTvReviews(tvId: Int) = apiService.getTvReviews(tvId)

    fun searchTv(query: String, page: Int) = apiService.searchTv(query, page)

    fun rateTv(tvId: Int, rateBody: RateBody, sessionId: String) = apiService.rateTv(tvId, rateBody, sessionId)

    fun deleteTvRating(tvId: Int, sessionId: String) = apiService.deleteTvRating(tvId, sessionId)

    fun getTvAccountStates(tvId: Int, sessionId: String) = apiService.getTvAccountStates(tvId, sessionId)

    //people

    fun getPopularPeople(page: Int) = apiService.getPopularPeople(page)

    fun getPersonDetails(personId: Int) = apiService.getPersonDetails(personId)

    fun getPersonCombinedCredits(personId: Int) = apiService.getPersonCombinedCredits(personId)

    fun searchPeople(query: String, page: Int) = apiService.searchPeople(query, page)

    //discover

    fun discoverMovies(year: Int?, voteAverage: Int?, genres: String?, page: Int) = apiService.discoverMovies(page, year, voteAverage, genres)

    fun discoverTv(airDate: String?, voteAverage: Int?, genres: String?, page: Int) = apiService.discoverTV(page, airDate, voteAverage, genres)

    //profile

    fun getRequstToken() = apiService.getRequestToken()

    fun createSessionId(requestTokenBody: RequestTokenBody) = apiService.createSessionId(requestTokenBody)

    fun logout(sessionId: String) = apiService.logout(sessionId)

    fun getAccountDetails(sessionId: String) = apiService.getAccountDetails(sessionId)

    fun markAsFavorite(markAsFavoriteBody: MarkAsFavoriteBody, sessionId: String) = apiService.markAsFavorite(markAsFavoriteBody, sessionId)

    fun addToWatchlist(addToWatchlistBody: AddToWatchlistBody, sessionId: String) = apiService.addToWatchlist(addToWatchlistBody, sessionId)

    fun getFavoriteMovies(sessionId: String, page: Int) = apiService.getFavoriteMovies(sessionId, page)

    fun getFavoriteTv(sessionId: String, page: Int) = apiService.getFavoriteTv(sessionId, page)

    fun getMovieWatchList(sessionId: String, page: Int) = apiService.getMovieWatchList(sessionId, page)

    fun getTvWatchList(sessionId: String, page: Int) = apiService.getTvWatchList(sessionId, page)

    fun getRatedMovies(sessionId: String, page: Int) = apiService.getRatedMovies(sessionId, page)

    fun getRatedTv(sessionId: String, page: Int) = apiService.getRatedTv(sessionId, page)
}