package com.mercadolibre.android.andesui.demoapp.components.thumbnail

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicThumbnailBadgeBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicThumbnailBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticThumbnailBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.shape.AndesThumbnailShape
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailState


class ThumbnailShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_thumbnail)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
            AndesuiDynamicThumbnailBinding.inflate(layoutInflater).root,
            AndesuiDynamicThumbnailBadgeBinding.inflate(layoutInflater).root,
            AndesuiStaticThumbnailBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        ThumbnailBadgeDynamicPage().create(this, adapter.views[1])
        addStaticPage(adapter.views[2])
    }

    @Suppress("ComplexMethod", "LongMethod")
    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicThumbnailBinding.bind(container)
        setupAssetTypeSpinner(binding)
        val hierarchySpinner = binding.hierarchySpinner
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_thumbnail_hierarchy_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hierarchySpinner.adapter = adapter
        }

        val scaleSpinner = binding.scaleSpinner
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_thumbnail_scale,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            scaleSpinner.adapter = adapter
        }

        val assetSpinner = binding.assetTypeSpinner
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_thumbnail_asset_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            assetSpinner.adapter = adapter
        }

        val shapeSpinner = binding.shapeSpinner
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_thumbnail_shape,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            shapeSpinner.adapter = adapter
        }

        val sizeSpinner = binding.sizeSpinner
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_thumbnail_size_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sizeSpinner.adapter = adapter
        }

        val stateSwitch = binding.stateSwitch
        val clearButton = binding.clearButton
        val changeButton = binding.changeButton
        val thumbnailTextInput = binding.thumbnailInitialsInput

        val andesThumbnail = binding.andesThumbnailIcon
        val demoImage =
            ResourcesCompat.getDrawable(resources, R.drawable.andes_otros_almanaque_20, theme)!!

        hierarchySpinner.setSelection(DEFAULT_HIERARCHY_OPTION)
        sizeSpinner.setSelection(DEFAULT_SIZE_OPTION)
        assetSpinner.setSelection(DEFAULT_TYPE_OPTION)
        scaleSpinner.setSelection(DEFAULT_SCALE_OPTION)
        stateSwitch.isChecked = true

        clearButton.setOnClickListener {
            binding.group.visibility = View.VISIBLE
            andesThumbnail.visibility = View.VISIBLE

            hierarchySpinner.setSelection(DEFAULT_HIERARCHY_OPTION)
            sizeSpinner.setSelection(DEFAULT_SIZE_OPTION)
            assetSpinner.setSelection(DEFAULT_TYPE_OPTION)
            shapeSpinner.setSelection(DEFAULT_TYPE_OPTION)
            scaleSpinner.setSelection(DEFAULT_SCALE_OPTION)
            stateSwitch.isChecked = true
            andesThumbnail.hierarchy = AndesThumbnailHierarchy.LOUD
            andesThumbnail.size = AndesThumbnailSize.SIZE_48
            andesThumbnail.accentColor = AndesColor(R.color.andes_red_700)
            andesThumbnail.state = AndesThumbnailState.ENABLED
            andesThumbnail.thumbnailShape = AndesThumbnailShape.Circle
            andesThumbnail.assetType = AndesThumbnailAssetType.Icon(demoImage)
            andesThumbnail.image = demoImage
        }

        changeButton.setOnClickListener {

            val hierarchy =
                when (hierarchySpinner.getItemAtPosition(hierarchySpinner.selectedItemPosition)) {
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

            val assetType =
                when (assetSpinner.getItemAtPosition(assetSpinner.selectedItemPosition)) {
                    "Icon" -> AndesThumbnailAssetType.Icon(demoImage)
                    "Image" -> AndesThumbnailAssetType.Image(demoImage)
                    "Text" -> AndesThumbnailAssetType.Text(binding.thumbnailInitialsInput.text.toString())
                    else -> AndesThumbnailAssetType.Icon(demoImage)
                }

            val shape = when (shapeSpinner.getItemAtPosition(shapeSpinner.selectedItemPosition)) {
                "Circle" -> AndesThumbnailShape.Circle
                "Square" -> AndesThumbnailShape.Square
                else -> AndesThumbnailShape.Circle
            }

            val scale = when (scaleSpinner.getItemAtPosition(scaleSpinner.selectedItemPosition)) {
                "center" -> ImageView.ScaleType.CENTER
                "fitXY" -> ImageView.ScaleType.FIT_XY
                "centerCrop" -> ImageView.ScaleType.CENTER_CROP
                "fitCenter" -> ImageView.ScaleType.FIT_CENTER
                "centerInside" -> ImageView.ScaleType.CENTER_INSIDE
                "fitEnd" -> ImageView.ScaleType.FIT_END
                "fitStart" -> ImageView.ScaleType.FIT_START
                "matrix" -> ImageView.ScaleType.MATRIX
                else -> ImageView.ScaleType.CENTER_CROP
            }
            if(assetType is AndesThumbnailAssetType.Text) {
                val inputText = thumbnailTextInput.text ?: ""
                if (inputText.isBlank()) {
                    thumbnailTextInput.state = AndesTextfieldState.ERROR
                    thumbnailTextInput.helper = CAMPO_OBLIGATORIO
                    return@setOnClickListener
                } else {
                    thumbnailTextInput.state = AndesTextfieldState.IDLE
                }
            }
            andesThumbnail.assetType = assetType
            andesThumbnail.thumbnailShape = shape
            andesThumbnail.scaleType = scale
            andesThumbnail.hierarchy = hierarchy
            andesThumbnail.size = size
            andesThumbnail.state = if (stateSwitch.isChecked) {
                AndesThumbnailState.ENABLED
            } else {
                AndesThumbnailState.DISABLED
            }
        }
    }

    private fun setupAssetTypeSpinner( binding: AndesuiDynamicThumbnailBinding) {
        with(binding.assetTypeSpinner) {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (selectedItem) {
                        "Icon" -> {
                            binding.initialsGroup.visibility = View.GONE
                            binding.thumbnailInitialsInput.text = ""
                            binding.scaleGroup.visibility = View.GONE
                            binding.hierarchyGroup.visibility = View.VISIBLE
                        }

                        "Image" -> {
                            binding.initialsGroup.visibility = View.GONE
                            binding.thumbnailInitialsInput.text = ""
                            binding.scaleGroup.visibility = View.VISIBLE
                            binding.hierarchyGroup.visibility = View.GONE
                        }
                        "Text" -> {
                            binding.initialsGroup.visibility = View.VISIBLE
                            binding.scaleGroup.visibility = View.GONE
                            binding.hierarchyGroup.visibility = View.VISIBLE
                        }
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }
    }

    private fun addStaticPage(container: View) {
        AndesuiStaticThumbnailBinding.bind(container).andesuiDemoappAndesSpecsButton.setOnClickListener {
            launchSpecs(container.context, AndesSpecs.THUMBNAIL)
        }
    }

    companion object {
        const val DEFAULT_HIERARCHY_OPTION = 1
        const val DEFAULT_TYPE_OPTION = 0
        const val DEFAULT_SIZE_OPTION = 3
        const val DEFAULT_SCALE_OPTION = 2
        const val CAMPO_OBLIGATORIO = "Campo obligatorio"
    }
}
