package com.mercadolibre.android.andesui.demoapp.utils

import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.ArrayRes
import android.app.Activity
import android.view.inputmethod.InputMethodManager

fun Context.getInDp(value: Float): Int {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, value, this.resources.displayMetrics).toInt()
}

fun Spinner.setupAdapter(@ArrayRes stringArray: Int, onItemSelected: ((Int) -> Unit)? = null) {
    ArrayAdapter
            .createFromResource(
            this.context,
            stringArray,
            android.R.layout.simple_spinner_item
    ).let { adapter ->
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.adapter = adapter
        onItemSelected?.let {
            this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) = Unit

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    onItemSelected.invoke(position)
                }
            }
        }
    }
}

internal fun doWhenGreaterThanApi(apiLevel: Int, actions: () -> Unit) {
    if (Build.VERSION.SDK_INT >= apiLevel) {
        actions()
    }
}

fun <T> MutableList<T>.replaceWith(items: List<T>) {
    with(this) {
        clear()
        addAll(items)
    }
}

fun View.hideKeyboard() {
    (context.getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as? InputMethodManager)?.hideSoftInputFromWindow(windowToken, 0)
}
