package com.mercadolibre.android.andesui.bottomsheet

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Looper.getMainLooper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.activateTalkbackForTest
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.bottomsheet.state.AndesBottomSheetContentMargin
import com.mercadolibre.android.andesui.bottomsheet.state.AndesBottomSheetState
import com.mercadolibre.android.andesui.bottomsheet.title.AndesBottomSheetTitleAlignment
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.Mockito.any
import org.mockito.internal.util.reflection.FieldSetter
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
@LooperMode(LooperMode.Mode.PAUSED)
class AndesBottomSheetTest {
    private lateinit var rl: RelativeLayout
    private var context = RuntimeEnvironment.application
    private lateinit var andesBottomSheet: AndesBottomSheet
    private lateinit var activity: AppCompatActivity
    private lateinit var button1: Button
    private lateinit var button2: Button

    @Before
    fun setUp() {
        setupTestActivity()
    }

    @Test
    fun `only context constructor`() {
        assertEquals(andesBottomSheet.peekHeight, DEFAULT_PEEK_HEIGHT)
        assertEquals(andesBottomSheet.state, DEFAULT_BOTTOM_SHEET_STATE)
        assertEquals(andesBottomSheet.titleText, null)
        assertEquals(andesBottomSheet.titleAlignment, AndesBottomSheetTitleAlignment.CENTERED)
    }

    @Test
    fun `params constructor`() {
        val peekHeight = 250
        val title = "title"
        val fitContent = true

        andesBottomSheet = AndesBottomSheet(
            context,
            peekHeight,
            AndesBottomSheetState.EXPANDED,
            title,
            AndesBottomSheetTitleAlignment.CENTERED,
            fitContent
        )

        assertEquals(andesBottomSheet.peekHeight, peekHeight)
        assertEquals(andesBottomSheet.state, AndesBottomSheetState.EXPANDED)
        assertEquals(andesBottomSheet.titleText, title)
        assertEquals(andesBottomSheet.fitContent, fitContent)
        assertEquals(andesBottomSheet.titleAlignment, AndesBottomSheetTitleAlignment.CENTERED)
    }

    @Test
    fun `set fragment should set fragment`() {
        val mockFragment = mock(Fragment::class.java)
        val mockFragmentManager = mockFragmentManager()

        andesBottomSheet.setContent(mockFragmentManager, mockFragment)

        verify(mockFragmentManager).beginTransaction()
    }

    @Test
    fun `set View should call addView on Frame Layout`() {
        val mockFrameLayout = mock(FrameLayout::class.java)
        val mockView = mock(View::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("frameView"), mockFrameLayout)

        andesBottomSheet.setContent(mockView)

        verify(mockFrameLayout).addView(mockView)
    }

    @Test
    fun `removeViews should not call removeAllViews on frameView if no views are attached`() {
        val mockFrameLayout = mock(FrameLayout::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("frameView"), mockFrameLayout)
        `when`(mockFrameLayout.childCount).thenReturn(0)

        andesBottomSheet.removeContent()

        verify(mockFrameLayout, never()).removeAllViews()
    }

    @Test
    fun `removeViews should call removeAllViews on frameView if views are attached`() {
        val mockFrameLayout = mock(FrameLayout::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("frameView"), mockFrameLayout)
        `when`(mockFrameLayout.childCount).thenReturn(1)

        andesBottomSheet.removeContent()

        verify(mockFrameLayout).removeAllViews()
    }

    @Test
    fun `touch outside and verify that old onTouchOutside is called`() {
        val listener = mock(BottomSheetListener::class.java)

        andesBottomSheet.setBottomSheetListener(listener)
        andesBottomSheet.backgroundDimView.performClick()

        verify(listener, times(1)).onTouchOutside()
    }

    @Test
    fun `touch outside and then collapse the sheet`() {
        val listener = spy(object : OnTouchOutsideListener {
            override fun onTouchOutside(): Boolean {
                return true
            }
        })

        andesBottomSheet.state = AndesBottomSheetState.EXPANDED
        andesBottomSheet.setOnTouchOutsideListener(listener)
        andesBottomSheet.backgroundDimView.performClick()

        verify(listener, times(1)).onTouchOutside()
        assertEquals(andesBottomSheet.state, AndesBottomSheetState.COLLAPSED)
    }

