package com.mercadolibre.android.andesui.snackbar

import android.app.Activity
import android.content.Context
import android.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import androidx.cardview.widget.CardView
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.snackbar.action.AndesSnackbarAction
import com.mercadolibre.android.andesui.snackbar.duration.AndesSnackbarDuration
import com.mercadolibre.android.andesui.snackbar.factory.AndesSnackbarAttrs
import com.mercadolibre.android.andesui.snackbar.factory.AndesSnackbarConfiguration
import com.mercadolibre.android.andesui.snackbar.factory.AndesSnackbarConfigurationFactory
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarType
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBold
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle

@Suppress("TooManyFunctions")
class AndesSnackbar : CardView {

    /**
     * Getter and setter for [type].
     */
    var type: AndesSnackbarType
        get() = andesSnackbarAttrs.andesSnackbarType
        set(value) {
            andesSnackbarAttrs.andesSnackbarType = value
            setupBackgroundComponents(createConfig())
        }

    /**
     * Getter and setter for [text].
     */
    var text: String
        get() = andesSnackbarAttrs.andesSnackbarText
        set(value) {
            andesSnackbarAttrs.andesSnackbarText = value
            setupMessageComponent(createConfig())
        }

    /**
     * Getter and setter for [duration].
     */
    var duration: AndesSnackbarDuration
        get() = andesSnackbarAttrs.andesSnackbarDuration
        set(value) {
            andesSnackbarAttrs.andesSnackbarDuration = value
            setupDurationComponent()
        }

    /**
     * Getter and setter for [action].
     */
    var action: AndesSnackbarAction?
        get() = andesSnackbarAttrs.andesSnackbarAction
        set(value) {
            andesSnackbarAttrs.andesSnackbarAction = value
            setupActionComponent()
        }

    /**
     * Getter and setter for [errorCode].
     */
    var errorCode: String?
        get() = andesSnackbarAttrs.andesSnackbarErrorCode
        set(value) {
            andesSnackbarAttrs.andesSnackbarErrorCode = value
            type = AndesSnackbarType.ERROR
            setupErrorCodeComponent(createConfig())
        }

    /**
     * Show the snackbar.
     */
    fun show() {
        if (this::snackbar.isInitialized) {
            snackbar.show()
        }
    }

    /**
     * Dismiss the snackbar.
     */
    fun dismiss() {
        if (isShown) {
            snackbar.dismiss()
        }
    }

    /**
     * Returns whether this {@link AndesSnackbar} is currently being shown.
     *
     * @return true if the snackbar is being shown, false otherwise
     */
    override fun isShown(): Boolean {
        if (this::snackbar.isInitialized) {
            return snackbar.isShown
        }
        return false
    }

    private lateinit var andesSnackbarAttrs: AndesSnackbarAttrs
    private lateinit var snackbar: Snackbar
    private lateinit var view: View

    @Suppress("unused", "LongParameterList")
    private constructor(context: Context) : super(context) {
        throw IllegalStateException("Constructor without parameters in Andes Snackbar is not allowed. You must provide some attributes.")
    }


    @Suppress("LongParameterList")
    constructor(
        context: Context,
        view: View,
        errorCode: String,
        text: String,
        duration: AndesSnackbarDuration,
        action: AndesSnackbarAction? = null
    ) : super(context) {
        initAttrs(
            view,
            AndesSnackbarType.ERROR,
            text,
            duration,
            action,
            errorCode
        )
    }

    @Suppress("unused")
    constructor(
        context: Context,
        view: View,
        type: AndesSnackbarType,
        text: String,
        duration: AndesSnackbarDuration
    ) : super(context) {
        initAttrs(view, type, text, duration)
    }

    @Suppress("unused", "LongParameterList")
    constructor(
        context: Context,
        view: View,
        type: AndesSnackbarType,
        text: String,
        duration: AndesSnackbarDuration,
        action: AndesSnackbarAction
    ) : super(context) {
        initAttrs(view, type, text, duration, action)
    }

    @Suppress("LongParameterList")
    private fun initAttrs(
        view: View,
        type: AndesSnackbarType,
        text: String,
        duration: AndesSnackbarDuration,
        action: AndesSnackbarAction? = null,
        errorCode: String? = null
    ) {
        andesSnackbarAttrs = AndesSnackbarAttrs(type, text, duration, action, errorCode)
        val config = AndesSnackbarConfigurationFactory.create(context, view, andesSnackbarAttrs)
        setupComponents(config)
    }

    /**
     * Responsible for setting up all properties of each component that is part of this snackbar.
     * Is like a choreographer ;)
     */
    private fun setupComponents(config: AndesSnackbarConfiguration) {
        initComponents(config)
        setupViewId()

        setupBackgroundComponents(config)
        setupMessageComponent(config)
        setupErrorCodeComponent(config)
        setupDurationComponent()
        setupActionComponent()
    }

    /**
     * Creates all the views that are part of this snackbar.
     * After a view is created then a view id is added to it.
     */
    private fun initComponents(config: AndesSnackbarConfiguration) {
        view = config.view
        snackbar = Snackbar.make(
            view,
            andesSnackbarAttrs.andesSnackbarText,
            andesSnackbarAttrs.andesSnackbarDuration.duration.duration()
        )
    }

