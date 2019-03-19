package tests

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.domain.DiscoverUseCase
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import com.shykun.volodymyr.movieviewer.domain.TvUseCase
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TvListViewModel
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
class TvListTest {

    private lateinit var viewModel: TvListViewModel
    private lateinit var tvuseCaseMock: TvUseCase
    private lateinit var profileUseCaseMock: ProfileUseCase
    private lateinit var searchUseCaseMock: SearchUseCase
    private lateinit var discoverUseCaseMock: DiscoverUseCase
    private var disposable: Disposable? = null

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        tvuseCaseMock = mock()
        profileUseCaseMock = mock()
        searchUseCaseMock = mock()
        discoverUseCaseMock = mock()

        viewModel = TvListViewModel(
                tvuseCaseMock,
                profileUseCaseMock,
                searchUseCaseMock,
                discoverUseCaseMock,
                Schedulers.trampoline(),
                Schedulers.trampoline()
        )
    }

    @After
    fun setDown() {
        disposable?.dispose()
    }

    @Test
    fun `get tv test`() {
        whenever(tvuseCaseMock.getTv(any(), any()))
                .thenReturn(Single.just(mock()))

        val tvType = TvType.POPULAR
        val page = 1
        disposable = viewModel.getTvList(tvType, page)

        verify(tvuseCaseMock).getTv(tvType, page)
        assertNotNull(viewModel.tvListLiveData.value)
    }

    @Test
    fun `search tv test`() {
        whenever(searchUseCaseMock.searchTv(any(), any()))
                .thenReturn(Single.just(mock()))

        val query = "search_query"
        val page = 1
        disposable = viewModel.searchTv(query, page)

        verify(searchUseCaseMock).searchTv(query, page)
        assertNotNull(viewModel.tvListLiveData.value)
    }

    @Test
    fun `discover tv test`() {
        whenever(discoverUseCaseMock.discoverTv(anyOrNull(), anyOrNull(), anyOrNull(), any()))
                .thenReturn(Single.just(mock()))

        val airDate = "10.12.2007"
        val rating  = 10
        val genres = "Action"
        val page = 1

        disposable = viewModel.discoverTv(airDate, rating, genres, page)

        verify(discoverUseCaseMock).discoverTv(airDate, rating, genres, page)
        assertNotNull(viewModel.tvListLiveData.value)

        disposable = viewModel.discoverTv(airDate, rating, null, page)

        verify(discoverUseCaseMock).discoverTv(airDate, rating, null, page)
        assertNotNull(viewModel.tvListLiveData.value)

        disposable = viewModel.discoverTv(airDate, null, genres, page)

        verify(discoverUseCaseMock).discoverTv(airDate, null, genres, page)
        assertNotNull(viewModel.tvListLiveData.value)

        disposable = viewModel.discoverTv(null, rating, genres, page)

        verify(discoverUseCaseMock).discoverTv(null, rating, genres, page)
        assertNotNull(viewModel.tvListLiveData.value)

        disposable = viewModel.discoverTv(null, null, null, page)

        verify(discoverUseCaseMock).discoverTv(null, null, null, page)
        assertNotNull(viewModel.tvListLiveData.value)
    }

    @Test
    fun `get rated tv test`() {
        whenever(profileUseCaseMock.getRatedTv(any(), any()))
                .thenReturn(Single.just(mock()))

        val sessionId = "sessionId"
        val page = 1
        disposable = viewModel.getRatedTv(sessionId, page)

        verify(profileUseCaseMock).getRatedTv(sessionId, page)
        assertNotNull(viewModel.tvListLiveData.value)
    }


    @Test
    fun `get tv watchlist test`() {
        whenever(profileUseCaseMock.getTvWatchList(any(), any()))
                .thenReturn(Single.just(mock()))

        val sessionId = "sessionId"
        val page = 1
        disposable = viewModel.getTvWatchlist(sessionId, page)

        verify(profileUseCaseMock).getTvWatchList(sessionId, page)
        assertNotNull(viewModel.tvListLiveData.value)
    }

    @Test
    fun `get favorite tv test`() {
        whenever(profileUseCaseMock.getFavoriteTv(any(), any()))
                .thenReturn(Single.just(mock()))
        val sessionId = "sessionId"
        val page = 1
        disposable = viewModel.getFavoriteTv(sessionId, page)

        verify(profileUseCaseMock).getFavoriteTv(sessionId, page)
        assertNotNull(viewModel.tvListLiveData.value)
    }
}