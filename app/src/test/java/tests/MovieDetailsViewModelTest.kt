package tests

import android.arch.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shykun.volodymyr.movieviewer.data.network.response.MovieDetailsResponse
import com.shykun.volodymyr.movieviewer.domain.MovieDetailsUseCase
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MovieDetailsViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class MovieDetailsViewModelTest {

    @Mock
    lateinit var movieDetailsUseCase: MovieDetailsUseCase
    @Mock
    lateinit var profileUseCase: ProfileUseCase
    lateinit var viewModel: MovieDetailsViewModel
    lateinit var gson: Gson

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MovieDetailsViewModel(movieDetailsUseCase, profileUseCase)

        gson = GsonBuilder().create()
    }

    @Test
    fun test() {
        val movieDetailsMock = Mockito.mock(MovieDetailsResponse::class.java)
        val observer = Observer<MovieDetailsResponse> {}

        `when`(movieDetailsUseCase.getMovieDetails(123)).thenReturn(Single.just(movieDetailsMock))

        viewModel.movieDetailsLiveData.observeForever(observer)
        viewModel.getMovieDetails(123)

        verify(observer).onChanged(movieDetailsMock)
    }
}