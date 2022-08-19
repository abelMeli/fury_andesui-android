package com.mercadolibre.android.andesui.feedback.screen.header

import androidx.annotation.DimenRes
import com.mercadolibre.android.andesui.R

sealed class AndesFeedbackScreenIllustrationSize(@DimenRes val width: Int,@DimenRes val height: Int) {
    /**
     * this object resize the illustration to 320dp width and 80dp height.
     */
    object Size80 :
        AndesFeedbackScreenIllustrationSize(
            R.dimen.andes_feedbackscreen_error_generic_screen_width,
            R.dimen.andes_feedbackscreen_error_screen_height_80
        )
    /**
     * this object resize the illustration to 320dp width and 128dp height.
     */
    object Size128 :
        AndesFeedbackScreenIllustrationSize(
            R.dimen.andes_feedbackscreen_error_generic_screen_width,
            R.dimen.andes_feedbackscreen_error_screen_height
        )
    /**
     * this object resize the illustration to 320dp width and 160dp height.
     */
    object Size160 :
        AndesFeedbackScreenIllustrationSize(
            R.dimen.andes_feedbackscreen_error_generic_screen_width,
            R.dimen.andes_feedbackscreen_error_screen_height_160
        )
}