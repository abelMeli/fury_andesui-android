package com.mercadolibre.android.andesui.dropdown.accessibility

import android.os.Bundle
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DropdownBottomSheetDialogAccessibilityDelegate(
    private val bottomSheetBehavior: BottomSheetBehavior<View>
) : View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.isDismissable = true
        addActions(info)
    }

    private fun addActions(info: AccessibilityNodeInfo?) {
        info?.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS)
        info?.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND)
    }

    override fun performAccessibilityAction(host: View?, action: Int, args: Bundle?): Boolean {
        return when (action) {
            AccessibilityNodeInfo.ACTION_DISMISS -> {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                true
            }
            AccessibilityNodeInfo.ACTION_EXPAND -> {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                true
            }
            else -> {
                super.performAccessibilityAction(host, action, args)
            }
        }
    }
}
