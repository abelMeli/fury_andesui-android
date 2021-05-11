package com.mercadolibre.android.andesui.checkbox

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.checkbox.factory.AndesCheckboxAttrParser
import com.mercadolibre.android.andesui.checkbox.factory.AndesCheckboxAttrs
import com.mercadolibre.android.andesui.checkbox.factory.AndesCheckboxConfiguration
import com.mercadolibre.android.andesui.checkbox.factory.AndesCheckboxConfigurationFactory
import com.mercadolibre.android.andesui.checkbox.align.AndesCheckboxAlign
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.checkbox.type.AndesCheckboxType
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import kotlinx.android.synthetic.main.andes_layout_checkbox.view.*

@Suppress("TooManyFunctions")
class AndesCheckbox : ConstraintLayout {

    /**
     * Getter and setter for [text].
     */
    var text: String?
        get() = andesCheckboxAttrs.andesCheckboxText
        set(value) {
            andesCheckboxAttrs = andesCheckboxAttrs.copy(andesCheckboxText = value)
            setupTitleComponent(createConfig())
        }

    /**
     * Getter and setter for [align].
     */
    var align: AndesCheckboxAlign
        get() = andesCheckboxAttrs.andesCheckboxAlign
        set(value) {
            andesCheckboxAttrs = andesCheckboxAttrs.copy(andesCheckboxAlign = value)
            setupAlignComponent(createConfig())
        }

    /**
     * Getter and setter for [AndesCheckboxStatus].
     */
    var status: AndesCheckboxStatus
        get() = andesCheckboxAttrs.andesCheckboxStatus
        set(value) {
            andesCheckboxAttrs = andesCheckboxAttrs.copy(andesCheckboxStatus = value)
            setupBackgroundComponent(createConfig())
        }

    /**
     * Getter and setter for [type].
     */
    var type: AndesCheckboxType
        get() = andesCheckboxAttrs.andesCheckboxType
        set(value) {
            andesCheckboxAttrs = andesCheckboxAttrs.copy(andesCheckboxType = value)
            setupBackgroundComponent(createConfig())
        }

    /**
     * Getter and setter for [titleNumberOfLines].
     */
    var titleNumberOfLines: Int
        get() = andesCheckboxAttrs.andesCheckboxTitleNumberOfLine
        set(value) {
            andesCheckboxAttrs = andesCheckboxAttrs.copy(andesCheckboxTitleNumberOfLine = value)
            setupTitleNumberLinesComponent(createConfig())
        }

    /**
     * Setter [OnClickListener].
     */
    fun setupCallback(@Nullable listener: OnClickListener) {
        if (this.privateListener != listener) {
            this.privateListener = listener
        }
    }

