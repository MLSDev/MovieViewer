package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.AppCompatRatingBar
import android.view.LayoutInflater
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import javax.inject.Inject

private const val MOVIE_ID_KEY = "movie_id_key"
private const val SESSION_ID_KEY = "session_id_key"

class MovieRateDialogFragment : DialogFragment() {

    private var movieId = -1
    private lateinit var sessionId: String
    private lateinit var viewModel: MovieDetailsViewModel
    @Inject
    lateinit var viewModelFactory: MovieDetailsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            movieId = it.getInt(MOVIE_ID_KEY)
            sessionId = it.getString(SESSION_ID_KEY)
        }

        (parentFragment?.parentFragment as TabNavigationFragment).component?.inject(this)
        viewModel = ViewModelProviders.of(parentFragment!!, viewModelFactory)
                .get(MovieDetailsViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(this.context).inflate(R.layout.fragment_dialog_rate, null)
        val builder = AlertDialog.Builder(activity)

        val ratingBar = view.findViewById<AppCompatRatingBar>(R.id.ratingBar)
        val ratingText = view.findViewById<TextView>(R.id.ratingTextView)

        ratingText.text = ratingBar.rating.toString()

        ratingBar.setOnTouchListener { v, event ->
            val rating = ratingBar.rating
            ratingText.text = rating.toString()

            ratingBar.onTouchEvent(event)

        }

        builder.setView(view)
                .setPositiveButton("OK") { dialog, which ->
                    viewModel.rateMovie(movieId, ratingBar.rating, sessionId)
                }
                .setNegativeButton("Cancel") { dialog, which -> dismiss() }
        val dialog = builder.create()

        dialog.window.setBackgroundDrawableResource(R.drawable.dialog_backgrond)

        return dialog
    }

    companion object {
        fun newInstance(movieId: Int, sessionId: String): MovieRateDialogFragment {
            val dialog = MovieRateDialogFragment()
            val args = Bundle()
            args.putInt(MOVIE_ID_KEY, movieId)
            args.putString(SESSION_ID_KEY, sessionId)
            dialog.arguments = args

            return dialog
        }
    }
}