package com.mercadolibre.android.andesui.linearprogress

import android.os.Build
import com.mercadolibre.android.andesui.linearprogress.size.AndesLargeLinearProgressSize
import com.mercadolibre.android.andesui.linearprogress.size.AndesSmallLinearProgressSize
import com.nhaarman.mockitokotlin2.spy
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesLinearProgressSizeTest : TestCase() {

    private var context = RuntimeEnvironment.application
    private var andesLargeLinearProgressSize = spy(AndesLargeLinearProgressSize())
    private var andesSmallLinearProgressSize = spy(AndesSmallLinearProgressSize())

    @Test
    fun `Large linear progress height`() {
        Assert.assertEquals(andesLargeLinearProgressSize.height(context), 8F)
    }

    @Test
    fun `Small linear progress height`() {
        Assert.assertEquals(andesSmallLinearProgressSize.height(context), 4F)
    }

    @Test
    fun `Large linear progress splitSize`() {
        Assert.assertEquals(andesLargeLinearProgressSize.splitSize(context), 8F)
    }

    @Test
    fun `Small linear progress splitSize`() {
        Assert.assertEquals(andesSmallLinearProgressSize.splitSize(context), 4F)
    }

    @Test
    fun `Large linear progress cornerRadius`() {
        Assert.assertEquals(andesLargeLinearProgressSize.cornerRadius(context), 4F)
    }

    @Test
    fun `Small linear progress cornerRadius`() {
        Assert.assertEquals(andesSmallLinearProgressSize.cornerRadius(context), 2F)
    }
}
