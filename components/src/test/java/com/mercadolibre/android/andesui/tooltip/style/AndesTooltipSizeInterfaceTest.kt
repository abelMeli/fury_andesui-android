package com.mercadolibre.android.andesui.tooltip.style

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.tooltip.location.*
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.spy
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE], qualifiers = "w720dp-h1600dp-mdpi")
class AndesTooltipSizeInterfaceTest {

    private val tooltipMeasures = spy(AndesTooltipLocationInterfaceImplTest)
    private val arrowLocation = mock(AndesTooltipArrowLocation::class.java)
    private val context = RuntimeEnvironment.application

    @Before
    fun setup() {
        setupMocks()
    }

    private fun setupActivityForTesting(view: View): ActivityController<AppCompatActivity>? {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(view)
        robolectricActivity.start().postCreate(null).resume().visible()
        return robolectricActivity
    }

    private fun setupMocks() {
        `when`(arrowLocation.getArrowPositionY(tooltipMeasures)).thenReturn(Y_VALUE)
    }

    @Test
    fun `when getArrowPoint in FULL_SIZE then return x Value`() {
        val returnedPoint = AndesTooltipSize.FULL_SIZE.type
            .getArrowPoint(arrowLocation, tooltipMeasures, FIXED_X_VALUE.toInt())

        assertEquals(returnedPoint.x, arrowLocation.getArrowPositionX(tooltipMeasures))
    }

    @Test
    fun `when getArrowPoint in FULL_SIZE then return fixedValue`() {
        //GIVE
        val tooltipMeasures = spy(AndesTooltipLocationInterfaceImplTestFullsize)

        //WHEN
        val returnedPoint = AndesTooltipSize.FULL_SIZE.type
            .getArrowPoint(arrowLocation, tooltipMeasures, FIXED_X_VALUE.toInt())

        //THEN
        assertEquals(returnedPoint.x, FIXED_X_VALUE)
    }

    @Test
    fun `when getTooltipXOffForSize in FULL_SIZE then return positionLeft`() {
        //GIVE
        val tooltipMeasures = spy(AndesTooltipLocationInterfaceImplTestFullsize)
        val mockView = spy(View(context))

        //WHEN
        val returnedArrowData = AndesTooltipSize.FULL_SIZE.type.getTooltipXOffForSize(mockView, tooltipMeasures)
        val expectedPoint = tooltipMeasures.paddingWithArrowHorizontal +
            tooltipMeasures.arrowWidth / 2 +
            tooltipMeasures.arrowBorder -
            tooltipMeasures.paddingWithArrowHorizontal

        //THEN
        assertEquals(ArrowPositionId.LEFT, returnedArrowData.positionInSide)
        assertEquals(expectedPoint, returnedArrowData.point)
    }

    @Test
    fun `when getTooltipXOffForSize in FULL_SIZE then return positionLeft_cut`() {
        //GIVE
        val tooltipMeasures = spy(AndesTooltipLocationInterfaceImplTestFullsize_cut)
        val view = View(context)
        val container = ConstraintLayout(context)
        view.id = 3

        with(view){
            layoutParams = FrameLayout.LayoutParams(235,90)
        }

        container.addView(view)

        ConstraintSet().apply {
            clone(container)
            connect(view.id,ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,0)
            connect(view.id,ConstraintSet.END,ConstraintSet.PARENT_ID,ConstraintSet.END,0)
            connect(view.id,ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START,
                0)
            setHorizontalBias(view.id, 0F)
            applyTo(container)
        }

        setupActivityForTesting(container)
        val mockView = spy(view)

        //WHEN
        val returnedArrowData = AndesTooltipSize.FULL_SIZE.type.getTooltipXOffForSize(mockView,
            tooltipMeasures)
        val expectedPoint = view.measuredWidth / 2 - tooltipMeasures.arrowWidth / 2 -
                tooltipMeasures.arrowBorder - tooltipMeasures.paddingWithArrowHorizontal

        //THEN
        assertEquals(ArrowPositionId.LEFT, returnedArrowData.positionInSide)
        assertEquals(expectedPoint, returnedArrowData.point)
    }

    @Test
    fun `when getTooltipXOffForSize in FULL_SIZE then return positionRight`() {
        //GIVE
        val tooltipMeasures = spy(AndesTooltipLocationInterfaceImplTestFullsize_cut)
        val view = View(context)
        val container = ConstraintLayout(context)
        view.id = 2

        with(view){
            layoutParams = FrameLayout.LayoutParams(235,90)
        }

        container.addView(view)

        ConstraintSet().apply {
            clone(container)
            connect(view.id,ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,0)
            connect(view.id,ConstraintSet.END,ConstraintSet.PARENT_ID,ConstraintSet.END,0)
            connect(view.id,ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START,0)
            connect(view.id,ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM,0)
            setHorizontalBias(view.id, 90F)
            applyTo(container)
        }

        setupActivityForTesting(container)
        val mockView = spy(view)

        //WHEN
        val returnedArrowData = AndesTooltipSize.FULL_SIZE.type.getTooltipXOffForSize(mockView, tooltipMeasures)
        val expectedPoint = -tooltipMeasures.tooltipMeasuredWidth + view.measuredWidth / 2 + tooltipMeasures.arrowWidth / 2 +
                tooltipMeasures.arrowBorder + tooltipMeasures.paddingWithArrowHorizontal

        //THEN
        assertEquals(ArrowPositionId.RIGHT, returnedArrowData.positionInSide)
        assertEquals(expectedPoint, returnedArrowData.point)
    }

    @Test
    fun `when getTooltipXOffForSize in FULL_SIZE then return positionCenter`() {
        //GIVE
        val tooltipMeasures = spy(AndesTooltipLocationInterfaceImplTestFullsize_cut)
        val view = View(context)
        val container = ConstraintLayout(context)
        view.id = 1

        with(view){
            layoutParams = FrameLayout.LayoutParams(235,90)
        }

        container.addView(view)

        ConstraintSet().apply {
            clone(container)
            connect(view.id,ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,0)
            connect(view.id,ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START,0)
            connect(view.id,ConstraintSet.END,ConstraintSet.PARENT_ID,ConstraintSet.END,0)
            applyTo(container)
        }

        setupActivityForTesting(container)
        val mockView = spy(view)

        //WHEN
        val returnedArrowData = AndesTooltipSize.FULL_SIZE.type.getTooltipXOffForSize(mockView, tooltipMeasures)
        val expectedPoint = ((view.measuredWidth / 2) - (tooltipMeasures.tooltipMeasuredWidth / 2))

        //THEN
        assertEquals(ArrowPositionId.MIDDLE, returnedArrowData.positionInSide)
        assertEquals(expectedPoint, returnedArrowData.point)
    }

    @Test
    fun `when get fromString then return proper one`() {
        val returnedFullSize = AndesTooltipSize.fromString("full_size")
        val returnedDynamic = AndesTooltipSize.fromString("dynamic")

        assertEquals(AndesTooltipSize.FULL_SIZE, returnedFullSize)
        assertEquals(AndesTooltipSize.DYNAMIC, returnedDynamic)
    }

    @Test
    fun `when get fromString then throw IllegalArgumentException`() {
        assertThrows(IllegalArgumentException::class.java) { AndesTooltipSize.fromString("other") }
    }

    companion object {
        private const val FIXED_X_VALUE = 1F
        private const val Y_VALUE = 123F
    }
}
