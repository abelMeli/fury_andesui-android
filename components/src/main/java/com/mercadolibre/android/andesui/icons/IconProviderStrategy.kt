package com.mercadolibre.android.andesui.icons

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import java.io.FileNotFoundException

/**
 * This interface defines the way to load an icon with a string name.
 */
interface IconProviderStrategy {
    fun loadIcon(name: String): Drawable?
    fun loadIcon(@DrawableRes idRes: Int): Drawable? = null
}

/**
 * This class has the responsibility of providing the icon which is inside of the library.
 */
class OfflineIconProviderStrategy(private val context: Context) : IconProviderStrategy {

    override fun loadIcon(name: String): Drawable? {
        val resId = context.resources.getIdentifier(name, "drawable", context.packageName)
        return try {
            ContextCompat.getDrawable(context, resId)
        } catch (e: FileNotFoundException) {
            Log.e("Andes UI", "File $name was not found.", e)
            null
        }
    }

    override fun loadIcon(@DrawableRes idRes: Int): Drawable? {
        return try {
            ContextCompat.getDrawable(context, idRes)
        } catch (e: FileNotFoundException) {
            Log.e("Andes UI", "File $idRes was not found.", e)
            null
        }
    }
}
