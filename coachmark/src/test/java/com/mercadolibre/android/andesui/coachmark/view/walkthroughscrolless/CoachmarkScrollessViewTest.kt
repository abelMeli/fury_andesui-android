package com.mercadolibre.android.andesui.coachmark.view.walkthroughscrolless

import android.os.Looper
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.model.AndesScrollessWalkthroughCoachmark
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStep
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStyle
import com.mercadolibre.android.andesui.coachmark.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.coachmark.utils.assertEquals
import com.mercadolibre.android.andesui.coachmark.utils.getDeclaredFieldValue
import org.junit.Assert
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

    @Test
    fun `given activity with support action bar, when taking measures, then value is correct`() {
        val coachmarkScrollessView = provideCoachmark()
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val toolbarSize = coachmarkScrollessView.getToolbarSize()

        Assert.assertNotEquals(0, toolbarSize)
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `given activity with custom toolbar, when taking toolbar measures, then value is correct`() {
        activity.supportActionBar?.hide()
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        (content.getChildAt(0) as ViewGroup).addView(Toolbar(activity))
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        val coachmarkScrollessView = provideCoachmark()
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val toolbarSize = coachmarkScrollessView.getToolbarSize()

        Assert.assertNotEquals(0, toolbarSize)
    }

    @Test
    fun `given activity with no toolbar, when taking toolbar measures, then value is correct`() {
        activity.supportActionBar?.hide()
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        val coachmarkScrollessView = provideCoachmark()
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val toolbarSize = coachmarkScrollessView.getToolbarSize()

        Assert.assertEquals(0, toolbarSize)
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        createContent(activity)
        activity.setContentView(content)
        activity.supportActionBar?.show()
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    private fun createContent(activity: AppCompatActivity) {
        content = NestedScrollView(activity).apply {
            addView(LinearLayout(activity))
        }
    }

    private fun WalkthroughScrollessMessageView.getNextButton() =
        findViewById<TextView>(R.id.walkthroughNextButton)

    private fun provideCoachmark(): CoachmarkScrollessView {
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
                    AndesWalkthroughCoachmarkStyle.CIRCLE
                )
            ),
            content
        ) {
            // no-op
        }
        return CoachmarkScrollessView.Builder(activity, coachmarkData).build()
    }
}
