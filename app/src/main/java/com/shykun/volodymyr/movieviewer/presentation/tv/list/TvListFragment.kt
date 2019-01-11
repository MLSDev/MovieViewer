package com.shykun.volodymyr.movieviewer.presentation.tv.list

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
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.base.ScrollObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movie_list.*


const val TV_LIST_FRAGMENT_KEY = "tv_list_fragment_key"
private const val TV_TYPE_KEY = "tv_type"
private lateinit var tvListAdapter: TvListAdapter

class TvListFragment : MvpAppCompatFragment(), TvListView {

    private lateinit var tvType: TvType
    @InjectPresenter
    lateinit var presenter: TvListPresenter

    @ProvidePresenter
    fun providePresenter() = (activity as AppActivity).appComponent.getTvListPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvType = arguments?.getSerializable(TV_TYPE_KEY) as TvType
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        subscribeScrollObervable()
        presenter.onViewLoaded(tvType)
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
                    presenter.getTvList(tvListAdapter.lastLoadedPage + 1)
                    tvListAdapter.lastLoadedPage++
                }
                .subscribe()
    }

    override fun showTvList(tvList: ArrayList<Tv>) {
        tvListAdapter.addTvList(tvList)
    }

    override fun showError() {
        Toast.makeText(this.context, "Error", Toast.LENGTH_SHORT).show()
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