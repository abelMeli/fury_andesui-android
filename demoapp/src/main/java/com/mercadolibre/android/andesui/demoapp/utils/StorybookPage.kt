package com.mercadolibre.android.andesui.demoapp.utils

import com.mercadolibre.android.andesui.demoapp.BuildConfig

internal enum class StorybookPage(val link: String) {
    HOMEPAGE(HOME),
    ANDESCARD("$HOME/?path=/story/card-card--andes-card"),
    ANDESCAROUSEL("$HOME/?path=/story/carousel-free--andes-component"),
    ANDESCHECKBOX("$HOME/?path=/story/checkbox--andes-checkbox-default"),
    ANDESFLOATINGMENU("$HOME/?path=/story/floating-menu--andes-floating-menu"),
    ANDESLIST("$HOME/?path=/story/list--andes-default-list"),
    ANDESTAG("$HOME/?path=/story/tag--andes-default-tag"),
    ANDESBADGE("$HOME/?path=/story/badge-pill--andes-badge-pill"),
    ANDESBUTTON("$HOME/?path=/story/button-button--andes-button"),
    ANDESMESSAGE("$HOME/?path=/story/message--andes-message-default"),
    ANDESRADIOBUTTON("$HOME/?path=/story/radiobutton--andes-radio-button"),
    ANDESLINEARPROGRESSINDICATOR("$HOME/?path=/story/progress-indicator-linear--progress-indicator-linear-default-story"),
    ANDESPROGRESSINDICATOR("$HOME/?path=/story/progress-indicator-circular--andes-progress-indicator-circular"),
    ANDESTOOLTIP("$HOME/?path=/story/tooltip--andes-tooltip"),
    ANDESTEXTFIELD("$HOME/?path=/story/textfield-textfield--andes-text-field"),
    ANDESTHUMBNAIL("$HOME/?path=/story/thumbnail--andes-thumbnail"),
    ANDESSWITCH("$HOME/?path=/story/switch--andes-switch"),
    ANDESFEEDBACKSCREEN("$HOME/?path=/story/feedbackscreen--andes-feedback-screen"),
    ANDESCOACHMARK("$HOME/?path=/story/coachmarks--andes-coach-marks"),
    ANDESDATEPICKER("$HOME/?path=/story/datepicker--andes-datepicker"),
    ANDESDROPDOWN("$HOME/?path=/story/dropdown--andes-dropdown-form"),
    ANDESSNACKBAR("$HOME/?path=/story/snackbar--andes-snackbar"),
    ANDESTIMEPICKER("$HOME/?path=/story/time-picker--andes-time-picker"),
    ANDESTABS("$HOME/?path=/story/tabs--andes-default-tabs"),
    ANDESMONEYAMOUNT("$HOME/?path=/story/money-amount--andes-money-amount");

    companion object {
        fun getStorybookPage(string: String): StorybookPage = valueOf(string.toUpperCase())
    }
}

private const val HOME = BuildConfig.STORYBOOK_URL
const val COMPONENT_KEY = "component"
const val QUERY_PARAMETER_KEY = "url"
