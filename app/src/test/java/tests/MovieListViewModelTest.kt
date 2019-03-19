package tests

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.domain.DiscoverUseCase
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MovieListViewModel
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
class MovieListViewModelTest {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var moviesUseCaseMock: MoviesUseCase
    private lateinit var profileUseCaseMock: ProfileUseCase
    private lateinit var searchUseCaseMock: SearchUseCase
    private lateinit var discoverUseCaseMock: DiscoverUseCase
    private var disposable: Disposable? = null

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        moviesUseCaseMock = mock()
        profileUseCaseMock = mock()
        searchUseCaseMock = mock()
        discoverUseCaseMock = mock()

        viewModel = MovieListViewModel(
                moviesUseCaseMock,
                profileUseCaseMock,
                searchUseCaseMock,
                discoverUseCaseMock,
                Schedulers.trampoline(),
                Schedulers.trampoline())
    }

    @After
    fun setDown() {
        disposable?.dispose()
    }

    @Test
    fun `get movies test`() {
        whenever(moviesUseCaseMock.getMovies(any(), any()))
                .thenReturn(Single.just(mock()))

        val moviesType = MoviesType.POPULAR
        val page = 1
        disposable = viewModel.getMovies(moviesType, page)

        verify(moviesUseCaseMock).getMovies(moviesType, page)
        assertNotNull(viewModel.moviesLiveData.value)
    }

    @Test
    fun `search movies test`() {
        whenever(searchUseCaseMock.searchMovies(any(), any()))
                .thenReturn(Single.just(mock()))

        val query = "search_query"
        val page = 1
        disposable = viewModel.searchMovie(query, page)

        verify(searchUseCaseMock).searchMovies(query, page)
        assertNotNull(viewModel.moviesLiveData.value)
    }

    @Test
    fun `discover movies test`() {
        whenever(discoverUseCaseMock.discoverMovies(anyOrNull(), anyOrNull(), anyOrNull(), any()))
                .thenReturn(Single.just(mock()))

        val year = 2007
        val rating = 10
        val genres = "Horror"
        val page = 1

        disposable = viewModel.discoverMovies(year, rating, genres, page)

        verify(discoverUseCaseMock).discoverMovies(year, rating, genres, page)
        assertNotNull(viewModel.moviesLiveData.value)

        disposable = viewModel.discoverMovies(year, rating, null, page)

        verify(discoverUseCaseMock).discoverMovies(year, rating, null, page)
        assertNotNull(viewModel.moviesLiveData.value)

        disposable = viewModel.discoverMovies(null, rating, genres, page)

        verify(discoverUseCaseMock).discoverMovies(null, rating, genres, page)
        assertNotNull(viewModel.moviesLiveData.value)

        disposable = viewModel.discoverMovies(null, null, null, page)

        verify(discoverUseCaseMock).discoverMovies(null, null, null, page)
        assertNotNull(viewModel.moviesLiveData.value)
    }


    @Test
    fun `get rated movies test`() {
        whenever(profileUseCaseMock.getRatedMovies(any(), any()))
                .thenReturn(Single.just(mock()))

        val sessionId = "sessionId"
        val page = 1
        disposable = viewModel.getRatedMovies(sessionId, page)

        verify(profileUseCaseMock).getRatedMovies(sessionId, page)
        assertNotNull(viewModel.moviesLiveData.value)
    }

    @Test
    fun `get movie watch list test`() {
        whenever(profileUseCaseMock.getMovieWatchList(any(), any()))
                .thenReturn(Single.just(mock()))

        val sessionId = "sessionId"
        val page = 1
        disposable = viewModel.getMovieWatchlist(sessionId, page)

        verify(profileUseCaseMock).getMovieWatchList(sessionId, page)
        assertNotNull(viewModel.moviesLiveData.value)
    }

    @Test
    fun `get favorite movies test`() {
        whenever(profileUseCaseMock.getFavoriteMovies(any(), any()))
                .thenReturn(Single.just(mock()))

        val sessionId = "sessionId"
        val page = 1
        disposable = viewModel.getFavoriteMovies(sessionId, page)

        verify(profileUseCaseMock).getFavoriteMovies(sessionId, page)
        assertNotNull(viewModel.moviesLiveData.value)
    }
}