package com.mercadolibre.android.andesui.demoapp.whatsnew

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDemoappWhatsnewBinding
import io.noties.markwon.Markwon
import java.io.ByteArrayOutputStream
import java.io.InputStream

class WhatsNewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = AndesuiDemoappWhatsnewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.andesuiNavBar)
        supportActionBar?.title = resources.getString(R.string.andes_demoapp_screen_whatsnew)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val changelog = readChangelogFile()
        if (changelog.isEmpty()) {
            binding.andesuiDemoappWhatsNew.setText(R.string.andes_demoapp_changelog_error)
        } else {
            val markwon = Markwon.create(this)
            markwon.setMarkdown(binding.andesuiDemoappWhatsNew, changelog)
        }
    }

    private fun readChangelogFile(): String {
        val inputStream: InputStream = resources.openRawResource(R.raw.andesui_demoapp_changelog)
        val byteArrayOutputStream = ByteArrayOutputStream()

        inputStream.use {
            var i = inputStream.read()
            while (i != -1) {
                byteArrayOutputStream.write(i)
                i = inputStream.read()
            }
        }

        return byteArrayOutputStream.toString()
    }
}
