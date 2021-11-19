package com.mercadolibre.android.andesui.demoapp.components.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.mercadolibre.android.andesui.bullet.AndesBullet
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.message.AndesMessage
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.message.hierarchy.AndesMessageHierarchy
import com.mercadolibre.android.andesui.message.type.AndesMessageType
import com.mercadolibre.android.andesui.textfield.AndesTextarea
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

class MessageShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_message)

    private fun initViewPager() {
        val inflater = LayoutInflater.from(this)
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
                inflater.inflate(R.layout.andesui_dynamic_message, null, false),
                inflater.inflate(R.layout.andesui_static_message, null, false)
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

    @Suppress("MagicNumber", "LongMethod")
    private fun addDynamicPage(container: View) {
        val hierarchySpinner: Spinner = container.findViewById(R.id.hierarchy_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.hierarchy_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hierarchySpinner.adapter = adapter
        }

        val typeSpinner: Spinner = container.findViewById(R.id.simple_type_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_textfield_state_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeSpinner.adapter = adapter
        }

        val thumbnailSpinner: Spinner = container.findViewById(R.id.thumbnail_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_thumbnail_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            thumbnailSpinner.adapter = adapter
        }

        val dismissableCheckbox = container.findViewById<AndesCheckbox>(R.id.dismissable_checkbox)

        val bulletCheckbox = container.findViewById<AndesCheckbox>(R.id.bullet_checkbox)

        val bodyText = container.findViewById<AndesTextarea>(R.id.body_text)

        val titleText = container.findViewById<AndesTextfield>(R.id.title_text)

        val primaryActionText = container.findViewById<AndesTextfield>(R.id.primary_action_text)

        val secondaryActionText = container.findViewById<AndesTextfield>(R.id.secondary_action_text)

        val linkActionText = container.findViewById<AndesTextfield>(R.id.link_action_text)

        val changeButton = container.findViewById<AndesButton>(R.id.change_button)
        val changeMessage = container.findViewById<AndesMessage>(R.id.message)

        val links = listOf(
                AndesBodyLink(4, 11),
                AndesBodyLink(60, 66),
                AndesBodyLink(79, 122),
                AndesBodyLink(50, 40)
        )

        changeMessage.bodyLinks = (AndesBodyLinks(
                links,
                listener = {
                    Toast.makeText(this, "Click at body link: $it", Toast.LENGTH_SHORT).show()
                }
        ))

        changeButton.setOnClickListener {
            if (bodyText.text.toString().isEmpty()) {
                bodyText.state = AndesTextfieldState.ERROR
                bodyText.helper = "Message cannot be visualized with null body"
                bodyText.requestFocus()
                return@setOnClickListener
            } else {
                bodyText.state = AndesTextfieldState.IDLE
                bodyText.helper = null
                changeMessage.body = bodyText.text.toString()
            }

            changeMessage.isDismissable = dismissableCheckbox.status == AndesCheckboxStatus.SELECTED
            changeMessage.title = titleText.text.toString()
            changeMessage.type = AndesMessageType.fromString(typeSpinner.selectedItem.toString())
            changeMessage.hierarchy = AndesMessageHierarchy.fromString(hierarchySpinner.selectedItem.toString())
            changeMessage.bodyLinks = null
            changeMessage.bullets = null

            if (primaryActionText.text.toString().isNotEmpty()) {
                changeMessage.setupPrimaryAction(
                        primaryActionText.text.toString(),
                        View.OnClickListener {
                            Toast.makeText(this, "Primary onClick", Toast.LENGTH_SHORT).show()
                        }
                )
                changeMessage.hideLinkAction()
            } else {
                changeMessage.hidePrimaryAction()
            }

            if (dismissableCheckbox.status == AndesCheckboxStatus.SELECTED) {
                changeMessage.setupDismissableCallback(
                        View.OnClickListener {
                            Toast.makeText(this, "Dismiss onClick", Toast.LENGTH_LONG).show()
                        }
                )
            }

            if (secondaryActionText.text.toString().isNotEmpty()) {
                when {
                    primaryActionText.text.toString() != "" -> {
                        changeMessage.setupSecondaryAction(
                                secondaryActionText.text.toString(),
                                View.OnClickListener {
                                    Toast.makeText(this, "Secondary onClick", Toast.LENGTH_SHORT).show()
                                }
                        )
                    }
                    else -> {
                        Toast.makeText(
                                this,
                                "Cannot set a secondary action without a primary one",
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                changeMessage.hideSecondaryAction()
            }

            if (linkActionText.text.toString().isNotEmpty()) {
                when {
                    primaryActionText.text.toString() == "" -> {
                        changeMessage.setupLinkAction(
                                linkActionText.text.toString(),
                                View.OnClickListener {
                                    Toast.makeText(this, "link onClick", Toast.LENGTH_SHORT).show()
                                }
                        )
                    }
                    else -> {
                        Toast.makeText(
                                this,
                                "Cannot set a link action with a primary one",
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                changeMessage.hideLinkAction()
            }

            val thumbnailDrawable = if (thumbnailSpinner.selectedItem.toString() == "With Thumbnail") {
                ResourcesCompat.getDrawable(resources, R.mipmap.andesui_demoapp_ic_launcher, null)
            } else {
                null
            }

            changeMessage.setupThumbnail(thumbnailDrawable)

            if (bulletCheckbox.status == AndesCheckboxStatus.SELECTED) {
                val bulletSpans = listOf(
                    AndesBullet(8, 19),
                    AndesBullet(19, 31)
                )
                changeMessage.bullets = bulletSpans
            } else {
                changeMessage.bullets = null
            }

            changeMessage.visibility = View.VISIBLE
        }
    }

    private fun addStaticPage(container: View) {
        container.findViewById<AndesButton>(R.id.andesui_demoapp_andes_specs_message).setOnClickListener {
            launchSpecs(it.context, AndesSpecs.MESSAGE)
        }

        container.findViewById<AndesMessage>(R.id.message_neutral_first)
                .setupPrimaryAction(
                        container.context.resources.getString(R.string.andes_message_static_action_first_neutral),
                        View.OnClickListener {
                            Toast.makeText(this, "Reenviado!", Toast.LENGTH_SHORT).show()
                        }
                )

        container.findViewById<AndesMessage>(R.id.message_neutral_third)
            .setupPrimaryAction(
                container.context.resources.getString(R.string.andes_message_static_action_first_neutral),
                View.OnClickListener {
                    Toast.makeText(this, "Click en primary action", Toast.LENGTH_SHORT).show()
                }
            )

        container.findViewById<AndesMessage>(R.id.message_success_first)
            .setupLinkAction(
                container.context.resources.getString(R.string.andes_message_static_action_link_success),
                View.OnClickListener {
                    Toast.makeText(this, "Click en link", Toast.LENGTH_SHORT).show()
                }
            )

        container.findViewById<AndesMessage>(R.id.message_success_second)
            .setupLinkAction(
                container.context.resources.getString(R.string.andes_message_static_action_link_success),
                View.OnClickListener {
                    Toast.makeText(this, "Click en link", Toast.LENGTH_SHORT).show()
                }
            )

        container.findViewById<AndesMessage>(R.id.message_warning_second)
            .setupPrimaryAction(
                container.context.resources.getString(R.string.andes_message_static_action_first_warning),
                View.OnClickListener {
                    Toast.makeText(this, "Click en primary action", Toast.LENGTH_SHORT).show()
                }
            )

        container.findViewById<AndesMessage>(R.id.message_warning_third)
            .setupPrimaryAction(
                container.context.resources.getString(R.string.andes_message_static_action_first_warning),
                View.OnClickListener {
                    Toast.makeText(this, "Click en primary action", Toast.LENGTH_SHORT).show()
                }
            )

        container.findViewById<AndesMessage>(R.id.message_warning_second)
            .setupSecondaryAction(
                container.context.resources.getString(R.string.andes_message_static_action_second_warning),
                View.OnClickListener {
                    Toast.makeText(this, "Click en secondary action", Toast.LENGTH_SHORT).show()
                }
            )

        container.findViewById<AndesMessage>(R.id.message_warning_third)
            .setupSecondaryAction(
                container.context.resources.getString(R.string.andes_message_static_action_second_warning),
                View.OnClickListener {
                    Toast.makeText(this, "Click en secondary action", Toast.LENGTH_SHORT).show()
                }
            )

        val links = listOf(
            AndesBodyLink(firstMessageLink.first, firstMessageLink.second),
            AndesBodyLink(secondMessageLink.first, secondMessageLink.second)
        )

        container.findViewById<AndesMessage>(R.id.message_error_first)
            .bodyLinks = (AndesBodyLinks(
            links,
            listener = {
                Toast.makeText(this, "Click at body link: $it", Toast.LENGTH_SHORT).show()
            }
        ))

        container.findViewById<AndesMessage>(R.id.message_error_second)
            .bodyLinks = (AndesBodyLinks(
            links,
            listener = {
                Toast.makeText(this, "Click at body link: $it", Toast.LENGTH_SHORT).show()
            }
        ))
    }

    companion object {
        private val firstMessageLink = 11 to 20
        private val secondMessageLink = 131 to 151
    }
}
