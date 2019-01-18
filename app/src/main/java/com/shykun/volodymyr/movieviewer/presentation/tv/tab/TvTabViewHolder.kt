package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.view.View
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderHorizontalItemTvBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class TvTabViewHolder(
        private val binding: ViewHolderHorizontalItemTvBinding,
        private val tvClickSubject: PublishSubject<Int>) : BaseViewHolder<Tv>(binding) {

    var type = -1
    lateinit var footerText: String
    var footerIconVisibility = View.VISIBLE


    override fun bind(item: Tv, position: Int) {
        super.bind(item, position)

        if (type == POPULAR_TV)
            footerIconVisibility = View.GONE

        footerText = when(type) {
            POPULAR_TV -> "#${position+1}"
            TOP_RATED_TV -> item.voteAverage.toString()
            TV_ON_THE_AIR -> item.voteAverage.toString()
            else -> ""

        }

        itemView.setOnClickListener { tvClickSubject.onNext(item.id) }

        executeBinding(item)
    }

    private fun executeBinding(tv: Tv) {
        binding.tv = tv
        binding.tvViewHolder = this
        binding.executePendingBindings()
    }
}