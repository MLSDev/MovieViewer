package com.shykun.volodymyr.movieviewer.presentation.people.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.databinding.FragmentSearchBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.people.details.PERSON_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.people.tab.PEOPLE_SEARCH_QUERY_KEY
import com.shykun.volodymyr.movieviewer.presentation.people.tab.PEOPLE_TAB_FRAGMENT_KEY
import kotlinx.android.synthetic.main.fragment_search.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val PEOPLE_SEARCH_FRAGMENT_KEY = "people_search_fragment_key"

class PeopleSearchFragment : Fragment(), BackButtonListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: PeopleSearchViewModel
    private lateinit var peopleSearchAdapter: PeopleSearchAdapter


    @Inject
    lateinit var viewModelFactory: PeopleSearchViewModelFactory
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PeopleSearchViewModel::class.java)
        peopleSearchAdapter = PeopleSearchAdapter(ArrayList())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSearchHint()
        setupAdapter()
        setupSearchView()
        setupBackButton()
        setupPersonClick()
        subscribeViewModel()
    }

    private fun setSearchHint() {
        binding.searchHint = getString(R.string.search_people)
    }

    private fun setupAdapter() {
        searchList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = peopleSearchAdapter
        }
    }

    private fun setupBackButton() {
        searchToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        searchToolbar.setNavigationOnClickListener { onBackClicked() }
    }

    private fun setupSearchView() {
        showKeyboard()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty())
                    viewModel.searchPeople(newText)
                else
                    showSearchedPeople(ArrayList())

                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                val args = Bundle()
                args.putString(PEOPLE_SEARCH_QUERY_KEY, query)
                router.navigateTo(PEOPLE_TAB_FRAGMENT_KEY, args)
                return true
            }
        })
    }

    private fun showKeyboard() {
        searchView.requestFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(searchView.windowToken, 0)
    }

    private fun subscribeViewModel() {
        viewModel.searchedPeopleLiveData.observe(this, Observer { showSearchedPeople(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showLoadingError(it) })
    }

    private fun showSearchedPeople(people: List<Person>?) {
        if (people != null) {
            peopleSearchAdapter.setPeople(people)
        }
    }

    private fun setupPersonClick() {
        peopleSearchAdapter.clickObservable.subscribe {
            router.navigateTo(PERSON_DETAILS_FRAGMENT_KEY, it)
        }
    }

    private fun showLoadingError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackClicked(): Boolean {
        hideKeyboard()
        router.exit()

        return true
    }
}