package com.mercadolibre.android.andesui.feedback.screen.actions

import android.view.View
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.buttongroup.align.AndesButtonGroupAlign
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.buttongroup.type.AndesButtonGroupType

data class AndesFeedbackScreenActions
@Deprecated(
    message = "Instead you can use constructor(AndesButtonGroup, View.OnClickListener? = nul)",
    replaceWith = ReplaceWith(
        "AndesFeedbackScreenActions(" +
                "buttonGroup = AndesButtonGroup(" +
                "context = context, " +
                "buttonList = listOf(AndesButton(context = context, buttonText = \"Button text\"))" +
                "), " +
                "closeCallback = closeCallback)",
        "com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup",
        "com.mercadolibre.android.andesui.button.AndesButton"
    ),
    level = DeprecationLevel.WARNING
) constructor(
    val button: AndesFeedbackScreenButton? = null,
    val closeCallback: View.OnClickListener? = null
) {
    var buttonGroup: AndesButtonGroup? = null
    constructor(buttonGroup: AndesButtonGroup, closeCallback: View.OnClickListener? = null) : this(closeCallback = closeCallback) {
        this.buttonGroup = buttonGroup
    }
}

data class AndesFeedbackScreenButton(
    val text: String,
    val onClick: View.OnClickListener
)
