package com.shykun.volodymyr.movieviewer.presentation.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.network.response.AccountDetailsResponse
import com.shykun.volodymyr.movieviewer.databinding.FragmentProfileBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.utils.NavigationKeys
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)
        viewModel = ViewModelProviders.of(parentFragment!!, viewModelFactory)
                .get(ProfileViewModel::class.java)
        subscribeViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
    }

    private fun setClickListeners() {
        loginButton.setOnClickListener { router.navigateTo(LOGIN_FRAGMENT_KEY) }
    }

    private fun subscribeViewModel() {
        viewModel.accountDetailsLiveData.observe(this, Observer { showAccountDetails(it) })
    }

    private fun showAccountDetails(accountDetailsResponse: AccountDetailsResponse?) {
        binding.accountDetails = accountDetailsResponse
    }

    override fun onBackClicked() = false
}