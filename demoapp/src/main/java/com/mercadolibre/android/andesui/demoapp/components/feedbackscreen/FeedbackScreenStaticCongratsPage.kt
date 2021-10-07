package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.feedback.screen.AndesFeedbackScreenView
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenActions
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenHeader
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenTextDescription
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType
import com.mercadolibre.android.andesui.message.AndesMessage
import com.mercadolibre.android.andesui.message.hierarchy.AndesMessageHierarchy
import com.mercadolibre.android.andesui.message.type.AndesMessageType
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType

class FeedbackScreenStaticCongratsPage {

    private lateinit var andesFeedbackScreen: AndesFeedbackScreenView
    private lateinit var feedbackHeader: AndesFeedbackScreenHeader
    private lateinit var feedbackActions: AndesFeedbackScreenActions
    private lateinit var feedbackBody: View

    fun create(context: Context, container: ConstraintLayout) {
        createHeader(context)
        createActions(context)
        createBody(context)
        createFeedbackScreen(context)
        addFeedbackScreen(container)
    }

    private fun createBody(context: Context) {
        feedbackBody = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL

            val messageDatos = AndesMessage(
                context = context,
                title = "Datos del vendedor",
                body = "Enviamos toda la info de contacto a tu mail",
                hierarchy = AndesMessageHierarchy.QUIET,
                type = AndesMessageType.NEUTRAL
            )

            addView(messageDatos)
        }
    }

    private fun createActions(context: Context) {
        val bottomButton = AndesFeedbackScreenButton(
            "Repetir compra",
            View.OnClickListener {
                Toast.makeText(context, "Click en repetir compra", Toast.LENGTH_SHORT).show()
            }
        )
        val feedbackCloseButton = View.OnClickListener {
            Toast.makeText(context, "Click in close", Toast.LENGTH_SHORT).show()
        }

        feedbackActions = AndesFeedbackScreenActions(
            button = bottomButton,
            closeCallback = feedbackCloseButton
        )
    }

    private fun createFeedbackScreen(context: Context) {
        andesFeedbackScreen = AndesFeedbackScreenView(
            context = context,
            type = AndesFeedbackScreenType.Congrats,
            header = feedbackHeader,
            body = feedbackBody,
            actions = feedbackActions
        )
    }

    private fun createHeader(context: Context) {
        val feedbackHeaderBody = AndesFeedbackScreenText(
            title = "Compraste Borcegos Camel",
            description = AndesFeedbackScreenTextDescription(
                "El envío está programado para mañana jueves 23 de junio."
            ),
            highlight = "Visa Santander **** 4545"
        )

        val feedbackHeaderThumbnail = AndesFeedbackScreenAsset.Thumbnail(
            image = ResourcesCompat.getDrawable(context.resources, R.drawable.botas_image, null)!!,
            badgeType = AndesThumbnailBadgeType.ImageCircle
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
