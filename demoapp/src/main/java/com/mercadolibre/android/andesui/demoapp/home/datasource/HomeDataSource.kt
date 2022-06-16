package com.mercadolibre.android.andesui.demoapp.home.datasource

import android.content.Intent
import android.net.Uri
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AnalyticsHelper
import com.mercadolibre.android.andesui.demoapp.commons.AnalyticsTracker
import com.mercadolibre.android.andesui.demoapp.home.model.MainAction
import com.mercadolibre.android.andesui.demoapp.home.model.Section
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.QUERY_PARAMETER_KEY
import com.mercadolibre.android.andesui.demoapp.utils.SafeIntent
import com.mercadolibre.android.andesui.demoapp.utils.StorybookPage

object HomeDataSource {

    fun getCarouselData() = listOf(
        MainAction("A11y Playground", R.drawable.andes_a11y_24) { context ->
            SafeIntent(context, "andes://playground/home")
        },
        MainAction("UX docs", R.drawable.andes_uxdocs_24) {
            AnalyticsTracker.trackSimpleScreen(AnalyticsHelper.specsTrack)
            Intent(Intent.ACTION_VIEW, Uri.parse(AndesSpecs.HOME_PAGE.link))
        },
        MainAction("Web views", R.drawable.andes_web_24) { context ->
            AnalyticsTracker.logWebviewComponentTracking("Home")
            SafeIntent(
                context, Uri.Builder()
                    .scheme("andes")
                    .authority("storybook")
                    .appendQueryParameter(QUERY_PARAMETER_KEY, StorybookPage.HOMEPAGE.link)
                    .build()
            )
        },
        MainAction("Contribute!", R.drawable.andes_contribute_24) {
            AnalyticsTracker.trackSimpleScreen(AnalyticsHelper.contributeTrack)
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // only email apps should handle this
                putExtra(Intent.EXTRA_EMAIL, arrayOf("andescore@mercadolibre.com"))
                putExtra(Intent.EXTRA_SUBJECT, "I want to contribute to Andes")
            }
        }
    ).sortedBy { it.name }

    fun getComponentsData() = listOf(
        Section("Card", "andes://card"),
        Section("Carousel", "andes://carousel"),
        Section("Tooltip", "andes://tooltip"),
        Section("Checkbox", "andes://checkbox"),
        Section("Radio button", "andes://radiobutton"),
        Section("Slider", "andes://slider"),
        Section("Snackbar", "andes://snackbar"),
        Section("Badge", "andes://badge"),
        Section("Button", "andes://button"),
        Section("Message", "andes://message"),
        Section("Textfield", "andes://textfield"),
        Section("Thumbnail", "andes://thumbnail"),
        Section("Dropdown", "andes://dropdown"),
        Section("List", "andes://list"),
        Section("Progress", "andes://progress"),
        Section("Linear Progress", "andes://linear_progress"),
        Section("Bottom sheet", "andes://bottom_sheet"),
        Section("Date picker", "andes://datepicker"),
        Section("Floating menu", "andes://floatingmenu"),
        Section("Tag", "andes://tag"),
        Section("Tabs", "andes://tabs"),
        Section("Time picker", "andes://timepicker"),
        Section("Money amount", "andes://moneyamount"),
        Section("Switch", "andes://switch"),
        Section("Amount Field", "andes://amountfield"),
        Section("Searchbox", "andes://searchbox")
    ).sortedBy { it.name }

    fun getPatternsData() = listOf(
        Section("Coach marks", "andes://coachmark"),
        Section("Feedback screens", "andes://feedbackscreen")
    ).sortedBy { it.name }

    fun getFoundationsData() = listOf(
        Section("Typography", "andes://typography")
    ).sortedBy { it.name }
}
