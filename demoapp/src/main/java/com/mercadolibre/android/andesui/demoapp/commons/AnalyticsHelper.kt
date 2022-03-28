package com.mercadolibre.android.andesui.demoapp.commons

internal class AnalyticsHelper {

    companion object {
        const val whatsNewTrack = "WhatsNew"
        const val specsTrack = "Contribute"
        const val contributeTrack = "Specs"
    }

    private val a11yExamples = mapOf(
        "PlaygroundHomeActivity" to "PlaygroundHome",
        "A11yLiveRegionActivity" to "AccessibilityLiveRegion",
        "A11yTraversalAfterActivity" to "AccessibilityTravelsarAfter",
        "AnnounceForA11yActivity" to "AnnounceForAccessibility",
        "ContentDescriptionActivity" to "ContentDescription",
        "HeadingActivity" to "Heading",
        "ImportantForA11yActivity" to "ImportantForAccessibility",
        "NextFocusActivity" to "NextFocus",
        "SemanticViewsActivity" to "SemanticViews"
    )

    private val components = mapOf(
        "BadgeShowcaseActivity" to "AndesBadge",
        "BottomSheetShowcaseActivity" to "AndesBottomSheet",
        "ButtonShowcaseActivity" to "AndesButton",
        "CardShowcaseActivity" to "AndesCard",
        "CarouselShowcaseActivity" to "AndesCarousel",
        "CheckboxShowcaseActivity" to "AndesCheckbox",
        "CoachmarkShowcaseActivity" to "AndesCoachmark",
        "DatePickerShowcaseActivity" to "AndesDatePicker",
        "DropdownShowcaseActivity" to "AndesDropdown",
        "FloatingMenuShowcaseActivity" to "AndesFloatingMenu",
        "ListShowcaseActivity" to "AndesList",
        "MessageShowcaseActivity" to "AndesMessage",
        "LinearProgressShowcaseActivity" to "AndesLinearProgressIndicator",
        "ProgressShowcaseActivity" to "AndesProgressIndicator",
        "RadioButtonShowcaseActivity" to "AndesRadioButton",
        "SliderShowcaseActivity" to "AndesSlider",
        "SnackbarShowcaseActivity" to "AndesSnackbar",
        "TagShowcaseActivity" to "AndesTag",
        "TextfieldShowcaseActivity" to "AndesTextfield",
        "TextViewShowcaseActivity" to "AndesTextView",
        "ThumbnailShowcaseActivity" to "AndesThumbnail",
        "TimePickerShowcaseActivity" to "AndesTimePicker",
        "TooltipShowcaseActivity" to "AndesTooltip",
        "SwitchShowcaseActivity" to "AndesSwitch",
        "TabsShowcaseActivity" to "AndesTabs",
        "FeedbackScreenShowcaseActivity" to "AndesFeedbackScreenView",
        "MoneyAmountShowcaseActivity" to "AndesMoneyAmount",
        "ButtonGroupShowcaseActivity" to "AndesButtonGroup"
    )

    private val screens = mapOf(
        "BadgeShowcaseActivity" to arrayListOf("/badge/dynamic", "/badge/static"),
        "BottomSheetShowcaseActivity" to arrayListOf("/bottomsheet/dynamic"),
        "ButtonShowcaseActivity" to arrayListOf("/button/dynamic", "/button/dynamicprogress", "/button/static"),
        "CardShowcaseActivity" to arrayListOf("/card/dynamic", "/card/static"),
        "CarouselShowcaseActivity" to arrayListOf("/carousel/dynamic", "/carousel/static"),
        "CheckboxShowcaseActivity" to arrayListOf("/checkbox/dynamic", "/checkbox/static"),
        "CoachmarkShowcaseActivity" to arrayListOf("/coachmark/static"),
        "DatePickerShowcaseActivity" to arrayListOf("/datepicker/static"),
        "DropdownShowcaseActivity" to arrayListOf("/dropdown/dynamic", "/dropdown/static"),
        "FloatingMenuShowcaseActivity" to arrayListOf(
            "/floatingmenu/dynamic",
            "/floatingmenu/static"
        ),
        "ListShowcaseActivity" to arrayListOf("/list/dynamic", "/list/static"),
        "MessageShowcaseActivity" to arrayListOf("/message/dynamic", "/message/static"),
        "ProgressShowcaseActivity" to arrayListOf("/progressindicator/static"),
        "LinearProgressShowcaseActivity" to arrayListOf(
            "/linearprogressindicator/dynamic",
            "/linearprogressindicator/static"
        ),
        "RadioButtonShowcaseActivity" to arrayListOf(
            "/radiobutton/radiobutton/dynamic",
            "/radiobutton/radiobutton/static", "/radiobutton/radiobuttongroup/static"
        ),
        "SliderShowcaseActivity" to arrayListOf(
            "/slider/dynamic",
            "/slider/static"
        ),
        "SnackbarShowcaseActivity" to arrayListOf("/snackbar/dynamic"),
        "TagShowcaseActivity" to arrayListOf(
            "/tag/simple/dynamic",
            "/tag/simple/static",
            "/tag/choice/static"
        ),
        "TextfieldShowcaseActivity" to arrayListOf(
            "/textfield/textfield/dynamic",
            "/textfield/textarea/dynamic",
            "/textfield/textcode/dynamic",
            "/textfield/autosuggest/dynamic",
            "/textfield/static"
        ),
        "TextViewShowcaseActivity" to arrayListOf(
            "/textview/dynamic",
            "/textview/static"
        ),
        "ThumbnailShowcaseActivity" to arrayListOf(
            "/thumbnail/dynamic",
            "/thumbnailbadge/dynamic",
            "/thumbnail/static"
        ),
        "TimePickerShowcaseActivity" to arrayListOf("/timepicker/dynamic", "/timepicker/static"),
        "TooltipShowcaseActivity" to arrayListOf("/tooltip/dynamic", "/tooltip/static"),
        "SwitchShowcaseActivity" to arrayListOf("/switch/dynamic", "/switch/static"),
        "TabsShowcaseActivity" to arrayListOf("/tabs/dynamic", "/tabs/static"),
        "FeedbackScreenShowcaseActivity" to arrayListOf(
            "/feedbackscreen/dynamic",
            "/feedbackscreen/simplesuccess/static",
            "/feedbackscreen/simplewarning/static",
            "/feedbackscreen/simplebody/static",
            "/feedbackscreen/congrats/static"
        ),
        "MoneyAmountShowcaseActivity" to arrayListOf("/moneyamount/dynamic", "/moneyamount/static")
    )

    fun getPath(className: String, position: Int): String? {
        return screens[className]?.getOrNull(position)
    }

    fun getComponentName(className: String): String? {
        return components[className]
    }

    fun getA11yExample(className: String): String? {
        return a11yExamples[className]
    }
}

/**
 * Custom exception to throw when the page is not configured to be tracked
 */
internal class AnalyticsTrackingException(override val message: String?) : RuntimeException(message)
