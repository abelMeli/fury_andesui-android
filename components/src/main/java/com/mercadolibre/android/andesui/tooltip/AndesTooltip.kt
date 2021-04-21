package com.mercadolibre.android.andesui.tooltip

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import com.facebook.drawee.view.SimpleDraweeView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipAction
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipLinkAction
import com.mercadolibre.android.andesui.tooltip.extensions.displaySize
import com.mercadolibre.android.andesui.tooltip.extensions.isFinishing
import com.mercadolibre.android.andesui.tooltip.extensions.visible
import com.mercadolibre.android.andesui.tooltip.factory.AndesTooltipAttrs
import com.mercadolibre.android.andesui.tooltip.factory.AndesTooltipConfiguration
import com.mercadolibre.android.andesui.tooltip.factory.AndesTooltipConfigurationFactory
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocation
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocationConfig
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocationInterface
import com.mercadolibre.android.andesui.tooltip.location.getAndesTooltipLocationConfig
import com.mercadolibre.android.andesui.tooltip.radius.RadiusLayout
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipStyle
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

@Suppress("TooManyFunctions")
class AndesTooltip(val context: Context) : AndesTooltipLocationInterface {

    /**
     * Getter and setter for [title].
     */
    var title: String?
        get() = andesTooltipAttrs.title
        set(value) {
            andesTooltipAttrs = andesTooltipAttrs.copy(title = value)
            setupComponents(createConfig(andesTooltipAttrs), andesTooltipLocationConfigRequired)
        }

    /**
     * Getter and setter for [body].
     */
    var body: String
        get() = andesTooltipAttrs.body
        set(value) {
            andesTooltipAttrs = andesTooltipAttrs.copy(body = value)
            setupComponents(createConfig(andesTooltipAttrs), andesTooltipLocationConfigRequired)
        }

    /**
     * Getter and setter for [isDismissible].
     */
    var isDismissible: Boolean
        get() = andesTooltipAttrs.isDismissible
        set(value) {
            andesTooltipAttrs = andesTooltipAttrs.copy(isDismissible = value)
            setupComponents(createConfig(andesTooltipAttrs), andesTooltipLocationConfigRequired)
        }

    /**
     * Getter and setter for [style].
     */
    var style: AndesTooltipStyle
        get() = andesTooltipAttrs.style
        set(value) {
            andesTooltipAttrs = andesTooltipAttrs.copy(style = value)
            setupComponents(createConfig(andesTooltipAttrs), andesTooltipLocationConfigRequired)
        }

    /**
     * Getter and setter for [mainAction].
     */
    var mainAction: AndesTooltipAction?
        get() = andesTooltipAttrs.mainAction
        set(value) {
            value?.let {
                andesTooltipAttrs = andesTooltipAttrs.copy(mainAction = it, secondaryAction = null, linkAction = null)
                setupComponents(createConfig(andesTooltipAttrs), andesTooltipLocationConfigRequired)
            }
        }

    /**
     * Getter and setter for [secondaryAction].
     */
    var secondaryAction: AndesTooltipAction?
        get() = andesTooltipAttrs.secondaryAction
        set(value) {
            value?.let {
                if (andesTooltipAttrs.mainAction != null) {
                    andesTooltipAttrs = andesTooltipAttrs.copy(secondaryAction = it, linkAction = null)
                    setupComponents(createConfig(andesTooltipAttrs), andesTooltipLocationConfigRequired)
                }
            }
        }

    /**
     * Getter and setter for [linkAction].
     */
    var linkAction: AndesTooltipLinkAction?
        get() = andesTooltipAttrs.linkAction
        set(value) {
            value?.let {
                andesTooltipAttrs = andesTooltipAttrs.copy(mainAction = null, secondaryAction = null, linkAction = it)
                setupComponents(createConfig(andesTooltipAttrs), andesTooltipLocationConfigRequired)
            }
        }

    /**
     * Getter and setter for [location].
     */
    var location: AndesTooltipLocation?
        get() = andesTooltipAttrs.tooltipLocation
        set(value) {
            value?.let {
                andesTooltipAttrs = andesTooltipAttrs.copy(tooltipLocation = it)
                setupComponents(createConfig(andesTooltipAttrs), andesTooltipLocationConfigRequired)
            }
        }

    private lateinit var andesTooltipAttrs: AndesTooltipAttrs
    private lateinit var andesTooltipLocationConfigRequired: AndesTooltipLocationConfig
    override lateinit var radiusLayout: RadiusLayout
    override lateinit var frameLayoutContainer: FrameLayout

