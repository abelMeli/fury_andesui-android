package com.mercadolibre.android.andesui.button

import android.graphics.drawable.BitmapDrawable
import android.os.Looper.getMainLooper
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonIcon
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonIconOrientation
import com.mercadolibre.android.andesui.button.size.AndesButtonSize
import com.mercadolibre.android.andesui.buttonprogress.status.AndesButtonProgressAction
import com.mercadolibre.android.andesui.icons.IconProvider
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.getBackgroundDrawable
import com.mercadolibre.android.andesui.utils.getTextColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@LooperMode(LooperMode.Mode.PAUSED)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesButtonTest {
    private var context = RuntimeEnvironment.application
    private lateinit var andesButton: AndesButton

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Before
    fun setUp() {
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
                // other setters
                .setRequestListeners(requestListeners)
                .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

    @Test
    fun `Only context constructor`() {
        andesButton = AndesButton(context)
        val textParams = andesButton.textComponent.layoutParams as ConstraintLayout.LayoutParams

        assertEquals(andesButton.textComponent.textSize, 16F)
        assertEquals(textParams.goneStartMargin, 8)
        assertEquals(textParams.goneEndMargin, 8)
        assertEquals(andesButton.componentContainer.paddingRight, 16)
        assertEquals(andesButton.componentContainer.paddingLeft, 16)
        assertNull(andesButton.leftIconComponent.drawable)
        assertNull(andesButton.rightIconComponent.drawable)
        assertEquals(andesButton.isLoading, false)
        assertEquals(andesButton.progressStatus, AndesButtonProgressAction.IDLE)
    }

    @Test
    fun `Hierarchy and size constructor`() {
        andesButton = AndesButton(context, AndesButtonSize.MEDIUM, AndesButtonHierarchy.QUIET)
        val textParams = andesButton.textComponent.layoutParams as ConstraintLayout.LayoutParams

        assertEquals(andesButton.textComponent.textSize, 14F)
        assertEquals(textParams.goneStartMargin, 0)
        assertEquals(textParams.goneEndMargin, 0)
        assertEquals(andesButton.componentContainer.paddingRight, 12)
        assertEquals(andesButton.componentContainer.paddingLeft, 12)
        assertNull(andesButton.leftIconComponent.drawable)
        assertNull(andesButton.rightIconComponent.drawable)
        assertEquals(andesButton.isLoading, false)
        assertEquals(andesButton.progressStatus, AndesButtonProgressAction.IDLE)
    }

    @Test
    fun `Hierarchy, size and icon constructor`() {
        val icon = AndesButtonIcon(ANDES_ICON, AndesButtonIconOrientation.LEFT)
        andesButton = AndesButton(context, AndesButtonSize.LARGE, AndesButtonHierarchy.TRANSPARENT, icon)
        val textParams = andesButton.textComponent.layoutParams as ConstraintLayout.LayoutParams
        val leftIconParams = andesButton.leftIconComponent.layoutParams as ConstraintLayout.LayoutParams

        assertEquals(andesButton.textComponent.textSize, 16F)
        assertEquals(leftIconParams.marginStart, 0)
        assertEquals(textParams.marginStart, 12)
        assertEquals(textParams.goneEndMargin, 8)
        assertEquals(andesButton.componentContainer.paddingRight, 16)
        assertEquals(andesButton.componentContainer.paddingLeft, 16)
        assertThat(andesButton.leftIconComponent.drawable).isEqualToComparingOnlyGivenFields(icon)
        assertNull(andesButton.rightIconComponent.drawable)
        assertEquals(andesButton.isLoading, false)
        assertEquals(andesButton.progressStatus, AndesButtonProgressAction.IDLE)
    }

    @Test
    fun `Hierarchy, size and right icon constructor`() {
        val icon = AndesButtonIcon(ANDES_ICON, AndesButtonIconOrientation.RIGHT)
        andesButton = AndesButton(context, AndesButtonSize.LARGE, AndesButtonHierarchy.TRANSPARENT, icon)
        val textParams = andesButton.textComponent.layoutParams as ConstraintLayout.LayoutParams
        val rightIconParams = andesButton.rightIconComponent.layoutParams as ConstraintLayout.LayoutParams

        assertEquals(andesButton.textComponent.textSize, 16F)
        assertEquals(textParams.marginEnd, 12)
        assertEquals(rightIconParams.marginEnd, 0)
        assertEquals(textParams.goneStartMargin, 8)
        assertEquals(andesButton.componentContainer.paddingRight, 16)
        assertEquals(andesButton.componentContainer.paddingLeft, 16)
        assertNull(andesButton.leftIconComponent.drawable)
        assertThat(andesButton.rightIconComponent.drawable).isEqualToComparingOnlyGivenFields(icon)
        assertEquals(andesButton.isLoading, false)
        assertEquals(andesButton.progressStatus, AndesButtonProgressAction.IDLE)
    }

    @Test
    fun `Drawable left is visible after set`() {
        andesButton = AndesButton(context, AndesButtonSize.LARGE, AndesButtonHierarchy.TRANSPARENT, null)
        assertEquals(andesButton.leftIconComponent.drawable, null)
        val drawable = IconProvider(context)
            .loadIcon("andes_ui_placeholder_imagen_24") as BitmapDrawable
        andesButton.setIconDrawable(drawable, AndesButtonIconOrientation.LEFT)
        assertEquals(andesButton.leftIconComponent.visibility, View.VISIBLE)
    }

    @Test
    fun `Drawable right is visible after set`() {
        andesButton = AndesButton(context, AndesButtonSize.LARGE, AndesButtonHierarchy.TRANSPARENT, null)
        assertNull(andesButton.rightIconComponent.drawable)
        val drawable = IconProvider(context)
            .loadIcon("andes_ui_placeholder_imagen_24") as BitmapDrawable
        andesButton.setIconDrawable(drawable, AndesButtonIconOrientation.RIGHT)
        assertNotNull(andesButton.rightIconComponent.drawable)
    }

    @Test
    fun `Given button quiet without icon, when transitioning into loud, then final colors are correct`() {
        andesButton = AndesButton(context = context, buttonHierarchy = AndesButtonHierarchy.QUIET)
        val newHierarchy = AndesButtonHierarchy.LOUD

        andesButton.transitionIntoNewHierarchy(newHierarchy, 200)
        shadowOf(getMainLooper()).idle()

        andesButton.hierarchy assertEquals newHierarchy
        assertThat(andesButton.getBackgroundDrawable())
            .usingRecursiveComparison()
            .isEqualTo(
                newHierarchy.hierarchy.background(
                    context,
                    andesButton.size.size.cornerRadius(context)
                )
            )
        assertThat(andesButton.getTextColor())
            .usingRecursiveComparison()
            .isEqualTo(
                newHierarchy.hierarchy.textColor(context)
            )
    }

    @Test
    fun `Given button loud with right icon, when transitioning into transparent, then final colors are correct`() {
        andesButton = AndesButton(context = context, buttonHierarchy = AndesButtonHierarchy.LOUD, buttonSize = AndesButtonSize.LARGE)
        ResourcesCompat.getDrawable(context.resources, R.drawable.andes_control_y_accion_buscar_24, null)?.let {
            andesButton.setIconDrawable(it, AndesButtonIconOrientation.RIGHT)
        }
        val newHierarchy = AndesButtonHierarchy.TRANSPARENT

        andesButton.transitionIntoNewHierarchy(newHierarchy, 200)
        shadowOf(getMainLooper()).idle()

        andesButton.hierarchy assertEquals newHierarchy

        assertThat(andesButton.getBackgroundDrawable())
            .usingRecursiveComparison()
            .isEqualTo(
                newHierarchy.hierarchy.background(
                    context,
                    andesButton.size.size.cornerRadius(context)
                )
            )

        assertThat(andesButton.getTextColor())
            .usingRecursiveComparison()
            .isEqualTo(
                newHierarchy.hierarchy.textColor(context)
            )
    }

    @Test
    fun `Given button loud with left icon, when transitioning into transparent, then final colors are correct`() {
        andesButton = AndesButton(context = context, buttonHierarchy = AndesButtonHierarchy.LOUD, buttonSize = AndesButtonSize.LARGE)
        ResourcesCompat.getDrawable(context.resources, R.drawable.andes_control_y_accion_buscar_24, null)?.let {
            andesButton.setIconDrawable(it, AndesButtonIconOrientation.LEFT)
        }
        val newHierarchy = AndesButtonHierarchy.TRANSPARENT

        andesButton.transitionIntoNewHierarchy(newHierarchy, 200)
        shadowOf(getMainLooper()).idle()

        andesButton.hierarchy assertEquals newHierarchy

        assertThat(andesButton.getBackgroundDrawable())
            .usingRecursiveComparison()
            .isEqualTo(
                newHierarchy.hierarchy.background(
                    context,
                    andesButton.size.size.cornerRadius(context)
                )
            )

        assertThat(andesButton.getTextColor())
            .usingRecursiveComparison()
            .isEqualTo(
                newHierarchy.hierarchy.textColor(context)
            )
    }
}
