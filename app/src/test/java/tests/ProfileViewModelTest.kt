package tests

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shykun.volodymyr.movieviewer.data.network.response.SessionIdResponse
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import com.shykun.volodymyr.movieviewer.presentation.profile.ProfileViewModel
import com.shykun.volodymyr.movieviewer.presentation.profile.SESSION_ID_KEY
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var profileUseCaseMock: ProfileUseCase
    private lateinit var contextMock: Context
    private lateinit var prefsMock: SharedPreferences
    private lateinit var prefsEditorMock: SharedPreferences.Editor
    private var disposable: Disposable? = null

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        profileUseCaseMock = mock()
        contextMock = mock()
        prefsMock = mock()
        prefsEditorMock = mock()
        whenever(prefsMock.edit())
                .thenReturn(prefsEditorMock)
        viewModel = ProfileViewModel(profileUseCaseMock, prefsMock, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @After
    fun setAfter() {
        disposable?.dispose()
    }

    @Test
    fun `get request token test`() {
        whenever(profileUseCaseMock.getRequestToken())
                .thenReturn(Single.just(mock()))

        disposable = viewModel.getRequestToken()

        verify(profileUseCaseMock).getRequestToken()
        assertNotNull(viewModel.requestTokenLiveData.value)
    }

    @Test
    fun `create session id test`() {
        val sessionId = "session_id"
        val response = SessionIdResponse(true, sessionId)
        whenever(profileUseCaseMock.createSessionId(any()))
                .thenReturn(Single.just(response))

        disposable = viewModel.createSessionId("token")

        verify(prefsMock.edit()).putString(SESSION_ID_KEY, sessionId)
//        verify(profileUseCaseMock).getAccountDetails(sessionId)
        assertNotNull(viewModel.sessionIdLiveData.value)
        assertNull(viewModel.logoutLiveData.value)

    }

    @Test
    fun `get account details test`() {
        whenever(profileUseCaseMock.getAccountDetails(any()))
                .thenReturn(Single.just(mock()))

        val sessionId = "session_id"
        disposable = viewModel.getAccountDetails(sessionId)

        verify(profileUseCaseMock).getAccountDetails(sessionId)
        assertNotNull(viewModel.accountDetailsLiveData.value)
    }

    @Test
    fun `logout test`() {
        whenever(profileUseCaseMock.logout(any()))
                .thenReturn(Single.just(mock()))

        val sessionId = "session_id"
        disposable = viewModel.logout(sessionId)

        verify(profileUseCaseMock).logout(sessionId)
        assertNotNull(viewModel.logoutLiveData.value)
    }
}