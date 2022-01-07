package com.mercadolibre.android.andesui.demoapp.commons

import android.app.ActionBar
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiCustomMenuItemBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiShowcaseMainBinding
import com.mercadolibre.android.andesui.demoapp.utils.COMPONENT_KEY
import com.mercadolibre.android.andesui.demoapp.utils.QUERY_PARAMETER_KEY
import com.mercadolibre.android.andesui.demoapp.utils.SafeIntent
import com.mercadolibre.android.andesui.demoapp.utils.StorybookPage

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var appBarTitleTextView: TextView
    protected val baseBinding: AndesuiShowcaseMainBinding by lazy { AndesuiShowcaseMainBinding.inflate(layoutInflater) }

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
        val customAppBarLayout = AndesuiCustomMenuItemBinding.inflate(layoutInflater)
        appBarTitleTextView = customAppBarLayout.customActionBarTitle
        val activityName = this::class.java.simpleName

        val webviewText = customAppBarLayout.customActionBarWebviewText

        webviewText.setOnClickListener {
            val uri = createUri(activityName)
            SafeIntent(this, uri).apply {
                putExtra(COMPONENT_KEY, activityName)
                startActivity(this)
            }
        }

        val layoutParams = androidx.appcompat.app.ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        supportActionBar?.setCustomView(customAppBarLayout.root, layoutParams)
        appBarTitleTextView.text = getAppBarTitle()
    }

    private fun createUri(activityName: String): Uri {
        val query = AnalyticsHelper().getComponentName(activityName) ?: "HomePage"
        val fullStorybookUrl = StorybookPage.getStorybookPage(query).link

        return Uri.Builder()
            .scheme("meli")
            .authority("andes")
            .path("storybook")
            .appendQueryParameter(QUERY_PARAMETER_KEY, fullStorybookUrl)
            .build()
    }

    abstract fun getAppBarTitle(): String

    protected fun updateAppBarTitle(title: String) {
        appBarTitleTextView.text = title
    }
}
