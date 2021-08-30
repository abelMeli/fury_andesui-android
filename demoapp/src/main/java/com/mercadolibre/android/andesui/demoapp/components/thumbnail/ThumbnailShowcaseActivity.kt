package com.mercadolibre.android.andesui.demoapp.components.thumbnail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.thumbnail.AndesThumbnail
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailState
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType
import kotlinx.android.synthetic.main.andesui_dynamic_thumbnail.*

class ThumbnailShowcaseActivity : AppCompatActivity() {

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
        supportActionBar?.title = resources.getString(R.string.andes_demoapp_screen_thumbnail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViewPager() {
        val inflater = LayoutInflater.from(this)
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
                inflater.inflate(R.layout.andesui_dynamic_thumbnail, null, false),
                inflater.inflate(R.layout.andesui_dynamic_thumbnail_badge, null, false),
                inflater.inflate(R.layout.andesui_static_thumbnail, null, false)
        ))
    }

    private fun attachIndicator() {
        val indicator = findViewById<PageIndicator>(R.id.page_indicator)
        indicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        ThumbnailBadgeDynamicPage().create(this, adapter.views[1])
        addStaticPage(adapter.views[2])
    }

    @Suppress("ComplexMethod", "LongMethod")
    private fun addDynamicPage(container: View) {
        val hierarchySpinner: Spinner = container.findViewById(R.id.hierarchy_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_thumbnail_hierarchy_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hierarchySpinner.adapter = adapter
        }

        val typeSpinner: Spinner = container.findViewById(R.id.simple_type_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_thumbnail_type_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeSpinner.adapter = adapter
        }

        val sizeSpinner: Spinner = container.findViewById(R.id.size_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_thumbnail_size_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sizeSpinner.adapter = adapter
        }

        val stateSwitch: SwitchCompat = container.findViewById(R.id.state_switch)

        val clearButton: AndesButton = container.findViewById(R.id.clear_button)
        val changeButton: AndesButton = container.findViewById(R.id.change_button)

        val andesThumbnail: AndesThumbnail = container.findViewById(R.id.andes_thumbnail_icon)

        hierarchySpinner.setSelection(DEFAULT_HIERARCHY_OPTION)
        typeSpinner.setSelection(DEFAULT_TYPE_OPTION)
        sizeSpinner.setSelection(DEFAULT_SIZE_OPTION)
        stateSwitch.isChecked = true

        clearButton.setOnClickListener {
            group.visibility = View.VISIBLE
            andesThumbnail.visibility = View.VISIBLE

            hierarchySpinner.setSelection(DEFAULT_HIERARCHY_OPTION)
            typeSpinner.setSelection(DEFAULT_TYPE_OPTION)
            sizeSpinner.setSelection(DEFAULT_SIZE_OPTION)
            stateSwitch.isChecked = true

            andesThumbnail.type = AndesThumbnailType.ICON
            andesThumbnail.hierarchy = AndesThumbnailHierarchy.LOUD
            andesThumbnail.size = AndesThumbnailSize.SIZE_48
            andesThumbnail.accentColor = AndesColor(R.color.andes_red_700)
            andesThumbnail.state = AndesThumbnailState.ENABLED
        }

        changeButton.setOnClickListener {
            val type = when (typeSpinner.getItemAtPosition(typeSpinner.selectedItemPosition)) {
                "Icon" -> AndesThumbnailType.ICON
                "Image Circle" -> AndesThumbnailType.IMAGE_CIRCLE
                "Image Square" -> AndesThumbnailType.IMAGE_SQUARE
                else -> AndesThumbnailType.ICON
            }

            val hierarchy = when (hierarchySpinner.getItemAtPosition(hierarchySpinner.selectedItemPosition)) {
                "Loud" -> AndesThumbnailHierarchy.LOUD
                "Quiet" -> AndesThumbnailHierarchy.QUIET
                "Default" -> AndesThumbnailHierarchy.DEFAULT
                else -> AndesThumbnailHierarchy.LOUD
            }

            val size = when (sizeSpinner.getItemAtPosition(sizeSpinner.selectedItemPosition)) {
                "Size 24" -> AndesThumbnailSize.SIZE_24
                "Size 32" -> AndesThumbnailSize.SIZE_32
                "Size 40" -> AndesThumbnailSize.SIZE_40
                "Size 48" -> AndesThumbnailSize.SIZE_48
                "Size 56" -> AndesThumbnailSize.SIZE_56
                "Size 64" -> AndesThumbnailSize.SIZE_64
                "Size 72" -> AndesThumbnailSize.SIZE_72
                "Size 80" -> AndesThumbnailSize.SIZE_80
                else -> AndesThumbnailSize.SIZE_48
            }

            andesThumbnail.type = type
            andesThumbnail.hierarchy = hierarchy
            andesThumbnail.size = size
            andesThumbnail.state = if (stateSwitch.isChecked) {
                AndesThumbnailState.ENABLED
            } else {
                AndesThumbnailState.DISABLED
            }
        }
    }

    private fun addStaticPage(container: View) {
        container.findViewById<AndesButton>(R.id.andesui_demoapp_andes_specs_button).setOnClickListener {
            launchSpecs(container.context, AndesSpecs.THUMBNAIL)
        }
    }

    companion object {
        const val DEFAULT_HIERARCHY_OPTION = 1
        const val DEFAULT_TYPE_OPTION = 0
        const val DEFAULT_SIZE_OPTION = 3
    }
}
