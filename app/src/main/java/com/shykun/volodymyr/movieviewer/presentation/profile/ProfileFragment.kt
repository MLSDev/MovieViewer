package com.shykun.volodymyr.movieviewer.presentation.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.data.network.response.AccountDetailsResponse
import com.shykun.volodymyr.movieviewer.data.network.response.LogoutResponse
import com.shykun.volodymyr.movieviewer.data.network.response.SessionIdResponse
import com.shykun.volodymyr.movieviewer.databinding.FragmentProfileBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_TYPE_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_TYPE_KEY
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val SESSION_ID_KEY = "session_id_key"
const val PROFILE_FRAGMENT_KEY = "profile_fragment_key"

class ProfileFragment : Fragment(), BackButtonListener {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    @Inject
    lateinit var viewModelFactory: ProfileViewModelFactory
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)
        viewModel = ViewModelProviders.of(parentFragment!!, viewModelFactory)
                .get(ProfileViewModel::class.java)

        val sessionId = prefs.getString(SESSION_ID_KEY, null)
        if (sessionId != null)
            viewModel.getAccountDetails(sessionId!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.viewModel = viewModel
        binding.sessionId = prefs.getString(SESSION_ID_KEY, null)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        subscribeViewModel()
        setClickListeners()
    }

    private fun setupToolbar() {
        val sessionId = prefs.getString(SESSION_ID_KEY, null)
        if (sessionId != null) {
            profileToolbar.inflateMenu(R.menu.menu_profile)
            profileToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_logout -> {
                        viewModel.logout(sessionId!!)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setClickListeners() {
        loginButton.setOnClickListener { router.navigateTo(LOGIN_FRAGMENT_KEY) }
        ratedMovies.setOnClickListener {
            val args = Bundle()
            args.putSerializable(MOVIE_TYPE_KEY, MoviesType.RATED)
            router.navigateTo(MOVIE_LIST_FRAGMENT_KEY, args)
        }
        ratedTv.setOnClickListener {
            val args = Bundle()
            args.putSerializable(TV_TYPE_KEY, TvType.RATED)
            router.navigateTo(TV_LIST_FRAGMENT_KEY, args)
        }
        favoriteMovies.setOnClickListener {
            val args = Bundle()
            args.putSerializable(MOVIE_TYPE_KEY, MoviesType.FAVORITE)
            router.navigateTo(MOVIE_LIST_FRAGMENT_KEY, args)
        }
        favoriteTv.setOnClickListener {
            val args = Bundle()
            args.putSerializable(TV_TYPE_KEY, TvType.FAVORITE)
            router.navigateTo(TV_LIST_FRAGMENT_KEY, args)
        }
        movieWatchlist.setOnClickListener {
            val args = Bundle()
            args.putSerializable(MOVIE_TYPE_KEY, MoviesType.WATCHLIST)
            router.navigateTo(MOVIE_LIST_FRAGMENT_KEY, args)
        }
        tvWatchList.setOnClickListener {
            val args = Bundle()
            args.putSerializable(TV_TYPE_KEY, TvType.WATCHLIST)
            router.navigateTo(TV_LIST_FRAGMENT_KEY, args)
        }
    }

    private fun subscribeViewModel() {
        viewModel.accountDetailsLiveData.observe(this, Observer { showAccountDetails(it) })
        viewModel.sessionIdLiveData.observe(this, Observer { handleLoginResponse(it) })
        viewModel.logoutLiveData.observe(this, Observer { handleLogoutReesponse(it) })
    }

    private fun showAccountDetails(accountDetailsResponse: AccountDetailsResponse?) {
        binding.accountDetails = accountDetailsResponse
    }

    private fun handleLoginResponse(sessionIdResponse: SessionIdResponse?) {
        if (sessionIdResponse != null) {
            binding.sessionId = sessionIdResponse.sessionId
        }
    }

    private fun handleLogoutReesponse(logoutResponse: LogoutResponse?) {
        if (logoutResponse != null) {
            binding.accountDetails = null
            binding.sessionId = null
        }
        prefs.edit().putString(SESSION_ID_KEY, null).apply()
    }

    override fun onBackClicked() = false
}