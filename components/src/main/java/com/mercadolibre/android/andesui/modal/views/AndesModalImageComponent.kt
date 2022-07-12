package com.mercadolibre.android.andesui.modal.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.databinding.AndesModalImageHeaderBinding
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalCardContentVariation
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalCardContentVariationThumbnail
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalContentVariationInterface
import com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces.AndesModalFullContentVariationThumbnail
import com.mercadolibre.android.andesui.utils.setDrawableSuspending

/**
 * Internal image component for the modal.
 * It will display either a plain image or an andesThumbnail, according to the chosen [contentVariation]
 */
internal class AndesModalImageComponent(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private val binding by lazy {
        AndesModalImageHeaderBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var contentVariation: AndesModalContentVariationInterface = DEFAULT_VARIATION
        set(value) {
            setupImageContent(value)
            field = value
        }

    var imageDrawable: Drawable? = null
        set(value) {
            setupImage(value)
            field = value
        }

    /**
     * Method that accepts a suspended lambda block that returns a Drawable.
     * Use this method to load images asynchronously into the component
     */
    fun setDrawableSuspended(suspendedDrawable: (suspend () -> Drawable?)) {
        setDrawableSuspending(suspendedDrawable) {
            imageDrawable = it
        }
    }

    private fun setupImage(image: Drawable?) {
        image?.let {
            if (contentVariation in arrayListOf(AndesModalCardContentVariationThumbnail, AndesModalFullContentVariationThumbnail)) {
                binding.thumbnail.image = it
            } else {
                binding.banner.setImageDrawable(it)
            }
        }
    }

    private fun setupImageContent(variation: AndesModalContentVariationInterface) {
        setVisibility(variation)
        val view = if (variation in arrayListOf(AndesModalCardContentVariationThumbnail, AndesModalFullContentVariationThumbnail)) {
            binding.thumbnail
        } else {
            binding.banner
        }
        setMarginImage(view, variation.getMarginTop(context))
        setImageSize(view, variation.getWidth(context), variation.getHeight(context))
    }

    private fun setVisibility(variation: AndesModalContentVariationInterface) {
        binding.banner.visibility = variation.bannerVisibility
        binding.thumbnail.visibility = variation.thumbnailVisibility
    }

    private fun setImageSize(view: View, width: Int, height: Int) {
        if (width != FULL_SIZE && height != FULL_SIZE) {
            view.layoutParams.apply {
                this.height = height
                this.width = width
            }
        }
    }

    private fun setMarginImage(view: View, marginTop: Int) {
        val layoutParam = view.layoutParams as LayoutParams
        layoutParam.setMargins(MARGIN_ZERO, marginTop, MARGIN_ZERO, MARGIN_ZERO)
        view.layoutParams = layoutParam
    }

    override fun getAccessibilityClassName(): CharSequence {
        return ImageView::class.java.name
    }

    companion object {
        private const val FULL_SIZE = 0
        private const val MARGIN_ZERO = 0
        private val DEFAULT_VARIATION = AndesModalCardContentVariation.NONE.variation
    }
}
