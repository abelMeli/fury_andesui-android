package com.mercadolibre.android.andesui.bottomsheet

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.bottomsheet.factory.AndesBottomSheetAttrs
import com.mercadolibre.android.andesui.bottomsheet.factory.AndesBottomSheetAttrsParser
import com.mercadolibre.android.andesui.bottomsheet.factory.AndesBottomSheetConfiguration
import com.mercadolibre.android.andesui.bottomsheet.factory.AndesBottomSheetConfigurationFactory
import com.mercadolibre.android.andesui.bottomsheet.state.AndesBottomSheetContentMargin
import com.mercadolibre.android.andesui.bottomsheet.state.AndesBottomSheetState
import com.mercadolibre.android.andesui.bottomsheet.title.AndesBottomSheetTitleAlignment
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import kotlin.math.roundToInt

@Suppress("TooManyFunctions")
class AndesBottomSheet : CoordinatorLayout {

    /**
     * Getter and Setter fo [peekHeight] of the BottomSheet.
     * This is how much of the view is seen when collapsed in px.
     */
    var peekHeight: Int
        get() = andesBottomSheetAttrs.andesBottomSheetPeekHeight
        set(value) {
            andesBottomSheetAttrs = andesBottomSheetAttrs.copy(andesBottomSheetPeekHeight = value)
            updatePeekHeight(createConfig())
        }

    /**
     * Getter and Setter for [state]
     */
    var state: AndesBottomSheetState
        get() = andesBottomSheetAttrs.andesBottomSheetState
        set(value) {
            andesBottomSheetAttrs = andesBottomSheetAttrs.copy(andesBottomSheetState = value)
            resolveBottomSheetState(createConfig())
        }

    /**
     * Getter and Setter for [titleText], if this value is null or an empty string no title will be shown
     */
    var titleText: String?
        get() = andesBottomSheetAttrs.andesBottomSheetTitleText
        set(value) {
            andesBottomSheetAttrs = andesBottomSheetAttrs.copy(andesBottomSheetTitleText = value)
            resolveTitleViewText(createConfig())
        }

    /**
     * Getter and Setter for [titleAlignment], alternatives: centered and left_aligned
     */
    var titleAlignment: AndesBottomSheetTitleAlignment?
        get() = andesBottomSheetAttrs.andesBottomSheetTitleAlignment
        set(value) {
            andesBottomSheetAttrs = andesBottomSheetAttrs.copy(andesBottomSheetTitleAlignment = value)
            resolveTitleViewAlignment(createConfig())
    }

    /**
     * Getter and Setter for [fitContent], if this value is false will not fit the content
     */
    var fitContent: Boolean
        get() = andesBottomSheetAttrs.andesBottomSheetFitContent
        set(value) {
            andesBottomSheetAttrs = andesBottomSheetAttrs.copy(andesBottomSheetFitContent = value)
            resolveBottomSheetFitContent(createConfig())
        }

    private lateinit var andesBottomSheetAttrs: AndesBottomSheetAttrs
    private lateinit var containerView: FrameLayout
    private lateinit var dragIndicator: View
    private lateinit var frameView: FrameLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var titleTextView: TextView
    private lateinit var backgroundDimView: View
    private var listener: BottomSheetListener? = null

