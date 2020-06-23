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
import com.shykun.volodymyr.movieviewer.domain.TvDetailsUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TvDetailsUseCaseTest {

    private lateinit var apiClientMock: ApiClient
    private lateinit var tvDetailsUseCase: TvDetailsUseCase

    private val tvId = 123
    private val sessionId = "session_id"

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        apiClientMock = mock()
        tvDetailsUseCase = TvDetailsUseCase(apiClientMock)
    }

    @Test
    fun `get tv details test`() {
        whenever(apiClientMock.getTvDetails(any()))
                .thenReturn(Single.just(mock()))

        tvDetailsUseCase.getTvDetails(tvId)

        verify(apiClientMock).getTvDetails(tvId)
    }

    @Test
    fun `get movie credits test`() {
        whenever(apiClientMock.getTvCredits(any()))
                .thenReturn(Single.just(mock()))

        tvDetailsUseCase.getTvCast(tvId)

        verify(apiClientMock).getTvCredits(tvId)
    }

    @Test
    fun `get movie reviews test`() {
        whenever(apiClientMock.getTvReviews(any()))
                .thenReturn(Single.just(mock()))

        tvDetailsUseCase.getTvReviews(tvId)

        verify(apiClientMock).getTvReviews(tvId)
    }

    @Test
    fun `get recommended movies`() {
        whenever(apiClientMock.getRecommedndedTv(any()))
                .thenReturn(Single.just(mock()))

        tvDetailsUseCase.getRecommendedTv(tvId)

        verify(apiClientMock).getRecommedndedTv(tvId)
    }

    @Test
    fun `rate movie test`() {
        whenever(apiClientMock.rateTv(any(), any(), any()))
                .thenReturn(Single.just(mock()))

        val rating = 7.0f
        tvDetailsUseCase.rateTv(tvId, rating, sessionId)

        verify(apiClientMock).rateTv(tvId, RateBody(rating), sessionId)
    }

    @Test
    fun `mark movie as favorite test`() {
        whenever(apiClientMock.markAsFavorite(any(), any()))
                .thenReturn(Single.just(mock()))

        tvDetailsUseCase.addToFavorites(tvId, sessionId)

        verify(apiClientMock).markAsFavorite(
                MarkAsFavoriteBody("tv", tvId, true),
                sessionId)
    }

    @Test
    fun `add movie to watch list test`() {
        whenever(apiClientMock.addToWatchlist(any(), any()))
                .thenReturn(Single.just(mock()))

        tvDetailsUseCase.addToWatchlist(tvId, sessionId)

        verify(apiClientMock).addToWatchlist(
                AddToWatchlistBody("tv", tvId, true),
                sessionId)
    }

    @Test
    fun `delete movie rating test`() {
        whenever(apiClientMock.deleteTvRating(any(), any()))
                .thenReturn(Single.just(mock()))

        tvDetailsUseCase.deleteTvRating(tvId, sessionId)

        verify(apiClientMock).deleteTvRating(tvId, sessionId)
    }

    @Test
    fun `delete movie from favorites test`() {
        whenever(apiClientMock.markAsFavorite(any(), any()))
                .thenReturn(Single.just(mock()))

        tvDetailsUseCase.deleteFromFavorites(tvId, sessionId)

        verify(apiClientMock).markAsFavorite(
                MarkAsFavoriteBody("tv", tvId, false),
                sessionId)
    }

    @Test
    fun `get movie account states test`() {
        whenever(apiClientMock.getTvAccountStates(any(), any()))
                .thenReturn(Single.just(mock()))

        tvDetailsUseCase.getTvAccountStates(tvId, sessionId)

        verify(apiClientMock).getTvAccountStates(tvId, sessionId)
    }
}