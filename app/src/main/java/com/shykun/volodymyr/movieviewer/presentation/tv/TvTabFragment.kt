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
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import kotlinx.android.synthetic.main.fragment_movies.*

const val POPULAR_TV = 0
const val TOP_RATED_TV = 1
const val TV_ON_THE_AIR = 2

class TvTabFragment : MvpAppCompatFragment(), TvTabView {

    @InjectPresenter
    lateinit var presenter: TvTabPresenter
    lateinit var generalTvTabAdapter: GeneralTvTabAdapter

    @ProvidePresenter
    fun provideTVPresenter() = (activity as AppActivity).appComponent.getTvPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
        generalTvTabAdapter = GeneralTvTabAdapter(ArrayList(3))
        movieList.apply {
            layoutManager = LinearLayoutManager(this@TvTabFragment.context, LinearLayoutManager.VERTICAL, false)
            adapter = generalTvTabAdapter
        }
    }

    override fun showPopularTV(tvList: ArrayList<Tv>) {
        generalTvTabAdapter.addTV(tvList, POPULAR_TV)
    }

    override fun showTopRatedTV(tvList: ArrayList<Tv>) {
        generalTvTabAdapter.addTV(tvList, TOP_RATED_TV)
    }

    override fun showTVOnTheAir(tvList: ArrayList<Tv>) {
        generalTvTabAdapter.addTV(tvList, TV_ON_THE_AIR)
    }

    override fun showError() {
        Toast.makeText(this.context, "Error", Toast.LENGTH_SHORT).show()
    }
}