package com.mercadolibre.android.andesui.demoapp.components.snackbar

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicSnackbarBinding
import com.mercadolibre.android.andesui.snackbar.AndesSnackbar
import com.mercadolibre.android.andesui.snackbar.action.AndesSnackbarAction
import com.mercadolibre.android.andesui.snackbar.duration.AndesSnackbarDuration
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarType
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

class SnackbarShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_snackbar)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf(
            AndesuiDynamicSnackbarBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
    }

    @Suppress("ComplexMethod", "LongMethod")
    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicSnackbarBinding.bind(container)
        val hasAction = binding.snackbarHasAction
        val text = binding.snackbarText
        val textAction = binding.snackbarTextAction
        val clearButton = binding.clearButton
        val confirmButton = binding.changeButton
        val actionGroup = binding.actionGroup

        hasAction.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                actionGroup.visibility = View.VISIBLE
            } else {
                actionGroup.visibility = View.GONE
            }
        }

        val snackbarType = binding.snackbarType
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_snackbar_type_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            snackbarType.adapter = adapter
        }

        val snackbarDuration = binding.snackbarDuration
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_snackbar_duration_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            snackbarDuration.adapter = adapter
        }

        clearButton.setOnClickListener {
            hasAction.isChecked = false
            actionGroup.visibility = View.GONE

            text.state = AndesTextfieldState.IDLE
            text.helper = null
            text.text = null

            textAction.state = AndesTextfieldState.IDLE
            textAction.helper = null
            textAction.text = null

            snackbarType.setSelection(0)
            snackbarDuration.setSelection(0)
        }

        confirmButton.setOnClickListener {
            var hasError = false
            if (text.text.isNullOrEmpty()) {
                text.state = AndesTextfieldState.ERROR
                text.helper = getString(R.string.andes_snackbar_error)
                hasError = true
            } else {
                text.state = AndesTextfieldState.IDLE
                text.helper = null
            }

            if (hasAction.isChecked && textAction.text.isNullOrEmpty()) {
                textAction.state = AndesTextfieldState.ERROR
                textAction.helper = getString(R.string.andes_snackbar_error)
                hasError = true
            } else {
                textAction.state = AndesTextfieldState.IDLE
                textAction.helper = null
            }

            if (hasError) {
                return@setOnClickListener
            }

            val selectedType = when (snackbarType.selectedItem) {
                "Neutral" -> AndesSnackbarType.NEUTRAL
                "Success" -> AndesSnackbarType.SUCCESS
                else -> AndesSnackbarType.ERROR
            }

            val selectedDuration = when (snackbarDuration.selectedItem) {
                "Long" -> AndesSnackbarDuration.LONG
                "Normal" -> AndesSnackbarDuration.NORMAL
                else -> AndesSnackbarDuration.SHORT
            }

            val snackbar = AndesSnackbar(
                    this,
                    container,
                    selectedType,
                    text.text!!,
                    selectedDuration
            )
            if (hasAction.isChecked) {
                snackbar.action = AndesSnackbarAction(
                        textAction.text!!,
                        View.OnClickListener {
                            Toast.makeText(applicationContext, "Callback", Toast.LENGTH_SHORT).show()
                        }
                )
            }
            snackbar.show()
        }
    }
}