    private lateinit var constraintContainer: ConstraintLayout
    private lateinit var titleComponent: TextView
    private lateinit var bodyComponent: TextView
    private lateinit var dismissComponent: SimpleDraweeView
    private lateinit var primaryActionComponent: AndesButton
    private lateinit var secondaryActionComponent: AndesButton
    private lateinit var linkActionComponent: TextView
    private lateinit var arrowComponent: AppCompatImageView

    private val bodyWindow: PopupWindow
    private var isShowing = false

    override val bodyWindowHeight: Int
        get() = bodyWindow.height

    override val bodyWindowWidth: Int
        get() = bodyWindow.width

    override val displaySizeX: Int
        get() = context.displaySize().x

    override val displaySizeY: Int
        get() = context.displaySize().y

    override val tooltipMeasuredWidth: Int
        get() = container.measuredWidth

    override val tooltipMeasuredHeight: Int
        get() = container.measuredHeight

    override val paddingWithArrow: Int
        get() = context.resources.getDimensionPixelOffset(R.dimen.andes_tooltip_padding_with_arrow)

    override val arrowImageInnerPadding: Int
        get() = context.resources.getDimensionPixelOffset(R.dimen.andes_tooltip_arrow_inner_margin)

    override val arrowWidth: Int
        get() = context.resources.getDimensionPixelOffset(R.dimen.andes_tooltip_arrow_width)

    override val arrowHeight: Int
        get() = context.resources.getDimensionPixelOffset(R.dimen.andes_tooltip_arrow_height)

    override val arrowBorder: Int
        get() = context.resources.getDimensionPixelOffset(R.dimen.andes_tooltip_arrow_border)

    override val elevation: Int
        get() = context.resources.getDimensionPixelOffset(R.dimen.andes_tooltip_elevation)

    @SuppressLint("InflateParams")
    private var container: ViewGroup = LayoutInflater.from(context).inflate(R.layout.andes_layout_tooltip, null) as ViewGroup

    init {
        bodyWindow = PopupWindow(
                container,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        )
        adjustFitsSystemWindows(container)
    }

    /**
     * Public constructor for Andes tooltip with required body and main AndesButton.
     */
    @Suppress("LongParameterList")
    @JvmOverloads
    constructor(
        context: Context,
        style: AndesTooltipStyle = STYLE_DEFAULT,
        title: String? = TITLE_DEFAULT,
        body: String,
        isDismissible: Boolean = IS_DISMISSIBLE_DEFAULT,
        tooltipLocation: AndesTooltipLocation = TIP_ORIENTATION_DEFAULT,
        mainAction: AndesTooltipAction,
        secondaryAction: AndesTooltipAction? = SECONDARY_ACTION_DEFAULT
    ) : this(context) {
        andesTooltipAttrs = AndesTooltipAttrs(
                style = style,
                title = title,
                body = body,
                isDismissible = isDismissible,
                tooltipLocation = tooltipLocation,
                mainAction = mainAction,
                secondaryAction = secondaryAction
        )
        initComponents(andesTooltipAttrs)
    }

    /**
     * Public constructor for Andes tooltip with required body.
     */
    @Suppress("LongParameterList")
    @JvmOverloads
    constructor(
        context: Context,
        style: AndesTooltipStyle = STYLE_DEFAULT,
        title: String? = TITLE_DEFAULT,
        body: String,
        isDismissible: Boolean = IS_DISMISSIBLE_DEFAULT,
        tooltipLocation: AndesTooltipLocation = TIP_ORIENTATION_DEFAULT,
        linkAction: AndesTooltipLinkAction? = LINK_ACTION_DEFAULT

    ) : this(context) {
        andesTooltipAttrs = AndesTooltipAttrs(
                style = style,
                title = title,
                body = body,
                isDismissible = isDismissible,
                tooltipLocation = tooltipLocation,
                linkAction = linkAction
        )
        initComponents(andesTooltipAttrs)
    }

    internal fun canShowTooltip(target: View) =
            !isShowing && !context.isFinishing() && ViewCompat.isAttachedToWindow(target)

