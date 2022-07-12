package com.mercadolibre.android.andesui.modal.card.dialogfragment

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.modal.card.builder.Action
import com.mercadolibre.android.andesui.modal.common.AndesModalInterface

/**
 * Base class for all the card modal variants.
 */
abstract class AndesDialogFragment : DialogFragment(), AndesModalInterface {

    protected var onDismissCallback: Action? = null
    protected var onModalShowCallback: Action? = null
    protected var modalDescription: String? = null
    protected var isDismissible: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.AndesDialogTheme)
    }

    /**
     * Native alternative for the show method.
     * Prefer using the overloaded method with the FragmentActivity parameter over this one.
     */
    override fun show(manager: FragmentManager, tag: String?) {
        try {
            if (!manager.isDestroyed) {
                super.show(manager, tag)
            }
        } catch (badTokenException: WindowManager.BadTokenException) {
            // no-op
        } catch (illegalStateException: IllegalStateException) {
            // no-op
        }
    }

    /**
     * Method to show the modal over the passed [activity].
     * Before displaying the modal, it will check internally if it is not already being shown.
     */
    fun show(activity: FragmentActivity) {
        val fragmentManager = activity.supportFragmentManager
        val dialogAlreadyShowing = fragmentManager.findFragmentByTag(MODAL_TAG) != null

        if (!dialogAlreadyShowing) {
            show(fragmentManager, MODAL_TAG)
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.let { window ->
            val leftPadding = resources.getDimension(R.dimen.andes_modal_margin_24).toInt()
            val rightPadding = resources.getDimension(R.dimen.andes_modal_margin_24).toInt()
            val bottomPadding = resources.getDimension(R.dimen.andes_modal_margin_48).toInt()
            val topPadding = if (isDismissible) {
                NO_PADDING
            } else {
                resources.getDimension(R.dimen.andes_modal_margin_48).toInt()
            }

            val inset = InsetDrawable(
                ColorDrawable(Color.TRANSPARENT),
                leftPadding,
                topPadding,
                rightPadding,
                bottomPadding
            )

            window.setBackgroundDrawable(inset)

            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onModalShowCallback?.invoke()
        modalDescription?.let {
            view.announceForAccessibility(it)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDismissCallback?.invoke()
        super.onDismiss(dialog)
    }

    override fun dismiss() {
        super.dismiss()
    }

    companion object {
        private const val MODAL_TAG = "andes_modal"
        private const val NO_PADDING = 0
    }
}
