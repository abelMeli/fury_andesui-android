package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.ActivityA11yCustomActionsBinding

class A11yCustomActionsActivity : A11yPlaygroundActivity() {

    private val binding by lazy {
        ActivityA11yCustomActionsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initActionBar()
        setupFirstItem()
        setupSecondItem()
    }

    private fun initActionBar() {
        val navBarTitle =
            resources.getString(R.string.andes_demoapp_playground_title_custom_actions)
        setSupportActionBar(binding.acaNavBar)
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupFirstItem() {
        binding.acaItemFirst.apply {
            image =
                ContextCompat.getDrawable(this@A11yCustomActionsActivity, R.drawable.botas_image)
            imageContentDescription = "Par de botas de viaje"
            title = "Botas de viaje"
            subtitle = "Llegan hoy"
            onFavoriteClick = View.OnClickListener {
                Toast.makeText(applicationContext, "Clicked in favorite", Toast.LENGTH_SHORT).show()
            }
            onCartClick = View.OnClickListener {
                Toast.makeText(applicationContext, "Clicked in add to cart", Toast.LENGTH_SHORT)
                    .show()
            }
            onInfoClick = View.OnClickListener {
                Toast.makeText(applicationContext, "Clicked in more info", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSecondItem() {
        binding.acaItemSecond.apply {
            image =
                ContextCompat.getDrawable(this@A11yCustomActionsActivity, R.drawable.botas_image)
            imageContentDescription = "Par de botas"
            title = "Botas de viaje"
            subtitle = "Llegan hoy"
            onFavoriteClick = View.OnClickListener {
                Toast.makeText(applicationContext, "Clicked in favorite", Toast.LENGTH_SHORT).show()
            }
            onCartClick = View.OnClickListener {
                Toast.makeText(applicationContext, "Clicked in add to cart", Toast.LENGTH_SHORT)
                    .show()
            }
            onInfoClick = View.OnClickListener {
                Toast.makeText(applicationContext, "Clicked in more info", Toast.LENGTH_SHORT).show()
            }

            // we mark this second card as semantic view. To see the internal work of the view and
            // the creation of each custom action, navigate inside this setter
            isSemanticView = true
        }
    }
}
