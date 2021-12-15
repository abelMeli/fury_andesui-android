package com.mercadolibre.android.andesui.demoapp.home.model

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes

data class MainAction(
    val name: String,
    @DrawableRes val asset: Int,
    val intent: (context: Context) -> Intent
)
