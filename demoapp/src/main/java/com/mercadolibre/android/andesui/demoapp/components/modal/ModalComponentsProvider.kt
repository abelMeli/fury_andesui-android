package com.mercadolibre.android.andesui.demoapp.components.modal

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.buttongroup.distribution.AndesButtonGroupDistribution
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.message.AndesMessage
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.message.hierarchy.AndesMessageHierarchy
import com.mercadolibre.android.andesui.message.type.AndesMessageType
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupData
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.AndesModalInterface
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle
import com.mercadolibre.android.uicomponents.resourceprovider.ResourceProvider
import com.mercadolibre.android.uicomponents.resourceprovider.builder.getSuspending
import com.mercadolibre.android.uicomponents.resourceprovider.model.ProviderType

/**
 * Provider of different components/classes needed to show modals.
 * Added here to reuse some code.
 */
object ModalComponentsProvider {

    private const val NO_BUTTONS = 0

    private val images = listOf(
        "placeholder_test",
        "pm_mla_modal_dogfood_discount",
        "beerfactory_mp_op_mlm",
        "modal-full_wild-foods"
    )

    private val largeContentImages = listOf(
        "bank_phone_new_es",
        "bank_error",
        "ilustra_medium_224x180",
        "wallet_home_banking_credits_ml"
    )

    private val buttonTexts = listOf(
        "Aceptar",
        "Continuar",
        "Quiero saber mas"
    )

    private val fullImages = listOf(
        "personalvida",
        "remedy_ic_mobile_person_exclamation_mark_ml",
        "personalvida",
        "remedy_ic_mobile_person_exclamation_mark_ml"
    )

    fun provideContentList(
        context: Context,
        pagesAmount: Int
    ): ArrayList<AndesModalContent> {
        val contentList = arrayListOf<AndesModalContent>()
        repeat(pagesAmount) { page ->
            contentList.add(
                provideCardContent(context, page)
            )
        }
        return contentList
    }

    fun provideFullContentList(
        context: Context,
        pagesAmount: Int
    ): ArrayList<AndesModalContent> {
        val contentList = arrayListOf<AndesModalContent>()
        repeat(pagesAmount) { page ->
            contentList.add(
                provideFullContent(context, page)
            )
        }
        return contentList
    }

    fun provideLargeContentList(
        context: Context,
        pagesAmount: Int
    ): ArrayList<AndesModalContent> {
        val contentList = arrayListOf<AndesModalContent>()
        repeat(pagesAmount) { page ->
            contentList.add(
                provideCardLargeContent(context, page)
            )
        }
        return contentList
    }

    fun provideFullLargeContentList(
        context: Context,
        pagesAmount: Int
    ): ArrayList<AndesModalContent> {
        val contentList = arrayListOf<AndesModalContent>()
        repeat(pagesAmount) { page ->
            contentList.add(
                provideFullLargeContent(context, page)
            )
        }
        return contentList
    }

    fun provideCardContent(context: Context, page: Int = 0) = AndesModalContent(
        title = "Sólo por hoy",
        subtitle = "Descuentos imperdibles en gastronomía! Aprovecha hoy hasta un 15% de descuento en lugares seleccionados",
        assetDrawable = provideImagePlaceholder(context),
        assetContentDescription = "Imagen de promoción",
        suspendedDrawable = {
            provideDrawableSuspended(context, images[page])
        }
    )

