package com.mercadolibre.android.andesui.thumbnail

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import com.mercadolibre.android.andesui.badge.AndesBadgeDot
import com.mercadolibre.android.andesui.badge.AndesBadgeIconPill
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgeDotSize
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgePillSize
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesTextThumbnailBadgeType
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.shape.AndesThumbnailShape
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
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
        with(getThumbnailBadgeThumb()) {
            this.image assertEquals drawable
            size assertEquals AndesThumbnailSize.SIZE_64
            assetType assertEquals AndesThumbnailAssetType.Icon(drawable)
            thumbnailShape assertEquals AndesThumbnailShape.Circle
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT

            val captor = argumentCaptor<ColorStateList>()
            verify(drawable, times(TINT_DEFAULT_INVOCATION_TIMES)).setTintList(captor.capture())
            captor.lastValue.defaultColor assertEquals AndesBadgeType.HIGHLIGHT.type.primaryColor()
               .colorInt(context)
        }

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
        andesThumbnailBadge.thumbnailType assertEquals AndesThumbnailBadgeType.Icon
        with(getThumbnailBadgeThumb()) {
            this.image assertEquals drawable
            size assertEquals AndesThumbnailSize.SIZE_32
            assetType assertEquals AndesThumbnailAssetType.Icon(drawable)
            thumbnailShape  assertEquals AndesThumbnailShape.Circle
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT

            val captor = argumentCaptor<ColorStateList>()
            verify(drawable, times(TINT_DEFAULT_INVOCATION_TIMES + 1)).setTintList(captor.capture())
            captor.lastValue.defaultColor assertEquals expectedColor
        }

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
        andesThumbnailBadge.thumbnailType assertEquals AndesThumbnailBadgeType.ImageCircle
        with(getThumbnailBadgeThumb()) {
            this.image assertEquals drawable
            size assertEquals AndesThumbnailSize.SIZE_64
            assetType assertEquals AndesThumbnailAssetType.Image(drawable)
            thumbnailShape assertEquals AndesThumbnailShape.Circle
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT

            val captor = argumentCaptor<ColorStateList>()
            // Four times because the drawable is re-tinted after a type change.
            verify(drawable, times(TINT_DEFAULT_INVOCATION_TIMES + 1)).setTintList(captor.capture())
            captor.lastValue assertIsNull true
        }

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
        andesThumbnailBadge.thumbnailType assertEquals AndesThumbnailBadgeType.Icon
        with(getThumbnailBadgeThumb()) {
            this.image assertEquals drawable
            size assertEquals AndesThumbnailSize.SIZE_64
            assetType assertEquals AndesThumbnailAssetType.Icon(drawable)
            thumbnailShape assertEquals AndesThumbnailShape.Circle
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT

            val captor = argumentCaptor<ColorStateList>()
            verify(drawable, times(TINT_DEFAULT_INVOCATION_TIMES)).setTintList(captor.capture())
            captor.lastValue.defaultColor assertEquals AndesBadgeType.HIGHLIGHT.type.primaryColor()
                .colorInt(context)
        }

        with(getThumbnailBadgeComponentView() as AndesBadgeIconPill) {
            type assertEquals AndesBadgeIconType.HIGHLIGHT
            size assertEquals AndesBadgePillSize.LARGE
        }
    }

    @Test
    fun `AndesThumbnailBadge using text constructor`() {
        // GIVEN
        val badge = AndesThumbnailBadgeComponent.IconPill(AndesBadgeIconType.HIGHLIGHT)
        andesThumbnailBadge = AndesThumbnailBadge(
            context = context,
            badgeComponent = badge,
            thumbnailType = AndesThumbnailBadgeType.Text,
            text = "AB"
        )

        // THEN
        andesThumbnailBadge assertIsNull false
        andesThumbnailBadge.thumbnailType assertEquals AndesThumbnailBadgeType.Text
        with(getThumbnailBadgeThumb()) {

            size assertEquals AndesThumbnailSize.SIZE_64
            assetType assertEquals AndesThumbnailAssetType.Text("AB")
            thumbnailShape assertEquals AndesThumbnailShape.Circle
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT
        }

        with(getThumbnailBadgeComponentView() as AndesBadgeIconPill) {
            type assertEquals AndesBadgeIconType.HIGHLIGHT
            size assertEquals AndesBadgePillSize.LARGE
        }
    }

    @Test
    fun `AndesThumbnailBadge type text`() {
        // GIVEN
        val badge = AndesThumbnailBadgeComponent.IconPill(AndesBadgeIconType.HIGHLIGHT)
        andesThumbnailBadge = AndesThumbnailBadge(
            context = context,
            badgeComponent = badge,
            thumbnailType = AndesThumbnailBadgeType.Text,
            text = "AB"
        )

        // WHEN
        andesThumbnailBadge.badgeComponent =
            AndesThumbnailBadgeComponent.IconPill(
                AndesBadgeIconType.WARNING,
                AndesThumbnailBadgePillSize.SIZE_80
            )

        // THEN
        andesThumbnailBadge assertIsNull false
        andesThumbnailBadge.thumbnailType assertEquals AndesThumbnailBadgeType.Text
        with(getThumbnailBadgeThumb()) {
            type assertEquals AndesTextThumbnailBadgeType.getThumbnailType()
            size assertEquals AndesThumbnailSize.SIZE_80
            assetType assertEquals AndesThumbnailAssetType.Text("AB")
            thumbnailShape assertEquals AndesThumbnailShape.Circle
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT

        }

        with(getThumbnailBadgeComponentView() as AndesBadgeIconPill) {
            type assertEquals AndesBadgeIconType.WARNING
            size assertEquals AndesBadgePillSize.LARGE
        }
    }

    @Test
    fun `AndesThumbnailBadge type text badge DOT`() {
        // GIVEN
        val badge = AndesThumbnailBadgeComponent.Dot(AndesBadgeIconType.HIGHLIGHT)
        andesThumbnailBadge = AndesThumbnailBadge(
            context = context,
            badgeComponent = badge,
            thumbnailType = AndesThumbnailBadgeType.Text,
            text = "AB"
        )

        // WHEN
        andesThumbnailBadge.text = "CD"
        andesThumbnailBadge.badgeComponent =
            AndesThumbnailBadgeComponent.Dot(
                AndesBadgeIconType.WARNING,
                AndesThumbnailBadgeDotSize.SIZE_32
            )

        // THEN
        andesThumbnailBadge assertIsNull false
        andesThumbnailBadge.thumbnailType assertEquals AndesThumbnailBadgeType.Text
        with(getThumbnailBadgeThumb()) {
            type assertEquals AndesTextThumbnailBadgeType.getThumbnailType()
            size assertEquals AndesThumbnailSize.SIZE_32
            assetType assertEquals AndesThumbnailAssetType.Text(andesThumbnailBadge.text)
            thumbnailShape assertEquals AndesThumbnailShape.Circle
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT

        }

        with(getThumbnailBadgeComponentView() as AndesBadgeDot) {
            type assertEquals AndesBadgeType.WARNING
        }
    }

    @Test
    fun `AndesThumbnailBadge image circle setting text badge DOT`() {
        // GIVEN
        val badge = AndesThumbnailBadgeComponent.Dot(AndesBadgeIconType.HIGHLIGHT)
        val text = "AB"
        andesThumbnailBadge = AndesThumbnailBadge(
            context = context,
            image = ContextCompat.getDrawable(context, R.drawable.andes_navegacion_compras_24)!!,
            badgeComponent = badge,
            thumbnailType = AndesThumbnailBadgeType.ImageCircle,

            )

        // WHEN
        //setting a value to text attribute
        andesThumbnailBadge.text = text

        // THEN
        // change the type of the thumbnailBadge to text type
        andesThumbnailBadge.thumbnailType assertEquals AndesThumbnailBadgeType.Text
        with(getThumbnailBadgeThumb()) {
            size assertEquals AndesThumbnailSize.SIZE_24
            assetType assertEquals AndesThumbnailAssetType.Text(text)
            thumbnailShape assertEquals AndesThumbnailShape.Circle
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT

        }

        with(getThumbnailBadgeComponentView() as AndesBadgeDot) {
            type assertEquals AndesBadgeType.HIGHLIGHT
        }
    }

    @Test
    fun `AndesThumbnailBadge text setting image badge DOT`() {
        // GIVEN
        val badge = AndesThumbnailBadgeComponent.Dot(AndesBadgeIconType.HIGHLIGHT)
        val drawable = spy(ContextCompat.getDrawable(context, R.drawable.andes_envio_envio_24)!!)
        andesThumbnailBadge = AndesThumbnailBadge(
            context = context,
            text = "AB",
            badgeComponent = badge,
            thumbnailType = AndesThumbnailBadgeType.Text,

            )

        // WHEN
        //setting a value to image attribute
        andesThumbnailBadge.image = drawable

        // THEN
        // change the type of the thumbnailBadge to Icon by default
        andesThumbnailBadge.thumbnailType assertEquals AndesThumbnailBadgeType.Icon
        with(getThumbnailBadgeThumb()) {
            size assertEquals AndesThumbnailSize.SIZE_24
            assetType assertEquals AndesThumbnailAssetType.Icon(drawable)
            thumbnailShape assertEquals AndesThumbnailShape.Circle
            hierarchy assertEquals AndesThumbnailHierarchy.DEFAULT

        }

        with(getThumbnailBadgeComponentView() as AndesBadgeDot) {
            type assertEquals AndesBadgeType.HIGHLIGHT
        }
    }

    private fun getThumbnailBadgeThumb(): AndesThumbnail =
        andesThumbnailBadge.getChildAt(0) as AndesThumbnail

    private fun getThumbnailBadgeComponentView(): View =
        andesThumbnailBadge.getChildAt(2)

    companion object {
        /**
         * Default number of times that tint thumbnail drawable is called in ThumbnailBadge. This behavior
         * happens because [AndesThumbnail] tints drawable to black in Icon image types.
         */
        private const val TINT_DEFAULT_INVOCATION_TIMES: Int = 2
    }
}
