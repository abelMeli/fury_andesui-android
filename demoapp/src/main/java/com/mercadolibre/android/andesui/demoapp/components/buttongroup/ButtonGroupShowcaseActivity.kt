package com.mercadolibre.android.andesui.demoapp.components.buttongroup

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.button.size.AndesButtonSize
import com.mercadolibre.android.andesui.buttongroup.align.AndesButtonGroupAlign
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.buttongroup.type.AndesButtonGroupType
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicButtonGroupBinding
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

class ButtonGroupShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager
    private lateinit var binding: AndesuiDynamicButtonGroupBinding
    private var previousQuantity = 1
    private val buttonArrayList = arrayListOf<AndesButton>()

    private val distributionData = mutableListOf<String>("Horizontal", "Vertical", "Auto")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewPager()
        attachIndicator()
        loadViews()
        createConfigGlobalProperties()
        createConfigButton(1)

    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_button_group)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(
            listOf<View>(
                AndesuiDynamicButtonGroupBinding.inflate(layoutInflater).root
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

    private fun addDefaultButton() {
        buttonArrayList.clear()
        buttonArrayList.add(AndesButton(context = this, buttonText = "Button 1"))
        binding.buttonGroup.setButtons(buttonArrayList)
    }

    private fun addDynamicPage(container: View) {
        binding = AndesuiDynamicButtonGroupBinding.bind(container)
        addDefaultButton()

        binding.apply {
            changeButton.setOnClickListener {
                val currentQuantity = quantitySpinner.selectedItem.toString().toInt()
                handleQuantityButtons(currentQuantity)

                val distributionPosition = distributionSpinner.selectedItemPosition
                if (currentQuantity < 3) {
                    distributionData.remove(DISTRIBUTION_MIXED)
                    createAdapterForDistribution(distributionData)
                } else if (distributionData.contains(DISTRIBUTION_MIXED).not()) {
                    distributionData.add(DISTRIBUTION_MIXED)
                    createAdapterForDistribution(distributionData)
                }
                distributionSpinner.setSelection(distributionPosition)

                val selectedSize = when (sizeSpinner.selectedItem) {
                    "Large" -> AndesButtonSize.LARGE
                    "Medium" -> AndesButtonSize.MEDIUM
                    "Small" -> AndesButtonSize.SMALL
                    else -> AndesButtonSize.LARGE
                }

                val selectedDistribution = when (distributionSpinner.selectedItem) {
                    "Horizontal" -> AndesButtonGroupDistribution.HORIZONTAL
                    "Vertical" -> AndesButtonGroupDistribution.VERTICAL
                    "Auto" -> AndesButtonGroupDistribution.AUTO
                    else -> AndesButtonGroupDistribution.MIXED
                }

                val selectedAlign = when (alignSpinner.selectedItem) {
                    "Left" -> AndesButtonGroupAlign.LEFT
                    "Right" -> AndesButtonGroupAlign.RIGHT
                    else -> AndesButtonGroupAlign.CENTER
                }

                val selectedType = when (typeSpinner.selectedItem) {
                    "Responsive" -> AndesButtonGroupType.Responsive(selectedAlign)
                    else -> AndesButtonGroupType.FullWidth
                }

                for (i in 0 until buttonArrayList.size) {
                    buttonArrayList[i].apply {
                        val viewGroup = binding.buttonPropertiesContent.getChildAt(i) as ViewGroup
                        val hierarchySpinner = viewGroup.getChildAt(2) as Spinner

                        val selectedhierarchy = when (hierarchySpinner.selectedItem) {
                            "Loud" -> AndesButtonHierarchy.LOUD
                            "Quiet" -> AndesButtonHierarchy.QUIET
                            "Transparent" -> AndesButtonHierarchy.TRANSPARENT
                            else -> AndesButtonHierarchy.LOUD
                        }

                        val textField = viewGroup.getChildAt(3) as AndesTextfield

                        if (textField.text.toString().isNullOrEmpty()) {
                            textField.state = AndesTextfieldState.ERROR
                            textField.helper = "Campo obligatorio"
                            return@setOnClickListener
                        } else {
                            textField.state = AndesTextfieldState.IDLE
                        }

                        hierarchy = selectedhierarchy
                        text = textField.text
                        size = selectedSize
                    }

                }

                binding.buttonGroup.apply {
                    setButtons(buttonArrayList)
                    distribution = selectedDistribution
                    type = selectedType
                    setAlign(selectedAlign)
                }

            }

            clearButton.setOnClickListener {
                binding.buttonPropertiesContent.removeAllViews()
                distributionData.remove(DISTRIBUTION_MIXED)
                createAdapterForDistribution(distributionData)
                distributionSpinner.setSelection(0)
                typeSpinner.setSelection(0)
                alignSpinner.setSelection(0)
                quantitySpinner.setSelection(0)
                addDefaultButton()
                createConfigButton(1)
            }
        }

    }

    private fun createAdapterForDistribution(data: MutableList<String>) {
        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            data
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.distributionSpinner.adapter = this
        }
    }

    private fun createConfigGlobalProperties() {

        createAdapterForDistribution(distributionData)

        ArrayAdapter.createFromResource(
            this,
            R.array.andes_button_group_type_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.typeSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.andes_button_group_align_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.alignSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.andes_button_size_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.sizeSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.andes_button_group_quantity_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.quantitySpinner.adapter = adapter
        }
    }

    private fun createConfigButton(index: Int) {
        previousQuantity = index
        var currentId = BASE_ID * index

        val constraintLayout = ConstraintLayout(this)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        constraintLayout.apply {
            layoutParams = params
            id = currentId
        }

        val buttonTitleParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val buttonTitle = TextView(this).apply {
            currentId++
            text = "BUTTON $index"
            id = currentId
            layoutParams = buttonTitleParams
            setTypeface(this.typeface, Typeface.BOLD)
        }
        configLayoutParamView(buttonTitle)

        val hierarchyTextView = TextView(this).apply {
            currentId++
            text = "Hierarchy"
            id = currentId
        }

        configLayoutParamView(view = hierarchyTextView, width = ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val hierarchySpinner = Spinner(this).apply {
            currentId++
            id = currentId
        }

        configLayoutParamView(view = hierarchySpinner, width = ConstraintLayout.LayoutParams.WRAP_CONTENT)

        ArrayAdapter.createFromResource(
            this,
            R.array.andes_button_hierarchy_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hierarchySpinner.adapter = adapter
        }

        val textFieldParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val textField = AndesTextfield(context = this, placeholder = "Button $index").apply {
            currentId++
            id = currentId
            layoutParams = textFieldParams
        }
        configLayoutParamView(textField)

        constraintLayout.addView(buttonTitle)
        constraintLayout.addView(hierarchyTextView)
        constraintLayout.addView(hierarchySpinner)
        constraintLayout.addView(textField)

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        constraintSet.connect(buttonTitle.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSet.connect(buttonTitle.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        constraintSet.connect(buttonTitle.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)

        constraintSet.connect(hierarchyTextView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSet.connect(hierarchyTextView.id, ConstraintSet.TOP, buttonTitle.id, ConstraintSet.BOTTOM)

        constraintSet.connect(hierarchySpinner.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        constraintSet.connect(hierarchySpinner.id, ConstraintSet.TOP, buttonTitle.id, ConstraintSet.BOTTOM)

        constraintSet.connect(textField.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSet.connect(textField.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        constraintSet.connect(textField.id, ConstraintSet.TOP, hierarchyTextView.id, ConstraintSet.BOTTOM)

        constraintSet.applyTo(constraintLayout)

        binding.buttonPropertiesContent.addView(constraintLayout)
    }

    private fun configLayoutParamView(
        view: View,
        width: Int = ConstraintLayout.LayoutParams.MATCH_PARENT,
        height: Int = ConstraintLayout.LayoutParams.WRAP_CONTENT,
        left: Int = 0,
        top: Int = resources.getDimension(R.dimen.margin_vertical_text_field).toInt(),
        right: Int = 0,
        bottom: Int = 0
    ) {
        val params = ConstraintLayout.LayoutParams(
            width,
            height
        )
        params.setMargins(left, top, right, bottom)

        view.layoutParams = params
    }

    private fun handleQuantityButtons(quantity: Int) {
        var indexButton = previousQuantity
        when {
            quantity > previousQuantity -> {
                val repeatUntil = quantity - previousQuantity
                for (i in 0 until repeatUntil) {
                    indexButton++
                    createConfigButton(indexButton)
                    buttonArrayList.add(AndesButton(context = this, buttonText = "Button $indexButton"))
                }
            }
            quantity < previousQuantity -> {
                val from = previousQuantity - quantity
                for (i in from downTo 1) {
                    binding.buttonPropertiesContent.removeViewAt(binding.buttonPropertiesContent.childCount - 1)
                    previousQuantity--
                    buttonArrayList.removeAt(buttonArrayList.size - 1)
                }
            }
        }
    }

    companion object {
        private const val BASE_ID = 100
        private const val DISTRIBUTION_MIXED = "Mixed"
    }
}