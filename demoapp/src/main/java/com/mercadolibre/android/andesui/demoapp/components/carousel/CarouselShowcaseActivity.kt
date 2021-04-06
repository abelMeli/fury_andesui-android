package com.mercadolibre.android.andesui.demoapp.components.carousel

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.card.AndesCard
import com.mercadolibre.android.andesui.card.type.AndesCardType
import com.mercadolibre.android.andesui.carousel.AndesCarousel
import com.mercadolibre.android.andesui.carousel.margin.AndesCarouselMargin
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselDelegate
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs

@Suppress("TooManyFunctions", "LongMethod")
class CarouselShowcaseActivity : AppCompatActivity(), AndesCarouselDelegate {

    private lateinit var viewPager: CustomViewPager

    lateinit var snappedlist: List<CarouselModel>
    lateinit var freelist: List<CarouselModel>
    lateinit var dynamicList: List<CarouselModel>

    var freeCarouselId: Int? = null
    var snappedCarouselId: Int? = null
    var dynamicCarouselId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.andesui_showcase_main)

        initActionBar()
        initViewPager()
        attachIndicator()
        loadViews()

        snappedlist = getDataSetSnapped()
        freelist = getDataSetFree()
        dynamicList = getDataSetSnapped()
    }

    private fun initActionBar() {
        setSupportActionBar(findViewById(R.id.andesui_nav_bar))
        supportActionBar?.title = resources.getString(R.string.andes_demoapp_screen_button)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViewPager() {
        val inflater = LayoutInflater.from(this)
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
                inflater.inflate(R.layout.andesui_dynamic_carousel, null, false),
                inflater.inflate(R.layout.andesui_static_carousel, null, false)
        ))
    }

    private fun attachIndicator() {
        val indicator = findViewById<PageIndicator>(R.id.page_indicator)
        indicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        addStaticPage(adapter.views[1])
    }

    private fun addDynamicPage(container: View) {
        val clearButton = container.findViewById<AndesButton>(R.id.clear_button)
        val changeButton = container.findViewById<AndesButton>(R.id.change_button)
        val carousel = container.findViewById<AndesCarousel>(R.id.andes_carousel)
        val checkboxCentered = container.findViewById<AndesCheckbox>(R.id.checkbox_centered)

        dynamicCarouselId = carousel.id
        carousel.delegate = this

        val marginSpinner: Spinner = container.findViewById(R.id.margin_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_carousel_margin_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            marginSpinner.adapter = adapter
        }

        clearButton.setOnClickListener {
            carousel.center = true
            carousel.margin = AndesCarouselMargin.DEFAULT

            checkboxCentered.status = AndesCheckboxStatus.SELECTED
            marginSpinner.setSelection(0)
        }

        changeButton.setOnClickListener {
            val margin = when (marginSpinner.selectedItem) {
                "None" -> AndesCarouselMargin.NONE
                "Default" -> AndesCarouselMargin.DEFAULT
                else -> AndesCarouselMargin.DEFAULT
            }
            carousel.center = checkboxCentered.status == AndesCheckboxStatus.SELECTED
            carousel.margin = margin
        }
    }

    private fun addStaticPage(container: View) {
        val carouselSnapped = container.findViewById<AndesCarousel>(R.id.carousel_snapped)
        val carouselFree = container.findViewById<AndesCarousel>(R.id.carousel_free)

        snappedCarouselId = carouselSnapped.id
        freeCarouselId = carouselFree.id
        carouselSnapped.delegate = this
        carouselFree.delegate = this

        bindAndesSpecsButton(container)
    }

    private fun bindAndesSpecsButton(container: View) {
        container.findViewById<AndesButton>(R.id.andesui_demoapp_andes_carousel_specs_button).setOnClickListener {
            launchSpecs(container.context, AndesSpecs.CAROUSEL)
        }
    }

    private fun getDataSetSnapped() = listOf(
            CarouselModel(Color.RED, getString(R.string.andes_carousel_text)),
            CarouselModel(Color.BLUE, getString(R.string.andes_carousel_text)),
            CarouselModel(Color.GREEN, getString(R.string.andes_carousel_text)),
            CarouselModel(Color.YELLOW, getString(R.string.andes_carousel_text)),
            CarouselModel(Color.RED, getString(R.string.andes_carousel_text)),
            CarouselModel(Color.TRANSPARENT, getString(R.string.andes_carousel_text))
    )

    private fun getDataSetFree() = listOf(
            CarouselModel(Color.TRANSPARENT, "${getString(R.string.andes_carousel_text)} ${getString(R.string.andes_carousel_text)}"),
            CarouselModel(Color.TRANSPARENT, "${getString(R.string.andes_carousel_text)} ${getString(R.string.andes_carousel_text)}"),
            CarouselModel(Color.TRANSPARENT, "${getString(R.string.andes_carousel_text)} ${getString(R.string.andes_carousel_text)}"),
            CarouselModel(Color.TRANSPARENT, "${getString(R.string.andes_carousel_text)} ${getString(R.string.andes_carousel_text)}"),
            CarouselModel(Color.TRANSPARENT, "${getString(R.string.andes_carousel_text)} ${getString(R.string.andes_carousel_text)}"),
            CarouselModel(Color.TRANSPARENT, "${getString(R.string.andes_carousel_text)} ${getString(R.string.andes_carousel_text)}")
    )

    override fun bind(andesCarouselView: AndesCarousel, view: View, position: Int) {
        val item = when (andesCarouselView.id) {
            freeCarouselId -> freelist[position]
            snappedCarouselId -> snappedlist[position]
            else -> dynamicList[position]
        }

        val cardItem = view.findViewById<AndesCard>(R.id.card_item).apply {
            val inflater = LayoutInflater.from(context)
            val cardView = inflater.inflate(R.layout.andesui_carousel_card_item, null, false)
            cardView.findViewById<TextView>(R.id.text).apply {
                this.text = item.label
            }
            cardView.findViewById<ImageView>(R.id.image).apply {
                if (andesCarouselView.id == freeCarouselId) {
                    this.visibility = View.GONE
                }
            }
            this.cardView = cardView
        }

        when (item.backgroundColor) {
            Color.RED -> cardItem.type = AndesCardType.ERROR
            Color.BLUE -> cardItem.type = AndesCardType.HIGHLIGHT
            Color.GREEN -> cardItem.type = AndesCardType.SUCCESS
            Color.YELLOW -> cardItem.type = AndesCardType.WARNING
            Color.TRANSPARENT -> cardItem.type = AndesCardType.NONE
        }
    }

    override fun onClickItem(andesCarouselView: AndesCarousel, position: Int) {
        when (andesCarouselView.id) {
            freeCarouselId -> {
                Toast.makeText(this@CarouselShowcaseActivity, freelist[position].toString(), Toast.LENGTH_SHORT).show()
            }
            snappedCarouselId -> {
                Toast.makeText(this@CarouselShowcaseActivity, snappedlist[position].toString(), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this@CarouselShowcaseActivity, dynamicList[position].toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getDataSetSize(andesCarouselView: AndesCarousel): Int {
        return when (andesCarouselView.id) {
            freeCarouselId -> freelist.size
            snappedCarouselId -> snappedlist.size
            else -> dynamicList.size
        }
    }

    override fun getLayoutItem(andesCarouselView: AndesCarousel) = R.layout.andesui_carousel_item
}
