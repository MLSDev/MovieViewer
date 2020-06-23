package tests.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.domain.DiscoverUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DiscoverUseCaseTest {

    private lateinit var apiClientMock: ApiClient
    private lateinit var discoverUseCase: DiscoverUseCase

    private val rating = 7
    private val genres = "Action, Horror"
    private val page = 1

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        apiClientMock = mock()
        discoverUseCase = DiscoverUseCase(apiClientMock)
    }

    @Test
    fun `discover movies test`() {
        whenever(apiClientMock.discoverMovies(any(), any(), any(), any()))
                .thenReturn(Single.just(mock()))

        val year = 2010
        discoverUseCase.discoverMovies(year, rating, genres, page)

        verify(apiClientMock).discoverMovies(year, rating, genres, page)
    }

    @Test
    fun `discover tv test`() {
        whenever(apiClientMock.discoverTv(any(), any(), any(), any()))
                .thenReturn(Single.just(mock()))

        val airDate = "10-12-2012"
        discoverUseCase.discoverTv(airDate, rating, genres, page)

        verify(apiClientMock).discoverTv(airDate, rating, genres, page)
    }
}