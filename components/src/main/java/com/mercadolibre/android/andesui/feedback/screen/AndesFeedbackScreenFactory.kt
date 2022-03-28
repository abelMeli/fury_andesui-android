package com.mercadolibre.android.andesui.feedback.screen

import android.content.Context
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenActions
import com.mercadolibre.android.andesui.feedback.screen.error.AndesFeedbackScreenErrorComponent
import com.mercadolibre.android.andesui.feedback.screen.header.*
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType

/**
 * Class used to simplify developer experience creating [AndesFeedbackScreenView]
 */
object AndesFeedbackScreenFactory {
    /**
     * Creates a Feedback Screen View of type Error.
     *
     * @param context Context
     * @param errorComponent It's recommended to use a single [AndesFeedbackScreenErrorComponent] in your whole app
     * to unify the user experience.
     */
    fun createAndesFeedbackScreenError(context: Context, errorComponent: AndesFeedbackScreenErrorComponent):  AndesFeedbackScreenView{
        val feedbackErrorData = errorComponent.getFeedbackScreenErrorData()
        return AndesFeedbackScreenView(
            context,
            type = AndesFeedbackScreenType.Error(errorComponent.getErrorCode()),
            header = AndesFeedbackScreenHeader(
                feedbackText = AndesFeedbackScreenText(
                    title = feedbackErrorData.title,
                    description = feedbackErrorData.description?.let { description ->
                        AndesFeedbackScreenTextDescription(description)
                    }
                ),
                feedbackImage = feedbackErrorData.asset?.let { drawable ->
                    AndesFeedbackScreenAsset.Illustration(
                        image = drawable,
                        size = AndesFeedbackScreenIllustrationSize.Size128
                    )
                }
            ),
            actions = feedbackErrorData.button?.let { AndesFeedbackScreenActions(it) },
            onViewCreated = errorComponent.onViewCreated()
        )
    }
}