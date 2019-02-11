package com.shykun.volodymyr.movieviewer.presentation.people.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Role
import com.shykun.volodymyr.movieviewer.data.network.response.PersonDetailsResponse
import com.shykun.volodymyr.movieviewer.databinding.FragmentPersonDetailsBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MOVIE_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TV_DETAILS_FRAGMENT_KEY
import kotlinx.android.synthetic.main.fragment_person_details.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val PERSON_DETAILS_FRAGMENT_KEY = "person_details_fragment_key"
private const val PERSON_ID_KEY = "person_id_key"

class PersonDetailsFragment : Fragment(), BackButtonListener {

    private var personId = -1
    private lateinit var viewModel: PersonDetailsViewModel
    private lateinit var binding: FragmentPersonDetailsBinding
    private lateinit var personCastAdapter: PersonCastAdapter

    @Inject
    lateinit var viewModelFactory: PersonDetailsViewModelFactory
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)

        personCastAdapter = PersonCastAdapter(ArrayList())
        personId = arguments?.getInt(PERSON_ID_KEY)!!
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PersonDetailsViewModel::class.java)

        subscribeViewModel()
        setupPersonCastCLick()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_person_details,
                container,
                false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBackButton()
        setupPersonCastAdapter()
        viewModel.onViewLoaded(personId)
    }

    private fun setupBackButton() {
        personDetailsToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        personDetailsToolbar.setNavigationOnClickListener { onBackClicked() }
    }

    private fun setupPersonCastAdapter() {
        personCast.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = personCastAdapter
        }
    }

    private fun subscribeViewModel() {
        viewModel.personDetailsLiveData.observe(this, Observer { showPersonDetails(it) })
        viewModel.personCastLiveData.observe(this, Observer { showPersonCast(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showLoadingError(it) })
    }

    private fun showPersonDetails(personDetails: PersonDetailsResponse?) {
        binding.personDetails = personDetails
    }

    private fun showPersonCast(roles: List<Role>?) {
        if (roles != null) {
            personCastAdapter.addRoles(roles)
        }
    }

    private fun showLoadingError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupPersonCastCLick() {
        personCastAdapter.clickObservable.subscribe {
            when (it.mediaType) {
                "tv" -> router.navigateTo(TV_DETAILS_FRAGMENT_KEY, it.id)
                "movie" -> router.navigateTo(MOVIE_DETAILS_FRAGMENT_KEY, it.id)
            }
        }
    }


    override fun onBackClicked(): Boolean {
        router.exit()

        return true
    }

    companion object {
        fun newInstance(personId: Int): PersonDetailsFragment {
            val personDetailsFragment = PersonDetailsFragment()
            val args = Bundle()
            args.putInt(PERSON_ID_KEY, personId)
            personDetailsFragment.arguments = args

            return personDetailsFragment
        }
    }

}
