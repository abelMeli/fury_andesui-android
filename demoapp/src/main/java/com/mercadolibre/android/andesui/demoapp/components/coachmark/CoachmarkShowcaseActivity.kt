package com.mercadolibre.android.andesui.demoapp.components.coachmark

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.mercadolibre.android.andesui.coachmark.model.AndesScrollessWalkthroughCoachmark
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStep
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStyle
import com.mercadolibre.android.andesui.coachmark.view.walkthroughscrolless.CoachmarkScrollessView
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
        binding.texto.text = "Texto a resaltar Texto a resaltar Texto"

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
            "A Primer titulo",
            "Resaltamos el primer texto",
            "Siguiente",
            supportActionBar?.customView?.findViewById(R.id.custom_action_bar_webview_text),
            AndesWalkthroughCoachmarkStyle.MENU_ITEM)
        )

        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep(
            "A Primer titulo",
            "Resaltamos el primer texto",
            "Siguiente",
            baseBinding.andesuiNavBar.navigationIconView,
            AndesWalkthroughCoachmarkStyle.HAMBURGER)
        )


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
                "Resaltamos el texto largo Resaltamos el texto largo Resaltamos el texto largo",
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
            CoachmarkScrollessView.Builder(this, AndesScrollessWalkthroughCoachmark(stepsNewCoachmark, binding.scrollview) {
                println("Entro al despues de cerrar")
            }).build()
        }
    }

    private val Toolbar.navigationIconView: View?
        get() {
            //check if contentDescription previously was set
            val hadContentDescription = !TextUtils.isEmpty(navigationContentDescription)
            val contentDescription = if (hadContentDescription) navigationContentDescription else "navigationIcon"
            navigationContentDescription = contentDescription
            val potentialViews = arrayListOf<View>()
            //find the view based on it's content description, set programatically or with android:contentDescription
            findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
            //Clear content description if not previously present
            if (!hadContentDescription) {
                navigationContentDescription = null
            }
            //Nav icon is always instantiated at this point because calling setNavigationContentDescription ensures its existence
            return potentialViews.firstOrNull()
        }
}
