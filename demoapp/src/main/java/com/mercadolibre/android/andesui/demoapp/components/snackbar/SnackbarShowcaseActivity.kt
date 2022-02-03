package com.mercadolibre.android.andesui.demoapp.components.snackbar

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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
    private lateinit var binding: AndesuiDynamicSnackbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_snackbar)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(
            listOf(
                AndesuiDynamicSnackbarBinding.inflate(layoutInflater).root
            )
        )
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
        binding = AndesuiDynamicSnackbarBinding.bind(container)
        val variation = binding.snackbarVariation
        val hasAction = binding.snackbarHasAction
        val text = binding.snackbarText
        val textAction = binding.snackbarTextAction
        val clearButton = binding.clearButton
        val confirmButton = binding.changeButton
        val actionGroup = binding.actionGroup

        setupAndesSnackabrVariation(variation)

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
            variation.setSelection(0)
            snackbarDuration.setSelection(0)
        }

        confirmButton.setOnClickListener {
            if (variation.selectedItem == SIMPLE) {
                showSimpleSnackbar(container)
            } else {
                showErrorCodeSnackbar(container)
            }
        }
    }

    private fun setupAndesSnackabrVariation(
        variationSpinner: Spinner
    ) {
        with(variationSpinner) {
            ArrayAdapter.createFromResource(
                context,
                R.array.andes_snackbar_variation_spinner,
                android.R.layout.simple_spinner_item
            ).let { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                this.adapter = adapter
            }

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (selectedItem) {
                        ERROR_CODE -> {
                            binding.snackbarType.visibility = View.GONE
                            binding.textView15.visibility = View.GONE
                        }
                        SIMPLE -> {
                            binding.snackbarType.visibility = View.VISIBLE
                            binding.textView15.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // no-op
                }
            }
        }
    }

    private fun showSimpleSnackbar(container: View) {
        if (!isValidText() || !isValidHasAction()) {
            return
        }

        val selectedType = getSelectedType()

        val selectedDuration = getSelectedDuration()

        val snackbar = AndesSnackbar(
            this,
            container,
            selectedType,
            binding.snackbarText.text!!,
            selectedDuration
        )
        if (binding.snackbarHasAction.isChecked) {
            snackbar.action = AndesSnackbarAction(
                binding.snackbarTextAction.text!!,
                View.OnClickListener {
                    Toast.makeText(this, "Callback", Toast.LENGTH_SHORT).show()
                }
            )
        }
        snackbar.show()
    }

    private fun showErrorCodeSnackbar(container: View) {
        if (!isValidText() || !isValidHasAction()) {
            return
        }

        val selectedDuration = getSelectedDuration()

        val snackbar = AndesSnackbar(
            this,
            container,
            "84AJDJ2",
            binding.snackbarText.text!!,
            selectedDuration
        )
        if (binding.snackbarHasAction.isChecked) {
            snackbar.action = AndesSnackbarAction(
                binding.snackbarTextAction.text!!,
                View.OnClickListener {
                    Toast.makeText(this, "Callback", Toast.LENGTH_SHORT).show()
                }
            )
        }
        snackbar.show()
    }

    private fun isValidText(): Boolean {
        return if (binding.snackbarText.text.isNullOrEmpty()) {
            binding.snackbarText.state = AndesTextfieldState.ERROR
            binding.snackbarText.helper = getString(R.string.andes_snackbar_error)
            false
        } else {
            binding.snackbarText.state = AndesTextfieldState.IDLE
            binding.snackbarText.helper = null
            true
        }
    }

    private fun isValidHasAction(): Boolean {
        return if (binding.snackbarHasAction.isChecked && binding.snackbarTextAction.text.isNullOrEmpty()) {
            binding.snackbarTextAction.state = AndesTextfieldState.ERROR
            binding.snackbarTextAction.helper = getString(R.string.andes_snackbar_error)
            false
        } else {
            binding.snackbarTextAction.state = AndesTextfieldState.IDLE
            binding.snackbarTextAction.helper = null
            true
        }
    }

    private fun getSelectedDuration(): AndesSnackbarDuration {
        return when (binding.snackbarDuration.selectedItem) {
            "Long" -> AndesSnackbarDuration.LONG
            "Normal" -> AndesSnackbarDuration.NORMAL
            "Infinite" -> AndesSnackbarDuration.INFINITE
            else -> AndesSnackbarDuration.SHORT
        }
    }

    private fun getSelectedType(): AndesSnackbarType {
        return when (binding.snackbarType.selectedItem) {
            "Neutral" -> AndesSnackbarType.NEUTRAL
            "Success" -> AndesSnackbarType.SUCCESS
            else -> AndesSnackbarType.ERROR
        }
    }

    companion object {
        private const val ERROR_CODE = "Error Code"
        private const val SIMPLE = "Simple"
    }
}
