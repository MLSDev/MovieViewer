package com.shykun.volodymyr.movieviewer.presentation.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.reactivex.Observable


object ScrollObservable {
    fun from(rv: RecyclerView, limit: Int): Observable<Int> {
        return Observable.create { subscriber ->
            val sl = object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!subscriber.isDisposed) {
                        val position = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        val updatePosition = rv.adapter!!.itemCount - 1 - limit / 2
                        if (position >= updatePosition) {
                            subscriber.onNext(rv.adapter!!.itemCount)
                        }
                    }
                }
            }
            rv.addOnScrollListener(sl)
        }
    }
}