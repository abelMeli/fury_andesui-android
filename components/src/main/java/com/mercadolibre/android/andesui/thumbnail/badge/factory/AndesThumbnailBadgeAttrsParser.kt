package com.mercadolibre.android.andesui.thumbnail.badge.factory

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgeDotSize
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgePillSize
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import java.lang.IllegalArgumentException

/**
 * The data class that contains the public components of the thumbnail.
 */
internal data class AndesThumbnailBadgeAttrs(
    val image: Drawable?,
    val badge: AndesThumbnailBadgeComponent,
    val thumbnailType: AndesThumbnailBadgeType,
    val text: String = ""
)

/**
 * This object parse the attribute set and return an instance of AndesThumbnailBadgeAttrs to be used by AndesThumbnailBadge
 */
internal object AndesThumbnailBadgeAttrsParser {
    private const val ANDES_THUMBNAILBADGE_TYPE_ICON = 2000
    private const val ANDES_THUMBNAILBADGE_TYPE_IMAGE_CIRCLE = 2001
    private const val ANDES_THUMBNAILBADGE_TYPE_TEXT = 2002
    private const val NO_THUMBNAILBADGE_TYPE =-1

    private const val ANDES_THUMBNAILBADGE_PILL = 3000
    private const val ANDES_THUMBNAILBADGE_ICON_PILL = 3001
    private const val ANDES_THUMBNAILBADGE_DOT = 3002
    private const val NO_BADGE_COMPONENT =-1

    private const val ANDES_THUMBNAILBADGE_DOT_SIZE_24 = 6000
    private const val ANDES_THUMBNAILBADGE_DOT_SIZE_32 = 6001
    private const val NO_DOT_SIZE = -1

    private const val ANDES_THUMBNAILBADGE_PILL_SIZE_40 = 6002
    private const val ANDES_THUMBNAILBADGE_PILL_SIZE_48 = 6003
    private const val ANDES_THUMBNAILBADGE_PILL_SIZE_56 = 6004
    private const val ANDES_THUMBNAILBADGE_PILL_SIZE_64 = 6005
    private const val ANDES_THUMBNAILBADGE_PILL_SIZE_72 = 6006
    private const val ANDES_THUMBNAILBADGE_PILL_SIZE_80 = 6007
    private const val NO_PILL_SIZE =-1

    private const val ANDES_THUMBNAILBADGE_COMPONENT_HIGHLIGHT = 5000
    private const val ANDES_THUMBNAILBADGE_COMPONENT_SUCCESS = 5001
    private const val ANDES_THUMBNAILBADGE_COMPONENT_WARNING = 5002
    private const val ANDES_THUMBNAILBADGE_COMPONENT_ERROR = 5003
    private const val NO_BADGE_TYPE =-1


