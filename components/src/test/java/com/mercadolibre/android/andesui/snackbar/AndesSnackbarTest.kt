package com.mercadolibre.android.andesui.snackbar

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.test.platform.app.InstrumentationRegistry
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.snackbar.action.AndesSnackbarAction
import com.mercadolibre.android.andesui.snackbar.duration.AndesSnackbarDuration
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarType
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesSnackbarTest {

    lateinit var context: Context
    lateinit var view: View

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        context.setTheme(R.style.Theme_AppCompat_Light)
        view = CoordinatorLayout(context)
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
    fun `Snackbar, ErrorCode without action`(){
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

    private companion object {
        const val MESSAGE = "Snackbar"
        const val ERROR_CODE = "1234ABC"
        const val MESSAGE2 = "Snackbar2"
        const val ERROR_CODE2 = "1234ABC2"
        const val ACTION_TEXT = "test"

        val action = AndesSnackbarAction(
            ACTION_TEXT,
            object : View.OnClickListener{
                override fun onClick(v: View?) {}
            }
        )
        val action2 = AndesSnackbarAction(
            ACTION_TEXT,
            object : View.OnClickListener{
                override fun onClick(v: View?) {}
            }
        )
    }

    private class TestActivity: Activity()
}
