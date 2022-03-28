package com.mercadolibre.android.andesui.feedback.screen.error

import com.mercadolibre.android.andesui.errorcomponent.AndesErrorComponent

/**
 * Interface used by feedback screen to show errors with business context.
 * We strongly recommend to use a unique implementation for your app
 * in order to show all your errors with a standard ux.
 */
interface AndesFeedbackScreenErrorComponent: AndesErrorComponent {
    /**
     * Return data to build the feedback screen.
     */
    fun getFeedbackScreenErrorData(): AndesFeedbackScreenErrorData
}