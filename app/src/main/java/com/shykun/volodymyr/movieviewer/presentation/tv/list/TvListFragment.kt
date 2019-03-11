package com.shykun.volodymyr.movieviewer.presentation.tv.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
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
import com.shykun.volodymyr.movieviewer.presentation.common.adapters.VerticalListAdapter
import com.shykun.volodymyr.movieviewer.presentation.discover.tab.GENRES_KEY
import com.shykun.volodymyr.movieviewer.presentation.discover.tab.RATING_KEY
import com.shykun.volodymyr.movieviewer.presentation.discover.tab.RELEASE_YEAR_KEY
import com.shykun.volodymyr.movieviewer.presentation.model.VerticalItemList
import com.shykun.volodymyr.movieviewer.presentation.profile.SESSION_ID_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TV_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.utils.ScrollObservable
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
    private lateinit var tvListAdapter: VerticalListAdapter

    @Inject
    lateinit var viewModelFactory: TvListViewModelFactory
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)
        tvType = arguments?.getSerializable(TV_TYPE_KEY) as TvType
        tvListAdapter = VerticalListAdapter()
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TvListViewModel::class.java)

        subscribeViewModel()
        setupTvClick()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle()
        setupBackButton()
        setupAdapter()
        subscribeScrollObervable()
    }

    private fun setToolbarTitle() {
        movieListToolbar.title = when (tvType) {
            TvType.TOP_RATED -> getString(R.string.top_rated_tv)
            TvType.POPULAR -> getString(R.string.popular_tv)
            TvType.ON_THE_AIR -> getString(R.string.tv_on_the_air)
            TvType.SEARCHED -> getString(R.string.results)
            TvType.RATED -> getString(R.string.rated_tv)
            TvType.WATCHLIST -> getString(R.string.tv_watchlist)
            TvType.FAVORITE -> getString(R.string.favorite_tv)
            TvType.DISCOVERED -> getString(R.string.results)
        }
    }

    private fun setupBackButton() {
        movieListToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        movieListToolbar.setNavigationOnClickListener { onBackClicked() }
    }

    private fun setupTvClick() {
        tvListAdapter.clickEvent.subscribe {
            router.navigateTo(TV_DETAILS_FRAGMENT_KEY, it)
        }
    }

    private fun subscribeViewModel() {
        viewModel.tvListLiveData.observe(this, Observer { showTvList(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showError(it) })
    }

    private fun setupAdapter() {
        movieList.apply {
            layoutManager = LinearLayoutManager(this@TvListFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false)
            adapter = tvListAdapter
        }
    }

    private fun subscribeScrollObervable() {
        val sessionId = prefs.getString(SESSION_ID_KEY, null)

        ScrollObservable.from(movieList, 20)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    when (tvType) {
                        TvType.TOP_RATED, TvType.POPULAR, TvType.ON_THE_AIR -> {
                            viewModel.getTvList(tvType, tvListAdapter.nextPage)
                        }
                        TvType.SEARCHED -> {
                            val query = arguments?.getString(SEARCH_QUERY_KEY)
                            viewModel.searchTv(query!!, tvListAdapter.nextPage)
                        }
                        TvType.RATED -> {
                            viewModel.getRatedTv(sessionId, tvListAdapter.nextPage)
                        }
                        TvType.FAVORITE -> {
                            viewModel.getFavoriteTv(sessionId, tvListAdapter.nextPage)
                        }
                        TvType.WATCHLIST -> {
                            viewModel.getTvWatchlist(sessionId, tvListAdapter.nextPage)
                        }
                        TvType.DISCOVERED -> {
                            val year = arguments?.getInt(RELEASE_YEAR_KEY)
                            val rating = arguments?.getInt(RATING_KEY)
                            val genres = arguments?.getString(GENRES_KEY)

                            val airDate = if (year != null) "$year-01-01" else null

                            viewModel.discoverTv(airDate, rating, genres, tvListAdapter.nextPage)
                        }
                    }
                    tvListAdapter.nextPage++
                }
                .subscribe()
    }

    fun showTvList(tvList: VerticalItemList?) {
        if (tvList != null) {
            if (tvList.totalItemsCount > 0) {
                tvListAdapter.addItems(tvList.items)
                tvListAdapter.totalItemsCount = tvList.totalItemsCount
            } else {
                movieList.visibility = View.GONE
                emptyState.visibility = View.VISIBLE
            }
        }
    }

    fun showError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()

        with(tvListAdapter) {
            clearItems()
            totalItemsCount = -1
            nextPage = 1
        }
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