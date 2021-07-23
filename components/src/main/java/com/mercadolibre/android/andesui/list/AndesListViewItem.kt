package com.mercadolibre.android.andesui.list

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.list.factory.AndesListViewItemConfiguration
import com.mercadolibre.android.andesui.list.factory.AndesListViewItemConfigurationFactory
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize

@Suppress("LongParameterList")
open class AndesListViewItem {
    internal var title: String = ""
    internal var subtitle: String? = ""
    internal var paddingLeft: Int = 0
    internal var paddingRight: Int = 0
    internal var paddingTop: Int = 0
    internal var paddingBottom: Int = 0
    internal var height: Float = 0.0f
    internal var titleColor: Int = 0
    internal var titleFontSize: Float = 0.0f
    internal var titleTypeFace: Typeface = Typeface.DEFAULT
    internal var subtitleColor: Int = 0
    internal var subtitleFontSize: Float = 0.0f
    internal var subtitleTypeFace: Typeface = Typeface.DEFAULT
    internal var titleMaxLines: Int = DEFAULT_TITLE_NUMBER_OF_LINES
    internal var spaceTitleSubtitle = 0
    internal var itemSelected: Boolean? = false
    internal var thumbnailSize: AndesThumbnailSize = AndesThumbnailSize.SIZE_32
    internal var separatorThumbnailWidth: Int = 0
    internal var iconSize: Int = 0
    internal var icon: Drawable? = null
    internal var iconContentDescription: String? = null
    internal var avatar: Drawable? = null
    internal var avatarContentDescription: String? = null
    internal var showSubtitle: Boolean = true

    companion object {
        const val DEFAULT_TITLE_NUMBER_OF_LINES = 50
    }

    internal fun andesListViewItemConfig(
        title: String,
        subtitle: String?,
        config: AndesListViewItemConfiguration,
        itemSelected: Boolean?,
        icon: Drawable?,
        iconContentDescription: String?,
        avatar: Drawable?,
        avatarContentDescription: String?,
        titleMaxLines: Int
    ) {
        this.title = title
        this.subtitle = subtitle
        this.paddingBottom = config.paddingBottom
        this.paddingLeft = config.paddingLeft
        this.paddingRight = config.paddingRight
        this.paddingTop = config.paddingTop
        this.subtitleFontSize = config.subTitleFontSize
        this.titleFontSize = config.titleFontSize
        this.height = config.height
        this.titleTypeFace = config.titleTypeface
        this.subtitleTypeFace = config.subTitleTypeface
        this.titleColor = config.titleColor
        this.subtitleColor = config.subTitleColor
        this.titleMaxLines = config.titleMaxLines
        this.spaceTitleSubtitle = config.spaceTitleSubtitle
        this.itemSelected = itemSelected
        this.thumbnailSize = config.avatarSize
        this.separatorThumbnailWidth = config.separatorThumbnailWidth
        this.iconSize = config.iconSize
        this.icon = icon
        this.iconContentDescription = iconContentDescription
        this.avatar = avatar
        this.avatarContentDescription = avatarContentDescription
        this.titleMaxLines = titleMaxLines
        this.showSubtitle = config.showSubtitle
    }
}

@Suppress("LongParameterList")
class AndesListViewItemSimple @JvmOverloads constructor(
    context: Context,
    title: String,
    subtitle: String? = null,
    itemSelected: Boolean = false,
    size: AndesListViewItemSize = AndesListViewItemSize.MEDIUM,
    icon: Drawable? = null,
    iconContentDescription: String? = null,
    avatar: Drawable? = null,
    avatarContentDescription: String? = null,
    titleMaxLines: Int = DEFAULT_TITLE_NUMBER_OF_LINES
) : AndesListViewItem() {

    init {
        val config = AndesListViewItemConfigurationFactory.create(context, size)
        this.andesListViewItemSimpleConfig(
            title,
            subtitle,
            config,
            itemSelected,
            icon,
            iconContentDescription,
            avatar,
            avatarContentDescription,
            titleMaxLines
        )
    }

    private fun andesListViewItemSimpleConfig(
        title: String,
        subtitle: String?,
        config: AndesListViewItemConfiguration,
        itemSelected: Boolean?,
        icon: Drawable?,
        iconContentDescription: String?,
        avatar: Drawable?,
        avatarContentDescription: String?,
        titleMaxLines: Int
    ) {

        super.andesListViewItemConfig(
            title,
            subtitle,
            config,
            itemSelected,
            icon,
            iconContentDescription,
            avatar,
            avatarContentDescription,
            titleMaxLines
        )
    }
}

@Suppress("LongParameterList")
class AndesListViewItemChevron @JvmOverloads constructor(
    context: Context,
    title: String,
    subtitle: String? = null,
    itemSelected: Boolean = false,
    size: AndesListViewItemSize = AndesListViewItemSize.MEDIUM,
    icon: Drawable? = null,
    iconContentDescription: String? = null,
    avatar: Drawable? = null,
    avatarContentDescription: String? = null,
    titleMaxLines: Int = DEFAULT_TITLE_NUMBER_OF_LINES
) : AndesListViewItem() {
    internal var chevronSize: Int = 0

    init {
        val config = AndesListViewItemConfigurationFactory.create(context, size)
        this.andesListViewItemSimpleConfig(
            title,
            subtitle,
            config,
            itemSelected,
            icon,
            iconContentDescription,
            avatar,
            avatarContentDescription,
            titleMaxLines
        )
    }

    private fun andesListViewItemSimpleConfig(
        title: String,
        subtitle: String?,
        config: AndesListViewItemConfiguration,
        itemSelected: Boolean?,
        icon: Drawable?,
        iconContentDescription: String?,
        avatar: Drawable?,
        avatarContentDescription: String?,
        titleMaxLines: Int
    ) {

        super.andesListViewItemConfig(
            title,
            subtitle,
            config,
            itemSelected,
            icon,
            iconContentDescription,
            avatar,
            avatarContentDescription,
            titleMaxLines
        )
        this.chevronSize = config.chevronSize
    }
}
