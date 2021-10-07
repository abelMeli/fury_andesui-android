package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.feedback.screen.AndesFeedbackScreenView
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenActions
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenHeader
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenTextDescription
import com.mercadolibre.android.andesui.feedback.screen.color.AndesFeedbackScreenColor
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType

class FeedbackScreenStaticSimpleOrangePage {

    private lateinit var andesFeedbackScreen: AndesFeedbackScreenView
    private lateinit var feedbackHeader: AndesFeedbackScreenHeader
    private lateinit var feedbackActions: AndesFeedbackScreenActions

    fun create(context: Context, container: ConstraintLayout) {
        createHeader(context)
        createActions(context)
        createFeedbackScreen(context)
        addFeedbackScreen(container)
    }

    private fun createActions(context: Context) {
        val feedbackButtonBody = AndesFeedbackScreenButton("Entendido",
            View.OnClickListener {
                Toast.makeText(context, "Click in button", Toast.LENGTH_SHORT).show()
            })

        val feedbackCloseButton = View.OnClickListener {
            Toast.makeText(context, "Click in close", Toast.LENGTH_SHORT).show()
        }

        feedbackActions = AndesFeedbackScreenActions(
            button = feedbackButtonBody,
            closeCallback = feedbackCloseButton
        )
    }

    private fun createFeedbackScreen(context: Context) {
        andesFeedbackScreen = AndesFeedbackScreenView(
            context = context,
            type = AndesFeedbackScreenType.Simple(AndesFeedbackScreenColor.ORANGE),
            header = feedbackHeader,
            body = null,
            actions = feedbackActions
        )
    }

    private fun createHeader(context: Context) {
        val feedbackHeaderBody = AndesFeedbackScreenText(
            title = "Revisa el saldo de tu tarjeta",
            description = AndesFeedbackScreenTextDescription(
                "No pudimos procesar tu pago."
            ),
            highlight = "Mastercard Santander **** 3434"
        )

        val feedbackHeaderThumbnail = AndesFeedbackScreenAsset.Thumbnail(
            image = ContextCompat.getDrawable(
                context,
                R.drawable.andes_pagar_y_cobrar_tarjeta_de_credito_24
            )!!,
            badgeType = AndesThumbnailBadgeType.Icon
        )

        feedbackHeader = AndesFeedbackScreenHeader(
            feedbackText = feedbackHeaderBody,
            feedbackImage = feedbackHeaderThumbnail
        )
    }

    private fun addFeedbackScreen(container: ConstraintLayout) {
        container.addView(andesFeedbackScreen)
    }
}
