package com.mercadolibre.android.andesui.demoapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.mercadolibre.android.andesui.demoapp.utils.SafeIntent
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.home.model.Section
import com.mercadolibre.android.andesui.demoapp.home.utils.setupAndesList
import com.mercadolibre.android.andesui.demoapp.home.viewmodel.SearchViewModel
import com.mercadolibre.android.andesui.demoapp.utils.replaceWith
import com.mercadolibre.android.andesui.demoapp.home.views.DemoappSearchBoxView
import com.mercadolibre.android.andesui.demoapp.home.views.DemoappSectionView
import com.mercadolibre.android.andesui.textview.AndesTextView

class SearchActivity : AppCompatActivity() {

    private val emptyText: AndesTextView by lazy { findViewById<AndesTextView>(R.id.andes_search_empty) }
    private val backArrow: ImageView by lazy { findViewById<ImageView>(R.id.andes_search_arrow_back) }
    private val patternsSection by lazy { findViewById<DemoappSectionView>(R.id.andes_search_patterns_section) }
    private val componentsSection by lazy { findViewById<DemoappSectionView>(R.id.andes_search_components_section) }
    private val foundationsSection by lazy { findViewById<DemoappSectionView>(R.id.andes_search_foundations_section) }
    private val searchbox: DemoappSearchBoxView by lazy { findViewById<DemoappSearchBoxView>(R.id.andes_main_searchbox_view) }
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        setupFoundationsSectionView()
        setupPatternsSectionView()
        setupComponentsSectionView()
        setupBackArrowView()
        setupSearchboxView()
    }

    private fun setupBackArrowView() {
        backArrow.setOnClickListener {
            finishAfterTransition()
        }
    }

    private fun setupComponentsSectionView() {
        val components: MutableList<Section> = mutableListOf()
        setupAndesList(this, components) { _, position ->
            startActivity(SafeIntent(this, components[position].uri))
            finishAfterTransition()
        }.also { list ->
            componentsSection.setContent(getString(R.string.andes_demoapp_components_title), list)
            viewModel.getComponents().observe(this, Observer {
                components.replaceWith(it)
                list.refreshListAdapter()
                refreshListVisibility(it, componentsSection)
                refreshNoResultsVisibility()
            })
        }
    }

    private fun setupPatternsSectionView() {
        val patterns: MutableList<Section> = mutableListOf()
        setupAndesList(this, patterns) { _, position ->
            startActivity(SafeIntent(this, patterns[position].uri))
            finishAfterTransition()
        }.also { list ->
            patternsSection.setContent(getString(R.string.andes_demoapp_patterns_title), list)
            viewModel.getPatterns().observe(this, Observer {
                patterns.replaceWith(it)
                list.refreshListAdapter()
                refreshListVisibility(it, patternsSection)
                refreshNoResultsVisibility()
            })
        }
    }

    private fun setupFoundationsSectionView() {
        val foundations: MutableList<Section> = mutableListOf()
        setupAndesList(this, foundations) { _, position ->
            startActivity(SafeIntent(this, foundations[position].uri))
            finishAfterTransition()
        }.also { list ->
            foundationsSection.setContent(getString(R.string.andes_demoapp_foundation_title), list)
            viewModel.getFoundations().observe(this, Observer {
                foundations.replaceWith(it)
                list.refreshListAdapter()
                refreshListVisibility(it, foundationsSection)
                refreshNoResultsVisibility()
            })
        }
    }

    private fun refreshNoResultsVisibility() {
        emptyText.isVisible = !(foundationsSection.isVisible || patternsSection.isVisible || componentsSection.isVisible)
    }

    private fun refreshListVisibility(list: List<Section>, view: View) {
        view.visibility = GONE.takeIf { list.isEmpty() } ?: VISIBLE
    }

    private fun setupSearchboxView() {
        with(searchbox.searchEditText) {
            isEnabled = true
            isClickable = true
            isFocusable = true

            doAfterTextChanged { editableText ->
                viewModel.filterSectionsData(editableText.toString())
            }
            requestFocus()
        }
    }
}
