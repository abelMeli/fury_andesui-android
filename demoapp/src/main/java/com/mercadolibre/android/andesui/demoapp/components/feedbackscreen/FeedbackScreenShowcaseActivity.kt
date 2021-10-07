package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType

class FeedbackScreenShowcaseActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.andesui_showcase_feedback)

        supportFragmentManager.setupForAccessibility()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FeedbackScreenConfigFragment())
            .commit()
    }

    @Suppress("LongParameterList")
    fun goToFeedbackFragment(
        feedbackScreenType: AndesFeedbackScreenType,
        feedbackHeaderThumbnail: AndesFeedbackScreenAsset?,
        feedbackHeaderBody: AndesFeedbackScreenText,
        feedbackButtonBody: AndesFeedbackScreenButton?,
        feedbackCloseButton: View.OnClickListener?,
        feedbackBodyItems: View?
    ) {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                FeedbackScreenTestFragment().create(
                    feedbackScreenType,
                    feedbackHeaderThumbnail,
                    feedbackHeaderBody,
                    feedbackButtonBody,
                    feedbackCloseButton,
                    feedbackBodyItems
                ),
                FeedbackScreenTestFragment::class.java.simpleName
            )
            .addToBackStack(FeedbackScreenTestFragment::class.java.simpleName)
            .commit()
    }
}

fun FragmentManager.setupForAccessibility() {
    addOnBackStackChangedListener {
        val lastFragmentWithView = fragments.last { it.view != null }
        for (fragment in fragments) {
            if (fragment == lastFragmentWithView) {
                fragment.view?.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_AUTO
            } else {
                fragment.view?.importantForAccessibility =
                    View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
            }
        }
    }
}
