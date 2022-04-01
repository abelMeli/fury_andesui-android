package com.mercadolibre.android.andesui.coachmark.view

import android.os.Build
import android.os.Looper
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmark
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStep
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStyle
import com.mercadolibre.android.andesui.coachmark.utils.assertEquals
import com.mercadolibre.android.andesui.coachmark.utils.getDeclaredFieldValue
import com.mercadolibre.android.andesui.coachmark.view.walkthroughmessage.WalkthroughMessageView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
@LooperMode(LooperMode.Mode.PAUSED)
class CoachmarkViewTest {

    private lateinit var content: NestedScrollView
    private lateinit var activity: AppCompatActivity

    @Before
    fun setUp() {
        setupActivityForTest()
    }

    @Test
    fun `CoachMark WalkthroughMessage color single item`() {
        // GIVEN
        val coachmarkData = AndesWalkthroughCoachmark(
            mutableListOf(
                AndesWalkthroughCoachmarkStep("Title", "Description", "Finalizar", null, AndesWalkthroughCoachmarkStyle.RECTANGLE)
            ),
            content
        ) {
            // no-op
        }
        val caochmarkView = CoachmarkView.Builder(activity, coachmarkData).build()

        // WHEN CREATED
        shadowOf(Looper.getMainLooper()).idle()

        // THEN
        val messageView = caochmarkView.getDeclaredFieldValue<WalkthroughMessageView>("walkthroughMessageView")
        messageView.getNextButton().background assertEquals R.drawable.andes_walkthrough_configuration_blue_button_background
    }

    @Test
    fun `CoachMark WalkthroughMessage color first item of more than one`() {
        // GIVEN
        val coachmarkData = AndesWalkthroughCoachmark(
            mutableListOf(
                AndesWalkthroughCoachmarkStep("Title", "Description", "Finalizar", null, AndesWalkthroughCoachmarkStyle.RECTANGLE),
                AndesWalkthroughCoachmarkStep("Title 2", "Description 2", "Finalizar 2", null, AndesWalkthroughCoachmarkStyle.RECTANGLE)
            ),
            content
        ) {
            // no-op
        }
        val caochmarkView = CoachmarkView.Builder(activity, coachmarkData).build()

        // WHEN CREATED
        shadowOf(Looper.getMainLooper()).idle()

        // THEN
        val messageView = caochmarkView.getDeclaredFieldValue<WalkthroughMessageView>("walkthroughMessageView")
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

    private fun WalkthroughMessageView.getNextButton() =
        findViewById<TextView>(R.id.walkthroughNextButton)
}
