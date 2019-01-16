package com.shykun.volodymyr.movieviewer.presentation.tv.details


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shykun.volodymyr.movieviewer.R

const val TV_DETAILS_FRAGMENT = "tv_details_fragment"

class TvDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv_details, container, false)
    }
}
