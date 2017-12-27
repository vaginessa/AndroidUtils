package io.example.androidutils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.androidutils.Presenter
import io.androidutils.PresenterSelector

object AppPresenterSelector : PresenterSelector {
    override fun getPresenter(item: Any?): Presenter = when (item) {
        is String -> StringPresenter
        is Int -> IntPresenter
        else -> throw IllegalArgumentException("Unknown item $item")
    }

    object IntPresenter : Presenter {
        override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
            return inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        }

        override fun onBindView(view: View, item: Any?, position: Int) {
            view.findViewById<TextView>(android.R.id.text1).text = item.toString()
        }

    }

    object StringPresenter : Presenter {
        override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
            return inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        }

        override fun onBindView(view: View, item: Any?, position: Int) {
            view.findViewById<TextView>(android.R.id.text1).text = item.toString()
        }
    }
}