package com.shykun.volodymyr.movieviewer.presentation.tv.list

import android.databinding.ViewDataBinding
import android.view.View
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.databinding.ItemLoadingBinding
import com.shykun.volodymyr.movieviewer.databinding.ItemTvBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject

open class BaseTvListViewHolder(viewDataBinding: ViewDataBinding)
    : BaseViewHolder<Tv>(viewDataBinding)

class TvListLoadingViewHolder(binding: ItemLoadingBinding)
    : BaseTvListViewHolder(binding)

class TvListViewHolder(
        private val binding: ItemTvBinding,
        private val tvType: TvType,
        private val tvClickSubject: PublishSubject<Int>)
    : BaseTvListViewHolder(binding) {

    lateinit var popularity: String
    var popularityVisibility = View.VISIBLE
    var firstAirDateVisibility = View.VISIBLE

    override fun bind(item: Tv, position: Int) {
        super.bind(item, position)

        when (tvType) {
            TvType.TOP_RATED -> {
                popularityVisibility = View.GONE
                firstAirDateVisibility = View.GONE
            }
            TvType.POPULAR -> {
                popularity = "#${position + 1}"
                firstAirDateVisibility = View.GONE
            }
            TvType.ON_THE_AIR -> {
                popularityVisibility = View.GONE
            }
        }

        itemView.setOnClickListener { tvClickSubject.onNext(item.id) }

        executeBinding(item)
    }

    private fun executeBinding(tv: Tv) {
        binding.tv = tv
        binding.executePendingBindings()
    }
}