    @Test
    fun `touch outside and then don't collapse the sheet`() {
        val listener = spy(object : OnTouchOutsideListener {
            override fun onTouchOutside(): Boolean {
                return false
            }
        })

        andesBottomSheet.state = AndesBottomSheetState.EXPANDED
        andesBottomSheet.setOnTouchOutsideListener(listener)
        andesBottomSheet.backgroundDimView.performClick()

        verify(listener, times(1)).onTouchOutside()
        assertEquals(andesBottomSheet.state, AndesBottomSheetState.EXPANDED)
    }

    @Test
    fun `expand method should set state to expanded from collapsed`() {
        andesBottomSheet.state = AndesBottomSheetState.COLLAPSED

        andesBottomSheet.expand()

        assertEquals(andesBottomSheet.state, AndesBottomSheetState.EXPANDED)
    }

    @Test
    fun `expand method should set state to expanded from half expanded`() {
        andesBottomSheet.state = AndesBottomSheetState.HALF_EXPANDED

        andesBottomSheet.expand()

        assertEquals(andesBottomSheet.state, AndesBottomSheetState.EXPANDED)
    }

    @Test
    fun `expand method should set state to expanded even if already expanded`() {
        andesBottomSheet.state = AndesBottomSheetState.EXPANDED

        andesBottomSheet.expand()

        assertEquals(andesBottomSheet.state, AndesBottomSheetState.EXPANDED)
    }

    @Test
    fun `collapse method should set state to collapsed from expanded`() {
        andesBottomSheet.state = AndesBottomSheetState.EXPANDED

        andesBottomSheet.collapse()

        assertEquals(andesBottomSheet.state, AndesBottomSheetState.COLLAPSED)
    }

    @Test
    fun `collapse method should set state to collapsed from half expanded`() {
        andesBottomSheet.state = AndesBottomSheetState.HALF_EXPANDED

        andesBottomSheet.collapse()

        assertEquals(andesBottomSheet.state, AndesBottomSheetState.COLLAPSED)
    }

    @Test
    fun `collapse method should set state to collapsed even if already collapsed`() {
        andesBottomSheet.state = AndesBottomSheetState.COLLAPSED

        andesBottomSheet.collapse()

        assertEquals(andesBottomSheet.state, AndesBottomSheetState.COLLAPSED)
    }

    @Test
    fun `half expand method should set state to collapsed from expanded`() {
        andesBottomSheet.state = AndesBottomSheetState.EXPANDED

        andesBottomSheet.halfExpand()

        assertEquals(andesBottomSheet.state, AndesBottomSheetState.HALF_EXPANDED)
    }

    @Test
    fun `half expand method should set state to collapsed from half collapsed`() {
        andesBottomSheet.state = AndesBottomSheetState.COLLAPSED

        andesBottomSheet.halfExpand()

        assertEquals(andesBottomSheet.state, AndesBottomSheetState.HALF_EXPANDED)
    }

    @Test
    fun `half expand method should set state to collapsed even if already half expand`() {
        andesBottomSheet.state = AndesBottomSheetState.HALF_EXPANDED

        andesBottomSheet.halfExpand()

        assertEquals(andesBottomSheet.state, AndesBottomSheetState.HALF_EXPANDED)
    }

    @Test
    fun `fit content true should set isFitToContent behavior in true`() {
        val mockSheet = mock(BottomSheetBehavior::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("bottomSheetBehavior"), mockSheet)
        andesBottomSheet.fitContent = true

        verify(mockSheet).isFitToContents = true
    }

    @Test
    fun `fit content false should set isFitToContent behavior in false`() {
        val mockSheet = mock(BottomSheetBehavior::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("bottomSheetBehavior"), mockSheet)
        andesBottomSheet.fitContent = false

        verify(mockSheet).isFitToContents = false
    }

    @Test
    fun `titleText is not null or empty should show title`() {
        val mockTextView = mock(TextView::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("titleTextView"), mockTextView)
        val text = "title"

        andesBottomSheet.titleText = text

        verify(mockTextView).setVisibility(View.VISIBLE)
        verify(mockTextView).setText(text)
    }

    @Test
    fun `titleText is null should not show title`() {
        val mockTextView = mock(TextView::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("titleTextView"), mockTextView)
        andesBottomSheet.titleText = null

        verify(mockTextView).setVisibility(View.GONE)
    }

    @Test
    fun `titleText is null or empty should not show title`() {
        val mockTextView = mock(TextView::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("titleTextView"), mockTextView)
        andesBottomSheet.titleText = ""

        verify(mockTextView).setVisibility(View.GONE)
    }

