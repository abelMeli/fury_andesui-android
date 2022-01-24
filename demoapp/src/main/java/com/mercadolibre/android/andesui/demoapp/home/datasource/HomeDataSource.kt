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
            SafeIntent(context, "meli://andes/playground/home")
        },
        MainAction("UX docs", R.drawable.andes_uxdocs_24) {
            AnalyticsTracker.trackSimpleScreen(AnalyticsHelper.specsTrack)
            Intent(Intent.ACTION_VIEW, Uri.parse(AndesSpecs.HOME_PAGE.link))
        },
        MainAction("Web views", R.drawable.andes_web_24) { context ->
            AnalyticsTracker.logWebviewComponentTracking("Home")
            SafeIntent(
                context, Uri.Builder()
                    .scheme("meli")
                    .authority("andes")
                    .path("storybook")
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
        Section("Card", "meli://andes/card"),
        Section("Carousel", "meli://andes/carousel"),
        Section("Tooltip", "meli://andes/tooltip"),
        Section("Checkbox", "meli://andes/checkbox"),
        Section("Radio button", "meli://andes/radiobutton"),
        Section("Slider", "meli://andes/slider"),
        Section("Snackbar", "meli://andes/snackbar"),
        Section("Badge", "meli://andes/badge"),
        Section("Button", "meli://andes/button"),
        Section("Message", "meli://andes/message"),
        Section("Textfield", "meli://andes/textfield"),
        Section("Thumbnail", "meli://andes/thumbnail"),
        Section("Dropdown", "meli://andes/dropdown"),
        Section("List", "meli://andes/list"),
        Section("Progress", "meli://andes/progress"),
        Section("Linear Progress", "meli://andes/linear_progress"),
        Section("Bottom sheet", "meli://andes/bottom_sheet"),
        Section("Date picker", "meli://andes/datepicker"),
        Section("Floating menu", "meli://andes/floatingmenu"),
        Section("Tag", "meli://andes/tag"),
        Section("Tabs", "meli://andes/tabs"),
        Section("Time picker", "meli://andes/timepicker"),
        Section("Money amount", "meli://andes/moneyamount"),
        Section("Switch", "meli://andes/switch")
    ).sortedBy { it.name }

    fun getPatternsData() = listOf(
        Section("Coach marks", "meli://andes/coachmark"),
        Section("Feedback screens", "meli://andes/feedbackscreen")
    ).sortedBy { it.name }

    fun getFoundationsData() = listOf(
        Section("Typography", "meli://andes/typography")
    ).sortedBy { it.name }
}
