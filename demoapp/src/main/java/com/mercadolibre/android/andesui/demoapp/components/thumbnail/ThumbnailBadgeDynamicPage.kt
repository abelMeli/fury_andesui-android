package com.mercadolibre.android.andesui.demoapp.components.thumbnail

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.ArrayRes
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.Group
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.utils.Constants.EMPTY_STRING
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.thumbnail.AndesThumbnailBadge
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgeDotSize
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgePillSize
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType

/**
 * Class responsible of creating AndesThumbnailBadge page
 */
@Suppress("TooManyFunctions")
class ThumbnailBadgeDynamicPage {

    private lateinit var thumbnailBadgePillTextStyleCaps: SwitchCompat
    private lateinit var thumbnailBadgePillText: AndesTextfield
    private lateinit var updateButton: AndesButton
    private lateinit var clearButton: AndesButton
    private lateinit var thumbnailBadgePillConfigs: Group
    private lateinit var thumbnailBadgeSizeSpinner: Spinner
    private lateinit var thumbnailBadgeComponentTypeSpinner: Spinner
    private lateinit var thumbnailBadgeComponentSpinner: Spinner
    private lateinit var thumbnailTypeSpinner: Spinner
    private lateinit var thumbnailBadge: AndesThumbnailBadge

    companion object {

        private const val BADGE_COMPONENT_ICON_PILL = "Icon Pill"
        private const val BADGE_COMPONENT_PILL = "Pill"
        private const val BADGE_COMPONENT_DOT = "Dot"
        private const val BADGE_COMPONENT_DEFAULT_POSITION = 0

        private const val THUMBNAIL_BADGE_TYPE_ICON = "Icon"
        private const val THUMBNAIL_BADGE_TYPE_IMAGE_CIRCLE = "Image Circle"
        private const val THUMBNAIL_BADGE_TYPE_DEFAULT_POSITION = 0

        private const val THUMBNAIL_BADGE_SIZE_24 = "Size 24"
        private const val THUMBNAIL_BADGE_SIZE_32 = "Size 32"
        private const val THUMBNAIL_BADGE_SIZE_40 = "Size 40"
        private const val THUMBNAIL_BADGE_SIZE_48 = "Size 48"
        private const val THUMBNAIL_BADGE_SIZE_56 = "Size 56"
        private const val THUMBNAIL_BADGE_SIZE_64 = "Size 64"
        private const val THUMBNAIL_BADGE_SIZE_72 = "Size 72"
        private const val THUMBNAIL_BADGE_SIZE_80 = "Size 80"
        private const val DEFAULT_DOT_SIZE_POSITION = 0
        private const val DEFAULT_PILL_SIZE_POSITION = 3

        private const val THUMBNAIL_BADGE_COLOR_HIGHLIGHT = "Highlight"
        private const val THUMBNAIL_BADGE_COLOR_SUCCESS = "Success"
        private const val THUMBNAIL_BADGE_COLOR_WARNING = "Warning"
        private const val THUMBNAIL_BADGE_COLOR_ERROR = "Error"
        private const val THUMBNAIL_BADGE_COLOR_DEFAULT_POSITION = 0
    }

    /**
     * Function used to bind data to the container view.
     */
    fun create(context: Context, container: View) {
        initComponents(container)

        setupSpinners(context)
        setupBadgeComponentGroups(context)
        setupActionButtons(context)
    }

    private fun setupActionButtons(context: Context) {
        updateButton.setOnClickListener {
            updateComponent()
        }

        clearButton.setOnClickListener {
            thumbnailBadgeComponentSpinner.setSelection(BADGE_COMPONENT_DEFAULT_POSITION)
            setupBadgeComponentConfig(
                context,
                View.GONE,
                R.array.andes_thumbnail_badge_pill_size_spinner,
                DEFAULT_PILL_SIZE_POSITION
            )
            thumbnailBadgeComponentTypeSpinner.setSelection(THUMBNAIL_BADGE_COLOR_DEFAULT_POSITION)
            thumbnailTypeSpinner.setSelection(THUMBNAIL_BADGE_TYPE_DEFAULT_POSITION)
            thumbnailBadgePillTextStyleCaps.isChecked = true
            thumbnailBadgePillText.text = EMPTY_STRING
            updateComponent()
        }
    }

    private fun updateComponent() {
        thumbnailBadge.thumbnailType = getThumbnailBadgeType()
        thumbnailBadge.badgeComponent = getThumbnailBadgeComponent()
    }

    private fun getThumbnailBadgeType() = when (thumbnailTypeSpinner.selectedItem) {
        THUMBNAIL_BADGE_TYPE_ICON -> AndesThumbnailBadgeType.Icon
        THUMBNAIL_BADGE_TYPE_IMAGE_CIRCLE -> AndesThumbnailBadgeType.ImageCircle
        else -> AndesThumbnailBadgeType.Icon
    }

    private fun getThumbnailBadgeComponent() = when (thumbnailBadgeComponentSpinner.selectedItem) {
        BADGE_COMPONENT_ICON_PILL -> AndesThumbnailBadgeComponent.IconPill(
            getBadgeColor(),
            getThumbnailPillSize()
        )
        BADGE_COMPONENT_PILL -> AndesThumbnailBadgeComponent.Pill(
            getBadgeColor(),
            thumbnailBadgePillText.text,
            thumbnailBadgePillTextStyleCaps.isChecked,
            getThumbnailPillSize()
        )
        BADGE_COMPONENT_DOT -> AndesThumbnailBadgeComponent.Dot(
            getBadgeColor(),
            getThumbnailDotSize()
        )
        else -> AndesThumbnailBadgeComponent.IconPill(
            getBadgeColor(),
            getThumbnailPillSize()
        )
    }

