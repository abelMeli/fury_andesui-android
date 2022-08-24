package com.mercadolibre.android.andesui.coachmark.presenter

import android.graphics.Rect
import androidx.core.widget.NestedScrollView
import android.view.View
import android.view.ViewGroup
import com.mercadolibre.android.andesui.coachmark.model.AndesScrollessWalkthroughCoachmark
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmark
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStep
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStyle
import com.mercadolibre.android.andesui.coachmark.model.WalkthroughMessagePosition
import com.mercadolibre.android.andesui.coachmark.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class CoachmarkPresenterTest {

    @Mock
    private val view = Mockito.mock(CoachmarkViewInterface::class.java)
    @Mock
    private val viewReference = Mockito.mock(View::class.java)
    @Mock
    private val scrollView = Mockito.mock(NestedScrollView::class.java)

    lateinit var coachmarkModel: AndesWalkthroughCoachmark

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val stepsNewCoachmark = ArrayList<AndesWalkthroughCoachmarkStep>()
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Primer titulo", "Resaltamos el primer texto",
                        "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.RECTANGLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Segundo titulo",
                "Probando el circulo magico con flecha abajo a la izquierda " +
                        "Probando el circulo magico con flecha abajo a la izquierda Probando" +
                        " el circulo magico con flecha abajo a la izquierda",
                "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.CIRCLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Tercer titulo ", "Resaltamos el primer texto",
                "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.RECTANGLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Cuarto titulo ",
                "Probando el circulo magico con flecha abajo a la derecha",
                "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.CIRCLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Quinto titulo ", "Resaltamos el texto largo " +
                "Resaltamos el texto largo Resaltamos el texto largo Resaltamos el texto largo Resaltamos " +
                "el texto largo Resaltamos el texto largo Resaltamos el texto largo Resaltamos el texto largo Resaltamos " +
                "el texto largo Resaltamos el texto largo Resaltamos el texto largo",
                "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.RECTANGLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Sexto titulo ", "Si vemos esto es porque scrolleo" +
                " al fin y estamos al final del coachmark ;)",
                "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.RECTANGLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Septimo titulo ",
                "Probando el circulo magico con flecha arriba a la izquierda",
                "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.CIRCLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Octavo titulo ",
                "Probando el circulo magico con flecha arriba a la derecha " +
                        "Probando el circulo magico con flecha arriba a la derecha Probando el circulo magico con " +
                        "flecha arriba a la derecha Probando el circulo magico con flecha arriba a la derecha",
                "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.CIRCLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Noveno titulo ",
                "Probando scroll hacia arriba",
                "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.RECTANGLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Decimo titulo ",
                "Esto sigue en prueba y esta bueno que funcione bien",
                "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.RECTANGLE))

        coachmarkModel = AndesWalkthroughCoachmark(stepsNewCoachmark, scrollView) {
            println("Entro al despues de cerrar")
        }

        Mockito.`when`(view.getTooltipMargin()).thenReturn(72)
        Mockito.`when`(view.getToolbarSize()).thenReturn(192)
        Mockito.`when`(view.getFooterHeigh()).thenReturn(288)
        Mockito.`when`(view.getScrollViewPaddingFromDimen()).thenReturn(48)
    }

    @Test
    fun `testing scrolless coachmark`() {
        val stepsNewCoachmark = ArrayList<AndesWalkthroughCoachmarkStep>()
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Primer titulo", "Resaltamos el primer texto",
            "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.RECTANGLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Segundo titulo",
            "Probando el circulo magico con flecha abajo a la izquierda " +
                    "Probando el circulo magico con flecha abajo a la izquierda Probando" +
                    " el circulo magico con flecha abajo a la izquierda",
            "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.CIRCLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Tercer titulo ", "Resaltamos el primer texto",
            "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.RECTANGLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Cuarto titulo ",
            "Probando el circulo magico con flecha abajo a la derecha",
            "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.CIRCLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Quinto titulo ", "Resaltamos el texto largo " +
                "Resaltamos el texto largo Resaltamos el texto largo Resaltamos el texto largo Resaltamos " +
                "el texto largo Resaltamos el texto largo Resaltamos el texto largo Resaltamos el texto largo Resaltamos " +
                "el texto largo Resaltamos el texto largo Resaltamos el texto largo",
            "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.RECTANGLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Sexto titulo ", "Si vemos esto es porque scrolleo" +
                " al fin y estamos al final del coachmark ;)",
            "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.RECTANGLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Septimo titulo ",
            "Probando el circulo magico con flecha arriba a la izquierda",
            "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.CIRCLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Octavo titulo ",
            "Probando el circulo magico con flecha arriba a la derecha " +
                    "Probando el circulo magico con flecha arriba a la derecha Probando el circulo magico con " +
                    "flecha arriba a la derecha Probando el circulo magico con flecha arriba a la derecha",
            "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.CIRCLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Noveno titulo ",
            "Probando scroll hacia arriba",
            "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.RECTANGLE))
        stepsNewCoachmark.add(AndesWalkthroughCoachmarkStep("Decimo titulo ",
            "Esto sigue en prueba y esta bueno que funcione bien",
            "Siguiente", viewReference, AndesWalkthroughCoachmarkStyle.RECTANGLE))

        val coachmarkScrollessModel = AndesScrollessWalkthroughCoachmark(stepsNewCoachmark, scrollView) {
            println("Entro al despues de cerrar")
        }

        Mockito.`when`(view.getTooltipMargin()).thenReturn(72)
        Mockito.`when`(view.getToolbarSize()).thenReturn(192)
        Mockito.`when`(view.getFooterHeigh()).thenReturn(288)
        Mockito.`when`(view.getScrollViewPaddingFromDimen()).thenReturn(48)
    }

    @Test
    fun `resolveScrollMode - Draw bottom WalkthroughMessage with space for draw WalkthroughMessage stephighlight full viewed`() {

        val stepReferenceGlobalRect = Rect(48, 288, 418, 361)
        val bodyGlobalRect = Rect(0, 240, 1080, 1632)
        val tooltipHeigh = 404
        val stepReferencedpHeigh = 73

        Mockito.`when`(viewReference.height).thenReturn(73)
        Mockito.`when`(viewReference.getLocalVisibleRect(bodyGlobalRect)).thenReturn(true)

        val presenter = CoachmarkPresenter(view)
        presenter.resolveScrollMode(
            coachmarkModel.steps[0],
            HEIGHT_SCREEN,
            stepReferencedpHeigh,
            stepReferenceGlobalRect,
            bodyGlobalRect,
            tooltipHeigh,
            WalkthroughMessagePosition.BELOW
        )

        verify(view).getToolbarSize()
        verify(view).getScrollViewPaddingFromDimen()
        verify(view).animateScroll(true, 96, coachmarkModel.steps[0])
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `resolveScrollMode - Draw Top WalkthroughMessage with space for draw WalkthroughMessage stephighlight full viewed`() {

        val stepReferenceGlobalRect = Rect(48, 1322, 1032, 1466)
        val bodyGlobalRect = Rect(0, 240, 1080, 1632)
        val tooltipHeigh = 233
        val stepReferencedpHeigh = 144

        Mockito.`when`(viewReference.height).thenReturn(144)
        Mockito.`when`(viewReference.getLocalVisibleRect(bodyGlobalRect)).thenReturn(true)

        val presenter = CoachmarkPresenter(view)
        presenter.resolveScrollMode(
            coachmarkModel.steps[9],
            HEIGHT_SCREEN,
            stepReferencedpHeigh,
            stepReferenceGlobalRect,
            bodyGlobalRect,
            tooltipHeigh,
            WalkthroughMessagePosition.ABOVE
        )

        verify(view).getToolbarSize()
        verify(view).getScrollViewPaddingFromDimen()
        verify(view).animateScroll(true, 1130, coachmarkModel.steps[9])
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `resolveScrollMode - Draw Below WalkthroughMessage with stephighlight not viewed`() {

        val stepReferenceGlobalRect = Rect(48, -396, 418, -323)
        val bodyGlobalRect = Rect(0, 240, 1080, 1632)
        val tooltipHeigh = 404
        val stepReferencedpHeigh = 73

        Mockito.`when`(viewReference.height).thenReturn(73)
        Mockito.`when`(viewReference.getLocalVisibleRect(bodyGlobalRect)).thenReturn(false)

        val presenter = CoachmarkPresenter(view)
        presenter.resolveScrollMode(
            coachmarkModel.steps[2],
            HEIGHT_SCREEN,
            stepReferencedpHeigh,
            stepReferenceGlobalRect,
            bodyGlobalRect,
            tooltipHeigh,
            WalkthroughMessagePosition.BELOW
        )

        verify(view).getScrollViewPaddingFromDimen()
        verify(view).getToolbarSize()
        verify(view).getTooltipMargin()
        verify(view).getFooterHeigh()
        verify(view).animateScroll(false, -1215, coachmarkModel.steps[2])
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `resolveScrollMode - Draw Above WalkthroughMessage with stephighlight not viewed`() {
        val stepReferenceGlobalRect = Rect(48, 1556, 408, 1916)
        val bodyGlobalRect = Rect(0, 240, 1080, 1632)
        val tooltipHeigh = 680
        val stepReferencedpHeigh = 360

        Mockito.`when`(viewReference.height).thenReturn(360)
        Mockito.`when`(viewReference.getLocalVisibleRect(bodyGlobalRect)).thenReturn(true)

        val presenter = CoachmarkPresenter(view)
        presenter.resolveScrollMode(
            coachmarkModel.steps[1],
            HEIGHT_SCREEN,
            stepReferencedpHeigh,
            stepReferenceGlobalRect,
            bodyGlobalRect,
            tooltipHeigh,
            WalkthroughMessagePosition.ABOVE
        )

        verify(view).getToolbarSize()
        verify(view).getFooterHeigh()
        verify(view).getTooltipMargin()
        verify(view).getScrollViewPaddingFromDimen()
        verify(view).setScrollViewPaddings(0, 0, 0, 336)
        verify(view).animateScroll(false, 564, coachmarkModel.steps[1])
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `addRect - Circle`() {
        val presenter = CoachmarkPresenter(view)
        presenter.addRect(coachmarkModel.steps[1])

        verify(view).addCircleRect(coachmarkModel.steps[1])
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `addRect - Rectangle`() {
        val presenter = CoachmarkPresenter(view)
        presenter.addRect(coachmarkModel.steps[0])

        verify(view).addRoundRect(coachmarkModel.steps[0])
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `relocateTooltip - Below`() {
        val stepReferenceGlobalRect = Rect(48, 288, 418, 361)
        val containerHeight = 1740

        val presenter = CoachmarkPresenter(view)
        presenter.relocateTooltip(404, WalkthroughMessagePosition.BELOW, stepReferenceGlobalRect, containerHeight)

        verify(view).getTooltipMargin()
        verify(view).setWalkthroughMessageViewY(433F)
        verify(view).hasArrow()
        verify(view).setNewMessageDimensions(1307)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `relocateTooltip - Below - with arrow`() {
        val stepReferenceGlobalRect = Rect(48, 288, 418, 361)

        val presenter = CoachmarkPresenter(view)
        `when`(view.hasArrow()).thenReturn(true)
        presenter.relocateTooltip(404, WalkthroughMessagePosition.BELOW, stepReferenceGlobalRect, CONTAINER_HEIGHT)

        verify(view).getTooltipMargin()
        verify(view).setWalkthroughMessageViewY(433F)
        verify(view).hasArrow()
        verify(view).setNewMessageDimensions(ViewGroup.LayoutParams.WRAP_CONTENT)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `relocateTooltip - Above`() {
        val stepReferenceGlobalRect = Rect(48, 1112, 408, 1472)

        val presenter = CoachmarkPresenter(view)
        presenter.relocateTooltip(680, WalkthroughMessagePosition.ABOVE, stepReferenceGlobalRect, CONTAINER_HEIGHT)

        verify(view).getTooltipMargin()
        verify(view).setWalkthroughMessageViewY(360F)
        verify(view).setNewMessageDimensions(ViewGroup.LayoutParams.WRAP_CONTENT)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `restorePreviousValues`() {
        val presenter = CoachmarkPresenter(view)
        presenter.restorePreviousValues()

        verify(view).cleanCoachmarkOverlayView()
        verify(view).clearWalkthroughMessageView()
        verify(view).setWalkthroughMessageViewY(0F)
        verify(view).setNewMessageDimensions(ViewGroup.LayoutParams.WRAP_CONTENT)
        verify(view).setScrollViewPaddings(0, 0, 0, 0)
        verifyNoMoreInteractions(view)
    }

    companion object {
        const val HEIGHT_SCREEN = 1392
        const val CONTAINER_HEIGHT = 1740
    }
}
