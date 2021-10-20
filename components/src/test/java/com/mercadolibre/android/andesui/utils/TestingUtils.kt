package com.mercadolibre.android.andesui

import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import org.junit.Assert
import org.mockito.internal.util.reflection.FieldSetter
import org.robolectric.Robolectric
import org.robolectric.Shadows.shadowOf
import org.robolectric.android.AttributeSetBuilder
import java.lang.reflect.Field

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

infix fun <T> T.assertEquals(expected: T) = Assert.assertEquals(this, expected)

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