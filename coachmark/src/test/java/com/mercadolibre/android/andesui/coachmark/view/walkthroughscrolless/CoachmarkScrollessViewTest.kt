package com.mercadolibre.android.andesui.coachmark.view.walkthroughscrolless

import android.os.Looper
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.model.AndesScrollessWalkthroughCoachmark
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStep
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStyle
import com.mercadolibre.android.andesui.coachmark.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.coachmark.utils.assertEquals
import com.mercadolibre.android.andesui.coachmark.utils.getDeclaredFieldValue
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
@LooperMode(LooperMode.Mode.PAUSED)
class CoachmarkScrollessViewTest {

    private lateinit var content: NestedScrollView
    private lateinit var activity: AppCompatActivity

    @Before
    fun setUp() {
        setupActivityForTest()
    }

    @Test
    fun `Scrolless CoachMark WalkthroughMessage color single item`() {
        // GIVEN
        val coachmarkData = AndesScrollessWalkthroughCoachmark(
            mutableListOf(
                AndesWalkthroughCoachmarkStep(
                    "Title",
                    "Description",
                    "Finalizar",
                    content.getChildAt(0),
                    AndesWalkthroughCoachmarkStyle.RECTANGLE
                )
            ),
            content
        ) {
            // no-op
        }
        val coachmarkScrollessView = CoachmarkScrollessView.Builder(activity, coachmarkData).build()

        // WHEN CREATED
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        // THEN
        val messageView = coachmarkScrollessView.getDeclaredFieldValue<WalkthroughScrollessMessageView>("walkthroughScrollessMessageView")
        messageView.getNextButton().background assertEquals R.drawable.andes_walkthrough_configuration_blue_button_background
    }

    @Test
    fun `Scrolless CoachMark WalkthroughMessage color first item of more than one`() {
        // GIVEN
        val coachmarkData = AndesScrollessWalkthroughCoachmark(
            mutableListOf(
                AndesWalkthroughCoachmarkStep(
                    "Title",
                    "Description",
                    "Finalizar",
                    content.getChildAt(0),
                    AndesWalkthroughCoachmarkStyle.RECTANGLE
                ),
                AndesWalkthroughCoachmarkStep(
                    "Title 2",
                    "Description 2",
                    "Finalizar 2",
                    content.getChildAt(0),
                    AndesWalkthroughCoachmarkStyle.RECTANGLE
                )
            ),
            content
        ) {
            // no-op
        }
        val coachmarkScrollessView = CoachmarkScrollessView.Builder(activity, coachmarkData).build()

        // WHEN CREATED
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        // THEN
        val messageView = coachmarkScrollessView.getDeclaredFieldValue<WalkthroughScrollessMessageView>("walkthroughScrollessMessageView")
        messageView.getNextButton().background assertEquals R.drawable.andes_walkthrough_configuration_button_background
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        createContent(activity)
        activity.setContentView(content)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    private fun createContent(activity: AppCompatActivity) {
        content = NestedScrollView(activity).apply {
            addView(LinearLayout(activity))
        }
    }

    private fun WalkthroughScrollessMessageView.getNextButton() =
        findViewById<TextView>(R.id.walkthroughNextButton)
}
