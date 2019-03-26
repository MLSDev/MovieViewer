package tests.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.MovieTabViewModel
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
class MovieTabViewModelTest {

    private lateinit var viewModel: MovieTabViewModel
    private lateinit var moviesUseCaseMock: MoviesUseCase
    private var disposable: Disposable? = null

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        moviesUseCaseMock = mock()
        whenever(moviesUseCaseMock.getMovies(any(), any()))
                .thenReturn(Single.just(mock()))
        viewModel = MovieTabViewModel(moviesUseCaseMock, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @After
    fun setDown() {
        disposable?.dispose()
    }

    @Test
    fun `get popular movies test`() {
        val page = 1
        disposable = viewModel.getPopularMovies(page)

        verify(moviesUseCaseMock).getMovies(MoviesType.POPULAR, page)
        assertNotNull(viewModel.popularMoviesLiveData.value)
    }

    @Test
    fun `get top rated movies test`() {
        val page = 1
        disposable = viewModel.getTopRatedMovies(page)

        verify(moviesUseCaseMock).getMovies(MoviesType.TOP_RATED, page)
        assertNotNull(viewModel.topRatedMoviesLiveData.value)
    }

    @Test
    fun `get upcoming movies test`() {
        val page = 1
        disposable = viewModel.getUpcomingMovies(page)

        verify(moviesUseCaseMock).getMovies(MoviesType.UPCOMING, page)
        assertNotNull(viewModel.upcomingMoviesLiveData.value)
    }

    @Test
    fun `on view loaded test`() {
        viewModel.onViewLoaded()

        val page = 1
        verify(moviesUseCaseMock).getMovies(MoviesType.POPULAR, page)
        verify(moviesUseCaseMock).getMovies(MoviesType.TOP_RATED, page)
        verify(moviesUseCaseMock).getMovies(MoviesType.UPCOMING, page)

        assertNotNull(viewModel.popularMoviesLiveData.value)
        assertNotNull(viewModel.topRatedMoviesLiveData.value)
        assertNotNull(viewModel.upcomingMoviesLiveData.value)
    }
}