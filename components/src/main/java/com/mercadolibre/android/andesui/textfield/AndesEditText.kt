package com.mercadolibre.android.andesui.textfield

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * Custom EditText for to be able to handle the actions of onTextContextMenuItem.
 */
internal class AndesEditText : AppCompatEditText {

    private var contextMenuListener: OnTextContextMenuItemListener = object : OnTextContextMenuItemListener {}

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setOnTextContextMenuItemListener(contextMenuListener: OnTextContextMenuItemListener) {
        this.contextMenuListener = contextMenuListener
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        val consumed = when (id) {
            android.R.id.cut -> {
                contextMenuListener.onCut()
            }
            android.R.id.paste -> {
                contextMenuListener.onPaste()
            }
            android.R.id.copy -> {
                contextMenuListener.onCopy()
            }
            else -> false
        }

        return consumed || super.onTextContextMenuItem(id)
    }

    /**
     * Interface for listen the actions of copy, cut and paste.
     */
    internal interface OnTextContextMenuItemListener {
        fun onCut(): Boolean = false
        fun onPaste(): Boolean = false
        fun onCopy(): Boolean = false
    }
}
