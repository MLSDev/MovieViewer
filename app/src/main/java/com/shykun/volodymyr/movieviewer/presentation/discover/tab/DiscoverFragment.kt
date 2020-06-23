package com.shykun.volodymyr.movieviewer.presentation.discover.tab

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.databinding.FragmentDiscoverBinding
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.common.dialog.MultipleSelectionDialog
import com.shykun.volodymyr.movieviewer.presentation.common.dialog.SingleSelectionDialog
import com.shykun.volodymyr.movieviewer.presentation.discover.DiscoverViewModel
import com.shykun.volodymyr.movieviewer.presentation.discover.MOVIE_TYPE
import com.shykun.volodymyr.movieviewer.presentation.discover.TV_TYPE
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_TYPE_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_TYPE_KEY
import com.shykun.volodymyr.movieviewer.presentation.utils.GenreHelper
import kotlinx.android.synthetic.main.fragment_discover.*
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

const val DISCOVER_FRAGMENT_KEY = "discover_fragment_key"

private const val GENRES_SELECTION_DIALOG_TAG = "genres_selection_dialog_tag"
private const val RATING_SELECTION_DIALOG_TAG = "rating_selection_dialog_tag"
private const val YEAR_SELECTION_DIALOG_TAG = "year_selection_dialog_tag"

const val RELEASE_YEAR_KEY = "release_year_key"
const val RATING_KEY = "rating_key"
const val GENRES_KEY = "genres_key"

class DiscoverFragment : Fragment(), BackButtonListener {

    @Inject
    lateinit var router: Router
    private lateinit var viewModel: DiscoverViewModel
    private lateinit var binding: FragmentDiscoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (parentFragment as TabNavigationFragment).component?.inject(this)

        viewModel = ViewModelProviders.of(activity as AppActivity).get(DiscoverViewModel::class.java)
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
        genreField.setOnClickListener { showGenresSelectionDialog() }
        ratingField.setOnClickListener { showRatingSelectionDialog() }
        releaseYearField.setOnClickListener { showYearSelectionDialog() }
        clearFiltersButton.setOnClickListener { viewModel.clearFilters() }
        showResultsButton.setOnClickListener { showResults() }
        movieRadioButton.setOnClickListener { viewModel.type.set(MOVIE_TYPE) }
        tvRadioButton.setOnClickListener { viewModel.type.set(TV_TYPE) }
    }

    private fun showGenresSelectionDialog() {
        val items = GenreHelper.genres.values.toTypedArray()
        val title = "Choose genres"
        val selectedGenres = viewModel.genres.get()!!.map { it.name }
        val selectedItems = BooleanArray(items.size) { position ->
            selectedGenres.contains(items[position])
        }

        val dialog = MultipleSelectionDialog.newInstance(title, items, selectedItems)
        dialog.show(childFragmentManager, GENRES_SELECTION_DIALOG_TAG)

        dialog.multipleSelectionObservable.subscribe {
            viewModel.genres.set(GenreHelper.getGenresByNames(it))
            viewModel.genreNames.set(
                    if (it.isNotEmpty())
                        it.joinToString()
                    else
                        "All genres")
        }
    }

    private fun showRatingSelectionDialog() {
        val title = "Choose min rating"
        val items = Array(10) { (10 - it).toString() }
        val rating = viewModel.rating.get() ?: -1

        val dialog = SingleSelectionDialog.newInstance(title, items, rating)
        dialog.show(childFragmentManager, RATING_SELECTION_DIALOG_TAG)

        dialog.singleSelectionObservable.subscribe {
            viewModel.rating.set(it.toInt())
        }
    }

    private fun showYearSelectionDialog() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val title = "Choose min year"
        val items = Array(currentYear - 1950 + 1) { (currentYear - it).toString() }
        val selectedYear = viewModel.year.get() ?: -1

        val dialog = SingleSelectionDialog.newInstance(title, items, selectedYear)
        dialog.show(childFragmentManager, YEAR_SELECTION_DIALOG_TAG)

        dialog.singleSelectionObservable.subscribe {
            viewModel.year.set(it.toInt())
        }
    }

    private fun showResults() {
        val args = Bundle()
        val rating = viewModel.rating.get() ?: -1
        val year = viewModel.year.get() ?: -1
        val genres = viewModel.genres.get() ?: arrayListOf()

        if (rating != -1)
            args.putInt(RATING_KEY, rating)
        if (year != -1)
            args.putInt(RELEASE_YEAR_KEY, year)
        if (genres.isNotEmpty())
            args.putString(GENRES_KEY, genres.joinToString(separator = ",") { it.id.toString() })

        if (viewModel.type.get() == MOVIE_TYPE) {
            args.putSerializable(MOVIE_TYPE_KEY, MoviesType.DISCOVERED)
            router.navigateTo(MOVIE_LIST_FRAGMENT_KEY, args)
        } else {
            args.putSerializable(TV_TYPE_KEY, TvType.DISCOVERED)
            router.navigateTo(TV_LIST_FRAGMENT_KEY, args)
        }
    }

    override fun onBackClicked(): Boolean {
        return false
    }
}