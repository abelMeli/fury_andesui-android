package com.mercadolibre.android.andesui.utils

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spanned
import android.text.style.CharacterStyle
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import java.lang.reflect.Field
import org.junit.Assert
import org.mockito.internal.util.reflection.FieldSetter
import org.robolectric.Robolectric
import org.robolectric.Shadows.shadowOf
import org.robolectric.android.AttributeSetBuilder

/** Returns the value of the private field [this].[name]. */
inline fun <reified T> Any.getPrivateField(name: String) = getDeclaredField(name).run {
    isAccessible = true
    get(this@getPrivateField) as T
}

/** Sets the given [value] on the private field [this].[name]. */
fun Any.setPrivateField(name: String, value: Any?): Any {
    FieldSetter.setField(this, javaClass.obtainDeclaredField(name), value)
    return this
}

fun Any.getDeclaredField(name: String, clazz: Class<*> = javaClass): Field {
    return try {
        clazz.getDeclaredField(name)
    } catch (e: NoSuchFieldException) {
        clazz.superclass?.let { getDeclaredField(name, it) } ?: throw e
    }
}

private tailrec fun Class<*>.obtainDeclaredField(name: String): Field? {
    val field = try {
        getDeclaredField(name)
    } catch (ignored: NoSuchFieldException) {
        null
    }
    return field ?: superclass?.obtainDeclaredField(name)
}

infix fun Drawable.assertEquals(@DrawableRes expected: Int) {
    Assert.assertEquals(expected, shadowOf(this).createdFromResId)
}

infix fun <T> T.assertEquals(expected: T) = Assert.assertEquals(expected, this)

infix fun <T> T.assertNotEquals(expected: T) = Assert.assertNotEquals(expected, this)

infix fun Typeface.assertEquals(expected: Typeface) {
    val expectedFontDesc = shadowOf(expected).fontDescription
    val actualFontDesc = shadowOf(this).fontDescription
    actualFontDesc.familyName assertEquals expectedFontDesc.familyName
    actualFontDesc.style assertEquals expectedFontDesc.style
}

infix fun <T> T.assertIsNull(expectedNull: Boolean) = if (expectedNull) {
    Assert.assertNull(this)
} else {
    Assert.assertNotNull(this)
}

fun buildAttributeSet(attrs: AttributeSetBuilder.() -> Unit): AttributeSet =
    with(Robolectric.buildAttributeSet()) {
        attrs()
        build()
    }

fun activateTalkbackForTest(context: Context) {
    val shadowA11yManager = shadowOf(context.getAccessibilityManager())
    shadowA11yManager.setEnabled(true)
}

fun CharSequence?.hasSpans(): Boolean {
    if (this !is Spanned) return false
    val spans = getSpans(0, length, CharacterStyle::class.java)
    return spans.isNotEmpty()
}

fun CharSequence?.getSpans(): List<CharacterStyle> {
    if (this !is Spanned) return emptyList()
    val spans = getSpans(0, length, CharacterStyle::class.java)
    return spans.map { it }
}

internal fun CharSequence?.getClickableSpans(): List<ClickableSpanWithText> {
    if (this !is Spanned) return emptyList()
    val spans = getSpans(0, length, ClickableSpanWithText::class.java)
    return spans.map { it }
}

internal fun Context.isSoftKeyboardVisible(): Boolean {
    val shadowManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    return shadowOf(shadowManager).isSoftInputVisible
}
