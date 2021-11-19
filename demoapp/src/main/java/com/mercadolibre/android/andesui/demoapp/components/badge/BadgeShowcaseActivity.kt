package com.mercadolibre.android.andesui.demoapp.components.badge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.mercadolibre.android.andesui.badge.AndesBadgeDot
import com.mercadolibre.android.andesui.badge.AndesBadgePill
import com.mercadolibre.android.andesui.badge.border.AndesBadgePillBorder
import com.mercadolibre.android.andesui.badge.hierarchy.AndesBadgePillHierarchy
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import kotlinx.android.synthetic.main.andesui_dynamic_badges.*

class BadgeShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle(): String = resources.getString(R.string.andes_demoapp_screen_badge)

    private fun initViewPager() {
        val inflater = LayoutInflater.from(this)
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
                inflater.inflate(R.layout.andesui_dynamic_badges, null, false),
                inflater.inflate(R.layout.andesui_static_badges, null, false)
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

    @Suppress("ComplexMethod", "LongMethod")
    private fun addDynamicPage(container: View) {
        val modifierSpinner: Spinner = container.findViewById(R.id.modifier_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_badge_modifier_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            modifierSpinner.adapter = adapter
        }

        val hierarchySpinner: Spinner = container.findViewById(R.id.hierarchy_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.hierarchy_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hierarchySpinner.adapter = adapter
        }

        val typeSpinner: Spinner = container.findViewById(R.id.simple_type_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.simple_type_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeSpinner.adapter = adapter
        }

        val sizeSpinner: Spinner = container.findViewById(R.id.size_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.size_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sizeSpinner.adapter = adapter
        }

        val borderSpinner: Spinner = container.findViewById(R.id.border_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_border_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            borderSpinner.adapter = adapter
        }

        val textField: AndesTextfield = container.findViewById(R.id.label_text)
        val clearButton: AndesButton = container.findViewById(R.id.clear_button)
        val changeButton: AndesButton = container.findViewById(R.id.change_button)

        val andesBadgePill: AndesBadgePill = container.findViewById(R.id.andes_badge_pill)
        val andesBadgeDot: AndesBadgeDot = container.findViewById(R.id.andes_badge_dot)

        val andesBadgeDefaultText: AndesCheckbox = container.findViewById(R.id.andesui_demoapp_defaul_text)

        modifierSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                when (modifierSpinner.getItemAtPosition(position)) {
                    "Pill" -> {
                        group.visibility = View.VISIBLE
                        andesBadgePill.visibility = View.VISIBLE
                        andesBadgeDot.visibility = View.INVISIBLE
                    }
                    "Dot" -> {
                        group.visibility = View.GONE
                        andesBadgePill.visibility = View.INVISIBLE
                        andesBadgeDot.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing.
            }
        }

        clearButton.setOnClickListener {
            group.visibility = View.VISIBLE
            andesBadgePill.visibility = View.VISIBLE
            andesBadgeDot.visibility = View.INVISIBLE

            modifierSpinner.setSelection(0)
            hierarchySpinner.setSelection(0)
            typeSpinner.setSelection(0)
            sizeSpinner.setSelection(0)
            borderSpinner.setSelection(0)

            textField.text = ""
            andesBadgePill.text = getString(R.string.andes_badge_text)
            andesBadgePill.type = AndesBadgeType.NEUTRAL
            andesBadgePill.pillHierarchy = AndesBadgePillHierarchy.LOUD
            andesBadgePill.pillSize = AndesBadgePillSize.LARGE
            andesBadgePill.pillBorder = AndesBadgePillBorder.STANDARD
        }

        changeButton.setOnClickListener {
            when (modifierSpinner.getItemAtPosition(modifierSpinner.selectedItemPosition)) {
                "Pill" -> {
                    if (textField.text.isNullOrEmpty()) {
                        textField.state = AndesTextfieldState.ERROR
                        textField.helper = "Campo obligatorio"
                        return@setOnClickListener
                    } else {
                        textField.state = AndesTextfieldState.IDLE
                        textField.helper = null
                    }

                    val type = when (typeSpinner.getItemAtPosition(typeSpinner.selectedItemPosition)) {
                        "Neutral" -> AndesBadgeType.NEUTRAL
                        "Highlight" -> AndesBadgeType.HIGHLIGHT
                        "Success" -> AndesBadgeType.SUCCESS
                        "Warning" -> AndesBadgeType.WARNING
                        "Error" -> AndesBadgeType.ERROR
                        else -> AndesBadgeType.NEUTRAL
                    }

                    val hierarchy = when (hierarchySpinner.getItemAtPosition(hierarchySpinner.selectedItemPosition)) {
                        "Loud" -> AndesBadgePillHierarchy.LOUD
                        "Quiet" -> AndesBadgePillHierarchy.QUIET
                        else -> AndesBadgePillHierarchy.LOUD
                    }

                    val size = when (sizeSpinner.getItemAtPosition(sizeSpinner.selectedItemPosition)) {
                        "Small" -> AndesBadgePillSize.SMALL
                        "Large" -> AndesBadgePillSize.LARGE
                        else -> AndesBadgePillSize.LARGE
                    }

                    val border = when (borderSpinner.getItemAtPosition(borderSpinner.selectedItemPosition)) {
                        "Standard" -> AndesBadgePillBorder.STANDARD
                        "Corner" -> AndesBadgePillBorder.CORNER
                        "Rounded" -> AndesBadgePillBorder.ROUNDED
                        else -> AndesBadgePillBorder.STANDARD
                    }

                    andesBadgePill.type = type
                    andesBadgePill.pillHierarchy = hierarchy
                    andesBadgePill.pillSize = size
                    andesBadgePill.pillBorder = border
                    andesBadgePill.text = textField.text
                    andesBadgePill.textStyleDefault = andesBadgeDefaultText.status == AndesCheckboxStatus.SELECTED
                }
                "Dot" -> {
                    val type = when (typeSpinner.getItemAtPosition(typeSpinner.selectedItemPosition)) {
                        "Neutral" -> AndesBadgeType.NEUTRAL
                        "Highlight" -> AndesBadgeType.HIGHLIGHT
                        "Success" -> AndesBadgeType.SUCCESS
                        "Warning" -> AndesBadgeType.WARNING
                        "Error" -> AndesBadgeType.ERROR
                        else -> AndesBadgeType.NEUTRAL
                    }
                    andesBadgeDot.type = type
                }
            }
        }
    }

    private fun addStaticPage(container: View) {
        bindAndesSpecsButton(container)
    }

    private fun bindAndesSpecsButton(container: View) {
        container.findViewById<AndesButton>(R.id.andesui_demoapp_andes_badge_specs_button).setOnClickListener {
            launchSpecs(container.context, AndesSpecs.BADGE)
        }
    }
}
