package com.mercadolibre.android.andesui.datepicker

import android.content.Context
import android.os.Build
import android.widget.CalendarView
import androidx.test.core.app.ApplicationProvider
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.datepicker.factory.AndesDatePickerAttrs
import com.mercadolibre.android.andesui.datepicker.factory.AndesDatePickerConfigurationFactory
import com.mercadolibre.android.andesui.datepicker.startofweek.AndesDatePickerStartOfWeek
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar
import java.util.Date

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesDatePickerTest {
    private val configFactory = Mockito.spy(AndesDatePickerConfigurationFactory)
    private lateinit var attrs: AndesDatePickerAttrs
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig
            .newBuilder(context)
            // other setters
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
    }

    @Test
    fun `attr btn enabled`() {
        attrs = AndesDatePickerAttrs(
            andesDatePickerText = "aplicar",
            andesDatePickerMinDate = "01/01/2020",
            andesDatePickerMaxDate = "31/12/2020",
            andesBtnVisibility = true
        )
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.text, "aplicar")
        Assert.assertEquals(config.minDate, "01/01/2020")
        Assert.assertEquals(config.maxDate, "31/12/2020")
        config.applyButtonVisibility?.let { Assert.assertTrue(it) }
    }

    @Test
    fun `attr btn disabled`() {
        attrs = AndesDatePickerAttrs(
            andesDatePickerText = "aplicar",
            andesDatePickerMinDate = "01/01/2020",
            andesDatePickerMaxDate = "31/12/2020",
            andesBtnVisibility = false
        )
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.text, "aplicar")
        Assert.assertEquals(config.minDate, "01/01/2020")
        Assert.assertEquals(config.maxDate, "31/12/2020")
        Assert.assertFalse(config.applyButtonVisibility!!)
    }

    @Test
    fun testAndesDatePickerMinDate() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -15)
        val oldDate = calendar.time.time
        val andesDatePicker = AndesDatePicker(context)

        andesDatePicker.setupMinDate(oldDate)
        Assert.assertEquals(andesDatePicker.calendarView().minDate, oldDate)
    }

    @Test
    fun testAndesDatePickerMinDateGreaterMaxDate() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, +3)
        val futureDate = calendar.time
        val nowDate = Date()
        val andesDatePicker = AndesDatePicker(context)
        andesDatePicker.setupMaxDate(nowDate.time)
        andesDatePicker.setupMinDate(futureDate.time)

        Assert.assertEquals(andesDatePicker.calendarView().maxDate, nowDate.time)
        Assert.assertNotEquals(andesDatePicker.calendarView().minDate, nowDate.time)
    }

    @Test
    fun testAndesDatePickerMaxDateGreaterMinDate() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, +3)
        val futureDate = calendar.time
        val nowDate = Date()
        val andesDatePicker = AndesDatePicker(context)
        andesDatePicker.setupMaxDate(futureDate.time)
        andesDatePicker.setupMinDate(nowDate.time)

        Assert.assertEquals(andesDatePicker.calendarView().minDate, nowDate.time)
        Assert.assertNotEquals(andesDatePicker.calendarView().maxDate, nowDate.time)
    }

    @Test
    fun testAndesDatePickerSetSelectedDate() {
        val datePicker = AndesDatePicker(context)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -4)
        datePicker.selectDate(calendar.timeInMillis)
        Assert.assertEquals(calendar.time.time, datePicker.calendarView().date)
    }

    @Test
    fun testAndesDatePickerSetSelectedDateMinDate() {
        val datePicker = AndesDatePicker(context)
        val selectDate = Calendar.getInstance()
        selectDate.add(Calendar.DATE, -4)

        val minDate = Calendar.getInstance()
        minDate.add(Calendar.DATE, -2)

        datePicker.setupMinDate(minDate.time.time)
        datePicker.selectDate(selectDate.timeInMillis)
        Assert.assertNotEquals(selectDate.time.time, datePicker.calendarView().date)
    }

    @Test
    fun testAndesDatePickerSetSelectedDateMaxDate() {
        val datePicker = AndesDatePicker(context)
        val selectDate = Calendar.getInstance()
        selectDate.add(Calendar.DATE, +10)

        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.DATE, +5)

        datePicker.setupMaxDate(maxDate.time.time)
        datePicker.selectDate(selectDate.timeInMillis)
        Assert.assertNotEquals(selectDate.time.time, datePicker.calendarView().date)
    }

    @Test
    fun testAndesDatePickerChangeStartOfWeekToSunday() {
        val datePicker = AndesDatePicker(context)
        datePicker.calendarView().firstDayOfWeek = 2
        Assert.assertEquals(2, datePicker.calendarView().firstDayOfWeek)
        datePicker.startOfWeek = AndesDatePickerStartOfWeek.SUNDAY
        Assert.assertEquals(1, datePicker.calendarView().firstDayOfWeek)
    }

    @Test
    fun testAndesDatePickerChangeStartOfWeekToMonday() {
        val datePicker = AndesDatePicker(context)
        datePicker.calendarView().firstDayOfWeek = 1
        Assert.assertEquals(1, datePicker.calendarView().firstDayOfWeek)
        datePicker.startOfWeek = AndesDatePickerStartOfWeek.MONDAY
        Assert.assertEquals(2, datePicker.calendarView().firstDayOfWeek)
    }

    @Test
    fun testAndesDatePickerGetSundayAsStartOfWeek() {
        val datePicker = AndesDatePicker(context)
        datePicker.calendarView().firstDayOfWeek = 1
        Assert.assertEquals(AndesDatePickerStartOfWeek.SUNDAY, datePicker.startOfWeek)
    }

    @Test
    fun testAndesDatePickerGetMondayAsStartOfWeek() {
        val datePicker = AndesDatePicker(context)
        datePicker.calendarView().firstDayOfWeek = 2
        Assert.assertEquals(AndesDatePickerStartOfWeek.MONDAY, datePicker.startOfWeek)
    }

    private fun AndesDatePicker.calendarView(): CalendarView = findViewById(R.id.calendarView)
}