    @Suppress("LongParameterList")
    @JvmOverloads
    constructor(
        context: Context,
        peekHeight: Int = DEFAULT_PEEK_HEIGHT,
        state: AndesBottomSheetState = DEFAULT_BOTTOM_SHEET_STATE,
        title: String? = DEFAULT_TITLE,
        titleAlignment: AndesBottomSheetTitleAlignment = DEFAULT_TITLE_ALIGNMENT,
        fitContent: Boolean = DEFAULT_FIT_CONTENT
    ) : super(context) {
        initAttrs(peekHeight, state, title, titleAlignment, fitContent)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        andesBottomSheetAttrs = AndesBottomSheetAttrsParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    private fun initAttrs(
        peekHeight: Int,
        bottomSheetState: AndesBottomSheetState,
        titleText: String?,
        titleAlignment: AndesBottomSheetTitleAlignment,
        fitContent: Boolean
    ) {
        andesBottomSheetAttrs =
                AndesBottomSheetAttrs(
                    peekHeight,
                    bottomSheetState,
                    titleText,
                    titleAlignment,
                    fitContent
                )
        setupComponents(createConfig())
    }

    private fun createConfig() = AndesBottomSheetConfigurationFactory.create(andesBottomSheetAttrs)

    private fun setupComponents(config: AndesBottomSheetConfiguration) {
        initComponents()

        setupViewId()

        resolveDragIndicator()
        resolveBottomSheetParams()
        resolveBottomSheetBackground()
        initBottomSheetBehavior()
        resolveBottomSheetState(config)
        resolveBottomSheetFitContent(config)
        resolveTitleViewText(config)
        resolveTitleViewAlignment(config)
        resolveBackgroundDim()

        updatePeekHeight(config)
    }

    /**
     * Creates all the views which are part of this bottomSheet and sets them an Id.
     */
    private fun initComponents() {
        val layout = LayoutInflater.from(context).inflate(R.layout.andes_layout_bottom_sheet, this)
        containerView = layout.findViewById(R.id.andes_bottom_sheet_container)
        dragIndicator = layout.findViewById(R.id.andes_bottom_sheet_drag_indicator)
        frameView = layout.findViewById(R.id.andes_bottom_sheet_frame_view)
        titleTextView = layout.findViewById(R.id.andes_bottom_sheet_title)
        backgroundDimView = layout.findViewById(R.id.andes_bottom_sheet_background_dim)
    }

    /**
     * Sets a view id to this bottomSheet.
     *
     */
    private fun setupViewId() {
        if (id == ConstraintLayout.NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    private fun resolveDragIndicator() {
        val cornerRadius = context.resources.getDimension(R.dimen.andes_bottom_sheet_drag_indicator_corner_radius)
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(ResourcesCompat.getColor(resources, R.color.andes_gray_250, context.theme))
        shape.cornerRadius = cornerRadius

        dragIndicator.background = shape
    }

    private fun resolveBottomSheetParams() {
        val params = containerView.layoutParams as LayoutParams
        params.behavior = BottomSheetBehavior<FrameLayout>()
    }

    private fun resolveBottomSheetBackground() {
        val cornerRadius = context.resources.getDimension(R.dimen.andes_bottom_sheet_default_radius)
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0f, 0f, 0f, 0f)
        shape.setColor(ResourcesCompat.getColor(resources, R.color.andes_white, context.theme))

        containerView.background = shape
    }

    private fun initBottomSheetBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(containerView)
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetBehaviorCallback)
    }

    private fun resolveBottomSheetState(config: AndesBottomSheetConfiguration) {
        // This .post grant the full initialization of the sheet before setting the state
        containerView.post {
            bottomSheetBehavior.state = config.state.state.getState()
        }
    }

    private fun resolveBottomSheetFitContent(config: AndesBottomSheetConfiguration) {
        isFitContent(config.fitContent)
    }

    private fun resolveTitleViewText(config: AndesBottomSheetConfiguration) {
        if (config.titleText.isNullOrEmpty()) {
            titleTextView.visibility = View.GONE
            return
        }

        titleTextView.visibility = View.VISIBLE
        titleTextView.text = config.titleText
        titleTextView.typeface = context.getFontOrDefault(R.font.andes_font_semibold)
    }

    private fun resolveTitleViewAlignment(config: AndesBottomSheetConfiguration) {
        if (config.titleAlignment != null) {
            titleTextView.gravity = config.titleAlignment.alignment.getAlignment()
        }
    }

    private fun updatePeekHeight(config: AndesBottomSheetConfiguration) {
        bottomSheetBehavior.peekHeight = config.peekHeight
    }

    private fun resolveBackgroundDim() {
        backgroundDimView.setOnClickListener {
            listener?.onTouchOutside()
            collapse()
        }
        if (state == AndesBottomSheetState.EXPANDED || state == AndesBottomSheetState.HALF_EXPANDED) {
            backgroundDimView.visibility = View.VISIBLE
            backgroundDimView.alpha = DIM_MAX_ALPHA
        }
    }

    /**
     * Sets the provided fragment inside of the bottomSheet
     *
     * @param fragmentManager the supportFragmentManager
     * @param fragment the fragment to be shown, should be
     * @param bundle the info bundle for the fragment
     */
    fun <T> setContent(fragmentManager: FragmentManager, fragment: T, bundle: Bundle? = null)
            where T : Fragment {
        if (bundle != null) {
            fragment.arguments = bundle
        }
        fragmentManager
                .beginTransaction()
                .replace(frameView.id, fragment)
                .commit()
    }

    /**
     * Set margin in content view
     * @param contentMargin this could be AndesBottomSheetContentMargin.DEFAULT and you will have
     * horizontal margins in content. And also this could be AndesBottomSheetContentMargin.NO_HORIZONTAL_MARGINS
     * and you wont have horizontal margins.
     */
    fun setContentMargin(contentMargin: AndesBottomSheetContentMargin) {
        val params = frameView.layoutParams as MarginLayoutParams
        val margins = contentMargin.margins.getMargins(resources)
        params.setMargins(margins.leftMils, margins.topMils, margins.rightMils, margins.bottomMils)
        frameView.layoutParams = params
    }