    fun parse(context: Context, attr: AttributeSet?): AndesThumbnailBadgeAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesThumbnailBadge)
        return AndesThumbnailBadgeAttrs(
            image = getInputImage(typedArray),
            badge = getBadgeComponent(typedArray),
            thumbnailType = getType(typedArray),
            text = getInputText(typedArray)
        ).also { typedArray.recycle() }
    }

    private fun getInputImage(typedArray: TypedArray): Drawable? =
         typedArray.getDrawable(R.styleable.AndesThumbnailBadge_andesThumbnailBadgeImage)

    private fun getInputText(typedArray: TypedArray): String =
        typedArray.getString(R.styleable.AndesThumbnailBadge_andesThumbnailBadgeText) ?: ""

    private fun getBadgeComponent(typedArray: TypedArray): AndesThumbnailBadgeComponent =
        when (typedArray.getInt(
            R.styleable.AndesThumbnailBadge_andesThumbnailBadgeComponent, NO_BADGE_COMPONENT
        )) {
            ANDES_THUMBNAILBADGE_PILL -> getBadgePill(typedArray)
            ANDES_THUMBNAILBADGE_ICON_PILL -> getAndesBadgeIconPill(typedArray)
            ANDES_THUMBNAILBADGE_DOT -> getAndesBadgeDot(typedArray)
            else -> getAndesBadgeIconPill(typedArray)
        }

    private fun getAndesBadgeDot(typedArray: TypedArray): AndesThumbnailBadgeComponent =
        AndesThumbnailBadgeComponent.Dot(
            getAndesBadgeType(typedArray),
            getAndesBadgeDotSize(typedArray)
        )

    private fun getBadgePill(typedArray: TypedArray): AndesThumbnailBadgeComponent =
        AndesThumbnailBadgeComponent.Pill(
            getAndesBadgeType(typedArray),
            typedArray.getString(R.styleable.AndesThumbnailBadge_andesThumbnailBadgePillText),
            typedArray.getBoolean(
                R.styleable.AndesThumbnailBadge_andesThumbnailBadgePillDefaultTextStyle,
                true
            ),
            getAndesBadgePillSize(typedArray)
        )

    private fun getAndesBadgeIconPill(typedArray: TypedArray): AndesThumbnailBadgeComponent =
        AndesThumbnailBadgeComponent.IconPill(
            getAndesBadgeType(typedArray),
            getAndesBadgePillSize(typedArray)
        )

    private fun getAndesBadgeDotSize(typedArray: TypedArray): AndesThumbnailBadgeDotSize =
        when (typedArray.getInt(
            R.styleable.AndesThumbnailBadge_andesThumbnailBadgeComponentDotSize,
            NO_DOT_SIZE
        )) {
            ANDES_THUMBNAILBADGE_DOT_SIZE_24 -> AndesThumbnailBadgeDotSize.SIZE_24
            ANDES_THUMBNAILBADGE_DOT_SIZE_32 -> AndesThumbnailBadgeDotSize.SIZE_32
            else -> AndesThumbnailBadgeDotSize.SIZE_24
        }

    private fun getAndesBadgePillSize(typedArray: TypedArray): AndesThumbnailBadgePillSize =
        when (typedArray.getInt(
            R.styleable.AndesThumbnailBadge_andesThumbnailBadgeComponentPillSize,
            NO_PILL_SIZE
        )) {
            ANDES_THUMBNAILBADGE_PILL_SIZE_40 -> AndesThumbnailBadgePillSize.SIZE_40
            ANDES_THUMBNAILBADGE_PILL_SIZE_48 -> AndesThumbnailBadgePillSize.SIZE_48
            ANDES_THUMBNAILBADGE_PILL_SIZE_56 -> AndesThumbnailBadgePillSize.SIZE_56
            ANDES_THUMBNAILBADGE_PILL_SIZE_64 -> AndesThumbnailBadgePillSize.SIZE_64
            ANDES_THUMBNAILBADGE_PILL_SIZE_72 -> AndesThumbnailBadgePillSize.SIZE_72
            ANDES_THUMBNAILBADGE_PILL_SIZE_80 -> AndesThumbnailBadgePillSize.SIZE_80
            else -> AndesThumbnailBadgePillSize.SIZE_64
        }

    private fun getAndesBadgeType(typedArray: TypedArray): AndesBadgeIconType =
        when (typedArray.getInt(
            R.styleable.AndesThumbnailBadge_andesThumbnailBadgeComponentType,
            NO_BADGE_TYPE
        )) {
            ANDES_THUMBNAILBADGE_COMPONENT_HIGHLIGHT -> AndesBadgeIconType.HIGHLIGHT
            ANDES_THUMBNAILBADGE_COMPONENT_SUCCESS -> AndesBadgeIconType.SUCCESS
            ANDES_THUMBNAILBADGE_COMPONENT_WARNING -> AndesBadgeIconType.WARNING
            ANDES_THUMBNAILBADGE_COMPONENT_ERROR -> AndesBadgeIconType.ERROR
            else -> AndesBadgeIconType.HIGHLIGHT
        }

    private fun getType(
        typedArray: TypedArray
    ): AndesThumbnailBadgeType =
        when (typedArray.getInt(
            R.styleable.AndesThumbnailBadge_andesThumbnailBadgeType,
            NO_THUMBNAILBADGE_TYPE
        )) {
            ANDES_THUMBNAILBADGE_TYPE_ICON -> AndesThumbnailBadgeType.Icon
            ANDES_THUMBNAILBADGE_TYPE_IMAGE_CIRCLE -> AndesThumbnailBadgeType.ImageCircle
            ANDES_THUMBNAILBADGE_TYPE_TEXT -> AndesThumbnailBadgeType.Text
            else -> AndesThumbnailBadgeType.Icon
        }
}
