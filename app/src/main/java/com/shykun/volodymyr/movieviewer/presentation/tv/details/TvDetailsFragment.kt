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
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.Video
import com.shykun.volodymyr.movieviewer.data.network.YOUTUBE_API_KEY
import com.shykun.volodymyr.movieviewer.data.network.response.TvDetailsResponse
import com.shykun.volodymyr.movieviewer.databinding.FragmentTvDetailsBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BackButtonListener
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.details.CastAdapter
import com.shykun.volodymyr.movieviewer.presentation.movies.details.ReviewAdapter
import com.shykun.volodymyr.movieviewer.presentation.people.details.PERSON_DETAILS_FRAGMENT_KEY
import kotlinx.android.synthetic.main.fragment_tv_details.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

const val TV_DETAILS_FRAGMENT_KEY = "tv_details_fragment"
private const val TV_ID_KEY = "tv_id_key"

class TvDetailsFragment : Fragment(), BackButtonListener {

    private var tvId = -1
    private var viewWasLoaded = false
    private var binding: FragmentTvDetailsBinding? = null
    private lateinit var viewModel: TvDetailsViewModel
    private lateinit var castAdapter: CastAdapter
    private lateinit var reviewsAdapter: ReviewAdapter
    private lateinit var recommendedTvAdapter: RecommendedTvAdapter

    @Inject
    lateinit var viewModelFactory: TvDetailsViewModelFactory
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as TabNavigationFragment).component?.inject(this)

        tvId = arguments?.getInt(TV_ID_KEY)!!
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(TvDetailsViewModel::class.java)

        castAdapter = CastAdapter(ArrayList())
        recommendedTvAdapter = RecommendedTvAdapter(ArrayList())
        reviewsAdapter = ReviewAdapter(ArrayList())

        subscribeViewModel()
        setupRecommendedTvClick()
        setupActorClick()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv_details, container, false)
            binding!!.viewModel = viewModel
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBackButton()
        setupCastAdapter()
        setupRecommendedTvAdapter()
        setupReviewsAdapter()

        if (!viewWasLoaded) {
            viewModel.onViewLoaded(tvId)
            viewWasLoaded = true
        }
    }

    private fun initTrailer(trailer: Video) {
        val youtubePlayerFragment = YouTubePlayerSupportFragment()
        youtubePlayerFragment.initialize(YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, player: YouTubePlayer, p2: Boolean) {
                if (!p2)
                    player.cueVideo(trailer.key)
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.tvTrailerContainer, youtubePlayerFragment)
        fragmentTransaction.commit()
    }

    private fun setupBackButton() {
        tvDetailsToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        tvDetailsToolbar.setNavigationOnClickListener { onBackClicked() }
    }

    private fun subscribeViewModel() {
        viewModel.tvDetailsLiveData.observe(this, Observer { showTvDetails(it) })
        viewModel.tvCastLiveData.observe(this, Observer { showTvCast(it) })
        viewModel.tvReviewsLiveData.observe(this, Observer { showReviews(it) })
        viewModel.recommendedTvLiveData.observe(this, Observer { showRecommendedTv(it) })
        viewModel.loadingErrorLiveData.observe(this, Observer { showLoadingError(it) })
    }

    private fun showTvDetails(tvDetailsResponse: TvDetailsResponse?) {
        binding?.tvDetails = tvDetailsResponse
        if (tvDetailsResponse != null) {
            val trailers = tvDetailsResponse.videos.results.filter { it.type == "Trailer" }
            if (trailers.isNotEmpty())
                initTrailer(trailers.first())
            else
                viewModel.trailerTitleVisibility.set(View.GONE)
        }
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
        tvCast.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }
    }

    private fun setupRecommendedTvAdapter() {
        recommendedTv.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendedTvAdapter
        }
    }

    private fun setupReviewsAdapter() {
        tvReviews.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
            adapter = reviewsAdapter
        }
    }

    private fun setupRecommendedTvClick() {
        recommendedTvAdapter.clickObservable.subscribe { router.navigateTo(TV_DETAILS_FRAGMENT_KEY, it) }
    }

    private fun setupActorClick() {
        castAdapter.clickObservable.subscribe { router.navigateTo(PERSON_DETAILS_FRAGMENT_KEY, it) }
    }

    override fun onBackClicked(): Boolean {
        router.exit()

        return true
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
