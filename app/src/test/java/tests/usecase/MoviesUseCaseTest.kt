package tests.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MoviesUseCaseTest {

    private lateinit var apiClientMock: ApiClient
    private lateinit var moviesUseCase: MoviesUseCase

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        apiClientMock = mock()
        moviesUseCase = MoviesUseCase(apiClientMock)
    }

    @Test
    fun `get movies test`() {
        whenever(apiClientMock.getMovies(any(), any()))
                .thenReturn(Single.just(mock()))

        val moviesType = MoviesType.POPULAR
        val page = 1

        moviesUseCase.getMovies(moviesType, page)

        verify(apiClientMock).getMovies(moviesType, page)
    }
}