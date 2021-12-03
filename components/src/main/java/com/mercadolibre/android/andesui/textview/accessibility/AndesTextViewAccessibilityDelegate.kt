package com.mercadolibre.android.andesui.textview.accessibility

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.utils.createActionList

/**
 * class responsible for handling the component accessibility, including the correct setting of the
 * contentDescription, available actions, and more.
 */
class AndesTextViewAccessibilityDelegate(private val andesTextView: AndesTextView) : View.AccessibilityDelegate() {

    private val actionList by lazy {
        createActionList(
            andesTextView.context,
            andesTextView.bodyLinks,
            andesTextView.text
        )
    }

    /**
     * method responsible for sending the component description to the Talkback service.
     */
    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            addLinkActions(info)
        }
    }

    /**
     * Callback for when we perform an action over the [andesTextView].
     *
     * We override this method to be able to filter when the user clicks a link from the actions
     * in the local context menu.
     */
    override fun performAccessibilityAction(host: View?, action: Int, args: Bundle?): Boolean {
        val listOfActionIds = actionList.map { it.id }
        when (action) {
            in listOfActionIds -> {
                andesTextView.bodyLinks?.listener?.invoke(listOfActionIds.indexOf(action))
                return true
            }
        }
        return super.performAccessibilityAction(host, action, args)
    }

    /**
     * Adds one action per link present in the [andesTextView].
     *
     * This method is only needed when the API level is less than [Build.VERSION_CODES.O], since
     * in versions prior to this API the TalkBack is not able to detect and activate the clickable spans.
     * More info in this link: https://support.google.com/accessibility/android/answer/6378148?hl=en
     *
     */
    private fun addLinkActions(info: AccessibilityNodeInfo?) {
        actionList.forEach {
            info?.addAction(it)
        }
    }
}
