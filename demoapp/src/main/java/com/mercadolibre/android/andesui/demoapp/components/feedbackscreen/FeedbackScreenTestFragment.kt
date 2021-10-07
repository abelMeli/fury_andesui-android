package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mercadolibre.android.andesui.feedback.screen.AndesFeedbackScreenView
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenActions
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenHeader
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType

/**
 * A simple [Fragment] subclass.
 * Use the [FeedbackScreenTestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FeedbackScreenTestFragment : Fragment() {

    private lateinit var feedbackScreenType: AndesFeedbackScreenType
    private var feedbackHeaderThumbnail: AndesFeedbackScreenAsset? = null
    private lateinit var feedbackHeaderBody: AndesFeedbackScreenText
    private var feedbackButtonBody: AndesFeedbackScreenButton? = null
    private var feedbackCloseButton: View.OnClickListener? = null
    private var feedbackBodyItems: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return AndesFeedbackScreenView(
            context = requireContext(),
            type = feedbackScreenType,
            header = AndesFeedbackScreenHeader(
                feedbackText = feedbackHeaderBody,
                feedbackImage = feedbackHeaderThumbnail
            ),
            body = feedbackBodyItems,
            actions = AndesFeedbackScreenActions(
                button = feedbackButtonBody,
                closeCallback = feedbackCloseButton
            )
        )
    }

    @Suppress("LongParameterList")
    fun create(
        feedbackScreenType: AndesFeedbackScreenType,
        feedbackHeaderThumbnail: AndesFeedbackScreenAsset?,
        feedbackHeaderBody: AndesFeedbackScreenText,
        feedbackButtonBody: AndesFeedbackScreenButton?,
        feedbackCloseButton: View.OnClickListener?,
        feedbackBodyItems: View?
    ): Fragment {
        this.feedbackScreenType = feedbackScreenType
        this.feedbackHeaderThumbnail = feedbackHeaderThumbnail
        this.feedbackHeaderBody = feedbackHeaderBody
        this.feedbackButtonBody = feedbackButtonBody
        this.feedbackCloseButton = feedbackCloseButton
        this.feedbackBodyItems = feedbackBodyItems
        return this
    }
}
