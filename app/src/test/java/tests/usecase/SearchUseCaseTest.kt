package tests.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchUseCaseTest {

    private lateinit var apiClientMock: ApiClient
    private lateinit var searchUseCase: SearchUseCase

    private val query = "Query"
    private val page = 1

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        apiClientMock = mock()
        searchUseCase = SearchUseCase(apiClientMock)
    }

    @Test
    fun `search movies test`() {
        whenever(apiClientMock.searchMovies(any(), any()))
                .thenReturn(Single.just(mock()))

        searchUseCase.searchMovies(query, page)

        verify(apiClientMock).searchMovies(query, page)
    }

    @Test
    fun `search tv test`() {
        whenever(apiClientMock.searchTv(any(), any()))
                .thenReturn(Single.just(mock()))

        searchUseCase.searchTv(query, page)

        verify(apiClientMock).searchTv(query, page)
    }

    @Test
    fun `search people test`() {
        whenever(apiClientMock.searchPeople(any(), any()))
                .thenReturn(Single.just(mock()))

        searchUseCase.searchPeople(query, page)

        verify(apiClientMock).searchPeople(query, page)
    }
}