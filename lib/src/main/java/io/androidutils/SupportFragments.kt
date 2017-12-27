package io.androidutils

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> Context.createFragment(fragmentClass: KClass<T>): T = Fragment.instantiate(this, fragmentClass.java.name) as T

fun <F : Fragment, T : Serializable?> F.putExtra(key: String, value: T): F = apply {
    checkArguments()
    arguments!!.putSerializable(key, value)
}

fun <F : Fragment, T : Parcelable?> F.putExtra(key: String, value: T): F = apply {
    checkArguments()
    arguments!!.putParcelable(key, value)
}

fun Fragment.stringListExtra(key: String? = null, defaultValue: () -> List<String> = { kotlin.collections.emptyList() }): ReadWriteProperty<Fragment, List<String>> = object : ReadWriteProperty<Fragment, List<String>> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): List<String> {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getStringArrayList(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: List<String>) {
        thisRef.checkArguments()
        thisRef.arguments!!.putStringArrayList(key ?: property.name, ArrayList(value))
    }
}

fun Fragment.stringExtra(key: String? = null, defaultValue: () -> String = { "" }): ReadWriteProperty<Fragment, String> = object : ReadWriteProperty<Fragment, String> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): String {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getString(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: String) {
        thisRef.checkArguments()
        thisRef.arguments!!.putString(key ?: property.name, value)
    }
}

fun Fragment.stringArrayExtra(key: String? = null, defaultValue: () -> Array<String> = { kotlin.emptyArray() }): ReadWriteProperty<Fragment, Array<String>> = object : ReadWriteProperty<Fragment, Array<String>> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Array<String> {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getStringArray(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Array<String>) {
        thisRef.checkArguments()
        thisRef.arguments!!.putStringArray(key ?: property.name, value)
    }
}

fun <T : Parcelable> Fragment.parcelableListExtra(key: String? = null, defaultValue: () -> List<T> = { kotlin.collections.emptyList() }): ReadWriteProperty<Fragment, List<T>> = object : ReadWriteProperty<Fragment, List<T>> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): List<T> {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getParcelableArrayList(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: List<T>) {
        thisRef.checkArguments()
        thisRef.arguments!!.putParcelableArrayList(key ?: property.name, ArrayList(value))
    }
}

inline fun <reified T : Parcelable> Fragment.parcelableArrayExtra(key: String? = null, crossinline defaultValue: () -> Array<T> = { kotlin.emptyArray() }): ReadWriteProperty<Fragment, Array<T>> = object : ReadWriteProperty<Fragment, Array<T>> {
    private fun Fragment.checkArguments() {
        if (arguments != null) return
        val field = Fragment::class.java.getDeclaredField("mArguments")
        val isAccessible = field.isAccessible
        if (!isAccessible) field.isAccessible = true
        field.set(this, android.os.Bundle())
        if (!field.isAccessible) field.isAccessible = false
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): Array<T> {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getParcelableArray(key ?: property.name).map { it as T }.toTypedArray()
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Array<T>) {
        thisRef.checkArguments()
        thisRef.arguments!!.putParcelableArray(key ?: property.name, value)
    }
}

fun Fragment.intExtra(key: String? = null, defaultValue: Int = 0): ReadWriteProperty<Fragment, Int> = object : ReadWriteProperty<Fragment, Int> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Int = thisRef.arguments?.getInt(key ?: property.name, defaultValue) ?: defaultValue
    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Int) {
        thisRef.checkArguments()
        thisRef.arguments!!.putInt(key ?: property.name, value)
    }
}

fun Fragment.intArrayExtra(key: String? = null, defaultValue: () -> IntArray = { IntArray(0) }): ReadWriteProperty<Fragment, IntArray> = object : ReadWriteProperty<Fragment, IntArray> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): IntArray {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getIntArray(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: IntArray) {
        thisRef.checkArguments()
        thisRef.arguments!!.putIntArray(key ?: property.name, value)
    }
}

fun Fragment.shortExtra(key: String? = null, defaultValue: Short = 0): ReadWriteProperty<Fragment, Short> = object : ReadWriteProperty<Fragment, Short> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Short = thisRef.arguments?.getShort(key ?: property.name, defaultValue) ?: defaultValue
    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Short) {
        thisRef.checkArguments()
        thisRef.arguments!!.putShort(key ?: property.name, value)
    }
}

