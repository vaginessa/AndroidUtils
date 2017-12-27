package io.example.androidutils.lists

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import io.androidutils.items
import io.androidutils.presenterSelector
import io.example.androidutils.AppPresenterSelector
import io.example.androidutils.R
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.presenterSelector = AppPresenterSelector
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.items.addAll((0..5))
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN or ItemTouchHelper.UP, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                recyclerView.items.swap(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                recyclerView.items.removeAt(viewHolder.adapterPosition)
            }
        })
        helper.attachToRecyclerView(recyclerView)
        add.setOnClickListener {
            val item = recyclerView.items.lastOrNull() as Int? ?: 0
            recyclerView.items.add(item + 1)
        }
    }
}