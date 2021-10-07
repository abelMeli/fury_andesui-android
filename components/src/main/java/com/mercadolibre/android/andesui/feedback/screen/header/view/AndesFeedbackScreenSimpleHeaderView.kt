package com.mercadolibre.android.andesui.feedback.screen.header.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgePillSize
import com.mercadolibre.android.andesui.utils.doWhenGreaterThanApi
import com.mercadolibre.android.andesui.utils.elevateWithShadow

@Suppress("TooManyFunctions")
internal class AndesFeedbackScreenSimpleHeaderView : AndesFeedbackScreenHeaderView {

    private var showCardGroup: Boolean = false
    private lateinit var cardViewBody: CardView

    constructor(context: Context) : super(context) {
        setupComponents()
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        setupComponents()
    }

    override fun setupComponents() {
        initComponents()
        super.setupComponents()
    }

    private fun initComponents() {
        val container = LayoutInflater.from(context)
            .inflate(R.layout.andes_layout_feedback_screen_header_simple, this)

        overline = container.findViewById(R.id.andes_feedbackscreen_header_overline)
        description = container.findViewById(R.id.andes_feedbackscreen_header_description)
        title = container.findViewById(R.id.andes_feedbackscreen_header_title)
        highlight = container.findViewById(R.id.andes_feedbackscreen_header_highlight)
        thumbnailBadge = container.findViewById(R.id.andes_feedbackscreen_header_image)
        cardViewBody = container.findViewById(R.id.andes_feedbackscreen_header_card)
    }

    override fun setupTextComponents(
        feedbackText: AndesFeedbackScreenText,
        type: AndesBadgeIconType,
        hasBody: Boolean
    ) {
        super.setupTextComponents(feedbackText, type, hasBody)
        showCardGroup = hasBody
        setupTextPaddings(
            description,
            highlight,
            title,
            overline,
            mustShowCardGroup = showCardGroup
        )
    }

    override fun setupThumbnailComponent(
        feedbackThumbnail: AndesFeedbackScreenAsset,
        type: AndesBadgeIconType
    ) {
        super.setupThumbnailComponent(feedbackThumbnail, type)
        thumbnailBadge.badgeComponent =
            AndesThumbnailBadgeComponent.IconPill(type, AndesThumbnailBadgePillSize.SIZE_64)
    }

    /**
     * this method sets the paddings of each visible textView in the header.
     * Since the bottom padding of the last visible textView is different from the others,
     * we must pass the parameter views in a certain order, last to first (the last view in first place).
     *
     * Also, horizontal padding is only needed when the textViews are inside the card. Otherwise,
     * it should be zero.
     */
    private fun setupTextPaddings(vararg views: View, mustShowCardGroup: Boolean) {
        var isLastViewPaddingSet = false
        val cardHorizontalPadding =
            resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_card_padding)
        val bottomPadding =
            resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_header_body_padding_vertical)

        views.forEach { view ->
            var viewHorizontalPadding = cardHorizontalPadding
            var viewBottomPadding = bottomPadding

            if (view.visibility == VISIBLE) {
                if (!isLastViewPaddingSet) { // only enters in the first visible textView of the loop
                    isLastViewPaddingSet = true
                    viewBottomPadding =
                        viewHorizontalPadding.takeIf { mustShowCardGroup } ?: NO_PADDING
                }

                if (!mustShowCardGroup) {
                    viewHorizontalPadding = NO_PADDING
                }

                view.setPadding(
                    viewHorizontalPadding,
                    NO_PADDING,
                    viewHorizontalPadding,
                    viewBottomPadding
                )
            }
        }
    }

    private fun setupCardView() {
        val viewHeight = height.takeIf { it > NO_HEIGHT } ?: measuredHeight
        cardViewBody.layoutParams.apply {
            height = viewHeight - thumbnailBadge.height / 2
        }
        cardViewBody.clipToPadding = false
        cardViewBody.visibility = VISIBLE

        doWhenGreaterThanApi(Build.VERSION_CODES.LOLLIPOP) {
            cardViewBody.elevateWithShadow(true)
            thumbnailBadge.elevateWithShadow()
            title.elevateWithShadow()
            description.elevateWithShadow()
            highlight.elevateWithShadow()
            overline.elevateWithShadow()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        showCardViewIfNecessary()
    }

    private fun showCardViewIfNecessary() {
        if (showCardGroup) {
            showCardGroup = false
            setupCardView()
        }
    }

    companion object {
        private const val NO_PADDING = 0
        private const val NO_HEIGHT = 0
    }
}
