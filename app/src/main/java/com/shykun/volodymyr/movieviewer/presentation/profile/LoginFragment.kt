package com.shykun.volodymyr.movieviewer.presentation.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.network.response.SessionIdResponse
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import kotlinx.android.synthetic.main.fragment_login.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val LOGIN_FRAGMENT_KEY = "login_fragment_key"

class LoginFragment : Fragment(), BackButtonListener {

    private lateinit var viewModel: ProfileViewModel
    @Inject
    lateinit var viewModelFactory: ProfileViewModelFactory
    @Inject
    lateinit var prefs: SharedPreferences
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)
        viewModel = ViewModelProviders.of(parentFragment!!, viewModelFactory)
                .get(ProfileViewModel::class.java)

        subscribeViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRequestToken()
    }

    private fun subscribeViewModel() {
        viewModel.requestTokenLiveData.observe(this, Observer { startLogin(it?.requestToken) })
    }

    private fun startLogin(requestToken: String?) {
        loginWebView.settings.javaScriptEnabled = true
        loginWebView.loadUrl("https://www.themoviedb.org/authenticate/$requestToken")

        loginWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val uri = request.url
                return handleUriLoading(view, uri, requestToken!!)
            }
        }
    }

    private fun handleUriLoading(view: WebView, uri: Uri, token: String): Boolean {
        val lastPath = uri.lastPathSegment
        if (lastPath == "allow")
            viewModel.createSessionId(token)
        return false
    }

    override fun onBackClicked(): Boolean {
        router.exit()

        return true
    }
}