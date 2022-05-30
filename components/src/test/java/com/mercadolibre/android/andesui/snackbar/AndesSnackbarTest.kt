package com.mercadolibre.android.andesui.snackbar

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.test.platform.app.InstrumentationRegistry
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.snackbar.action.AndesSnackbarAction
import com.mercadolibre.android.andesui.snackbar.callback.AndesSnackbarCallback
import com.mercadolibre.android.andesui.snackbar.duration.AndesSnackbarDuration
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesSnackbarTest {

    lateinit var context: Context
    lateinit var view: View
    private lateinit var activity: AppCompatActivity
    private lateinit var callback: AndesSnackbarCallback

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        context.setTheme(R.style.Theme_AppCompat_Light)
        setupActivityForTesting()
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        view = CoordinatorLayout(context)
        activity.setContentView(view)
        callback = spy(object : AndesSnackbarCallback() {
            override fun onSnackbarDismissed() {
                // no-op
            }

            override fun onSnackbarShown() {
                // no-op
            }
        })
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `Snackbar, Neutral`() {
        val snackbar = AndesSnackbar(context, view, AndesSnackbarType.NEUTRAL, MESSAGE, AndesSnackbarDuration.SHORT)
        Assert.assertEquals(snackbar.type, AndesSnackbarType.NEUTRAL)
    }

    @Test
    fun `Snackbar, Success`() {
        val snackbar = AndesSnackbar(context, view, AndesSnackbarType.SUCCESS, MESSAGE, AndesSnackbarDuration.SHORT)
        Assert.assertEquals(snackbar.type, AndesSnackbarType.SUCCESS)
    }

    @Test
    fun `Snackbar, Error`() {
        val snackbar = AndesSnackbar(context, view, AndesSnackbarType.ERROR, MESSAGE, AndesSnackbarDuration.SHORT)
        Assert.assertEquals(snackbar.type, AndesSnackbarType.ERROR)
    }

    @Test
    fun `Snackbar, Short duration`() {
        val snackbar = AndesSnackbar(context, view, AndesSnackbarType.NEUTRAL, MESSAGE, AndesSnackbarDuration.SHORT)
        Assert.assertEquals(snackbar.duration, AndesSnackbarDuration.SHORT)
    }

    @Test
    fun `Snackbar, Normal duration`() {
        val snackbar = AndesSnackbar(context, view, AndesSnackbarType.NEUTRAL, MESSAGE, AndesSnackbarDuration.NORMAL)
        Assert.assertEquals(snackbar.duration, AndesSnackbarDuration.NORMAL)
    }

    @Test
    fun `Snackbar, Long duration`() {
        val snackbar = AndesSnackbar(context, view, AndesSnackbarType.NEUTRAL, MESSAGE, AndesSnackbarDuration.LONG)
        Assert.assertEquals(snackbar.duration, AndesSnackbarDuration.LONG)
    }

    @Test
    fun `Snackbar, Message`() {
        val snackbar = AndesSnackbar(context, view, AndesSnackbarType.NEUTRAL, MESSAGE, AndesSnackbarDuration.NORMAL)
        Assert.assertEquals(snackbar.text, MESSAGE)
    }

    @Test
    fun `Snackbar, isShow before show`() {
        val snackbar = AndesSnackbar(
            context,
            view,
            AndesSnackbarType.NEUTRAL,
            MESSAGE,
            AndesSnackbarDuration.SHORT
        )
        Assert.assertEquals(snackbar.isShown, false)
    }

    @Test
    fun `Snackbar, isShow after show`() {
        val snackbar = AndesSnackbar(
            context,
            view,
            AndesSnackbarType.NEUTRAL,
            MESSAGE,
            AndesSnackbarDuration.SHORT
        )
        snackbar.show()
        Assert.assertEquals(snackbar.isShown, true)
    }

    @Test
    fun `Snackbar, ErrorCode without action`() {
        val snackbar = AndesSnackbar(
            context,
            view,
            ERROR_CODE,
            MESSAGE,
            AndesSnackbarDuration.LONG
        )

        snackbar.show()
        Assert.assertEquals(snackbar.text, MESSAGE)
        Assert.assertEquals(snackbar.action, null)
        Assert.assertEquals(snackbar.errorCode, ERROR_CODE)
        Assert.assertEquals(snackbar.duration, AndesSnackbarDuration.LONG)
    }

    @Test
    fun `Snackbar, ErrorCode change attributes`() {
        val snackbar = AndesSnackbar(
            context,
            view,
            ERROR_CODE,
            MESSAGE,
            AndesSnackbarDuration.LONG
        )

        snackbar.text = MESSAGE2
        snackbar.errorCode = ERROR_CODE2
        snackbar.duration = AndesSnackbarDuration.INFINITE

        snackbar.show()
        Assert.assertEquals(snackbar.text, MESSAGE2)
        Assert.assertEquals(snackbar.action, null)
        Assert.assertEquals(snackbar.errorCode, ERROR_CODE2)
        Assert.assertEquals(snackbar.duration, AndesSnackbarDuration.INFINITE)
    }

    @Test
    fun `Snackbar, ErrorCode change type`() {
        val snackbar = AndesSnackbar(
            context,
            view,
            ERROR_CODE,
            MESSAGE,
            AndesSnackbarDuration.LONG
        )

        snackbar.type = AndesSnackbarType.NEUTRAL

        snackbar.show()
        Assert.assertEquals(snackbar.type, AndesSnackbarType.ERROR)
    }

    @Test
    fun `Snackbar, ErrorCode with action`() {
        val testActivity = Robolectric.buildActivity(TestActivity::class.java).create().get()
        val snackbar = AndesSnackbar(
            testActivity,
            view,
            ERROR_CODE,
            MESSAGE,
            AndesSnackbarDuration.LONG,
            action
        )
        snackbar.show()
        Assert.assertEquals(snackbar.text, MESSAGE)
        Assert.assertEquals(snackbar.action, action)
        Assert.assertEquals(snackbar.errorCode, ERROR_CODE)
        Assert.assertEquals(snackbar.duration, AndesSnackbarDuration.LONG)
    }

    @Test
    fun `Snackbar, ErrorCode change action`() {
        val testActivity = Robolectric.buildActivity(TestActivity::class.java).create().get()
        val snackbar = AndesSnackbar(
            testActivity,
            view,
            ERROR_CODE,
            MESSAGE,
            AndesSnackbarDuration.LONG,
            action
        )

        snackbar.action = action2

        snackbar.show()
        Assert.assertEquals(snackbar.action, action2)
    }

    @Test
    fun `Snackbar, add ErrorCode`() {
        val snackbar = AndesSnackbar(
            context,
            view,
            AndesSnackbarType.NEUTRAL,
            MESSAGE,
            AndesSnackbarDuration.SHORT
        )

        snackbar.errorCode = ERROR_CODE

        snackbar.show()
        Assert.assertEquals(snackbar.type, AndesSnackbarType.ERROR)
        Assert.assertEquals(snackbar.duration, AndesSnackbarDuration.SHORT)
        Assert.assertEquals(snackbar.errorCode, ERROR_CODE)
    }

    @Test
    fun `Snackbar, onShown callback is invoked when callback is setted from api`() {
        val snackbar = AndesSnackbar(
            context,
            view,
            AndesSnackbarType.NEUTRAL,
            MESSAGE,
            AndesSnackbarDuration.SHORT
        )

        snackbar.addCallback(callback)
        snackbar.show()

        verify(callback, times(1)).onShown(any())
        verify(callback, times(1)).onSnackbarShown()
    }

    @Test
    fun `Snackbar, onDismissed callback is invoked when callback is setted from api`() {
        val snackbar = AndesSnackbar(
            context,
            view,
            AndesSnackbarType.NEUTRAL,
            MESSAGE,
            AndesSnackbarDuration.SHORT
        )

        snackbar.addCallback(callback)
        snackbar.show()
        snackbar.dismiss()

        verify(callback, times(1)).onDismissed(any(), any())
        verify(callback, times(1)).onSnackbarDismissed()
    }

    @Test
    fun `Snackbar, onShown callback is not invoked when callback is removed from api`() {
        val snackbar = AndesSnackbar(
            context,
            view,
            AndesSnackbarType.NEUTRAL,
            MESSAGE,
            AndesSnackbarDuration.SHORT
        )

        snackbar.addCallback(callback)
        snackbar.removeCallback(callback)
        snackbar.show()

        verify(callback, times(0)).onShown(any())
        verify(callback, times(0)).onSnackbarShown()
    }

    private companion object {
        const val MESSAGE = "Snackbar"
        const val ERROR_CODE = "1234ABC"
        const val MESSAGE2 = "Snackbar2"
        const val ERROR_CODE2 = "1234ABC2"
        const val ACTION_TEXT = "test"

        val action = AndesSnackbarAction(
            ACTION_TEXT,
            object : View.OnClickListener {
                override fun onClick(v: View?) {}
            }
        )
        val action2 = AndesSnackbarAction(
            ACTION_TEXT,
            object : View.OnClickListener {
                override fun onClick(v: View?) {}
            }
        )
    }

    private class TestActivity : Activity()
}
