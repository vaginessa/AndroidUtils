package io.androidutils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

fun <T : Activity> Context.newActivity(activityClass: KClass<T>): ActivityIntentBuilder<T> = ActivityIntentBuilder(Intent(this, activityClass.java), this)
fun Context.newActivity(intent: Intent): ActivityIntentBuilder<Activity> = ActivityIntentBuilder(intent, this)

fun Activity.stringListExtra(key: String? = null, defaultValue: () -> List<String> = { emptyList() }): ReadWriteProperty<Activity, List<String>> = object : ReadWriteProperty<Activity, List<String>> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): List<String> {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getStringArrayListExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: List<String>) {
        thisRef.intent.putExtra(key ?: property.name, ArrayList(value))
    }
}

fun Activity.stringExtra(key: String? = null, defaultValue: () -> String = { "" }): ReadWriteProperty<Activity, String> = object : ReadWriteProperty<Activity, String> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): String {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getStringExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: String) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.stringArrayExtra(key: String? = null, defaultValue: () -> Array<String> = { emptyArray() }): ReadWriteProperty<Activity, Array<String>> = object : ReadWriteProperty<Activity, Array<String>> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): Array<String> {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getStringArrayExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: Array<String>) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun <T : Parcelable> Activity.parcelableListExtra(key: String? = null, defaultValue: () -> List<T> = { emptyList() }): ReadWriteProperty<Activity, List<T>> = object : ReadWriteProperty<Activity, List<T>> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): List<T> {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getParcelableArrayListExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: List<T>) {
        thisRef.intent.putExtra(key ?: property.name, ArrayList(value))
    }
}

inline fun <reified T : Parcelable> Activity.parcelableArrayExtra(key: String? = null, crossinline defaultValue: () -> Array<T> = { emptyArray() }): ReadWriteProperty<Activity, Array<T>> = object : ReadWriteProperty<Activity, Array<T>> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): Array<T> {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getParcelableArrayExtra(key ?: property.name).map { it as T }.toTypedArray()
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: Array<T>) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.shortExtra(key: String? = null, defaultValue: Short = 0): ReadWriteProperty<Activity, Short> = object : ReadWriteProperty<Activity, Short> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): Short = thisRef.intent.getShortExtra(key ?: property.name, defaultValue)
    override fun setValue(thisRef: Activity, property: KProperty<*>, value: Short) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.shortArrayExtra(key: String? = null, defaultValue: () -> ShortArray = { ShortArray(0) }): ReadWriteProperty<Activity, ShortArray> = object : ReadWriteProperty<Activity, ShortArray> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): ShortArray {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getShortArrayExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: ShortArray) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.longExtra(key: String? = null, defaultValue: Long = 0L): ReadWriteProperty<Activity, Long> = object : ReadWriteProperty<Activity, Long> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): Long = thisRef.intent.getLongExtra(key ?: property.name, defaultValue)
    override fun setValue(thisRef: Activity, property: KProperty<*>, value: Long) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.longArrayExtra(key: String? = null, defaultValue: () -> LongArray = { LongArray(0) }): ReadWriteProperty<Activity, LongArray> = object : ReadWriteProperty<Activity, LongArray> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): LongArray {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getLongArrayExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: LongArray) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.floatExtra(key: String? = null, defaultValue: Float = 0f): ReadWriteProperty<Activity, Float> = object : ReadWriteProperty<Activity, Float> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): Float = thisRef.intent.getFloatExtra(key ?: property.name, defaultValue)
    override fun setValue(thisRef: Activity, property: KProperty<*>, value: Float) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.floatArrayExtra(key: String? = null, defaultValue: () -> FloatArray = { FloatArray(0) }): ReadWriteProperty<Activity, FloatArray> = object : ReadWriteProperty<Activity, FloatArray> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): FloatArray {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getFloatArrayExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: FloatArray) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.doubleExtra(key: String? = null, defaultValue: Double = 0.0): ReadWriteProperty<Activity, Double> = object : ReadWriteProperty<Activity, Double> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): Double = thisRef.intent.getDoubleExtra(key ?: property.name, defaultValue)
    override fun setValue(thisRef: Activity, property: KProperty<*>, value: Double) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.doubleArrayExtra(key: String? = null, defaultValue: () -> DoubleArray = { DoubleArray(0) }): ReadWriteProperty<Activity, DoubleArray> = object : ReadWriteProperty<Activity, DoubleArray> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): DoubleArray {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getDoubleArrayExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: DoubleArray) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.charSequenceListExtra(key: String? = null, defaultValue: () -> List<CharSequence> = { emptyList() }): ReadWriteProperty<Activity, List<CharSequence>> = object : ReadWriteProperty<Activity, List<CharSequence>> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): List<CharSequence> {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getCharSequenceArrayListExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: List<CharSequence>) {
        thisRef.intent.putExtra(key ?: property.name, ArrayList(value))
    }
}

