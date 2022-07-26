package com.mercadolibre.android.andesui.tooltip

import android.app.Activity
import android.content.Context
import android.os.Looper.getMainLooper
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipAction
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipLinkAction
import com.mercadolibre.android.andesui.tooltip.factory.Constants.NO_A11Y_ACTION
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocation
import com.mercadolibre.android.andesui.tooltip.location.BottomAndesTooltipLocationConfig
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipSize
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipStyle
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.activateTalkbackForTest
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertNotEquals
import com.mercadolibre.android.andesui.utils.getActionId
import com.mercadolibre.android.andesui.utils.getBodyWindow
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.mockito.Mockito.validateMockitoUsage
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Mockito.times
import org.mockito.Mockito.never
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesTooltipTest {
    private lateinit var context: Context
    private lateinit var activity: Activity

    private val title = "My title"
    private val body = "my body"
    private val isDismissible = false
    private val location = AndesTooltipLocation.BOTTOM
    private lateinit var trigger: View

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
                // other setters
                .setRequestListeners(requestListeners)
                .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
        trigger = View(context)
        setupActivityForTesting()
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(trigger)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @After
    fun validate() {
        validateMockitoUsage()
    }

    @Test
    fun `should build default tooltip`() {
        val body = "my body"
        val isDismissibleDefault = true
        val locationDefault = AndesTooltipLocation.TOP
        val styleDefault = AndesTooltipStyle.LIGHT
        val basicTooltip = AndesTooltip(context = context, body = body)

        assertEquals(body, basicTooltip.body)
        assertEquals(isDismissibleDefault, basicTooltip.isDismissible)
        assertEquals(locationDefault, basicTooltip.location)
        assertEquals(styleDefault, basicTooltip.style)
        assertNull(basicTooltip.title)
        assertNull(basicTooltip.mainAction)
        assertNull(basicTooltip.secondaryAction)
        assertNull(basicTooltip.linkAction)
    }

    @Test
    fun `should build LIGHT tooltip`() {
        val body = "my body"
        val isDismissibleDefault = true
        val locationDefault = AndesTooltipLocation.TOP
        val styleDefault = AndesTooltipStyle.LIGHT
        val basicTooltip = AndesTooltip(context = context, body = body, style = styleDefault)

        assertEquals(body, basicTooltip.body)
        assertEquals(isDismissibleDefault, basicTooltip.isDismissible)
        assertEquals(locationDefault, basicTooltip.location)
        assertEquals(styleDefault, basicTooltip.style)
        assertNull(basicTooltip.title)
        assertNull(basicTooltip.mainAction)
        assertNull(basicTooltip.secondaryAction)
        assertNull(basicTooltip.linkAction)
    }

    @Test
    fun `should build HIGHLIGHT tooltip`() {
        val body = "my body"
        val isDismissibleDefault = true
        val locationDefault = AndesTooltipLocation.TOP
        val styleDefault = AndesTooltipStyle.HIGHLIGHT
        val basicTooltip = AndesTooltip(context = context, body = body, style = styleDefault)

        assertEquals(body, basicTooltip.body)
        assertEquals(isDismissibleDefault, basicTooltip.isDismissible)
        assertEquals(locationDefault, basicTooltip.location)
        assertEquals(styleDefault, basicTooltip.style)
        assertNull(basicTooltip.title)
        assertNull(basicTooltip.mainAction)
        assertNull(basicTooltip.secondaryAction)
        assertNull(basicTooltip.linkAction)
    }

    @Test
    fun `should build HIGHLIGHT tooltip with main and secondary actions`() {
        val body = "my body"
        val isDismissibleDefault = true
        val locationDefault = AndesTooltipLocation.TOP
        val styleDefault = AndesTooltipStyle.HIGHLIGHT
        val basicTooltip = AndesTooltip(context = context, body = body, style = styleDefault)
        val mainActionData = AndesTooltipAction(
                label = "main action",
                hierarchy = AndesButtonHierarchy.LOUD,
                onActionClicked = { _, _ -> }
        )
        val secondaryActionData = AndesTooltipAction(
                label = "secondary action",
                hierarchy = AndesButtonHierarchy.TRANSPARENT,
                onActionClicked = { _, _ -> }
        )

        basicTooltip.mainAction = mainActionData
        basicTooltip.secondaryAction = secondaryActionData

        assertEquals(body, basicTooltip.body)
        assertEquals(isDismissibleDefault, basicTooltip.isDismissible)
        assertEquals(locationDefault, basicTooltip.location)
        assertEquals(styleDefault, basicTooltip.style)
        assertNull(basicTooltip.title)
        assertEquals(basicTooltip.mainAction, mainActionData)
        assertEquals(basicTooltip.secondaryAction, secondaryActionData)
        assertNull(basicTooltip.linkAction)
    }

    @Test
    fun `should build HIGHLIGHT tooltip with link action`() {
        val body = "my body"
        val isDismissibleDefault = true
        val locationDefault = AndesTooltipLocation.TOP
        val styleDefault = AndesTooltipStyle.HIGHLIGHT
        val basicTooltip = AndesTooltip(context = context, body = body, style = styleDefault)
        val linkActionData = AndesTooltipLinkAction(
                label = "link action",
                onActionClicked = { _, _ -> }
        )

        basicTooltip.linkAction = linkActionData

        assertEquals(body, basicTooltip.body)
        assertEquals(isDismissibleDefault, basicTooltip.isDismissible)
        assertEquals(locationDefault, basicTooltip.location)
        assertEquals(styleDefault, basicTooltip.style)
        assertNull(basicTooltip.title)
        assertNull(basicTooltip.mainAction)
        assertNull(basicTooltip.secondaryAction)
        assertEquals(basicTooltip.linkAction, linkActionData)
    }

    @Test
    fun `should build DARK tooltip with link action`() {
        val body = "my body"
        val isDismissibleDefault = true
        val locationDefault = AndesTooltipLocation.TOP
        val styleDefault = AndesTooltipStyle.DARK
        val basicTooltip = AndesTooltip(context = context, body = body, style = styleDefault)
        val linkActionData = AndesTooltipLinkAction(
                label = "link action",
                onActionClicked = { _, _ -> }
        )

        basicTooltip.linkAction = linkActionData

        assertEquals(body, basicTooltip.body)
        assertEquals(isDismissibleDefault, basicTooltip.isDismissible)
        assertEquals(locationDefault, basicTooltip.location)
        assertEquals(styleDefault, basicTooltip.style)
        assertNull(basicTooltip.title)
        assertNull(basicTooltip.mainAction)
        assertNull(basicTooltip.secondaryAction)
        assertEquals(basicTooltip.linkAction, linkActionData)
    }

    @Test
    fun `should NOT build tooltip with secondary action loud in LIGHT style`() {
        val mainActionData = AndesTooltipAction(
                label = "main action",
                hierarchy = AndesButtonHierarchy.LOUD,
                onActionClicked = { _, _ -> }
        )
        val secondaryLoudAction = AndesTooltipAction(
                label = "sec action",
                hierarchy = AndesButtonHierarchy.LOUD,
                onActionClicked = { _, _ -> }
        )
        val tooltip = spy(AndesTooltip(
                context = context,
                style = AndesTooltipStyle.LIGHT,
                title = title,
                body = body,
                isDismissible = isDismissible,
                tooltipLocation = location
        ))

        tooltip.mainAction = mainActionData

        assertThrows(IllegalStateException::class.java) {
            tooltip.secondaryAction = secondaryLoudAction
        }
    }

    @Test
    fun `should NOT build tooltip with secondary action loud in HIGHLIGHT style`() {
        val mainActionData = AndesTooltipAction(
            label = "main action",
            hierarchy = AndesButtonHierarchy.LOUD,
            onActionClicked = { _, _ -> }
        )
        val secondaryLoudAction = AndesTooltipAction(
            label = "sec action",
            hierarchy = AndesButtonHierarchy.LOUD,
            onActionClicked = { _, _ -> }
        )
        val tooltip = spy(AndesTooltip(
            context = context,
            style = AndesTooltipStyle.HIGHLIGHT,
            title = title,
            body = body,
            isDismissible = isDismissible,
            tooltipLocation = location
        ))

        tooltip.mainAction = mainActionData

        assertThrows(IllegalStateException::class.java) {
            tooltip.secondaryAction = secondaryLoudAction
        }
    }

    @Test
    fun `should NOT build tooltip with main action transparent in LIGHT style`() {
        val mainTransparentAction = AndesTooltipAction(
                label = "main action",
                hierarchy = AndesButtonHierarchy.TRANSPARENT,
                onActionClicked = { _, _ -> }
        )
        val tooltip = AndesTooltip(context = context, body = body, style = AndesTooltipStyle.LIGHT)

        assertThrows(IllegalStateException::class.java) {
            tooltip.mainAction = mainTransparentAction
        }
    }

    @Test
    fun `should NOT build tooltip with main action transparent in HIGHLIGHT style`() {
        val mainTransparentAction = AndesTooltipAction(
            label = "main action",
            hierarchy = AndesButtonHierarchy.TRANSPARENT,
            onActionClicked = { _, _ -> }
        )
        val tooltip = AndesTooltip(context = context, body = body, style = AndesTooltipStyle.HIGHLIGHT)

        assertThrows(IllegalStateException::class.java) {
            tooltip.mainAction = mainTransparentAction
        }
    }

    @Test
    fun `should build tooltip with LOUD AndesButtonHierarchy on primary action button`() {
        val tooltip = spy(AndesTooltip(
                context = context,
                style = AndesTooltipStyle.LIGHT,
                title = title,
                body = body,
                isDismissible = isDismissible,
                tooltipLocation = location,
                mainAction = AndesTooltipAction("some action", AndesButtonHierarchy.LOUD) { _, _ -> }
        ))
        assertEquals(AndesButtonHierarchy.LOUD, tooltip.mainAction?.hierarchy)
    }

    @Test
    fun `should NOT build tooltip with QUIET or TRANSPARENT AndesButtonHierarchy on primary action button`() {
        val tooltip = spy(AndesTooltip(
                context = context,
                style = AndesTooltipStyle.LIGHT,
                title = title,
                body = body,
                isDismissible = isDismissible,
                tooltipLocation = location
        ))
        assertThrows(IllegalStateException::class.java) {
            tooltip.mainAction = AndesTooltipAction("some action", AndesButtonHierarchy.QUIET) { _, _ -> }
        }
        assertThrows(IllegalStateException::class.java) {
            tooltip.mainAction = AndesTooltipAction("some action", AndesButtonHierarchy.TRANSPARENT) { _, _ -> }
        }
    }

    @Test
    fun `should build tooltip with QUIET AndesButtonHierarchy on secondary action button`() {
        val tooltip = spy(AndesTooltip(
                context = context,
                style = AndesTooltipStyle.LIGHT,
                title = title,
                body = body,
                isDismissible = isDismissible,
                tooltipLocation = location,
                mainAction = AndesTooltipAction("some action", AndesButtonHierarchy.LOUD) { _, _ -> },
                secondaryAction = AndesTooltipAction("some action", AndesButtonHierarchy.QUIET) { _, _ -> }
        ))
        assertEquals(AndesButtonHierarchy.QUIET, tooltip.secondaryAction?.hierarchy)
    }

    @Test
    fun `should build tooltip with TRANSPARENT AndesButtonHierarchy on secondary action button`() {
        val tooltip = spy(AndesTooltip(
                context = context,
                style = AndesTooltipStyle.LIGHT,
                title = title,
                body = body,
                isDismissible = isDismissible,
                tooltipLocation = location,
                mainAction = AndesTooltipAction("some action", AndesButtonHierarchy.LOUD) { _, _ -> },
                secondaryAction = AndesTooltipAction("some action", AndesButtonHierarchy.TRANSPARENT) { _, _ -> }
        ))
        assertEquals(AndesButtonHierarchy.TRANSPARENT, tooltip.secondaryAction?.hierarchy)
    }

    @Test
    fun `should NOT build tooltip with LOUD AndesButtonHierarchy on secondary action button`() {
        val tooltip = spy(AndesTooltip(
                context = context,
                style = AndesTooltipStyle.LIGHT,
                title = title,
                body = body,
                isDismissible = isDismissible,
                tooltipLocation = location,
                mainAction = AndesTooltipAction("some action", AndesButtonHierarchy.LOUD) { _, _ -> }
        ))
        assertThrows(IllegalStateException::class.java) {
            tooltip.secondaryAction = AndesTooltipAction("some action", AndesButtonHierarchy.LOUD) { _, _ -> }
        }
    }

    @Test
    fun `should execute passed onClick action`() {
        val onClickFunction: (view: View, tooltip: AndesTooltip) -> Unit = spy({ _, _ -> })
        val mainActionData = mock<AndesTooltipAction>()
        `when`(mainActionData.onActionClicked).thenReturn(onClickFunction)

        val tooltip = AndesTooltip(context = context, body = body)
        tooltip.mainAction = mainActionData

        val mainActionButton = ReflectionHelpers.getField(
                tooltip,
                "primaryActionComponent"
        ) as AndesButton

        mainActionButton.performClick()
        verify(mainActionData.onActionClicked).invoke(mainActionButton, tooltip)
    }

    @Test
    fun `should execute passed onClick secondary action`() {
        val onClickFunction: (view: View, tooltip: AndesTooltip) -> Unit = spy({ _, _ -> })
        val mainActionData = mock<AndesTooltipAction>()
        `when`(mainActionData.onActionClicked).thenReturn(onClickFunction)

        val onClickFunctionSec: (view: View, tooltip: AndesTooltip) -> Unit = spy({ _, _ -> })
        val secActionData = mock<AndesTooltipAction>()
        `when`(secActionData.onActionClicked).thenReturn(onClickFunctionSec)

        val tooltip = spy(AndesTooltip(context = context, body = body))

        tooltip.mainAction = mainActionData
        tooltip.secondaryAction = secActionData

        val secActionButton = ReflectionHelpers.getField(
                tooltip,
                "secondaryActionComponent"
        ) as AndesButton

        secActionButton.performClick()
        verify(secActionData.onActionClicked).invoke(secActionButton, tooltip)
    }

    @Test
    fun `should execute passed onClick link action`() {
        val onLinkActionClicked: (view: View, tooltip: AndesTooltip) -> Unit = spy({ _, _ -> })
        val linkActionData = mock<AndesTooltipLinkAction>()
        `when`(linkActionData.onActionClicked).thenReturn(onLinkActionClicked)

        val tooltip = spy(AndesTooltip(context = context, body = body))
        tooltip.linkAction = linkActionData

        val linkActionButton = ReflectionHelpers.getField(
                tooltip,
                "linkActionComponent"
        ) as TextView

        linkActionButton.performClick()
        verify(onLinkActionClicked).invoke(linkActionButton, tooltip)
    }

    @Test
    fun `should apply title`() {
        val newTitle = "my new title"
        val tooltip = AndesTooltip(context = context, body = body)

        tooltip.title = newTitle

        assertEquals(newTitle, tooltip.title)
    }

    @Test
    fun `should apply body`() {
        val newBody = "my new body"
        val tooltip = spy(AndesTooltip(
                context = context,
                style = AndesTooltipStyle.LIGHT,
                title = title,
                body = body,
                isDismissible = isDismissible,
                tooltipLocation = location
        ))

        assertEquals(tooltip.body, body)

        tooltip.body = newBody

        assertEquals(tooltip.body, newBody)
    }

    @Test
    fun `should apply isDismissible`() {
        val newValue = true
        val tooltip = AndesTooltip(context = context, body = body)

        tooltip.isDismissible = newValue

        assertEquals(tooltip.isDismissible, newValue)
    }

    @Test
    fun `should apply mainAction`() {
        val newValue = AndesTooltipAction(
                label = "main action",
                hierarchy = AndesButtonHierarchy.LOUD,
                onActionClicked = { _, _ -> }
        )
        val tooltip = spy(AndesTooltip(
                context = context,
                style = AndesTooltipStyle.LIGHT,
                title = title,
                body = body,
                isDismissible = isDismissible,
                tooltipLocation = location
        ))

        assertNotEquals(tooltip.mainAction, newValue)

        tooltip.mainAction = newValue

        assertEquals(tooltip.mainAction, newValue)
    }

    @Test
    fun `should apply secondaryAction`() {
        val tooltip = AndesTooltip(context = context, body = body)
        val newValueMain = AndesTooltipAction(
                label = "main action",
                hierarchy = AndesButtonHierarchy.LOUD,
                onActionClicked = { _, _ -> }
        )

        val newValue = AndesTooltipAction(
                label = "sec action",
                hierarchy = AndesButtonHierarchy.QUIET,
                onActionClicked = { _, _ -> }
        )

        assertNotEquals(tooltip.secondaryAction, newValue)

        tooltip.mainAction = newValueMain
        tooltip.secondaryAction = newValue

        assertEquals(tooltip.secondaryAction, newValue)
    }

    @Test
    fun `should apply location`() {
        val newValue = AndesTooltipLocation.RIGHT
        val tooltip = spy(AndesTooltip(
                context = context,
                style = AndesTooltipStyle.LIGHT,
                title = title,
                body = body,
                isDismissible = isDismissible,
                tooltipLocation = location
        ))

        assertNotEquals(tooltip.location, newValue)

        tooltip.location = newValue

        assertEquals(tooltip.location, newValue)
    }

    @Test
    fun `should show tooltip by required location config`() {
        val tooltip = spy(AndesTooltip(context = context, body = body))
        val mockTarget: View = AndesButton(context)
        val locationConfig = mock<BottomAndesTooltipLocationConfig>()

        ReflectionHelpers.setField(tooltip, "andesTooltipLocationConfigRequired", locationConfig)
        `when`(tooltip.canShowTooltip(mockTarget)).thenReturn(true)
        `when`(locationConfig.canBuildTooltipInRequiredLocation(mockTarget)).thenReturn(true)

        tooltip.show(mockTarget)

        verify(locationConfig, times(1)).canBuildTooltipInRequiredLocation(mockTarget)
        verify(locationConfig, never()).iterateOtherLocations(mockTarget)
    }

    @Test
    fun `should show tooltip by required location config and FULL_SIZE`() {
        val tooltip = spy(AndesTooltip(context = context, title = title, body = body, andesTooltipSize = AndesTooltipSize.FULL_SIZE))
        val mockTarget: View = AndesButton(context)
        val locationConfig = mock<BottomAndesTooltipLocationConfig>()

        ReflectionHelpers.setField(tooltip, "andesTooltipLocationConfigRequired", locationConfig)
        `when`(tooltip.canShowTooltip(mockTarget)).thenReturn(true)
        `when`(locationConfig.canBuildTooltipInRequiredLocation(mockTarget)).thenReturn(true)

        tooltip.show(mockTarget)

        verify(locationConfig, times(1)).canBuildTooltipInRequiredLocation(mockTarget)
        verify(locationConfig, never()).iterateOtherLocations(mockTarget)
    }

    @Test
    fun `should show tooltip by another location config`() {
        val tooltip = spy(AndesTooltip(context = context, body = body))
        val mockTarget: View = AndesButton(context)
        val locationConfig = mock<BottomAndesTooltipLocationConfig>()
        ReflectionHelpers.setField(tooltip, "andesTooltipLocationConfigRequired", locationConfig)
        `when`(tooltip.canShowTooltip(mockTarget)).thenReturn(true)
        `when`(locationConfig.canBuildTooltipInRequiredLocation(mockTarget)).thenReturn(false)

        tooltip.show(mockTarget)

        verify(locationConfig, times(1)).canBuildTooltipInRequiredLocation(mockTarget)
        verify(locationConfig).iterateOtherLocations(mockTarget)
    }

    @Test
    fun `should showDropdown bodyWindow with config`() {
        val tooltip = spy(AndesTooltip(
                context = context,
                title = title,
                body = body
        ))
        val mockTarget: View = spy(AndesButton(context))
        val locationConfig = spy(BottomAndesTooltipLocationConfig(tooltip))
        ReflectionHelpers.setField(tooltip, "andesTooltipLocationConfigRequired", locationConfig)
        `when`(tooltip.canShowTooltip(mockTarget)).thenReturn(true)

        tooltip.show(mockTarget)

        verify(locationConfig).canBuildTooltipInRequiredLocation(mockTarget)
        verify(tooltip).showDropDown(mockTarget, -30, 0, locationConfig)
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `should dismiss tooltip`() {
        val tooltip = AndesTooltip(
            context = activity,
            title = title,
            body = body
        )
        val bodyWindow = tooltip.getBodyWindow()

        tooltip.show(trigger)
        shadowOf(getMainLooper()).idle()
        tooltip.dismiss()

        bodyWindow.isShowing assertEquals false
    }

    @Test
    fun `should not show tooltip when context is null`() {
        val activity = Robolectric.buildActivity(Activity::class.java).create().get()
        val tooltip = spy(AndesTooltip(
            context = activity,
            title = title,
            body = body
        ))

        val mockTarget: View = spy(AndesButton(activity))

        ReflectionHelpers.setField(tooltip, "context", null)
        `when`(mockTarget.isAttachedToWindow).thenReturn(true)

        assertEquals(tooltip.canShowTooltip(mockTarget), false)
    }

    @Test
    fun `should not show tooltip when context is not activity`() {
        val tooltip = spy(AndesTooltip(
            context = context,
            title = title,
            body = body
        ))

        val mockTarget: View = spy(AndesButton(context))

        ReflectionHelpers.setField(tooltip, "context", context)
        `when`(mockTarget.isAttachedToWindow).thenReturn(true)

        assertEquals(tooltip.canShowTooltip(mockTarget), false)
    }

    @Test
    fun `should not show tooltip when activity is finishing`() {
        val activity = Robolectric.buildActivity(Activity::class.java).create().get()
        val tooltip = spy(AndesTooltip(
            context = activity,
            title = title,
            body = body
        ))

        val mockTarget: View = spy(AndesButton(activity))

        `when`(mockTarget.isAttachedToWindow).thenReturn(true)
        activity.finish()

        assertEquals(tooltip.canShowTooltip(mockTarget), false)
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `should not show tooltip when tooltip is already showing`() {
        val tooltip = spy(AndesTooltip(
            context = activity,
            title = title,
            body = body
        ))

        tooltip.show(trigger)
        shadowOf(getMainLooper()).idle()

        assertEquals(tooltip.canShowTooltip(trigger), false)
    }

    @Test
    fun `should not show tooltip when target is not attached to activity`() {
        val tooltip = spy(AndesTooltip(
            context = activity,
            title = title,
            body = body
        ))

        val mockTarget: View = AndesButton(context)

        assertEquals(tooltip.canShowTooltip(mockTarget), false)
    }

    @Test
    fun `should show tooltip when tooltip is not showing and can attach to activity`() {
        val activity = Robolectric.buildActivity(Activity::class.java).create().get()
        val tooltip = spy(AndesTooltip(
            context = activity,
            title = title,
            body = body
        ))

        val mockTarget: View = spy(AndesButton(activity))

        `when`(mockTarget.isAttachedToWindow).thenReturn(true)

        assertEquals(tooltip.canShowTooltip(mockTarget), true)
    }

    @Test
    fun `should set body visibility in false when blank or empty text`() {
        val tooltip = spy(AndesTooltip(
            context = context,
            title = title,
            body = ""
        ))

        val bodyComponent = ReflectionHelpers.getField<TextView>(tooltip, "bodyComponent")

        assertEquals(bodyComponent.visibility, View.GONE)
    }

    @Test
    fun `should set body visibility in true when NOT blank or empty text`() {
        val tooltip = spy(AndesTooltip(
            context = context,
            title = title,
            body = body
        ))

        val bodyComponent = ReflectionHelpers.getField<TextView>(tooltip, "bodyComponent")

        assertEquals(bodyComponent.visibility, View.VISIBLE)
    }

    @Test
    fun `setters content description`() {
        val body = "my body"
        val bodyContentDescription = "bodyContentDescription"
        val titleContentDescription = "titleContentDescription"
        val basicTooltip = AndesTooltip(context = context, body = body)
        basicTooltip.bodyContentDescription = bodyContentDescription
        basicTooltip.titleContentDescription = titleContentDescription

        assertEquals(bodyContentDescription, basicTooltip.bodyContentDescription)
        assertEquals(titleContentDescription, basicTooltip.titleContentDescription)
    }

    @Test
    fun `setters content description null`() {
        val body = "my body"
        val title = "my title"
        val basicTooltip = AndesTooltip(context = context, body = body, title = title)
        assertNull(basicTooltip.bodyContentDescription)
        assertNull(basicTooltip.titleContentDescription)
    }

    @Test
    fun `given tooltip focusable, when changing to not focusable, getter returns correct value`() {
        val tooltip = AndesTooltip(
            context = activity,
            title = "title",
            body = "body"
        )

        tooltip.shouldGainA11yFocus = false

        tooltip.shouldGainA11yFocus assertEquals false
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `given tooltip with a11y on and focus false, when showing, then a11y action is added`() {
        activateTalkbackForTest(context)
        val tooltip = AndesTooltip(
            context = activity,
            title = "title",
            body = "body",
            shouldGainA11yFocus = false
        )

        tooltip.show(trigger)
        shadowOf(getMainLooper()).idle()
        val actionId = tooltip.getActionId()

        actionId assertNotEquals NO_A11Y_ACTION
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `given tooltip with a11y on and focus false, when calling action over trigger, no new action is added`() {
        activateTalkbackForTest(context)
        val tooltip = AndesTooltip(
            context = activity,
            title = "title",
            body = "body",
            shouldGainA11yFocus = false
        )
        tooltip.show(trigger)
        shadowOf(getMainLooper()).idle()
        tooltip.dismiss()
        val originalActionId = tooltip.getActionId()

        ViewCompat.performAccessibilityAction(trigger, originalActionId, null)
        shadowOf(getMainLooper()).idle()
        val newActionId = tooltip.getActionId()

        originalActionId assertEquals newActionId
    }
}
