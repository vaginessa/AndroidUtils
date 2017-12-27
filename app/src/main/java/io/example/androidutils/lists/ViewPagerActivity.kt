package io.example.androidutils.lists

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.androidutils.TitleSelector
import io.androidutils.items
import io.androidutils.pageTitleSelector
import io.androidutils.presenterSelector
import io.example.androidutils.AppPresenterSelector
import io.example.androidutils.R
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        pager.presenterSelector = AppPresenterSelector
        pager.pageTitleSelector = object : TitleSelector {
            override fun getTitle(index: Int, item: Any?): CharSequence = item.toString()
        }
        pager.items.addAll((0..5))
        tabs.setupWithViewPager(pager)
        add.setOnClickListener {
            val item = pager.items.lastOrNull() as Int? ?: 0
            pager.items.add(item + 1)
        }

        remove.setOnClickListener {
            if (pager.items.isEmpty()) return@setOnClickListener
            pager.items.removeAt(pager.currentItem)
        }
    }
}