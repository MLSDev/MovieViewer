package com.shykun.volodymyr.movieviewer.presentation.discover.tab

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.databinding.FragmentDiscoverBinding
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.discover.DiscoverViewModel
import com.shykun.volodymyr.movieviewer.presentation.discover.DiscoverViewModelFactory
import com.shykun.volodymyr.movieviewer.presentation.discover.filter.FILTER_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.discover.filter.FilterType
import com.shykun.volodymyr.movieviewer.presentation.discover.list.DISCOVER_LIST_FRAGMENT_KEY
import kotlinx.android.synthetic.main.fragment_discover.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val DISCOVER_FRAGMENT_KEY = "discover_fragment_key"

class DiscoverFragment : Fragment() {

    @Inject lateinit var router: Router
    @Inject lateinit var viewModelFactory: DiscoverViewModelFactory
    private lateinit var viewModel: DiscoverViewModel
    private lateinit var binding: FragmentDiscoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppActivity).appComponent.inject(this)

        viewModel = ViewModelProviders.of(activity as AppActivity, viewModelFactory)
                .get(DiscoverViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        genreField.setOnClickListener { router.navigateTo(FILTER_LIST_FRAGMENT_KEY, FilterType.GENRE) }
        ratingField.setOnClickListener { router.navigateTo(FILTER_LIST_FRAGMENT_KEY, FilterType.RATING) }
        releaseYearField.setOnClickListener { router.navigateTo(FILTER_LIST_FRAGMENT_KEY, FilterType.YEAR) }
        clearFiltersButton.setOnClickListener { viewModel.clearFilters() }
        showResultsButton.setOnClickListener { router.navigateTo(DISCOVER_LIST_FRAGMENT_KEY) }
    }
}