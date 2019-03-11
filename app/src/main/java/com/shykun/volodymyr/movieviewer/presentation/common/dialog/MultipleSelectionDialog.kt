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
private const val SELECTED_ITEMS = "checked_items"

class MultipleSelectionDialog : DialogFragment() {

    private lateinit var title: String
    private lateinit var items: Array<String>
    private lateinit var selectedItems: BooleanArray

    private val multipleSelectionSubject = PublishSubject.create<ArrayList<String>>()
    val multipleSelectionObservable: Observable<ArrayList<String>> = multipleSelectionSubject

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        arguments?.apply {
            items = getStringArray(ITEMS_KEY)!!
            title = getString(TITLE_KEY)!!
            selectedItems = getBooleanArray(SELECTED_ITEMS) ?: BooleanArray(items.size) { false }
        }

        val builder = AlertDialog.Builder(activity!!, R.style.DialogTheme)
                .setTitle(title)
                .setMultiChoiceItems(items, selectedItems, { _, position: Int, isChecked: Boolean ->
                    selectedItems[position] = isChecked
                })
                .setPositiveButton("OK") { dialog, _ -> setResults() }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

        return builder.create()
    }

    private fun setResults() {
        val results = ArrayList<String>()
        for ((position, isChecked) in selectedItems.withIndex()) {
            if (isChecked)
                results.add(items[position])
        }
        multipleSelectionSubject.onNext(results)
    }

    companion object {
        fun newInstance(title: String, items: Array<String>, selectedItems: BooleanArray? = null): MultipleSelectionDialog {
            val dialog = MultipleSelectionDialog()
            val args = Bundle()

            args.putString(TITLE_KEY, title)
            args.putStringArray(ITEMS_KEY, items)
            args.putBooleanArray(SELECTED_ITEMS, selectedItems)
            dialog.arguments = args

            return dialog
        }
    }
}
