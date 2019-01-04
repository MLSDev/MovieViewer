package com.shykun.volodymyr.movieviewer.presentation.tv

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.TV
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

const val POPULAR_TV = 0
const val TOP_RATED_TV = 1
const val TV_ON_THE_AIR = 2

class TVFragment : MvpAppCompatFragment(), TVView {

    @Inject
    @InjectPresenter
    lateinit var presenter: TVPresenter
    lateinit var generalTVAdapter: GeneralTVAdapter

    @ProvidePresenter
    fun provideTVPresenter() = (activity as AppActivity).appComponent.getTVPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        (activity as AppActivity).appComponent.inject(this)
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
        generalTVAdapter = GeneralTVAdapter(ArrayList(3))
        movieList.apply {
            layoutManager = LinearLayoutManager(this@TVFragment.context, LinearLayoutManager.VERTICAL, false)
            adapter = generalTVAdapter
        }
    }

    override fun showPopularTV(tvList: ArrayList<TV>) {
        generalTVAdapter.addTV(tvList, POPULAR_TV)
    }

    override fun showTopRatedTV(tvList: ArrayList<TV>) {
        generalTVAdapter.addTV(tvList, TOP_RATED_TV)
    }

    override fun showTVOnTheAir(tvList: ArrayList<TV>) {
        generalTVAdapter.addTV(tvList, TV_ON_THE_AIR)
    }

    override fun showError() {
        Toast.makeText(this.context, "Error", Toast.LENGTH_SHORT).show()
    }
}