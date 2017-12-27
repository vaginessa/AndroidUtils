package io.androidutils

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

fun Context.toast(@StringRes textRes: Int, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, textRes, length).show()
}

fun Context.toast(text: CharSequence, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, length).show()
}