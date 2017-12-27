package io.example.androidutils.lists

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.androidutils.newActivity
import io.example.androidutils.R
import kotlinx.android.synthetic.main.activity_lists.*

class ListsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)
        recyclerView.setOnClickListener { newActivity(RecyclerViewActivity::class).start() }
        listView.setOnClickListener { newActivity(ListViewActivity::class).start() }
        viewPager.setOnClickListener { newActivity(ViewPagerActivity::class).start() }
        viewPagerFragments.setOnClickListener { newActivity(ViewPagerFragmentActivity::class).start() }
    }

}
