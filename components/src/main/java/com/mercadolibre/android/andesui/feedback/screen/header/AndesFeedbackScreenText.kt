package com.mercadolibre.android.andesui.feedback.screen.header

import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks

data class AndesFeedbackScreenText(
    val title: String,
    val description: AndesFeedbackScreenTextDescription? = null,
    val highlight: String? = null
) {
    val overline: String?
        get() = internalOverline
    private var internalOverline: String? = null

    constructor(title: String, overline: String) : this(title) {
        internalOverline = overline
    }
}

data class AndesFeedbackScreenTextDescription(
    val text: String,
    val links: AndesBodyLinks? = null
)
