package tests

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.domain.TvDetailsUseCase
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TvDetailsViewModel
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

@RunWith(JUnit4::class)
class TvDetailsViewModelTest {

    private lateinit var viewModel: TvDetailsViewModel
    private lateinit var tvDetailsUseCaseMock: TvDetailsUseCase
    private var disposable: Disposable? = null

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        tvDetailsUseCaseMock = mock()
        viewModel = TvDetailsViewModel(tvDetailsUseCaseMock, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @After
    fun setDown() {
        disposable?.dispose()
    }

    @Test
    fun `get tv details test`() {
        whenever(tvDetailsUseCaseMock.getTvDetails(anyInt()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getTvDetails(anyInt())

        verify(tvDetailsUseCaseMock).getTvDetails(anyInt())
        assertNotNull(viewModel.tvDetailsLiveData.value)
    }

    @Test
    fun `get recommended tv test`() {
        whenever(tvDetailsUseCaseMock.getRecommendedTv(anyInt()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getRecommendedTv(anyInt())

        verify(tvDetailsUseCaseMock).getRecommendedTv(anyInt())
        assertNotNull(viewModel.recommendedTvLiveData.value)
    }

    @Test
    fun `get tv reviews test`() {
        whenever(tvDetailsUseCaseMock.getTvReviews(anyInt()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getTvReviews(anyInt())

        verify(tvDetailsUseCaseMock).getTvReviews(anyInt())
        assertNotNull(viewModel.tvReviewsLiveData.value)
    }

    @Test
    fun `get tv actors test`() {
        whenever(tvDetailsUseCaseMock.getTvCast(anyInt()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getTvCast(anyInt())

        verify(tvDetailsUseCaseMock).getTvCast(anyInt())
        assertNotNull(viewModel.tvActorsLiveData.value)
    }

    @Test
    fun `rate tv test`() {
        whenever(tvDetailsUseCaseMock.rateTv(anyInt(), anyFloat(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.rateTv(anyInt(), anyFloat(), anyString())

        verify(tvDetailsUseCaseMock).rateTv(anyInt(), anyFloat(), anyString())
        assertNotNull(viewModel.rateTvLiveData.value)
    }

    @Test
    fun `delete tv rating test`() {
        whenever(tvDetailsUseCaseMock.deleteTvRating(anyInt(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.deleteTvRating(anyInt(), anyString())

        verify(tvDetailsUseCaseMock).deleteTvRating(anyInt(), anyString())
        assertNotNull(viewModel.rateTvLiveData.value)
    }

    @Test
    fun `add tv to watchlist test`() {
        whenever(tvDetailsUseCaseMock.addToWatchlist(anyInt(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.addToWatchlist(anyInt(), anyString())

        verify(tvDetailsUseCaseMock).addToWatchlist(anyInt(), anyString())
        assertNotNull(viewModel.addToWatchListLiveData.value)
    }

    @Test
    fun `delete tv from watchlist test`() {
        whenever(tvDetailsUseCaseMock.deleteFromWatchlist(anyInt(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.deleteFromWatchlist(anyInt(), anyString())

        verify(tvDetailsUseCaseMock).deleteFromWatchlist(anyInt(), anyString())
        assertNotNull(viewModel.addToWatchListLiveData.value)
    }

    @Test
    fun `add tv to favorites test`() {
        whenever(tvDetailsUseCaseMock.addToFavorites(anyInt(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.addToFavorites(anyInt(), anyString())

        verify(tvDetailsUseCaseMock).addToFavorites(anyInt(), anyString())
        assertNotNull(viewModel.markAsFavoriteLiveData.value)
    }

    @Test
    fun `delete from favorites test`() {
        whenever(tvDetailsUseCaseMock.deleteFromFavorites(anyInt(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.deleteFromFavorites(anyInt(), anyString())

        verify(tvDetailsUseCaseMock).deleteFromFavorites(anyInt(), anyString())
        assertNotNull(viewModel.markAsFavoriteLiveData.value)
    }

    @Test
    fun `get tv account state test`() {
        whenever(tvDetailsUseCaseMock.getTvAccountStates(anyInt(), anyString()))
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getTvAccountStates(anyInt(), anyString())

        verify(tvDetailsUseCaseMock).getTvAccountStates(anyInt(), anyString())
        assertNotNull(viewModel.tvAccountStatesLiveData.value)
    }

    @Test
    fun `view loaded test`() {
        whenever(tvDetailsUseCaseMock.getTvDetails(anyInt()))
                .thenReturn(Single.just(mock()))
        whenever(tvDetailsUseCaseMock.getRecommendedTv(anyInt()))
                .thenReturn(Single.just(mock()))
        whenever(tvDetailsUseCaseMock.getTvReviews(anyInt()))
                .thenReturn(Single.just(mock()))
        whenever(tvDetailsUseCaseMock.getTvCast(anyInt()))
                .thenReturn(Single.just(mock()))

        viewModel.onViewLoaded(anyInt())

        verify(tvDetailsUseCaseMock).getTvDetails(anyInt())
        verify(tvDetailsUseCaseMock).getRecommendedTv(anyInt())
        verify(tvDetailsUseCaseMock).getTvReviews(anyInt())
        verify(tvDetailsUseCaseMock).getTvCast(anyInt())

        assertNotNull(viewModel.tvDetailsLiveData.value)
        assertNotNull(viewModel.recommendedTvLiveData.value)
        assertNotNull(viewModel.tvReviewsLiveData.value)
        assertNotNull(viewModel.tvActorsLiveData.value)
    }
}