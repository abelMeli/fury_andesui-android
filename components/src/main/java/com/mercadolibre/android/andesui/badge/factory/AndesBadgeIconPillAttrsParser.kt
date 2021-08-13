package com.mercadolibre.android.andesui.badge.factory

import android.content.Context
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType

internal data class AndesBadgeIconPillAttrs(
    val andesBadgeType: AndesBadgeType,
    val andesBadgeSize: AndesBadgePillSize
)

internal object AndesBadgeIconPillAttrsParser {

    private const val ANDES_BADGE_TYPE_HIGHLIGHT = 2001
    private const val ANDES_BADGE_TYPE_SUCCESS = 2002
    private const val ANDES_BADGE_TYPE_WARNING = 2003
    private const val ANDES_BADGE_TYPE_ERROR = 2004

    private const val ANDES_BADGE_SIZE_LARGE = 3000
    private const val ANDES_BADGE_SIZE_SMALL = 3001

    fun parse(context: Context, attrs: AttributeSet?): AndesBadgeIconPillAttrs {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AndesBadgeIconPill)

        val type = when (typedArray.getInt(R.styleable.AndesBadgeIconPill_andesBadgeIconPillType, ANDES_BADGE_TYPE_HIGHLIGHT)) {
            ANDES_BADGE_TYPE_HIGHLIGHT -> AndesBadgeType.HIGHLIGHT
            ANDES_BADGE_TYPE_SUCCESS -> AndesBadgeType.SUCCESS
            ANDES_BADGE_TYPE_WARNING -> AndesBadgeType.WARNING
            ANDES_BADGE_TYPE_ERROR -> AndesBadgeType.ERROR
            else -> AndesBadgeType.HIGHLIGHT
        }

        val size = when (typedArray.getInt(R.styleable.AndesBadgeIconPill_andesBadgeIconPillSize, ANDES_BADGE_SIZE_SMALL)) {
            ANDES_BADGE_SIZE_LARGE -> AndesBadgePillSize.LARGE
            ANDES_BADGE_SIZE_SMALL -> AndesBadgePillSize.SMALL
            else -> AndesBadgePillSize.SMALL
        }

        return AndesBadgeIconPillAttrs(type, size).also {
            typedArray.recycle()
        }
    }
}
