package com.mercadolibre.android.andesui.demoapp.components.list

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.list.type.AndesListType
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

@SuppressWarnings("TooManyFunctions")
class ListShowcaseActivity : BaseActivity(), AndesListDelegate {

    private lateinit var viewPager: CustomViewPager
    private lateinit var andesListStatic: AndesList
    private lateinit var andesListDynamic: AndesList

    private var itemStaticSelected: Int = -1
    private var itemDynamicSelected: Int = -1
    private var dynamicTitle: String = ITEM_TITLE
    private var dynamicSubtitle: String = ITEM_SUBTITLE
    private var dynamicMaxLines: Int = DEFAULT_TITLE_NUMBER_OF_LINES
    private var avatar: Drawable? = null
    private var icon: Drawable? = null

    companion object {
        const val LIST_SIZE = 100
        const val DEFAULT_TITLE_NUMBER_OF_LINES = 1
        const val ITEM_TITLE = "Lorem ipsum"
        const val ITEM_SUBTITLE = "Lorem ipsum dolor sit amet description"
        const val ANDES_LIST_STATIC = "AndesListStatic"
        const val ANDES_LIST_DYNAMIC = "AndesListDynamic"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_list)

    private fun initViewPager() {
        val inflater = LayoutInflater.from(this)
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
                inflater.inflate(R.layout.andesui_dynamic_list, null, false),
                inflater.inflate(R.layout.andesui_static_list, null, false)
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
        val textFieldTitle = container.findViewById<AndesTextfield>(R.id.title)
        val textFieldSubtitle = container.findViewById<AndesTextfield>(R.id.subtitle)
        val textFieldMaxLines = container.findViewById<AndesTextfield>(R.id.max_lines)

        andesListDynamic = container.findViewById(R.id.andesList)
        andesListDynamic.tag = ANDES_LIST_DYNAMIC
        andesListDynamic.dividerItemEnabled = true
        andesListDynamic.delegate = this

        andesListDynamic.size = AndesListViewItemSize.SMALL
        andesListDynamic.type = AndesListType.SIMPLE
        andesListDynamic.refreshListAdapter()

        val listSize: Spinner = container.findViewById(R.id.list_size_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_list_size_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            listSize.adapter = adapter
        }

        val listType: Spinner = container.findViewById(R.id.list_type_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_list_type_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            listType.adapter = adapter
        }

        val listAsset: Spinner = container.findViewById(R.id.list_asset_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_list_asset_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            listAsset.adapter = adapter
        }

        textFieldTitle.text = ITEM_TITLE
        textFieldSubtitle.text = ITEM_SUBTITLE
        textFieldMaxLines.text = DEFAULT_TITLE_NUMBER_OF_LINES.toString()

        container.findViewById<AndesButton>(R.id.change_button).setOnClickListener {
            getAssetSelected(listAsset)
            val type = getTypeSelected(listType)
            val size = getSizeSelected(listSize)
            val statusTitle = setTextFieldStatus(textFieldTitle)
            val statusSubtitle = setTextFieldStatus(textFieldSubtitle)
            val statusMaxLines = setTextFieldStatus(textFieldMaxLines)

            if (statusTitle || statusSubtitle || statusMaxLines) {
                return@setOnClickListener
            }

            dynamicTitle = textFieldTitle.text.toString()
            dynamicSubtitle = textFieldSubtitle.text.toString()
            dynamicMaxLines = try {
                textFieldMaxLines.text!!.toInt()
            } catch (e: Exception) {
                0
            }

            andesListDynamic.size = size
            andesListDynamic.type = type
            andesListDynamic.refreshListAdapter()
        }

        container.findViewById<AndesButton>(R.id.clear_button).setOnClickListener {
            listSize.setSelection(0)
            listType.setSelection(0)
            listAsset.setSelection(0)
            textFieldTitle.text = ITEM_TITLE
            textFieldSubtitle.text = ITEM_SUBTITLE
            textFieldMaxLines.text = DEFAULT_TITLE_NUMBER_OF_LINES.toString()
            avatar = null
            icon = null

            itemDynamicSelected = -1
            dynamicTitle = ITEM_TITLE
            dynamicSubtitle = ITEM_SUBTITLE
            dynamicMaxLines = DEFAULT_TITLE_NUMBER_OF_LINES

            andesListDynamic.size = AndesListViewItemSize.SMALL
            andesListDynamic.type = AndesListType.SIMPLE

            setTextFieldStatus(textFieldTitle)
            setTextFieldStatus(textFieldSubtitle)
            setTextFieldStatus(textFieldMaxLines)

            andesListDynamic.refreshListAdapter()
        }
    }

