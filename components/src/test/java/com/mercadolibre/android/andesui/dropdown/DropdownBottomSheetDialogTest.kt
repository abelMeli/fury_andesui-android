package com.mercadolibre.android.andesui.dropdown

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.dropdown.utils.DropdownBottomSheetDialog
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.ScreenUtils
import com.mercadolibre.android.andesui.utils.getAccessibilityManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class DropdownBottomSheetDialogTest {

    private lateinit var context: Context
    private lateinit var bottomSheet: DropdownBottomSheetDialog
    private val theme = R.style.Andes_BottomSheetDialog

    companion object {
        private const val EMPTY_LIST_LENGHT = 0
        private const val fullScreenHeight = ConstraintLayout.LayoutParams.MATCH_PARENT
        private val halfScreenHeight = ScreenUtils.getScreenHeight() / 2
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()

        SoLoader.setInTestMode()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

    @Test
    fun `testing bottomSheet with a11y disabled`() {
        bottomSheet = DropdownBottomSheetDialog(
            context,
            theme,
            createCustomDelegate()
        )

        bottomSheet.show()

        val containerViewHeight = bottomSheet.containerView?.layoutParams?.height
        Assert.assertEquals(fullScreenHeight, containerViewHeight)
    }

    @Test
    fun `testing bottomSheet with a11y enabled`() {
        val shadowA11y =
            Shadows.shadowOf(context.getAccessibilityManager())
        shadowA11y.setEnabled(true)

        bottomSheet = DropdownBottomSheetDialog(
            context,
            theme,
            createCustomDelegate()
        )

        bottomSheet.show()
        val containerViewHeight = bottomSheet.containerView?.layoutParams?.height

        Assert.assertEquals(halfScreenHeight, containerViewHeight)
    }

    private fun createCustomDelegate() = object : AndesListDelegate {
        override fun onItemClick(andesList: AndesList, position: Int) {
            // no-op
        }

        override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
            return AndesListViewItem()
        }

        override fun getDataSetSize(andesList: AndesList): Int {
            return EMPTY_LIST_LENGHT
        }
    }
}
