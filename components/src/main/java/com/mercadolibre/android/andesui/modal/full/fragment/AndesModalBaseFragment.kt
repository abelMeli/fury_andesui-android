package com.mercadolibre.android.andesui.modal.full.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.mercadolibre.android.andesui.modal.common.AndesModalInterface
import com.mercadolibre.android.andesui.modal.full.builder.ModalFullAction

/**
 * Base class for all the full modal variants.
 */
abstract class AndesModalBaseFragment<T : ViewBinding>(
    contentLayoutId: Int
) : Fragment(contentLayoutId), AndesModalInterface {

    private var _binding: T? = null
    protected val binding: T get() = _binding!!
    protected var onDismissCallback: ModalFullAction? = null
    protected var onModalShowCallback: ModalFullAction? = null
    protected var modalDescription: String? = null

    protected abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): T

    /**
     * Utility method to show the fragment inside the provided [containerId].
     * Note that this method will **replace** the fragment, without adding it to the backstack.
     *
     * Since all full modal variants inherit from [Fragment], feel free to handle
     * and show the modal with your own fragmentManager setting your preferred strategy for the
     * backstack handling.
     */
    fun show(activity: FragmentActivity?, @IdRes containerId: Int) {
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.beginTransaction()?.replace(containerId, this)?.commit()
    }

    /**
     * Utility method to dismiss the modal.
     *
     * Similarly to the [show] method, feel free to handle the navigation with your own fragmentManager.
     */
    override fun dismiss() {
        onDismissCallback?.invoke()
        activity?.onBackPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onModalShowCallback?.invoke()
        modalDescription?.let {
            view.announceForAccessibility(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
