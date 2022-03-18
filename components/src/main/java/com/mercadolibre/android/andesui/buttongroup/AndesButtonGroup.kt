package com.mercadolibre.android.andesui.buttongroup

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.buttongroup.align.AndesButtonGroupAlign
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.buttongroup.factory.AndesButtonGroupAttrs
import com.mercadolibre.android.andesui.buttongroup.factory.AndesButtonGroupAttrsParser
import com.mercadolibre.android.andesui.buttongroup.factory.AndesButtonGroupConfiguration
import com.mercadolibre.android.andesui.buttongroup.factory.AndesButtonGroupConfigurationFactory
import com.mercadolibre.android.andesui.buttongroup.type.AndesButtonGroupType
import com.mercadolibre.android.andesui.buttongroup.utils.FIRST_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.THREE_BUTTON
import com.mercadolibre.android.andesui.utils.setConstraints

/**
 * User interface element that functions as a container for [AndesButton] You can add from 1 to 3 buttons.
 *
 * It has the same characteristics of the AndesButtons, only with the particularity
 * that these can be distributed according to convenience, horizontally, vertically or automatically
 * if the button texts are too long to be distributed in the best way.
 *
 *
 * Buttons can only be instantiated by code using the [setButtons] method which supports a list of [AndesButtons].
 *
 * If your desire is to use AndesButtonGroup via XML, you must always use the addButtons method
 * at the code level in order to set the buttons.
 *
 * <pre>
 * &lt;com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
 *     android:layout_width="math_parent"
 *     android:layout_height="wrap_content" /&gt;</pre>
 *
 *
 * This AndesButtonGroup relies heavily in a configuration created by [AndesButtonGroupConfigurationFactory] from
 * its attributes. Some values of this configuration can be updated programmatically,
 * like [align], [type] and [distribution], by accessing its related setters.
 *
 */
class AndesButtonGroup : ConstraintLayout {

    private lateinit var andesButtonGroupAttrs: AndesButtonGroupAttrs

    private lateinit var andesButtonList: List<AndesButton>

    /**
     * Getter and setter for [distribution].
     */
    var distribution: AndesButtonGroupDistribution
        get() = andesButtonGroupAttrs.andesButtonGroupDistribution
        set(value) {
            andesButtonGroupAttrs = andesButtonGroupAttrs.copy(andesButtonGroupDistribution = value)
            updateComponent(createConfig())
        }

    /**
     * Getter and setter for [type].
     */
    var type: AndesButtonGroupType
        get() = andesButtonGroupAttrs.andesButtonGroupType
        set(value) {
            andesButtonGroupAttrs = andesButtonGroupAttrs.copy(andesButtonGroupType = value)
            updateComponent(createConfig())
        }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        buttonList: List<AndesButton>,
        type: AndesButtonGroupType = TYPE_DEFAULT,
        distribution: AndesButtonGroupDistribution = DISTRIBUTION_DEFAULT
    ) : super(context) {
        initAttrs(distribution, type)
        setButtons(buttonList)
    }

    /**
     * Set Andes buttons
     *
     * @param buttonList button list
     */
    fun setButtons(buttonList: List<AndesButton>) {
        when {
            buttonList.isEmpty() ->
                throw IllegalStateException("No button was found in the list, at least one button is needed in the list.")
            buttonList.size > THREE_BUTTON ->
                throw IllegalStateException("Number of buttons exceeded, a maximum of 3 buttons is allowed.")
        }
        andesButtonList = buttonList

        removeAllViews()
        andesButtonList.forEach { andesButton ->
            addView(andesButton)
        }
        updateComponent(createConfig())
    }

    /**
     * Set button alignment
     *
     * @param align alignment property for the Responsive type
     */
    fun setAlign(align: AndesButtonGroupAlign) {
        if (type != AndesButtonGroupType.FullWidth) {
            type = AndesButtonGroupType.Responsive(align)
        }
    }

    /**
     * Sets the proper configuration for this button based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesButtonGroupAttrs = AndesButtonGroupAttrsParser.parse(context, attrs)
        setupComponents()
    }

    private fun initAttrs(
        distribution: AndesButtonGroupDistribution,
        type: AndesButtonGroupType
    ) {
        andesButtonGroupAttrs = AndesButtonGroupAttrs(
            distribution,
            type
        )
        setupComponents()
    }

    private fun setupComponents() {
        setupViewId()
    }

    private fun setLayoutWidth(andesButton: AndesButton, size: Int) {
        val params = LayoutParams(size, andesButton.layoutParams.height)
        andesButton.layoutParams = params
    }

    private fun configLayoutParams(config: AndesButtonGroupConfiguration) {
        andesButtonList.forEach { andesButton ->
            setLayoutWidth(andesButton, config.layoutWidth)
        }
    }

    private fun updateComponent(config: AndesButtonGroupConfiguration) {
        if (this::andesButtonList.isInitialized) {
            configSizeAndesButton()
            configLayoutParams(config)

            setConstraints {
                clearConstraints()
            }
            config.instructions.map {
                setConstraints(it)
            }
        }
    }

    /**
     * By convention the size of all buttons is set according to the size of the first button.
     */
    private fun configSizeAndesButton() {
        andesButtonList.map {
            it.size = andesButtonList[FIRST_BUTTON].size
        }
    }

    private fun ConstraintSet.clearConstraints() {
        andesButtonList.forEach { andesButton ->
            clear(andesButton.id, ConstraintSet.START)
            clear(andesButton.id, ConstraintSet.END)
            clear(andesButton.id, ConstraintSet.TOP)
            clear(andesButton.id, ConstraintSet.BOTTOM)
        }
    }

    private fun createConfig(): AndesButtonGroupConfiguration {
        return AndesButtonGroupConfigurationFactory.create(
            context,
            andesButtonGroupAttrs,
            this
        )
    }

    /**
     * Sets a view id to this button.
     *
     */
    private fun setupViewId() {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    companion object {
        private val DISTRIBUTION_DEFAULT = AndesButtonGroupDistribution.HORIZONTAL
        private val TYPE_DEFAULT = AndesButtonGroupType.FullWidth
    }
}
