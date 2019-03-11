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
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalItem
import com.shykun.volodymyr.movieviewer.presentation.model.ItemType
import com.shykun.volodymyr.movieviewer.presentation.search.ITEM_TYPE_KEY
import com.shykun.volodymyr.movieviewer.presentation.search.SEARCH_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TV_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_TYPE_KEY
import kotlinx.android.synthetic.main.fragment_movies_tab.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val TV_TAB_FRAGMENT_KEY = "tv_tab_fragment_key"

const val POPULAR_TV = 0
const val TOP_RATED_TV = 1
const val TV_ON_THE_AIR = 2

class TvTabFragment : Fragment(), BackButtonListener {

    private lateinit var generalTvTabAdapter: GeneralTvTabAdapter
    private lateinit var viewModel: TvTabViewModel

    @Inject
    lateinit var viewModelFactory: TvTabViewModelFactory
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)

        generalTvTabAdapter = GeneralTvTabAdapter()
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TvTabViewModel::class.java)

        subscribeViewModel()
        setupSeeAllClick()
        setupTvClick()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupToolbar()
        if (viewModel.topRatedTvLiveData.value == null)
            viewModel.onViewLoaded()

    }

    private fun setupToolbar() {
        moviesToolbar.inflateMenu(R.menu.manu_app)
        moviesToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> router.navigateTo(SEARCH_FRAGMENT_KEY, Bundle().apply {
                    putSerializable(ITEM_TYPE_KEY, ItemType.TV)
                })
            }
            true
        }
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
            val args = Bundle()
            args.putSerializable(TV_TYPE_KEY, tvType)
            router.navigateTo(TV_LIST_FRAGMENT_KEY, args)
        }
    }

    private fun setupTvClick() {
        generalTvTabAdapter.tvClickEvent.subscribe { router.navigateTo(TV_DETAILS_FRAGMENT_KEY, it) }
    }

    fun showPopularTV(tvList: List<HorizontalItem>?) {
        if (tvList != null) {
            generalTvTabAdapter.addTV(tvList, POPULAR_TV)
        }
    }

    fun showTopRatedTV(tvList: List<HorizontalItem>?) {
        if (tvList != null) {
            generalTvTabAdapter.addTV(tvList, TOP_RATED_TV)
        }
    }

    fun showTVOnTheAir(tvList: List<HorizontalItem>?) {
        if (tvList != null) {
            generalTvTabAdapter.addTV(tvList, TV_ON_THE_AIR)
        }
    }

    fun showError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackClicked(): Boolean {
        return false
    }
}