package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.feedback.screen.AndesFeedbackScreenView
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenActions
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenHeader
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.color.AndesFeedbackScreenColor
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType
import com.mercadolibre.android.andesui.message.AndesMessage
import com.mercadolibre.android.andesui.message.hierarchy.AndesMessageHierarchy
import com.mercadolibre.android.andesui.message.type.AndesMessageType

class FeedbackScreenStaticSimpleRedBodyPage {

    private lateinit var andesFeedbackScreen: AndesFeedbackScreenView
    private lateinit var feedbackHeader: AndesFeedbackScreenHeader
    private lateinit var feedbackActions: AndesFeedbackScreenActions
    private lateinit var feedbackBody: View

    fun create(context: Context, container: ConstraintLayout) {
        createHeader()
        createActions(context)
        createBody(context)
        createFeedbackScreen(context)
        addFeedbackScreen(container)
    }

    private fun createBody(context: Context) {
        feedbackBody = LinearLayout(context).apply {
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setPadding(
                    -resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_content_horizontal_margin),
                    0,
                    -resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_content_horizontal_margin),
                    0
                )
            }.also {
                layoutParams = it
            }

            orientation = LinearLayout.VERTICAL

            val messageDireccion = AndesMessage(
                context = context,
                title = "Dirección incorrecta",
                body = "Ingresá en tus datos y revisá tu dirección",
                hierarchy = AndesMessageHierarchy.QUIET,
                type = AndesMessageType.ERROR
            )

            val messageMail = AndesMessage(
                context = context,
                title = "Email incorrecto",
                body = "Ingresá en tus datos y revisá tu email",
                hierarchy = AndesMessageHierarchy.QUIET,
                type = AndesMessageType.ERROR
            )

            val messageDni = AndesMessage(
                context = context,
                title = "DNI incorrecto",
                body = "Ingresá en tus datos y revisá tu número de DNI",
                hierarchy = AndesMessageHierarchy.QUIET,
                type = AndesMessageType.ERROR
            )

            addView(messageDireccion)
            addView(buildEmptyView(context))
            addView(messageMail)
            addView(buildEmptyView(context))
            addView(messageDni)
        }
    }

    private fun createActions(context: Context) {
        val feedbackButtonBody = AndesFeedbackScreenButton("Reintentar",
            View.OnClickListener {
                Toast.makeText(context.applicationContext, "Click in button", Toast.LENGTH_SHORT).show()
            })

        val feedbackCloseButton = View.OnClickListener {
            Toast.makeText(context.applicationContext, "Click in close", Toast.LENGTH_SHORT).show()
        }

        feedbackActions = AndesFeedbackScreenActions(
            button = feedbackButtonBody,
            closeCallback = feedbackCloseButton
        )
    }

    private fun createFeedbackScreen(context: Context) {
        andesFeedbackScreen = AndesFeedbackScreenView(
            context = context,
            type = AndesFeedbackScreenType.Simple(AndesFeedbackScreenColor.RED),
            header = feedbackHeader,
            body = feedbackBody,
            actions = feedbackActions
        )
    }

    private fun createHeader() {
        val feedbackHeaderBody = AndesFeedbackScreenText(
            title = "No pudimos corroborar los datos que cargaste en el administrador de ventas",
            overline = "Revisá tus datos de cuenta"
        )

        feedbackHeader = AndesFeedbackScreenHeader(
            feedbackText = feedbackHeaderBody,
            feedbackImage = null
        )
    }

    private fun addFeedbackScreen(container: ConstraintLayout) {
        container.addView(andesFeedbackScreen)
    }
}
