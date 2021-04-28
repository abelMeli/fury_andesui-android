package com.mercadolibre.android.andesui.badge.factory

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.border.AndesBadgePillBorder
import com.mercadolibre.android.andesui.badge.factory.Constants.ANDES_BADGE_TYPE_ERROR
import com.mercadolibre.android.andesui.badge.factory.Constants.ANDES_BADGE_TYPE_HIGHLIGHT
import com.mercadolibre.android.andesui.badge.factory.Constants.ANDES_BADGE_TYPE_NEUTRAL
import com.mercadolibre.android.andesui.badge.factory.Constants.ANDES_BADGE_TYPE_SUCCESS
import com.mercadolibre.android.andesui.badge.factory.Constants.ANDES_BADGE_TYPE_WARNING
import com.mercadolibre.android.andesui.badge.hierarchy.AndesBadgePillHierarchy
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType

/**
 * The data class that contains the public components of the badge.
 */
internal data class AndesBadgePillAttrs(
    val andesBadgePillHierarchy: AndesBadgePillHierarchy,
    val andesBadgeType: AndesBadgeType,
    val andesBadgePillBorder: AndesBadgePillBorder,
    val andesBadgePillSize: AndesBadgePillSize,
    val andesBadgeText: String?,
    val andesBadgeTextStyleDefault: Boolean = true
)

/**
 * This object parse the attribute set and return an instance of AndesBadgeAttrs to be used by AndesBadge
 */
internal object AndesBadgePillAttrsParser {

    private const val ANDES_BADGE_PILL_HIERARCHY_LOUD = "1000"
    private const val ANDES_BADGE_PILL_HIERARCHY_QUIET = "1001"

    private const val ANDES_BADGE_PILL_SIZE_LARGE = "3000"
    private const val ANDES_BADGE_PILL_SIZE_SMALL = "3001"

    private const val ANDES_BADGE_PILL_BORDER_CORNER = "4000"
    private const val ANDES_BADGE_PILL_BORDER_ROUNDED = "4001"
    private const val ANDES_BADGE_PILL_BORDER_STANDARD = "4002"

    fun parse(context: Context, attr: AttributeSet?): AndesBadgePillAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesBadgePill)

        val hierarchy = parseHierarchy(typedArray)
        val type = parseType(typedArray)
        val border = parseBorder(typedArray)
        val size = parseSize(typedArray)

        return AndesBadgePillAttrs(
            andesBadgePillHierarchy = hierarchy,
            andesBadgeType = type,
            andesBadgePillBorder = border,
            andesBadgePillSize = size,
            andesBadgeText = typedArray.getString(R.styleable.AndesBadgePill_andesBadgePillText),
            andesBadgeTextStyleDefault = typedArray.getBoolean(R.styleable.AndesBadgePill_andesBadgeTextStyleDefault, true)
        ).also { typedArray.recycle() }
    }

    private fun parseHierarchy(typedArray: TypedArray): AndesBadgePillHierarchy {
        return when (typedArray.getString(R.styleable.AndesBadgePill_andesBadgePillHierarchy)) {
            ANDES_BADGE_PILL_HIERARCHY_LOUD -> AndesBadgePillHierarchy.LOUD
            ANDES_BADGE_PILL_HIERARCHY_QUIET -> AndesBadgePillHierarchy.QUIET
            else -> AndesBadgePillHierarchy.LOUD
        }
    }

    private fun parseType(typedArray: TypedArray): AndesBadgeType {
        return when (typedArray.getString(R.styleable.AndesBadgePill_andesBadgePillType)) {
            ANDES_BADGE_TYPE_NEUTRAL -> AndesBadgeType.NEUTRAL
            ANDES_BADGE_TYPE_HIGHLIGHT -> AndesBadgeType.HIGHLIGHT
            ANDES_BADGE_TYPE_SUCCESS -> AndesBadgeType.SUCCESS
            ANDES_BADGE_TYPE_WARNING -> AndesBadgeType.WARNING
            ANDES_BADGE_TYPE_ERROR -> AndesBadgeType.ERROR
            else -> AndesBadgeType.NEUTRAL
        }
    }

    private fun parseBorder(typedArray: TypedArray): AndesBadgePillBorder {
        return when (typedArray.getString(R.styleable.AndesBadgePill_andesBadgePillBorder)) {
            ANDES_BADGE_PILL_BORDER_CORNER -> AndesBadgePillBorder.CORNER
            ANDES_BADGE_PILL_BORDER_ROUNDED -> AndesBadgePillBorder.ROUNDED
            ANDES_BADGE_PILL_BORDER_STANDARD -> AndesBadgePillBorder.STANDARD
            else -> AndesBadgePillBorder.STANDARD
        }
    }

    private fun parseSize(typedArray: TypedArray): AndesBadgePillSize {
        return when (typedArray.getString(R.styleable.AndesBadgePill_andesBadgePillSize)) {
            ANDES_BADGE_PILL_SIZE_LARGE -> AndesBadgePillSize.LARGE
            ANDES_BADGE_PILL_SIZE_SMALL -> AndesBadgePillSize.SMALL
            else -> AndesBadgePillSize.LARGE
        }
    }
}
