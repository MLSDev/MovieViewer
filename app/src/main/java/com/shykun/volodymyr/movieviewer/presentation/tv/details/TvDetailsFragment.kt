package com.shykun.volodymyr.movieviewer.presentation.tv.details


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.network.response.TvDetailsResponse
import com.shykun.volodymyr.movieviewer.databinding.FragmentTvDetailsBinding
import com.shykun.volodymyr.movieviewer.presentation.base.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.details.CastAdapter
import com.shykun.volodymyr.movieviewer.presentation.movies.details.ReviewAdapter
import kotlinx.android.synthetic.main.fragment_tv_details.*

const val TV_DETAILS_FRAGMENT = "tv_details_fragment"
private const val TV_ID_KEY = "tv_id_key"

class TvDetailsFragment : Fragment() {

    private var tvId = -1
    private lateinit var binding: FragmentTvDetailsBinding
    private lateinit var viewModel: TvDetailsViewModel
    private lateinit var castAdapter: CastAdapter
    private lateinit var reviewsAdapter: ReviewAdapter
    private lateinit var recommendedTvAdapter: RecommendedTvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvId = arguments?.getInt(TV_ID_KEY)!!
        viewModel = ViewModelProviders
                .of(this, (parentFragment as TabNavigationFragment).component?.getTvDetailsViewModelFactory())
                .get(TvDetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBackButton()
        subscribeViewModel()
        setupCastAdapter()
        setupReviewsAdapter()
        setupRecommendedTvAdapter()
        viewModel.onViewLoaded(tvId)
    }

    private fun setupBackButton() {
        tvDetailsToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        tvDetailsToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun subscribeViewModel() {
        viewModel.tvDetailsLiveData.observe(this, Observer { showTvDetails(it) })
        viewModel.tvCastLiveData.observe(this, Observer { showTvCast(it) })
        viewModel.tvReviewsLiveData.observe(this, Observer { showReviews(it) })
        viewModel.recommendedTvLiveData.observe(this, Observer { showRecommendedTv(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showLoadingError(it) })
    }

    private fun showTvDetails(tvDetailsResponse: TvDetailsResponse?) {
        binding.tvDetails = tvDetailsResponse
    }

    private fun showTvCast(tvCast: List<Actor>?) {
        if (tvCast != null) {
            castAdapter.addCast(tvCast)
        }
    }

    private fun showReviews(reviews: List<Review>?) {
        if (reviews != null) {
            reviewsAdapter.addReviews(reviews)
        }
    }

    private fun showRecommendedTv(tvList: List<Tv>?) {
        if (tvList != null) {
            recommendedTvAdapter.addTvList(tvList)
        }
    }

    private fun showLoadingError(message: String?) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupCastAdapter() {
        castAdapter = CastAdapter(ArrayList())
        tvCast.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }
    }

    private fun setupReviewsAdapter() {
        reviewsAdapter = ReviewAdapter(ArrayList())
        tvReviews.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
            adapter = reviewsAdapter
        }
    }

    private fun setupRecommendedTvAdapter() {
        recommendedTvAdapter = RecommendedTvAdapter(ArrayList())
        recommendedTv.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendedTvAdapter
        }
    }

    companion object {
        fun newInstance(tvId: Int): TvDetailsFragment {
            val tvDetailsFragment = TvDetailsFragment()
            val args = Bundle()
            args.putInt(TV_ID_KEY, tvId)
            tvDetailsFragment.arguments = args

            return tvDetailsFragment
        }
    }
}
