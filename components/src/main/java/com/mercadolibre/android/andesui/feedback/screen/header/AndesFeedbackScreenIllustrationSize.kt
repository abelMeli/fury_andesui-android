package com.mercadolibre.android.andesui.feedback.screen.header

import androidx.annotation.DimenRes
import com.mercadolibre.android.andesui.R

sealed class AndesFeedbackScreenIllustrationSize(@DimenRes val width: Int,@DimenRes val height: Int) {
    object Size128:
        AndesFeedbackScreenIllustrationSize(R.dimen.andes_feedbackscreen_error_generic_screen_width,
            R.dimen.andes_feedbackscreen_error_screen_height)
}