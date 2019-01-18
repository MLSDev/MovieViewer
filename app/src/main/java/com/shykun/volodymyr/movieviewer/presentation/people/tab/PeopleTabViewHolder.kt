package com.shykun.volodymyr.movieviewer.presentation.people.tab

import android.databinding.ViewDataBinding
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderLoadingBinding
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderPersonBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_holder_person.view.*

open class BasePeopleViewHolder(viewDataBinding: ViewDataBinding)
    : BaseViewHolder<Person>(viewDataBinding)

class PeopleTabLoadingViewHolder(binding: ViewHolderLoadingBinding)
    : BasePeopleViewHolder(binding)

class PeopleTabViewHolder(private val binding: ViewHolderPersonBinding, private val personClickSubject: PublishSubject<Int>)
    : BasePeopleViewHolder(binding) {

    override fun bind(item: Person, position: Int) {
        super.bind(item, position)

        itemView.setOnClickListener { personClickSubject.onNext(item.id) }
        executeBinding(item)
    }

    private fun executeBinding(person: Person) {
        binding.person = person
        binding.executePendingBindings()
    }
}