package com.mercadolibre.android.andesui.bottomsheet.state

import android.content.res.Resources
import android.print.PrintAttributes
import com.mercadolibre.android.andesui.R
import kotlin.math.roundToInt

interface AndesBottomSheetContentMarginInterface {
    fun getMargins(resource: Resources): PrintAttributes.Margins
}

internal object AndesBottomSheetDefaultContentMargin : AndesBottomSheetContentMarginInterface {
    private const val ZERO = 0

    override fun getMargins(resource: Resources): PrintAttributes.Margins {
        return PrintAttributes.Margins(
            resource.getDimension(R.dimen.andes_bottom_sheet_default_margin).roundToInt(),
            resource.getDimension(R.dimen.andes_bottom_sheet_title_bottom_margin).roundToInt(),
            resource.getDimension(R.dimen.andes_bottom_sheet_default_margin).roundToInt(),
            ZERO
        )
    }
}

internal object AndesBottomSheetHorizontalContentMargin : AndesBottomSheetContentMarginInterface {
    private const val ZERO = 0

    override fun getMargins(resource: Resources): PrintAttributes.Margins {
        return PrintAttributes.Margins(
            ZERO,
            resource.getDimension(R.dimen.andes_bottom_sheet_title_bottom_margin).roundToInt(),
            ZERO,
            ZERO
        )
    }
}
