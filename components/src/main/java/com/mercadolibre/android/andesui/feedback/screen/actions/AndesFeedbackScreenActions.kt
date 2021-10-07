package com.mercadolibre.android.andesui.feedback.screen.actions

import android.view.View

data class AndesFeedbackScreenActions(
    val button: AndesFeedbackScreenButton? = null,
    val closeCallback: View.OnClickListener? = null
)

data class AndesFeedbackScreenButton(
    val text: String,
    val onClick: View.OnClickListener
)
