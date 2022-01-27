package com.mercadolibre.android.andesui.carousel

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.carousel.factory.AndesCarouselAttrs
import com.mercadolibre.android.andesui.carousel.factory.AndesCarouselConfigurationFactory
import com.mercadolibre.android.andesui.carousel.margin.AndesCarouselMargin
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselDelegate
import com.nhaarman.mockitokotlin2.verify
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
    fun `test padding default`() {
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
    fun `test padding small`() {
        attrs = AndesCarouselAttrs(
            CENTER,
            AndesCarouselMargin.SMALL
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.margin, context.resources.getDimension(R.dimen.andes_carousel_padding_small).toInt())
        Assert.assertEquals(config.padding, context.resources.getDimension(R.dimen.andes_carousel_recylcer_padding_small).toInt())
    }

    @Test
    fun `test padding medium`() {
        attrs = AndesCarouselAttrs(
            CENTER,
            AndesCarouselMargin.MEDIUM
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.margin, context.resources.getDimension(R.dimen.andes_carousel_padding_medium).toInt())
        Assert.assertEquals(config.padding, context.resources.getDimension(R.dimen.andes_carousel_recylcer_padding_medium).toInt())
    }

    @Test
    fun `test padding large`() {
        attrs = AndesCarouselAttrs(
            CENTER,
            AndesCarouselMargin.LARGE
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.margin, context.resources.getDimension(R.dimen.andes_carousel_padding_large).toInt())
        Assert.assertEquals(config.padding, context.resources.getDimension(R.dimen.andes_carousel_recylcer_padding_large).toInt())
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

    @Test
    fun `test scroll carousel`() {
        val getDataSetFree = listOf("test", "test")

        var cardPositionDisplayed = 0
        val andesCarouselDelegate = object : AndesCarouselDelegate {
            override fun bind(andesCarouselView: AndesCarousel, view: View, position: Int) {
                view.findViewById<TextView>(R.id.andes_badge_text).text = getDataSetFree[position]
            }

            override fun onClickItem(andesCarouselView: AndesCarousel, position: Int) {
                // Empty method
            }

            override fun getDataSetSize(andesCarouselView: AndesCarousel) = getDataSetFree.size

            override fun getLayoutItem(andesCarouselView: AndesCarousel) = R.layout.andes_layout_badge_pill
        }
        val scroll = spy(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val position = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
                if (position != -1) {
                    cardPositionDisplayed = position
                }
            }
        })

        val carousel = spy(AndesCarousel(context, true, AndesCarouselMargin.NONE))
        carousel.delegate = andesCarouselDelegate
        carousel.addOnScrollListener(scroll)

        carousel.scrollToPosition(1)

        verify(carousel).scrollToPosition(1)
    }

    companion object {
        private const val CENTER = true
        private const val FREE = false
        private const val SIZE = 0
        private const val LAYOUT = 0
    }
}
