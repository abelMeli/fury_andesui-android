package com.mercadolibre.android.andesui.demoapp.components.textfield

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.Toast
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicAutosuggestBinding
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.textfield.AndesAutosuggest
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.random.Random

@SuppressLint("ViewConstructor")
@Suppress("TooManyFunctions")
class AutosuggestView(
    context: Context,
    private val coroutineScope: CoroutineScope
) : ScrollView(context), AndesListDelegate, TextWatcher {

    private val changeButton: AndesButton
    private val clearButton: AndesButton
    private val labelTextfield: AndesTextfield
    private val helperTextfield: AndesTextfield
    private val placeholderTextfield: AndesTextfield
    private val serverCallsCheckbox: AndesCheckbox
    private val suggestionsTextfield: AndesTextfield
    private val autosuggest: AndesAutosuggest
    private val stateSpinner: Spinner

    private var isServerCallEnabled = false

    private var selectedSuggestion: String? = null
    private var serverCallJob: Job? = null
    private var visibleSuggestions: List<String> = emptyList()
    private var allSuggestions: List<String> = emptyList()
        set(value) {
            field = value
            visibleSuggestions = if (!isServerCallEnabled) value else emptyList()
        }

    init {
        val binding = AndesuiDynamicAutosuggestBinding.inflate(LayoutInflater.from(context), this)
        changeButton = binding.changeButton
        clearButton = binding.clearButton
        labelTextfield = binding.labelText.apply {
            text = context.getString(R.string.andes_autosuggest_suggestions_label_default)
        }
        helperTextfield = binding.helperText
        placeholderTextfield = binding.placeholderText
        serverCallsCheckbox = binding.serverCallsCheckbox
        suggestionsTextfield = binding.suggestionsText.apply {
            text = context.getString(R.string.andes_autosuggest_suggestions_default)
        }
        autosuggest = binding.autosuggest
        stateSpinner = binding.stateSpinner.apply {
            adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                context.resources.getStringArray(R.array.andes_textfield_state_2_spinner)
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        }

        changeButton.setOnClickListener { updateAutosuggest() }
        clearButton.setOnClickListener { clearAutosuggest() }

        updateAutosuggest()
    }

    override fun onItemClick(andesList: AndesList, position: Int) {
        val selected = visibleSuggestions[position]
        selectedSuggestion = selected
        setSelectedSuggestionText()
        labelTextfield.requestFocusOnTextField()
        Toast
            .makeText(
                andesList.context,
                andesList.context.getString(R.string.andes_autosuggest_suggestions_selected_text, selected),
                Toast.LENGTH_SHORT
            )
            .show()
    }

    override fun bind(andesList: AndesList, view: View, position: Int) = AndesListViewItemSimple(
        context = andesList.context,
        title = visibleSuggestions[position]
    )

    override fun getDataSetSize(andesList: AndesList) = visibleSuggestions.size

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Do nothing.
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Do nothing.
    }

    override fun afterTextChanged(query: Editable?) {
        selectedSuggestion = null
        visibleSuggestions = if (isServerCallEnabled) {
            serverCallJob?.cancel()
            if (query?.toString().orEmpty().length >= SERVER_THRESHOLD) {
                simulateServerCall(query?.toString().orEmpty())
            }
            emptyList()
        } else {
            allSuggestions.filter { query.isNullOrEmpty() || it.contains(query.toString(), ignoreCase = true) }
        }
        if (visibleSuggestions.isEmpty()) autosuggest.hideSuggestions() else autosuggest.showSuggestions()
    }

    private fun updateAutosuggest() = with(autosuggest) {
        textWatcher = null
        text = ""
        label = labelTextfield.text.toString()
        helper = helperTextfield.text.toString()
        placeholder = placeholderTextfield.text.toString()
        state = AndesTextfieldState.valueOf(stateSpinner.selectedItem.toString().toUpperCase(Locale.US))
        isServerCallEnabled = serverCallsCheckbox.status == AndesCheckboxStatus.SELECTED
        suggestionsDelegate = this@AutosuggestView
        textWatcher = this@AutosuggestView
        onTouch = doOnTouch()
        allSuggestions = getSuggestions(suggestionsTextfield)
    }

    private fun clearAutosuggest() {
        labelTextfield.text = null
        placeholderTextfield.placeholder = context.resources.getString(R.string.andes_textfield_placeholder_text)
        placeholderTextfield.text = null
        helperTextfield.text = null
        suggestionsTextfield.text = null
        serverCallsCheckbox.status = AndesCheckboxStatus.UNSELECTED
        stateSpinner.setSelection(0)

        updateAutosuggest()
    }

    private fun doOnTouch() = { event: MotionEvent ->
        if (!isServerCallEnabled && event.actionMasked == MotionEvent.ACTION_DOWN && visibleSuggestions.isNotEmpty()) {
            autosuggest.showSuggestions()
        }
    }

    private fun getSuggestions(suggestionsTextfield: AndesTextfield) = suggestionsTextfield.text
        .orEmpty()
        .split(",")
        .filter { it.isNotEmpty() }

    private fun setSelectedSuggestionText() {
        autosuggest.textWatcher = null
        autosuggest.text = selectedSuggestion
        autosuggest.textWatcher = this
    }

    private fun simulateServerCall(query: String) {
        serverCallJob = coroutineScope.launch(Dispatchers.Main) {
            autosuggest.setIndeterminate()
            withContext(Dispatchers.Default) {
                delay(Random.nextLong(SERVER_DELAY_MIN_MS, SERVER_DELAY_MAX_MS))
                visibleSuggestions = allSuggestions.filter { it.contains(query, ignoreCase = true) }
            }
            if (visibleSuggestions.isNotEmpty()) {
                autosuggest.showSuggestions()
            }
            autosuggest.rightContent = null
            serverCallJob = null
        }
    }

    companion object {

        private const val SERVER_THRESHOLD = 3
        private const val SERVER_DELAY_MIN_MS: Long = 1_000
        private const val SERVER_DELAY_MAX_MS: Long = 3_000
    }
}
