package com.mercadolibre.android.andesui.textview

import android.content.Context
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmount
import com.mercadolibre.android.andesui.textview.accessibility.AndesTextViewAccessibilityDelegate
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.textview.factory.AndesTextViewAttrs
import com.mercadolibre.android.andesui.textview.factory.AndesTextViewAttrsParser
import com.mercadolibre.android.andesui.textview.factory.AndesTextViewConfiguration
import com.mercadolibre.android.andesui.textview.factory.AndesTextViewConfigurationFactory
import com.mercadolibre.android.andesui.textview.moneyamount.AndesTextViewMoneyAmount
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle
import com.mercadolibre.android.andesui.utils.getAccessibilityManager
import com.mercadolibre.android.andesui.utils.getRange
import com.mercadolibre.android.andesui.utils.isNotValidRange

@Suppress("TooManyFunctions")
class AndesTextView : AppCompatTextView {

    /**
     * Getter and setter for [style].
     */
    var style: AndesTextViewStyle
        get() = andesTextViewAttrs.andesTextViewStyle
        set(value) {
            andesTextViewAttrs = andesTextViewAttrs.copy(andesTextViewStyle = value)
            createConfig().also {
                setupSize(it)
                setupFont(it)
                setupLineHeight(it)
            }
        }

    /**
     * Getter and setter for [bodyLinks].
     */
    var bodyLinks: AndesBodyLinks?
        get() = andesTextViewAttrs.andesTextViewBodyLinks
        set(value) {
            andesTextViewAttrs = andesTextViewAttrs.copy(andesTextViewBodyLinks = value)
            setupText(createConfig())
            setupA11yDelegate()
        }

    /**
     * Getter and setter for [bodyBolds].
     */
    var bodyBolds: AndesBodyBolds?
        get() = andesTextViewAttrs.andesTextViewBodyBolds
        set(value) {
            andesTextViewAttrs = andesTextViewAttrs.copy(andesTextViewBodyBolds = value)
            setupText(createConfig())
        }

    /**
     * Getter and setter for [isLinkColorInverted]. When this value is true,
     * the links present in the text will be white colored and underlined.
     * When false, the links will be accent colored.
     */
    var isLinkColorInverted: Boolean
        get() = andesTextViewAttrs.isLinkColorInverted
        set(value) {
            andesTextViewAttrs = andesTextViewAttrs.copy(isLinkColorInverted = value)
            setupText(createConfig())
        }

    private var andesTextViewAttrs: AndesTextViewAttrs
    private val gestureDetector by lazy { createGestureDetector() }
    /**
     * This value indicates if the click performed over the component was made over a linked section
     * or not.
     */
    internal var shouldCallOnClickListener = true

    /**
     * Private var to helps accessibilityText to know when this.text has been change by a setText() call
     * out of append() functions.
     */
    private var lastText = ""

    /**
     * get the accessibility text
     */
    var accessibilityText: String = ""
        get() {
            val strText = this.text.toString()
            // When this.text has changed out of append, a11yText changes as well with the this.text string content.
            return field.takeIf { lastText == strText } ?: strText.also {
                lastText = it
                field = it
            }
        }
        private set

    /**
     * Standard xml constructor.
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    /**
     * Standard code constructor.
     */
    constructor(
        context: Context,
        color: AndesTextViewColor = COLOR_DEFAULT,
        style: AndesTextViewStyle = STYLE_DEFAULT
    ) : super(context) {
        andesTextViewAttrs = AndesTextViewAttrs(color, style)
        setupComponents(createConfig())
    }

    /**
     * Responsible for setting up all properties of each component that is part of this andesTextView.
     * Is like a choreographer 😉
     */
    private fun setupComponents(config: AndesTextViewConfiguration) {
        setupViewId()
        setupColor(config)
        setupSize(config)
        setupFont(config)
        setupText(config)
        setupLineHeight(config)
        setupA11yDelegate()
        setupTouchListener()
    }

    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    private fun setupColor(config: AndesTextViewConfiguration) {
        setTextColor(config.color)
    }

