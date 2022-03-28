package com.mercadolibre.android.andesui.feedback.screen.type

import com.mercadolibre.android.andesui.feedback.screen.color.AndesFeedbackScreenColor

sealed class AndesFeedbackScreenType(internal val type: AndesFeedbackScreenTypeInterface) {

    object Congrats : AndesFeedbackScreenType(AndesCongratsFeedbackScreenType)

    data class Simple(val color: AndesFeedbackScreenColor) : AndesFeedbackScreenType(AndesSimpleFeedbackScreenType(color.color))

    internal data class Error(val errorCode: String?): AndesFeedbackScreenType(AndesErrorFeedbackScreenType(errorCode))
}
