package com.mercadolibre.android.andesui.demoapp.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mercadolibre.android.andesui.card.AndesCard
import com.mercadolibre.android.andesui.carousel.AndesCarousel
import com.mercadolibre.android.andesui.carousel.margin.AndesCarouselMargin
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselDelegate
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AnalyticsHelper
import com.mercadolibre.android.andesui.demoapp.commons.AnalyticsTracker
import com.mercadolibre.android.andesui.demoapp.home.model.MainAction
import com.mercadolibre.android.andesui.demoapp.home.model.Section
import com.mercadolibre.android.andesui.demoapp.home.utils.setupAndesList
import com.mercadolibre.android.andesui.demoapp.home.viewmodel.MainViewModel
import com.mercadolibre.android.andesui.demoapp.utils.SafeIntent
import com.mercadolibre.android.andesui.demoapp.utils.replaceWith
import com.mercadolibre.android.andesui.demoapp.home.views.DemoappSearchBoxView
import com.mercadolibre.android.andesui.demoapp.home.views.DemoappSectionView
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBold
import com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds

/**
 * Main activity class
 */
class MainActivity : AppCompatActivity() {

    private val changelogCard: AndesCard by lazy { findViewById<AndesCard>(R.id.andes_main_changelog) }
    private val carouselActions: AndesCarousel by lazy { findViewById<AndesCarousel>(R.id.andes_main_carousel) }
    private val foundationSection: DemoappSectionView by lazy { findViewById<DemoappSectionView>(R.id.andes_main_foundations_section) }
    private val patternsSection: DemoappSectionView by lazy { findViewById<DemoappSectionView>(R.id.andes_main_patterns_section) }
    private val componentsSection: DemoappSectionView by lazy { findViewById<DemoappSectionView>(R.id.andes_main_components_section) }
    private val searchbox: DemoappSearchBoxView by lazy { findViewById<DemoappSearchBoxView>(R.id.andes_main_searchbox_view) }
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.andesui_demoapp_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupChangelogCardView()
        setupCarouselActionsView()
        setupFoundationsSectionView()
        setupPatternsSectionView()
        setupComponentsSectionView()
        setupSearchBoxView()
    }

    private fun setupComponentsSectionView() {
        val components: MutableList<Section> = mutableListOf()
        setupAndesList(this, components) { _, position ->
            startActivity(SafeIntent(this, components[position].uri))
        }.also { list ->
            componentsSection.setContent(getString(R.string.andes_demoapp_components_title), list)
            viewModel.getComponents().observe(this, Observer { components.replaceWith(it) })
        }
    }

    private fun setupPatternsSectionView() {
        val patterns: MutableList<Section> = mutableListOf()
        setupAndesList(this, patterns) { _, position ->
            startActivity(SafeIntent(this, patterns[position].uri))
        }.also { list ->
            patternsSection.setContent(getString(R.string.andes_demoapp_patterns_title), list)
            viewModel.getPatterns().observe(this, Observer { patterns.replaceWith(it) })
        }
    }

    private fun setupFoundationsSectionView() {
        val foundations: MutableList<Section> = mutableListOf()
        setupAndesList(this, foundations) { _, position ->
            startActivity(SafeIntent(this, foundations[position].uri))
        }.also { list ->
            foundationSection.setContent(getString(R.string.andes_demoapp_foundation_title), list)
            viewModel.getFoundations().observe(this, Observer { foundations.replaceWith(it) })
        }
    }

    private fun setupSearchBoxView() {
        searchbox.isEditable = false
        searchbox.setOnClickListener {
            startActivity(
                Intent(this, SearchActivity::class.java),
                ActivityOptions
                    .makeSceneTransitionAnimation(this, it, "searchbox").toBundle()
            )
        }
    }

    private fun setupChangelogCardView() {
        with(changelogCard) {
            val versionName = getString(R.string.andesui_demoapp_version)
            with(findViewById<AndesTextView>(R.id.andes_changelog_text)) {
                text = getString(R.string.andes_main_changelog_text, versionName)
                bodyBolds = AndesBodyBolds(
                    listOf(
                        AndesBodyBold(
                            text.indexOf(versionName),
                            text.indexOf(versionName) + versionName.length
                        )
                    )
                )
                setOnClickListener { changelogCard.performClick() }
            }
            setOnClickListener {
                AnalyticsTracker.trackSimpleScreen(AnalyticsHelper.whatsNewTrack)
                startActivity(SafeIntent(this@MainActivity, "meli://andes/whats-new"))
            }
        }
    }

    private fun setupCarouselActionsView() {
        val mainActions: MutableList<MainAction> = mutableListOf()
        with(carouselActions) {
            margin = AndesCarouselMargin.XSMALL
            (getChildAt(0) as? ConstraintLayout)?.clipChildren = false
            delegate = object : AndesCarouselDelegate {
                override fun bind(andesCarouselView: AndesCarousel, view: View, position: Int) {
                    val item = mainActions[position]
                    with(view.findViewById<ImageView>(R.id.andes_main_carousel_image)) {
                        setImageResource(item.asset)
                    }
                    with(view.findViewById<AndesTextView>(R.id.andes_main_carousel_text)) {
                        text = item.name
                        setOnClickListener { view.performClick() }
                    }
                }

                override fun onClickItem(andesCarouselView: AndesCarousel, position: Int) {
                    startActivity(mainActions[position].intent(this@MainActivity))
                }

                override fun getDataSetSize(andesCarouselView: AndesCarousel) = mainActions.size

                override fun getLayoutItem(andesCarouselView: AndesCarousel) =
                    R.layout.andes_main_carousel_item
            }
        }
        viewModel.getMainActions().observe(this, Observer { mainActions.replaceWith(it) })
    }
}
