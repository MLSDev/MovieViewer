package com.shykun.volodymyr.movieviewer.presentation.utils

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

fun <T> Single<T>.ioMainSubscribe(onSuccess: (T) -> Unit, onError: (error: Throwable) -> Unit) {
    this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
}