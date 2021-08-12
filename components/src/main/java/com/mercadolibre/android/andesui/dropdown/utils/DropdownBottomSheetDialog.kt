package com.mercadolibre.android.andesui.dropdown.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.dropdown.accessibility.DropdownBottomSheetDialogAccessibilityDelegate
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.utils.ScreenUtils
import com.mercadolibre.android.andesui.utils.getAccessibilityManager

@Suppress("TooManyFunctions")
internal class DropdownBottomSheetDialog(
    context: Context,
    theme: Int,
    private val andesListDelegate: AndesListDelegate
) : BottomSheetDialog(context, theme) {
    internal var containerView: ConstraintLayout? = null; private set
    private var dragIndicator: View? = null
    internal var andesList: AndesList? = null
    private var bottomSheet: FrameLayout? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private val a11yManager = context.getAccessibilityManager()

    companion object {
        private const val fullScreenHeight = ConstraintLayout.LayoutParams.MATCH_PARENT
        private val halfScreenHeight = ScreenUtils.getScreenHeight() / 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.andes_layout_dropdown_bottom_sheet)

        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        initComponents()
        configAndesList()
        initBottomSheetBehavior()
        resolveDragIndicator()
        resolveBottomSheetBackground()
        setA11yDelegate()
        setContainerHeight()
    }

    private fun initComponents() {
        andesList = findViewById(R.id.andesListDropdown)
        dragIndicator = findViewById(R.id.andes_bottom_sheet_drag_indicator)
        containerView = findViewById(R.id.andes_dropdown_bottom_sheet_container)
        bottomSheet = findViewById(com.google.android.material.R.id.design_bottom_sheet)
    }

    private fun configAndesList() {
        andesList?.delegate = andesListDelegate
    }

    private fun setA11yDelegate() {
        bottomSheet?.let {
            it.accessibilityDelegate = DropdownBottomSheetDialogAccessibilityDelegate(
                bottomSheetBehavior
            )
        }
    }

    private fun resolveDragIndicator() {
        val cornerRadius = context.resources.getDimension(R.dimen.andes_bottom_sheet_drag_indicator_corner_radius)
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(ResourcesCompat.getColor(context.resources, R.color.andes_gray_250, context.theme))
        shape.cornerRadius = cornerRadius

        dragIndicator?.background = shape
    }

    private fun resolveBottomSheetBackground() {
        val cornerRadius = context.resources.getDimension(R.dimen.andes_bottom_sheet_default_radius)
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0f, 0f, 0f, 0f)
        shape.setColor(ResourcesCompat.getColor(context.resources, R.color.andes_white, context.theme))

        containerView?.background = shape
    }

    /**
     * Add (if needed) a callback to handle the bottomsheet actions, and reset the bottomsheet
     * container height according to the talkback state
     */
    private fun setContainerHeight() {
        if (a11yManager.isEnabled) {
            setBottomSheetCallback()
            setBottomSheetContainerHeight(halfScreenHeight)
        } else {
            setBottomSheetContainerHeight(fullScreenHeight)
        }
    }

    /**
     * this method resets the bottomsheet container height. We need this method for this two cases:
     *
     * -If the talkback is enabled, when the bottomsheet has state HALF_EXPANDED (default state) the
     * container height should be half the screen size. Otherwise, the recyclerView from the internal
     * list will incorrectly set part of the still invisible items as "visible", causing the navigation
     * by elements to misfunction.
     *
     * -If the talkback is disabled, the container height should be full screen size. This way, the
     * animations will work correctly and the user will be able to expand/half-expand/dismiss the
     * bottomsheet as he wants.
     */
    private fun setBottomSheetContainerHeight(newHeight: Int) {
        containerView?.let { containerView ->
            val containerParams = containerView.layoutParams
            containerParams.height = newHeight
            containerView.layoutParams = containerParams
            containerView.requestLayout()
        }
    }

    /**
     * this method is needed to set the callback in which the bottomsheet will receive the
     * accessibility events the user may perform over the component.
     */
    private fun setBottomSheetCallback() {
        bottomSheetBehavior.setBottomSheetCallback(createBottomSheetCallback())
    }

    private fun initBottomSheetBehavior() {
        bottomSheet?.let {
            bottomSheetBehavior = BottomSheetBehavior.from(it)
            bottomSheetBehavior.peekHeight = halfScreenHeight
        }
    }

    /**
     * Method needed to create a callback used to handle the available actions for the bottomsheet
     * when the user has the talkback enabled.
     *
     * This actions can be "Expand", in which case we need to set the container height to MATCH_PARENT,
     * or "Dismiss", in which case we need to call the dismiss() method.
     */
    private fun createBottomSheetCallback(): BottomSheetBehavior.BottomSheetCallback {
        return object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        setBottomSheetContainerHeight(fullScreenHeight)
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        setBottomSheetContainerHeight(halfScreenHeight)
                        dismiss()
                    }
                    else -> {
                        // no-op
                    }
                }
            }

            override fun onSlide(p0: View, slideOffset: Float) {
                // no-op
            }
        }
    }
}
