package com.shykun.volodymyr.movieviewer.data.network

import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.data.network.body.AddToWatchlistBody
import com.shykun.volodymyr.movieviewer.data.network.body.MarkAsFavoriteBody
import com.shykun.volodymyr.movieviewer.data.network.body.RateBody
import com.shykun.volodymyr.movieviewer.data.network.body.RequestTokenBody
import io.reactivex.android.schedulers.AndroidSchedulers
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getMovieDetails(movieId: Int) = apiService.getMovieDetails(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getMovieCredits(movieId: Int) = apiService.getMovieCredits(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getMovieReviews(movieId: Int) = apiService.getMovieReviews(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getRecommendedMovies(movieId: Int) = apiService.getRecommendedMovies(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun searchMovies(query: String, page: Int) = apiService.searchMovies(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun rateMovie(movieId: Int, rateBody: RateBody, sessionId: String) = apiService.rateMovie(movieId, rateBody, sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deleteMovieRating(movieId: Int, sessionId: String) = apiService.deleteMovieRating(movieId, sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getMovieAccountStates(movieId: Int, sessionId: String) = apiService.getMovieAccountStates(movieId, sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    //tv

    fun getTV(tvType: TvType, page: Int) = apiService.getTV(tvType.path, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTvDetails(tvId: Int) = apiService.getTvDetails(tvId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTvCredits(tvId: Int) = apiService.getTvCredits(tvId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getRecommedndedTv(tvId: Int) = apiService.getRecommendedTv(tvId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTvReviews(tvId: Int) = apiService.getTvReviews(tvId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun searchTv(query: String, page: Int) = apiService.searchTv(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun rateTv(tvId: Int, rateBody: RateBody, sessionId: String) = apiService.rateTv(tvId, rateBody, sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deleteTvRating(tvId: Int, sessionId: String) = apiService.deleteTvRating(tvId, sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTvAccountStates(tvId: Int, sessionId: String) = apiService.getTvAccountStates(tvId, sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    //people

    fun getPopularPeople(page: Int) = apiService.getPopularPeople(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getPersonDetails(personId: Int) = apiService.getPersonDetails(personId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getPersonCombinedCredits(personId: Int) = apiService.getPersonCombinedCredits(personId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun searchPeople(query: String, page: Int) = apiService.searchPeople(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    //discover

    fun discoverMovies(year: Int?, voteAverage: Int?, genres: String?, page: Int) = apiService
            .discoverMovies(page, year, voteAverage, genres)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun discoverTv(airDate: String?, voteAverage: Int?, genres: String?, page: Int) = apiService
            .discoverTV(page, airDate, voteAverage, genres)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    //profile

    fun getRequstToken() = apiService.getRequestToken()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun createSessionId(requestTokenBody: RequestTokenBody) = apiService.createSessionId(requestTokenBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun logout(sessionId: String) = apiService.logout(sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getAccountDetails(sessionId: String) = apiService.getAccountDetails(sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun markAsFavorite(markAsFavoriteBody: MarkAsFavoriteBody, sessionId: String) = apiService.markAsFavorite(markAsFavoriteBody, sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun addToWatchlist(addToWatchlistBody: AddToWatchlistBody, sessionId: String) = apiService.addToWatchlist(addToWatchlistBody, sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getFavoriteMovies(sessionId: String, page: Int) = apiService.getFavoriteMovies(sessionId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getFavoriteTv(sessionId: String, page: Int) = apiService.getFavoriteTv(sessionId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getMovieWatchList(sessionId: String, page: Int) = apiService.getMovieWatchList(sessionId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTvWatchList(sessionId: String, page: Int) = apiService.getTvWatchList(sessionId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getRatedMovies(sessionId: String, page: Int) = apiService.getRatedMovies(sessionId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getRatedTv(sessionId: String, page: Int) = apiService.getRatedTv(sessionId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}