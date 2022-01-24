package com.mercadolibre.android.andesui.demoapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

private const val HOME = "https://company-161429.frontify.com/d/kxHCRixezmfK"
private const val COMPONENT_PREFIX = "$HOME/n-a#/components"
private const val FOUNDATIONS_PREFIX = "$HOME/n-a#/foundations"

internal enum class AndesSpecs(val link: String) {
    HOME_PAGE(HOME),
    CARD("$COMPONENT_PREFIX/card-1567452592"),
    CAROUSEL("$COMPONENT_PREFIX/carousel"),
    CHECKBOX("$COMPONENT_PREFIX/checkbox"),
    FLOATINGMENU("$COMPONENT_PREFIX/floating-menu"),
    LIST("$COMPONENT_PREFIX/list-1584477775"),
    TAG("$COMPONENT_PREFIX/tag-1575576234"),
    BADGE("$COMPONENT_PREFIX/badge"),
    BUTTON("$COMPONENT_PREFIX/button-1584453489"),
    MESSAGE("$COMPONENT_PREFIX/message"),
    RADIOBUTTON("$COMPONENT_PREFIX/radio-button"),
    PROGRESS_INDICATOR("$COMPONENT_PREFIX/progress-indicator"),
    TOOLTIP("$COMPONENT_PREFIX/tooltip"),
    TEXTFIELD("$COMPONENT_PREFIX/textfield"),
    THUMBNAIL("$COMPONENT_PREFIX/thumbnail-1589997379"),
    SWITCH("$COMPONENT_PREFIX/switch"),
    TEXTVIEW("$FOUNDATIONS_PREFIX/typography"),
    SLIDER("$COMPONENT_PREFIX/slider")
}

internal fun launchSpecs(context: Context, specs: AndesSpecs) {
    startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(specs.link)), null)
}
