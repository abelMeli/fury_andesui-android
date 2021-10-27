package com.mercadolibre.android.andesui.badge.factory

import android.content.Context
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.hierarchy.AndesBadgeIconHierarchy
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize

internal data class AndesBadgeIconPillAttrs(
    val andesBadgeType: AndesBadgeIconType,
    val andesBadgeSize: AndesBadgePillSize,
    val andesBadgeHierarchy: AndesBadgeIconHierarchy
)

internal object AndesBadgeIconPillAttrsParser {

    private const val ANDES_BADGE_ICON_TYPE_HIGHLIGHT = 2001
    private const val ANDES_BADGE_ICON_TYPE_SUCCESS = 2002
    private const val ANDES_BADGE_ICON_TYPE_WARNING = 2003
    private const val ANDES_BADGE_ICON_TYPE_ERROR = 2004

    private const val ANDES_BADGE_SIZE_LARGE = 3000
    private const val ANDES_BADGE_SIZE_SMALL = 3001

    private const val ANDES_BADGE_ICON_HIERARCHY_LOUD = 1001
    private const val ANDES_BADGE_ICON_HIERARCHY_SECONDARY = 1002

    fun parse(context: Context, attrs: AttributeSet?): AndesBadgeIconPillAttrs {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AndesBadgeIconPill)

        val type = when (typedArray.getInt(R.styleable.AndesBadgeIconPill_andesBadgeIconPillType, ANDES_BADGE_ICON_TYPE_HIGHLIGHT)) {
            ANDES_BADGE_ICON_TYPE_HIGHLIGHT -> AndesBadgeIconType.HIGHLIGHT
            ANDES_BADGE_ICON_TYPE_SUCCESS -> AndesBadgeIconType.SUCCESS
            ANDES_BADGE_ICON_TYPE_WARNING -> AndesBadgeIconType.WARNING
            ANDES_BADGE_ICON_TYPE_ERROR -> AndesBadgeIconType.ERROR
            else -> AndesBadgeIconType.HIGHLIGHT
        }

        val size = when (typedArray.getInt(R.styleable.AndesBadgeIconPill_andesBadgeIconPillSize, ANDES_BADGE_SIZE_SMALL)) {
            ANDES_BADGE_SIZE_LARGE -> AndesBadgePillSize.LARGE
            ANDES_BADGE_SIZE_SMALL -> AndesBadgePillSize.SMALL
            else -> AndesBadgePillSize.SMALL
        }

        val hierarchy = when (typedArray.getInt(R.styleable.AndesBadgeIconPill_andesBadgeIconPillHierarchy, ANDES_BADGE_ICON_HIERARCHY_LOUD)) {
            ANDES_BADGE_ICON_HIERARCHY_LOUD -> AndesBadgeIconHierarchy.LOUD
            ANDES_BADGE_ICON_HIERARCHY_SECONDARY -> AndesBadgeIconHierarchy.SECONDARY
            else -> AndesBadgeIconHierarchy.LOUD
        }

        return AndesBadgeIconPillAttrs(type, size, hierarchy).also {
            typedArray.recycle()
        }
    }
}
