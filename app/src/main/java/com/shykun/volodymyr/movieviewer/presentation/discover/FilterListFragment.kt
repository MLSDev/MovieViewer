package com.shykun.volodymyr.movieviewer.presentation.discover


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.utils.GenreHelper
import kotlinx.android.synthetic.main.fragment_filter_list.*
import java.util.*
import kotlin.collections.ArrayList

const val FILTER_TYPE_KEY = "filter_type_key"

enum class FilterType {
    GENRE, YEAR, RATING
}

class FilterListFragment : Fragment() {

    private lateinit var type: FilterType
    private lateinit var genreListAdapter: MultipleSelectionAdapter
    private lateinit var ratingListAdapter: SingleSelectionAdapter
    private lateinit var yearsListAdapter: SingleSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        type = arguments?.getSerializable(FILTER_TYPE_KEY) as FilterType
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFilterList()
    }

    private fun initFilterList() {
        when (type) {
            FilterType.GENRE -> setupGenreList()
            FilterType.YEAR -> setupYearsList()
            FilterType.RATING -> setupRatingList()
        }
    }

    private fun setupYearsList() {
        val years = ArrayList<Int>()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        years.addAll((currentYear downTo 1950))

        yearsListAdapter = SingleSelectionAdapter(years)
        filterList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = yearsListAdapter
        }
    }

    private fun setupGenreList() {
        val genres = ArrayList<String>()
        genres.addAll(GenreHelper.genres.values.toList())

        val genreListAdapter = MultipleSelectionAdapter(genres)
        filterList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = genreListAdapter
        }
    }

    private fun setupRatingList() {
        val ratingList = ArrayList<Int>()
        ratingList.addAll((10 downTo 1))

        val ratingListAdapter = SingleSelectionAdapter(ratingList)
        filterList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = ratingListAdapter
        }
    }

    companion object {
        fun newInstance(type: FilterType): FilterListFragment {
            val filterListFragment = FilterListFragment()
            val args = Bundle()
            args.putSerializable(FILTER_TYPE_KEY, type)
            filterListFragment.arguments = args

            return filterListFragment
        }
    }
}

