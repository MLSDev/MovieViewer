package tests

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.domain.MovieDetailsUseCase
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MovieDetailsViewModel
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
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class MovieDetailsViewModelTest {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieDetailsUseCaseMock: MovieDetailsUseCase
    private var disposable: Disposable? = null

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        movieDetailsUseCaseMock = mock()
        viewModel = MovieDetailsViewModel(movieDetailsUseCaseMock, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @After
    fun setDown() {
        disposable?.dispose()
    }

    @Test
    fun `get movie details test`() {
        whenever(movieDetailsUseCaseMock.getMovieDetails(anyInt()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getMovieDetails(anyInt())

        verify(movieDetailsUseCaseMock).getMovieDetails(anyInt())
        assertNotNull(viewModel.movieDetailsLiveData.value)
    }

    @Test
    fun `get movie cast test`() {
        whenever(movieDetailsUseCaseMock.getMovieCredits(anyInt()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getMovieCast(anyInt())

        verify(movieDetailsUseCaseMock).getMovieCredits(anyInt())
        assertNotNull(viewModel.movieActorsLiveData.value)
    }

    @Test
    fun `get movie reviews test`() {
        whenever(movieDetailsUseCaseMock.getMovieReviews(anyInt()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getMovieReviews(anyInt())

        verify(movieDetailsUseCaseMock).getMovieReviews(anyInt())
        assertNotNull(viewModel.movieReviewLiveData.value)
    }

    @Test
    fun `get recommended movies test`() {
        whenever(movieDetailsUseCaseMock.getRecommendedMovies(anyInt()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getRecommendedMovies(anyInt())

        verify(movieDetailsUseCaseMock).getRecommendedMovies(anyInt())
        assertNotNull(viewModel.recommendedMoviesLiveData.value)
    }


    @Test
    fun `rate movie test`() {
        whenever(movieDetailsUseCaseMock.rateMovie(anyInt(), anyFloat(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.rateMovie(anyInt(), anyFloat(), anyString())

        verify(movieDetailsUseCaseMock).rateMovie(anyInt(), anyFloat(), anyString())
        assertNotNull(viewModel.rateMovieLiveData.value)
    }

    @Test
    fun `delete movie rating test`() {
        whenever(movieDetailsUseCaseMock.deleteMovieRating(anyInt(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.deleteMovieRating(anyInt(), anyString())

        verify(movieDetailsUseCaseMock).deleteMovieRating(anyInt(), anyString())
        assertNotNull(viewModel.rateMovieLiveData.value)
    }

    @Test
    fun `add to watchlist test`() {
        whenever(movieDetailsUseCaseMock.addToWatchlist(anyInt(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.addToWatchList(anyInt(), anyString())

        verify(movieDetailsUseCaseMock).addToWatchlist(anyInt(), anyString())
        assertNotNull(viewModel.addToWatchListLiveData.value)
    }

    @Test
    fun `delete from watchlist test`() {
        whenever(movieDetailsUseCaseMock.removeFromWatchList(anyInt(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.deleteFromWatchList(anyInt(), anyString())

        verify(movieDetailsUseCaseMock).removeFromWatchList(anyInt(), anyString())
        assertNotNull(viewModel.addToWatchListLiveData.value)
    }

    @Test
    fun `add to favorites test`() {
        whenever(movieDetailsUseCaseMock.markAsFavorite(anyInt(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.markAsFavorite(anyInt(), anyString())

        verify(movieDetailsUseCaseMock).markAsFavorite(anyInt(), anyString())
        assertNotNull(viewModel.markAsFavoriteLiveData.value)
    }

    @Test
    fun `delete movie form favorites`() {
        whenever(movieDetailsUseCaseMock.deleteFromFavorites(anyInt(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.deleteFromFavorites(anyInt(), anyString())

        verify(movieDetailsUseCaseMock).deleteFromFavorites(anyInt(), anyString())
        assertNotNull(viewModel.markAsFavoriteLiveData.value)
    }

    @Test
    fun `get movie account states`() {
        whenever(movieDetailsUseCaseMock.getMovieAccountStates(anyInt(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getMovieAccountStates(anyInt(), anyString())

        verify(movieDetailsUseCaseMock).getMovieAccountStates(anyInt(), anyString())
        assertNotNull(viewModel.movieAccountLiveData.value)
    }

    @Test
    fun `test on view loaded`() {
        whenever(movieDetailsUseCaseMock.getMovieDetails(anyInt()))
                .thenReturn(Single.just(mock()))
        whenever(movieDetailsUseCaseMock.getMovieCredits(anyInt()))
                .thenReturn(Single.just(mock()))
        whenever(movieDetailsUseCaseMock.getMovieReviews(anyInt()))
                .thenReturn(Single.just(mock()))
        whenever(movieDetailsUseCaseMock.getRecommendedMovies(anyInt()))
                .thenReturn(Single.just(mock()))

        viewModel.onViewLoaded(anyInt())

        verify(movieDetailsUseCaseMock).getMovieDetails(anyInt())
        verify(movieDetailsUseCaseMock).getMovieCredits(anyInt())
        verify(movieDetailsUseCaseMock).getMovieReviews(anyInt())
        verify(movieDetailsUseCaseMock).getRecommendedMovies(anyInt())

        assertNotNull(viewModel.movieDetailsLiveData.value)
        assertNotNull(viewModel.movieActorsLiveData.value)
        assertNotNull(viewModel.movieReviewLiveData.value)
        assertNotNull(viewModel.recommendedMoviesLiveData.value)
    }
}