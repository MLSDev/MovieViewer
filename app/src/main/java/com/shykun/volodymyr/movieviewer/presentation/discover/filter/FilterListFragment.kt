package com.shykun.volodymyr.movieviewer.presentation.discover.filter


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.discover.DiscoverViewModel
import com.shykun.volodymyr.movieviewer.presentation.discover.DiscoverViewModelFactory
import com.shykun.volodymyr.movieviewer.presentation.utils.GenreHelper
import kotlinx.android.synthetic.main.fragment_filter_list.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

const val FILTER_TYPE_KEY = "filter_type_key"
const val FILTER_LIST_FRAGMENT_KEY = "filter_list_fragment_key"

enum class FilterType {
    GENRE, YEAR, RATING
}

class FilterListFragment : Fragment() {

    private lateinit var type: FilterType
    private lateinit var genreListAdapter: MultipleSelectionAdapter
    private lateinit var ratingListAdapter: SingleSelectionAdapter
    private lateinit var yearsListAdapter: SingleSelectionAdapter
    private lateinit var viewModel: DiscoverViewModel
    @Inject
    lateinit var viewModelFactory: DiscoverViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppActivity).appComponent.inject(this)

        type = arguments?.getSerializable(FILTER_TYPE_KEY) as FilterType
        viewModel = ViewModelProviders.of(activity as AppActivity, viewModelFactory)
                .get(DiscoverViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFilterList()
        setupBackButton()
    }

    override fun onPause() {
        super.onPause()

        when (type) {
            FilterType.GENRE -> {
                val checkedItems = genreListAdapter.getCheckedItems()
                viewModel.genres.get()?.clear()
                viewModel.genres.set(GenreHelper.getGenresByNames(checkedItems))
                viewModel.genreNames.set(
                        if (checkedItems.isNotEmpty())
                            checkedItems.joinToString()
                        else
                            "All genres")
            }
            FilterType.RATING -> {
                viewModel.rating.set(ratingListAdapter.getChosenItem())
            }
            FilterType.YEAR -> {
                viewModel.year.set(yearsListAdapter.getChosenItem())
            }
        }
    }

    private fun initFilterList() {
        when (type) {
            FilterType.GENRE -> setupGenreList()
            FilterType.YEAR -> setupYearsList()
            FilterType.RATING -> setupRatingList()
        }
    }

    private fun setupBackButton() {
        filterListToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        filterListToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupYearsList() {
        val years = ArrayList<Int>()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        years.addAll((currentYear downTo 1950))

        yearsListAdapter = SingleSelectionAdapter(years)
        if (viewModel.year.get() != -1)
            yearsListAdapter.lastClickedPosition = currentYear - viewModel.year.get()!!

        filterList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = yearsListAdapter
        }
    }

    private fun setupGenreList() {
        val genres = ArrayList<String>()
        genres.addAll(GenreHelper.genres.values.toList())

        genreListAdapter = MultipleSelectionAdapter(genres)
        if (viewModel.genres.get() != null) {
            for (genre in viewModel.genres.get()!!)
                genreListAdapter.checkedItems[genre.name] = true
        }
        filterList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = genreListAdapter
        }
    }

    private fun setupRatingList() {
        val ratingList = ArrayList<Int>()
        ratingList.addAll((10 downTo 1))

        ratingListAdapter = SingleSelectionAdapter(ratingList)
        if (viewModel.rating.get() != -1)
            ratingListAdapter.lastClickedPosition = 10 - viewModel.rating.get()!!
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

