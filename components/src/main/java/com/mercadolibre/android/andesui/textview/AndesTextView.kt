package com.mercadolibre.android.andesui.textview

import android.content.Context
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.textview.accessibility.AndesTextViewAccessibilityDelegate
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.textview.factory.AndesTextViewAttrs
import com.mercadolibre.android.andesui.textview.factory.AndesTextViewAttrsParser
import com.mercadolibre.android.andesui.textview.factory.AndesTextViewConfiguration
import com.mercadolibre.android.andesui.textview.factory.AndesTextViewConfigurationFactory
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle
import com.mercadolibre.android.andesui.utils.getAccessibilityManager

@Suppress("TooManyFunctions")
class AndesTextView : AppCompatTextView {

    /**
     * Setter for the textColor
     */
    fun setTextColor(color: AndesTextViewColor) {
        andesTextViewAttrs = andesTextViewAttrs.copy(andesTextViewColor = color)
        setupColor(createConfig())
    }

    /**
     * Getter and setter for [style]
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
     * Getter and setter for [bodyLinks]
     */
    var bodyLinks: AndesBodyLinks?
        get() = andesTextViewAttrs.andesTextViewBodyLinks
        set(value) {
            andesTextViewAttrs = andesTextViewAttrs.copy(andesTextViewBodyLinks = value)
            setupText(createConfig())
            setupA11yDelegate()
        }

    /**
     * Getter and setter for [bodyBolds]
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
     * Standard xml constructor
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        andesTextViewAttrs = AndesTextViewAttrsParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    /**
     * Standard code constructor
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
        setTypeface(config.textFont, config.textStyle)
    }

    private fun setupText(config: AndesTextViewConfiguration) {
        movementMethod = LinkMovementMethod.getInstance()
        text = config.spannedText
    }

    private fun setupLineHeight(config: AndesTextViewConfiguration) {
        lineHeight = config.lineHeight
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
     * Sets the custom gesture detector previously created as the onTouchListener
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
     * This value indicates if the click performed over the component was made over a linked section
     * or not.
     */
    internal var shouldCallOnClickListener = true

    /**
     * Getter for textColor. Only for internal purposes
     */
    internal fun getTextColor() = andesTextViewAttrs.andesTextViewColor

    private fun createConfig() =
        AndesTextViewConfigurationFactory.create(context, andesTextViewAttrs, text)

    companion object {
        private val COLOR_DEFAULT = AndesTextViewColor.Primary
        private val STYLE_DEFAULT = AndesTextViewStyle.BodyS
    }
}
