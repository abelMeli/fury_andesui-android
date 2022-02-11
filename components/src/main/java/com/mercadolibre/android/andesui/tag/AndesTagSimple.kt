package com.mercadolibre.android.andesui.tag

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesLayoutSimpleTagBinding
import com.mercadolibre.android.andesui.tag.factory.AndesSimpleTagConfigurationFactory
import com.mercadolibre.android.andesui.tag.factory.AndesTagSimpleAttrs
import com.mercadolibre.android.andesui.tag.factory.AndesTagSimpleAttrsParser
import com.mercadolibre.android.andesui.tag.factory.AndesTagSimpleConfiguration
import com.mercadolibre.android.andesui.tag.leftcontent.AndesTagLeftContent
import com.mercadolibre.android.andesui.tag.leftcontent.LeftContent
import com.mercadolibre.android.andesui.tag.rightcontent.AndesTagRightContent
import com.mercadolibre.android.andesui.tag.rightcontent.RightContent
import com.mercadolibre.android.andesui.tag.rightcontent.RightContentDismiss
import com.mercadolibre.android.andesui.tag.size.AndesTagSize
import com.mercadolibre.android.andesui.tag.type.AndesTagType
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

class AndesTagSimple : ConstraintLayout {

    /**
     * Getter and setter for [type].
     */
    var type: AndesTagType
        get() = andesTagAttrs.andesTagType
        set(value) {
            andesTagAttrs = andesTagAttrs.copy(andesTagType = value)
            setupBackgroundComponents(createConfig())
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

    /**
     * Getter and setter for [isDismissable].
     */
    var isDismissable: Boolean
        get() = andesTagAttrs.rightContent == AndesTagRightContent.DISMISS
        set(value) {
            if (value) {
                andesTagAttrs = andesTagAttrs.copy(rightContentData = RightContent(dismiss = RightContentDismiss()))
                andesTagAttrs = andesTagAttrs.copy(rightContent = AndesTagRightContent.DISMISS)
            } else {
                andesTagAttrs = andesTagAttrs.copy(rightContentData = null)
                andesTagAttrs = andesTagAttrs.copy(rightContent = null)
            }

            val config = createConfig()
            setupRightContent(config)
            setupTitleComponent(config)
        }

    fun setupDismsissableCallback(onClickListener: OnClickListener) {
        if (isDismissable && andesTagAttrs.rightContentData?.dismiss != null) {
            andesTagAttrs.rightContentData?.dismiss?.onClickListener = onClickListener
        }
    }

    private lateinit var andesTagAttrs: AndesTagSimpleAttrs

    private val binding: AndesLayoutSimpleTagBinding by lazy {
        AndesLayoutSimpleTagBinding.inflate(LayoutInflater.from(context), this, true)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    @Suppress("unused")
    constructor(
        context: Context,
        type: AndesTagType = TYPE_DEFAULT,
        size: AndesTagSize = SIZE_DEFAULT,
        text: String? = TEXT_DEFAULT
    ) : super(context) {
        initAttrs(type, size, text)
    }

    /**
     * Sets the proper [config] for this message based on the [attrs] received via XML.
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesTagAttrs = AndesTagSimpleAttrsParser.parse(context, attrs)
        val config = AndesSimpleTagConfigurationFactory.create(andesTagAttrs)
        setupComponents(config)
    }

    private fun initAttrs(
        type: AndesTagType,
        size: AndesTagSize,
        text: String?,
        leftContent: AndesTagLeftContent? = null,
        leftContentData: LeftContent? = null
    ) {
        andesTagAttrs = AndesTagSimpleAttrs(type, size, text, leftContentData, leftContent)
        val config = AndesSimpleTagConfigurationFactory.create(andesTagAttrs)
        setupComponents(config)
    }

    /**
     * Responsible for setting up all properties of each component that is part of this badge.
     * Is like a choreographer ;)
     */
    private fun setupComponents(config: AndesTagSimpleConfiguration) {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }

        setupBackgroundComponents(config)
        setupTitleComponent(config)
        setupLeftContent(config)
        setupRightContent(config)
    }

    private fun setupBackgroundComponents(config: AndesTagSimpleConfiguration) {
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
    private fun setupTitleComponent(config: AndesTagSimpleConfiguration) {
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
            constraintSet.setMargin(R.id.simpleTagText, ConstraintSet.START, config.leftContent.content.leftMarginText(context, size))
            constraintSet.setMargin(R.id.simpleTagText, ConstraintSet.END, config.rightContent.content.rightMarginText(context, size))
            constraintSet.applyTo(binding.andesTagContainer)
        }
    }

    private fun setupLeftContent(config: AndesTagSimpleConfiguration) {
        val leftContent = findViewById<FrameLayout>(R.id.leftContent)
        if (config.leftContent != AndesTagLeftContent.NONE && config.leftContentData != null) {
            leftContent.removeAllViews()
            leftContent.addView(config.leftContent.content.view(context, config.leftContentData))
            leftContent.visibility = View.VISIBLE

            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.andesTagContainer)
            constraintSet.setMargin(R.id.leftContent, ConstraintSet.START, config.leftContent.content.leftMargin(context))
            constraintSet.setMargin(R.id.leftContent, ConstraintSet.END, config.leftContent.content.rightMargin(context))
            constraintSet.applyTo(binding.andesTagContainer)
        } else {
            leftContent.visibility = View.GONE
        }
    }

    private fun setupRightContent(config: AndesTagSimpleConfiguration) {
        val rightContent = findViewById<FrameLayout>(R.id.rightContent)
        if (config.rightContent != AndesTagRightContent.NONE) {
            rightContent.removeAllViews()

            val view = config.rightContent.content.view(
                context,
                config.dismissColor,
                config.rightContentData,
                OnClickListener {
                    binding.andesTagContainer.visibility = View.GONE
                    config.rightContentData?.dismiss?.onClickListener?.onClick(this)
                }
            )
            view.contentDescription = "${resources.getString(R.string.andes_tag_dismiss)} ${andesTagAttrs.andesSimpleTagText}"
            rightContent.addView(view)

            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.andesTagContainer)
            constraintSet.setMargin(R.id.rightContent, ConstraintSet.START, config.rightContent.content.leftMargin(context, size))
            constraintSet.setMargin(R.id.rightContent, ConstraintSet.END, config.rightContent.content.rightMargin(context, size))
            constraintSet.applyTo(binding.andesTagContainer)

            rightContent.visibility = View.VISIBLE
        } else {
            rightContent.visibility = View.GONE
        }
    }

    private fun createConfig() = AndesSimpleTagConfigurationFactory.create(andesTagAttrs)

    companion object {
        private val TYPE_DEFAULT = AndesTagType.NEUTRAL
        private val SIZE_DEFAULT = AndesTagSize.LARGE
        private val TEXT_DEFAULT = null
    }
}
