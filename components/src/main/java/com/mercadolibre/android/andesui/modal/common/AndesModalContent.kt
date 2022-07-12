package com.mercadolibre.android.andesui.modal.common

import android.graphics.drawable.Drawable

/**
 * Data class that represents the modal content.
 *
 * @param title the optional title to display
 * @param subtitle the optional body text to display
 * @param assetDrawable the optional asset to show in the header
 * @param suspendedDrawable optional way to set a drawable in the modal asynchronously, passing a suspend fun
 * @param assetContentDescription the optional content description for the header image
 */
data class AndesModalContent(
    val title: CharSequence? = null,
    val subtitle: CharSequence? = null,
    val assetDrawable: Drawable? = null,
    val assetContentDescription: String? = null,
    val suspendedDrawable: (suspend () -> Drawable?)? = null
)
