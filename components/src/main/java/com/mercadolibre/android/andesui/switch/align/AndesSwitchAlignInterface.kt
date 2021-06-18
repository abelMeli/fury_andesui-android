package com.mercadolibre.android.andesui.switch.align

import android.view.View

internal interface AndesSwitchAlignInterface {
    fun leftTextVisibility(): Int
    fun rightTextVisibility(): Int
}

internal object AndesSwitchLeft : AndesSwitchAlignInterface {
    override fun leftTextVisibility(): Int {
        return View.GONE
    }

    override fun rightTextVisibility(): Int {
        return View.VISIBLE
    }
}

internal object AndesSwitchRight : AndesSwitchAlignInterface {
    override fun leftTextVisibility(): Int {
        return View.VISIBLE
    }

    override fun rightTextVisibility(): Int {
        return View.GONE
    }
}
