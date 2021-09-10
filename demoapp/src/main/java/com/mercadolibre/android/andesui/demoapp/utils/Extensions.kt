package com.mercadolibre.android.andesui.demoapp.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.ArrayRes

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
