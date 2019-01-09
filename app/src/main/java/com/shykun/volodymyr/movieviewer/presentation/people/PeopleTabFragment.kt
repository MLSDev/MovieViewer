package com.shykun.volodymyr.movieviewer.presentation.people

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.base.ScrollObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_people_tab.*

class PeopleTabFragment : MvpAppCompatFragment(), PeopleTabView {

    @InjectPresenter
    lateinit var presenter: PeopleTabPresenter
    lateinit var peopleTabAdapter: PeopleTabAdapter

    @ProvidePresenter
    fun providePeoplePresenter() = (activity as AppActivity).appComponent.getPeopleTabPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_people_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.onViewLoaded()
        peopleTabAdapter = PeopleTabAdapter(ArrayList())
        peopleList.apply {
            layoutManager = GridLayoutManager(this@PeopleTabFragment.context, 3)
            adapter = peopleTabAdapter
        }

        subscribeScrollObervable()
    }

    fun subscribeScrollObervable() {
        ScrollObservable.from(peopleList, 20)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    presenter.getPeople(peopleTabAdapter.lastLoadedPage + 1)
                    peopleTabAdapter.lastLoadedPage++
                }
                .subscribe()
    }

    override fun showPeople(people: ArrayList<Person>) {
        peopleTabAdapter.addPeople(people)
    }

    override fun showError() {
        Toast.makeText(this.context, "Error", Toast.LENGTH_SHORT).show()
    }
}
