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
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.base.ScrollObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movie_list.*


const val TV_LIST_FRAGMENT_KEY = "tv_list_fragment_key"

private const val TV_TYPE_KEY = "tv_type"
private lateinit var tvListAdapter: TvListAdapter

class TvListFragment : Fragment() {

    private lateinit var tvType: TvType
    private lateinit var viewModel: TvListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvType = arguments?.getSerializable(TV_TYPE_KEY) as TvType
        viewModel = ViewModelProviders.of(this, (activity as AppActivity).appComponent.getTvListViewModelFactory())
                .get(TvListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        subscribeScrollObervable()
        subscribeViewModel()
        viewModel.onViewLoaded(tvType)
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
                    viewModel.getTvList(tvListAdapter.lastLoadedPage + 1)
                    tvListAdapter.lastLoadedPage++
                }
                .subscribe()
    }

    fun showTvList(tvList: List<Tv>?) {
        if (tvList != null) {
            tvListAdapter.addTvList(tvList)
        }
    }

    fun showError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(tvType: TvType): TvListFragment {
            val tvListFragment = TvListFragment()
            val args = Bundle()
            args.putSerializable(TV_TYPE_KEY, tvType)
            tvListFragment.arguments = args

            return tvListFragment
        }
    }
}