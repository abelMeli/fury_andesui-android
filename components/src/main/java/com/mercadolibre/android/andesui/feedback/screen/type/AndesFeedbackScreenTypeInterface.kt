package com.mercadolibre.android.andesui.feedback.screen.type

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.feedback.screen.header.view.AndesFeedbackScreenCongratsHeaderView
import com.mercadolibre.android.andesui.feedback.screen.header.view.AndesFeedbackScreenSimpleHeaderView
import com.mercadolibre.android.andesui.feedback.screen.color.AndesFeedbackScreenColor

internal interface AndesFeedbackScreenTypeInterface {
    val feedbackColor: AndesBadgeIconType

    fun getHeaderView(context: Context, hasBody: Boolean): View

    fun getGradientVisiblity(hasBody: Boolean): Int

    fun getCloseTintColor(context: Context, hasBody: Boolean): Int

    fun getHeaderTopMargin(context: Context, hasBody: Boolean): Int

    fun getDefaultAsset(context: Context, hasBody: Boolean): Drawable?

    fun getStatusBarColor(context: Context): Int? = null
}

internal class AndesSimpleFeedbackScreenType(override val feedbackColor: AndesBadgeIconType) :
    AndesFeedbackScreenTypeInterface {

    override fun getHeaderView(context: Context, hasBody: Boolean) = AndesFeedbackScreenSimpleHeaderView(context)

    override fun getGradientVisiblity(hasBody: Boolean) = View.GONE

    override fun getCloseTintColor(context: Context, hasBody: Boolean) =
        ContextCompat.getColor(context, R.color.andes_gray_550)

    override fun getHeaderTopMargin(context: Context, hasBody: Boolean) =
        context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_simple_header_margin_top)

    override fun getDefaultAsset(context: Context, hasBody: Boolean): Drawable? {
        return feedbackColor.iconType.type.feedbackIcon(context, true)
    }
}

internal object AndesCongratsFeedbackScreenType : AndesFeedbackScreenTypeInterface {
    override val feedbackColor = AndesFeedbackScreenColor.GREEN.color

    override fun getHeaderView(context: Context, hasBody: Boolean): View {
        return AndesFeedbackScreenCongratsHeaderView(context).takeIf { hasBody } ?: AndesFeedbackScreenSimpleHeaderView(context)
    }

    override fun getGradientVisiblity(hasBody: Boolean) =
        View.VISIBLE.takeIf { hasBody } ?: View.GONE

    override fun getCloseTintColor(context: Context, hasBody: Boolean): Int {
        return if (hasBody) {
            ContextCompat.getColor(context, R.color.andes_white)
        } else {
            ContextCompat.getColor(context, R.color.andes_gray_550)
        }
    }

    override fun getHeaderTopMargin(context: Context, hasBody: Boolean): Int {
        return if (hasBody) {
            context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_congrats_header_margin_top)
        } else {
            context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_simple_header_margin_top)
        }
    }

    override fun getDefaultAsset(context: Context, hasBody: Boolean): Drawable? {
        return feedbackColor.iconType.type.feedbackIcon(context, !hasBody)
    }

    override fun getStatusBarColor(context: Context) =
        ContextCompat.getColor(context, R.color.andes_green_500)
}
