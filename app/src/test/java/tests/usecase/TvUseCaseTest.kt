package tests.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.domain.TvUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TvUseCaseTest {

    private lateinit var apiClientMock: ApiClient
    private lateinit var tvUseCase: TvUseCase

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        apiClientMock = mock()
        tvUseCase = TvUseCase(apiClientMock)
    }

    @Test
    fun `get tv test`() {
        whenever(apiClientMock.getTV(any(), any()))
                .thenReturn(Single.just(mock()))
        val tvType = TvType.POPULAR
        val page = 1

        tvUseCase.getTv(tvType, page)

        verify(apiClientMock).getTV(tvType, page)
    }
}