    /**
     * After AndesTooltip is built, this show(target: View) method will present the tooltip on the screen.
     *
     * @param target view used as anchor for AndesTooltip. It is the view that is going to be referenced
     */
    @MainThread
    fun show(target: View) {
        if (canShowTooltip(target)) {
            target.post {
                container.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                bodyWindow.width = tooltipMeasuredWidth
                bodyWindow.height = tooltipMeasuredHeight
                constraintContainer.layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                )

                if (!andesTooltipLocationConfigRequired.canBuildTooltipInRequiredLocation(target)) {
                    andesTooltipLocationConfigRequired.iterateOtherLocations(target)
                }
            }
        }
    }

    /**
     * After AndesTooltip is shown, this dismiss() method will dismiss the tooltip on the screen.
     */
    fun dismiss() {
        if (isShowing) {
            isShowing = false
            bodyWindow.dismiss()
        }
    }

    /**
     * OnDismissListener for tooltip
     * @param callback void method with no params which will be invoked when the tooltip is dismissed.*/
    @Suppress("unused")
    fun setOnAndesTooltipDismissListener(callback: (() -> Unit)? = null) {
        bodyWindow.setOnDismissListener {
            this@AndesTooltip.dismiss()
            callback?.invoke()
        }
    }

    private fun initComponents(attrs: AndesTooltipAttrs) {
        radiusLayout = container.findViewById(R.id.andes_tooltip_radio_layout)
        frameLayoutContainer = container.findViewById(R.id.andes_tooltip_content)
        constraintContainer = container.findViewById(R.id.andes_tooltip_container)
        titleComponent = container.findViewById(R.id.andes_tooltip_title)
        bodyComponent = container.findViewById(R.id.andes_tooltip_body)
        dismissComponent = container.findViewById(R.id.andes_tooltip_dismiss)
        primaryActionComponent = container.findViewById(R.id.andes_tooltip_primary_action)
        secondaryActionComponent = container.findViewById(R.id.andes_tooltip_secondary_action)
        linkActionComponent = container.findViewById(R.id.andes_tooltip_link_action)
        arrowComponent = container.findViewById(R.id.andes_tooltip_arrow)

        setupComponents(createConfig(attrs), andesTooltipLocationConfigRequired)
    }

    private fun createConfig(attrs: AndesTooltipAttrs): AndesTooltipConfiguration {
        val config = AndesTooltipConfigurationFactory.create(context, attrs)
        andesTooltipLocationConfigRequired = getAndesTooltipLocationConfig(this, attrs.tooltipLocation)
        return config
    }

    private fun adjustFitsSystemWindows(parent: ViewGroup) {
        parent.fitsSystemWindows = false
        (0 until parent.childCount).map { parent.getChildAt(it) }.forEach { child ->
            child.fitsSystemWindows = false
            if (child is ViewGroup) {
                adjustFitsSystemWindows(child)
            }
        }
    }

    private fun setupComponents(config: AndesTooltipConfiguration, locationConfig: AndesTooltipLocationConfig) {
        initializeBackground(config)
        initializeAndesTooltipWindow()
        initializeAndesTooltipContent(config, locationConfig)
    }

    private fun initializeArrow(locationConfig: AndesTooltipLocationConfig, configuration: AndesTooltipConfiguration) {
        with(arrowComponent) {
            layoutParams = FrameLayout.LayoutParams(arrowWidth, arrowHeight)
            rotation = locationConfig.getArrowRotation()
            alpha = context.resources.getDimensionPixelOffset(R.dimen.andes_tooltip_alpha).toFloat()
            colorFilter = PorterDuffColorFilter(configuration.backgroundColor.colorInt(context), PorterDuff.Mode.SRC_IN)
            radiusLayout.post {
                ViewCompat.setElevation(this, this@AndesTooltip.elevation.toFloat())
                val arrowPoint = locationConfig.getArrowPoint()
                x = arrowPoint.x
                y = arrowPoint.y
            }
        }
    }

    private fun initializeBackground(config: AndesTooltipConfiguration) {
        with(radiusLayout) {
            alpha = context.resources.getDimensionPixelOffset(R.dimen.andes_tooltip_alpha).toFloat()
            ViewCompat.setElevation(this, this@AndesTooltip.elevation.toFloat())
            background = GradientDrawable().apply {
                setColor(config.backgroundColor.colorInt(context))
                cornerRadius = context.resources.getDimensionPixelOffset(R.dimen.andes_tooltip_corner_radius).toFloat()
            }
            if (!config.isDynamicWidth) {
                minimumWidth = context.resources.getDimensionPixelOffset(R.dimen.andes_tooltip_min_width_with_buttons)
            }
        }
    }

    private fun initializeAndesTooltipWindow() {
        with(bodyWindow) {
            isOutsideTouchable = true
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                elevation = this@AndesTooltip.elevation.toFloat()
            }
            isClippingEnabled = false
            setTouchInterceptor(
                    object : View.OnTouchListener {
                        @SuppressLint("ClickableViewAccessibility")
                        override fun onTouch(view: View, event: MotionEvent): Boolean {
                            if (event.action == MotionEvent.ACTION_OUTSIDE) {
                                this@AndesTooltip.dismiss()
                                return true
                            }
                            return false
                        }
                    }
            )
        }
    }

    private fun initializeAndesTooltipContent(config: AndesTooltipConfiguration, locationConfig: AndesTooltipLocationConfig) {
        with(frameLayoutContainer) {
            setPadding(paddingWithArrow, paddingWithArrow, paddingWithArrow, paddingWithArrow)
        }
        initTooltipTitle(config)
        initTooltipBody(config)
        initDismiss(config)
        initPrimaryAction(config)
        initSecondaryAction(config)
        initLinkAction(config)
    }

    private fun initTooltipTitle(config: AndesTooltipConfiguration) {
        with(titleComponent) {
            if (!config.titleText.isNullOrEmpty()) {
                text = config.titleText
                typeface = config.titleTypeface
                config.titleTextSize?.let { setTextSize(TypedValue.COMPLEX_UNIT_PX, it) }
                setTextColor(config.textColor.colorInt(context))
                visible(true)
            } else {
                visible(false)
            }
        }
    }

    private fun initTooltipBody(config: AndesTooltipConfiguration) {
        with(bodyComponent) {
            text = config.bodyText
            typeface = config.bodyTypeface
            setTextColor(config.textColor.colorInt(context))
            config.bodyTextSize?.let { setTextSize(TypedValue.COMPLEX_UNIT_PX, it) }
        }
    }

    private fun initDismiss(config: AndesTooltipConfiguration) {
        with(dismissComponent) {
            if (config.isDismissible) {
                setImageDrawable(config.dismissibleIcon)
                setOnClickListener { dismiss() }
                visible(true)
            } else {
                visible(false)
            }
        }
    }

    private fun initPrimaryAction(config: AndesTooltipConfiguration) {
        with(primaryActionComponent) {
            if (config.primaryAction != null) {
                text = config.primaryAction.label
                config.primaryAction.hierarchy?.let { hierarchy = it }
                config.primaryActionBackgroundColor?.let { primaryActionComponent.changeBackgroundColor(it) }
                config.primaryActionTextColor?.let { primaryActionComponent.changeTextColor(it.colorInt(context)) }
                setOnClickListener {
                    dismiss()
                    config.primaryAction.onActionClicked(it, this@AndesTooltip)
                }
                visible(true)
            } else {
                visible(false)
            }
        }
    }

    private fun initSecondaryAction(config: AndesTooltipConfiguration) {
        with(secondaryActionComponent) {
            if (config.secondaryAction != null) {
                text = config.secondaryAction.label
                config.secondaryAction.hierarchy?.let { hierarchy = it }
                config.secondaryActionBackgroundColor?.let { changeBackgroundColor(it) }
                config.secondaryActionTextColor?.let { changeTextColor(it.colorInt(context)) }
                setOnClickListener {
                    dismiss()
                    config.secondaryAction.onActionClicked(it, this@AndesTooltip)
                }
                visible(true)
            } else {
                visible(false)
            }
        }
    }

    private fun initLinkAction(config: AndesTooltipConfiguration) {
        with(linkActionComponent) {
            if (config.linkAction != null) {
                text = config.linkAction.label
                typeface = context.getFontOrDefault(R.font.andes_font_regular)
                config.linkActionTextColor?.let { setTextColor(it.colorInt(context)) }
                if (config.linkActionIsUnderlined) {
                    paintFlags = Paint.UNDERLINE_TEXT_FLAG
                }
                setOnClickListener {
                    dismiss()
                    config.linkAction.onActionClicked(it, this@AndesTooltip)
                }
                visible(true)
            } else {
                visible(false)
            }
        }
    }

    private fun applyAndesTooltipAnimation() {
        bodyWindow.animationStyle = R.style.Andes_FadeWindowAnimation
    }

    override fun showDropDown(target: View, xOff: Int, yOff: Int, locationConfig: AndesTooltipLocationConfig) {
        isShowing = true
        var attrs = andesTooltipAttrs

        bodyWindow.showAsDropDown(target, xOff, yOff)

        if (locationConfig.mLocation != andesTooltipLocationConfigRequired.mLocation) {
            attrs = andesTooltipAttrs.copy(tooltipLocation = locationConfig.mLocation)
        }

        val config = AndesTooltipConfigurationFactory.create(context, attrs)
        initializeArrow(locationConfig, config)
        setupComponents(config, locationConfig)
        applyAndesTooltipAnimation()
    }

    companion object {
        private val STYLE_DEFAULT = AndesTooltipStyle.LIGHT
        private val TIP_ORIENTATION_DEFAULT = AndesTooltipLocation.TOP
        private val TITLE_DEFAULT = null
        private val SECONDARY_ACTION_DEFAULT = null
        private val LINK_ACTION_DEFAULT = null
        private const val IS_DISMISSIBLE_DEFAULT = true
    }
}
