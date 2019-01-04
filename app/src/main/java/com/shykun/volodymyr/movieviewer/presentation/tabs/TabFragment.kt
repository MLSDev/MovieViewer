package com.shykun.volodymyr.movieviewer.presentation.tabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shykun.volodymyr.movieviewer.R
import kotlinx.android.synthetic.main.fragment_tab.*

class TabFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentViewPager.adapter = ContentViewPagerAdapter(childFragmentManager)
        contentTabLayout.setupWithViewPager(contentViewPager)

    }

}