    private fun setTextFieldStatus(textField: AndesTextfield): Boolean {
        return if (textField.text.isNullOrEmpty()) {
            textField.state = AndesTextfieldState.ERROR
            textField.helper = "Requerido"
            true
        } else {
            textField.state = AndesTextfieldState.IDLE
            textField.helper = null
            false
        }
    }

    private fun getTypeSelected(spinnerType: Spinner): AndesListType {
        return when (spinnerType.selectedItem) {
            "Simple" -> AndesListType.SIMPLE
            "Chevron" -> AndesListType.CHEVRON
            else -> AndesListType.SIMPLE
        }
    }

    private fun getAssetSelected(spinnerAsset: Spinner) {
        return when (spinnerAsset.selectedItem) {
            "Icon" -> {
                icon = ContextCompat.getDrawable(this, R.drawable.andes_envio_envio_24)
                avatar = null
            }
            "Thumbnail" -> {
                icon = null
                avatar = ContextCompat.getDrawable(this, R.drawable.andes_otros_almanaque_20)
            }
            else -> {
                icon = null
                avatar = null
            }
        }
    }

    private fun getSizeSelected(spinnerSize: Spinner): AndesListViewItemSize {
        return when (spinnerSize.selectedItem) {
            "Small" -> AndesListViewItemSize.SMALL
            "Medium" -> AndesListViewItemSize.MEDIUM
            "Large" -> AndesListViewItemSize.LARGE
            else -> AndesListViewItemSize.SMALL
        }
    }

    private fun addStaticPage(container: View) {
        container.findViewById<AndesButton>(R.id.andesui_demoapp_andes_checkbox_specs_button).setOnClickListener {
            launchSpecs(it.context, AndesSpecs.LIST)
        }

        andesListStatic = container.findViewById(R.id.andesList)
        andesListStatic.tag = ANDES_LIST_STATIC
        andesListStatic.dividerItemEnabled = true
        andesListStatic.delegate = this
    }

    override fun onItemClick(andesList: AndesList, position: Int) {
        Toast.makeText(this, "Position of item selected $position", Toast.LENGTH_SHORT).show()
        if (andesList.tag == andesListStatic.tag) {
            itemStaticSelected = position
            andesListStatic.refreshListAdapter()
        } else {
            itemDynamicSelected = position
            andesListDynamic.refreshListAdapter()
        }
    }

    override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
        return if (andesList.tag == andesListStatic.tag) {
            AndesListViewItemSimple(
                    context = this,
                    title = "$ITEM_TITLE $position",
                    subtitle = ITEM_SUBTITLE,
                    size = andesList.size,
                    icon = null,
                    avatar = ContextCompat.getDrawable(this, R.drawable.andes_otros_almanaque_20),
                    titleMaxLines = DEFAULT_TITLE_NUMBER_OF_LINES,
                    itemSelected = itemStaticSelected == position
            )
        } else {
            AndesListViewItemSimple(
                    context = this,
                    title = dynamicTitle,
                    subtitle = dynamicSubtitle,
                    size = andesList.size,
                    icon = icon,
                    avatar = avatar,
                    titleMaxLines = dynamicMaxLines,
                    itemSelected = itemDynamicSelected == position
            )
        }
    }

    override fun getDataSetSize(andesList: AndesList): Int {
        return LIST_SIZE
    }
}
