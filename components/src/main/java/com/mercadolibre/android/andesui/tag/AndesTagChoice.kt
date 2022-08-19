package com.mercadolibre.android.andesui.tag

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesLayoutSimpleTagBinding
import com.mercadolibre.android.andesui.tag.accessibility.AndesTagChoiceAccessibilityDelegate
import com.mercadolibre.android.andesui.tag.choice.AndesTagChoiceCallback
import com.mercadolibre.android.andesui.tag.choice.mode.AndesTagChoiceMode
import com.mercadolibre.android.andesui.tag.choice.state.AndesTagChoiceState
import com.mercadolibre.android.andesui.tag.factory.AndesChoiceTagConfigurationFactory
import com.mercadolibre.android.andesui.tag.factory.AndesTagChoiceAttrs
import com.mercadolibre.android.andesui.tag.factory.AndesTagChoiceAttrsParser
import com.mercadolibre.android.andesui.tag.factory.AndesTagChoiceConfiguration
import com.mercadolibre.android.andesui.tag.leftcontent.AndesTagLeftContent
import com.mercadolibre.android.andesui.tag.leftcontent.LeftContent
import com.mercadolibre.android.andesui.tag.rightcontent.AndesTagRightContent
import com.mercadolibre.android.andesui.tag.size.AndesTagSize
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

@Suppress("TooManyFunctions")
class AndesTagChoice : ConstraintLayout {

    private lateinit var andesTagAttrs: AndesTagChoiceAttrs
    private val binding by lazy {
        AndesLayoutSimpleTagBinding.inflate(LayoutInflater.from(context), this, true)
    }

    /**
     * Getter and setter for [state].
     */
    var state: AndesTagChoiceState
        get() = andesTagAttrs.andesTagChoiceState
        set(value) {
            andesTagAttrs = andesTagAttrs.copy(andesTagChoiceState = value)
            setupComponents(createConfig())
        }

    /**
     * Getter and setter for [text].
     */
    var text: String?
        get() = andesTagAttrs.andesSimpleTagText
        set(value) {
            andesTagAttrs = andesTagAttrs.copy(andesSimpleTagText = value)
            setupTitleComponent(createConfig())
        }

    /**
     * Getter and setter for [mode].
     */
    var mode: AndesTagChoiceMode
        get() = andesTagAttrs.andesTagChoiceMode
        set(value) {
            andesTagAttrs = andesTagAttrs.copy(andesTagChoiceMode = value)
            setupComponents(createConfig())
        }

    /**
     * Getter and setter for [shouldAnimateTag].
     */
    var shouldAnimateTag: Boolean
        get() = andesTagAttrs.shouldAnimateTag
        set(value) {
            andesTagAttrs = andesTagAttrs.copy(shouldAnimateTag = value)
        }

    /**
     * Getter and setter for [size].
     */
    var size: AndesTagSize
        get() = andesTagAttrs.andesTagSize
        set(value) {
            andesTagAttrs = andesTagAttrs.copy(andesTagSize = value)
            setupBackgroundComponents(createConfig())
            setupTitleComponent(createConfig())
        }

    var callback: AndesTagChoiceCallback? = null

    /**
     * Getter and setter for [leftContent].
     */
    var leftContent: LeftContent?
        get() = andesTagAttrs.leftContentData
        set(value) {
            if (size == AndesTagSize.SMALL) {
                andesTagAttrs = andesTagAttrs.copy(leftContentData = null)
                andesTagAttrs = andesTagAttrs.copy(leftContent = null)
                Log.e("TAG", "LeftContent can only be used with tag large")
            } else {
                val andesTagLeftContent = when {
                    value?.dot != null -> AndesTagLeftContent.DOT
                    value?.icon != null -> AndesTagLeftContent.ICON
                    value?.image != null -> AndesTagLeftContent.IMAGE
                    else -> AndesTagLeftContent.NONE
                }
                andesTagAttrs = andesTagAttrs.copy(leftContentData = value)
                andesTagAttrs = andesTagAttrs.copy(leftContent = andesTagLeftContent)
            }

            val config = createConfig()
            setupLeftContent(config)
            setupTitleComponent(config)
        }

    fun syncStateAndMode(mode: AndesTagChoiceMode, state: AndesTagChoiceState) {
        andesTagAttrs = andesTagAttrs.copy(andesTagChoiceMode = mode, andesTagChoiceState = state)
        setupComponents(createConfig())
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    @Suppress("unused")
    constructor(
        context: Context,
        mode: AndesTagChoiceMode = TYPE_DEFAULT,
        size: AndesTagSize = SIZE_DEFAULT,
        state: AndesTagChoiceState = STATE_DEFAULT,
        text: String? = TEXT_DEFAULT
    ) : super(context) {
        initAttrs(mode, size, state, text)
    }

    /**
     * Sets the proper [config] for this message based on the [attrs] received via XML.
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesTagAttrs = AndesTagChoiceAttrsParser.parse(context, attrs)
        val config = AndesChoiceTagConfigurationFactory.create(andesTagAttrs)
        setupComponents(config)
    }

    private fun initAttrs(
        mode: AndesTagChoiceMode,
        size: AndesTagSize,
        state: AndesTagChoiceState,
        text: String?
    ) {
        andesTagAttrs = AndesTagChoiceAttrs(text, mode, size, state, null, null)
        val config = AndesChoiceTagConfigurationFactory.create(andesTagAttrs)
        setupComponents(config)
    }

    /**
     * Responsible for setting up all properties of each component that is part of this TagView.
     * Is like a choreographer ;)
     */
    private fun setupComponents(config: AndesTagChoiceConfiguration) {
        initComponents()

        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }

        setupTitleComponent(config)
        setupLeftContent(config)
        setupRightContent(config)
        setupBackgroundComponents(config)

       setOnClickListener {
            onTagClick()
        }

        setupAccessibility()
    }

