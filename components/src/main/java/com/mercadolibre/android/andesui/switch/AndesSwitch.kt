package com.mercadolibre.android.andesui.switch

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.switch.accessibility.AndesSwitchAccessibilityDelegate
import com.mercadolibre.android.andesui.switch.accessibility.AndesSwitchAccessibilityEventDispatcher
import com.mercadolibre.android.andesui.switch.align.AndesSwitchAlign
import com.mercadolibre.android.andesui.switch.factory.AndesSwitchAttrs
import com.mercadolibre.android.andesui.switch.factory.AndesSwitchAttrsParser
import com.mercadolibre.android.andesui.switch.factory.AndesSwitchConfiguration
import com.mercadolibre.android.andesui.switch.factory.AndesSwitchConfigurationFactory
import com.mercadolibre.android.andesui.switch.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switch.type.AndesSwitchType

/**
 * This component allows the user to inmediately turn ON or OFF a preference or functionality.
 */
@Suppress("TooManyFunctions")
class AndesSwitch : ConstraintLayout {

    /**
     * Getter and setter for [text].
     */
    var text: String?
        get() = andesSwitchAttrs.andesSwitchText
        set(value) {
            andesSwitchAttrs = andesSwitchAttrs.copy(andesSwitchText = value)
            setupTextComponent(createConfig())
        }

    /**
     * Getter and setter for [align].
     */
    var align: AndesSwitchAlign
        get() = andesSwitchAttrs.andesSwitchAlign
        set(value) {
            andesSwitchAttrs = andesSwitchAttrs.copy(andesSwitchAlign = value)
            setupTextComponent(createConfig())
        }

    /**
     * Getter and setter for [type].
     */
    var type: AndesSwitchType
        get() = andesSwitchAttrs.andesSwitchType
        set(value) {
            andesSwitchAttrs = andesSwitchAttrs.copy(andesSwitchType = value)
            val config = createConfig()
            setupSwitchComponent(config)
            setupType(config)
        }

    /**
     * Getter and setter for [status].
     */
    var status: AndesSwitchStatus
        get() = andesSwitchAttrs.andesSwitchStatus
        set(value) {
            andesSwitchAttrs = andesSwitchAttrs.copy(andesSwitchStatus = value)
            setupSwitchComponent(createConfig())
        }

    private lateinit var container: View
    private lateinit var andesSwitchAttrs: AndesSwitchAttrs
    private lateinit var leftTextComponent: TextView
    private lateinit var rightTextComponent: TextView
    private lateinit var switchComponent: AndesSwitchComponent
    internal var onStatusChangeListener: OnStatusChangeListener? = null
    private lateinit var a11yEventDispatcher: AndesSwitchAccessibilityEventDispatcher
    private lateinit var switchComponentCheckedChangeListener: AndesSwitchComponent.OnCheckedChangeListener

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        text: String? = null,
        align: AndesSwitchAlign = ANDES_SWITCH_ALIGN_DEFAULT_VALUE,
        type: AndesSwitchType = ANDES_SWITCH_TYPE_DEFAULT_VALUE,
        status: AndesSwitchStatus = ANDES_SWITCH_STATUS_DEFAULT_VALUE
    ) : super(context) {
        initAttrs(text, align, type, status)
    }

    /**
     * Sets the proper config for this view based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet) {
        andesSwitchAttrs = AndesSwitchAttrsParser.parse(context, attrs)
        val config = AndesSwitchConfigurationFactory.create(andesSwitchAttrs)
        setupComponents(config)
    }

    private fun initAttrs(
        text: String?,
        align: AndesSwitchAlign,
        type: AndesSwitchType,
        status: AndesSwitchStatus
    ) {
        andesSwitchAttrs = AndesSwitchAttrs(align, status, type, text)
        val config = AndesSwitchConfigurationFactory.create(andesSwitchAttrs)
        setupComponents(config)
    }

    private fun setupComponents(config: AndesSwitchConfiguration) {
        initComponents()
        setupA11yDelegate()
        setupA11yEventDispatcher()
        setupAccessibilityNavigation()
        setupViewId()
        setupTextComponent(config)
        setupSwitchComponent(config)
        setupType(config)
    }

    private fun initComponents() {
        container = LayoutInflater.from(context).inflate(R.layout.andes_layout_switch, this)
        switchComponent = container.findViewById(R.id.switch_component)
        leftTextComponent = container.findViewById(R.id.switch_text_left)
        rightTextComponent = container.findViewById(R.id.switch_text_right)
    }

    private fun setupA11yDelegate() {
        accessibilityDelegate = AndesSwitchAccessibilityDelegate(this)
    }

    private fun setupA11yEventDispatcher() {
        a11yEventDispatcher = AndesSwitchAccessibilityEventDispatcher()
    }

    private fun setupAccessibilityNavigation() {
        container.isFocusable = true
        container.isClickable = false
        container.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES

        leftTextComponent.isFocusable = false
        leftTextComponent.isClickable = false
        leftTextComponent.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO

        rightTextComponent.isFocusable = false
        rightTextComponent.isClickable = false
        rightTextComponent.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO

        switchComponent.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        switchComponent.isClickable = false
    }

    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    private fun setupTextComponent(config: AndesSwitchConfiguration) {
        leftTextComponent.visibility = config.leftTextVisibility
        leftTextComponent.text = config.text
        rightTextComponent.visibility = config.rightTextVisibility
        rightTextComponent.text = config.text
    }

    private fun setupSwitchComponent(config: AndesSwitchConfiguration) {
        setupSwitchComponentListener()
        switchComponent.setupSwitch(switchComponentCheckedChangeListener, config.status, config.type)
    }

    private fun setupType(config: AndesSwitchConfiguration) {
        isEnabled = when (config.type) {
            AndesSwitchType.ENABLED -> true
            AndesSwitchType.DISABLED -> false
        }
    }

    private fun setupSwitchComponentListener() {
        switchComponentCheckedChangeListener = object : AndesSwitchComponent.OnCheckedChangeListener {
            override fun onCheckedChange(status: AndesSwitchStatus) {
                onStatusChangeListener?.onStatusChange(status)
                a11yEventDispatcher.notifyA11yStatusChanged(this@AndesSwitch, status)
                this@AndesSwitch.status = status
            }
        }
    }

    private fun createConfig() = AndesSwitchConfigurationFactory.create(andesSwitchAttrs)

    /**
     * setter for [onStatusChangeListener]
     */
    fun setOnStatusChangeListener(listener: OnStatusChangeListener) {
        onStatusChangeListener = listener
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let {
            if (it.action == MotionEvent.ACTION_DOWN) {
                switchComponent.performClick()
                return super.onInterceptTouchEvent(ev)
            }
        }
        return false
    }

    /**
     * Interface used to get info when the status of the switch changes
     */
    interface OnStatusChangeListener {
        fun onStatusChange(andesSwitch: AndesSwitchStatus)
    }

    override fun getAccessibilityClassName(): CharSequence {
        return Switch::class.java.name
    }

    companion object {
        private val ANDES_SWITCH_ALIGN_DEFAULT_VALUE = AndesSwitchAlign.RIGHT
        private val ANDES_SWITCH_TYPE_DEFAULT_VALUE = AndesSwitchType.ENABLED
        private val ANDES_SWITCH_STATUS_DEFAULT_VALUE = AndesSwitchStatus.UNCHECKED
    }
}