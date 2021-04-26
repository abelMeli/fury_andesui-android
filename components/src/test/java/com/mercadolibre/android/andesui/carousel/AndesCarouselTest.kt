package com.mercadolibre.android.andesui.carousel

import android.os.Build
import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.carousel.factory.AndesCarouselAttrs
import com.mercadolibre.android.andesui.carousel.factory.AndesCarouselConfigurationFactory
import com.mercadolibre.android.andesui.carousel.margin.AndesCarouselMargin
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselDelegate
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesCarouselTest {

    private var context = RuntimeEnvironment.application
    private val configFactory = spy(AndesCarouselConfigurationFactory)
    private lateinit var attrs: AndesCarouselAttrs

    @Test
    fun `test padding none`() {
        attrs = AndesCarouselAttrs(
            CENTER,
            AndesCarouselMargin.NONE
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.margin, context.resources.getDimension(R.dimen.andes_carousel_padding_none).toInt())
    }

    @Test
    fun `test padding small`() {
        attrs = AndesCarouselAttrs(
                CENTER,
                AndesCarouselMargin.DEFAULT
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.margin, context.resources.getDimension(R.dimen.andes_carousel_padding_small).toInt())
    }

    @Test
    fun `test padding Xsmall`() {
        attrs = AndesCarouselAttrs(
                CENTER,
                AndesCarouselMargin.XSMALL
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.margin, context.resources.getDimension(R.dimen.andes_carousel_padding_xsmall).toInt())
        Assert.assertEquals(config.padding, context.resources.getDimension(R.dimen.andes_carousel_recylcer_padding_xsmall).toInt())
    }

    @Test
    fun `test center true`() {
        attrs = AndesCarouselAttrs(
                CENTER,
                AndesCarouselMargin.NONE
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.center, true)
    }

    @Test
    fun `test center false`() {
        attrs = AndesCarouselAttrs(
                FREE,
                AndesCarouselMargin.NONE
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.center, false)
    }

    @Test
    fun `test delegate`() {
        val andesCarouselDelegate = object : AndesCarouselDelegate {
            override fun bind(andesCarouselView: AndesCarousel, view: View, position: Int) {
                // Empty method
            }

            override fun onClickItem(andesCarouselView: AndesCarousel, position: Int) {
                // Empty method
            }

            override fun getDataSetSize(andesCarouselView: AndesCarousel) = SIZE

            override fun getLayoutItem(andesCarouselView: AndesCarousel) = LAYOUT
        }
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE)
        carousel.delegate = andesCarouselDelegate
        Assert.assertNotNull(carousel.delegate)

        Assert.assertEquals(SIZE, andesCarouselDelegate.getDataSetSize(carousel))
        Assert.assertEquals(LAYOUT, andesCarouselDelegate.getLayoutItem(carousel))
    }

    @Test
    fun `test carousel with different padding`() {
        var carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE)
        Assert.assertEquals(carousel.margin, AndesCarouselMargin.NONE)

        carousel = AndesCarousel(context, true, AndesCarouselMargin.DEFAULT)
        Assert.assertEquals(carousel.margin, AndesCarouselMargin.DEFAULT)
    }

    companion object {
        private const val CENTER = true
        private const val FREE = false
        private const val SIZE = 0
        private const val LAYOUT = 0
    }
}
