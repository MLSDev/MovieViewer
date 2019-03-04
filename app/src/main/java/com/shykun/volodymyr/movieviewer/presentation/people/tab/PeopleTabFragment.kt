package com.shykun.volodymyr.movieviewer.presentation.people.tab

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.ScrollObservable
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.people.details.PERSON_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.people.search.PEOPLE_SEARCH_FRAGMENT_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_people_tab.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val PEOPLE_TAB_FRAGMENT_KEY = "people_tab_fragment_key"
const val PEOPLE_SEARCH_QUERY_KEY = "people_search_query"

class PeopleTabFragment : Fragment(), BackButtonListener {

    private lateinit var peopleTabAdapter: PeopleTabAdapter
    private lateinit var viewModel: PeopleTabViewModel
    private var searchQuery: String? = null

    @Inject
    lateinit var viewModelFactory: PeopleTabViewModelFactory
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)

        searchQuery = arguments?.getString(PEOPLE_SEARCH_QUERY_KEY, null)
        peopleTabAdapter = PeopleTabAdapter()
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PeopleTabViewModel::class.java)

        subscribeViewModel()
        setupPersonClick()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_people_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupAdapter()
        subscribeScrollObervable()
    }

    private fun setupToolbar() {
        peopleToolbar.inflateMenu(R.menu.manu_app)
        peopleToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.action_search -> router.navigateTo(PEOPLE_SEARCH_FRAGMENT_KEY)
            }
            true
        }
    }

    private fun subscribeViewModel() {
        viewModel.peopleLiveData.observe(this, Observer { showPeople(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showError(it) })
    }

    private fun setupAdapter() {
        peopleList.apply {
            layoutManager = getGridLayoutManager()
            layoutManager
            adapter = peopleTabAdapter
        }
    }

    fun getGridLayoutManager(): GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(this@PeopleTabFragment.context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when(peopleTabAdapter.getItemViewType(position)) {
                PERSON -> 1
                LOADING -> 3
                else -> -1
            }
        }
        return gridLayoutManager
    }

    private fun setupPersonClick() {
        peopleTabAdapter.personClickEvent.subscribe {
            router.navigateTo(PERSON_DETAILS_FRAGMENT_KEY, it)
        }
    }

    private fun subscribeScrollObervable() {
        ScrollObservable.from(peopleList, 20)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    if (searchQuery == null)
                        viewModel.getPeople(peopleTabAdapter.lastLoadedPage + 1)
                    else
                        viewModel.searchPeople(searchQuery!!, peopleTabAdapter.lastLoadedPage + 1)
                    peopleTabAdapter.lastLoadedPage++
                }
                .subscribe()
    }

    private fun showPeople(people: List<Person>?) {
        if (people != null) {
            peopleTabAdapter.addPeople(people)
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackClicked(): Boolean {
        return false
    }

    companion object {
        fun newInstance(args: Bundle?): PeopleTabFragment {
            val peopleTabFragment = PeopleTabFragment()
            peopleTabFragment.arguments = args

            return peopleTabFragment
        }
    }
}
