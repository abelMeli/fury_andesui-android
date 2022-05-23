<img src="https://user-images.githubusercontent.com/17315076/100236434-6ecd6680-2f0c-11eb-8c8d-9702b8a5ac01.gif" width="230" height="450"/>

# Introduction
The Andes DatePicker, as its name says, is the DatePicker that this UI library offers.
It can be customized, with action button, without action button.
Agregar gif andes-datepicker1
# How to use
There are two ways to use AndesButton: XML or programmatically. 
## XML
### Example Pill:
```
<com.mercadolibre.android.andesui.datepicker.AndesDatePicker
            android:id="@+id/andesDatePicker"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:andesDatePickerMinDate="25/11/2020"
            app:andesDatePickerText="aplicar"
            app:andesDatePickerMaxDate="30/11/2020"/>
```
### Programmatically
```
 datepicker.setDateListener()
```
 Gets the selected date.

```
 datepicker.setDateAppearance()
```
 Sets the dateAppareance Style

```
 datepicker.setWeekDayAppearance()
```
 Sets the weekDayAppareance Style

```
 datepicker.setupButtonVisibility(false)       
``` 
 Disable the action button

```
 datepicker.setupButtonVisibility(true)
```
 Enable the action button

```
 datepicker.setupButtonText("Aplicar")
```
 Sets the action button text label.

```
 datepicker.setupMinDate(MinDate:Long)
```
 Sets the minimal allowed date.

```
 datepicker.setupMaxDate(MaxDate:Long)
```
 Sets the maximal allowed date.

<img src="https://user-images.githubusercontent.com/17315076/100236434-6ecd6680-2f0c-11eb-8c8d-9702b8a5ac01.gif" width="230" height="450"/>


 Sets which day of the week it should start with.

```
 datepicker.startOfWeek = AndesDatePickerStartOfWeek.SUNDAY
```

<img src="https://user-images.githubusercontent.com/92759355/168887566-f3c28316-b87a-477c-bea6-c7fbd7e88c2f.gif" width="230" height="450"/>
