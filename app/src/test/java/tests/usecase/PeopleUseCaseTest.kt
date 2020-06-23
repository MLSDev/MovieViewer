package tests.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.domain.PeopleUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PeopleUseCaseTest {

    private lateinit var apiClientMock: ApiClient
    private lateinit var peopleUseCase: PeopleUseCase

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        apiClientMock = mock()
        peopleUseCase = PeopleUseCase(apiClientMock)
    }

    @Test
    fun `get popular people test`() {
        whenever(apiClientMock.getPopularPeople(any()))
                .thenReturn(Single.just(mock()))

        val page = 1

        peopleUseCase.getPopularPeople(page)

        verify(apiClientMock).getPopularPeople(page)
    }
}