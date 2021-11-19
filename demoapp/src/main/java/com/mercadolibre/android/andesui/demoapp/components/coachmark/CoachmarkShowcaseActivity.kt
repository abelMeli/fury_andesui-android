package com.mercadolibre.android.andesui.demoapp.components.coachmark

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmark
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStep
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStyle
import com.mercadolibre.android.andesui.coachmark.view.CoachmarkView
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator

@SuppressWarnings("MaxLineLength")
class CoachmarkShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_coachmark)

    private fun initViewPager() {
        val inflater = LayoutInflater.from(this)
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
                inflater.inflate(R.layout.andesui_static_coachmark, null, false)
        ))
    }

    private fun attachIndicator() {
        val indicator = findViewById<PageIndicator>(R.id.page_indicator)
        indicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addStaticPage(adapter.views[0])
    }

    @SuppressLint("SetTextI18n", "LongMethod")
    private fun addStaticPage(container: View) {

        container.findViewById<TextView>(R.id.texto).text = "Texto a resaltar"

        container.findViewById<TextView>(R.id.textoLargo).text = "Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsøum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum "

        container.findViewById<AndesButton>(R.id.actionButton).text = "Empezar CoachMark"
        container.findViewById<TextView>(R.id.textoAbajo).text = "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum"

        val stepsNewCoachmark = ArrayList<AndesWalkthroughCoachmarkStep>()

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep(
                "Primer titulo",
                "Resaltamos el primer texto",
                "Siguiente",
                container.findViewById<TextView>(R.id.texto),
                AndesWalkthroughCoachmarkStyle.RECTANGLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Segundo titulo",
                "Probando el circulo magico con flecha abajo a la izquierda Probando el " +
                        "circulo magico con flecha abajo a la izquierda Probando el circulo magico " +
                        "con flecha abajo a la izquierda",
                "Siguiente",
                container.findViewById<ImageView>(R.id.circleAdd),
                AndesWalkthroughCoachmarkStyle.CIRCLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Tercer titulo ",
                "Resaltamos el primer texto",
                "Siguiente",
                container.findViewById<TextView>(R.id.texto),
                AndesWalkthroughCoachmarkStyle.RECTANGLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Cuarto titulo ",
                "Probando el circulo magico con flecha abajo a la derecha",
                "Siguiente",
                container.findViewById<ImageView>(R.id.circleRight),
                AndesWalkthroughCoachmarkStyle.CIRCLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Quinto titulo ",
                "Resaltamos el texto largo Resaltamos el texto largo Resaltamos el texto largo " +
                        "Resaltamos el texto largo Resaltamos el texto largo Resaltamos el texto largo " +
                        "Resaltamos el texto largo Resaltamos el texto largo Resaltamos el texto largo " +
                        "Resaltamos el texto largo Resaltamos el texto largo",
                "Siguiente",
                container.findViewById<TextView>(R.id.textoLargo),
                AndesWalkthroughCoachmarkStyle.RECTANGLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Sexto titulo ",
                "Si vemos esto es porque scrolleo al fin y estamos al final del coachmark ;)",
                "Siguiente",
                container.findViewById<TextView>(R.id.textoAbajo),
                AndesWalkthroughCoachmarkStyle.RECTANGLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Septimo titulo ",
                "Probando el circulo magico con flecha arriba a la izquierda",
                "Siguiente",
                container.findViewById<ImageView>(R.id.circleAdd),
                AndesWalkthroughCoachmarkStyle.CIRCLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Octavo titulo ",
                "Probando el circulo magico con flecha arriba a la derecha Probando el " +
                        "circulo magico con flecha arriba a la derecha Probando el circulo magico con " +
                        "flecha arriba a la derecha Probando el circulo magico con flecha arriba a la derecha",
                "Siguiente",
                container.findViewById<ImageView>(R.id.circleRight),
                AndesWalkthroughCoachmarkStyle.CIRCLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Noveno titulo ",
                "Probando scroll hacia arriba", "Siguiente",
                container.findViewById<TextView>(R.id.textoLargo),
                AndesWalkthroughCoachmarkStyle.RECTANGLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Decimo titulo ",
                "Esto sigue en prueba y esta bueno que funcione bien",
                "Siguiente",
                container.findViewById<AndesButton>(R.id.actionButton),
                AndesWalkthroughCoachmarkStyle.RECTANGLE)
        )

        container.findViewById<AndesButton>(R.id.actionButton).setOnClickListener {
            val scrollView = container.findViewById<NestedScrollView>(R.id.scrollview)
            CoachmarkView.Builder(this, AndesWalkthroughCoachmark(stepsNewCoachmark, scrollView) {
                println("Entro al despues de cerrar")
            }).build()
        }
    }
}