    private fun setupSize(config: AndesTextViewConfiguration) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, config.textSize)
    }

    private fun setupFont(config: AndesTextViewConfiguration) {
        typeface = config.textFont
    }

    private fun setupText(config: AndesTextViewConfiguration) {
        movementMethod = LinkMovementMethod.getInstance()
        text = config.spannedText
    }

    private fun setupLineHeight(config: AndesTextViewConfiguration) {
        lineHeight = config.lineHeight
    }

    /**
     * Setter for the textColor.
     */
    fun setTextColor(color: AndesTextViewColor) {
        andesTextViewAttrs = andesTextViewAttrs.copy(andesTextViewColor = color)
        setupColor(createConfig())
    }

    /**
     * Sets the custom a11yDelegate.
     */
    private fun setupA11yDelegate() {
        accessibilityDelegate = AndesTextViewAccessibilityDelegate(this)
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
        return GestureDetector(
            context,
            object : GestureDetector.OnGestureListener {
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
            }
        )
    }

    /**
     * Sets the custom gesture detector previously created as the onTouchListener.
     */
    private fun setupTouchListener() {
        val onTouchListener = OnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }
        setOnTouchListener(onTouchListener)
    }

    /**
     * We add this check to be able to differ if we need to call the onClickListener
     * when a click action is performed over this component. When the click is performed
     * over a link section, we should not call the listener, but the [bodyLinks] listener instead.
     */
    override fun performClick(): Boolean {
        if (shouldCallOnClickListener) {
            super.performClick()
        }
        shouldCallOnClickListener = true
        return hasOnClickListeners()
    }

    /**
     * Getter for textColor. Only for internal purposes
     */
    internal fun getTextColor() = andesTextViewAttrs.andesTextViewColor

    private fun createConfig() =
        AndesTextViewConfigurationFactory.create(context, andesTextViewAttrs, text)

    /**
     * Appends a money amount text to the current text.
     * @param moneyAmountInfo AndesMoneyAmount that can provides text data
     * @param color AndesColor must applied to the styled text.
     */
    fun append(moneyAmount: AndesMoneyAmount, color: AndesColor) {
        append(moneyAmount, color.colorInt(context))
    }

    /**
     * Updates the color of a substring.
     * @param substring String where color must be changed.
     * @param color Int color code.
     */
    fun updateColor(substring: String, @ColorInt color: Int) {
        val spannedString = SpannableStringBuilder(text)
        spannedString.getRange(substring)?.let {
            updateColor(it, color)
        }
    }

    /**
     * Updates the color of a substring.
     * @param substring String where color must be changed.
     * @param color AndesColor with color code.
     */
    fun updateColor(substring: String, color: AndesColor) {
        updateColor(substring, color.colorInt(context))
    }

    /**
     * Updates the color of a string range.
     * @param substringRange  Range end inclusive where color must be changed.
     * @param color Int color code.
     */
    fun updateColor(range: IntRange, @ColorInt color: Int) {
        val spannedString = SpannableStringBuilder(text)
        if (spannedString.isNotValidRange(range)) {
            return
        }
        range.let { subRange ->
            spannedString.apply {
                val spanList = getSpans(subRange.first, subRange.last, ForegroundColorSpan::class.java)
                spanList.forEach {
                    removeSpan(it)
                }
                setSpan(
                    ForegroundColorSpan(color),
                    subRange.first,
                    subRange.last,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            text = spannedString
        }
    }

    /**
     * Updates the color of a substring.
     * @param substringRange Range end inclusive where color must be changed.
     * @param color AndesColor with color code.
     */
    fun updateColor(substringRange: IntRange, color: AndesColor) {
        updateColor(substringRange, color.colorInt(context))
    }

    fun clear() {
        text = SpannableString("")
        accessibilityText = ""
    }

    /**
     * Appends a text to the current text.
     * @param textToAdd String that can provides text data
     * @param color Int must applied to the styled text.
     */
    fun append(text: String, color: Int?) {
        val strText = this.text.toString()
        val combination = SpannableStringBuilder(this.text)
        val spannableText = SpannableStringBuilder(text).apply {
            setSpan(
                ForegroundColorSpan(color ?: andesTextViewAttrs.andesTextViewColor.color.getColor(context)),
                0,
                text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        accessibilityText = (
                strText.takeIf { accessibilityText.isEmpty() } ?: accessibilityText
                ) + text


        combination.append(spannableText)
        this.text = combination
        lastText = combination.toString()
        setupText(createConfig())
    }

    /**
     * Appends a money amount text to the current text.
     * @param moneyAmount AndesMoneyAmount that can provides text data
     * @param color color must applied to the styled text.
     */
    fun append(moneyAmount: AndesMoneyAmount, @ColorInt color: Int?) {
        andesTextViewAttrs = andesTextViewAttrs.copy(
            andesTextViewTextViewMoneyAmount = AndesTextViewMoneyAmount(moneyAmount, color)
        )

        val text = this.text.toString()

        accessibilityText = (
                text.takeIf { accessibilityText.isEmpty() } ?: accessibilityText
                ) + moneyAmount.contentDescription

        setupText(createConfig())
        lastText = this.text.toString()
    }

    /**
     * Appends a text to the current text.
     * @param textToAdd String that can provides text data
     * @param color AndesColor must applied to the styled text.
     */
    fun append(text: String, color: AndesColor) {
        append(text, color.colorInt(context))
    }

    companion object {
        private val COLOR_DEFAULT = AndesTextViewColor.Primary
        private val STYLE_DEFAULT = AndesTextViewStyle.BodyS
    }
}
