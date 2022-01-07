package com.mercadolibre.android.andesui.demoapp.components.card

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.mercadolibre.android.andesui.card.bodyPadding.AndesCardBodyPadding
import com.mercadolibre.android.andesui.card.hierarchy.AndesCardHierarchy
import com.mercadolibre.android.andesui.card.padding.AndesCardPadding
import com.mercadolibre.android.andesui.card.style.AndesCardStyle
import com.mercadolibre.android.andesui.card.type.AndesCardType
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicCardBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticCardBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs

class CardShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_card)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf(
            AndesuiDynamicCardBinding.inflate(layoutInflater).root,
            AndesuiStaticCardBinding.inflate(layoutInflater).root
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

    @Suppress("ComplexMethod", "LongMethod")
    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicCardBinding.bind(container)
        val title = "Andes card!  \uD83D\uDE04"
        val link = "View more"
        val message = "Cards are containers that allow you to group or structure the content on the screen. " +
                "They are used as a support for actions, text, images and other components throughout the entire UI."

        val andesCard = binding.andesCard
        andesCard.setCardAction(
                View.OnClickListener {
                    Toast.makeText(applicationContext, "OnClicked card!", Toast.LENGTH_LONG).show()
                }
        )
        andesCard.title = title
        andesCard.setLinkAction(
                link,
                View.OnClickListener {
                    launchSpecs(it.context, AndesSpecs.CARD)
                }
        )
        andesCard.padding = AndesCardPadding.SMALL
        andesCard.type = AndesCardType.HIGHLIGHT

        // View card
        val textView = TextView(this)
        textView.text = message
        textView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.title_text_size_card)
        )
        textView.setTextColor(resources.getColor(R.color.andes_text_color_primary))
        andesCard.cardView = textView

        val andesCardTitle = binding.andesTitle
        andesCardTitle.text = title

        val andesCardLink = binding.andesLink
        andesCardLink.text = link

        val spinnerType: Spinner = binding.spinnerType
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_card_type_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }
        spinnerType.setSelection(1)

        val spinnerStyle = binding.spinnerStyle
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_card_style_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerStyle.adapter = adapter
        }

        val spinnerPadding = binding.spinnerPadding
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_card_padding_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPadding.adapter = adapter
        }
        spinnerPadding.setSelection(1)

        val spinnerBodyPadding = binding.spinnerBodyPadding
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_card_body_padding_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBodyPadding.adapter = adapter
        }
        spinnerBodyPadding.setSelection(1)

        val spinnerHierarchy = binding.spinnerHierarchy
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_card_hierarchy_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerHierarchy.adapter = adapter
        }

        binding.buttonUpdate.setOnClickListener {
            val type = when (spinnerType.selectedItem) {
                "None" -> AndesCardType.NONE
                "Highlight" -> AndesCardType.HIGHLIGHT
                "Error" -> AndesCardType.ERROR
                "Success" -> AndesCardType.SUCCESS
                "Warning" -> AndesCardType.WARNING
                else -> AndesCardType.NONE
            }
            val style = when (spinnerStyle.selectedItem) {
                "Elevated" -> AndesCardStyle.ELEVATED
                "Outline" -> AndesCardStyle.OUTLINE
                else -> AndesCardStyle.ELEVATED
            }
            val padding = when (spinnerPadding.selectedItem) {
                "None" -> AndesCardPadding.NONE
                "Small" -> AndesCardPadding.SMALL
                "Medium" -> AndesCardPadding.MEDIUM
                "Large" -> AndesCardPadding.LARGE
                "XLarge" -> AndesCardPadding.XLARGE
                else -> AndesCardPadding.NONE
            }
            val bodyPadding = when (spinnerBodyPadding.selectedItem) {
                "None" -> AndesCardBodyPadding.NONE
                "Small" -> AndesCardBodyPadding.SMALL
                "Medium" -> AndesCardBodyPadding.MEDIUM
                "Large" -> AndesCardBodyPadding.LARGE
                "XLarge" -> AndesCardBodyPadding.XLARGE
                else -> AndesCardBodyPadding.NONE
            }
            val hierarchy = when (spinnerHierarchy.selectedItem) {
                "Primary" -> AndesCardHierarchy.PRIMARY
                "Secondary" -> AndesCardHierarchy.SECONDARY
                "Secondary dark" -> AndesCardHierarchy.SECONDARY_DARK
                else -> AndesCardHierarchy.PRIMARY
            }

            andesCard.type = type
            andesCard.style = style
            andesCard.padding = padding
            andesCard.bodyPadding = bodyPadding
            andesCard.hierarchy = hierarchy
            andesCard.title = andesCardTitle.text
            if (!andesCardLink.text.isNullOrEmpty()) {
                andesCard.setLinkAction(
                        andesCardLink.text!!,
                        View.OnClickListener {
                            Toast.makeText(applicationContext, "OnClicked action!", Toast.LENGTH_LONG).show()
                        }
                )
            } else {
                andesCard.removeLinkAction()
            }
        }

        binding.buttonClear.setOnClickListener {
            andesCardTitle.text = title
            andesCardLink.text = link
            andesCard.cardView = textView
            andesCard.title = title
            andesCard.padding = AndesCardPadding.SMALL
            andesCard.bodyPadding = AndesCardBodyPadding.SMALL
            andesCard.type = AndesCardType.HIGHLIGHT
            andesCard.style = AndesCardStyle.ELEVATED
            andesCard.setCardAction(
                    View.OnClickListener {
                        Toast.makeText(applicationContext, "OnClicked card!", Toast.LENGTH_LONG).show()
                    }
            )
            andesCard.setLinkAction(
                    link,
                    View.OnClickListener {
                        Toast.makeText(applicationContext, "OnClicked action card!", Toast.LENGTH_SHORT).show()
                    }
            )
            spinnerType.setSelection(1)
            spinnerStyle.setSelection(0)
            spinnerPadding.setSelection(1)
            spinnerBodyPadding.setSelection(1)
            spinnerHierarchy.setSelection(0)
        }
    }

    private fun addStaticPage(container: View) {
        val binding = AndesuiStaticCardBinding.bind(container)
        binding.cardExample1.setLinkAction(
                "Action",
                View.OnClickListener {
                    Toast.makeText(applicationContext, "Action clicked!", Toast.LENGTH_SHORT).show()
                }
        )
        val textView1 = TextView(this)
        textView1.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.title_text_size_card)
        )
        textView1.setTextColor(resources.getColor(R.color.andes_text_color_primary))
        textView1.text = resources.getString(R.string.andes_card_example_1)
        binding.cardExample1.cardView = textView1

        val textView2 = TextView(this)
        textView2.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.title_text_size_card)
        )
        textView2.setTextColor(resources.getColor(R.color.andes_text_color_primary))
        textView2.text = resources.getString(R.string.andes_card_example_2)
        binding.cardExample2.cardView = textView2

        binding.cardExample3.setLinkAction(
                "Action",
                View.OnClickListener {
                    Toast.makeText(applicationContext, "Action clicked!", Toast.LENGTH_SHORT).show()
                }
        )
        val textView3 = TextView(this)
        textView3.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.title_text_size_card)
        )
        textView3.setTextColor(resources.getColor(R.color.andes_text_color_primary))
        textView3.text = resources.getString(R.string.andes_card_example_3)
        binding.cardExample3.cardView = textView3

        val textView4 = TextView(this)
        textView4.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.title_text_size_card)
        )
        textView4.setTextColor(resources.getColor(R.color.andes_text_color_primary))
        textView4.text = resources.getString(R.string.andes_card_example_4)
        binding.cardExample4.cardView = textView4

        val textView5 = TextView(this)
        textView5.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.title_text_size_card)
        )
        textView5.setTextColor(resources.getColor(R.color.andes_text_color_primary))
        textView5.text = resources.getString(R.string.andes_card_example_5)
        binding.cardExample5.cardView = textView5

        binding.andesuiDemoappAndesCardSpecsButton.setOnClickListener {
            launchSpecs(it.context, AndesSpecs.CARD)
        }
    }
}
