package com.shykun.volodymyr.movieviewer.presentation.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.AppCompatRatingBar
import android.view.LayoutInflater
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.R

class RateDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(this.context).inflate(R.layout.fragment_dialog_rate, null)
        val builder = AlertDialog.Builder(activity)

        val ratingBar = view.findViewById<AppCompatRatingBar>(R.id.ratingBar)
        val ratingText = view.findViewById<TextView>(R.id.ratingTextView)

        ratingText.text = ratingBar.rating.toString()

        ratingBar.setOnTouchListener { v, event ->
            val rating = ratingBar.rating
            if (rating >= 0.5f)
                ratingText.text = ratingBar.rating.toString()

            ratingBar.onTouchEvent(event)

        }

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating < 0.5f)
                ratingBar.rating = 0.5f
        }

        builder.setView(view)
                .setPositiveButton("OK") { dialog, which ->
                    (parentFragment as RateListener).onRateClickListener(ratingBar.rating)
                }
                .setNegativeButton("Cancel") { dialog, which -> dismiss() }
        val dialog = builder.create()

        dialog.window.setBackgroundDrawableResource(R.drawable.dialog_backgrond)

        return dialog
    }
}

interface RateListener {
    fun onRateClickListener(rating: Float)
}