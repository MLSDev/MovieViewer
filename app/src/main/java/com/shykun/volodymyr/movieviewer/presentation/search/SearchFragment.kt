package com.shykun.volodymyr.movieviewer.presentation.search

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
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.databinding.FragmentSearchBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.model.ItemType
import com.shykun.volodymyr.movieviewer.presentation.model.SearchListItem
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MOVIE_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_TYPE_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.SEARCH_QUERY_KEY
import com.shykun.volodymyr.movieviewer.presentation.people.details.PERSON_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TV_DETAILS_FRAGMENT_KEY
import kotlinx.android.synthetic.main.fragment_search.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val SEARCH_FRAGMENT_KEY = "search_fragment_key"
private const val ITEM_TYPE_KEY = "item_type_key"

class SearchFragment : Fragment(), BackButtonListener {

    private lateinit var itemType: ItemType
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)

        itemType = arguments?.getSerializable(ITEM_TYPE_KEY) as ItemType
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SearchViewModel::class.java)
        searchAdapter = SearchAdapter()

        subscribeViewModel()
        setupItemClick()

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
        binding.searchHint = when (itemType) {
            ItemType.MOVIE -> getString(R.string.search_movies)
            ItemType.TV -> getString(R.string.search_tv)
            ItemType.PERSON -> getString(R.string.search_people)
        }
    }

    private fun setupAdapter() {
        searchList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
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
                    viewModel.search(newText, itemType)
                else
                    searchAdapter.clearItems()

                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                hideKeyboard()

                val args = Bundle()
                args.putSerializable(MOVIE_TYPE_KEY, MoviesType.SEARCHED)
                args.putString(SEARCH_QUERY_KEY, query)
                router.navigateTo(MOVIE_LIST_FRAGMENT_KEY, args)
                return true
            }
        })
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

    private fun subscribeViewModel() {
        viewModel.searchResultsLiveData.observe(this, Observer { showSearchedMovies(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showLoadingError(it) })
    }

    private fun showSearchedMovies(searchResults: List<SearchListItem>?) {
        if (searchResults != null) {
            searchAdapter.setItems(searchResults)
        }
    }

    private fun setupItemClick() {
        searchAdapter.clickObservable.subscribe {
            hideKeyboard()
            openDetailsScreen(it)
        }
    }

    private fun openDetailsScreen(id: Int) {
        val screenKey = when (itemType) {
            ItemType.MOVIE -> MOVIE_DETAILS_FRAGMENT_KEY
            ItemType.TV -> TV_DETAILS_FRAGMENT_KEY
            ItemType.PERSON -> PERSON_DETAILS_FRAGMENT_KEY
        }

        router.navigateTo(screenKey, id)
    }

    private fun showLoadingError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackClicked(): Boolean {
        hideKeyboard()
        router.exit()

        return true
    }

    companion object {
        fun newInstance(itemType: ItemType): SearchFragment {
            val searchFragment = SearchFragment()
            val args = Bundle()
            args.putSerializable(ITEM_TYPE_KEY, itemType)
            searchFragment.arguments = args

            return searchFragment
        }
    }
}

