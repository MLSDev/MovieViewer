package tests.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.data.network.body.AddToWatchlistBody
import com.shykun.volodymyr.movieviewer.data.network.body.MarkAsFavoriteBody
import com.shykun.volodymyr.movieviewer.data.network.body.RateBody
import com.shykun.volodymyr.movieviewer.domain.MovieDetailsUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDetailsUseCaseTest {

    private lateinit var apiClientMock: ApiClient
    private lateinit var movieDetailsUseCase: MovieDetailsUseCase

    private val movieId = 123
    private val sessionId = "session_id"

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        apiClientMock = mock()
        movieDetailsUseCase = MovieDetailsUseCase(apiClientMock)
    }

    @Test
    fun `get movie details test`() {
        whenever(apiClientMock.getMovieDetails(any()))
                .thenReturn(Single.just(mock()))

        movieDetailsUseCase.getMovieDetails(movieId)

        verify(apiClientMock).getMovieDetails(movieId)
    }

    @Test
    fun `get movie credits test`() {
        whenever(apiClientMock.getMovieCredits(any()))
                .thenReturn(Single.just(mock()))

        movieDetailsUseCase.getMovieCredits(movieId)

        verify(apiClientMock).getMovieCredits(movieId)
    }

    @Test
    fun `get movie reviews test`() {
        whenever(apiClientMock.getMovieReviews(any()))
                .thenReturn(Single.just(mock()))

        movieDetailsUseCase.getMovieReviews(movieId)

        verify(apiClientMock).getMovieReviews(movieId)
    }

    @Test
    fun `get recommended movies`() {
        whenever(apiClientMock.getRecommendedMovies(any()))
                .thenReturn(Single.just(mock()))

        movieDetailsUseCase.getRecommendedMovies(movieId)

        verify(apiClientMock).getRecommendedMovies(movieId)
    }

    @Test
    fun `rate movie test`() {
        whenever(apiClientMock.rateMovie(any(), any(), any()))
                .thenReturn(Single.just(mock()))

        val rating = 7.0f
        movieDetailsUseCase.rateMovie(movieId, rating, sessionId)

        verify(apiClientMock).rateMovie(movieId, RateBody(rating), sessionId)
    }

    @Test
    fun `mark movie as favorite test`() {
        whenever(apiClientMock.markAsFavorite(any(), any()))
                .thenReturn(Single.just(mock()))

        movieDetailsUseCase.markAsFavorite(movieId, sessionId)

        verify(apiClientMock).markAsFavorite(
                MarkAsFavoriteBody("movie", movieId, true),
                sessionId)
    }

    @Test
    fun `add movie to watch list test`() {
        whenever(apiClientMock.addToWatchlist(any(), any()))
                .thenReturn(Single.just(mock()))

        movieDetailsUseCase.addToWatchlist(movieId, sessionId)

        verify(apiClientMock).addToWatchlist(
                AddToWatchlistBody("movie", movieId, true),
                sessionId)
    }

    @Test
    fun `delete movie rating test`() {
        whenever(apiClientMock.deleteMovieRating(any(), any()))
                .thenReturn(Single.just(mock()))

        movieDetailsUseCase.deleteMovieRating(movieId, sessionId)

        verify(apiClientMock).deleteMovieRating(movieId, sessionId)
    }

    @Test
    fun `delete movie from favorites test`() {
        whenever(apiClientMock.markAsFavorite(any(), any()))
                .thenReturn(Single.just(mock()))

        movieDetailsUseCase.deleteFromFavorites(movieId, sessionId)

        verify(apiClientMock).markAsFavorite(
                MarkAsFavoriteBody("movie", movieId, false),
                sessionId)
    }

    @Test
    fun `get movie account states test`() {
        whenever(apiClientMock.getMovieAccountStates(any(), any()))
                .thenReturn(Single.just(mock()))

        movieDetailsUseCase.getMovieAccountStates(movieId, sessionId)

        verify(apiClientMock).getMovieAccountStates(movieId, sessionId)
    }
}