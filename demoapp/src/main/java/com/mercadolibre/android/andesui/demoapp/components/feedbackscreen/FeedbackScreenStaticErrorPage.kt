package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.feedback.screen.AndesFeedbackScreenFactory
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton
import com.mercadolibre.android.andesui.feedback.screen.error.AndesFeedbackScreenErrorComponent
import com.mercadolibre.android.andesui.feedback.screen.error.AndesFeedbackScreenErrorData

class FeedbackScreenStaticErrorPage {
    fun create(container: ConstraintLayout) {
        showErrorFeedbackScreenWithThumbnail(container)
    }

    private fun showErrorFeedbackScreenWithThumbnail(container: ConstraintLayout) {
        container.addView(
            AndesFeedbackScreenFactory.createAndesFeedbackScreenError(
                container.context,
                DummyErrorComponent(
                    container.context
                )
            )
        )
    }
}

class DummyErrorComponent(
    private val context: Context
) : AndesFeedbackScreenErrorComponent {

    override fun getErrorCode(): String = "FUN00-1H4441Q"

    override fun onViewCreated(): () -> Unit {
        return {
        }
    }

    override fun getFeedbackScreenErrorData(): AndesFeedbackScreenErrorData =
        AndesFeedbackScreenErrorData(
            "Algo salió mal",
            "Estamos trabajando para solucionarlo",
            null,
            AndesFeedbackScreenButton(
                "Reintentar",
                View.OnClickListener {
                    Toast.makeText(context.applicationContext, "Click in button", Toast.LENGTH_SHORT)
                        .show()
                }
            )
        )
}