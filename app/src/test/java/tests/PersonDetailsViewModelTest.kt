package tests

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.domain.PersonDetailsUseCase
import com.shykun.volodymyr.movieviewer.presentation.people.details.PersonDetailsViewModel
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PersonDetailsViewModelTest {

    private lateinit var viewModel: PersonDetailsViewModel
    private lateinit var personDetailsUseCaseMock: PersonDetailsUseCase
    private var disposable: Disposable? = null
    private val personId = 1

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        personDetailsUseCaseMock = mock()
        viewModel = PersonDetailsViewModel(
                personDetailsUseCaseMock,
                Schedulers.trampoline(),
                Schedulers.trampoline())
    }

    @After
    fun setDown() {
        disposable?.dispose()
    }

    @Test
    fun `get person details test`() {
        whenever(personDetailsUseCaseMock.getPersonDetails(any()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getPersonDetails(personId)

        verify(personDetailsUseCaseMock).getPersonDetails(personId)
        assertNotNull(viewModel.personDetailsLiveData.value)
    }

    @Test
    fun `get person cast test`() {
        whenever(personDetailsUseCaseMock.getPersonCast(any()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getPersonCast(personId)

        verify(personDetailsUseCaseMock).getPersonCast(personId)
        assertNotNull(viewModel.personCastLiveData.value)
    }
}