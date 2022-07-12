package com.mercadolibre.android.andesui.modal.full.headertype

import android.text.TextUtils
import android.view.Gravity

internal interface AndesModalFullHeaderStatusInterface {
    val ellipsize: TextUtils.TruncateAt?
    val isSingleLine: Boolean
    val gravity: Int
}

internal object AndesModalFullHeaderStatusCollapsed : AndesModalFullHeaderStatusInterface {
    override val ellipsize
        get() = TextUtils.TruncateAt.END
    override val isSingleLine: Boolean
        get() = true
    override val gravity
        get() = Gravity.NO_GRAVITY
}

internal object AndesModalFullHeaderStatusExpanded : AndesModalFullHeaderStatusInterface {
    override val ellipsize: TextUtils.TruncateAt?
        get() = null
    override val isSingleLine: Boolean
        get() = false
    override val gravity
        get() = Gravity.CENTER
}
