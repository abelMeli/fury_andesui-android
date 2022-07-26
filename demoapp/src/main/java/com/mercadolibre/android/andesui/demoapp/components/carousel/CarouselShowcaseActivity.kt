package com.mercadolibre.android.andesui.demoapp.components.carousel

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.mercadolibre.android.andesui.card.type.AndesCardType
import com.mercadolibre.android.andesui.carousel.AndesCarousel
import com.mercadolibre.android.andesui.carousel.margin.AndesCarouselMargin
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselDelegate
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiCarouselCardItemBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiCarouselItemBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicCarouselBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticCarouselBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs

@Suppress("TooManyFunctions", "LongMethod")
class CarouselShowcaseActivity : BaseActivity(), AndesCarouselDelegate {

    private lateinit var viewPager: CustomViewPager

    lateinit var snappedlist: List<CarouselModel>
    lateinit var freelist: List<CarouselModel>
    lateinit var dynamicList: List<CarouselModel>

    var freeCarouselId: Int? = null
    var snappedCarouselId: Int? = null
    var dynamicCarouselId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()

        snappedlist = getDataSetSnapped()
        freelist = getDataSetFree()
        dynamicList = getDataSetSnapped()

        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_carousel)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf(
            AndesuiDynamicCarouselBinding.inflate(layoutInflater).root,
            AndesuiStaticCarouselBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        addStaticPage(adapter.views[1])
    }

    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicCarouselBinding.bind(container)
        val clearButton = binding.clearButton
        val changeButton = binding.changeButton
        val scrollButton = binding.scrollButton
        val smoothScrollButton = binding.smoothScrollButton
        val scrollEditText: AppCompatEditText = binding.scrollEditText
        val carousel = binding.andesCarousel
        val checkboxCentered = binding.checkboxCentered
        val checkboxInfinite = binding.checkboxInfinite
        val checkboxAutoplay = binding.checkboxAutoplay
        val textFieldAutoplaySpeed = binding.textfieldAutoplaySpeed

        dynamicCarouselId = carousel.id
        carousel.delegate = this

        val marginSpinner = binding.marginSpinner
        ArrayAdapter.createFromResource(
            this,
            R.array.andes_carousel_margin_spinner,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            marginSpinner.adapter = adapter
        }

        scrollButton.setOnClickListener {
            scrollEditText.text?.toString()?.apply {
                val position =
                    if (scrollEditText.text?.toString().isNullOrBlank()) 0
                    else scrollEditText.text?.toString()?.toInt()
                carousel.scrollToPosition(position ?: 0)
            }
        }

        smoothScrollButton.setOnClickListener {
            scrollEditText.text?.toString()?.apply {
                val position =
                    if (scrollEditText.text?.toString().isNullOrBlank()) 0
                    else scrollEditText.text?.toString()?.toInt()
                carousel.smoothScrollToPosition(position ?: 0)
            }
        }

        clearButton.setOnClickListener {
            carousel.center = true
            carousel.margin = AndesCarouselMargin.DEFAULT

            checkboxCentered.status = AndesCheckboxStatus.SELECTED
            checkboxInfinite.status = AndesCheckboxStatus.UNSELECTED
            checkboxAutoplay.status = AndesCheckboxStatus.UNSELECTED
            textFieldAutoplaySpeed.text = ""
            marginSpinner.setSelection(0)
            carousel.autoplay = false
            carousel.autoplaySpeed = 3000
        }

        changeButton.setOnClickListener {
            val margin = when (marginSpinner.selectedItem) {
                "None" -> AndesCarouselMargin.NONE
                "XSmall" -> AndesCarouselMargin.XSMALL
                "Default" -> AndesCarouselMargin.DEFAULT
                "Small" -> AndesCarouselMargin.SMALL
                "Medium" -> AndesCarouselMargin.MEDIUM
                "Large" -> AndesCarouselMargin.LARGE
                else -> AndesCarouselMargin.DEFAULT
            }
            carousel.center = checkboxCentered.status == AndesCheckboxStatus.SELECTED
            carousel.margin = margin
            carousel.infinite = checkboxInfinite.status == AndesCheckboxStatus.SELECTED
            carousel.autoplay = checkboxAutoplay.status == AndesCheckboxStatus.SELECTED
            textFieldAutoplaySpeed.text?.toLongOrNull()?.let {
                carousel.autoplaySpeed = it
            }
        }

        checkboxAutoplay.setupCallback {
            carousel.autoplay = checkboxAutoplay.status == AndesCheckboxStatus.SELECTED
            textFieldAutoplaySpeed.text?.toLongOrNull()?.let {
                carousel.autoplaySpeed = it
            }
        }
    }

    private fun addStaticPage(container: View) {
        val binding = AndesuiStaticCarouselBinding.bind(container)
        val carouselSnapped = binding.carouselSnapped
        val carouselFree = binding.carouselFree

        snappedCarouselId = carouselSnapped.id
        freeCarouselId = carouselFree.id
        carouselSnapped.delegate = this
        carouselFree.delegate = this

        binding.andesuiDemoappAndesCarouselSpecsButton.setOnClickListener {
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
        val binding = AndesuiCarouselItemBinding.bind(view)
        val item = when (andesCarouselView.id) {
            freeCarouselId -> freelist[position]
            snappedCarouselId -> snappedlist[position]
            else -> dynamicList[position]
        }

        val cardItem = binding.cardItem.apply {
            val cardView = AndesuiCarouselCardItemBinding.inflate(LayoutInflater.from(context))
            cardView.text.apply {
                this.text = item.label
            }
            cardView.image.apply {
                if (andesCarouselView.id == freeCarouselId) {
                    this.visibility = View.GONE
                }
            }
            this.cardView = cardView.root
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
                Toast.makeText(applicationContext, freelist[position].toString(), Toast.LENGTH_SHORT).show()
            }
            snappedCarouselId -> {
                Toast.makeText(applicationContext, snappedlist[position].toString(), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(applicationContext, dynamicList[position].toString(), Toast.LENGTH_SHORT).show()
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
