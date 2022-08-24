package com.mercadolibre.android.andesui.coachmark.utils

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

internal fun ConstraintLayout.setConstraints(actions: ConstraintSet.() -> Unit) {
    ConstraintSet().apply {
        clone(this@setConstraints)
        actions()
        applyTo(this@setConstraints)
    }
}
