package com.mercadolibre.android.andesui.message

import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.BuildConfig
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.bullet.AndesBullet
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.message.factory.AndesMessageAttrs
import com.mercadolibre.android.andesui.message.factory.AndesMessageAttrsParser
import com.mercadolibre.android.andesui.message.factory.AndesMessageConfigurationFactory
import com.mercadolibre.android.andesui.message.hierarchy.AndesMessageHierarchy
import com.mercadolibre.android.andesui.message.type.AndesMessageType
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import org.mockito.Mockito.spy
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesMessageTest {

    private var context = RuntimeEnvironment.application

    private lateinit var andesMessage: AndesMessage

    private lateinit var attrs: AndesMessageAttrs

    private val configFactory = spy(AndesMessageConfigurationFactory)

    private fun AndesMessage.getBulletContainer() = findViewById<LinearLayout>(R.id.andes_bullet_container)

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
    fun `Body Links`() {
        var indexSelected = -1

        andesMessage = AndesMessage(context, AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS,
                "This is a body message", "Title", true, null)

        val links = listOf(AndesBodyLink(0, 10))
        andesMessage.bodyLinks = AndesBodyLinks(links, listener = {
            indexSelected = it
        })

        (andesMessage.bodyComponent.text as? SpannableString)?.let {
            it.getSpans(0, 10, ClickableSpan::class.java)[0].onClick(andesMessage.bodyComponent)
        }

        assertEquals(indexSelected, 0)
    }

    @Test
    fun `Andes Message Without Thumbnail`() {
        andesMessage = AndesMessage(context, AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS,
            "This is a body message", "Title", true, null, null
        )

        assertEquals(andesMessage.thumbnail.visibility, View.GONE)
    }

    @Test
    fun `Andes Message With Thumbnail`() {
        andesMessage = AndesMessage(context, AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS,
            "This is a body message", "Title", true, null, Mockito.mock(Drawable::class.java)
        )

        assertEquals(andesMessage.thumbnail.visibility, View.VISIBLE)
    }

    @Test
    fun `AndesMessage with linkAction set with a primaryAction set does not work`() {
        ReflectionHelpers.setStaticField(BuildConfig::class.java, "DEBUG", false)
        val actionText = "action"
        val onClickListener = View.OnClickListener {}
        andesMessage = AndesMessage(context, AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS,
            "This is a body message", "Title", true, null, null
        )

        andesMessage.setupPrimaryAction(actionText, onClickListener)
        andesMessage.setupLinkAction(actionText, onClickListener)

        assertEquals(View.GONE, andesMessage.getLinkAction().visibility)
    }

    @Test
    fun `AndesMessage with linkAction set without primaryAction set works correctly`() {
        val actionText = "action"
        val onClickListener = View.OnClickListener {}
        andesMessage = AndesMessage(context, AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS,
            "This is a body message", "Title", true, null, null
        )

        andesMessage.setupLinkAction(actionText, onClickListener)

        assertEquals(View.VISIBLE, andesMessage.getLinkAction().visibility)
    }

    private fun AndesMessage.getLinkAction() = findViewById<TextView>(R.id.andes_link_action)

    @Test
    fun `AndesMessage with bullets`() {
        val bulletText = "Bullet multiline example with simple dummy text."
        val andesMessage = AndesMessage(
            context,
            AndesMessageHierarchy.LOUD,
            AndesMessageType.SUCCESS,
            "This is a body message",
            null,
            true,
            null,
            null
        )
        var indexSelected = -1
        val links = listOf(AndesBodyLink(0, 10))
        andesMessage.bullets = arrayListOf(
            AndesBullet(
                bulletText,
                AndesBodyLinks(
                    links,
                    listener = {
                        indexSelected = it
                    }
                )
            )
        )
        val bulletTextField = andesMessage.getBulletContainer().getChildAt(0) as AndesTextView
        bulletTextField.let {
            (it.text as SpannableString).getSpans(0, 10, ClickableSpan::class.java)[0].onClick(bulletTextField)
        }
        assertEquals(indexSelected, 0)
        assertEquals(View.VISIBLE, bulletTextField.visibility)
        assertEquals(bulletText, bulletTextField.text.toString())
    }

    @Test
    fun `AndesMessage without bullets`() {
        val andesMessage = AndesMessage(
            context,
            AndesMessageHierarchy.LOUD,
            AndesMessageType.SUCCESS,
            "This is a body message",
            null,
            true,
            null,
            null
        )

        andesMessage.bullets = null
        assertEquals(View.GONE, andesMessage.getBulletContainer().visibility)
    }

    @Test
    fun `AndesMessage check contentDescription dismiss button`() {
        andesMessage = AndesMessage(context, AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS,
            "This is a body message", "Title", true, null, null
        )
        val closeButton = andesMessage.findViewById<SimpleDraweeView>(R.id.andes_dismissable)
        closeButton.contentDescription assertEquals context.resources.getString(R.string.andes_message_dismiss_button_content_description)
    }

    @Test
    fun `AndesMessage with isHeadingEnable true`() {
        andesMessage = AndesMessage(context, AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS,
            "This is a body message", "Title", true, null, null, null, true
        )

        val title = andesMessage.findViewById<AndesTextView>(R.id.andes_title)
        ViewCompat.isAccessibilityHeading(title) assertEquals true
    }

    @Test
    fun `AndesMessage with isHeadingEnable false`() {
        andesMessage = AndesMessage(context, AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS,
            "This is a body message", "Title", true, null, null
        )
        andesMessage.a11yTitleIsHeader = false

        val title = andesMessage.findViewById<AndesTextView>(R.id.andes_title)
        ViewCompat.isAccessibilityHeading(title) assertEquals false
    }

    @Test
    fun `Message attrs`() {
        attrs = AndesMessageAttrs(
            AndesMessageHierarchy.LOUD,
            AndesMessageType.SUCCESS,
            body = "description",
            title = "AndesUI",
            isDismissable = true,
            bodyLinks = null,
            thumbnail = null,
            bullets = listOf(AndesBullet("Bullet 1", null)),
            true
        )

        val config = configFactory.create(context, attrs)
        config.a11yTitleIsHeader assertEquals true
        config.pipeColor assertEquals AndesMessageType.SUCCESS.type.pipeColor()
        config.bodyText assertEquals "description"
        config.titleText assertEquals "AndesUI"
        config.isDismissable assertEquals true
        config.thumbnail assertIsNull true
    }

    @Test
    fun `AndesMessage check a11yTitleIsHeader value have an xml attribute`() {
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesMessageA11yTitleIsHeader, "true")
            .build()

        val buttonGroupAttrs = AndesMessageAttrsParser.parse(context, attrs)
        buttonGroupAttrs.a11yTitleIsHeader assertEquals true
    }

    @Test
    fun `AndesMessage check bullet value have an xml attribute`() {
        val neutral = "2000"
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesMessageType, neutral)
            .build()

        val buttonGroupAttrs = AndesMessageAttrsParser.parse(context, attrs)
        buttonGroupAttrs.bullets assertIsNull true
    }

    @Test
    fun `AndesMessage check contentDescription for dismissableComponent`() {
        andesMessage = AndesMessage(context, AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS,
            "This is a body message", "Title", true, null, null
        )
        val dismissableComponent = andesMessage.findViewById<SimpleDraweeView>(R.id.andes_dismissable)
        dismissableComponent.contentDescription assertEquals context.resources.getString(R.string.andes_message_dismiss_button_content_description)
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }
}
