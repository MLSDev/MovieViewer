package com.shykun.volodymyr.movieviewer.presentation.movies.details

import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderHorizontalItemActorBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder

class CastViewHolder(private val binding: ViewHolderHorizontalItemActorBinding) : BaseViewHolder<Actor>(binding) {

    override fun bind(item: Actor, position: Int) {
        super.bind(item, position)

        executeBinding(item)
    }

    private fun executeBinding(actor: Actor) {
        binding.actor = actor
        binding.executePendingBindings()
    }
}