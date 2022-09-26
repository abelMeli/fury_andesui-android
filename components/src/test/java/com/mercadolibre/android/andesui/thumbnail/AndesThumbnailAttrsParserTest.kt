package com.mercadolibre.android.andesui.thumbnail

import android.content.Context
import android.widget.ImageView
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.factory.AndesThumbnailAttrs
import com.mercadolibre.android.andesui.thumbnail.factory.AndesThumbnailAttrsParser
import com.mercadolibre.android.andesui.thumbnail.hierarchy.AndesThumbnailHierarchy
import com.mercadolibre.android.andesui.thumbnail.shape.AndesThumbnailShape
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.state.AndesThumbnailState
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType
import com.mercadolibre.android.andesui.utils.Constants
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.buildAttributeSet
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
class AndesThumbnailAttrsParserTest {

    private lateinit var context: Context
    private lateinit var andesThumbnailAttrs: AndesThumbnailAttrs

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parsing with no custom values will throw exception`() {
        val attrs = buildAttributeSet {  }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)
    }

    @Test
    fun `hierarchy default, type icon, size 24, disabled, scale type center, color accent, image`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1000")
            addAttribute(R.attr.andesThumbnailType, "2000")
            addAttribute(R.attr.andesThumbnailSize, "3000")
            addAttribute(R.attr.andesThumbnailState, "4000")
            addAttribute(R.attr.andesThumbnailScaleType, "5000")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailImage, "@drawable/andes_test_drawable")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)

        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.CENTER
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.DEFAULT
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_24
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.DISABLED
        andesThumbnailAttrs.andesThumbnailType assertEquals AndesThumbnailType.ICON
    }

    @Test
    fun `hierarchy loud, type image circle, size 32, enabled, scale type center crop, color accent, image`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1001")
            addAttribute(R.attr.andesThumbnailType, "2001")
            addAttribute(R.attr.andesThumbnailSize, "3001")
            addAttribute(R.attr.andesThumbnailState, "4001")
            addAttribute(R.attr.andesThumbnailScaleType, "5001")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailImage, "@drawable/andes_test_drawable")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)

        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.CENTER_CROP
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.LOUD
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_32
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.ENABLED
        andesThumbnailAttrs.andesThumbnailType assertEquals AndesThumbnailType.IMAGE_CIRCLE
    }

    @Test
    fun `hierarchy quiet, type image square, size 40, enabled, scale type center inside, color accent, image`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1002")
            addAttribute(R.attr.andesThumbnailType, "2002")
            addAttribute(R.attr.andesThumbnailSize, "3002")
            addAttribute(R.attr.andesThumbnailState, "4001")
            addAttribute(R.attr.andesThumbnailScaleType, "5002")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailImage, "@drawable/andes_test_drawable")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)

        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.CENTER_INSIDE
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.QUIET
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_40
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.ENABLED
        andesThumbnailAttrs.andesThumbnailType assertEquals AndesThumbnailType.IMAGE_SQUARE
    }

    @Test
    fun `hierarchy quiet, type image square, size 48, enabled, scale type fit center, color accent, image`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1002")
            addAttribute(R.attr.andesThumbnailType, "2002")
            addAttribute(R.attr.andesThumbnailSize, "3003")
            addAttribute(R.attr.andesThumbnailState, "4001")
            addAttribute(R.attr.andesThumbnailScaleType, "5003")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailImage, "@drawable/andes_test_drawable")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)

        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.FIT_CENTER
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.QUIET
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_48
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.ENABLED
        andesThumbnailAttrs.andesThumbnailType assertEquals AndesThumbnailType.IMAGE_SQUARE
    }

    @Test
    fun `hierarchy quiet, type image square, size 56, enabled, scale type fit end, color accent, image`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1002")
            addAttribute(R.attr.andesThumbnailType, "2002")
            addAttribute(R.attr.andesThumbnailSize, "3004")
            addAttribute(R.attr.andesThumbnailState, "4001")
            addAttribute(R.attr.andesThumbnailScaleType, "5004")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailImage, "@drawable/andes_test_drawable")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)

        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.FIT_END
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.QUIET
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_56
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.ENABLED
        andesThumbnailAttrs.andesThumbnailType assertEquals AndesThumbnailType.IMAGE_SQUARE
    }

    @Test
    fun `hierarchy quiet, type image square, size 64, enabled, scale type fit start, color accent, image`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1002")
            addAttribute(R.attr.andesThumbnailType, "2002")
            addAttribute(R.attr.andesThumbnailSize, "3005")
            addAttribute(R.attr.andesThumbnailState, "4001")
            addAttribute(R.attr.andesThumbnailScaleType, "5005")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailImage, "@drawable/andes_test_drawable")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)

        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.FIT_START
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.QUIET
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_64
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.ENABLED
        andesThumbnailAttrs.andesThumbnailType assertEquals AndesThumbnailType.IMAGE_SQUARE
    }

    @Test
    fun `hierarchy quiet, type image square, size 72, enabled, scale type fit xy, color accent, image`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1002")
            addAttribute(R.attr.andesThumbnailType, "2002")
            addAttribute(R.attr.andesThumbnailSize, "3006")
            addAttribute(R.attr.andesThumbnailState, "4001")
            addAttribute(R.attr.andesThumbnailScaleType, "5006")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailImage, "@drawable/andes_test_drawable")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)

        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.FIT_XY
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.QUIET
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_72
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.ENABLED
        andesThumbnailAttrs.andesThumbnailType assertEquals AndesThumbnailType.IMAGE_SQUARE
    }

    @Test
    fun `hierarchy quiet, type image square, size 80, enabled, scale type matrix, color accent, image`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1002")
            addAttribute(R.attr.andesThumbnailType, "2002")
            addAttribute(R.attr.andesThumbnailSize, "3007")
            addAttribute(R.attr.andesThumbnailState, "4001")
            addAttribute(R.attr.andesThumbnailScaleType, "5007")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailImage, "@drawable/andes_test_drawable")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)

        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.MATRIX
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.QUIET
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_80
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.ENABLED
        andesThumbnailAttrs.andesThumbnailType assertEquals AndesThumbnailType.IMAGE_SQUARE
    }

    @Test
    fun `hierarchy loud, type text, size 80, shape circle,  enabled, scale type matrix, color accent, text`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1001")
            addAttribute(R.attr.andesThumbnailSize, "3007")
            addAttribute(R.attr.andesThumbnailAssetType, "6002")
            addAttribute(R.attr.andesThumbnailShape, "7000")
            addAttribute(R.attr.andesThumbnailState, "4001")
            addAttribute(R.attr.andesThumbnailScaleType, "5007")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailText, "AB")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)
        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.MATRIX
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.LOUD
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_80
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.ENABLED
        andesThumbnailAttrs.andesThumbnailAssetType assertEquals AndesThumbnailAssetType.Text("AB")
        andesThumbnailAttrs.andesThumbnailShape assertEquals AndesThumbnailShape.Circle
    }

    @Test
    fun `hierarchy default, type text, size 56, shape square, disabled,  color accent, text`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1000")
            addAttribute(R.attr.andesThumbnailSize, "3004")
            addAttribute(R.attr.andesThumbnailAssetType, "6002")
            addAttribute(R.attr.andesThumbnailShape, "7001")
            addAttribute(R.attr.andesThumbnailState, "4000")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailText, "AB")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)
        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.CENTER_CROP
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.DEFAULT
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_56
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.DISABLED
        andesThumbnailAttrs.andesThumbnailAssetType assertEquals AndesThumbnailAssetType.Text("AB")
        andesThumbnailAttrs.andesThumbnailShape assertEquals AndesThumbnailShape.Square
    }

    @Test
    fun `hierarchy default, type icon, size 64, shape square, enabled, color accent`() {
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1000")
            addAttribute(R.attr.andesThumbnailSize, "3005")
            addAttribute(R.attr.andesThumbnailAssetType, "6000")
            addAttribute(R.attr.andesThumbnailShape, "7001")
            addAttribute(R.attr.andesThumbnailState, "4001")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailImage, "@drawable/andes_test_drawable")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)
        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.CENTER_CROP
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.DEFAULT
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_64
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.ENABLED
        andesThumbnailAttrs.andesThumbnailShape assertEquals AndesThumbnailShape.Square
    }

    @Test
    fun `hierarchy quiet, type image, size 80, shape circle, enabled,  color accent`() {

        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1002")
            addAttribute(R.attr.andesThumbnailSize, "3007")
            addAttribute(R.attr.andesThumbnailAssetType, "6001")
            addAttribute(R.attr.andesThumbnailShape, "7000")
            addAttribute(R.attr.andesThumbnailState, "4001")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailImage, "@drawable/andes_test_drawable")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)
        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.CENTER_CROP
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.QUIET
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_80
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.ENABLED
        assertThat(andesThumbnailAttrs.andesThumbnailAssetType is AndesThumbnailAssetType.Image).isTrue
        andesThumbnailAttrs.andesThumbnailShape assertEquals AndesThumbnailShape.Circle
    }

    @Test
    fun `hierarchy quiet, without type and shape, size 24, shape circle  color accent`() {

        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesThumbnailHierarchy, "1002")
            addAttribute(R.attr.andesThumbnailSize, "3000")
            addAttribute(R.attr.andesThumbnailAccentColor, "@color/andes_test_color")
            addAttribute(R.attr.andesThumbnailImage, "@drawable/andes_test_drawable")
        }

        andesThumbnailAttrs = AndesThumbnailAttrsParser.parse(context, attrs)
        andesThumbnailAttrs.andesThumbnailScaleType assertEquals ImageView.ScaleType.CENTER_CROP
        andesThumbnailAttrs.andesThumbnailHierarchy assertEquals AndesThumbnailHierarchy.QUIET
        andesThumbnailAttrs.andesThumbnailSize assertEquals AndesThumbnailSize.SIZE_24
        andesThumbnailAttrs.andesThumbnailState assertEquals AndesThumbnailState.ENABLED
        assertThat(andesThumbnailAttrs.andesThumbnailAssetType is AndesThumbnailAssetType.Icon).isTrue
        andesThumbnailAttrs.andesThumbnailShape assertEquals AndesThumbnailShape.Circle
    }
}