    fun provideCardLargeContent(context: Context, page: Int = 0) = AndesModalContent(
        title = "Actualizamos nuestros términos y condiciones, tomate un instante para releerlos",
        subtitle = "Debido a los recientes cambios en la normativa bancaria, actualizamos nuestros términos y condiciones. Te recomendamos " +
                "revisarlos y chequear tus datos de contacto para mantenerlos actualizados. " +
                "${context.resources.getString(R.string.andes_demoapp_playground_lorem)}${
                    context.resources.getString(
                        R.string.andes_demoapp_playground_lorem
                    )
                }${
                    context.resources.getString(
                        R.string.andes_demoapp_playground_lorem
                    )
                }${
                    context.resources.getString(
                        R.string.andes_demoapp_playground_lorem
                    )
                }${
                    context.resources.getString(
                        R.string.andes_demoapp_playground_lorem
                    )
                }",
        assetDrawable = provideImagePlaceholder(context),
        assetContentDescription = "Imagen de banco",
        suspendedDrawable = {
            provideDrawableSuspended(context, largeContentImages[page])
        }
    )

    fun provideFullContent(context: Context, page: Int = 0) = AndesModalContent(
        title = "Bienvenido!",
        subtitle = "Te damos la bienvenida, tomate tu tiempo para ver nuestro contenido",
        assetDrawable = provideImagePlaceholder(context),
        suspendedDrawable = {
            provideDrawableSuspended(context, fullImages[page])
        }
    )

    fun provideFullLargeContent(context: Context, page: Int = 0) = AndesModalContent(
        title = "Bienvenido! Te damos la bienvenida, tomate tu tiempo para ver nuestro contenido.",
        subtitle = "Te damos la bienvenida, tomate tu tiempo para ver nuestro contenido. ${
            context.resources.getString(
                R.string.andes_demoapp_playground_lorem
            )
        }${
            context.resources.getString(
                R.string.andes_demoapp_playground_lorem
            )
        }${
            context.resources.getString(
                R.string.andes_demoapp_playground_lorem
            )
        }${
            context.resources.getString(
                R.string.andes_demoapp_playground_lorem
            )
        }${
            context.resources.getString(
                R.string.andes_demoapp_playground_lorem
            )
        }",
        assetDrawable = provideImagePlaceholder(context),
        suspendedDrawable = {
            provideDrawableSuspended(context, fullImages[page])
        }
    )

    private suspend fun provideDrawableSuspended(
        context: Context,
        resourceName: String
    ): Drawable? {
        return ResourceProvider.with(context)
            .name(resourceName)
            .image()
            .from(ProviderType.REMOTE)
            .getSuspending()
    }

    private fun provideImagePlaceholder(context: Context): Drawable {
        return ColorDrawable(context.resources.getColor(R.color.andes_gray_250))
    }

    fun provideButtonGroupCreator(
        context: Context,
        buttonsAmount: Int
    ): AndesButtonGroupCreator? {
        if (buttonsAmount == NO_BUTTONS) return null

        return object : AndesButtonGroupCreator {
            override fun create(modalInterface: AndesModalInterface): AndesButtonGroupData {
                val buttonList = mutableListOf<AndesButton>()
                repeat(buttonsAmount) { index ->
                    buttonList.add(
                        provideButton(context, index, modalInterface)
                    )
                }

                return AndesButtonGroupData(
                    AndesButtonGroup(
                        context = context,
                        buttonList = buttonList,
                        distribution = AndesButtonGroupDistribution.VERTICAL
                    ),
                    0
                )
            }
        }
    }

    private fun provideButton(context: Context, index: Int, modalInterface: AndesModalInterface) =
        AndesButton(
            context = context,
            buttonText = buttonTexts[index]
        ).apply {
            setOnClickListener {
                Toast.makeText(
                    context.applicationContext,
                    "Click in button ${buttonTexts[index]}",
                    Toast.LENGTH_SHORT
                ).show()
                modalInterface.dismiss()
            }
        }

    fun provideCustomViewFromCode(context: Context, showLargeContent: Boolean) = LinearLayout(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        orientation = LinearLayout.VERTICAL
        addView(
            AndesTextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(
                        0,
                        resources.getDimensionPixelSize(R.dimen.margin_horizontal_text_field),
                        0,
                        resources.getDimensionPixelSize(R.dimen.margin_horizontal_text_field)
                    )
                }
                text = "Modal custom with views created by code"
                style = AndesTextViewStyle.TitleM
                setTextColor(AndesTextViewColor.Primary)
            }
        )
        addView(
            AndesMessage(
                context,
                AndesMessageHierarchy.LOUD,
                AndesMessageType.SUCCESS,
                "Use this to pass any view you want from code",
                "Code created",
                false,
                null,
                null
            ).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(
                        0,
                        0,
                        0,
                        resources.getDimensionPixelSize(R.dimen.margin_horizontal_text_field)
                    )
                }
            }
        )
        addView(
            AndesTextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(
                        0,
                        0,
                        0,
                        resources.getDimensionPixelSize(R.dimen.margin_horizontal_text_field)
                    )
                }
                text = "You can use our AndesTextView to pass text with clickable links"
                style = AndesTextViewStyle.BodyL
                bodyLinks = AndesBodyLinks(
                    links = listOf(
                        AndesBodyLink(48, 63)
                    ),
                    listener = { _ ->
                        Toast.makeText(
                            context.applicationContext,
                            "Click en link",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
                setTextColor(AndesTextViewColor.Secondary)
            }
        )
        if (showLargeContent) {
            addView(
                AndesMessage(
                    context,
                    AndesMessageHierarchy.QUIET,
                    AndesMessageType.NEUTRAL,
                    "You can also use this modal with an xml-based view by inflating it and passing it into the same builder",
                    "Also:",
                    false,
                    null,
                    null
                ).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(
                            0,
                            0,
                            0,
                            resources.getDimensionPixelSize(R.dimen.margin_horizontal_text_field)
                        )
                    }
                }
            )

            addView(
                AndesTextView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(
                            0,
                            resources.getDimensionPixelSize(R.dimen.margin_horizontal_text_field),
                            0,
                            resources.getDimensionPixelSize(R.dimen.margin_horizontal_text_field)
                        )
                    }
                    text = "Modal custom with views created by code"
                    style = AndesTextViewStyle.TitleM
                    setTextColor(AndesTextViewColor.Primary)
                }
            )
            addView(
                AndesMessage(
                    context,
                    AndesMessageHierarchy.LOUD,
                    AndesMessageType.SUCCESS,
                    "Use this to pass any view you want from code",
                    "Code created",
                    false,
                    null,
                    null
                ).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(
                            0,
                            0,
                            0,
                            resources.getDimensionPixelSize(R.dimen.margin_horizontal_text_field)
                        )
                    }
                }
            )
            addView(
                AndesTextView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(
                            0,
                            0,
                            0,
                            resources.getDimensionPixelSize(R.dimen.margin_horizontal_text_field)
                        )
                    }
                    text = "You can use our AndesTextView to pass text with clickable links"
                    style = AndesTextViewStyle.BodyL
                    bodyLinks = AndesBodyLinks(
                        links = listOf(
                            AndesBodyLink(48, 63)
                        ),
                        listener = { _ ->
                            Toast.makeText(
                                context.applicationContext,
                                "Click en link",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                    setTextColor(AndesTextViewColor.Secondary)
                }
            )
            addView(
                AndesMessage(
                    context,
                    AndesMessageHierarchy.QUIET,
                    AndesMessageType.NEUTRAL,
                    "You can also use this modal with an xml-based view by inflating it and passing it into the same builder",
                    "Also:",
                    false,
                    null,
                    null
                ).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(
                            0,
                            0,
                            0,
                            resources.getDimensionPixelSize(R.dimen.margin_horizontal_text_field)
                        )
                    }
                }
            )
        }
    }

    fun provideCustomViewFromXml(context: Context) = LayoutInflater.from(context)
        .inflate(R.layout.andesui_dynamic_modal_card_custom_view_example, null, false)
}