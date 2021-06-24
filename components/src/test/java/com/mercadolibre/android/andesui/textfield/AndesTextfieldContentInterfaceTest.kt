package com.mercadolibre.android.andesui.textfield
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.widget.TextView
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.button.size.AndesButtonSize
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.color.toColor
import com.mercadolibre.android.andesui.icons.IconProvider
import com.mercadolibre.android.andesui.textfield.content.AndesIconTextfieldContent
import com.mercadolibre.android.andesui.textfield.content.AndesPrefixTextfieldContent
import com.mercadolibre.android.andesui.textfield.content.AndesSuffixTextfieldContent
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldContentInterface
import com.mercadolibre.android.andesui.textfield.content.AndesClearTextfieldContent
import com.mercadolibre.android.andesui.textfield.content.AndesValidatedTextfieldContent
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.utils.buildColoredAndesBitmapDrawable
import junit.framework.Assert.assertEquals
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesTextfieldContentInterfaceTest {
    private var context = RuntimeEnvironment.application
    private lateinit var contentInterface: AndesTextfieldContentInterface

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
    fun `suffix content`() {
        contentInterface = AndesSuffixTextfieldContent
        val suffix = TextView(context)
        suffix.setTextColor(R.color.andes_text_color_secondary.toColor(context))
        suffix.text = context.getString(R.string.andes_suffix_hint)

        assertEquals(
                context.resources.getDimension(R.dimen.andes_textfield_suffix_left_margin).toInt(),
                contentInterface.leftMargin(context, AndesTextfieldState.IDLE.state)
        )
        assertEquals(context.resources.getDimension(R.dimen.andes_textfield_suffix_right_margin).toInt(), contentInterface.rightMargin(context))
        assertThat(contentInterface.component(context)).isEqualToComparingOnlyGivenFields(suffix)
    }

    @Test
    fun `prefix content`() {
        contentInterface = AndesPrefixTextfieldContent
        val prefix = TextView(context)
        prefix.setTextColor(R.color.andes_text_color_secondary.toColor(context))
        prefix.text = context.getString(R.string.andes_prefix_hint)

        assertEquals(context.resources.getDimension(
                R.dimen.andes_textfield_prefix_left_margin).toInt(),
                contentInterface.leftMargin(context, AndesTextfieldState.IDLE.state)
        )
        assertEquals(context.resources.getDimension(R.dimen.andes_textfield_prefix_right_margin).toInt(), contentInterface.rightMargin(context))
        assertThat(contentInterface.component(context)).isEqualToComparingOnlyGivenFields(prefix)
    }

    @Test
    fun `icon content`() {
        contentInterface = AndesIconTextfieldContent
        val icon = SimpleDraweeView(context)
        icon.setImageDrawable(buildColoredAndesBitmapDrawable(
                IconProvider(context).loadIcon("andes_ui_placeholder_imagen_24") as BitmapDrawable,
                context,
                color = R.color.andes_gray_900.toAndesColor()))

        assertEquals(context.resources.getDimension(R.dimen.andes_textfield_icon_left_margin).toInt(), contentInterface.rightMargin(context))
        assertEquals(context.resources.getDimension(R.dimen.andes_textfield_icon_right_margin).toInt(),
                contentInterface.leftMargin(context, AndesTextfieldState.IDLE.state))
        assertThat(contentInterface.component(context)).isEqualToComparingOnlyGivenFields(icon)
    }

    @Test
    fun `validated content`() {
        contentInterface = AndesValidatedTextfieldContent
        val validated = SimpleDraweeView(context)
        validated.setImageDrawable(buildColoredAndesBitmapDrawable(
                IconProvider(context).loadIcon("andes_ui_feedback_success_24") as BitmapDrawable,
                context,
                color = R.color.andes_green_500.toAndesColor())
        )

        assertEquals(context.resources.getDimension(R.dimen.andes_textfield_validated_left_margin).toInt(), contentInterface.rightMargin(context))
        assertEquals(context.resources.getDimension(R.dimen.andes_textfield_validated_right_margin).toInt(),
                contentInterface.leftMargin(context, AndesTextfieldState.IDLE.state))
        assertThat(contentInterface.component(context)).isEqualToComparingOnlyGivenFields(validated)
    }

    @Test
    fun `clear content`() {
        contentInterface = AndesClearTextfieldContent
        val clear = SimpleDraweeView(context)
        clear.setImageDrawable(buildColoredAndesBitmapDrawable(
                IconProvider(context).loadIcon("andes_ui_close_24") as BitmapDrawable,
                context,
                color = R.color.andes_gray_550.toAndesColor())
        )

        assertEquals(context.resources.getDimension(R.dimen.andes_textfield_clear_left_margin).toInt(), contentInterface.rightMargin(context))
        assertEquals(context.resources.getDimension(R.dimen.andes_textfield_clear_right_margin).toInt(),
                contentInterface.leftMargin(context, AndesTextfieldState.IDLE.state))
        assertThat(contentInterface.component(context)).isEqualToComparingOnlyGivenFields(clear)
    }

    @Test
    fun `action content`() {
        contentInterface = AndesClearTextfieldContent
        val action = AndesButton(context, AndesButtonSize.MEDIUM, AndesButtonHierarchy.TRANSPARENT)

        assertEquals(context.resources.getDimension(R.dimen.andes_textfield_action_left_margin).toInt(), contentInterface.rightMargin(context))
        assertEquals(context.resources.getDimension(R.dimen.andes_textfield_action_right_margin).toInt(),
                contentInterface.leftMargin(context, AndesTextfieldState.IDLE.state))
        assertThat(contentInterface.component(context)).isEqualToComparingOnlyGivenFields(action)
    }
}
