package com.shykun.volodymyr.movieviewer.presentation.tv.list

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
import com.shykun.volodymyr.movieviewer.data.network.response.TvResponse
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.ScrollObservable
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TV_DETAILS_FRAGMENT_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movie_list.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject


const val TV_LIST_FRAGMENT_KEY = "tv_list_fragment_key"
const val TV_TYPE_KEY = "tv_type"
const val SEARCH_QUERY_KEY = "query_search_key"

class TvListFragment : Fragment(), BackButtonListener {

    private lateinit var tvType: TvType
    private lateinit var viewModel: TvListViewModel
    private lateinit var tvListAdapter: TvListAdapter
    private var searchQuery: String? = null

    @Inject
    lateinit var viewModelFactory: TvListViewModelFactory
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (parentFragment as TabNavigationFragment).component?.inject(this)

        tvType = arguments?.getSerializable(TV_TYPE_KEY) as TvType
        searchQuery = arguments?.getString(SEARCH_QUERY_KEY, null)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TvListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackButton()
        setupAdapter()
        setupTvClick()
        subscribeScrollObervable()
        subscribeViewModel()
    }

    private fun setupBackButton() {
        movieListToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        movieListToolbar.setNavigationOnClickListener { onBackClicked() }
    }

    private fun setupTvClick() {
        tvListAdapter.clickObservable.subscribe {
            router.navigateTo(TV_DETAILS_FRAGMENT_KEY, it)
        }
    }

    private fun subscribeViewModel() {
        viewModel.tvListLiveData.observe(this, Observer { showTvList(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showError(it) })
    }

    private fun setupAdapter() {
        tvListAdapter = TvListAdapter(ArrayList(), tvType)
        movieList.apply {
            layoutManager = LinearLayoutManager(this@TvListFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false)
            adapter = tvListAdapter
        }
    }

    private fun subscribeScrollObervable() {
        ScrollObservable.from(movieList, 10)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    if (tvType != TvType.SEARCHED)
                        viewModel.getTvList(tvListAdapter.lastLoadedPage + 1, tvType)
                    else
                        viewModel.searchTv(searchQuery!!,tvListAdapter.lastLoadedPage + 1)
                    tvListAdapter.lastLoadedPage++
                }
                .subscribe()
    }

    fun showTvList(tvResponse: TvResponse?) {
        if (tvResponse != null) {
            tvListAdapter.addTvList(tvResponse.results)
            tvListAdapter.totalItemsCount = tvResponse.totalResults
        }
    }

    fun showError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackClicked(): Boolean {
        router.exit()

        return true
    }

    companion object {
        fun newInstance(args: Bundle): TvListFragment {
            val tvListFragment = TvListFragment()
            tvListFragment.arguments = args

            return tvListFragment
        }
    }
}