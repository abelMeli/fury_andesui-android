package com.mercadolibre.android.andesui.thumbnail

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.assertIsNull
import com.mercadolibre.android.andesui.badge.AndesBadgeDot
import com.mercadolibre.android.andesui.badge.AndesBadgeIconPill
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgeDotSize
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesThumbnailBadgeTest {

    private lateinit var context: Context
    private lateinit var andesThumbnailBadge: AndesThumbnailBadge

    companion object {
        /**
         * Default number of times that tint thumbnail drawable is called in ThumbnailBadge. This behavior
         * happens because [AndesThumbnail] tints drawable to black in Icon image types.
         */
        private const val TINT_DEFAULT_INVOCATION_TIMES: Int = 2
    }

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `AndesThumbnailBadge min args built`() {
        // GIVEN
        val drawable = spy(ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!)
        val badge = AndesThumbnailBadgeComponent.IconPill(AndesBadgeIconType.HIGHLIGHT)

        // WHEN
        andesThumbnailBadge = AndesThumbnailBadge(
            context = context,
            image = drawable,
            badgeComponent = badge
        )

        // THEN
        andesThumbnailBadge assertIsNull false
        with(getThumbnailBadgeThumb(), {
            this.image assertEquals drawable
            size assertEquals AndesThumbnailSize.SIZE_64
            type assertEquals AndesThumbnailType.ICON
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT

            val captor = argumentCaptor<ColorStateList>()
            verify(drawable, times(TINT_DEFAULT_INVOCATION_TIMES)).setTintList(captor.capture())
            captor.lastValue.defaultColor assertEquals AndesBadgeType.HIGHLIGHT.type.primaryColor()
                .colorInt(context)
        })

        with(getThumbnailBadgeComponentView() as AndesBadgeIconPill) {
            type assertEquals AndesBadgeIconType.HIGHLIGHT
            size assertEquals AndesBadgePillSize.LARGE
        }
    }

    @Test
    fun `AndesThumbnailBadge min args, set badge DOT`() {
        // GIVEN
        val drawable = spy(ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!)
        val badge = AndesThumbnailBadgeComponent.IconPill(AndesBadgeIconType.HIGHLIGHT)
        andesThumbnailBadge = AndesThumbnailBadge(
            context = context,
            image = drawable,
            badgeComponent = badge
        )

        // WHEN
        andesThumbnailBadge.badgeComponent = AndesThumbnailBadgeComponent.Dot(
            color = AndesBadgeIconType.SUCCESS,
            thumbnailSize = AndesThumbnailBadgeDotSize.SIZE_32
        )

        // THEN
        val expectedColor = AndesBadgeType.SUCCESS.type.primaryColor().colorInt(context)
        andesThumbnailBadge assertIsNull false
        with(getThumbnailBadgeThumb(), {
            this.image assertEquals drawable
            size assertEquals AndesThumbnailSize.SIZE_32
            type assertEquals AndesThumbnailType.ICON
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT

            val captor = argumentCaptor<ColorStateList>()
            verify(drawable, times(TINT_DEFAULT_INVOCATION_TIMES + 1)).setTintList(captor.capture())
            captor.lastValue.defaultColor assertEquals expectedColor
        })

        with(getThumbnailBadgeComponentView() as AndesBadgeDot) {
            type assertEquals AndesBadgeType.SUCCESS
        }
    }

    @Test
    fun `AndesThumbnailBadge min args, set thumbnail ImageCircle`() {
        // GIVEN
        val drawable = spy(ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!)
        val badge = AndesThumbnailBadgeComponent.IconPill(AndesBadgeIconType.HIGHLIGHT)
        andesThumbnailBadge = AndesThumbnailBadge(
            context = context,
            image = drawable,
            badgeComponent = badge
        )

        // WHEN
        andesThumbnailBadge.thumbnailType = AndesThumbnailBadgeType.ImageCircle

        // THEN
        andesThumbnailBadge assertIsNull false
        with(getThumbnailBadgeThumb(), {
            this.image assertEquals drawable
            size assertEquals AndesThumbnailSize.SIZE_64
            type assertEquals AndesThumbnailType.IMAGE_CIRCLE
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT

            val captor = argumentCaptor<ColorStateList>()
            // Four times because the drawable is re-tinted after a type change.
            verify(drawable, times(TINT_DEFAULT_INVOCATION_TIMES + 2)).setTintList(captor.capture())
            captor.lastValue assertIsNull true
        })

        with(getThumbnailBadgeComponentView() as AndesBadgeIconPill) {
            type assertEquals AndesBadgeIconType.HIGHLIGHT
            size assertEquals AndesBadgePillSize.LARGE
        }
    }

    @Test
    fun `AndesThumbnailBadge min args, set image`() {
        // GIVEN
        val badge = AndesThumbnailBadgeComponent.IconPill(AndesBadgeIconType.HIGHLIGHT)
        andesThumbnailBadge = AndesThumbnailBadge(
            context = context,
            image = ContextCompat.getDrawable(context, R.drawable.andes_navegacion_compras_24)!!,
            badgeComponent = badge
        )

        // WHEN
        val drawable = spy(ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!)
        andesThumbnailBadge.image = drawable

        // THEN
        andesThumbnailBadge assertIsNull false
        with(getThumbnailBadgeThumb(), {
            this.image assertEquals drawable
            size assertEquals AndesThumbnailSize.SIZE_64
            type assertEquals AndesThumbnailType.ICON
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT

            val captor = argumentCaptor<ColorStateList>()
            verify(drawable, times(TINT_DEFAULT_INVOCATION_TIMES)).setTintList(captor.capture())
            captor.lastValue.defaultColor assertEquals AndesBadgeType.HIGHLIGHT.type.primaryColor()
                .colorInt(context)
        })

        with(getThumbnailBadgeComponentView() as AndesBadgeIconPill) {
            type assertEquals AndesBadgeIconType.HIGHLIGHT
            size assertEquals AndesBadgePillSize.LARGE
        }
    }

    private fun getThumbnailBadgeThumb(): AndesThumbnail =
        andesThumbnailBadge.getChildAt(0) as AndesThumbnail

    private fun getThumbnailBadgeComponentView(): View =
        andesThumbnailBadge.getChildAt(2)
}
