package com.mercadolibre.android.andesui.demoapp.components.tooltip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.textfield.AndesTextarea
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.tooltip.AndesTooltip
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipAction
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipLinkAction
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocation
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipStyle

@Suppress("TooManyFunctions")
class TooltipShowcaseActivity : AppCompatActivity() {

    private lateinit var andesTooltipToShow: AndesTooltip
    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.andesui_showcase_main)

        initActionBar()
        initViewPager()
        attachIndicator()
        loadViews()
    }

    private fun initActionBar() {
        setSupportActionBar(findViewById(R.id.andesui_nav_bar))
        supportActionBar?.title = resources.getString(R.string.andes_demoapp_screen_tooltip)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViewPager() {
        val inflater = LayoutInflater.from(this)
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
                inflater.inflate(R.layout.andesui_dynamic_tooltip, null, false),
                inflater.inflate(R.layout.andesui_static_tooltip, null, false)
        ))
    }

    private fun attachIndicator() {
        val indicator = findViewById<PageIndicator>(R.id.page_indicator)
        indicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        addStaticPage(adapter.views[1])
    }

    @Suppress("LongMethod")
    private fun addDynamicPage(container: View) {
        val checkboxDismiss = container.findViewById<AndesCheckbox>(R.id.dismissable_checkbox)
        val title = container.findViewById<AndesTextfield>(R.id.title_text)
        val body = container.findViewById<AndesTextarea>(R.id.body_text)
        val primaryActionText = container.findViewById<AndesTextfield>(R.id.primary_action_text)
        val secondaryActionText = container.findViewById<AndesTextfield>(R.id.secondary_action_text)
        val linkActionText = container.findViewById<AndesTextfield>(R.id.link_action_text)
        val update = container.findViewById<AndesButton>(R.id.change_button)
        val clear = container.findViewById<AndesButton>(R.id.clear_button)
        val tooltip = container.findViewById<AndesButton>(R.id.andes_tooltip_button)

        val mainActionConfig = container.findViewById<Group>(R.id.main_action_config)
        val secondaryActionConfig = container.findViewById<Group>(R.id.secondary_action_config)

        body.text = getString(R.string.andes_tooltip_message)
        checkboxDismiss.status = AndesCheckboxStatus.UNSELECTED

        val spinnerStyle = container.findViewById<Spinner>(R.id.style_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_tooltip_style_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerStyle.adapter = adapter
        }

        val spinnerOrientation = container.findViewById<Spinner>(R.id.orientation_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_tooltip_location_spinner,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerOrientation.adapter = adapter
        }

        val spinnerActionType = container.findViewById<Spinner>(R.id.action_type_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_tooltip_action_type_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerActionType.adapter = adapter
            spinnerActionType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) = Unit

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
            }
        }

        val primaryActionSpinner = container.findViewById<Spinner>(R.id.primary_action_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_tooltip_main_action_style_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            primaryActionSpinner.adapter = adapter
        }

        val spinnerSecondAction = container.findViewById<Spinner>(R.id.secondary_action_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_tooltip_sec_action_style_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSecondAction.adapter = adapter
        }

        andesTooltipToShow = AndesTooltip(
                context = this,
                isDismissible = false,
                style = getStyleBySpinner(spinnerStyle),
                title = title.text,
                body = body.text!!,
                tooltipLocation = getLocation(spinnerOrientation)
        )

        clear.setOnClickListener {
            spinnerStyle.setSelection(0)
            spinnerOrientation.setSelection(0)
            spinnerActionType.setSelection(0)
            primaryActionSpinner.setSelection(0)
            spinnerSecondAction.setSelection(0)

            title.text = null
            body.text = getString(R.string.andes_tooltip_message)
            primaryActionText.text = null
            secondaryActionText.text = null
            checkboxDismiss.status = AndesCheckboxStatus.UNSELECTED

            andesTooltipToShow = AndesTooltip(
                    context = this,
                    isDismissible = checkboxDismiss.status == AndesCheckboxStatus.UNSELECTED,
                    style = getStyleBySpinner(spinnerStyle),
                    title = title.text,
                    body = body.text!!,
                    tooltipLocation = getLocation(spinnerOrientation)
            )
        }

        update.setOnClickListener {
            if (body.text.isNullOrEmpty()) {
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
                    tooltipLocation = getLocation(spinnerOrientation)
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
        }

        tooltip.setOnClickListener {
            andesTooltipToShow.show(it)
        }
    }

    private fun addStaticPage(container: View) {
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

        container.findViewById<ImageView>(R.id.andes_tooltip_left).setOnClickListener {
            tooltipLeft.show(it)
        }

        container.findViewById<ImageView>(R.id.andes_tooltip_right).setOnClickListener {
            tooltipRight.show(it)
        }

        container.findViewById<AndesButton>(R.id.andes_tooltip_top).setOnClickListener {
            tooltipTop.show(it)
        }

        container.findViewById<AndesButton>(R.id.andes_tooltip_bottom).setOnClickListener {
            tooltipBottom.show(it)
        }

        container.findViewById<AndesButton>(R.id.andesui_demoapp_andes_tooltip_specs_button).setOnClickListener {
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

    private fun buildMainAction(text: String, hierarchy: AndesButtonHierarchy): AndesTooltipAction {
        return AndesTooltipAction(text, hierarchy) { _, _ ->
            Toast.makeText(this, "$text was clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildLinkAction(text: String): AndesTooltipLinkAction {
        return AndesTooltipLinkAction(text) { _, _ ->
            Toast.makeText(this, "$text was clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getStyleBySpinner(spinner: Spinner): AndesTooltipStyle {
        return when (spinner.selectedItem) {
            "Light" -> AndesTooltipStyle.LIGHT
            "Highlight" -> AndesTooltipStyle.HIGHLIGHT
            else -> AndesTooltipStyle.LIGHT
        }
    }

    companion object {
        private const val TOOLTIP_WITH_NO_ACTION = 0
        private const val TOOLTIP_WITH_LINK_ACTION = 1
        private const val TOOLTIP_WITH_MAIN_ACTION = 2
        private const val TOOLTIP_WITH_MAIN_AND_SEC_ACTION = 3
    }
}
