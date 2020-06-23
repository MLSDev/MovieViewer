package tests.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.domain.PersonDetailsUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PersonDetailsUseCaseTest {

    private lateinit var apiClientMock: ApiClient
    private lateinit var personDetailsUseCase: PersonDetailsUseCase

    private val personId = 123

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        apiClientMock = mock()
        personDetailsUseCase = PersonDetailsUseCase(apiClientMock)
    }

    @Test
    fun `get person details test`() {
        whenever(apiClientMock.getPersonDetails(any()))
                .thenReturn(Single.just(mock()))

        personDetailsUseCase.getPersonDetails(personId)

        verify(apiClientMock).getPersonDetails(personId)
    }

    @Test
    fun `get person cast test`() {
        whenever(apiClientMock.getPersonCombinedCredits(any()))
                .thenReturn(Single.just(mock()))

        personDetailsUseCase.getPersonCast(personId)

        verify(apiClientMock).getPersonCombinedCredits(personId)
    }
}