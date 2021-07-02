package com.mercadolibre.android.andesui.floatingmenu

import android.widget.PopupWindow
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.annotation.RealObject
import org.robolectric.shadow.api.Shadow.directlyOn
import org.robolectric.shadows.ShadowPopupWindow

/**
 * This class ONLY exists because robolectric ShadowPopupWindow does not call onDismiss listener
 * when the popup is truly dismissed.
 */
@Implements(PopupWindow::class)
class ShadowPopupWindowDismissible : ShadowPopupWindow() {
    @RealObject
    private lateinit var realPopupWindow: PopupWindow

    /**
     * ShadowPopupWindow do not call PopupWindow dismiss.
     * This shadow method Calls popup dismiss like a real PopupWindow object.
     */
    @Implementation
    fun dismiss() {
        directlyOn(realPopupWindow, PopupWindow::class.java).dismiss()
    }
}
