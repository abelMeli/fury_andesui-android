package com.mercadolibre.android.andesui.pageIndicator

import android.content.res.Resources

inline val Int.dp: Int
  get() = (this * Resources.getSystem().displayMetrics.density).toInt()

inline val Float.dp: Int
  get() = (this * Resources.getSystem().displayMetrics.density).toInt()