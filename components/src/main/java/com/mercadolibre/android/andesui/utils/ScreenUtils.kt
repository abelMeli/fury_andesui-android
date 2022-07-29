package com.mercadolibre.android.andesui.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.Window

internal object ScreenUtils {

    @Deprecated(
        message = "Avoid this method since the returned value" +
                " changes with different API levels",
        replaceWith = ReplaceWith("ScreenUtils.getScreenHeight(context = context)")
    )
    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    /**
     * Returns the screen height without including status bar.
     *
     * @param context The activity to retrieve the window
     * @param valuesToSubstract Optional values to substract from the screen height
     */
    fun getScreenHeight(context: Context, vararg valuesToSubstract: Int = intArrayOf()): Int {
        if (context !is Activity) return 0
        var totalHeight = context.window.findViewById<View>(Window.ID_ANDROID_CONTENT).height
        valuesToSubstract.forEach {
            totalHeight -= it
        }
        return totalHeight
    }
}
