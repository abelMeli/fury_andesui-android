package com.mercadolibre.android.andesui.linearprogress

import android.os.Build
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.linearprogress.size.AndesLinearProgressSize
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesLinearProgressIndicatorDeterminateTest : TestCase() {
    private var context = RuntimeEnvironment.application
    private lateinit var andesLinearProgress: AndesLinearProgressIndicatorDeterminate

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Before
    fun `setup initial`() {
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
        andesLinearProgress = AndesLinearProgressIndicatorDeterminate(context)

        Assert.assertEquals(andesLinearProgress.size, AndesLinearProgressSize.SMALL)
        Assert.assertEquals(andesLinearProgress.indicatorTint, 0)
        Assert.assertEquals(andesLinearProgress.trackTint, 0)
        Assert.assertEquals(andesLinearProgress.isSplit, false)
        Assert.assertEquals(andesLinearProgress.numberOfSteps, 10)
    }

    @Test
    fun `attr and context constructor`() {
        andesLinearProgress = AndesLinearProgressIndicatorDeterminate(context, null)

        Assert.assertEquals(andesLinearProgress.size, AndesLinearProgressSize.SMALL)
        Assert.assertEquals(andesLinearProgress.indicatorTint, 0)
        Assert.assertEquals(andesLinearProgress.trackTint, 0)
        Assert.assertEquals(andesLinearProgress.isSplit, false)
        Assert.assertEquals(andesLinearProgress.numberOfSteps, 10)
    }

    @Test
    fun `Change size large`() {
        andesLinearProgress = AndesLinearProgressIndicatorDeterminate(context)
        andesLinearProgress.size = AndesLinearProgressSize.LARGE

        Assert.assertEquals(andesLinearProgress.layoutParams.height, 8)
    }

    @Test
    fun `Change size small`() {
        andesLinearProgress = AndesLinearProgressIndicatorDeterminate(context)
        andesLinearProgress.size = AndesLinearProgressSize.SMALL

        Assert.assertEquals(andesLinearProgress.layoutParams.height, 4)
    }

    @Test
    fun `Change number of steps no split`() {
        andesLinearProgress = AndesLinearProgressIndicatorDeterminate(context)
        andesLinearProgress.numberOfSteps = 2
        andesLinearProgress.isSplit = false

        Assert.assertEquals(andesLinearProgress.childCount, 2)
    }

    @Test
    fun `Change number of steps with split`() {
        andesLinearProgress = AndesLinearProgressIndicatorDeterminate(context)
        andesLinearProgress.numberOfSteps = 6
        andesLinearProgress.isSplit = true

        Assert.assertEquals(andesLinearProgress.childCount, 6)
    }

    @Test
    fun `Next step no split`() {
        andesLinearProgress = AndesLinearProgressIndicatorDeterminate(context)
        andesLinearProgress.nextStep()
        val indicatorOne = andesLinearProgress.findViewById<SimpleDraweeView>(1)
        val indicatorTwo = andesLinearProgress.findViewById<SimpleDraweeView>(2)

        Assert.assertNotNull(indicatorOne.background)
        Assert.assertNull(indicatorTwo.background)
    }

    @Test
    fun `previous step no split`() {
        andesLinearProgress = AndesLinearProgressIndicatorDeterminate(context)
        andesLinearProgress.jumpToStep(3)
        andesLinearProgress.jumpToStep(2)
        val indicatorTwo = andesLinearProgress.findViewById<SimpleDraweeView>(2)
        val indicatorThree = andesLinearProgress.findViewById<SimpleDraweeView>(3)

        Assert.assertNotNull(indicatorTwo.background)
        Assert.assertNull(indicatorThree.background)
    }
}
