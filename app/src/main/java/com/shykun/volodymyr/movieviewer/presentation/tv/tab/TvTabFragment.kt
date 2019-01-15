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
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import kotlinx.android.synthetic.main.fragment_movies.*
import java.lang.Exception

const val POPULAR_TV = 0
const val TOP_RATED_TV = 1
const val TV_ON_THE_AIR = 2

class TvTabFragment : Fragment() {

    private lateinit var generalTvTabAdapter: GeneralTvTabAdapter
    private lateinit var viewModel: TvTabViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, (activity as AppActivity).appComponent.getTvTabViewModelFactory())
                .get(TvTabViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupSeeAllClick()
        subscribeViewModel()
        viewModel.onViewLoaded()

    }

    private fun subscribeViewModel() {
        viewModel.popularTvLiveData.observe(this, Observer { showPopularTV(it) })
        viewModel.topRatedTvLiveData.observe(this, Observer { showTopRatedTV(it) })
        viewModel.tvOnTheAirLiveData.observe(this, Observer { showTVOnTheAir(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showError(it) })
    }

    private fun setupAdapter() {
        generalTvTabAdapter = GeneralTvTabAdapter(ArrayList())
        movieCategoryList.apply {
            layoutManager = LinearLayoutManager(this@TvTabFragment.context, LinearLayoutManager.VERTICAL, false)
            adapter = generalTvTabAdapter
        }
    }

    private fun setupSeeAllClick() {
        generalTvTabAdapter.clickEvent.subscribe {
            val tvType = when (it) {
                POPULAR_TV -> TvType.POPULAR
                TOP_RATED_TV -> TvType.TOP_RATED
                TV_ON_THE_AIR -> TvType.ON_THE_AIR
                else -> throw Exception("Undefined tv type")
            }
            viewModel.onViewAllButtonClicked(tvType)
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