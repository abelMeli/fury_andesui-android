package com.mercadolibre.android.andesui.demoapp.home

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.ActivitySearchBinding
import com.mercadolibre.android.andesui.demoapp.home.model.Section
import com.mercadolibre.android.andesui.demoapp.home.utils.setupAndesList
import com.mercadolibre.android.andesui.demoapp.home.viewmodel.SearchViewModel
import com.mercadolibre.android.andesui.demoapp.utils.SafeIntent
import com.mercadolibre.android.andesui.demoapp.utils.replaceWith

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel
    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        setupFoundationsSectionView()
        setupPatternsSectionView()
        setupComponentsSectionView()
        setupBackArrowView()
        setupSearchboxView()
    }

    private fun setupBackArrowView() {
        binding.andesSearchArrowBack.setOnClickListener {
            finishAfterTransition()
        }
    }

    private fun setupComponentsSectionView() {
        val components: MutableList<Section> = mutableListOf()
        setupAndesList(this, components) { _, position ->
            startActivity(SafeIntent(this, components[position].uri))
            finishAfterTransition()
        }.also { list ->
            binding.andesSearchComponentsSection.setContent(
                getString(R.string.andes_demoapp_components_title),
                list
            )
            viewModel.getComponents().observe(this, Observer {
                components.replaceWith(it)
                list.refreshListAdapter()
                refreshListVisibility(it, binding.andesSearchComponentsSection)
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
            binding.andesSearchPatternsSection.setContent(
                getString(R.string.andes_demoapp_patterns_title),
                list
            )
            viewModel.getPatterns().observe(this, Observer {
                patterns.replaceWith(it)
                list.refreshListAdapter()
                refreshListVisibility(it, binding.andesSearchPatternsSection)
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
            binding.andesSearchFoundationsSection.setContent(
                getString(R.string.andes_demoapp_foundation_title),
                list
            )
            viewModel.getFoundations().observe(this, Observer {
                foundations.replaceWith(it)
                list.refreshListAdapter()
                refreshListVisibility(it, binding.andesSearchFoundationsSection)
                refreshNoResultsVisibility()
            })
        }
    }

    private fun refreshNoResultsVisibility() {
        binding.andesSearchEmpty.isVisible = (
                binding.andesSearchFoundationsSection.isVisible ||
                binding.andesSearchPatternsSection.isVisible ||
                binding.andesSearchComponentsSection.isVisible
        ).not()
    }

    private fun refreshListVisibility(list: List<Section>, view: View) {
        view.visibility = GONE.takeIf { list.isEmpty() } ?: VISIBLE
    }

    private fun setupSearchboxView() {
        with(binding.andesMainSearchboxView.searchEditText) {
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
