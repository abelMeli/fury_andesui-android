package com.mercadolibre.android.andesui.message

import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.BuildConfig
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.bullet.AndesBullet
import com.mercadolibre.android.andesui.bullet.AndesBulletSpannable
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.message.hierarchy.AndesMessageHierarchy
import com.mercadolibre.android.andesui.message.type.AndesMessageType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesMessageTest {

    private var context = RuntimeEnvironment.application

    private lateinit var andesMessage: AndesMessage

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
        andesMessage.bullets = arrayListOf(AndesBullet(1, 2))

        with(andesMessage.getBody()) {
            text.toString() assertEquals "T\n\nhis is a body message"
            visibility assertEquals android.view.View.VISIBLE
            val spans = (text as SpannedString).getSpans(
                0,
                "This is a body message".lastIndex,
                AndesBulletSpannable::class.java
            )
            spans.size assertEquals 1
        }
    }

    private fun AndesMessage.getBody() = findViewById<TextView>(R.id.andes_body)
}
