package com.mercadolibre.android.andesui.feedback.screen.header.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackBadgeIconType
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBold
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgePillSize
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

        errorContainer = container.findViewById(R.id.andes_feedbackscreen_container_error_code)
        errorImage = container.findViewById(R.id.andes_error_code_image)
        overline = container.findViewById(R.id.andes_feedbackscreen_header_overline)
        description = container.findViewById(R.id.andes_feedbackscreen_header_description)
        title = container.findViewById(R.id.andes_feedbackscreen_header_title)
        errorCode = container.findViewById(R.id.andes_feedbackscreen_header_error_code)
        highlight = container.findViewById(R.id.andes_feedbackscreen_header_highlight)
        assetContainer = container.findViewById(R.id.andes_feedbackscreen_header_image)
        cardViewBody = container.findViewById(R.id.andes_feedbackscreen_header_card)
    }

    override fun setupTextComponents(
        feedbackText: AndesFeedbackScreenText,
        type: AndesFeedbackBadgeIconType,
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

    internal fun setErrorCodeConfiguration(errorInfo: String?) {
        if (errorInfo.isNullOrEmpty()) {
            errorCode.visibility = GONE
            errorImage.visibility = GONE
            errorContainer.visibility = GONE

        } else {
            errorImage.setColorFilter(AndesColor(R.color.andes_accent_color_500).colorInt(context))
            errorImage.visibility = VISIBLE
            errorCode.visibility = VISIBLE
            errorContainer.visibility = VISIBLE

            setupSpanTextView(
                errorCode,
                resources.getString(R.string.andes_feedbackscreen_error_code),
                AndesColor(R.color.andes_gray_550)
            )
            setupSpanTextView(
                errorCode,
                errorInfo,
                AndesColor(R.color.andes_accent_color_500)
            )

            errorContainer.setOnClickListener(getOnErrorClickListener(errorInfo))
            errorCode.setOnClickListener(getOnErrorClickListener(errorInfo))
            errorImage.setOnClickListener(getOnErrorClickListener(errorInfo))
            errorCode.bodyBolds = AndesBodyBolds(
                listOf(
                    AndesBodyBold(
                        errorCode.text.toString().indexOf(errorInfo),
                        errorCode.text.length
                    )
                )
            )
        }
    }

    private fun setupSpanTextView(textview: TextView, mText: String?, color: AndesColor) {
        textview.visibility = if (mText.isNullOrEmpty()) {
            GONE
        } else {
            val word: Spannable = SpannableString(mText)
            word.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, color.colorRes)),
                0,
                word.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textview.append(word)
            VISIBLE
        }
    }

    private fun getOnErrorClickListener(errorInfo: String): OnClickListener {
        return OnClickListener {
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText(
                context.getString(R.string.andes_feedbackscreen_error_code_label),
                errorInfo
            )
            clipboardManager.setPrimaryClip(clip)

            Toast.makeText(
                context.applicationContext,
                context.getString(R.string.andes_feedbackscreen_error_code_clipboard) + errorInfo,
                Toast.LENGTH_SHORT
            ).show()
        }
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
            height = viewHeight - assetContainer.height / 2
        }
        cardViewBody.clipToPadding = false
        cardViewBody.visibility = VISIBLE

        cardViewBody.elevateWithShadow(true)
        assetContainer.elevateWithShadow()
        title.elevateWithShadow()
        description.elevateWithShadow()
        errorCode.elevateWithShadow()
        highlight.elevateWithShadow()
        overline.elevateWithShadow()
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
