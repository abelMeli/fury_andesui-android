package com.mercadolibre.android.andesui.demoapp.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.feature.utils.PageIndicator
import com.mercadolibre.android.andesui.snackbar.AndesSnackbar
import com.mercadolibre.android.andesui.snackbar.action.AndesSnackbarAction
import com.mercadolibre.android.andesui.snackbar.duration.AndesSnackbarDuration
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarType
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

class SnackbarShowcaseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.andesui_showcase_main)

        setSupportActionBar(findViewById(R.id.andesui_nav_bar))
        supportActionBar?.title = resources.getString(R.string.andesui_demoapp_screen_snackbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.setScreenName(getString(R.string.andes_snackbar_showcase))
        viewPager.adapter = AndesPagerAdapter(loadViews())

        val indicator = findViewById<PageIndicator>(R.id.page_indicator)
        indicator.attach(viewPager)

        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicSnackbar(adapter.views[0])
    }

    private fun loadViews(): List<View> {
        val inflater = LayoutInflater.from(this)
        val layout = inflater.inflate(R.layout.andesui_snackbar_showcase_change, null, false)
        return listOf<View>(layout)
    }

    @Suppress("ComplexMethod", "LongMethod")
    private fun addDynamicSnackbar(container: View) {
        val type: Spinner = container.findViewById(R.id.snackbar_type)
        val duration: Spinner = container.findViewById(R.id.snackbar_duration)
        val hasAction: Switch = container.findViewById(R.id.snackbar_has_action)
        val text: AndesTextfield = container.findViewById(R.id.snackbar_text)
        val textAction: AndesTextfield = container.findViewById(R.id.snackbar_text_action)
        val clearButton: AndesButton = container.findViewById(R.id.clear_button)
        val confirmButton: AndesButton = container.findViewById(R.id.change_button)
        val actionGroup: Group = container.findViewById(R.id.action_group)

        hasAction.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                actionGroup.visibility = View.VISIBLE
            } else {
                actionGroup.visibility = View.GONE
            }
        }

        val snackbarType: Spinner = container.findViewById(R.id.snackbar_type)
        ArrayAdapter.createFromResource(
                this,
                R.array.snackbar_type_spinner,
                android.R.layout.simple_spinner_item
        )
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    snackbarType.adapter = adapter
                }

        val snackbarDuration: Spinner = container.findViewById(R.id.snackbar_duration)
        ArrayAdapter.createFromResource(
                this,
                R.array.snackbar_duration_spinner,
                android.R.layout.simple_spinner_item
        )
                .also { adapter ->
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

            type.setSelection(0)
            duration.setSelection(0)
        }

        confirmButton.setOnClickListener {
            var hasError = false
            if (text.text.isNullOrEmpty()) {
                text.state = AndesTextfieldState.ERROR
                text.helper = getString(R.string.andesui_demoapp_snackbar_error)
                hasError = true
            } else {
                text.state = AndesTextfieldState.IDLE
                text.helper = null
            }

            if (hasAction.isChecked && textAction.text.isNullOrEmpty()) {
                textAction.state = AndesTextfieldState.ERROR
                textAction.helper = getString(R.string.andesui_demoapp_snackbar_error)
                hasError = true
            } else {
                textAction.state = AndesTextfieldState.IDLE
                textAction.helper = null
            }

            if (hasError) {
                return@setOnClickListener
            }

            val selectedType = when (type.selectedItem) {
                "Neutral" -> AndesSnackbarType.NEUTRAL
                "Success" -> AndesSnackbarType.SUCCESS
                else -> AndesSnackbarType.ERROR
            }

            val selectedDuration = when (duration.selectedItem) {
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
                            Toast.makeText(this, "Callback", Toast.LENGTH_SHORT).show()
                        }
                )
            }
            snackbar.show()
        }
    }
}