fun Fragment.shortArrayExtra(key: String? = null, defaultValue: () -> ShortArray = { kotlin.ShortArray(0) }): ReadWriteProperty<Fragment, ShortArray> = object : ReadWriteProperty<Fragment, ShortArray> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): ShortArray {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getShortArray(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: ShortArray) {
        thisRef.checkArguments()
        thisRef.arguments!!.putShortArray(key ?: property.name, value)
    }
}

fun Fragment.longExtra(key: String? = null, defaultValue: Long = 0L): ReadWriteProperty<Fragment, Long> = object : ReadWriteProperty<Fragment, Long> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Long = thisRef.arguments?.getLong(key ?: property.name, defaultValue) ?: defaultValue
    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Long) {
        thisRef.checkArguments()
        thisRef.arguments!!.putLong(key ?: property.name, value)
    }
}

fun Fragment.longArrayExtra(key: String? = null, defaultValue: () -> LongArray = { kotlin.LongArray(0) }): ReadWriteProperty<Fragment, LongArray> = object : ReadWriteProperty<Fragment, LongArray> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): LongArray {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getLongArray(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: LongArray) {
        thisRef.checkArguments()
        thisRef.arguments!!.putLongArray(key ?: property.name, value)
    }
}

fun Fragment.floatExtra(key: String? = null, defaultValue: Float = 0f): ReadWriteProperty<Fragment, Float> = object : ReadWriteProperty<Fragment, Float> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Float = thisRef.arguments?.getFloat(key ?: property.name, defaultValue) ?: defaultValue
    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Float) {
        thisRef.checkArguments()
        thisRef.arguments!!.putFloat(key ?: property.name, value)
    }
}

fun Fragment.floatArrayExtra(key: String? = null, defaultValue: () -> FloatArray = { kotlin.FloatArray(0) }): ReadWriteProperty<Fragment, FloatArray> = object : ReadWriteProperty<Fragment, FloatArray> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): FloatArray {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getFloatArray(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: FloatArray) {
        thisRef.checkArguments()
        thisRef.arguments!!.putFloatArray(key ?: property.name, value)
    }
}

fun Fragment.doubleExtra(key: String? = null, defaultValue: Double = 0.0): ReadWriteProperty<Fragment, Double> = object : ReadWriteProperty<Fragment, Double> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Double = thisRef.arguments?.getDouble(key ?: property.name, defaultValue) ?: defaultValue
    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Double) {
        thisRef.checkArguments()
        thisRef.arguments!!.putDouble(key ?: property.name, value)
    }
}

fun Fragment.doubleArrayExtra(key: String? = null, defaultValue: () -> DoubleArray = { kotlin.DoubleArray(0) }): ReadWriteProperty<Fragment, DoubleArray> = object : ReadWriteProperty<Fragment, DoubleArray> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): DoubleArray {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getDoubleArray(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: DoubleArray) {
        thisRef.checkArguments()
        thisRef.arguments!!.putDoubleArray(key ?: property.name, value)
    }
}

fun Fragment.charSequenceListExtra(key: String? = null, defaultValue: () -> List<CharSequence> = { kotlin.collections.emptyList() }): ReadWriteProperty<Fragment, List<CharSequence>> = object : ReadWriteProperty<Fragment, List<CharSequence>> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): List<CharSequence> {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getCharSequenceArrayList(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: List<CharSequence>) {
        thisRef.checkArguments()
        thisRef.arguments!!.putCharSequenceArrayList(key ?: property.name, ArrayList(value))
    }
}

fun Fragment.charSequenceExtra(key: String? = null, defaultValue: () -> CharSequence = { "" }): ReadWriteProperty<Fragment, CharSequence> = object : ReadWriteProperty<Fragment, CharSequence> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): CharSequence {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getCharSequence(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: CharSequence) {
        thisRef.checkArguments()
        thisRef.arguments!!.putCharSequence(key ?: property.name, value)
    }
}

fun Fragment.charSequenceArrayExtra(key: String? = null, defaultValue: () -> Array<CharSequence> = { kotlin.emptyArray() }): ReadWriteProperty<Fragment, Array<CharSequence>> = object : ReadWriteProperty<Fragment, Array<CharSequence>> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Array<CharSequence> {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getCharSequenceArray(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Array<CharSequence>) {
        thisRef.checkArguments()
        thisRef.arguments!!.putCharSequenceArray(key ?: property.name, value)
    }
}

fun Fragment.charExtra(key: String? = null, defaultValue: Char = 0.toChar()): ReadWriteProperty<Fragment, Char> = object : ReadWriteProperty<Fragment, Char> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Char = thisRef.arguments?.getChar(key ?: property.name, defaultValue) ?: defaultValue

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Char) {
        thisRef.checkArguments()
        thisRef.arguments!!.putChar(key ?: property.name, value)
    }
}

