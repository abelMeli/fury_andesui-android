package com.mercadolibre.android.andesui.demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mercadolibre.android.andesui.demoapp.utils.SafeIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        lifecycleScope.launchWhenResumed {
            withContext(Dispatchers.Default) { delay(DEFAULT_DELAY_MILLIS) }
            goMainActivity()
        }
    }

    private fun goMainActivity() {
        SafeIntent(this, "andes://home").also {
            startActivity(it)
            finishAfterTransition()
        }
    }

    companion object {
        private const val DEFAULT_DELAY_MILLIS = 250L
    }
}
