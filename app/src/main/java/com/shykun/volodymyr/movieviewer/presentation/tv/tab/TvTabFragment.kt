package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.presentation.base.TabNavigationFragment
import kotlinx.android.synthetic.main.fragment_movies.*
import java.lang.Exception

const val TV_TAB_FRAGMENT_KEY = "tv_tab_fragment_key"

const val POPULAR_TV = 0
const val TOP_RATED_TV = 1
const val TV_ON_THE_AIR = 2

class TvTabFragment : Fragment() {

    private lateinit var generalTvTabAdapter: GeneralTvTabAdapter
    private lateinit var viewModel: TvTabViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        generalTvTabAdapter = GeneralTvTabAdapter(ArrayList())
        viewModel = ViewModelProviders.of(this, (parentFragment as TabNavigationFragment).component?.getTvTabViewModelFactory())
                .get(TvTabViewModel::class.java)
        subscribeViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupSeeAllClick()
        setupTvClick()
        if (viewModel.topRatedTvLiveData.value == null)
            viewModel.onViewLoaded()

    }

    private fun subscribeViewModel() {
        viewModel.popularTvLiveData.observe(this, Observer { showPopularTV(it) })
        viewModel.topRatedTvLiveData.observe(this, Observer { showTopRatedTV(it) })
        viewModel.tvOnTheAirLiveData.observe(this, Observer { showTVOnTheAir(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showError(it) })
    }

    private fun setupAdapter() {
        movieCategoryList.apply {
            layoutManager = LinearLayoutManager(this@TvTabFragment.context, LinearLayoutManager.VERTICAL, false)
            adapter = generalTvTabAdapter
        }
    }

    private fun setupSeeAllClick() {
        generalTvTabAdapter.seeAllClickEvent.subscribe {
            val tvType = when (it) {
                POPULAR_TV -> TvType.POPULAR
                TOP_RATED_TV -> TvType.TOP_RATED
                TV_ON_THE_AIR -> TvType.ON_THE_AIR
                else -> throw Exception("Undefined tv type")
            }
            viewModel.onViewAllButtonClicked(tvType)
        }
    }

    private fun setupTvClick() {
        generalTvTabAdapter.tvClickEvent.subscribe {
            viewModel.onTvClicked(it)
        }
    }

    fun showPopularTV(tvList: List<Tv>?) {
        if (tvList != null) {
            generalTvTabAdapter.addTV(tvList, POPULAR_TV)
        }
    }

    fun showTopRatedTV(tvList: List<Tv>?) {
        if (tvList != null) {
            generalTvTabAdapter.addTV(tvList, TOP_RATED_TV)
        }
    }

    fun showTVOnTheAir(tvList: List<Tv>?) {
        if (tvList != null) {
            generalTvTabAdapter.addTV(tvList, TV_ON_THE_AIR)
        }
    }

    fun showError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }
}