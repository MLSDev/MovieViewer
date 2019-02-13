package com.shykun.volodymyr.movieviewer.presentation.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.utils.NavigationKeys
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : TabNavigationFragment() {

    override val navigationKey = NavigationKeys.PROFILE_NAVIGATION_KEY

    private lateinit var viewModel: ProfileViewModel
    @Inject
    lateinit var viewModelFactory: ProfileViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component?.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ProfileViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeViewModel()
        loginButton.setOnClickListener { viewModel.getRequestToken() }
    }

    private fun subscribeViewModel() {
        viewModel.requestTokenLiveData.observe(this, Observer { startLogin(it?.requestToken) })
    }

    private fun startLogin(requestToken: String?) {
        loginButton.visibility = View.GONE
        loginWebView.visibility = View.VISIBLE

        loginWebView.settings.javaScriptEnabled = true;
        loginWebView.loadUrl("https://www.themoviedb.org/authenticate/$requestToken?redirect_to=moviebase://auth/v3")

        loginWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val uri = request.url
                return handleUriLoading(view, uri)
            }
        }
    }

    fun handleUriLoading(view: WebView, uri: Uri): Boolean {
        return false
    }

    override fun backToRoot() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}