package com.shykun.volodymyr.movieviewer.presentation.movies.details

import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalActorBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class CastViewHolder(
        private val binding: ItemHorizontalActorBinding,
        private val clickSubject: PublishSubject<Int>) : BaseViewHolder<Actor>(binding) {

    override fun bind(item: Actor?) {
        super.bind(item)

        itemView.setOnClickListener {
            if (item != null) {
                clickSubject.onNext(item.id)
            }
        }
        executeBinding()
    }

    private fun executeBinding() {
        binding.actor = item
        binding.executePendingBindings()
    }
}