# Andes template Plugin

This plugin will help you to create a new Andes Component. You only need to specify a name and choose between a few options
and the plugin will create all the needed classes to manage your new component.

## How to install the plugin
- Download the .jar file present in this directory
- Install it entering in Android Studio -> Preferences -> Plugins -> Install Plugin from Disk...
- Restart IDE
- Run Build -> Rebuild project
- The plugin is now ready for use!

## How to use the plugin
- Inside the Android Studio and the Andes UI project, right click in the `components` folder -> New -> Other -> New Andes Component
- Choose a new component name. It should start with "Andes...". Example: for a date picker, the name should be `AndesDatePicker`.
- Choose a new base component name. It should be the component name in lower case, without the Andes prefix. Example: for a date picker, the base component name should be `datepicker`.
- Mark the needed options for the component you will develop. Each check is responsible for creating an inner package with an enum class and an interface for the chosen attribute.
- The package name should be automatically set to `com.mercadolibre.android.andesui`. If this value does not match, you are trying to create a new component in an incorrect place. Verify the selected package.
- The Target Source Set should be automatically set to main. Do not change it.
- Click on Finish
- The main package with all the needed classes is ready to be edited.

## How to contribute to the template plugin
The code used to generate this plugin is in [this repository](https://github.com/mercadolibre/fury_ide-templates-android).
If you have suggestions or find any errors in the generated code, consider sending a PR with the fix into the template repository.