fun Fragment.charArrayExtra(key: String? = null, defaultValue: () -> CharArray = { kotlin.CharArray(0) }): ReadWriteProperty<Fragment, CharArray> = object : ReadWriteProperty<Fragment, CharArray> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): CharArray {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getCharArray(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: CharArray) {
        thisRef.checkArguments()
        thisRef.arguments!!.putCharArray(key ?: property.name, value)
    }
}

fun Fragment.byteExtra(key: String? = null, defaultValue: Byte = 0): ReadWriteProperty<Fragment, Byte> = object : ReadWriteProperty<Fragment, Byte> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Byte = thisRef.arguments?.getByte(key ?: property.name, defaultValue) ?: defaultValue
    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Byte) {
        thisRef.checkArguments()
        thisRef.arguments!!.putByte(key ?: property.name, value)
    }
}

fun Fragment.byteArrayExtra(key: String? = null, defaultValue: () -> ByteArray = { kotlin.ByteArray(0) }): ReadWriteProperty<Fragment, ByteArray> = object : ReadWriteProperty<Fragment, ByteArray> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): ByteArray {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getByteArray(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: ByteArray) {
        thisRef.checkArguments()
        thisRef.arguments!!.putByteArray(key ?: property.name, value)
    }
}

fun Fragment.bundleExtra(key: String? = null, defaultValue: () -> Bundle = { android.os.Bundle() }): ReadWriteProperty<Fragment, Bundle> = object : ReadWriteProperty<Fragment, Bundle> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Bundle {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getBundle(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Bundle) {
        thisRef.checkArguments()
        thisRef.arguments!!.putBundle(key ?: property.name, value)
    }
}

fun Fragment.booleanExtra(key: String? = null, defaultValue: Boolean = false): ReadWriteProperty<Fragment, Boolean> = object : ReadWriteProperty<Fragment, Boolean> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Boolean = thisRef.arguments?.getBoolean(key ?: property.name, defaultValue) ?: defaultValue
    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Boolean) {
        thisRef.checkArguments()
        thisRef.arguments!!.putBoolean(key ?: property.name, value)
    }
}

fun Fragment.booleanArrayExtra(key: String? = null, defaultValue: () -> BooleanArray = { kotlin.BooleanArray(0) }): ReadWriteProperty<Fragment, BooleanArray> = object : ReadWriteProperty<Fragment, BooleanArray> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): BooleanArray {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getBooleanArray(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: BooleanArray) {
        thisRef.checkArguments()
        thisRef.arguments!!.putBooleanArray(key ?: property.name, value)
    }
}

inline fun <reified T : Serializable?> Fragment.serializableExtra(key: String? = null, crossinline defaultValue: () -> T = { null as T }): ReadWriteProperty<Fragment, T> = object : ReadWriteProperty<Fragment, T> {
    private fun Fragment.checkArguments() {
        if (arguments != null) return
        val field = Fragment::class.java.getDeclaredField("mArguments")
        val isAccessible = field.isAccessible
        if (!isAccessible) field.isAccessible = true
        field.set(this, android.os.Bundle())
        if (!field.isAccessible) field.isAccessible = false
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getSerializable(key ?: property.name) as T
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.checkArguments()
        thisRef.arguments!!.putSerializable(key ?: property.name, value)
    }
}

inline fun <reified T : Parcelable?> Fragment.parcelableExtra(key: String? = null, crossinline defaultValue: () -> T = { null as T }): ReadWriteProperty<Fragment, T> = object : ReadWriteProperty<Fragment, T> {
    private fun Fragment.checkArguments() {
        if (arguments != null) return
        val field = Fragment::class.java.getDeclaredField("mArguments")
        val isAccessible = field.isAccessible
        if (!isAccessible) field.isAccessible = true
        field.set(this, android.os.Bundle())
        if (!field.isAccessible) field.isAccessible = false
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val intent = thisRef.arguments ?: return defaultValue()
        return if (!intent.containsKey(key ?: property.name)) defaultValue()
        else intent.getParcelable<T>(key ?: property.name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.checkArguments()
        thisRef.arguments!!.putParcelable(key ?: property.name, value)
    }
}

private fun Fragment.checkArguments() {
    if (arguments != null) return
    val field = Fragment::class.java.getDeclaredField("mArguments")
    val isAccessible = field.isAccessible
    if (!isAccessible) field.isAccessible = true
    field.set(this, android.os.Bundle())
    if (!field.isAccessible) field.isAccessible = false
}