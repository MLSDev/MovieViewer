package com.shykun.volodymyr.movieviewer.presentation.tabs

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NonSwipeableViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {
    override fun onInterceptTouchEvent(ev: MotionEvent?) = false
    override fun onTouchEvent(ev: MotionEvent?) = false
}