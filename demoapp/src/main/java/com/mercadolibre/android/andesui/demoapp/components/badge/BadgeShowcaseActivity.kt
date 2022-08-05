package com.mercadolibre.android.andesui.demoapp.components.badge

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.mercadolibre.android.andesui.badge.border.AndesBadgePillBorder
import com.mercadolibre.android.andesui.badge.hierarchy.AndesBadgePillHierarchy
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicBadgesBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticBadgesBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

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
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
            AndesuiDynamicBadgesBinding.inflate(layoutInflater).root,
            AndesuiStaticBadgesBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        addStaticPage(adapter.views[1])
    }

    @Suppress("ComplexMethod", "LongMethod")
    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicBadgesBinding.bind(container)
        val modifierSpinner = binding.modifierSpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_badge_modifier_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            modifierSpinner.adapter = adapter
        }

        val hierarchySpinner = binding.hierarchySpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.hierarchy_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hierarchySpinner.adapter = adapter
        }

        val typeSpinner = binding.simpleTypeSpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.simple_type_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeSpinner.adapter = adapter
        }

        val sizeSpinner = binding.sizeSpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.size_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sizeSpinner.adapter = adapter
        }

        val borderSpinner = binding.borderSpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_border_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            borderSpinner.adapter = adapter
        }

        val textField = binding.labelText

        val andesBadgePill = binding.andesBadgePill
        val andesBadgeDot = binding.andesBadgeDot
        val group = binding.group
        val andesBadgeDefaultText = binding.andesuiDemoappDefaulText

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

        binding.clearButton.setOnClickListener {
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

        binding.changeButton.setOnClickListener {
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
                        "Corner Left" -> AndesBadgePillBorder.CORNER_LEFT
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
        val binding = AndesuiStaticBadgesBinding.bind(container)
        binding.andesuiDemoappAndesBadgeSpecsButton.setOnClickListener {
            launchSpecs(container.context, AndesSpecs.BADGE)
        }
    }
}
