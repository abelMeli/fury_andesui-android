package com.mercadolibre.android.andesui.demoapp.components.datepicker

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mercadolibre.android.andesui.datepicker.AndesDatePicker
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticDatepickerBinding
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DatePickerShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_datepicker)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf(
            AndesuiStaticDatepickerBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addStaticPage(adapter.views[0])
    }

    private fun addStaticPage(container: View) {
        val binding = AndesuiStaticDatepickerBinding.bind(container)
        val datepicker = binding.andesDatePicker
        val btnSend = binding.btnSendMinMaxDate
        val btnReset = binding.btnReset
        val btnSendDate = binding.btnSendDate
        val inputMinDate = binding.andesTextfieldMinDate
        val inputMaxDate = binding.andesTextfieldMaxDate
        val inputSetDate = binding.andesTextfieldSetDate
        datepicker.setupButtonVisibility(false)
        datepicker.setupButtonText("Aplicar")

        fun convertStringToDate(time: String, format: String): Date {
            val format = SimpleDateFormat(format)
            return format.parse(time)
        }

        fun isValid(time: String, format: String): Boolean {
            val df = SimpleDateFormat(format)
            df.isLenient = false
            return try {
                df.parse(time)
                true
            } catch (e: ParseException) {
                false
            }
        }

        btnSend.setOnClickListener() {
            datepicker.clearMinMaxDate()
            val setterMax: String? = inputMaxDate.text?.trim()
            val setterMin: String? = inputMinDate.text?.trim()
            if (setterMax != null && setterMax.isNotEmpty() && isValid(setterMax, "dd/MM/yyyy")) {
                datepicker.setupMaxDate(convertStringToDate(setterMax, "dd/MM/yyyy").time)
            } else {
                Toast.makeText(applicationContext, "la fecha maxima no es una fecha valida", Toast.LENGTH_SHORT).show()
            }
            if (setterMin != null && setterMin.isNotEmpty() && isValid(setterMin, "dd/MM/yyyy")) {
                datepicker.setupMinDate(convertStringToDate(setterMin, "dd/MM/yyyy").time)
            } else {
                Toast.makeText(applicationContext, "la fecha minima no es una fecha valida", Toast.LENGTH_SHORT).show()
            }
        }

        btnReset.setOnClickListener() {
            datepicker.clearMinMaxDate()
        }

        btnSendDate.setOnClickListener() {
            val setterDate: String? = inputSetDate.text?.trim()
            if (setterDate != null && setterDate.isNotEmpty() && isValid(setterDate, "dd/MM/yyy")) {

                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, -1)

                val format = SimpleDateFormat("dd/MM/yyyy")
                val date = format.parse(setterDate)

                datepicker.selectDate(date.time)
            } else {
                Toast
                    .makeText(this, "La fecha no es una fecha valida", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        datepicker.setDateAppearance(R.style.Andes_CalendarDateDemo)
        datepicker.setWeekDayAppearance(R.style.Andes_CalendarDateWeekDemo)

        datepicker.setDateListener(object : AndesDatePicker.ApplyDatePickerClickListener {
            override fun onDateApply(date: Calendar) {
                val dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT)
                val formattedDate = dateFormatter.format(date.time)
                Toast.makeText(applicationContext, formattedDate, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
