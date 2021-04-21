package com.mercadolibre.android.andesui.list.utils

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemChevron
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.type.AndesListType
import com.mercadolibre.android.andesui.thumbnail.AndesThumbnail
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

@Suppress("TooManyFunctions")
class AndesListAdapter(
    private val andesList: AndesList,
    private val delegate: AndesListDelegate,
    private var listType: AndesListType
) : RecyclerView.Adapter<AndesListAdapter.ViewHolder>() {

    companion object {
        const val SIMPLE = 0
        const val CHEVRON = 1
        const val CHECK_BOX = 2
        const val RADIO_BUTTON = 3
    }

    override fun getItemCount() = delegate.getDataSetSize(andesList)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(andesList, delegate, position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layout = when (viewType) {
            SIMPLE -> R.layout.andes_layout_list_item_simple
            CHEVRON -> R.layout.andes_layout_list_item_chevron
            CHECK_BOX -> R.layout.andes_layout_list_item_check_box
            RADIO_BUTTON -> R.layout.andes_layout_list_item_radio_button
            else -> R.layout.andes_layout_list_item_simple
        }

        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(layout, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return when (listType) {
            AndesListType.SIMPLE -> SIMPLE
            AndesListType.CHEVRON -> CHEVRON
            AndesListType.CHECK_BOX -> CHECK_BOX
            AndesListType.RADIO_BUTTON -> RADIO_BUTTON
        }
    }

    internal fun changeAndesListType(listType: AndesListType) {
        this.listType = listType
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var titleTextView: TextView
        private lateinit var subtitleTextView: TextView
        private lateinit var spaceTitleSubtitleView: View
        private lateinit var andesListItemContainer: View
        private lateinit var andesListItemSelectionView: View
        private lateinit var andesListItemAvatar: AndesThumbnail
        private lateinit var andesListItemIcon: ImageView
        private lateinit var andesViewThumbnailSeparator: View

        fun bind(andesList: AndesList, delegate: AndesListDelegate, position: Int) {
            val andesListItemConfig = delegate.bind(andesList, itemView, position)

            itemView.setOnClickListener { delegate.onItemClick(andesList, position) }

            findCommonViewsById()
            setDefaultCommonViewValues()

            when (andesListItemConfig) {
                is AndesListViewItemSimple -> bindSimpleItem(andesListItemConfig)
                is AndesListViewItemChevron -> bindChevronItem(andesListItemConfig)
            }
        }

        /**
         * Build Andes List item based on AndesListViewItem configuration for type SIMPLE
         *
         * @param itemConfig current AndesListViewItem config
         */
        private fun bindSimpleItem(itemConfig: AndesListViewItemSimple) {
            bindItemCommons(itemConfig)
        }

        /**
         * Build Andes List item based on AndesListViewItem configuration for type CHEVRON
         *
         * @param itemConfig current AndesListViewItem config
         */
        private fun bindChevronItem(itemConfig: AndesListViewItemChevron) {
            bindItemCommons(itemConfig)

            val andesListItemChevron: ImageView = itemView.findViewById(R.id.andes_thumbnail_chevron)
            val layoutParamsChevron = andesListItemChevron.layoutParams as ConstraintLayout.LayoutParams

            layoutParamsChevron.height = itemConfig.chevronSize
            layoutParamsChevron.width = itemConfig.chevronSize

            setViewItemPosition(andesListItemChevron) {
                calculateChevronTopMargin(itemConfig, andesListItemChevron)
            }
        }

        private fun findCommonViewsById() {
            titleTextView = itemView.findViewById(R.id.text_view_item_title)
            subtitleTextView = itemView.findViewById(R.id.text_view_item_sub_title)
            spaceTitleSubtitleView = itemView.findViewById<View>(R.id.view_space_title_subtitle)
            andesListItemContainer = itemView.findViewById<View>(R.id.andes_list_item_container)
            andesListItemSelectionView = itemView.findViewById<View>(R.id.view_item_selected)
            andesListItemAvatar = itemView.findViewById(R.id.andes_list_item_asset)
            andesViewThumbnailSeparator = itemView.findViewById(R.id.andesViewThumbnailSeparator)
            andesListItemIcon = itemView.findViewById(R.id.image_view_list_item_icon)
        }

        private fun setDefaultCommonViewValues() {
            // Default view states
            subtitleTextView.visibility = View.GONE
            spaceTitleSubtitleView.visibility = View.GONE
            andesListItemSelectionView.visibility = View.GONE
            andesViewThumbnailSeparator.visibility = View.GONE
            andesListItemAvatar.visibility = View.GONE
            andesListItemIcon.visibility = View.GONE

            // Default Title and Subtitle color
            titleTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.andes_gray_800))
            subtitleTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.andes_gray_450))

            // Title and Subtitle TypeFace
            titleTextView.typeface = itemView.context.getFontOrDefault(R.font.andes_font_regular)
            subtitleTextView.typeface = itemView.context.getFontOrDefault(R.font.andes_font_regular)
        }

        /**
         * Calculate margin top of Andes chevron
         */
        private fun calculateChevronTopMargin(andesListItemConfig: AndesListViewItemChevron, andesListItemChevron: View): Int {
            return if (andesListItemConfig.avatar != null) {
                getChevronTopMarginBasedInAvatar(andesListItemChevron)
            } else {
                getChevronTopMarginBasedInTitleFirstLine(andesListItemChevron)
            }
        }

        /**
         * Set view position vertically based on @param functionToCalculateTopMargin
         *
         * @param view current view to position vertically
         * @param functionToCalculateTopMargin function that returns the top margin that is applied
         * to the view
         */
        private fun setViewItemPosition(view: View, functionToCalculateTopMargin: () -> Int) {
            titleTextView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    titleTextView.viewTreeObserver.removeOnPreDrawListener(this)

                    val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
                    val topMargin = functionToCalculateTopMargin()

                    layoutParams.setMargins(
                            0,
                            topMargin,
                            0,
                            0
                    )

                    view.layoutParams = layoutParams

                    return true
                }
            })
        }

        /**
         * Build Andes List item based on AndesListViewItem configuration
         *
         * @param itemConfig current AndesListViewItem config
         */
        private fun bindItemCommons(itemConfig: AndesListViewItem) {
            setAndesListTitleConfiguration(itemConfig)

            if (itemConfig.showSubtitle && !itemConfig.subtitle.isNullOrEmpty()) {
                setAndesListSubtitleConfiguration(itemConfig)
            }

            itemConfig.itemSelected?.let {
                if (it) {
                    andesListItemSelectionView.visibility = View.VISIBLE
                    titleTextView.setTextColor(ContextCompat.getColor(
                            itemView.context,
                            R.color.andes_blue_ml_500
                    ))
                }
            }

            setAndesListIconConfiguration(itemConfig)

            setAndesListAvatarConfiguration(itemConfig)

            andesListItemContainer.setPadding(
                    itemConfig.paddingLeft,
                    itemConfig.paddingTop,
                    itemConfig.paddingRight,
                    itemConfig.paddingBottom
            )
        }

        /**
         * Set AndesList title configuration based on AndesListViewItem data
         *
         * @param itemConfig current AndesListViewItem config
         */
        private fun setAndesListTitleConfiguration(itemConfig: AndesListViewItem) {
            titleTextView.text = itemConfig.title
            titleTextView.maxLines = itemConfig.titleMaxLines
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemConfig.titleFontSize)
            titleTextView.typeface = itemConfig.titleTypeFace
            titleTextView.setTextColor(itemConfig.titleColor)
        }

        /**
         * Set AndesList subtitle configuration based on AndesListViewItem data
         *
         * @param itemConfig current AndesListViewItem config
         */
        private fun setAndesListSubtitleConfiguration(itemConfig: AndesListViewItem) {
            showSpaceBetweenTitleAndSubtitle(itemConfig.spaceTitleSubtitle)

            subtitleTextView.visibility = View.VISIBLE
            subtitleTextView.text = itemConfig.subtitle
            titleTextView.typeface = itemConfig.subtitleTypeFace
            subtitleTextView.setTextColor(itemConfig.subtitleColor)
            subtitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemConfig.subtitleFontSize)
        }

        /**
         * Set AndesList icon configuration based on AndesListViewItem config
         *
         * @param itemConfig current AndesListViewItem config
         */
        private fun setAndesListIconConfiguration(itemConfig: AndesListViewItem) {
            itemConfig.icon?.let {
                andesListItemIcon.visibility = View.VISIBLE
                andesListItemIcon.layoutParams.width = itemConfig.iconSize
                andesListItemIcon.layoutParams.height = itemConfig.iconSize
                andesListItemIcon.setImageDrawable(it)

                showSpaceBetweenAssetAndTitle(itemConfig.separatorThumbnailWidth)

                setViewItemPosition(andesListItemIcon) {
                    calculateIconTopMargin()
                }
            }
        }

        /**
         * Set AndesList Avatar configuration based on AndesListViewItem config
         *
         * @param itemConfig current AndesListViewItem config
         */
        private fun setAndesListAvatarConfiguration(itemConfig: AndesListViewItem) {
            itemConfig.avatar?.let {
                andesListItemAvatar.visibility = View.VISIBLE
                andesListItemAvatar.size = itemConfig.thumbnailSize
                andesListItemAvatar.image = it

                showSpaceBetweenAssetAndTitle(itemConfig.separatorThumbnailWidth)

                setViewItemPosition(andesListItemAvatar) {
                    calculateAvatarTopMargin()
                }
            }
        }

        /**
         * Get the top margin to be applied to the chevron based on avatar position.
         *
         * @param andesListItemChevron the view that contains the chevron image.
         */
        private fun getChevronTopMarginBasedInAvatar(andesListItemChevron: View): Int {
            val chevronHalfHeight = andesListItemChevron.height / 2
            val assetHalfHeight = andesListItemAvatar.height / 2
            val layoutParams = andesListItemAvatar.layoutParams as ConstraintLayout.LayoutParams

            return assetHalfHeight + layoutParams.topMargin - chevronHalfHeight
        }

        /**
         * Get the top margin to be applied to the chevron based on the first line of the title.
         *
         * @param andesListItemChevron the view that contains the chevron image.
         */
        private fun getChevronTopMarginBasedInTitleFirstLine(andesListItemChevron: View): Int {
            val layoutParams = titleTextView.layoutParams as ConstraintLayout.LayoutParams
            val chevronHalfHeight = andesListItemChevron.height / 2

            return getTitleHeightInTheFirstLine() / 2 + layoutParams.topMargin - chevronHalfHeight
        }

        /**
         * Calculate margin top of Andes icon based on title position
         */
        private fun calculateIconTopMargin(): Int {
            val layoutParams = titleTextView.layoutParams as ConstraintLayout.LayoutParams
            val assetHalfHeight = andesListItemIcon.height / 2

            return getTitleHeightInTheFirstLine() / 2 + layoutParams.topMargin - assetHalfHeight
        }

        /**
         * Get the height of the first line of the title.
         *
         */
        private fun getTitleHeightInTheFirstLine(): Int {
            val textViewTitleHeight = titleTextView.measuredHeight
            val numberOfLines = titleTextView.lineCount

            return (textViewTitleHeight / numberOfLines)
        }

        /**
         * Calculate Andes Avatar top margin to draw it correctly based on title and subtitle position
         */
        private fun calculateAvatarTopMargin(): Int {
            var topMargin = 0

            val layoutParams = titleTextView.layoutParams as ConstraintLayout.LayoutParams
            val assetHalfHeight = andesListItemAvatar.height / 2

            if (titleTextView.lineCount > 2) {
                val textViewTitleHeight = titleTextView.measuredHeight
                val numberOfLines = titleTextView.lineCount

                topMargin = (2 * textViewTitleHeight / numberOfLines) / 2 + layoutParams.topMargin - assetHalfHeight
            }

            return topMargin
        }

        private fun showSpaceBetweenTitleAndSubtitle(spaceHeight: Int) {
            spaceTitleSubtitleView.visibility = View.VISIBLE
            spaceTitleSubtitleView.layoutParams.height = spaceHeight
        }

        private fun showSpaceBetweenAssetAndTitle(spaceWidth: Int) {
            andesViewThumbnailSeparator.visibility = View.VISIBLE
            andesViewThumbnailSeparator.layoutParams.height = spaceWidth
        }
    }
}
