package com.mercadolibre.android.andesui.feedback.screen.header.view

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenTextDescription
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackBadgeIconType
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenTypeInterface
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

/**
 * [AndesFeedbackScreenCongratsHeaderView] & [AndesFeedbackScreenSimpleHeaderView] are similar components
 * with some differences in styling. For this reason, all common methods and view are hold in this class.
 *
 * IMPORTANT NOTE: in case one of this common functions turns different for one of the subclasses, you should
 * remove the function from this class.
 */
internal open class AndesFeedbackScreenHeaderView : ConstraintLayout {

    protected lateinit var assetContainer: FrameLayout
    protected lateinit var overline: TextView
    protected lateinit var errorImage: ImageView
    protected lateinit var errorContainer: ConstraintLayout
    protected lateinit var description: TextView
    protected lateinit var title: TextView
    protected lateinit var highlight: TextView
    protected lateinit var errorCode: AndesTextView

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)

    private fun setupViewId() {
        if (id == View.NO_ID) {
            id = generateViewId()
        }
    }

    protected open fun setupComponents() {
        setupViewId()
        setupLayout()
    }

    private fun setupLayout() {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        clipChildren = false
    }

    open fun setupTextComponents(
        feedbackText: AndesFeedbackScreenText,
        type: AndesFeedbackBadgeIconType,
        hasBody: Boolean
    ) {
        setupTextView(overline, feedbackText.overline)
        setupDescriptionTextView(description, feedbackText.description, context)
        setupTextView(title, feedbackText.title)
        setupTextView(highlight, feedbackText.highlight)
        highlight.setTextColor(type.type.type.primaryColor().colorInt(context))

        setupA11y()
    }

    fun setupAssetComponent(
        feedbackAsset: AndesFeedbackScreenAsset,
        type: AndesFeedbackScreenTypeInterface,
        hasBody: Boolean
    ) {
        feedbackAsset.asset.let { asset ->
            assetContainer.removeAllViews()
            assetContainer.addView(asset.getView(context, type, hasBody))
        }
    }

    private fun setupTextView(textview: TextView, mText: String?) {
        with(textview) {
            if (mText.isNullOrEmpty()) {
                visibility = GONE
            } else {
                text = mText
                visibility = VISIBLE
            }
        }
    }

    private fun setupDescriptionTextView(
        textView: TextView,
        descriptionData: AndesFeedbackScreenTextDescription?,
        context: Context
    ) {
        if (descriptionData == null || descriptionData.text.isEmpty()) {
            textView.visibility = GONE
        } else {
            textView.visibility = VISIBLE
            val spannableString = SpannableString(descriptionData.text)
            descriptionData.links?.let {
                it.links.forEachIndexed { linkIndex, andesBodyLink ->
                    if (andesBodyLink.isValidRange(spannableString)) {
                        val clickableSpan = object : ClickableSpan() {
                            override fun onClick(view: View) {
                                it.listener(linkIndex)
                            }

                            override fun updateDrawState(ds: TextPaint) {
                                ds.color =
                                    ContextCompat.getColor(context, R.color.andes_accent_color_500)
                            }
                        }
                        spannableString.setSpan(
                            clickableSpan,
                            andesBodyLink.startIndex, andesBodyLink.endIndex,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
                textView.movementMethod = LinkMovementMethod.getInstance()
            }
            textView.text = spannableString
        }
    }

    private fun setupA11y() {
        setupViewFirstFocusableItem(overline, title)
        ViewCompat.setAccessibilityHeading(title, true)
    }

    private fun setupViewFirstFocusableItem(expectedView: View, defaultView: View) {
        val focusableView =
            expectedView.takeIf { expectedView.visibility == VISIBLE } ?: defaultView
        focusableView.post { focusableView.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED) }
    }
}