    @Test
    fun `title alignment center should show it centered`() {
        val mockTextView = mock(TextView::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("titleTextView"), mockTextView)
        andesBottomSheet.titleText = "title"
        andesBottomSheet.titleAlignment = AndesBottomSheetTitleAlignment.CENTERED

        verify(mockTextView).setGravity(Gravity.CENTER)
    }

    @Test
    fun `container margin should not have horizontal margins`() {
        val mockContainer = mock(FrameLayout::class.java)
        val marginParams = ViewGroup.MarginLayoutParams(ZERO, ZERO)
        val mockResource = mock(Resources::class.java)
        `when`(mockContainer.layoutParams).thenReturn(marginParams)
        `when`(mockResource.getDimension(R.dimen.andes_bottom_sheet_title_bottom_margin)).thenReturn(ZERO_FLOAT)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("frameView"), mockContainer)

        val margins = AndesBottomSheetContentMargin.NO_HORIZONTAL_MARGINS.margins.getMargins(mockResource)
        marginParams.setMargins(margins.leftMils, margins.topMils, margins.rightMils, margins.bottomMils)
        andesBottomSheet.setContentMargin(AndesBottomSheetContentMargin.NO_HORIZONTAL_MARGINS)

        verify(mockContainer).setLayoutParams(marginParams)
    }

    @Test
    fun `container margin should have default margins`() {
        val mockContainer = mock(FrameLayout::class.java)
        val marginParams = ViewGroup.MarginLayoutParams(ZERO, ZERO)
        val mockResource = mock(Resources::class.java)
        `when`(mockContainer.layoutParams).thenReturn(marginParams)
        `when`(mockResource.getDimension(R.dimen.andes_bottom_sheet_title_bottom_margin)).thenReturn(ZERO_FLOAT)
        `when`(mockResource.getDimension(R.dimen.andes_bottom_sheet_default_margin)).thenReturn(ZERO_FLOAT)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("frameView"), mockContainer)

        val margins = AndesBottomSheetContentMargin.DEFAULT.margins.getMargins(mockResource)
        marginParams.setMargins(margins.leftMils, margins.topMils, margins.rightMils, margins.bottomMils)
        andesBottomSheet.setContentMargin(AndesBottomSheetContentMargin.DEFAULT)

        verify(mockContainer).setLayoutParams(marginParams)
    }

    @Test
    fun `content shadow should be visible`() {
        val mockShadow = mock(View::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("contentShadow"), mockShadow)

        andesBottomSheet.setContentShadow(true)

        verify(mockShadow).setVisibility(View.VISIBLE)
    }

    @Test
    fun `content shadow should be invisible`() {
        val mockShadow = mock(View::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("contentShadow"), mockShadow)

        andesBottomSheet.setContentShadow(false)

        verify(mockShadow).setVisibility(View.GONE)
    }

    @Test
    fun `title alignment left should show it left`() {
        val mockTextView = mock(TextView::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("titleTextView"), mockTextView)
        andesBottomSheet.titleText = "title"
        andesBottomSheet.titleAlignment = AndesBottomSheetTitleAlignment.LEFT_ALIGN

        verify(mockTextView).setGravity(Gravity.START)
    }

    @Test
    fun `when  state expanded should show background dim`() {
        val mockView = mock(View::class.java)
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("backgroundDimView"), mockView)

        andesBottomSheet.expand()

        verify(mockView, never()).setVisibility(View.VISIBLE)
    }

    @Test
    fun `when set peek height then should update bottom sheet behavior peek height`() {
        val mockBottomSheetBehavior = mock(BottomSheetBehavior::class.java)
        val newPeekHeight = 300
        FieldSetter.setField(andesBottomSheet, andesBottomSheet::class.java.getDeclaredField("bottomSheetBehavior"), mockBottomSheetBehavior)

        andesBottomSheet.peekHeight = newPeekHeight

        verify(mockBottomSheetBehavior).setPeekHeight(newPeekHeight)
    }

    @Test
    fun `check that all siblings have the importantForAccessibility with IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS, when status is expanded`() {
        activateTalkbackForTest(context)

        andesBottomSheet.expand()
        shadowOf(getMainLooper()).idle()

        button1.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
        button2.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
    }

    @Test
    fun `check that all siblings have the default importantForAccessibility, when the expanded and collapsed method is invoked`() {
        activateTalkbackForTest(context)

        andesBottomSheet.expand()
        shadowOf(getMainLooper()).idle()
        andesBottomSheet.collapse()
        shadowOf(getMainLooper()).idle()

        button1.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_YES
        button2.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_YES
    }

    @Test
    fun `check that all siblings have the default importantForAccessibility, when the expanded and collapsed method is invoked after add new view to container`() {
        activateTalkbackForTest(context)
        val button3 = Button(context).apply {
            id = 10
            importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
        }

        andesBottomSheet.halfExpand()
        shadowOf(getMainLooper()).idle()

        rl.addView(button3)
        
        andesBottomSheet.collapse()
        shadowOf(getMainLooper()).idle()

        button1.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_YES
        button2.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_YES
        button3.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_YES
    }


    @Test
    fun `check that all siblings have the importantForAccessibility with IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS, when status is halfExpand`() {
        activateTalkbackForTest(context)

        andesBottomSheet.halfExpand()
        shadowOf(getMainLooper()).idle()

        button1.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
        button2.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
    }

    @Test
    fun `check that all siblings have the default importantForAccessibility, when the expanded, halfExpanded and collapsed method is invoked`() {
        activateTalkbackForTest(context)

        andesBottomSheet.expand()
        shadowOf(getMainLooper()).idle()
        andesBottomSheet.halfExpand()
        shadowOf(getMainLooper()).idle()
        andesBottomSheet.collapse()
        shadowOf(getMainLooper()).idle()

        button1.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_YES
        button2.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_YES
    }

    @Test
    fun `check that all siblings have the default importantForAccessibility, when the expanded, halfExpanded and collapsed method is invoked  after add new view to container`() {
        activateTalkbackForTest(context)
        val button3 = Button(context).apply {
            id = 10
            importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
        }
        
        andesBottomSheet.expand()
        shadowOf(getMainLooper()).idle()

        rl.addView(button3)

        andesBottomSheet.halfExpand()
        shadowOf(getMainLooper()).idle()
        andesBottomSheet.collapse()
        shadowOf(getMainLooper()).idle()

        button1.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_YES
        button2.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_YES
        button3.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_YES
    }

    @Test
    fun `check that all siblings have the same importantForAccessibility, when status is expanded and a11y is off`() {
        andesBottomSheet.expand()
        shadowOf(getMainLooper()).idle()

        button1.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_YES
        button2.importantForAccessibility assertEquals View.IMPORTANT_FOR_ACCESSIBILITY_YES
    }

    @Test
    fun `when set transparent dimmer the bottom sheet get transparent color`() {
        andesBottomSheet.setDimmerColorTransparent()

        val color = (andesBottomSheet.backgroundDimView.background as ColorDrawable).color

        assertEquals(Color.TRANSPARENT, color)
    }

    @Test
    fun `when uses default dimmer the bottom sheet get gray color`() {
        val color = (andesBottomSheet.backgroundDimView.background as ColorDrawable).color

        val defaultColor = ContextCompat.getColor(context, R.color.andes_gray_800)

        assertEquals(defaultColor, color)
    }

    @Test
    fun `when set default dimmer the bottom sheet get gray color`() {
        andesBottomSheet.setDimmerColorDefault()

        val color = (andesBottomSheet.backgroundDimView.background as ColorDrawable).color

        val defaultColor = ContextCompat.getColor(context, R.color.andes_gray_800)

        assertEquals(defaultColor, color)
    }

    private fun mockFragmentManager(): FragmentManager {
        val fragmentTransaction = mock(FragmentTransaction::class.java)
        `when`(fragmentTransaction.replace(anyOrNull(), any(Fragment::class.java))).thenReturn(fragmentTransaction)
        `when`(fragmentTransaction.commit()).thenReturn(0)

        val fragmentManager = mock(FragmentManager::class.java)
        `when`(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction)

        return fragmentManager
    }

    private fun setupTestActivity() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
        rl = RelativeLayout(activity).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
        }
        activity.setTheme(R.style.Theme_AppCompat_NoActionBar)
        button1 = Button(activity).apply {
            id = 1
            importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
        }
        button2 = Button(activity).apply {
            id = 2
            importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
        }
        andesBottomSheet = AndesBottomSheet(activity)
        rl.addView(button1)
        rl.addView(button2)
        rl.addView(andesBottomSheet)
        activity.setContentView(rl)

        robolectricActivity.start().postCreate(null).resume().visible()
    }

    companion object {
        private const val ZERO = 0
        private const val ZERO_FLOAT = 0f
        private const val DEFAULT_PEEK_HEIGHT = 0
        private val DEFAULT_BOTTOM_SHEET_STATE = AndesBottomSheetState.COLLAPSED
    }
}
