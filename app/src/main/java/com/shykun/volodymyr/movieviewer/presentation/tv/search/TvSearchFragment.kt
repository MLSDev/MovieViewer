package com.shykun.volodymyr.movieviewer.presentation.tv.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.data.network.response.TvResponse
import com.shykun.volodymyr.movieviewer.databinding.FragmentSearchBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.list.SEARCH_QUERY_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TV_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_TYPE_KEY
import kotlinx.android.synthetic.main.fragment_search.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val TV_SEARCH_FRAGMENT_KEY = "tv_search_fragment_key"

class TvSearchFragment : Fragment(), BackButtonListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: TvSearchViewModel
    private lateinit var tvSearchAdapter: TvSearchAdapter

    @Inject
    lateinit var viewModelFactory: TvSearchViewModelFactory
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TvSearchViewModel::class.java)
        tvSearchAdapter = TvSearchAdapter(ArrayList())

        subscribeViewModel()
        setupTvClick()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSearchHint()
        setupAdapter()
        setupSearchView()
        setupBackButton()
    }

    private fun setSearchHint() {
        binding.searchHint = getString(R.string.search_tv)
    }

    private fun setupAdapter() {
        searchList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = tvSearchAdapter
        }
    }

    private fun setupBackButton() {
        searchToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        searchToolbar.setNavigationOnClickListener { onBackClicked() }
    }

    private fun setupSearchView() {
        showKeyboard()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty())
                    viewModel.searchTv(newText)
                else
                    tvSearchAdapter.clearTvList()

                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                hideKeyboard()

                val args = Bundle()
                args.putSerializable(TV_TYPE_KEY, TvType.SEARCHED)
                args.putString(SEARCH_QUERY_KEY, query)
                router.navigateTo(TV_LIST_FRAGMENT_KEY, args)

                return true
            }
        })
    }

    private fun subscribeViewModel() {
        viewModel.searchedLiveData.observe(this, Observer { showTvList(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showLoadingError(it) })
    }

    private fun setupTvClick() {
        tvSearchAdapter.clickObservable.subscribe {
            hideKeyboard()
            router.navigateTo(TV_DETAILS_FRAGMENT_KEY, it)
        }
    }

    private fun showKeyboard() {
        searchView.requestFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(searchView.windowToken, 0)
    }

    private fun showTvList(tvResponse: TvResponse?) {
        if (tvResponse != null) {
            tvSearchAdapter.setTvList(tvResponse.results)
        }
    }

    private fun showLoadingError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackClicked(): Boolean {
        hideKeyboard()
        router.exit()

        return true
    }
}