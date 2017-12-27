package io.example.androidutils.lists

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.androidutils.*
import io.example.androidutils.R
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPagerFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        pager.fragmentManager = supportFragmentManager
        pager.pageTitleSelector = object : TitleSelector {
            override fun getTitle(index: Int, item: Any?): CharSequence = (item as PageFragment).number.toString()
        }
        pager.fragments.addAll((0..5).map { createFragment(PageFragment::class).putExtra("number", it) })
        tabs.setupWithViewPager(pager)
        add.setOnClickListener {
            val fragment = pager.fragments.lastOrNull() as PageFragment?
            val number = fragment?.number ?: 0
            pager.fragments.add(createFragment(PageFragment::class).putExtra("number", number + 1))
        }
        remove.setOnClickListener {
            if (pager.fragments.isEmpty()) return@setOnClickListener
            pager.fragments.removeAt(pager.currentItem)
        }
    }

    class PageFragment : Fragment() {
        val number by intExtra()
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(android.R.layout.simple_list_item_1, container, false)
            view.findViewById<TextView>(android.R.id.text1).text = number.toString()
            return view
        }
    }
}