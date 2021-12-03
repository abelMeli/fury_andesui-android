package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks

/**
 * This file is added to handle all the a11y related matters in the [com.mercadolibre.android.andesui.textview.AndesTextView]
 */

private val actionLinkFirst = R.id.accessibility_action_click_first_link
private val actionLinkSecond = R.id.accessibility_action_click_second_link
private val actionLinkThird = R.id.accessibility_action_click_third_link
private val actionLinkFourth = R.id.accessibility_action_click_fourth_link

/**
 * list of custom accessibility actions created to handle the possible links an andesTextView
 * can contain.
 *
 * We add this values as id resources because we need this type of values to create
 * custom [AccessibilityNodeInfo.AccessibilityAction] objects.
 *
 * We create only four because with the text extension we handle in our components,
 * putting more than four links in a text should not occur. If you as dev encounter a use case
 * in which you will need more than four, please let us know
 */
internal val actionLinkIdList = listOf(
    actionLinkFirst,
    actionLinkSecond,
    actionLinkThird,
    actionLinkFourth
)

/**
 * Returns a new Accessibility action ready to be added to an [AccessibilityNodeInfo] node.
 * @param [actionText] The text to display in the local context menu.
 */
internal fun Int.toA11yAction(actionText: String): AccessibilityNodeInfo.AccessibilityAction {
    return AccessibilityNodeInfo.AccessibilityAction(this, actionText)
}

/**
 * Creates a new accessibility action for each link present in the text.
 * This way, when giving a11y focus to the component, we can select the different links in the text body
 * as different actions from the local context menu.
 */
internal fun createActionList(context: Context, bodyLinks: AndesBodyLinks?, text: CharSequence?): List<AccessibilityNodeInfo.AccessibilityAction> {
    val list = mutableListOf<AccessibilityNodeInfo.AccessibilityAction>()
    bodyLinks?.let {
        it.links.forEachIndexed { index, _ ->
            val linkTexts = text.getLinkTextList()
            if (linkTexts.isNotEmpty()) {
                if (!linkTexts[index].isNullOrEmpty()) {
                    val action = actionLinkIdList[index].toA11yAction(generateLinkActionText(context, linkTexts[index]))
                    list.add(action)
                }
            }
        }
    }
    return list
}

private fun generateLinkActionText(context: Context, text: String?): String {
    return context.resources.getString(R.string.andes_textview_available_link, text)
}
