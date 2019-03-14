package di

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import dagger.Module
import org.mockito.Mockito.mock

@Module
class TestModule {
    fun provideMockApiClient(): ApiClient = mock(ApiClient::class.java)
}