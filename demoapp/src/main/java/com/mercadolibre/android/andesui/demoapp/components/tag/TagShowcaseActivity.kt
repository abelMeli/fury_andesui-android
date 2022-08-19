package com.mercadolibre.android.andesui.demoapp.components.tag

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.Group
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicTagBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTagChoiceBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTagSimpleBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.tag.AndesTagChoice
import com.mercadolibre.android.andesui.tag.AndesTagSimple
import com.mercadolibre.android.andesui.tag.choice.AndesTagChoiceCallback
import com.mercadolibre.android.andesui.tag.choice.mode.AndesTagChoiceMode
import com.mercadolibre.android.andesui.tag.choice.state.AndesTagChoiceState
import com.mercadolibre.android.andesui.tag.leftcontent.LeftContent
import com.mercadolibre.android.andesui.tag.leftcontent.LeftContentDot
import com.mercadolibre.android.andesui.tag.leftcontent.LeftContentIcon
import com.mercadolibre.android.andesui.tag.leftcontent.LeftContentImage
import com.mercadolibre.android.andesui.tag.leftcontent.IconSize
import com.mercadolibre.android.andesui.tag.size.AndesTagSize
import com.mercadolibre.android.andesui.tag.type.AndesTagType
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.utils.validateColor

class TagShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_tag)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
                AndesuiDynamicTagBinding.inflate(layoutInflater).root,
                AndesuiStaticTagSimpleBinding.inflate(layoutInflater).root,
                AndesuiStaticTagChoiceBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        addStaticPage(adapter.views[1])
        addStaticSecondPage(adapter.views[2])
    }

    @Suppress("ComplexMethod", "LongMethod")
    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicTagBinding.bind(container)
        val andesTagSimple = binding.andesuiTag
        val simpleTypeTextView = binding.simpleTypeTextView
        val andesTagChoice = binding.andesuiTagChoice

        val typeSpinner = binding.tagTypeSpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_tag_type_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeSpinner.adapter = adapter
        }

        val simpleTypeSpinner = binding.simpleTypeSpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.simple_type_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            simpleTypeSpinner.adapter = adapter
        }

        val groupDot: Group = binding.groupDot
        val groupIcon: Group = binding.groupIcon
        val groupImage: Group = binding.groupImage
        groupDot.visibility = View.GONE
        groupIcon.visibility = View.GONE
        groupImage.visibility = View.GONE

        val sizeSpinner = binding.sizeSpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.size_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sizeSpinner.adapter = adapter
        }

        val leftContentSpinner = binding.leftContentSpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_left_content_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            leftContentSpinner.adapter = adapter
        }

        val backgroundColor = binding.leftContentBackgroundColor
        backgroundColor.setPrefix("#")
        backgroundColor.placeholder = "FFFFFF"

        val color = binding.leftContentTextColor
        color.setPrefix("#")
        color.placeholder = "FFFFFF"

        val iconBackgroundColor = binding.leftContentIconBackgroundColor
        iconBackgroundColor.setPrefix("#")
        iconBackgroundColor.placeholder = "FFFFFF"

        val iconColor = binding.leftContentIconColor
        iconColor.setPrefix("#")
        iconColor.placeholder = "FFFFFF"

        val array = arrayOf("Warning", "Success", "Close", "Info")
        val iconsSpinner = binding.iconSpinner
        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                array
        )
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        iconsSpinner.adapter = spinnerArrayAdapter

        val arrayUrl = arrayOf("Image 1", "Image 2", "Image 3")
        val urlSpinner = binding.spinnerUrl
        val urlArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrayUrl
        )
        urlArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        urlSpinner.adapter = urlArrayAdapter

        val labelText = binding.labelText
        labelText.placeholder = resources.getString(R.string.andes_textfield_label_text)

        val dismissable = binding.dismissable
        val shouldAnimate: SwitchCompat = binding.shouldAnimate

        typeSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (typeSpinner.getItemAtPosition(position)) {
                    "Simple" -> {
                        andesTagChoice.visibility = View.INVISIBLE
                        andesTagSimple.visibility = View.VISIBLE
                        simpleTypeSpinner.visibility = View.VISIBLE
                        simpleTypeTextView.visibility = View.VISIBLE
                        dismissable.visibility = View.VISIBLE
                        shouldAnimate.visibility = View.GONE
                    }
                    "Choice" -> {
                        andesTagSimple.visibility = View.INVISIBLE
                        andesTagChoice.visibility = View.VISIBLE
                        simpleTypeSpinner.visibility = View.GONE
                        simpleTypeTextView.visibility = View.GONE
                        dismissable.visibility = View.GONE
                        shouldAnimate.visibility = View.VISIBLE
                    }
                }
            }
        }

        leftContentSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                when (leftContentSpinner.getItemAtPosition(position)) {
                    "Dot" -> {
                        groupDot.visibility = View.VISIBLE
                        groupIcon.visibility = View.GONE
                        groupImage.visibility = View.GONE
                    }
                    "Icon" -> {
                        groupDot.visibility = View.GONE
                        groupIcon.visibility = View.VISIBLE
                        groupImage.visibility = View.GONE
                    }
                    "Image" -> {
                        groupDot.visibility = View.GONE
                        groupIcon.visibility = View.GONE
                        groupImage.visibility = View.VISIBLE
                    }
                    else -> {
                        groupDot.visibility = View.GONE
                        groupIcon.visibility = View.GONE
                        groupImage.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing.
            }
        }

        binding.clearButton.setOnClickListener {
            andesTagSimple.visibility = View.VISIBLE

            dismissable.isChecked = false
            simpleTypeSpinner.setSelection(0)
            typeSpinner.setSelection(0)
            sizeSpinner.setSelection(0)
            leftContentSpinner.setSelection(0)
            labelText.text = ""
            labelText.state = AndesTextfieldState.IDLE
            labelText.helper = null

            andesTagSimple.text = "Simple tag"
            andesTagSimple.type = AndesTagType.NEUTRAL
            andesTagSimple.size = AndesTagSize.LARGE
            andesTagSimple.isDismissable = false
            andesTagSimple.leftContent = null
        }

        binding.changeButton.setOnClickListener {
            val size = AndesTagSize.fromString(sizeSpinner.selectedItem as String)

            if (labelText.text.isNullOrEmpty()) {
                labelText.state = AndesTextfieldState.ERROR
                labelText.helper = "Este campo es requerido"
                return@setOnClickListener
            } else {
                labelText.state = AndesTextfieldState.IDLE
                labelText.helper = null
            }
            val text = labelText.text

            var leftContent: LeftContent? = null
            when (leftContentSpinner.selectedItem) {
                "Dot" -> {
                    if (backgroundColor.text.isNullOrEmpty()) {
                        backgroundColor.state = AndesTextfieldState.ERROR
                        backgroundColor.helper = "Este campo es requerido"
                        return@setOnClickListener
                    } else if (!validateColor("#${backgroundColor.text!!}")) {
                        backgroundColor.state = AndesTextfieldState.ERROR
                        backgroundColor.helper = "Color inválido"
                        return@setOnClickListener
                    } else {
                        backgroundColor.state = AndesTextfieldState.IDLE
                        backgroundColor.helper = ""
                    }
                    var textColor: String? = null
                    if (!color.text.isNullOrEmpty()) {
                        if (!validateColor("#${color.text!!}")) {
                            color.state = AndesTextfieldState.ERROR
                            color.helper = "Color inválido"
                            return@setOnClickListener
                        } else {
                            color.state = AndesTextfieldState.IDLE
                            color.helper = ""
                            textColor = "#${color.text}"
                        }
                    } else {
                        color.state = AndesTextfieldState.IDLE
                        color.helper = ""
                    }
                    leftContent = LeftContent(
                            dot = LeftContentDot(
                                    "#${backgroundColor.text!!}",
                                    binding.leftContentText.text,
                                    textColor
                            )
                    )
                }
                "Icon" -> {
                    val icon: String?
                    if (!iconBackgroundColor.text.isNullOrEmpty() && !validateColor("#${iconBackgroundColor.text!!}")) {
                        iconBackgroundColor.state = AndesTextfieldState.ERROR
                        iconBackgroundColor.helper = "Color inválido"
                        return@setOnClickListener
                    } else {
                        iconBackgroundColor.state = AndesTextfieldState.IDLE
                        iconBackgroundColor.helper = ""
                        icon = "#${iconColor.text}"
                    }
                    val path = when (iconsSpinner.selectedItem) {
                        "Warning" -> "andes_ui_feedback_warning_24"
                        "Success" -> "andes_ui_feedback_success_24"
                        "Close" -> "andes_ui_close_24"
                        "Info" -> "andes_ui_feedback_info_24"
                        else -> "andes_ui_close_24"
                    }
                    var background: String? = null
                    if (!iconBackgroundColor.text.isNullOrEmpty()) {
                        background = "#${iconBackgroundColor.text!!}"
                    }
                    leftContent = LeftContent(
                            icon = LeftContentIcon(
                                    backgroundColor = background,
                                    path = path,
                                    iconColor = icon
                            )
                    )
                }
                "Image" -> {
                    val pictureUrl = when (urlSpinner.selectedItemPosition) {
                        0 -> "https://conceptodefinicion.de/wp-content/uploads/2014/10/persona.jpg"
                        1 -> "https://eststatic.com/2015/responsive-images/" +
                                "virtudes-de-una-persona___large_990_660.jpg"
                        else -> "https://imagenes.universia.net/gc/net/images/" +
                                "gente/f/fr/fra/frases_de_confianza.jpg"
                    }
                    Glide.with(this)
                            .load(pictureUrl)
                            .asBitmap()
                            .into(
                                    object : SimpleTarget<Bitmap>() {
                                        override fun onResourceReady(
                                            resource: Bitmap?,
                                            glideAnimation: GlideAnimation<in Bitmap?>?
                                        ) {
                                            if (resource != null) {
                                                leftContent = LeftContent(image = LeftContentImage(resource))
                                                andesTagSimple.leftContent = leftContent
                                            }
                                        }
                                    }
                            )
                }
            }

            when (typeSpinner.selectedItem as String) {
                "Simple" -> {
                    val simpleType = AndesTagType.fromString(simpleTypeSpinner.selectedItem as String)
                    val isDismissable = dismissable.isChecked

                    andesTagSimple.text = text
                    andesTagSimple.type = simpleType
                    andesTagSimple.size = size
                    andesTagSimple.isDismissable = isDismissable
                    andesTagSimple.leftContent = leftContent
                }
                "Choice" -> {
                    val animation = shouldAnimate.isChecked
                    andesTagChoice.shouldAnimateTag = animation
                    andesTagChoice.text = text
                    andesTagChoice.size = size
                    andesTagChoice.leftContent = leftContent
                }
            }
        }
    }

    @Suppress("LongMethod")
    private fun addStaticPage(container: View) {
        val binding = AndesuiStaticTagSimpleBinding.bind(container)
        binding.andesuiDemoappAndesTagSpecsButton.setOnClickListener {
            launchSpecs(it.context, AndesSpecs.TAG)
        }

        val firstColumn = binding.firstColumn
        val secondColumn = binding.secondColumn

        val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, resources.getDimension(R.dimen.badge_margin_vertical).toInt())

        val tagSimpleSmall = AndesTagSimple(
                this,
                AndesTagType.HIGHLIGHT,
                AndesTagSize.SMALL,
                "Lorem Ipsum"
        )
        firstColumn.addView(tagSimpleSmall, params)

        val tagSimpleSmallDismissable = AndesTagSimple(
                this,
                AndesTagType.NEUTRAL,
                AndesTagSize.SMALL,
                "Dismissable"
        )
        tagSimpleSmallDismissable.isDismissable = true

        firstColumn.addView(tagSimpleSmallDismissable, params)

        val tagSimpleSmallDismissableWithCallback = AndesTagSimple(
                this,
                AndesTagType.SUCCESS,
                AndesTagSize.SMALL,
                "Callback"
        )
        tagSimpleSmallDismissableWithCallback.isDismissable = true
        tagSimpleSmallDismissableWithCallback.setupDismsissableCallback(
                View.OnClickListener {
                    Toast.makeText(applicationContext, "Dismiss onClicked", Toast.LENGTH_LONG).show()
                }
        )
        firstColumn.addView(tagSimpleSmallDismissableWithCallback, params)

        val tagSimple = AndesTagSimple(
                this,
                AndesTagType.ERROR,
                AndesTagSize.LARGE,
                "Lorem Ipsum"
        )
        firstColumn.addView(tagSimple, params)

        val tagSimpleDismissable = AndesTagSimple(
                this,
                AndesTagType.NEUTRAL,
                AndesTagSize.LARGE,
                "Dismissable"
        )
        tagSimpleDismissable.isDismissable = true
        firstColumn.addView(tagSimpleDismissable, params)

        val tagSimpleDismissableWithCallback = AndesTagSimple(
                this,
                AndesTagType.WARNING,
                AndesTagSize.LARGE,
                "Callback"
        )
        tagSimpleDismissableWithCallback.isDismissable = true
        tagSimpleDismissableWithCallback.setupDismsissableCallback(
                View.OnClickListener {
                    Toast.makeText(applicationContext, "Dismiss onClicked", Toast.LENGTH_LONG).show()
                }
        )
        firstColumn.addView(tagSimpleDismissableWithCallback, params)

        // Left content DOT
        val tagSimpleDot = AndesTagSimple(
                this,
                AndesTagType.NEUTRAL,
                AndesTagSize.LARGE,
                "Amarillo"
        )
        tagSimpleDot.leftContent = LeftContent(
                dot = LeftContentDot("#FFEC2B")
        )
        firstColumn.addView(tagSimpleDot, params)

        val tagSimpleDotDismissable = AndesTagSimple(
                this,
                AndesTagType.NEUTRAL,
                AndesTagSize.LARGE,
                "Azul"
        )
        tagSimpleDotDismissable.isDismissable = true
        tagSimpleDotDismissable.leftContent = LeftContent(
                dot = LeftContentDot("#2B5BFF")
        )
        firstColumn.addView(tagSimpleDotDismissable, params)

        val tagSimpleDotText = AndesTagSimple(
                this,
                AndesTagType.NEUTRAL,
                AndesTagSize.LARGE,
                "Camila Farías"
        )
        tagSimpleDotText.leftContent = LeftContent(
                dot = LeftContentDot("#2E97FF", "CF", "#FFFFFF")
        )
        secondColumn.addView(tagSimpleDotText, params)

        val tagSimpleDotTextDismissable = AndesTagSimple(
                this,
                AndesTagType.NEUTRAL,
                AndesTagSize.LARGE,
                "Camila Farías"
        )
        tagSimpleDotTextDismissable.isDismissable = true
        tagSimpleDotTextDismissable.leftContent = LeftContent(
                dot = LeftContentDot("#E3E3E3", "CF", "#8C8C8C")
        )
        secondColumn.addView(tagSimpleDotTextDismissable, params)

        val tagSimpleIcon = AndesTagSimple(
                this,
                AndesTagType.NEUTRAL,
                AndesTagSize.LARGE,
                "Tag con icono"
        )
        tagSimpleIcon.leftContent = LeftContent(
                icon = LeftContentIcon(
                        backgroundColor = "#10B906",
                        path = "andes_ui_feedback_success_24",
                        iconColor = "#FFFFFF"
                )

        )
        secondColumn.addView(tagSimpleIcon, params)

        val tagSimpleIconDismissable = AndesTagSimple(
                this,
                AndesTagType.NEUTRAL,
                AndesTagSize.LARGE,
                "Icono"
        )
        tagSimpleIconDismissable.isDismissable = true
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.andes_navegacion_ajustes, applicationContext.theme)
        tagSimpleIconDismissable.leftContent = LeftContent(
                icon = LeftContentIcon(
                        backgroundColor = "#E7E7E7",
                        icon = drawable,
                        iconColor = "#8C8C8C"
                )
        )
        secondColumn.addView(tagSimpleIconDismissable, params)

        Glide.with(this)
                .load("https://imagenes.universia.net/gc/net/images/gente/f/fr/fra/frases_de_confianza.jpg")
                .asBitmap()
                .into(
                        object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap?>?) {
                                if (resource != null) {
                                    val tagSimpleImage = AndesTagSimple(
                                            this@TagShowcaseActivity,
                                            AndesTagType.NEUTRAL,
                                            AndesTagSize.LARGE,
                                            "Tatiana"
                                    )
                                    tagSimpleImage.leftContent = LeftContent(image = LeftContentImage(resource))
                                    secondColumn.addView(tagSimpleImage, params)
                                }
                            }
                        }
                )

        Glide.with(this)
                .load(
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:" +
                                "ANd9GcQPUjE4Vno7piEReTnCrwR3oSxBxkfhIYAnXzcs7zC0ekNPPwnc&s"
                )
                .asBitmap()
                .into(
                        object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap?>?) {
                                if (resource != null) {
                                    val tagSimpleImage = AndesTagSimple(
                                            this@TagShowcaseActivity,
                                            AndesTagType.NEUTRAL,
                                            AndesTagSize.LARGE,
                                            "Lorenzo"
                                    )
                                    tagSimpleImage.isDismissable = true
                                    tagSimpleImage.leftContent = LeftContent(image = LeftContentImage(resource))
                                    secondColumn.addView(tagSimpleImage, params)
                                }
                            }
                        }
                )
    }

    @Suppress("LongMethod")
    private fun addStaticSecondPage(container: View) {
        val binding = AndesuiStaticTagChoiceBinding.bind(container)
        binding.andesuiDemoappAndesTagSpecsButton.setOnClickListener {
            launchSpecs(this, AndesSpecs.TAG)
        }
        val viewTitle = binding.staticTagTitle
        viewTitle.text = "Choice tag"

        val drawable = ResourcesCompat.getDrawable(
                resources, R.drawable.andes_navegacion_ajustes, applicationContext.theme
        )
        val firstColumn = binding.firstColumn
        val secondColumn = binding.secondColumn

        val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, resources.getDimension(R.dimen.badge_margin_vertical).toInt())

        val iconLargeTag = AndesTagChoice(
                this,
                AndesTagChoiceMode.SIMPLE,
                AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED,
                "Icon large"
        )
        iconLargeTag.leftContent = LeftContent(
                icon = LeftContentIcon(
                        backgroundColor = null,
                        icon = drawable,
                        iconColor = "#8C8C8C"
                )
        )
        firstColumn.addView(iconLargeTag, params)

        val iconLargeColoredTag = AndesTagChoice(
                this,
                AndesTagChoiceMode.SIMPLE,
                AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED,
                "Icon large colored"
        )

        iconLargeColoredTag.leftContent = LeftContent(
                icon = LeftContentIcon(
                        backgroundColor = null,
                        icon = drawable
                )
        )
        firstColumn.addView(iconLargeColoredTag, params)

        val iconSmallTag = AndesTagChoice(
                this,
                AndesTagChoiceMode.SIMPLE,
                AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED,
                "Icon small"
        )
        iconSmallTag.leftContent = LeftContent(
                icon = LeftContentIcon(
                        backgroundColor = null,
                        icon = drawable,
                        iconColor = "#8C8C8C",
                        iconSize = IconSize.SMALL
                )
        )
        firstColumn.addView(iconSmallTag, params)

        val iconSmallTagColored = AndesTagChoice(
                this,
                AndesTagChoiceMode.SIMPLE,
                AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED,
                "Icon small colored"
        )
        iconSmallTagColored.leftContent = LeftContent(
                icon = LeftContentIcon(
                        backgroundColor = null,
                        icon = drawable,
                        iconSize = IconSize.SMALL
                )
        )
        firstColumn.addView(iconSmallTagColored, params)

        val tagChoiceSmall = AndesTagChoice(
                this,
                AndesTagChoiceMode.SIMPLE,
                AndesTagSize.SMALL,
                AndesTagChoiceState.SELECTED,
                "Small tag"
        )
        firstColumn.addView(tagChoiceSmall, params)

        val tagAnimatedSmall = AndesTagChoice(
                this,
                AndesTagChoiceMode.SIMPLE,
                AndesTagSize.SMALL,
                AndesTagChoiceState.SELECTED,
                "Small animated"
        )
        tagAnimatedSmall.shouldAnimateTag = true
        firstColumn.addView(tagAnimatedSmall, params)

        val tagAnimatedLarge = AndesTagChoice(
                this,
                AndesTagChoiceMode.SIMPLE,
                AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED,
                "Large animated"
        )
        tagAnimatedLarge.shouldAnimateTag = true
        firstColumn.addView(tagAnimatedLarge, params)

        val discounts = ResourcesCompat.getDrawable(
                resources, R.drawable.andes_navegacion_ofertas_24, applicationContext.theme
        )
        val iconSmallAnimated = AndesTagChoice(
                this,
                AndesTagChoiceMode.SIMPLE,
                AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED,
                "Descuentos"
        )
        iconSmallAnimated.shouldAnimateTag = true
        iconSmallAnimated.leftContent = LeftContent(
                icon = LeftContentIcon(
                        backgroundColor = null,
                        icon = discounts,
                        iconSize = IconSize.SMALL
                )
        )
        iconSmallAnimated.shouldAnimateTag = true
        firstColumn.addView(iconSmallAnimated, params)

        val tagChoiceDropdown = AndesTagChoice(
                this,
                AndesTagChoiceMode.DROPDOWN,
                AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE,
                "Dropdown"
        )
        secondColumn.addView(tagChoiceDropdown, params)

        val tagChoiceDropdownCallback = AndesTagChoice(
                this,
                AndesTagChoiceMode.DROPDOWN,
                AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE,
                "Callback false"
        )
        tagChoiceDropdownCallback.callback = object : AndesTagChoiceCallback {
            override fun shouldSelectTag(andesTagChoice: AndesTagChoice): Boolean {
                Toast.makeText(applicationContext, "Dropdown clicked. Return false", Toast.LENGTH_LONG).show()
                return false
            }
        }
        secondColumn.addView(tagChoiceDropdownCallback, params)

        val tagChoiceDropdownCallback2 = AndesTagChoice(
                this,
                AndesTagChoiceMode.DROPDOWN,
                AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE,
                "Callback true"
        )
        tagChoiceDropdownCallback2.callback = object : AndesTagChoiceCallback {
            override fun shouldSelectTag(andesTagChoice: AndesTagChoice): Boolean {
                Toast.makeText(applicationContext, "Dropdown clicked. Return true", Toast.LENGTH_LONG).show()
                return true
            }
        }
        secondColumn.addView(tagChoiceDropdownCallback2, params)

        val tagChoiceDropdownCallbackSmall = AndesTagChoice(
                this,
                AndesTagChoiceMode.DROPDOWN,
                AndesTagSize.SMALL,
                AndesTagChoiceState.IDLE,
                "Callback false"
        )
        tagChoiceDropdownCallbackSmall.callback = object : AndesTagChoiceCallback {
            override fun shouldSelectTag(andesTagChoice: AndesTagChoice): Boolean {
                Toast.makeText(applicationContext, "Dropdown clicked. Return false", Toast.LENGTH_LONG).show()
                return false
            }
        }
        secondColumn.addView(tagChoiceDropdownCallbackSmall, params)

        val tagChoiceDropdownCallbackSmall2 = AndesTagChoice(
                this,
                AndesTagChoiceMode.DROPDOWN,
                AndesTagSize.SMALL,
                AndesTagChoiceState.IDLE,
                "Callback true"
        )
        tagChoiceDropdownCallbackSmall2.callback = object : AndesTagChoiceCallback {
            override fun shouldSelectTag(andesTagChoice: AndesTagChoice): Boolean {
                Toast.makeText(applicationContext, "Dropdown clicked. Return true", Toast.LENGTH_LONG).show()
                return true
            }
        }
        secondColumn.addView(tagChoiceDropdownCallbackSmall2, params)
    }
}
