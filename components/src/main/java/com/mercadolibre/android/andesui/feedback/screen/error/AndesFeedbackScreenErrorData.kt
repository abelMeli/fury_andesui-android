package com.mercadolibre.android.andesui.feedback.screen.error

import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton

/**
 * Feedback Screen Error data used to build the view.
 */
data class AndesFeedbackScreenErrorData(
    val title: String,
    val description: String?,
    val asset: Drawable?,
    val button: AndesFeedbackScreenButton?
)
