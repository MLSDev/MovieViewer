package com.shykun.volodymyr.movieviewer.presentation.tv.details

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

private const val TV_ID_KEY = "tv_id_key"
private const val SESSION_ID_KEY = "session_id_key"

class TvRateDialogFragment : DialogFragment() {

    private var tvId = -1
    private lateinit var sessionId: String
    private lateinit var viewModel: TvDetailsViewModel
    @Inject
    lateinit var viewModelFactory: TvDetailsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            tvId = it.getInt(TV_ID_KEY)
            sessionId = it.getString(SESSION_ID_KEY)
        }

        (parentFragment?.parentFragment as TabNavigationFragment).component?.inject(this)
        viewModel = ViewModelProviders.of(parentFragment!!, viewModelFactory)
                .get(TvDetailsViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(this.context).inflate(R.layout.fragment_dialog_rate, null)
        val builder = AlertDialog.Builder(activity)

        val ratingBar = view.findViewById<AppCompatRatingBar>(R.id.ratingBar)
        val ratingText = view.findViewById<TextView>(R.id.ratingTextView)

        ratingText.text = ratingBar.rating.toString()

        ratingBar.setOnTouchListener { v, event ->
            val rating = ratingBar.rating
            if (rating >= 0.5f)
                ratingText.text = rating.toString()

            ratingBar.onTouchEvent(event)
        }

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating < 0.5f)
                ratingBar.rating = 0.5f
        }


        builder.setView(view)
                .setPositiveButton("OK") { dialog, which ->
                    viewModel.rateTv(tvId, ratingBar.rating, sessionId)
                }
                .setNegativeButton("Cancel") { dialog, which -> dismiss() }
        val dialog = builder.create()

        dialog.window.setBackgroundDrawableResource(R.drawable.dialog_backgrond)

        return dialog
    }

    companion object {
        fun newInstance(tvId: Int, sessionId: String): TvRateDialogFragment {
            val dialog = TvRateDialogFragment()
            val args = Bundle()
            args.putInt(TV_ID_KEY, tvId)
            args.putString(SESSION_ID_KEY, sessionId)
            dialog.arguments = args

            return dialog
        }
    }
}