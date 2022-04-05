package com.mercadolibre.android.andesui.amountfield.accessibility

import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldState

internal class AndesAmountFieldSimpleMoneyA11yEventDispatcher {

    fun notifyNewStateToA11y(view: View, state: AndesAmountFieldState, helperText: CharSequence?) {
        val context = view.context
        val errorText = context.resources.getString(R.string.andes_amount_field_error_text)
        val stateAnnouncement = "$errorText, $helperText"
        val fieldIsFullAnnouncement = context.resources.getString(R.string.andes_amount_field_field_is_full_announcement)

        if (state == AndesAmountFieldState.AmountExceeded) {
            view.announceForAccessibility(fieldIsFullAnnouncement)
        }

        if (state != AndesAmountFieldState.Idle) {
            view.announceForAccessibility(stateAnnouncement)
        }
    }
}
