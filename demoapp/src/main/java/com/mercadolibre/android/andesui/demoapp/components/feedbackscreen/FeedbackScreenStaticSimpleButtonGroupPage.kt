package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.feedback.screen.AndesFeedbackScreenView
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenActions
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton
import com.mercadolibre.android.andesui.feedback.screen.color.AndesFeedbackScreenColor
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenHeader
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenTextDescription
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType

class FeedbackScreenStaticSimpleButtonGroupPage {

    private lateinit var andesFeedbackScreen: AndesFeedbackScreenView
    private lateinit var feedbackHeader: AndesFeedbackScreenHeader
    private lateinit var feedbackActions: AndesFeedbackScreenActions

    fun create(context: Context, container: ConstraintLayout) {
        createHeader()
        createActions(context)
        createFeedbackScreen(context)
        addFeedbackScreen(container)
    }

    private fun createActions(context: Context) {
        val buttonGroup = AndesButtonGroup(
            context,
            listOf(
                AndesButton(context, buttonText = "Reply").apply {
                    setOnClickListener { showToast(context, "Click in Reply button") }
                },
                AndesButton(context, buttonText = "Send e-mail", buttonHierarchy = AndesButtonHierarchy.QUIET).apply {
                    setOnClickListener { showToast(context, "Click in Send e-mail button") }
                },
                AndesButton(context, buttonText = "Call").apply {
                    setOnClickListener { showToast(context, "Click in Call button") }
                }
            ),
            distribution = AndesButtonGroupDistribution.MIXED
        )

        val feedbackCloseButton = View.OnClickListener {
            Toast.makeText(context.applicationContext, "Click in close", Toast.LENGTH_SHORT).show()
        }

        feedbackActions = AndesFeedbackScreenActions(buttonGroup, feedbackCloseButton)
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun createFeedbackScreen(context: Context) {
        andesFeedbackScreen = AndesFeedbackScreenView(
            context = context,
            type = AndesFeedbackScreenType.Simple(AndesFeedbackScreenColor.GREEN),
            header = feedbackHeader,
            body = null,
            actions = feedbackActions
        )
    }

    private fun createHeader() {
        val feedbackHeaderBody = AndesFeedbackScreenText(
            title = "Cargaste todos los datos",
            description = AndesFeedbackScreenTextDescription(
                "Volvé a iniciar sesión para aplicar los cambios"
            )
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
