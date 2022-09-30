package com.mercadolibre.android.andesui.demoapp.components.a11yplayground.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import com.mercadolibre.android.andesui.demoapp.databinding.CustomActionItemBinding

class CustomActionItem(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(
    context, attributeSet
) {
    private val binding by lazy {
        CustomActionItemBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var title: CharSequence?
        get() = binding.itemTvTitle.text
        set(value) {
            binding.itemTvTitle.text = value
        }

    var subtitle: CharSequence?
        get() = binding.itemTvSubtitle.text
        set(value) {
            binding.itemTvSubtitle.text = value
        }

    var image: Drawable?
        get() = binding.itemIv.drawable
        set(value) {
            binding.itemIv.setImageDrawable(value)
        }

    var imageContentDescription: CharSequence?
        get() = binding.itemIv.contentDescription
        set(value) {
            binding.itemIv.contentDescription = value
        }

    var onFavoriteClick: OnClickListener? = null
        set(value) {
            field = value
            binding.itemIvFavorite.setOnClickListener(value)
        }

    var onCartClick: OnClickListener? = null
        set(value) {
            field = value
            binding.itemIvCart.setOnClickListener(value)
        }

    var onInfoClick: OnClickListener? = null
        set(value) {
            field = value
            binding.itemIvInfo.setOnClickListener(value)
        }

    var isSemanticView: Boolean = false
        set(value) {
            field = value
            setupSemanticView(value)
        }

    private val a11yActionIds = mutableListOf<Int>()

    /**
     * According to the setup we want to make, when marking the component as semantic view
     * we will ignore all the internal components, mark the parent view as focusable and assign
     * the title and subtitle concatenated as its content description.
     * Additionally, we will add one custom action for each listener to this same parent container.
     *
     * Otherwise, if we want to separate the view, we should allow internal items to gain focus,
     * mark the parent as not focusable and remove its content description.
     */
    private fun setupSemanticView(isSemanticView: Boolean) {
        if (isSemanticView) {
            binding.itemContainer.importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
            binding.root.contentDescription = "$title, $subtitle."
            binding.root.isFocusable = true
        } else {
            binding.itemContainer.importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_AUTO
            binding.root.contentDescription = null
            binding.root.isFocusable = false
        }
        setupA11yActions(isSemanticView)
    }

    /**
     * Previously remove old actions to avoid repeating it.
     */
    private fun setupA11yActions(isSemanticView: Boolean) {
        a11yActionIds.forEach {
            ViewCompat.removeAccessibilityAction(binding.root, it)
        }
        if (isSemanticView) {
            val favoriteAction = ViewCompat.addAccessibilityAction(binding.root, "Add to favorites") { _, _ ->
                onFavoriteClick?.onClick(binding.itemIvFavorite)
                true
            }
            val cartAction = ViewCompat.addAccessibilityAction(binding.root, "Add to cart") { _, _ ->
                onCartClick?.onClick(binding.itemIvCart)
                true
            }
            val infoAction = ViewCompat.addAccessibilityAction(binding.root, "More info") { _, _ ->
                onInfoClick?.onClick(binding.itemIvInfo)
                true
            }
            a11yActionIds.add(favoriteAction)
            a11yActionIds.add(cartAction)
            a11yActionIds.add(infoAction)
        }
    }
}
