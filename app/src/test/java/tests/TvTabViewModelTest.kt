package tests

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.domain.TvUseCase
import com.shykun.volodymyr.movieviewer.presentation.tv.tab.TvTabViewModel
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TvTabViewModelTest {

    private lateinit var viewModel: TvTabViewModel
    private lateinit var tvUseCaseMock: TvUseCase
    private var disposable: Disposable? = null

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        tvUseCaseMock = mock()
        whenever(tvUseCaseMock.getTv(any(), any()))
                .thenReturn(Single.just(mock()))
        viewModel = TvTabViewModel(tvUseCaseMock, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @After
    fun setDown() {
        disposable?.dispose()
    }

    @Test
    fun `get popular tv test`() {
        val page = 1
        disposable = viewModel.getPopularTV(page)

        verify(tvUseCaseMock).getTv(TvType.POPULAR, page)
        Assert.assertNotNull(viewModel.popularTvLiveData.value)
    }

    @Test
    fun `get top rated tv test`() {
        val page = 1
        disposable = viewModel.getTopRatedTV(page)

        verify(tvUseCaseMock).getTv(TvType.TOP_RATED, page)
        Assert.assertNotNull(viewModel.topRatedTvLiveData.value)
    }

    @Test
    fun `get tv on the air test`() {
        val page = 1
        disposable = viewModel.getTVOnTheAir(page)

        verify(tvUseCaseMock).getTv(TvType.ON_THE_AIR, page)
        Assert.assertNotNull(viewModel.tvOnTheAirLiveData.value)
    }

    @Test
    fun `on view loaded test`() {
        viewModel.onViewLoaded()

        val page = 1
        verify(tvUseCaseMock).getTv(TvType.POPULAR, page)
        verify(tvUseCaseMock).getTv(TvType.TOP_RATED, page)
        verify(tvUseCaseMock).getTv(TvType.ON_THE_AIR, page)

        Assert.assertNotNull(viewModel.popularTvLiveData.value)
        Assert.assertNotNull(viewModel.topRatedTvLiveData.value)
        Assert.assertNotNull(viewModel.tvOnTheAirLiveData.value)
    }
}