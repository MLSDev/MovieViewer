package tests

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.domain.PeopleUseCase
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import com.shykun.volodymyr.movieviewer.presentation.people.tab.PeopleTabViewModel
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
class PeopleTabViewModelTest {

    private lateinit var viewModel: PeopleTabViewModel
    private lateinit var peopleUseCaseMock: PeopleUseCase
    private lateinit var searchUseCaseMock: SearchUseCase
    private var disposable: Disposable? = null

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        peopleUseCaseMock = mock()
        searchUseCaseMock = mock()
        viewModel = PeopleTabViewModel(
                peopleUseCaseMock,
                searchUseCaseMock,
                Schedulers.trampoline(),
                Schedulers.trampoline()
        )
    }

    @After
    fun setDown() {
        disposable?.dispose()
    }

    @Test
    fun `get people test`() {
        val page = 1
        whenever(peopleUseCaseMock.getPopularPeople(page))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getPeople(page)

        verify(peopleUseCaseMock).getPopularPeople(page)
        assertNotNull(viewModel.peopleLiveData.value)
    }

    @Test
    fun `search people test`() {
        val page = 1
        val query = "search_query"
        whenever(searchUseCaseMock.searchPeople(query, page))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.searchPeople(query, page)

        verify(searchUseCaseMock).searchPeople(query, page)
        assertNotNull(viewModel.peopleLiveData.value)
    }
}