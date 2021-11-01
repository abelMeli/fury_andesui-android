package com.mercadolibre.android.andesui.demoapp.components.a11yplayground

import android.os.Bundle
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R

@SuppressWarnings("TooManyFunctions")
class NextFocusActivity : A11yPlaygroundActivity() {

    private lateinit var button7: AndesButton
    private lateinit var button8: AndesButton
    private lateinit var button9: AndesButton
    private lateinit var button10: AndesButton
    private lateinit var button11: AndesButton
    private lateinit var button12: AndesButton
    private lateinit var button13: AndesButton
    private lateinit var button14: AndesButton
    private lateinit var button15: AndesButton
    private lateinit var button16: AndesButton
    private lateinit var button17: AndesButton
    private lateinit var button18: AndesButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_focus)
        initActionBar()
        setupViews()
        setupFocus()
    }

    private fun setupViews() {
        button7 = findViewById(R.id.nf_button_7)
        button8 = findViewById(R.id.nf_button_8)
        button9 = findViewById(R.id.nf_button_9)
        button10 = findViewById(R.id.nf_button_10)
        button11 = findViewById(R.id.nf_button_11)
        button12 = findViewById(R.id.nf_button_12)
        button13 = findViewById(R.id.nf_button_13)
        button14 = findViewById(R.id.nf_button_14)
        button15 = findViewById(R.id.nf_button_15)
        button16 = findViewById(R.id.nf_button_16)
        button17 = findViewById(R.id.nf_button_17)
        button18 = findViewById(R.id.nf_button_18)
    }

    private fun setupFocus() {
        setupFocusBtn10()
        setupFocusBtn11()
        setupFocusBtn12()
        setupFocusBtn13()
        setupFocusBtn14()
        setupFocusBtn15()
        setupFocusBtn16()
        setupFocusBtn17()
        setupFocusBtn18()
    }

    private fun setupFocusBtn10() {
        button10.apply {
            nextFocusRightId = button11.id
            nextFocusUpId = button7.id
            nextFocusDownId = button13.id
            nextFocusForwardId = button11.id
        }
    }

    private fun setupFocusBtn11() {
        button11.apply {
            nextFocusLeftId = button10.id
            nextFocusRightId = button12.id
            nextFocusUpId = button8.id
            nextFocusDownId = button14.id
            nextFocusForwardId = button12.id
        }
    }

    private fun setupFocusBtn12() {
        button12.apply {
            nextFocusLeftId = button11.id
            nextFocusUpId = button9.id
            nextFocusDownId = button15.id
        }
    }

    private fun setupFocusBtn13() {
        button13.apply {
            nextFocusRightId = button14.id
            nextFocusUpId = button10.id
            nextFocusDownId = button16.id
            nextFocusForwardId = button14.id
        }
    }

    private fun setupFocusBtn14() {
        button14.apply {
            nextFocusLeftId = button13.id
            nextFocusRightId = button15.id
            nextFocusUpId = button11.id
            nextFocusDownId = button17.id
            nextFocusForwardId = button15.id
        }
    }

    private fun setupFocusBtn15() {
        button15.apply {
            nextFocusLeftId = button14.id
            nextFocusUpId = button12.id
            nextFocusDownId = button18.id
        }
    }

    private fun setupFocusBtn16() {
        button16.apply {
            nextFocusRightId = button17.id
            nextFocusUpId = button13.id
            nextFocusForwardId = button17.id
        }
    }

    private fun setupFocusBtn17() {
        button17.apply {
            nextFocusLeftId = button16.id
            nextFocusRightId = button18.id
            nextFocusUpId = button14.id
            nextFocusForwardId = button18.id
        }
    }

    private fun setupFocusBtn18() {
        button18.apply {
            nextFocusLeftId = button17.id
            nextFocusUpId = button15.id
        }
    }

    private fun initActionBar() {
        val navBarTitle = resources.getString(R.string.andes_demoapp_playground_title_next_focus)
        setSupportActionBar(findViewById(R.id.nfa_nav_bar))
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
