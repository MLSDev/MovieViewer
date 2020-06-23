package tests.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import com.shykun.volodymyr.movieviewer.presentation.search.SearchViewModel
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
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var searchUseCaseMock: SearchUseCase
    private var disposable: Disposable? = null
    private val query = "query"

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        searchUseCaseMock = mock()
        viewModel = SearchViewModel(searchUseCaseMock, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @After
    fun setDown() {
        disposable?.dispose()
    }

    @Test
    fun `search movies test`() {
        whenever(searchUseCaseMock.searchMovies(any(), any()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.searchMovies(query)

        verify(searchUseCaseMock).searchMovies(query)
        assertNotNull(viewModel.searchResultsLiveData.value)
    }

    @Test
    fun `search tv test`() {
        whenever(searchUseCaseMock.searchTv(any(), any()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.searchTv(query)

        verify(searchUseCaseMock).searchTv(query)
        assertNotNull(viewModel.searchResultsLiveData.value)
    }

    @Test
    fun `search people test`() {
        whenever(searchUseCaseMock.searchPeople(any(), any()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.searchPeople(query)

        verify(searchUseCaseMock).searchPeople(query)
        assertNotNull(viewModel.searchResultsLiveData.value)
    }
}