package com.mercadolibre.android.andesui.demoapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class SafeIntent : Intent {

    constructor(context: Context, uri: String) : super(ACTION_VIEW, (Uri.parse(uri))) {
        setPackage(context.applicationContext.packageName)
    }

    constructor(context: Context, uri: Uri) : super(ACTION_VIEW, uri) {
        setPackage(context.applicationContext.packageName)
    }
}
