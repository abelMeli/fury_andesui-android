package com.mercadolibre.android.andesui.demoapp.components.coachmark

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmark
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStep
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStyle
import com.mercadolibre.android.andesui.coachmark.view.CoachmarkView
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticCoachmarkBinding

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
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf(
            AndesuiStaticCoachmarkBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addStaticPage(adapter.views[0])
    }

    @SuppressLint("SetTextI18n", "LongMethod")
    private fun addStaticPage(container: View) {
        val binding = AndesuiStaticCoachmarkBinding.bind(container)
        binding.texto.text = "Texto a resaltar"

        binding.textoLargo.text = "Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsøum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum "

        binding.actionButton.text = "Empezar CoachMark"
        binding.textoAbajo.text = "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum " +
                "Lorem ipsum Lorem ipsum Lorem ipsum"

        val stepsNewCoachmark = ArrayList<AndesWalkthroughCoachmarkStep>()

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep(
                "Primer titulo",
                "Resaltamos el primer texto",
                "Siguiente",
                binding.texto,
                AndesWalkthroughCoachmarkStyle.RECTANGLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Segundo titulo",
                "Probando el circulo magico con flecha abajo a la izquierda Probando el " +
                        "circulo magico con flecha abajo a la izquierda Probando el circulo magico " +
                        "con flecha abajo a la izquierda",
                "Siguiente",
                binding.circleAdd,
                AndesWalkthroughCoachmarkStyle.CIRCLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Tercer titulo ",
                "Resaltamos el primer texto",
                "Siguiente",
                binding.texto,
                AndesWalkthroughCoachmarkStyle.RECTANGLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Cuarto titulo ",
                "Probando el circulo magico con flecha abajo a la derecha",
                "Siguiente",
                binding.circleRight,
                AndesWalkthroughCoachmarkStyle.CIRCLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Quinto titulo ",
                "Resaltamos el texto largo Resaltamos el texto largo Resaltamos el texto largo " +
                        "Resaltamos el texto largo Resaltamos el texto largo Resaltamos el texto largo " +
                        "Resaltamos el texto largo Resaltamos el texto largo Resaltamos el texto largo " +
                        "Resaltamos el texto largo Resaltamos el texto largo",
                "Siguiente",
                binding.textoLargo,
                AndesWalkthroughCoachmarkStyle.RECTANGLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Sexto titulo ",
                "Si vemos esto es porque scrolleo al fin y estamos al final del coachmark ;)",
                "Siguiente",
                binding.textoAbajo,
                AndesWalkthroughCoachmarkStyle.RECTANGLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Septimo titulo ",
                "Probando el circulo magico con flecha arriba a la izquierda",
                "Siguiente",
                binding.circleAdd,
                AndesWalkthroughCoachmarkStyle.CIRCLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Octavo titulo ",
                "Probando el circulo magico con flecha arriba a la derecha Probando el " +
                        "circulo magico con flecha arriba a la derecha Probando el circulo magico con " +
                        "flecha arriba a la derecha Probando el circulo magico con flecha arriba a la derecha",
                "Siguiente",
                binding.circleRight,
                AndesWalkthroughCoachmarkStyle.CIRCLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Noveno titulo ",
                "Probando scroll hacia arriba", "Siguiente",
                binding.textoLargo,
                AndesWalkthroughCoachmarkStyle.RECTANGLE)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Decimo titulo ",
                "Esto sigue en prueba y esta bueno que funcione bien",
                "Siguiente",
                binding.actionButton,
                AndesWalkthroughCoachmarkStyle.RECTANGLE)
        )

        binding.actionButton.setOnClickListener {
            CoachmarkView.Builder(this, AndesWalkthroughCoachmark(stepsNewCoachmark, binding.scrollview) {
                println("Entro al despues de cerrar")
            }).build()
        }
    }
}
