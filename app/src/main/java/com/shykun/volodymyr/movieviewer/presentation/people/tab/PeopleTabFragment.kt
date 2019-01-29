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
import com.shykun.volodymyr.movieviewer.presentation.base.ScrollObservable
import com.shykun.volodymyr.movieviewer.presentation.base.TabNavigationFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_people_tab.*

const val PEOPLE_TAB_FRAGMENT_KEY = "people_tab_fragment_key"

class PeopleTabFragment : Fragment() {

    private lateinit var peopleTabAdapter: PeopleTabAdapter
    private lateinit var viewModel: PeopleTabViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        peopleTabAdapter = PeopleTabAdapter(ArrayList())

        viewModel = ViewModelProviders.of(this, (parentFragment as TabNavigationFragment).component?.getPeopleTabViewModelFactory())
                .get(PeopleTabViewModel::class.java)
        subscribeViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_people_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupPersonClick()
        subscribeScrollObervable()
        if (viewModel.peopleLiveData.value == null)
            viewModel.onViewLoaded()
    }

    private fun subscribeViewModel() {
        viewModel.peopleLiveData.observe(this, Observer { showPeople(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showError(it) })
    }

    private fun setupAdapter() {
        peopleList.apply {
            layoutManager = GridLayoutManager(this@PeopleTabFragment.context, 3)
            adapter = peopleTabAdapter
        }
    }

    private fun setupPersonClick() {
        peopleTabAdapter.personClickEvent.subscribe {
            viewModel.onPersonClicked(it)
        }
    }

    fun subscribeScrollObervable() {
        ScrollObservable.from(peopleList, 20)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    viewModel.getPeople(peopleTabAdapter.lastLoadedPage + 1)
                    peopleTabAdapter.lastLoadedPage++
                }
                .subscribe()
    }

    fun showPeople(people: List<Person>?) {
        if (people != null) {
            peopleTabAdapter.addPeople(people)
        }
    }

    fun showError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }
}
