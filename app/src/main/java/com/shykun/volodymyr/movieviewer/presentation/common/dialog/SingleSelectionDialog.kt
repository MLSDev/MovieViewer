package com.shykun.volodymyr.movieviewer.presentation.common.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.shykun.volodymyr.movieviewer.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

private const val TITLE_KEY = "title_key"
private const val ITEMS_KEY = "items_key"
private const val CHECKED_ITEM_KEY = "checked_item_key"

class SingleSelectionDialog : DialogFragment() {

    private lateinit var title: String
    private lateinit var items: Array<String>
    private var checkedItem = -1

    private val singleSelectionSubject = PublishSubject.create<String>()
    val singleSelectionObservable: Observable<String> = singleSelectionSubject

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        arguments?.apply {
            items = getStringArray(ITEMS_KEY)!!
            title = getString(TITLE_KEY)!!
            checkedItem = getInt(CHECKED_ITEM_KEY)
        }

        val builder = AlertDialog.Builder(activity!!, R.style.DialogTheme)
                .setTitle(title)
                .setSingleChoiceItems(items, checkedItem, { _, position ->
                    checkedItem = position
                })
                .setPositiveButton("OK") { _, _ -> setResult() }
                .setNegativeButton("Cancel") { _, _ -> dismiss() }

        return builder.create()
    }

    private fun setResult() {
        singleSelectionSubject.onNext(items[checkedItem])
    }

    companion object {
        fun newInstance(title: String, items: Array<String>, checkedItem: Int = -1): SingleSelectionDialog {
            val dialog = SingleSelectionDialog()
            val args = Bundle()

            args.putString(TITLE_KEY, title)
            args.putStringArray(ITEMS_KEY, items)
            args.putInt(CHECKED_ITEM_KEY, checkedItem)

            dialog.arguments = args
            return dialog
        }
    }
}