package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.view.View
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalTvBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class TvTabViewHolder(
        private val binding: ItemHorizontalTvBinding,
        private val clickSubject: PublishSubject<Int>,
        private val tvType: TvType) : BaseViewHolder<Tv>(binding) {

    lateinit var footerText: String
    var footerIconVisibility = View.VISIBLE


    override fun bind(item: Tv?) {
        super.bind(item)

        if (tvType == TvType.POPULAR)
            footerIconVisibility = View.GONE

        footerText = when (tvType) {
            TvType.POPULAR -> "#${adapterPosition + 1}"
            TvType.TOP_RATED -> item?.voteAverage.toString()
            TvType.ON_THE_AIR -> item?.voteAverage.toString()
            else -> ""
        }

        itemView.setOnClickListener {
            if (item != null) {
                clickSubject.onNext(item.id)
            }
        }

        executeBinding()
    }

    private fun executeBinding() {
        binding.tv = item
        binding.tvViewHolder = this
        binding.executePendingBindings()
    }
}