package com.mercadolibre.android.andesui.checkbox

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.CheckBox
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.checkbox.accessibility.AndesCheckboxAccessibilityDelegate
import com.mercadolibre.android.andesui.checkbox.accessibility.AndesCheckboxAccessibilityEventDispatcher
import com.mercadolibre.android.andesui.checkbox.align.AndesCheckboxAlign
import com.mercadolibre.android.andesui.checkbox.factory.AndesCheckboxAttrParser
import com.mercadolibre.android.andesui.checkbox.factory.AndesCheckboxAttrs
import com.mercadolibre.android.andesui.checkbox.factory.AndesCheckboxConfiguration
import com.mercadolibre.android.andesui.checkbox.factory.AndesCheckboxConfigurationFactory
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.checkbox.type.AndesCheckboxType
import com.mercadolibre.android.andesui.databinding.AndesLayoutCheckboxBinding
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.utils.getAccessibilityManager

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
            createConfig().also {
                setupBackgroundComponent(it)
                setupType(it)
            }
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
     * Getter and setter for [bodyLinks].
     */
    var bodyLinks: AndesBodyLinks?
        get() = andesCheckboxAttrs.andesCheckboxBodyLinks
        set(value) {
            andesCheckboxAttrs = andesCheckboxAttrs.copy(andesCheckboxBodyLinks = value)
            setupTitleComponent(createConfig())
            setupA11yDelegate()
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
    private val a11yEventDispatcher by lazy { AndesCheckboxAccessibilityEventDispatcher() }
    internal lateinit var spannableText: CharSequence; private set
    private lateinit var clickableView: View
    private val gestureDetector by lazy { createGestureDetector() }
    private val binding by lazy { AndesLayoutCheckboxBinding.inflate(LayoutInflater.from(context), this, true) }

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
        setupAccessibilityNavigation()
        setupViewId()
        setupTitleComponent(config)
        setupAlignComponent(config)
        setupBackgroundComponent(config)
        setupTitleNumberLinesComponent(config)
        setupType(config)
        setupA11yDelegate()
    }

    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    private fun setupA11yDelegate() {
        accessibilityDelegate = AndesCheckboxAccessibilityDelegate(this)
    }

    private fun setupAccessibilityNavigation() {
        isClickable = true
        isFocusable = true
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES

        with(binding.andesCheckboxContainer) {
            isFocusable = false
            isClickable = false
            importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
        }
    }

    private fun setOnCheckedChangeListener() {
        clickableView.setOnClickListener {
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
            a11yEventDispatcher.notifyA11yStatusChanged(this@AndesCheckbox, status)
        }
        binding.checkboxText.setOnClickListener {
            clickableView.callOnClick()
        }
        setOnClickListener {
            binding.checkboxText.performClick()
        }
    }

    /**
     * Gets data from the config and sets to the title component of this checkbox.
     */
    private fun setupTitleComponent(config: AndesCheckboxConfiguration) {
        binding.checkboxText.apply {
            text = config.text
            ellipsize = TextUtils.TruncateAt.END
            setTextColor(config.type.type.textColor())
            bodyLinks = config.bodyLinks
            spannableText = text
        }
        setupTitleNumberLinesComponent(config)
    }

    /**
     * Gets data from the config and sets to the align component of this checkbox.
     * Also, according to the align, we change the clickable area of the component to
     * the left or the right checkbox container.
     *
     * Every time we change this value, we need to remove the click listener from the now
     * unused and hidden view, and re-set it in the now visible view.
     */
    private fun setupAlignComponent(config: AndesCheckboxConfiguration) {
        when (config.align) {
            AndesCheckboxAlign.LEFT -> {
                binding.containerLeftCheckbox.visibility = View.VISIBLE
                binding.containerRightCheckbox.visibility = View.GONE
                clickableView = binding.containerLeftCheckbox
            }
            AndesCheckboxAlign.RIGHT -> {
                binding.containerLeftCheckbox.visibility = View.GONE
                binding.containerRightCheckbox.visibility = View.VISIBLE
                clickableView = binding.containerRightCheckbox
            }
        }
        setOnCheckedChangeListener()
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
        val icon =
            config.status.status.icon(context, config.type.type.iconColor(context, config.status))
        binding.leftCheckboxIcon.setImageDrawable(icon)
        binding.rightCheckboxIcon.setImageDrawable(icon)
        binding.leftCheckbox.background = shape
        binding.rightCheckbox.background = shape
    }

    /**
     * Sets component enable state in order to handle accessibility properly.
     */
    private fun setupType(config: AndesCheckboxConfiguration) {
        isEnabled = config.type != AndesCheckboxType.DISABLED
    }

    /**
     * Gets data from the config and sets to the titleNumberOfLines component of this checkbox.
     */
    private fun setupTitleNumberLinesComponent(config: AndesCheckboxConfiguration) {
        binding.checkboxText.maxLines = config.titleNumberOfLines
    }

    /**
     * Creates the gesture detector needed to filter the click events over the component.
     *
     * The onSingleTapUp() return represents if the action is consumed here or is passed.
     * When we use TalkBack, we need to catch the tap action here in order to avoid passing
     * the direct click to the links that may be present in the text. Thus, when the reader is
     * enabled, this method should return true.
     */
    private fun createGestureDetector(): GestureDetector {
        return GestureDetector(context, object : GestureDetector.OnGestureListener {
            override fun onDown(e: MotionEvent?): Boolean {
                return false
            }

            override fun onShowPress(e: MotionEvent?) { /* no-op */
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return context.getAccessibilityManager().let {
                    if (it.isEnabled) {
                        performClick()
                    }
                    it.isEnabled
                }
            }

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                return false
            }

            override fun onLongPress(e: MotionEvent?) { /* no-op */
            }

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                return false
            }
        })
    }

    /**
     * Intercepts the touch event before it reaches the children of this viewgroup.
     * When talkback is ON, we need to catch the click event in order to avoid clicking in
     * the text links.
     * For more info, check [this site](https://developer.android.com/training/gestures/viewgroup.html)
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (context.getAccessibilityManager().isEnabled) {
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }

    /**
     * gets the touch event intercepted in the [onInterceptTouchEvent] when the talkback is ON
     * and passes the [event] to the [gestureDetector]
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (context.getAccessibilityManager().isEnabled) {
            gestureDetector.onTouchEvent(event)
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun createConfig() = AndesCheckboxConfigurationFactory.create(andesCheckboxAttrs)

    /**
     * Overrides accessibility view role.
     */
    override fun getAccessibilityClassName(): CharSequence {
        return CheckBox::class.java.name
    }

    companion object {
        private val ANDES_ALIGN_DEFAULT_VALUE = AndesCheckboxAlign.LEFT
        private val ANDES_STATUS_DEFAULT_VALUE = AndesCheckboxStatus.UNSELECTED
        private val ANDES_TYPE_DEFAULT_VALUE = AndesCheckboxType.IDLE
        internal const val DEFAULT_LINES = 1
    }
}
