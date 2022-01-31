package com.mercadolibre.android.andesui.demoapp.commons

import android.app.ActionBar
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiCustomMenuItemBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiShowcaseMainBinding
import com.mercadolibre.android.andesui.demoapp.utils.COMPONENT_KEY
import com.mercadolibre.android.andesui.demoapp.utils.QUERY_PARAMETER_KEY
import com.mercadolibre.android.andesui.demoapp.utils.SafeIntent
import com.mercadolibre.android.andesui.demoapp.utils.StorybookPage
import com.mercadolibre.android.andesui.snackbar.AndesSnackbar
import com.mercadolibre.android.andesui.snackbar.duration.AndesSnackbarDuration
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarType
import com.mercadolibre.android.andesui.textview.AndesTextView

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var appBarTitleTextView: TextView
    protected val baseBinding: AndesuiShowcaseMainBinding by lazy {
        AndesuiShowcaseMainBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(baseBinding.root)
        initActionBar()
    }

    private fun initActionBar() {
        setSupportActionBar(baseBinding.andesuiNavBar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayUseLogoEnabled(false)
            setDisplayShowCustomEnabled(true)
            setDisplayShowHomeEnabled(false)
        }
        val customAppBarBinding = AndesuiCustomMenuItemBinding.inflate(layoutInflater)
        appBarTitleTextView = customAppBarBinding.customActionBarTitle

        setupWebViewComponent(customAppBarBinding)

        val layoutParams = androidx.appcompat.app.ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        supportActionBar?.setCustomView(customAppBarBinding.root, layoutParams)
        appBarTitleTextView.text = getAppBarTitle()
    }

    private fun setupWebViewComponent(binding: AndesuiCustomMenuItemBinding) {
        val activityName = this::class.java.simpleName
        getStorybookPageUrl(activityName)?.let { url ->
            binding.customActionBarWebviewNotSupported.visibility = View.GONE
            configureWebviewTextButton(
                binding.customActionBarWebviewText,
                getString(R.string.andesui_demoapp_web_storybook_description),
                View.OnClickListener {
                    startStoryBookAction(
                        url,
                        activityName
                    )
                }
            )
        } ?: run {
            binding.customActionBarWebviewNotSupported.visibility = View.VISIBLE
            configureWebviewTextButton(
                binding.customActionBarWebviewText,
                getString(R.string.andesui_demoapp_web_storybook_caution_description),
                View.OnClickListener {
                    AndesSnackbar(
                        this@BaseActivity,
                        baseBinding.root,
                        AndesSnackbarType.ERROR,
                        getString(R.string.andesui_demoapp_web_storybook_not_recommended),
                        AndesSnackbarDuration.NORMAL
                    ).show()
                }
            )
        }
    }

    private fun configureWebviewTextButton(
        textButton: AndesTextView,
        contentDescription: String,
        onClick: View.OnClickListener
    ) {
        textButton.setOnClickListener(onClick)
        textButton.accessibilityDelegate = object : View.AccessibilityDelegate() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View?,
                info: AccessibilityNodeInfo?
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info?.run {
                    className = Button::class.java.name
                    this.contentDescription = contentDescription
                }
            }
        }
    }

    private fun startStoryBookAction(url: String, activityName: String) {
        val uri = createUri(url)
        SafeIntent(this, uri).apply {
            putExtra(COMPONENT_KEY, activityName)
            startActivity(this)
        }
    }

    private fun getStorybookPageUrl(activityName: String): String? =
        (AnalyticsHelper().getComponentName(activityName) ?: "HomePage").let { query ->
            StorybookPage.getStorybookPage(query)?.link
        }

    private fun createUri(url: String): Uri {
        return Uri.Builder()
            .scheme("meli")
            .authority("andes")
            .path("storybook")
            .appendQueryParameter(QUERY_PARAMETER_KEY, url)
            .build()
    }

    abstract fun getAppBarTitle(): String

    protected fun updateAppBarTitle(title: String) {
        appBarTitleTextView.text = title
    }
}