fun Activity.charSequenceExtra(key: String? = null, defaultValue: () -> CharSequence = { "" }): ReadWriteProperty<Activity, CharSequence> = object : ReadWriteProperty<Activity, CharSequence> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): CharSequence {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getCharSequenceExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: CharSequence) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.charSequenceArrayExtra(key: String? = null, defaultValue: () -> Array<CharSequence> = { emptyArray() }): ReadWriteProperty<Activity, Array<CharSequence>> = object : ReadWriteProperty<Activity, Array<CharSequence>> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): Array<CharSequence> {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getCharSequenceArrayExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: Array<CharSequence>) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.charExtra(key: String? = null, defaultValue: Char = 0.toChar()): ReadWriteProperty<Activity, Char> = object : ReadWriteProperty<Activity, Char> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): Char = thisRef.intent.getCharExtra(key ?: property.name, defaultValue)

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: Char) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.charArrayExtra(key: String? = null, defaultValue: () -> CharArray = { CharArray(0) }): ReadWriteProperty<Activity, CharArray> = object : ReadWriteProperty<Activity, CharArray> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): CharArray {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getCharArrayExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: CharArray) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.byteExtra(key: String? = null, defaultValue: Byte = 0): ReadWriteProperty<Activity, Byte> = object : ReadWriteProperty<Activity, Byte> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): Byte = thisRef.intent.getByteExtra(key ?: property.name, defaultValue)
    override fun setValue(thisRef: Activity, property: KProperty<*>, value: Byte) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.byteArrayExtra(key: String? = null, defaultValue: () -> ByteArray = { ByteArray(0) }): ReadWriteProperty<Activity, ByteArray> = object : ReadWriteProperty<Activity, ByteArray> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): ByteArray {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getByteArrayExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: ByteArray) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.bundleExtra(key: String? = null, defaultValue: () -> Bundle = { Bundle() }): ReadWriteProperty<Activity, Bundle> = object : ReadWriteProperty<Activity, Bundle> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): Bundle {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getBundleExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: Bundle) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.booleanExtra(key: String? = null, defaultValue: Boolean = false): ReadWriteProperty<Activity, Boolean> = object : ReadWriteProperty<Activity, Boolean> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): Boolean = thisRef.intent.getBooleanExtra(key ?: property.name, defaultValue)
    override fun setValue(thisRef: Activity, property: KProperty<*>, value: Boolean) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

fun Activity.booleanArrayExtra(key: String? = null, defaultValue: () -> BooleanArray = { BooleanArray(0) }): ReadWriteProperty<Activity, BooleanArray> = object : ReadWriteProperty<Activity, BooleanArray> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): BooleanArray {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getBooleanArrayExtra(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: BooleanArray) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

inline fun <reified T : Serializable?> Activity.serializableExtra(key: String? = null, crossinline defaultValue: () -> T = { null as T }): ReadWriteProperty<Activity, T> = object : ReadWriteProperty<Activity, T> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getSerializableExtra(key ?: property.name) as T
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: T) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

inline fun <reified T : Parcelable?> Activity.parcelableExtra(key: String? = null, crossinline defaultValue: () -> T = { null as T }): ReadWriteProperty<Activity, T> = object : ReadWriteProperty<Activity, T> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        val intent = thisRef.intent
        return if (!intent.hasExtra(key ?: property.name)) defaultValue()
        else intent.getParcelableExtra<T>(key ?: property.name)
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: T) {
        thisRef.intent.putExtra(key ?: property.name, value)
    }
}

@Suppress("UNCHECKED_CAST")
class ActivityIntentBuilder<T : Activity> internal constructor(intent: Intent, private val context: Context) {
    private val intent = Intent(intent)
    private var options: Bundle? = null
    private val key = UUID.randomUUID().toString()
    fun putExtra(key: String, value: Serializable?): ActivityIntentBuilder<T> = apply {
        intent.putExtra(key, value)
    }

    fun putExtra(key: String, value: Parcelable?): ActivityIntentBuilder<T> = apply {
        intent.putExtra(key, value)
    }

    fun addFlag(flag: Int): ActivityIntentBuilder<T> = apply {
        intent.addFlags(flag)
    }

    fun onCreated(callBack: (T) -> Unit): ActivityIntentBuilder<T> = apply {
        onCreatedCallBacks.put(key) { callBack(it as T) }
        intent.putExtra(EXTRA_KEY, key)
    }

    fun onResult(callBack: (Int, Intent) -> Unit): ActivityIntentBuilder<T> = apply {
        onResultCallBacks.put(key, callBack)
        intent.putExtra(EXTRA_KEY, key)
    }

    fun setOptions(options: Bundle?): ActivityIntentBuilder<T> = apply {
        this.options = options
    }

    fun start() {
        init(context.applicationContext as Application)
        if (context !is Activity) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent, options)
    }

    companion object : Application.ActivityLifecycleCallbacks {
        private val EXTRA_KEY = ActivityIntentBuilder::class.java.name + ".key"
        private val onCreatedCallBacks = hashMapOf<String, (Activity) -> Unit>()
        private val onResultCallBacks = hashMapOf<String, (Int, Intent) -> Unit>()
        private val createdActivities = arrayListOf<Activity>()
        private var isInitialized = false

        private fun init(app: Application) {
            if (isInitialized) return
            app.registerActivityLifecycleCallbacks(this)
        }

        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
            createdActivities.remove(activity ?: return)
            val key = activity.intent.getStringExtra(EXTRA_KEY) ?: return
            val callBack = onResultCallBacks.remove(key) ?: return
            val codeField = Activity::class.java.getDeclaredField("mResultCode")
            val dataField = Activity::class.java.getDeclaredField("mResultData")
            codeField.isAccessible = true
            dataField.isAccessible = true
            val code = codeField.getInt(activity)
            val data = dataField.get(activity) as Intent? ?: Intent()
            callBack(code, data)
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            val key = activity?.intent?.getStringExtra(EXTRA_KEY) ?: return
            val callBack = onCreatedCallBacks.remove(key) ?: return
            callBack(activity)
        }
    }
}