    private var privateListener: OnClickListener? = null
    private lateinit var andesCheckboxAttrs: AndesCheckboxAttrs
    private lateinit var containerCheckbox: ConstraintLayout

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        text: String,
        align: AndesCheckboxAlign = ANDES_ALIGN_DEFAULT_VALUE,
        status: AndesCheckboxStatus = ANDES_STATUS_DEFAULT_VALUE,
        type: AndesCheckboxType = ANDES_TYPE_DEFAULT_VALUE
    ) : super(context) {
        initAttrs(text, align, status, type)
    }

    /**
     * Sets the proper [config] for this message based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesCheckboxAttrs = AndesCheckboxAttrParser.parse(context, attrs)
        val config = AndesCheckboxConfigurationFactory.create(andesCheckboxAttrs)
        setupComponents(config)
    }

    private fun initAttrs(
        text: String,
        align: AndesCheckboxAlign = ANDES_ALIGN_DEFAULT_VALUE,
        status: AndesCheckboxStatus = ANDES_STATUS_DEFAULT_VALUE,
        type: AndesCheckboxType = ANDES_TYPE_DEFAULT_VALUE
    ) {
        andesCheckboxAttrs = AndesCheckboxAttrs(align, text, status, type)
        val config = AndesCheckboxConfigurationFactory.create(andesCheckboxAttrs)
        setupComponents(config)
    }

    private fun setupComponents(config: AndesCheckboxConfiguration) {
        initComponents()

        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }

        setupTitleComponent(config)
        setupAlignComponent(config)
        setupBackgroundComponent(config)
        setupTitleNumberLinesComponent(config)
    }

    /**
     * Creates all the views that are part of this checkbox.
     * After a view is created then a view id is added to it.
     */
    private fun initComponents() {
        val container = LayoutInflater.from(context).inflate(R.layout.andes_layout_checkbox, this)
        containerCheckbox = container.findViewById(R.id.andes_checkbox_container)
        onCheckedChangeListener(containerCheckbox)
    }

    private fun onCheckedChangeListener(view: View) {
        view.setOnClickListener {
            when (type) {
                AndesCheckboxType.ERROR -> {
                    type = AndesCheckboxType.IDLE
                    status = AndesCheckboxStatus.SELECTED
                    privateListener?.onClick(this)
                    setupBackgroundComponent(createConfig())
                }
                AndesCheckboxType.IDLE -> {
                    status = when (status) {
                        AndesCheckboxStatus.SELECTED -> AndesCheckboxStatus.UNSELECTED
                        AndesCheckboxStatus.UNSELECTED -> AndesCheckboxStatus.SELECTED
                        AndesCheckboxStatus.UNDEFINED -> AndesCheckboxStatus.SELECTED
                    }
                    privateListener?.onClick(this)
                    setupBackgroundComponent(createConfig())
                }
            }
        }
    }

    /**
     * Gets data from the config and sets to the title component of this checkbox.
     */
    private fun setupTitleComponent(config: AndesCheckboxConfiguration) {
        checkboxText.text = config.text
        checkboxText.ellipsize = TextUtils.TruncateAt.END
        checkboxText.typeface = context.getFontOrDefault(R.font.andes_font_regular)
        checkboxText.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.andes_checkbox_text_size))
        checkboxText.setTextColor(config.type.type.textColor(context).colorInt(context))
        setupTitleNumberLinesComponent(config)
    }

    /**
     * Gets data from the config and sets to the align component of this checkbox.
     */
    private fun setupAlignComponent(config: AndesCheckboxConfiguration) {
        when (config.align) {
            AndesCheckboxAlign.LEFT -> {
                containerLeftCheckbox.visibility = View.VISIBLE
                containerRightCheckbox.visibility = View.GONE
            }
            AndesCheckboxAlign.RIGHT -> {
                containerLeftCheckbox.visibility = View.GONE
                containerRightCheckbox.visibility = View.VISIBLE
            }
        }
    }

    /**
     * The checkbox is drawn with a shape (border and background) and then the icon is added
     */
    private fun setupBackgroundComponent(config: AndesCheckboxConfiguration) {
        val shape = GradientDrawable()
        shape.cornerRadius = resources.getDimension(R.dimen.andes_checkbox_radius)
        // Border
        shape.setStroke(
            resources.getDimension(R.dimen.andes_checkbox_stroke_width).toInt(),
            config.type.type.borderColor(context, config.status).colorInt(context)
        )
        // Background
        shape.setColor(config.type.type.backgroundColor(context, config.status).colorInt(context))
        // Icon
        val icon = config.status.status.icon(context, config.type.type.iconColor(context, config.status))
        leftCheckboxIcon.setImageDrawable(icon)
        rightCheckboxIcon.setImageDrawable(icon)
        leftCheckbox.background = shape
        rightCheckbox.background = shape
    }

    /**
     * Gets data from the config and sets to the titleNumberOfLines component of this checkbox.
     */
    private fun setupTitleNumberLinesComponent(config: AndesCheckboxConfiguration) {
        checkboxText.setLines(config.titleNumberOfLines)
    }

    private fun createConfig() = AndesCheckboxConfigurationFactory.create(andesCheckboxAttrs)

    companion object {
        private val ANDES_ALIGN_DEFAULT_VALUE = AndesCheckboxAlign.LEFT
        private val ANDES_STATUS_DEFAULT_VALUE = AndesCheckboxStatus.UNSELECTED
        private val ANDES_TYPE_DEFAULT_VALUE = AndesCheckboxType.IDLE
        internal const val DEFAULT_LINES = 1
    }
}