    /**
     * Sets a view inside the bottomSheet
     */
    fun setContent(view: View) {
        frameView.addView(view)
    }

    /**
     * Removes all views from FrameLayout inside of the bottomSheet
     */
    fun removeContent() {
        if (frameView.childCount > 0) {
            frameView.removeAllViews()
        }
    }

    /**
     * Fully expands this bottomSheet
     */
    fun expand() {
        state = AndesBottomSheetState.EXPANDED
    }

    /**
     * Half Expand this bottomSheet
     */
    fun halfExpand() {
        state = AndesBottomSheetState.HALF_EXPANDED
    }

    /**
     * Collapses this bottomSheet
     */
    fun collapse() {
        state = AndesBottomSheetState.COLLAPSED
    }

    /**
     * Enable/Disable fit content in bottom sheet
     * Whether the height of the expanded sheet is determined by the height of its contents,
     * or if it is expanded in two stages (half the height of the parent container,
     * full height of parent container).
     * This disable/enable half expanded behavior, if it is disabled, anyway you can set
     * half expanded state manually with [halfExpand] but maybe you wont have the result you want.
     */
    private fun isFitContent(fitContent: Boolean) {
        bottomSheetBehavior.isFitToContents = fitContent
        val contentParams = frameView.layoutParams
        val params = containerView.layoutParams
        params.width = LayoutParams.MATCH_PARENT
        if (fitContent) {
            // Best way to set layout with isFitToContent=true
            contentParams.height = LayoutParams.WRAP_CONTENT
            params.height = LayoutParams.WRAP_CONTENT
        } else {
            // Best way to set layout with isFitToContent=false
            // Height must be zero to limit correctly the layout.
            contentParams.height = CONTENT_LAYOUT_LIMIT
            params.height = LayoutParams.MATCH_PARENT
        }
        containerView.layoutParams = params
        frameView.layoutParams = contentParams
    }

    /**
     * Sets listener to the onCollapse and onExpand events.
     *
     * @param listener the events listener
     */
    fun setBottomSheetListener(listener: BottomSheetListener) {
        this.listener = listener
    }

    private val bottomSheetBehaviorCallback: BottomSheetBehavior.BottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_HIDDEN -> {
                    // not used
                }
                BottomSheetBehavior.STATE_EXPANDED -> {
                    listener?.onExpanded()
                    updateStateFromBehavior(newState)
                }
                BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    listener?.onHalfExpanded()
                    updateStateFromBehavior(newState)
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    listener?.onCollapsed()
                    updateStateFromBehavior(newState)
                }
                BottomSheetBehavior.STATE_DRAGGING -> {
                    // not used
                }
                BottomSheetBehavior.STATE_SETTLING -> {
                    // not used
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            if (backgroundDimView.visibility == View.GONE && slideOffset > MIN_OFFSET_CHANGE) {
                backgroundDimView.visibility = View.VISIBLE
            } else if (slideOffset == FLOAT_ZERO) {
                backgroundDimView.visibility = View.GONE
            }
            if (((slideOffset * ONE_HUNDRED).roundToInt() % TWO) == ZERO) {
                if (slideOffset < DIM_HALF_SHEET) {
                    backgroundDimView.alpha = slideOffset * TWO
                } else {
                    backgroundDimView.alpha = DIM_MAX_ALPHA
                }
            }
        }

        private fun updateStateFromBehavior(bottomSheetBehaviorState: Int) {
            when (bottomSheetBehaviorState) {
                BottomSheetBehavior.STATE_EXPANDED -> state = AndesBottomSheetState.EXPANDED
                BottomSheetBehavior.STATE_HALF_EXPANDED -> state = AndesBottomSheetState.HALF_EXPANDED
                BottomSheetBehavior.STATE_COLLAPSED -> state = AndesBottomSheetState.COLLAPSED
            }
        }
    }

    companion object {
        private const val DEFAULT_PEEK_HEIGHT = 0
        private const val DIM_MAX_ALPHA = 1f
        private const val DIM_HALF_SHEET = 0.5f
        private const val FLOAT_ZERO = 0f
        private const val ONE_HUNDRED = 100
        private const val TWO = 2
        private const val ZERO = 0
        private const val MIN_OFFSET_CHANGE = 0.01f
        private const val CONTENT_LAYOUT_LIMIT = 0
        private val DEFAULT_BOTTOM_SHEET_STATE = AndesBottomSheetState.COLLAPSED
        private val DEFAULT_TITLE = null // no title
        private const val DEFAULT_FIT_CONTENT = true // enabled
        private val DEFAULT_TITLE_ALIGNMENT = AndesBottomSheetTitleAlignment.CENTERED
    }
}
