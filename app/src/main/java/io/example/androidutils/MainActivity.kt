package io.example.androidutils

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.androidutils.newActivity
import io.androidutils.requestPermissions
import io.androidutils.toast
import io.example.androidutils.lists.ListsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lists.setOnClickListener { newActivity(ListsActivity::class).start() }
        permissions.setOnClickListener {
            requestPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
            ) { granted, refused ->
                val grantedStr = granted.joinToString("\n") { "$it: Granted" }
                val refusedStr = refused.joinToString("\n") { "$it: Refused" }
                toast("$grantedStr\n$refusedStr".trim())
            }
        }
    }
}