    /**
     * Sets a view id to this snackbar.
     */
    private fun setupViewId() {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    /**
     * Gets data from the config and sets to the backroung component of this snackbar.
     */
    private fun setupBackgroundComponents(config: AndesSnackbarConfiguration) {
        cardElevation = 0f
        radius = context.resources.getDimension(R.dimen.andes_snackbar_radius)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)

        val andesSnackbar =
            LayoutInflater.from(context).inflate(R.layout.andes_layout_snackbar, this)
        val snackContainer = andesSnackbar.findViewById<ConstraintLayout>(R.id.snack_constraint)
        snackContainer.setBackgroundColor(config.backgroundColor.colorInt(context))

        val layout = snackbar.view as Snackbar.SnackbarLayout
        layout.removeAllViews()
        layout.addView(andesSnackbar)

        layout.setPadding(config.marginLeft, 0, config.marginRight, config.marginBottom)
    }

    /**
     * Gets data from the config and sets to the message of this snackbar.
     */
    private fun setupMessageComponent(config: AndesSnackbarConfiguration) {
        val layout = snackbar.view as Snackbar.SnackbarLayout
        val title = layout.findViewById<TextView>(R.id.tv_message)
        val snackConstraint = layout.findViewById<ConstraintLayout>(R.id.snack_constraint)
        title.text = andesSnackbarAttrs.andesSnackbarText
        title.typeface = context.getFontOrDefault(R.font.andes_font_regular)
        title.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            context.resources.getDimension(R.dimen.andes_snackbar_text)
        )
        title.setTextColor(config.textColor.colorInt(context))

        snackConstraint.setPadding(
            0,
            context.resources.getDimension(R.dimen.andes_snackbar_padding).toInt(),
            0,
            context.resources.getDimension(R.dimen.andes_snackbar_padding).toInt()
        )
    }

    /**
     * Gets data from the config and sets to the action of this snackbar.
     */
    private fun setupActionComponent() {
        if (andesSnackbarAttrs.andesSnackbarAction != null) {
            val layout = snackbar.view as Snackbar.SnackbarLayout
            val bottomButton = layout.findViewById<TextView>(R.id.bottomButton)
            val rightButton = layout.findViewById<TextView>(R.id.rightButton)
            val title = layout.findViewById<TextView>(R.id.tv_message)

            rightButton.text = andesSnackbarAttrs.andesSnackbarAction!!.text
            rightButton.typeface = context.getFontOrDefault(R.font.andes_font_semibold)

            bottomButton.text = andesSnackbarAttrs.andesSnackbarAction!!.text
            bottomButton.typeface = context.getFontOrDefault(R.font.andes_font_semibold)

            title.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
            rightButton.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
            val widthText = title.measuredWidth
            val widthTextAction = rightButton.measuredWidth

            val freeSpace = calculateFreeSpace(widthText)
            if (freeSpace > widthTextAction) {
                rightButton.visibility = View.VISIBLE
                rightButton.setOnClickListener {
                    andesSnackbarAttrs.andesSnackbarAction?.callback?.onClick(it)
                    snackbar.dismiss()
                }
            } else {
                bottomButton.visibility = View.VISIBLE
                bottomButton.setOnClickListener {
                    andesSnackbarAttrs.andesSnackbarAction?.callback?.onClick(it)
                    snackbar.dismiss()
                }
            }
        }
    }

    /**
     * Gets data from the config and sets to the duration of this snackbar.
     */
    private fun setupDurationComponent() {
        if (this::snackbar.isInitialized) {
            snackbar.duration = andesSnackbarAttrs.andesSnackbarDuration.duration.duration()
        }
    }

    /**
     * Gets data from the config and sets to the errorCode of this snackbar.
     */
    private fun setupErrorCodeComponent(config: AndesSnackbarConfiguration) {
        val layout = snackbar.view as Snackbar.SnackbarLayout
        val errorCode = layout.findViewById<AndesTextView>(R.id.error_code)
        if (!andesSnackbarAttrs.andesSnackbarErrorCode.isNullOrEmpty()) {
            errorCode.style = AndesTextViewStyle.BodyXs

            val codeText = layout.resources.getString(R.string.andes_snackbar_code)
            val codeValue = andesSnackbarAttrs.andesSnackbarErrorCode.orEmpty()

            errorCode.text = codeText.plus(codeValue)
            val andesBodyBold = AndesBodyBold(codeText.count() - 1, errorCode.text.count())
            errorCode.bodyBolds = AndesBodyBolds(listOf(andesBodyBold))

            errorCode.setTextColor(config.textColor.colorInt(context))
            errorCode.visibility = View.VISIBLE
        } else {
            errorCode.visibility = View.GONE
        }
    }

    /**
     * Gets the screen resolution and returns the width.
     */
    private fun getFullScreenWidth(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    /**
     * Gets the width of the screen and subtracts the width of
     * the text, paddings and margins to calculate free space.
     */
    private fun calculateFreeSpace(widthText: Int): Int {
        var freeSpace = getFullScreenWidth()
        freeSpace -= resources.getDimensionPixelSize(R.dimen.andes_snackbar_left_margin) // Left margin
        freeSpace -= resources.getDimensionPixelSize(R.dimen.andes_snackbar_right_margin) // Right margin
        freeSpace -= ANDES_SNACKBAR_PADDING * resources.getDimensionPixelSize(R.dimen.andes_snackbar_padding) // Paddings
        freeSpace -= widthText // Space occupied by the text
        return freeSpace
    }

    private fun createConfig() =
        AndesSnackbarConfigurationFactory.create(context, view, andesSnackbarAttrs)

    companion object {
        const val ANDES_SNACKBAR_PADDING = 3
    }
}
