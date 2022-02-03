package com.mercadolibre.android.andesui.snackbar.factory

import android.content.Context
import android.util.Log
import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.snackbar.action.AndesSnackbarAction
import com.mercadolibre.android.andesui.snackbar.duration.AndesSnackbarDuration
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarType
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarTypeInterface

/**
 * The data class that contains the public components of the tag.
 */
internal data class AndesSnackbarAttrs(
    var andesSnackbarType: AndesSnackbarType,
    var andesSnackbarText: String,
    var andesSnackbarDuration: AndesSnackbarDuration,
    var andesSnackbarAction: AndesSnackbarAction? = null,
    var andesSnackbarErrorCode: String? = null
)

internal data class AndesSnackbarConfiguration(
    val view: View,
    val backgroundColor: AndesColor,
    val textColor: AndesColor,
    val textActionColor: AndesColor? = null,
    val radius: Int,
    val marginLeft: Int,
    val marginRight: Int,
    val marginBottom: Int
)

internal object AndesSnackbarConfigurationFactory {

    fun create(context: Context, view: View, andesSnackbarAttrs: AndesSnackbarAttrs): AndesSnackbarConfiguration {
        resolveType(andesSnackbarAttrs)
        return AndesSnackbarConfiguration(
                view = view,
                backgroundColor = resolveBackgroundColor(andesSnackbarAttrs.andesSnackbarType.type),
                textColor = resolveTextColor(andesSnackbarAttrs.andesSnackbarType.type),
                textActionColor = resolveTextColor(andesSnackbarAttrs.andesSnackbarType.type),
                radius = context.resources.getDimension(R.dimen.andes_snackbar_radius).toInt(),
                marginLeft = context.resources.getDimension(R.dimen.andes_snackbar_left_margin).toInt(),
                marginRight = context.resources.getDimension(R.dimen.andes_snackbar_right_margin).toInt(),
                marginBottom = context.resources.getDimension(R.dimen.andes_snackbar_bottom_margin).toInt()
        )
    }

    private fun resolveType(andesSnackbarAttrs: AndesSnackbarAttrs){
        if (!andesSnackbarAttrs.andesSnackbarErrorCode.isNullOrEmpty() && andesSnackbarAttrs.andesSnackbarType != AndesSnackbarType.ERROR) {
            andesSnackbarAttrs.andesSnackbarType = AndesSnackbarType.ERROR
            Log.e("Andes UI", "Type not supported for errorCode")
        }
    }

    private fun resolveBackgroundColor(type: AndesSnackbarTypeInterface) = type.primaryColor()
    private fun resolveTextColor(type: AndesSnackbarTypeInterface) = type.secondaryColor()
}
