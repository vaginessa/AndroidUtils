package io.example.androidutils.lists

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.PopupMenu
import io.androidutils.items
import io.androidutils.presenterSelector
import io.example.androidutils.AppPresenterSelector
import io.example.androidutils.R
import kotlinx.android.synthetic.main.activity_list_view.*

class ListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        listView.presenterSelector = AppPresenterSelector
        listView.items.addAll((0..5))
        listView.setOnItemLongClickListener { _, view, position, _ ->
            val popup = PopupMenu(this, view)
            popup.menu.add(R.string.remove).setOnMenuItemClickListener { listView.items.removeAt(position);true }
            popup.show()
            true
        }
        add.setOnClickListener {
            val item = listView.items.lastOrNull() as Int? ?: 0
            listView.items.add(item + 1)
        }
    }
}