    private fun setupAccessibility() {
        isFocusable = true
        binding.andesTagContainer.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
        accessibilityDelegate = AndesTagChoiceAccessibilityDelegate(this)
    }

    private fun onTagClick() {
        val result = callback?.shouldSelectTag(this) ?: true
        if (result) {
            state = if (state == AndesTagChoiceState.SELECTED) {
                AndesTagChoiceState.IDLE
            } else {
                AndesTagChoiceState.SELECTED
            }
        }
    }

    /**
     * Creates all the views that are part of this TagView.
     * After a view is created then a view id is added to it.
     */
    private fun initComponents() {
        // enable animations
        if (shouldAnimateTag) {
            val layoutTransition = LayoutTransition()
            layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            binding.andesTagContainer.layoutTransition = layoutTransition
        } else {
            binding.andesTagContainer.layoutTransition = null
        }
    }

    private fun setupBackgroundComponents(config: AndesTagChoiceConfiguration) {
        val shape = GradientDrawable()
        shape.cornerRadius = size.size.border(context)
        shape.setColor(config.backgroundColor.colorInt(context))
        val borderSize = resources.getDimension(R.dimen.andes_tag_border)
        shape.setStroke(borderSize.toInt(), config.borderColor.colorInt(context))
        background = shape

        with(binding.andesTagContainer) {
            minHeight = size.size.height(context).toInt()
            maxHeight = size.size.height(context).toInt()
            minWidth = size.size.height(context).toInt()
        }
    }

    /**
     * Gets data from the config and sets to the title component of this tag.
     */
    private fun setupTitleComponent(config: AndesTagChoiceConfiguration) {
        if (state == AndesTagChoiceState.SELECTED) {
            binding.andesTagContainer.contentDescription =
                config.text + context.resources.getString(R.string.andes_tag_choice_selected)
        } else {
            binding.andesTagContainer.contentDescription = config.text
        }

        if (config.text == null || config.text.isEmpty()) {
            binding.andesTagContainer.visibility = View.GONE
        } else {
            binding.andesTagContainer.visibility = View.VISIBLE

            with(binding.simpleTagText) {
                text = config.text
                typeface = context.getFontOrDefault(R.font.andes_font_regular)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, size.size.textSize(context))
                setTextColor(config.textColor.colorInt(context))
            }

            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.andesTagContainer)
            constraintSet.setMargin(
                R.id.simpleTagText,
                ConstraintSet.START,
                config.leftContent.content.leftMarginText(context, size)
            )
            constraintSet.setMargin(
                R.id.simpleTagText,
                ConstraintSet.END,
                config.rightContent.content.rightMarginText(context, size)
            )
            constraintSet.applyTo(binding.andesTagContainer)
        }
    }

    private fun setupLeftContent(config: AndesTagChoiceConfiguration) {
        val leftContent = findViewById<FrameLayout>(R.id.leftContent)
        if (config.leftContentData != null && config.leftContent != AndesTagLeftContent.NONE) {
            if (config.leftContentData.icon != null) {
                config.leftContentData.icon?.iconDefaultColor = config.leftContentColor
            }

            val width = config.leftContentWidth
            val height = config.leftContentHeight
            if (width != null && height != null) {
                val params = leftContent.layoutParams
                params.height = context.resources.getDimension(config.leftContentHeight).toInt()
                params.width = context.resources.getDimension(config.leftContentWidth).toInt()
                leftContent.layoutParams = params
            }
            leftContent.removeAllViews()
            leftContent.addView(config.leftContent.content.view(context, config.leftContentData))
            leftContent.visibility = View.VISIBLE

            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.andesTagContainer)
            constraintSet.setMargin(
                R.id.leftContent,
                ConstraintSet.START,
                config.leftContent.content.leftMargin(context)
            )
            constraintSet.setMargin(
                R.id.leftContent,
                ConstraintSet.END,
                config.leftContent.content.rightMargin(context)
            )
            constraintSet.applyTo(binding.andesTagContainer)
        } else {
            leftContent.visibility = View.GONE
        }
    }

    private fun setupRightContent(config: AndesTagChoiceConfiguration) {
        val rightContent = findViewById<FrameLayout>(R.id.rightContent)
        if (config.rightContent != AndesTagRightContent.NONE) {
            rightContent.removeAllViews()
            rightContent.addView(
                config.rightContent.content.view(
                    context,
                    config.rightContentColor,
                    null,
                    null
                )
            )

            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.andesTagContainer)
            constraintSet.setMargin(
                R.id.rightContent,
                ConstraintSet.START,
                config.rightContent.content.leftMargin(context, size)
            )
            constraintSet.setMargin(
                R.id.rightContent,
                ConstraintSet.END,
                config.rightContent.content.rightMargin(context, size)
            )
            constraintSet.applyTo(binding.andesTagContainer)

            rightContent.visibility = View.VISIBLE
        } else {
            rightContent.visibility = View.GONE
        }
    }

    private fun createConfig() = AndesChoiceTagConfigurationFactory.create(andesTagAttrs)

    override fun getAccessibilityClassName(): CharSequence {
        return CheckBox::class.java.name
    }

    companion object {
        private val TYPE_DEFAULT = AndesTagChoiceMode.SIMPLE
        private val STATE_DEFAULT = AndesTagChoiceState.IDLE
        private val SIZE_DEFAULT = AndesTagSize.LARGE
        private val TEXT_DEFAULT = null
    }
}
