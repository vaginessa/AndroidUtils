package io.androidutils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import java.util.*

private val callBacks = hashMapOf<String, (Set<String>, Set<String>) -> Unit>()

@SuppressLint("NewApi")
fun Context.requestPermissions(vararg permissions: String, onResult: (granted: Set<String>, refused: Set<String>) -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val refusedPermissions = permissions.filter { checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
        if (refusedPermissions.isEmpty()) {
            onResult(permissions.toSet(), emptySet())
            return
        }
        val grantedPermissions = permissions.filter { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
        val key = UUID.randomUUID().toString()
        callBacks.put(key) { granted, refused -> onResult(granted + grantedPermissions, refused) }
        newActivity(PermissionsActivity::class)
                .putExtra("permissions", refusedPermissions.toTypedArray())
                .putExtra("key", key)
                .start()
    } else onResult(permissions.toSet(), emptySet())
}

@TargetApi(Build.VERSION_CODES.M)
class PermissionsActivity : Activity() {
    private val permissions by stringArrayExtra()
    private val key by stringExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(permissions, 0)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        finish()
        val callBack = callBacks.remove(key) ?: return
        val map = (0 until permissions.size).map { permissions[it] to (grantResults[it] == PackageManager.PERMISSION_GRANTED) }.toMap()
        callBack(map.filter { it.value }.keys, map.filter { !it.value }.keys)
    }
}
