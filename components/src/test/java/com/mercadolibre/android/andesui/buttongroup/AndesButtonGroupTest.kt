package com.mercadolibre.android.andesui.buttongroup

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.buildAttributeSet
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.size.AndesButtonSize
import com.mercadolibre.android.andesui.buttongroup.align.AndesButtonGroupAlign
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.buttongroup.factory.AndesButtonGroupConfigurationFactory
import com.mercadolibre.android.andesui.buttongroup.type.AndesButtonGroupType
import com.mercadolibre.android.andesui.buttongroup.utils.FIRST_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.SECOND_BUTTON
import com.mercadolibre.android.andesui.buttongroup.utils.THIRD_BUTTON
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.getAllChildren
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesButtonGroupTest {

    private var marginLarge: Int = 0

    private lateinit var context: Context

    private var expectedMargin: Int = FIRST_BUTTON

    companion object {
        private const val DISTRIBUTION_VERTICAL = "2001"
        private const val TYPE_FULL_WIDTH = "1000"
        private const val ALIGN_LEFT = "3000"
        private const val NO_CONSTRAINT = -1
    }

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.setTheme(R.style.Theme_AppCompat)
        marginLarge = context.resources.getDimensionPixelSize(R.dimen.andes_button_group_margin_large)
        expectedMargin = marginLarge / 2

        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

    @Test
    fun `ConstraintSet with 1 button, and Fullwidth type`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1"))
        )
        val constraintButton1 = andesButtonGroup.getChildAt(FIRST_BUTTON).layoutParams as ConstraintLayout.LayoutParams
        constraintButton1.startToStart assertEquals ConstraintSet.PARENT_ID
        constraintButton1.endToEnd assertEquals ConstraintSet.PARENT_ID
    }

    @Test
    fun `ConstraintSet with 1 button, Responsive type and align LEFT`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1")),
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.LEFT)
        )

        val constraintButton1 = andesButtonGroup.getChildAt(FIRST_BUTTON).layoutParams as ConstraintLayout.LayoutParams

        constraintButton1.startToStart assertEquals ConstraintSet.PARENT_ID
        constraintButton1.endToEnd assertEquals NO_CONSTRAINT
    }

    @Test
    fun `ConstraintSet with 1 button, Responsive type and align RIGHT`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1")),
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.RIGHT)
        )

        val constraintButton1 = andesButtonGroup.getChildAt(FIRST_BUTTON).layoutParams as ConstraintLayout.LayoutParams

        constraintButton1.endToEnd assertEquals ConstraintSet.PARENT_ID
        constraintButton1.startToStart assertEquals NO_CONSTRAINT
    }

    @Test
    fun `ConstraintSet with 1 button, Responsive type and align CENTER`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1")),
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.CENTER)
        )

        val constraintButton1 = andesButtonGroup.getChildAt(FIRST_BUTTON).layoutParams as ConstraintLayout.LayoutParams

        constraintButton1.endToEnd assertEquals ConstraintSet.PARENT_ID
        constraintButton1.startToStart assertEquals ConstraintSet.PARENT_ID
    }

    @Test
    fun `ConstraintSet with 2 button, HORIZONTAL distribution and Fullwidth type`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1"), AndesButton(context, buttonText = "Button 2"))
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToStart assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[FIRST_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[SECOND_BUTTON].startToEnd assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].marginStart assertEquals expectedMargin
    }

    @Test
    fun `ConstraintSet with 2 button, HORIZONTAL distribution and Responsive type and LEFT align`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1"), AndesButton(context, buttonText = "Button 2")),
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.LEFT)
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToStart assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[FIRST_BUTTON].marginStart assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[FIRST_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[SECOND_BUTTON].startToEnd assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[SECOND_BUTTON].marginStart assertEquals expectedMargin
    }

    @Test
    fun `ConstraintSet with 2 button, HORIZONTAL distribution and Responsive type and RIGHT align`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1"), AndesButton(context, buttonText = "Button 2")),
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.RIGHT)
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[FIRST_BUTTON].endToStart assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[FIRST_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[SECOND_BUTTON].startToEnd assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].marginStart assertEquals expectedMargin
    }

    @Test
    fun `ConstraintSet with 2 button, HORIZONTAL distribution and Responsive type and CENTER align`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1"), AndesButton(context, buttonText = "Button 2")),
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.CENTER)
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToStart assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[FIRST_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[SECOND_BUTTON].startToEnd assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].marginStart assertEquals expectedMargin
    }

    @Test
    fun `ConstraintSet with 3 button, HORIZONTAL distribution and Fullwidth type`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "Button 1"),
                AndesButton(context, buttonText = "Button 2"),
                AndesButton(context, buttonText = "Button 3")
            )
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToStart assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[FIRST_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[SECOND_BUTTON].startToEnd assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].endToStart assertEquals button[THIRD_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].marginStart assertEquals expectedMargin

        buttonAsLayoutParam[THIRD_BUTTON].startToEnd assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[THIRD_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[THIRD_BUTTON].marginStart assertEquals expectedMargin
    }

    @Test
    fun `ConstraintSet with 3 button, HORIZONTAL distribution and Responsive type and LEFT align`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "Button 1"),
                AndesButton(context, buttonText = "Button 2"),
                AndesButton(context, buttonText = "Button 3")
            ),
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.LEFT)
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToStart assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[FIRST_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[SECOND_BUTTON].startToEnd assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[SECOND_BUTTON].marginStart assertEquals expectedMargin
        buttonAsLayoutParam[SECOND_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[THIRD_BUTTON].startToEnd assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[THIRD_BUTTON].endToEnd assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[THIRD_BUTTON].marginStart assertEquals expectedMargin
    }

    @Test
    fun `ConstraintSet with 3 button, HORIZONTAL distribution and Responsive type and RIGHT align`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "Button 1"),
                AndesButton(context, buttonText = "Button 2"),
                AndesButton(context, buttonText = "Button 3")
            ),
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.RIGHT)
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[FIRST_BUTTON].endToStart assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[FIRST_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[SECOND_BUTTON].startToEnd assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[SECOND_BUTTON].endToStart assertEquals button[THIRD_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].marginStart assertEquals expectedMargin
        buttonAsLayoutParam[SECOND_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[THIRD_BUTTON].startToEnd assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[THIRD_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[THIRD_BUTTON].marginStart assertEquals expectedMargin
    }

    @Test
    fun `ConstraintSet with 3 button, HORIZONTAL distribution and Responsive type and CENTER align`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "Button 1"),
                AndesButton(context, buttonText = "Button 2"),
                AndesButton(context, buttonText = "Button 3")
            ),
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.CENTER)
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToStart assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[FIRST_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[SECOND_BUTTON].startToEnd assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].endToStart assertEquals button[THIRD_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].marginStart assertEquals expectedMargin
        buttonAsLayoutParam[SECOND_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[THIRD_BUTTON].startToEnd assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[THIRD_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[THIRD_BUTTON].marginStart assertEquals expectedMargin
    }

    @Test
    fun `ConstraintSet with 2 button, VERTICAL distribution and Fullwidth type`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1"), AndesButton(context, buttonText = "Button 2")),
            distribution = AndesButtonGroupDistribution.VERTICAL
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].topToTop assertEquals ConstraintSet.PARENT_ID

        buttonAsLayoutParam[SECOND_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].topToBottom assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].topMargin assertEquals marginLarge
    }

    @Test
    fun `ConstraintSet with 2 button, VERTICAL distribution, Responsive type and LEFT align`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1"), AndesButton(context, buttonText = "Button 2")),
            distribution = AndesButtonGroupDistribution.VERTICAL,
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.LEFT)
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToEnd assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[FIRST_BUTTON].topToTop assertEquals ConstraintSet.PARENT_ID

        buttonAsLayoutParam[SECOND_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[SECOND_BUTTON].topToBottom assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].topMargin assertEquals marginLarge
    }

    @Test
    fun `ConstraintSet with 2 button, VERTICAL distribution, Responsive type and RIGHT align`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1"), AndesButton(context, buttonText = "Button 2")),
            distribution = AndesButtonGroupDistribution.VERTICAL,
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.RIGHT)
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[FIRST_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].topToTop assertEquals ConstraintSet.PARENT_ID

        buttonAsLayoutParam[SECOND_BUTTON].startToStart assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].topToBottom assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].topMargin assertEquals marginLarge
    }

    fun `ConstraintSet with 2 button, VERTICAL distribution, Responsive type and CENTER align`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1"), AndesButton(context, buttonText = "Button 2")),
            distribution = AndesButtonGroupDistribution.VERTICAL,
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.CENTER)
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].topToTop assertEquals ConstraintSet.PARENT_ID

        buttonAsLayoutParam[SECOND_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].topToBottom assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].topMargin assertEquals marginLarge
    }

    @Test
    fun `ConstraintSet with 3 button, VERTICAL distribution and Fullwidth type`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "Button 1"),
                AndesButton(context, buttonText = "Button 2"),
                AndesButton(context, buttonText = "Button 3")
            ),
            distribution = AndesButtonGroupDistribution.VERTICAL
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].topToTop assertEquals ConstraintSet.PARENT_ID

        buttonAsLayoutParam[SECOND_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].topToBottom assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].topMargin assertEquals marginLarge

        buttonAsLayoutParam[THIRD_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[THIRD_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[THIRD_BUTTON].topToBottom assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[THIRD_BUTTON].topMargin assertEquals marginLarge
    }

    @Test
    fun `ConstraintSet with 3 button, VERTICAL distribution, Responsive type and LEFT align`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "Button 1"),
                AndesButton(context, buttonText = "Button 2"),
                AndesButton(context, buttonText = "Button 3")
            ),
            distribution = AndesButtonGroupDistribution.VERTICAL,
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.LEFT)
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToEnd assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[FIRST_BUTTON].topToTop assertEquals ConstraintSet.PARENT_ID

        buttonAsLayoutParam[SECOND_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[SECOND_BUTTON].topToBottom assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].topMargin assertEquals marginLarge

        buttonAsLayoutParam[THIRD_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[THIRD_BUTTON].endToEnd assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[THIRD_BUTTON].topToBottom assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[THIRD_BUTTON].topMargin assertEquals marginLarge
    }

    @Test
    fun `ConstraintSet with 3 button, VERTICAL distribution, Responsive type and RIGHT align`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "Button 1"),
                AndesButton(context, buttonText = "Button 2"),
                AndesButton(context, buttonText = "Button 3")
            ),
            distribution = AndesButtonGroupDistribution.VERTICAL,
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.RIGHT)
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[FIRST_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].topToTop assertEquals ConstraintSet.PARENT_ID

        buttonAsLayoutParam[SECOND_BUTTON].startToStart assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].topToBottom assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].topMargin assertEquals marginLarge

        buttonAsLayoutParam[THIRD_BUTTON].startToStart assertEquals NO_CONSTRAINT
        buttonAsLayoutParam[THIRD_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[THIRD_BUTTON].topToBottom assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[THIRD_BUTTON].topMargin assertEquals marginLarge
    }

    @Test
    fun `ConstraintSet with 3 button, VERTICAL distribution, Responsive type and CENTER align`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "Button 1"),
                AndesButton(context, buttonText = "Button 2"),
                AndesButton(context, buttonText = "Button 3")
            ),
            distribution = AndesButtonGroupDistribution.VERTICAL,
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.CENTER)
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].topToTop assertEquals ConstraintSet.PARENT_ID

        buttonAsLayoutParam[SECOND_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].topToBottom assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].topMargin assertEquals marginLarge

        buttonAsLayoutParam[THIRD_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[THIRD_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[THIRD_BUTTON].topToBottom assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[THIRD_BUTTON].topMargin assertEquals marginLarge
    }

    @Test @Config(qualifiers = "w320dp")
    fun `ConstraintSet with 2 button, AUTO distribution, Fullwidth type and recommended distribution HORIZONTAL`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "Button 1"),
                AndesButton(context, buttonText = "Button 2")
            ),
            distribution = AndesButtonGroupDistribution.AUTO
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToStart assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[FIRST_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[SECOND_BUTTON].startToEnd assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].marginStart assertEquals expectedMargin
    }

    @Test @Config(qualifiers = "w120dp")
    fun `ConstraintSet with 2 button, AUTO distribution, Fullwidth type and recommended distribution VERTICAL`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "ENVIAR A MI DOMICILIO"),
                AndesButton(context, buttonText = "RETIRAR POR UNA SUCURSAL")
            ),
            distribution = AndesButtonGroupDistribution.AUTO
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].topToTop assertEquals ConstraintSet.PARENT_ID

        buttonAsLayoutParam[SECOND_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].topToBottom assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].topMargin assertEquals marginLarge

    }

    @Test @Config(qualifiers = "w360dp")
    fun `ConstraintSet with 2 button, MIXED distribution, Fullwidth type and recommended distribution HORIZONTAL`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "Button 1"),
                AndesButton(context, buttonText = "Button 2")
            ),
            distribution = AndesButtonGroupDistribution.MIXED
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToStart assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[FIRST_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[SECOND_BUTTON].startToEnd assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].marginStart assertEquals expectedMargin
    }

    @Test @Config(qualifiers = "w120dp")
    fun `ConstraintSet with 2 button, MIXED distribution, Fullwidth type and recommended distribution VERTICAL`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "ENVIAR A MI DOMICILIO"),
                AndesButton(context, buttonText = "RETIRAR POR UNA SUCURSAL")
            ),
            distribution = AndesButtonGroupDistribution.MIXED
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].topToTop assertEquals ConstraintSet.PARENT_ID

        buttonAsLayoutParam[SECOND_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].topToBottom assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].topMargin assertEquals marginLarge
    }

    @Test
    fun `ConstraintSet with 3 button, MIXED distribution, Fullwidth type and second and third recommended distribution HORIZONTAL`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "ENVIAR A MI DOMICILIO"),
                AndesButton(context, buttonText = "Button 2"),
                AndesButton(context, buttonText = "Button 3")
            ),
            AndesButtonGroupDistribution.MIXED
        )

        val button = andesButtonGroup.getAllChildren()

        val buttonAsLayoutParam = button.map { view ->
            view.layoutParams as ConstraintLayout.LayoutParams
        }

        buttonAsLayoutParam[FIRST_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[FIRST_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID

        buttonAsLayoutParam[SECOND_BUTTON].startToStart assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[SECOND_BUTTON].endToStart assertEquals button[THIRD_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].topToBottom assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[SECOND_BUTTON].topMargin assertEquals marginLarge
        buttonAsLayoutParam[SECOND_BUTTON].marginEnd assertEquals expectedMargin

        buttonAsLayoutParam[THIRD_BUTTON].startToEnd assertEquals button[SECOND_BUTTON].id
        buttonAsLayoutParam[THIRD_BUTTON].endToEnd assertEquals ConstraintSet.PARENT_ID
        buttonAsLayoutParam[THIRD_BUTTON].topToBottom assertEquals button[FIRST_BUTTON].id
        buttonAsLayoutParam[THIRD_BUTTON].topMargin assertEquals marginLarge
        buttonAsLayoutParam[THIRD_BUTTON].marginStart assertEquals expectedMargin
    }

    @Test(expected = IllegalStateException::class)
    fun `Cannot build AndesButtonGroup without AndesButton list`() {
        createAndesButtonGroup(emptyList())
    }

    @Test(expected = IllegalStateException::class)
    fun `Cannot be built AndesButtonGroup because it exceeds the number of buttons`() {
        createAndesButtonGroup(listOf(AndesButton(context), AndesButton(context), AndesButton(context), AndesButton(context)))
    }

    @Test
    fun `Validate that all buttons have the same size setting`() {
        val andesButtonGroup = createAndesButtonGroup(
            listOf(
                AndesButton(context, buttonText = "Button 1", buttonSize = AndesButtonSize.LARGE),
                AndesButton(context, buttonText = "Button 2", buttonSize = AndesButtonSize.MEDIUM),
                AndesButton(context, buttonText = "Button 3", buttonSize = AndesButtonSize.SMALL)
            )
        )

        andesButtonGroup.getAllChildren().map { button ->
            (button as AndesButton).size assertEquals AndesButtonSize.LARGE
        }
    }

    @Test
    fun `AndesButtonGroup with FullWidth type`() {
        val buttonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1")),
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.CENTER)
        )

        buttonGroup.type = AndesButtonGroupType.FullWidth

        buttonGroup.type assertEquals AndesButtonGroupType.FullWidth
    }

    @Test
    fun `AndesButtonGroup with Responsive type`() {
        val buttonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1")),
            type = AndesButtonGroupType.FullWidth
        )

        val responsiveType = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.CENTER)

        buttonGroup.type = responsiveType

        buttonGroup.type assertEquals responsiveType
    }

    @Test
    fun `AndesButtonGroup with Align LEFT`() {
        val buttonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1")),
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.CENTER)
        )

        buttonGroup.setAlign(AndesButtonGroupAlign.LEFT)

        buttonGroup.type.type.buttonGroupAlign assertEquals AndesButtonGroupAlign.LEFT
    }

    @Test
    fun `AndesButtonGroup with Align RIGHT`() {
        val buttonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1")),
            type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.CENTER)
        )

        buttonGroup.setAlign(AndesButtonGroupAlign.RIGHT)

        buttonGroup.type.type.buttonGroupAlign assertEquals AndesButtonGroupAlign.RIGHT
    }

    @Test
    fun `AndesButtonGroup with Align CENTER`() {
        val buttonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1"))
        )

        buttonGroup.setAlign(AndesButtonGroupAlign.CENTER)

        buttonGroup.type.type.buttonGroupAlign assertEquals AndesButtonGroupAlign.CENTER
    }

    @Test
    fun `AndesButtonGroup with distribution HORIZONTAL`() {
        val buttonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1")),
            distribution = AndesButtonGroupDistribution.VERTICAL
        )

        buttonGroup.distribution = AndesButtonGroupDistribution.HORIZONTAL

        buttonGroup.distribution assertEquals AndesButtonGroupDistribution.HORIZONTAL
    }

    @Test
    fun `AndesButtonGroup with distribution VERTICAL`() {
        val buttonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1")),
            distribution = AndesButtonGroupDistribution.HORIZONTAL
        )

        buttonGroup.distribution = AndesButtonGroupDistribution.VERTICAL

        buttonGroup.distribution assertEquals AndesButtonGroupDistribution.VERTICAL
    }

    @Test
    fun `AndesButtonGroup with distribution AUTO`() {
        val buttonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1")),
            distribution = AndesButtonGroupDistribution.HORIZONTAL
        )

        buttonGroup.distribution = AndesButtonGroupDistribution.AUTO

        buttonGroup.distribution assertEquals AndesButtonGroupDistribution.AUTO
    }

    @Test
    fun `AndesButtonGroup with distribution MIXED`() {
        val buttonGroup = createAndesButtonGroup(
            listOf(AndesButton(context, buttonText = "Button 1")),
            distribution = AndesButtonGroupDistribution.HORIZONTAL
        )

        buttonGroup.distribution = AndesButtonGroupDistribution.MIXED

        buttonGroup.distribution assertEquals AndesButtonGroupDistribution.MIXED
    }

    @Test
    fun `Check LARGE margin`() {
        val marginValue = AndesButtonGroupConfigurationFactory.getMargin(
            context,
            AndesButton(context, buttonText = "Button 1", buttonSize = AndesButtonSize.LARGE)
        )
        marginValue assertEquals marginLarge
    }

    @Test
    fun `Check MEDIUM margin`() {
        val expectedMargin = context.resources.getDimension(R.dimen.andes_button_group_margin_medium).toInt()
        val marginValue = AndesButtonGroupConfigurationFactory.getMargin(
            context,
            AndesButton(context, buttonText = "Button 1", buttonSize = AndesButtonSize.MEDIUM)
        )
        marginValue assertEquals expectedMargin
    }

    @Test
    fun `Check SMALL margin`() {
        val expectedMargin = context.resources.getDimension(R.dimen.andes_button_group_margin_small).toInt()
        val marginValue = AndesButtonGroupConfigurationFactory.getMargin(
            context,
            AndesButton(context, buttonText = "Button 1", buttonSize = AndesButtonSize.SMALL)
        )
        marginValue assertEquals expectedMargin
    }

    @Test
    fun `constructing by code with default values`() {
        val attrs = buildAttributeSet {}
        val buttonGroup = AndesButtonGroup(context, attrs)

        buttonGroup.distribution assertEquals AndesButtonGroupDistribution.HORIZONTAL
        buttonGroup.type assertEquals AndesButtonGroupType.FullWidth
    }

    @Test
    fun `constructing by code with VERTICAL distribution and Fullwidth type values`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesButtonGroupDistribution, DISTRIBUTION_VERTICAL)
            addAttribute(R.attr.andesButtonGroupAlign, ALIGN_LEFT)
            addAttribute(R.attr.andesButtonGroupType, TYPE_FULL_WIDTH)
        }

        val buttonGroup = AndesButtonGroup(context, attrs)

        buttonGroup.distribution assertEquals AndesButtonGroupDistribution.VERTICAL
        buttonGroup.type assertEquals AndesButtonGroupType.FullWidth
    }

    @Test
    fun `constructing by code with AUTO distribution, CENTER align and Responsive type values`() {
        val type = AndesButtonGroupType.Responsive(AndesButtonGroupAlign.CENTER)
        val buttonGroup = AndesButtonGroup(
            context,
            listOf(AndesButton(context)),
            type,
            AndesButtonGroupDistribution.AUTO
        )

        buttonGroup.distribution assertEquals AndesButtonGroupDistribution.AUTO
        buttonGroup.type.type.buttonGroupAlign assertEquals AndesButtonGroupAlign.CENTER
        buttonGroup.type assertEquals type
    }

    private fun createAndesButtonGroup(
        buttons: List<AndesButton>,
        distribution: AndesButtonGroupDistribution = AndesButtonGroupDistribution.HORIZONTAL,
        type: AndesButtonGroupType = AndesButtonGroupType.FullWidth
    ) : AndesButtonGroup {
        val andesButtonGroup = AndesButtonGroup(
            context,
            buttons,
            distribution = distribution,
            type = type
        )

        return andesButtonGroup
    }
}