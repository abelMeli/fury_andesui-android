# Andes UI

Andes UI is a library which contains all the UI components defined with its functionality and tries to generate a unified design language along Mercado Libre.

From IT (native + web) in conjunction with UX, we are looking forward to creating conventions to refer to each component so everybody could understand and talks about the same.

### Live examples
Install our Showcase App via Meli Store and enjoy it!

![Android Showcase Download.png](resources/android-showcase-meli-store-download.png)

### New! WebView live examples
#### Access to the live example of the WebView version for each component.
Navigate to the desired component screen, and click in the "WebView" button, in the action bar.

![Storybook Access](resources/storybook-access.png)

Inside the web storybook you can access to the Addons section, where you can modify the different properties the component allows (change the text value, test the available colors, and more)

![Storybook Addons](resources/storybook-addons.png)

You can also navigate to the sidebar, where you can find the different variants for the component. For example, in the button, you can choose between the Default implementation, the Icon implementation, the Progress implementation, and more.

![Storybook Sidebar](resources/storybook-sidebar.png)

#### Open any storybook web component in a WebView
Navigate to any component present in any version of the storybook, including forked versions!

You only need to launch a deeplink with this structure:

"meli://andes/storybook?url=[https://www.your-link-to-the-storybook.com]()"

Example: install the demoapp, run it through the Android Studio Emulator and launch the following deeplink from the command line:

```console
adb shell am start -a android.intent.action.VIEW -d "meli://andes/storybook?url=https://tnovas.github.io/fury_frontend-andes-ui/?path=/docs/documentation-button--default-story"
```

## Definitions

* Monomodule
* Each class has the prefix "Andes" or "andes" depending on the use case.
* Well-defined and intuitive architecture.
* Has custom attributes that allows the devs specify the component they would like to use.
* Components can be used by XML or programmatically.
* Compatible with Jetpack Compose.

## Contributing
See the [CONTRIBUTING](https://github.com/mercadolibre/fury_andesui-android/blob/master/CONTRIBUTING.md)

## Accessibility Playground
* [A11y Playground](a11y-playground/A11yPlayground.md)

## Setup
See the [Setup page](Setup.md) to apply production styles in Mercado Libre & Mercado Pago modules. In case your module belongs to other app check how is configured in the main proyect. To setup your app module manually read Fonts & Colors section.

## Fonts & Colors
See the [Fonts & Colors page](Fonts&Colors.md) to be aware of how to use the proper Colors and Fonts with AndesUI.

## Components
* [AndesAutosuggest](autosuggest/AndesAutosuggest.md)
* [AndesBadge](badge/AndesBadge.md)
* [AndesBadgeIconPill](badge/AndesBadgeIconPill.md)
* [AndesBadgePill](badge/AndesBadgePill.md)
* [AndesButton](button/AndesButton.md)
* [AndesCarousel](carousel/AndesCarousel.md)
* [AndesCheckbox](checkbox/AndesCheckbox.md)
* [AndesCoachMark](coachmark/AndesCoachMark.md)
* [AndesDatePicker](datepicker/AndesDatePicker.md)
* [AndesDropdown](dropdown/AndesDropdown.md)
* [AndesFeedbackScreen](feedbackscreen/AndesFeedbackScreen.md)
* [AndesFloatingMenu](floatingmenu/AndesFloatingMenu.md)
* [AndesList](list/AndesList.md)
* [AndesMessage](message/AndesMessage.md)
* [AndesModal](modal/AndesModal.md)
* [AndesMoneyAmount](moneyamount/AndesMoneyAmount.md)
* [AndesMoneyAmountCombo](moneyamount/AndesMoneyAmountCombo.md)
* [AndesMoneyAmountDiscount](moneyamount/AndesMoneyAmountDiscount.md)
* [AndesSnackbar](snackbar/AndesSnackbar.md)
* [AndesSwitch](switch/AndesSwitch.md)
* [AndesTabs](tabs/AndesTabs.md)
* [AndesTag](tag/AndesTag.md)
* [AndesTextfield](textfield/AndesTextfield.md)
* [AndesTextView](textview/AndesTextView.md)
* [AndesThumbnail](thumbnail/AndesThumbnail.md)
* [AndesThumbnailBadge](thumbnail/AndesThumbnailBadge.md)
* [AndesTimePicker](timepicker/AndesTimePicker.md)
* [AndesTooltip](tooltip/AndesTooltip.md)
