package com.mercadolibre.android.andesui.demoapp.components.tooltip

import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.viewpager.widget.ViewPager
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicTooltipBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTooltipBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTooltipFocusableBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTooltipNotFocusableBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.demoapp.utils.setupAdapter
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.tooltip.AndesTooltip
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipAction
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipLinkAction
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocation
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipSize
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipStyle

@Suppress("TooManyFunctions")
class TooltipShowcaseActivity : BaseActivity() {

    private lateinit var andesTooltipToShow: AndesTooltip
    private lateinit var viewPager: CustomViewPager
    private var appBarTitle = ""
    private lateinit var notFocusablePage: TooltipNotFocusablePage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_tooltip)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf(
            AndesuiDynamicTooltipBinding.inflate(layoutInflater).root,
            AndesuiStaticTooltipBinding.inflate(layoutInflater).root,
            AndesuiStaticTooltipFocusableBinding.inflate(layoutInflater).root,
            AndesuiStaticTooltipNotFocusableBinding.inflate(layoutInflater).root
        ))
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit
            override fun onPageSelected(position: Int) {
                appBarTitle = when (position) {
                    0, 1 -> "Tooltip"
                    2 -> "Tooltip: Focusable"
                    3 -> "Tooltip: Not Focusable"
                    else -> "Tooltip"
                }
                updateAppBarTitle(appBarTitle)
                if (position == 3) {
                    notFocusablePage.showTooltip()
                } else {
                    notFocusablePage.dismissTooltip()
                }
            }
        })
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        addStaticPage(adapter.views[1])
        TooltipFocusablePage().create(adapter.views[2])
        notFocusablePage = TooltipNotFocusablePage()
        notFocusablePage.create(adapter.views[3])
    }

    @Suppress("LongMethod")
    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicTooltipBinding.bind(container)
        val checkboxDismiss = binding.dismissableCheckbox
        val checkboxFocusable = binding.focusableCheckbox
        val title = binding.titleText
        val body = binding.bodyText
        val primaryActionText = binding.primaryActionText
        val secondaryActionText = binding.secondaryActionText
        val linkActionText = binding.linkActionText
        val update = binding.changeButton
        val clear = binding.clearButton
        val tooltip = binding.andesTooltipButton
        val targetBias = binding.tooltipPosition

        val mainActionConfig = binding.mainActionConfig
        val secondaryActionConfig = binding.secondaryActionConfig

        val spinnerStyle = binding.styleSpinner
        val spinnerOrientation = binding.orientationSpinner
        val spinnerActionType = binding.actionTypeSpinner
        val spinnerSizeStyle = binding.sizeStyleSpinner
        val primaryActionSpinner = binding.primaryActionSpinner
        val spinnerSecondAction = binding.secondaryActionSpinner

        targetBias.text = getString(R.string.andes_tooltip_position_default)
        body.text = getString(R.string.andes_tooltip_message)
        checkboxDismiss.status = AndesCheckboxStatus.UNSELECTED

        spinnerStyle.setupAdapter(R.array.andes_tooltip_style_spinner) { position ->
            val hasDarkTooltipStyle = position == TOOLTIP_DARK_STYLE
            val spinnerTextArray = R.array.andes_dark_tooltip_sec_action_style_spinner
                    .takeIf { hasDarkTooltipStyle }
                    ?: R.array.andes_tooltip_sec_action_style_spinner
            spinnerSecondAction.setupAdapter(spinnerTextArray)
        }

        spinnerOrientation.setupAdapter(R.array.andes_tooltip_location_spinner)

        spinnerActionType.setupAdapter(R.array.andes_tooltip_action_type_spinner) { position ->
            when (position) {
                TOOLTIP_WITH_MAIN_ACTION -> {
                    runOnUiThread {
                        mainActionConfig.visibility = View.VISIBLE
                        secondaryActionConfig.visibility = View.GONE
                        linkActionText.visibility = View.GONE
                    }
                }
                TOOLTIP_WITH_MAIN_AND_SEC_ACTION -> {
                    runOnUiThread {
                        mainActionConfig.visibility = View.VISIBLE
                        secondaryActionConfig.visibility = View.VISIBLE
                        linkActionText.visibility = View.GONE
                    }
                }
                TOOLTIP_WITH_LINK_ACTION -> {
                    runOnUiThread {
                        mainActionConfig.visibility = View.GONE
                        secondaryActionConfig.visibility = View.GONE
                        linkActionText.visibility = View.VISIBLE
                    }
                }
                TOOLTIP_WITH_NO_ACTION -> {
                    runOnUiThread {
                        mainActionConfig.visibility = View.GONE
                        secondaryActionConfig.visibility = View.GONE
                        linkActionText.visibility = View.GONE
                    }
                }
            }
        }

        spinnerSizeStyle.setupAdapter(R.array.andes_tooltip_size_style_spinner)

        primaryActionSpinner.setupAdapter(R.array.andes_tooltip_main_action_style_spinner)

        spinnerSecondAction.setupAdapter(R.array.andes_tooltip_sec_action_style_spinner)

        andesTooltipToShow = AndesTooltip(
                context = this,
                isDismissible = false,
                style = getStyleBySpinner(spinnerStyle),
                title = title.text,
                body = body.text!!,
                tooltipLocation = getLocation(spinnerOrientation),
                andesTooltipSize = getSizeStyle(spinnerSizeStyle)
        )

        clear.setOnClickListener {
            spinnerStyle.setSelection(0)
            spinnerOrientation.setSelection(0)
            spinnerActionType.setSelection(0)
            spinnerSizeStyle.setSelection(0)
            primaryActionSpinner.setSelection(0)
            spinnerSecondAction.setSelection(0)

            title.text = null
            body.text = getString(R.string.andes_tooltip_message)
            primaryActionText.text = null
            secondaryActionText.text = null
            checkboxDismiss.status = AndesCheckboxStatus.UNSELECTED
            checkboxFocusable.status = AndesCheckboxStatus.UNSELECTED

            andesTooltipToShow = AndesTooltip(
                    context = this,
                    isDismissible = checkboxDismiss.status == AndesCheckboxStatus.UNSELECTED,
                    style = getStyleBySpinner(spinnerStyle),
                    title = title.text,
                    body = body.text!!,
                    tooltipLocation = getLocation(spinnerOrientation),
                    andesTooltipSize = getSizeStyle(spinnerSizeStyle)
            )
        }

        update.setOnClickListener {
            if (body.text == null) {
                body.state = AndesTextfieldState.ERROR
                return@setOnClickListener
            } else {
                body.state = AndesTextfieldState.IDLE
            }

            if (spinnerActionType.selectedItemPosition == TOOLTIP_WITH_LINK_ACTION && linkActionText.text.isNullOrEmpty()) {
                linkActionText.state = AndesTextfieldState.ERROR
                return@setOnClickListener
            } else {
                linkActionText.state = AndesTextfieldState.IDLE
            }

            if (spinnerActionType.selectedItemPosition == TOOLTIP_WITH_MAIN_ACTION && primaryActionText.text.isNullOrEmpty()) {
                primaryActionText.state = AndesTextfieldState.ERROR
                return@setOnClickListener
            } else {
                primaryActionText.state = AndesTextfieldState.IDLE
            }

            if (spinnerActionType.selectedItemPosition == TOOLTIP_WITH_MAIN_AND_SEC_ACTION && primaryActionText.text.isNullOrEmpty()) {
                primaryActionText.state = AndesTextfieldState.ERROR
                return@setOnClickListener
            } else {
                primaryActionText.state = AndesTextfieldState.IDLE
            }

            if (spinnerActionType.selectedItemPosition == TOOLTIP_WITH_MAIN_AND_SEC_ACTION && secondaryActionText.text.isNullOrEmpty()) {
                secondaryActionText.state = AndesTextfieldState.ERROR
                return@setOnClickListener
            } else {
                secondaryActionText.state = AndesTextfieldState.IDLE
            }

            andesTooltipToShow = AndesTooltip(
                    context = this,
                    isDismissible = checkboxDismiss.status == AndesCheckboxStatus.SELECTED,
                    style = getStyleBySpinner(spinnerStyle),
                    title = title.text,
                    body = body.text!!,
                    tooltipLocation = getLocation(spinnerOrientation),
                    andesTooltipSize = getSizeStyle(spinnerSizeStyle)
            )

            if (spinnerActionType.selectedItemPosition == TOOLTIP_WITH_LINK_ACTION) {
                andesTooltipToShow.linkAction = buildLinkAction(linkActionText.text.toString())
            }

            if (spinnerActionType.selectedItemPosition == TOOLTIP_WITH_MAIN_ACTION) {
                andesTooltipToShow.mainAction = buildMainAction(
                        primaryActionText.text.toString(),
                        getHierarchyBySpinner(primaryActionSpinner)
                )
            }

            if (spinnerActionType.selectedItemPosition == TOOLTIP_WITH_MAIN_AND_SEC_ACTION) {
                andesTooltipToShow.mainAction = buildMainAction(
                        primaryActionText.text.toString(),
                        getHierarchyBySpinner(primaryActionSpinner)
                )
                andesTooltipToShow.secondaryAction = buildMainAction(
                        secondaryActionText.text.toString(),
                        getHierarchyBySpinner(spinnerSecondAction)
                )
            }

            val bias = if (targetBias.text.isNullOrEmpty()) {
                0.5f
            } else {
                targetBias.text!!.toFloat() / 100
            }

            ConstraintSet().apply {
                clone(binding.andesTooltipContainer)
                setHorizontalBias(tooltip.id, bias)
                applyTo(binding.andesTooltipContainer)
            }
        }

        tooltip.setOnClickListener {
            andesTooltipToShow.shouldGainA11yFocus = checkboxFocusable.status == AndesCheckboxStatus.SELECTED
            andesTooltipToShow.show(it)
        }
    }

    private fun addStaticPage(container: View) {
        val binding = AndesuiStaticTooltipBinding.bind(container)
        val tooltipTop = AndesTooltip(
                context = this,
                isDismissible = false,
                style = AndesTooltipStyle.HIGHLIGHT,
                title = "Tooltip bottom",
                body = "This tooltip is shown bottom and without dismiss",
                tooltipLocation = AndesTooltipLocation.BOTTOM
        )

        val tooltipBottom = AndesTooltip(
                context = this,
                isDismissible = true,
                style = AndesTooltipStyle.HIGHLIGHT,
                title = "Tooltip top",
                body = "This tooltip is shown top, with link and dismiss",
                tooltipLocation = AndesTooltipLocation.TOP
        )
        tooltipBottom.linkAction = buildLinkAction("This link")

        val tooltipLeft = AndesTooltip(
                context = this,
                isDismissible = true,
                style = AndesTooltipStyle.HIGHLIGHT,
                title = "Tooltip right",
                body = "This tooltip is shown right, with main action and dismiss",
                tooltipLocation = AndesTooltipLocation.RIGHT
        )
        tooltipLeft.mainAction = buildMainAction("Primary", AndesButtonHierarchy.LOUD)

        val tooltipRight = AndesTooltip(
                context = this,
                isDismissible = false,
                style = AndesTooltipStyle.HIGHLIGHT,
                title = "Tooltip left",
                body = "This tooltip is shown left, with main action, second action and without dismiss",
                tooltipLocation = AndesTooltipLocation.LEFT
        )
        tooltipRight.mainAction = buildMainAction("Primary", AndesButtonHierarchy.LOUD)
        tooltipRight.secondaryAction = buildMainAction("Secondary", AndesButtonHierarchy.QUIET)

        binding.andesTooltipLeft.setOnClickListener {
            tooltipLeft.show(it)
        }

        binding.andesTooltipRight.setOnClickListener {
            tooltipRight.show(it)
        }

        binding.andesTooltipTop.setOnClickListener {
            tooltipTop.show(it)
        }

        binding.andesTooltipBottom.setOnClickListener {
            tooltipBottom.show(it)
        }

        binding.andesuiDemoappAndesTooltipSpecsButton.setOnClickListener {
            launchSpecs(container.context, AndesSpecs.TOOLTIP)
        }
    }

    private fun getLocation(orientationSpinner: Spinner): AndesTooltipLocation {
        return when (orientationSpinner.selectedItem) {
            "Top" -> AndesTooltipLocation.TOP
            "Bottom" -> AndesTooltipLocation.BOTTOM
            "Left" -> AndesTooltipLocation.LEFT
            "Right" -> AndesTooltipLocation.RIGHT
            else -> AndesTooltipLocation.BOTTOM
        }
    }

    private fun getHierarchyBySpinner(spinner: Spinner): AndesButtonHierarchy {
        return when (spinner.selectedItem) {
            "Loud" -> AndesButtonHierarchy.LOUD
            "Quiet" -> AndesButtonHierarchy.QUIET
            "Transparent" -> AndesButtonHierarchy.TRANSPARENT
            else -> AndesButtonHierarchy.QUIET
        }
    }

    private fun getSizeStyle(spinner: Spinner) = when (spinner.selectedItem) {
        "Dynamic" -> AndesTooltipSize.DYNAMIC
        "Full Size" -> AndesTooltipSize.FULL_SIZE
        else -> AndesTooltipSize.DYNAMIC
    }

    private fun buildMainAction(text: String, hierarchy: AndesButtonHierarchy): AndesTooltipAction {
        return AndesTooltipAction(text, hierarchy) { _, _ ->
            Toast.makeText(applicationContext, "$text was clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildLinkAction(text: String): AndesTooltipLinkAction {
        return AndesTooltipLinkAction(text) { _, _ ->
            Toast.makeText(applicationContext, "$text was clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getStyleBySpinner(spinner: Spinner): AndesTooltipStyle {
        return when (spinner.selectedItem) {
            "Light" -> AndesTooltipStyle.LIGHT
            "Dark" -> AndesTooltipStyle.DARK
            "Highlight" -> AndesTooltipStyle.HIGHLIGHT
            else -> AndesTooltipStyle.LIGHT
        }
    }

    companion object {
        private const val TOOLTIP_DARK_STYLE = 1
        private const val TOOLTIP_WITH_NO_ACTION = 0
        private const val TOOLTIP_WITH_LINK_ACTION = 1
        private const val TOOLTIP_WITH_MAIN_ACTION = 2
        private const val TOOLTIP_WITH_MAIN_AND_SEC_ACTION = 3
    }
}