    private fun getThumbnailDotSize() = when (thumbnailBadgeSizeSpinner.selectedItem) {
        THUMBNAIL_BADGE_SIZE_32 -> AndesThumbnailBadgeDotSize.SIZE_32
        THUMBNAIL_BADGE_SIZE_24 -> AndesThumbnailBadgeDotSize.SIZE_24
        else -> AndesThumbnailBadgeDotSize.SIZE_24
    }

    private fun getThumbnailPillSize() = when (thumbnailBadgeSizeSpinner.selectedItem) {
        THUMBNAIL_BADGE_SIZE_80 -> AndesThumbnailBadgePillSize.SIZE_80
        THUMBNAIL_BADGE_SIZE_72 -> AndesThumbnailBadgePillSize.SIZE_72
        THUMBNAIL_BADGE_SIZE_64 -> AndesThumbnailBadgePillSize.SIZE_64
        THUMBNAIL_BADGE_SIZE_56 -> AndesThumbnailBadgePillSize.SIZE_56
        THUMBNAIL_BADGE_SIZE_48 -> AndesThumbnailBadgePillSize.SIZE_48
        THUMBNAIL_BADGE_SIZE_40 -> AndesThumbnailBadgePillSize.SIZE_40
        else -> AndesThumbnailBadgePillSize.SIZE_64
    }

    private fun getBadgeColor() = when (thumbnailBadgeComponentTypeSpinner.selectedItem) {
        THUMBNAIL_BADGE_COLOR_HIGHLIGHT -> AndesBadgeIconType.HIGHLIGHT
        THUMBNAIL_BADGE_COLOR_SUCCESS -> AndesBadgeIconType.SUCCESS
        THUMBNAIL_BADGE_COLOR_WARNING -> AndesBadgeIconType.WARNING
        THUMBNAIL_BADGE_COLOR_ERROR -> AndesBadgeIconType.ERROR
        else -> AndesBadgeIconType.HIGHLIGHT
    }

    private fun setupBadgeComponentGroups(context: Context) {
        thumbnailBadgeComponentSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (thumbnailBadgeComponentSpinner.selectedItem) {
                        BADGE_COMPONENT_ICON_PILL -> setupBadgeComponentConfig(
                            context,
                            View.GONE,
                            R.array.andes_thumbnail_badge_pill_size_spinner,
                            DEFAULT_PILL_SIZE_POSITION
                        )
                        BADGE_COMPONENT_PILL -> setupBadgeComponentConfig(
                            context,
                            View.VISIBLE,
                            R.array.andes_thumbnail_badge_pill_size_spinner,
                            DEFAULT_PILL_SIZE_POSITION
                        )
                        BADGE_COMPONENT_DOT -> setupBadgeComponentConfig(
                            context,
                            View.GONE,
                            R.array.andes_thumbnail_badge_dot_size_spinner,
                            DEFAULT_DOT_SIZE_POSITION
                        )
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // no-op
                }
            }
    }

    private fun setupBadgeComponentConfig(
        context: Context,
        pillConfigsVisibility: Int,
        @ArrayRes sizesArray: Int,
        defaultPosition: Int
    ) {
        thumbnailBadgePillConfigs.visibility = pillConfigsVisibility
        thumbnailBadgeSizeSpinner.let { spinner ->
            setupSpinnerComponent(
                context,
                spinner,
                sizesArray
            )
            spinner.setSelection(defaultPosition)
        }
    }

    private fun setupSpinners(context: Context) {
        setupSpinnerComponent(
            context,
            thumbnailTypeSpinner,
            R.array.andes_thumbnail_badge_type_spinner
        )
        setupSpinnerComponent(
            context,
            thumbnailBadgeComponentSpinner,
            R.array.andes_thumbnail_badge_component_spinner
        )
        setupSpinnerComponent(
            context,
            thumbnailBadgeComponentTypeSpinner,
            R.array.andes_thumbnail_badge_component_type_spinner
        )
        setupSpinnerComponent(
            context,
            thumbnailBadgeSizeSpinner,
            R.array.andes_thumbnail_badge_pill_size_spinner
        ).also {
            thumbnailBadgeSizeSpinner.setSelection(DEFAULT_PILL_SIZE_POSITION)
        }
    }

    private fun initComponents(container: View) {
        thumbnailBadge = container.findViewById(R.id.andes_thumbnail_badge_icon)
        thumbnailTypeSpinner = container.findViewById(R.id.thumbnail_badge_type_spinner)
        thumbnailBadgeComponentSpinner =
            container.findViewById(R.id.thumbnail_badge_component_spinner)
        thumbnailBadgeComponentTypeSpinner =
            container.findViewById(R.id.thumbnail_badge_component_type_spinner)
        thumbnailBadgeSizeSpinner = container.findViewById(R.id.thumbnail_badge_size_spinner)
        thumbnailBadgePillConfigs = container.findViewById(R.id.thumbnail_badge_pill_configs)
        clearButton = container.findViewById(R.id.clear_button)
        updateButton = container.findViewById(R.id.change_button)
        thumbnailBadgePillText = container.findViewById(R.id.thumbnail_badge_pill_text)
        thumbnailBadgePillTextStyleCaps =
            container.findViewById(R.id.thumbnail_badge_pill_text_style_switch)
    }

    private fun setupSpinnerComponent(context: Context, spinner: Spinner, @ArrayRes content: Int) {
        ArrayAdapter.createFromResource(
            context,
            content,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }
}
