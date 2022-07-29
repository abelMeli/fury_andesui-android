package com.mercadolibre.android.andesui.demoapp.components.message

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.mercadolibre.android.andesui.bullet.AndesBullet
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicMessageBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticMessageBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.message.hierarchy.AndesMessageHierarchy
import com.mercadolibre.android.andesui.message.type.AndesMessageType
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
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf(
                AndesuiDynamicMessageBinding.inflate(layoutInflater).root,
                AndesuiStaticMessageBinding.inflate(layoutInflater).root
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

    @Suppress("MagicNumber", "LongMethod")
    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicMessageBinding.bind(container)
        val hierarchySpinner = binding.hierarchySpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.hierarchy_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hierarchySpinner.adapter = adapter
        }

        val typeSpinner = binding.simpleTypeSpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_textfield_state_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeSpinner.adapter = adapter
        }

        val thumbnailSpinner = binding.thumbnailSpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_thumbnail_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            thumbnailSpinner.adapter = adapter
        }

        val dismissableCheckbox = binding.dismissableCheckbox
        val bulletCheckbox = binding.bulletCheckbox
        val bodyText = binding.bodyText
        val titleText = binding.titleText
        val primaryActionText = binding.primaryActionText
        val secondaryActionText = binding.secondaryActionText
        val linkActionText = binding.linkActionText
        val changeMessage = binding.message
        val headingCheckbox = binding.headingCheckbox

        val links = listOf(
                AndesBodyLink(4, 11),
                AndesBodyLink(60, 66),
                AndesBodyLink(79, 122),
                AndesBodyLink(50, 40)
        )

        changeMessage.bodyLinks = (AndesBodyLinks(
                links,
                listener = {
                    Toast.makeText(applicationContext, "Click at body link: $it", Toast.LENGTH_SHORT).show()
                }
        ))

        binding.changeButton.setOnClickListener {
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
            changeMessage.a11yTitleIsHeader = headingCheckbox.status == AndesCheckboxStatus.SELECTED

            if (primaryActionText.text.toString().isNotEmpty()) {
                changeMessage.setupPrimaryAction(
                        primaryActionText.text.toString(),
                        View.OnClickListener {
                            Toast.makeText(applicationContext, "Primary onClick", Toast.LENGTH_SHORT).show()
                        }
                )
                changeMessage.hideLinkAction()
            } else {
                changeMessage.hidePrimaryAction()
            }

            if (dismissableCheckbox.status == AndesCheckboxStatus.SELECTED) {
                changeMessage.setupDismissableCallback(
                        View.OnClickListener {
                            Toast.makeText(applicationContext, "Dismiss onClick", Toast.LENGTH_LONG).show()
                        }
                )
            }

            if (secondaryActionText.text.toString().isNotEmpty()) {
                when {
                    primaryActionText.text.toString() != "" -> {
                        changeMessage.setupSecondaryAction(
                                secondaryActionText.text.toString(),
                                View.OnClickListener {
                                    Toast.makeText(applicationContext, "Secondary onClick", Toast.LENGTH_SHORT).show()
                                }
                        )
                    }
                    else -> {
                        Toast.makeText(
                            applicationContext,
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
                                    Toast.makeText(applicationContext, "link onClick", Toast.LENGTH_SHORT).show()
                                }
                        )
                    }
                    else -> {
                        Toast.makeText(
                            applicationContext,
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
                val secondBulletLinks = listOf(
                    AndesBodyLink(0, 26),
                    AndesBodyLink(38, 44)
                )
                val bodyLinksForSecondBullet = AndesBodyLinks(
                    secondBulletLinks,
                    listener = {
                        Toast.makeText(applicationContext, "Click at body link: $it", Toast.LENGTH_SHORT).show()
                    }
                )
                val bullets = listOf(
                    AndesBullet("Bullet 1 example.", null),
                    AndesBullet(
                        "Bullet 2 Multiline example with simple dummy text of the printing and tysetting industry. Lorem impsum.",
                        bodyLinksForSecondBullet
                    ),
                    AndesBullet("Bullet 3 example.", null)
                )
                changeMessage.bullets = bullets
            } else {
                changeMessage.bullets = null
            }

            changeMessage.visibility = View.VISIBLE
        }
    }

    private fun addStaticPage(container: View) {
        val binding = AndesuiStaticMessageBinding.bind(container)
        binding.andesuiDemoappAndesSpecsMessage.setOnClickListener {
            launchSpecs(it.context, AndesSpecs.MESSAGE)
        }

        binding.messageNeutralFirst.setupPrimaryAction(
            container.context.resources.getString(R.string.andes_message_static_action_first_neutral),
            View.OnClickListener {
                Toast.makeText(applicationContext, "Reenviado!", Toast.LENGTH_SHORT).show()
            }
        )

        binding.messageNeutralThird.setupPrimaryAction(
            container.context.resources.getString(R.string.andes_message_static_action_first_neutral),
            View.OnClickListener {
                Toast.makeText(applicationContext, "Click en primary action", Toast.LENGTH_SHORT).show()
            }
        )

        binding.messageSuccessFirst
            .setupLinkAction(
                container.context.resources.getString(R.string.andes_message_static_action_link_success),
                View.OnClickListener {
                    Toast.makeText(applicationContext, "Click en link", Toast.LENGTH_SHORT).show()
                }
            )

        binding.messageSuccessSecond.setupLinkAction(
            container.context.resources.getString(R.string.andes_message_static_action_link_success),
            View.OnClickListener {
                Toast.makeText(applicationContext, "Click en link", Toast.LENGTH_SHORT).show()
            }
        )

        binding.messageWarningSecond.setupPrimaryAction(
            container.context.resources.getString(R.string.andes_message_static_action_first_warning),
            View.OnClickListener {
                Toast.makeText(applicationContext, "Click en primary action", Toast.LENGTH_SHORT).show()
            }
        )

        binding.messageWarningThird.setupPrimaryAction(
            container.context.resources.getString(R.string.andes_message_static_action_first_warning),
            View.OnClickListener {
                Toast.makeText(applicationContext, "Click en primary action", Toast.LENGTH_SHORT).show()
            }
        )

        binding.messageWarningSecond.setupSecondaryAction(
            container.context.resources.getString(R.string.andes_message_static_action_second_warning),
            View.OnClickListener {
                Toast.makeText(applicationContext, "Click en secondary action", Toast.LENGTH_SHORT).show()
            }
        )

        binding.messageWarningThird.setupSecondaryAction(
            container.context.resources.getString(R.string.andes_message_static_action_second_warning),
            View.OnClickListener {
                Toast.makeText(applicationContext, "Click en secondary action", Toast.LENGTH_SHORT).show()
            }
        )

        val links = listOf(
            AndesBodyLink(firstMessageLink.first, firstMessageLink.second),
            AndesBodyLink(secondMessageLink.first, secondMessageLink.second)
        )

        binding.messageErrorFirst.bodyLinks = (AndesBodyLinks(
            links,
            listener = {
                Toast.makeText(applicationContext, "Click at body link: $it", Toast.LENGTH_SHORT).show()
            }
        ))

        binding.messageErrorSecond.bodyLinks = (AndesBodyLinks(
            links,
            listener = {
                Toast.makeText(applicationContext, "Click at body link: $it", Toast.LENGTH_SHORT).show()
            }
        ))
    }

    companion object {
        private val firstMessageLink = 11 to 20
        private val secondMessageLink = 131 to 151
    }
}
