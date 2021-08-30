package com.mercadolibre.android.andesui.thumbnail.badge.component

import android.content.Context
import android.view.View
import android.view.View.MeasureSpec.UNSPECIFIED
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.AndesBadgeDot
import com.mercadolibre.android.andesui.badge.AndesBadgeIconPill
import com.mercadolibre.android.andesui.badge.AndesBadgePill
import com.mercadolibre.android.andesui.badge.border.AndesBadgePillBorder
import com.mercadolibre.android.andesui.badge.hierarchy.AndesBadgePillHierarchy
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import com.mercadolibre.android.andesui.thumbnail.AndesThumbnail
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import kotlin.math.roundToInt

internal interface AndesThumbnailBadgeComponentInterface {
    val badgeType: AndesBadgeType
    val thumbnailSize: AndesThumbnailSize
    fun getView(context: Context): View
    fun getBadgeOutlineSize(context: Context): Int
    fun getComponentPosition(
        context: Context,
        constraintSet: ConstraintSet,
        badge: View,
        thumbnail: AndesThumbnail
    ) {
        constraintSet.connect(badge.id, ConstraintSet.BOTTOM, thumbnail.id, ConstraintSet.BOTTOM)
        constraintSet.connect(badge.id, ConstraintSet.END, thumbnail.id, ConstraintSet.END)
    }
}

internal interface AndesThumbnailBadgeSize : AndesThumbnailBadgeComponentInterface {
    fun getBadgeSize() =
        if (isBiggestSizes()) AndesBadgePillSize.LARGE else AndesBadgePillSize.SMALL

    override fun getBadgeOutlineSize(context: Context) = if (isBiggestSizes()) {
        context.resources.getDimensionPixelSize(R.dimen.andes_thumbnail_badge_outline_3)
    } else {
        context.resources.getDimensionPixelSize(R.dimen.andes_thumbnail_badge_outline_2)
    }

    private fun isBiggestSizes() = thumbnailSize == AndesThumbnailSize.SIZE_80 ||
            thumbnailSize == AndesThumbnailSize.SIZE_72 ||
            thumbnailSize == AndesThumbnailSize.SIZE_64
}

internal class AndesPillThumbnailBadgeComponent(
    private val text: String?,
    private val textStyleDefault: Boolean,
    color: AndesBadgeIconType,
    override val thumbnailSize: AndesThumbnailSize
) : AndesThumbnailBadgeSize {

    companion object {
        private const val CIRCLE_DIAMETER_PERCENTAGE = 0.825F
        private const val HALF_WIDTH = 2F
    }

    override val badgeType by lazy { color.iconType }

    override fun getView(context: Context) = AndesBadgePill(
        context = context,
        type = badgeType,
        pillHierarchy = AndesBadgePillHierarchy.LOUD,
        pillSize = getBadgeSize(),
        pillBorder = AndesBadgePillBorder.STANDARD,
        text = text,
        textStyleDefault = textStyleDefault
    )

    override fun getComponentPosition(
        context: Context,
        constraintSet: ConstraintSet,
        badge: View,
        thumbnail: AndesThumbnail
    ) {
        val badgeMargin = thumbnailDiameterPosition(thumbnail) - badgeHalfWidth(badge)
        constraintSet.connect(
            badge.id,
            ConstraintSet.BOTTOM,
            thumbnail.id,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            badge.id,
            ConstraintSet.START,
            thumbnail.id,
            ConstraintSet.START,
            badgeMargin.roundToInt()
        )
    }

    private fun badgeHalfWidth(badge: View): Float {
        badge.measure(UNSPECIFIED, UNSPECIFIED)
        return badge.measuredWidth / HALF_WIDTH
    }

    private fun thumbnailDiameterPosition(thumbnail: AndesThumbnail): Float {
        thumbnail.measure(UNSPECIFIED, UNSPECIFIED)
        return thumbnail.measuredWidth * CIRCLE_DIAMETER_PERCENTAGE
    }
}

internal class AndesDotThumbnailBadgeComponent(
    color: AndesBadgeIconType,
    override val thumbnailSize: AndesThumbnailSize
) : AndesThumbnailBadgeComponentInterface {
    override val badgeType by lazy { color.iconType }

    override fun getView(context: Context) =
        AndesBadgeDot(context = context, type = badgeType)

    override fun getBadgeOutlineSize(context: Context) =
        context.resources.getDimensionPixelSize(R.dimen.andes_thumbnail_badge_outline_2)

    override fun getComponentPosition(
        context: Context,
        constraintSet: ConstraintSet,
        badge: View,
        thumbnail: AndesThumbnail
    ) {
        super.getComponentPosition(context, constraintSet, badge, thumbnail)
        if (thumbnailSize == AndesThumbnailSize.SIZE_32) {
            val margin =
                context.resources.getDimensionPixelSize(R.dimen.andes_thumbnail_badge_dot_margin)
            constraintSet.setMargin(badge.id, ConstraintSet.END, margin)
            constraintSet.setMargin(badge.id, ConstraintSet.BOTTOM, margin)
        }
    }
}

internal class AndesIconPillThumbnailBadgeComponent(
    val color: AndesBadgeIconType,
    override val thumbnailSize: AndesThumbnailSize
) : AndesThumbnailBadgeSize {
    override val badgeType by lazy { color.iconType }

    override fun getView(context: Context) =
        AndesBadgeIconPill(context = context, type = color, size = getBadgeSize())
}
