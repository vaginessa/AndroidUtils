package io.androidutils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import java.util.*
import kotlin.properties.Delegates

interface Presenter {
    fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View
    fun onBindView(view: View, item: Any?, position: Int)
    fun onUnbindView(view: View) = Unit
}

interface PresenterSelector {
    fun getPresenter(item: Any?): Presenter
}

interface TitleSelector {
    fun getTitle(index: Int, item: Any?): CharSequence
}

interface WidthSelector {
    fun getWidth(index: Int, item: Any?): Float
}

var RecyclerView.presenterSelector: PresenterSelector?
    get() = getTag(R.id.tag_presenter_selector) as PresenterSelector?
    set(value) {
        setTag(R.id.tag_presenter_selector, value)
        adapter = if (value != null) RecyclerViewArrayAdapter(value) else null
    }

val RecyclerView.items: MutableSwapList<Any?>
    get() {
        return adapter as? RecyclerViewArrayAdapter? ?: throw IllegalStateException("No presenter selector set")
    }

var AdapterView<*>.presenterSelector: PresenterSelector?
    get() = getTag(R.id.tag_presenter_selector) as PresenterSelector?
    set(value) {
        setTag(R.id.tag_presenter_selector, value)
        adapter = if (value != null) ListViewArrayAdapter(value) else null
    }

val AdapterView<*>.items: MutableSwapList<Any?>
    get() {
        return adapter as? ListViewArrayAdapter? ?: throw IllegalStateException("No presenter selector set")
    }

var ViewPager.pageTitleSelector: TitleSelector?
    get() = getTag(R.id.tag_title_selector) as TitleSelector?
    set(value) {
        setTag(R.id.tag_title_selector, value)
        (adapter as? ViewPagerArrayAdapter?)?.titleSelector = value
        (adapter as? ViewPagerFragmentAdapter?)?.titleSelector = value
    }

var ViewPager.pageWidthSelector: WidthSelector?
    get() = getTag(R.id.tag_width_selector) as WidthSelector?
    set(value) {
        setTag(R.id.tag_width_selector, value)
        (adapter as? ViewPagerArrayAdapter?)?.widthSelector = value
        (adapter as? ViewPagerFragmentAdapter?)?.widthSelector = value
    }

var ViewPager.presenterSelector: PresenterSelector?
    get() = getTag(R.id.tag_presenter_selector) as PresenterSelector?
    set(value) {
        setTag(R.id.tag_presenter_selector, value)
        adapter = if (value != null) ViewPagerArrayAdapter(value).apply {
            titleSelector = pageTitleSelector
            widthSelector = pageWidthSelector
        } else null
    }

var ViewPager.fragmentManager: FragmentManager?
    get() = getTag(R.id.tag_fragment_manager) as FragmentManager?
    set(value) {
        setTag(R.id.tag_fragment_manager, value)
        adapter = if (value != null) ViewPagerFragmentAdapter(value).apply {
            titleSelector = pageTitleSelector
            widthSelector = pageWidthSelector
        } else null
    }

val ViewPager.fragments: MutableSwapList<Fragment>
    get() {
        return adapter as? ViewPagerFragmentAdapter? ?: throw IllegalStateException("No fragment manager set")
    }

val ViewPager.items: MutableSwapList<Any?>
    get() {
        return adapter as? ViewPagerArrayAdapter? ?: throw IllegalStateException("No presenter selector set")
    }

private class ViewPagerFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm), MutableSwapList<Fragment> {
    var titleSelector by Delegates.observable(null as TitleSelector?) { _, _, _ -> notifyDataSetChanged() }
    var widthSelector by Delegates.observable(null as WidthSelector?) { _, _, _ -> notifyDataSetChanged() }
    private val list = arrayListOf<Fragment>()

    override fun getItem(position: Int): Fragment = get(position)

    override fun getCount(): Int = size

    override val size: Int get() = list.size

    override fun contains(element: Fragment): Boolean = list.contains(element)

    override fun containsAll(elements: Collection<Fragment>): Boolean = list.containsAll(elements)

    override fun get(index: Int): Fragment = list[index]

    override fun indexOf(element: Fragment): Int = list.indexOf(element)

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun getItemPosition(`object`: Any): Int {
        val index = indexOf(`object`)
        return if (index < 0) PagerAdapter.POSITION_NONE else index
    }

    override fun swap(sourceIndex: Int, targetIndex: Int) {
        Collections.swap(list, sourceIndex, targetIndex)
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? = titleSelector?.getTitle(position, get(position)) ?: super.getPageTitle(position)

    override fun getPageWidth(position: Int): Float = widthSelector?.getWidth(position, get(position)) ?: super.getPageWidth(position)

    override fun iterator(): MutableIterator<Fragment> = object : MutableIterator<Fragment> {
        private var position = 0
        override fun hasNext(): Boolean = position < size - 1
        override fun next(): Fragment = get(position++)
        override fun remove() {
            removeAt(position--)
        }
    }

    override fun lastIndexOf(element: Fragment): Int = list.lastIndexOf(element)

    override fun add(element: Fragment): Boolean {
        val result = list.add(element)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun add(index: Int, element: Fragment) {
        list.add(index, element)
        notifyDataSetChanged()
    }

    override fun addAll(index: Int, elements: Collection<Fragment>): Boolean {
        val result = list.addAll(index, elements)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun addAll(elements: Collection<Fragment>): Boolean {
        val result = list.addAll(elements)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun clear() {
        val count = size
        list.clear()
        if (count > 0) notifyDataSetChanged()
    }

    override fun listIterator(): MutableListIterator<Fragment> = listIterator(0)

    override fun listIterator(index: Int): MutableListIterator<Fragment> = object : MutableListIterator<Fragment> {
        private var position = index
        override fun hasPrevious(): Boolean = position > 0
        override fun nextIndex(): Int = position + 1
        override fun previous(): Fragment = get(--position)
        override fun previousIndex(): Int = position - 1
        override fun add(element: Fragment) = add(position, element)
        override fun hasNext(): Boolean = position < size - 1
        override fun next(): Fragment = get(position++)
        override fun remove() {
            removeAt(position--)
        }

        override fun set(element: Fragment) {
            set(position, element)
        }
    }

    override fun remove(element: Fragment): Boolean {
        val result = list.remove(element)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun removeAll(elements: Collection<Fragment>): Boolean {
        val result = list.removeAll(elements)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun removeAt(index: Int): Fragment {
        val result = list.removeAt(index)
        notifyDataSetChanged()
        return result
    }

    override fun retainAll(elements: Collection<Fragment>): Boolean {
        val result = list.retainAll(elements)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun set(index: Int, element: Fragment): Fragment {
        val result = list.set(index, element)
        notifyDataSetChanged()
        return result
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<Fragment> = ArrayList(list).subList(fromIndex, toIndex)
}

private class ViewPagerArrayAdapter(private val presenterSelector: PresenterSelector) : PagerAdapter(), MutableSwapList<Any?> {
    var titleSelector by Delegates.observable(null as TitleSelector?) { _, _, _ -> notifyDataSetChanged() }
    var widthSelector by Delegates.observable(null as WidthSelector?) { _, _, _ -> notifyDataSetChanged() }
    private val list = arrayListOf<Any?>()
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = get(position)
        val presenter = presenterSelector.getPresenter(item)
        val view = presenter.onCreateView(LayoutInflater.from(container.context), container)
        presenter.onBindView(view, item, position)
        view.setTag(R.id.tag_presenter, presenter)
        view.setTag(R.id.tag_object, item)
        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return view
    }

    override fun swap(sourceIndex: Int, targetIndex: Int) {
        Collections.swap(list, sourceIndex, targetIndex)
        notifyDataSetChanged()
    }

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun getPageTitle(position: Int): CharSequence? {
        return titleSelector?.getTitle(position, get(position)) ?: super.getPageTitle(position)
    }

    override fun getPageWidth(position: Int): Float {
        return widthSelector?.getWidth(position, get(position)) ?: super.getPageWidth(position)
    }

    override fun getItemPosition(`object`: Any): Int {
        val index = indexOf((`object` as View).getTag(R.id.tag_object))
        return if (index < 0) POSITION_NONE else index
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
    override fun getCount(): Int = size

    override val size: Int get() = list.size

    override fun contains(element: Any?): Boolean = list.contains(element)

    override fun containsAll(elements: Collection<Any?>): Boolean = list.containsAll(elements)

    override fun get(index: Int): Any? = list[index]

    override fun indexOf(element: Any?): Int = list.indexOf(element)

    override fun iterator(): MutableIterator<Any?> = object : MutableIterator<Any?> {
        private var index = 0
        override fun hasNext(): Boolean = index < size - 1
        override fun next(): Any? = get(index++)
        override fun remove() {
            removeAt(index--)
        }
    }

    override fun lastIndexOf(element: Any?): Int = list.lastIndexOf(element)

    override fun add(element: Any?): Boolean {
        val result = list.add(element)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun add(index: Int, element: Any?) {
        list.add(index, element)
        notifyDataSetChanged()
    }

    override fun addAll(index: Int, elements: Collection<Any?>): Boolean {
        val result = list.addAll(index, elements)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun addAll(elements: Collection<Any?>): Boolean {
        val result = list.addAll(elements)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun clear() {
        val count = size
        list.clear()
        if (count > 0) notifyDataSetChanged()
    }

    override fun listIterator(): MutableListIterator<Any?> = listIterator(0)

    override fun listIterator(index: Int): MutableListIterator<Any?> = object : MutableListIterator<Any?> {
        private var position = index
        override fun hasPrevious(): Boolean = position > 0
        override fun nextIndex(): Int = position + 1
        override fun previous(): Any? = get(--position)
        override fun previousIndex(): Int = position - 1
        override fun add(element: Any?) = add(position, element)
        override fun hasNext(): Boolean = position < size - 1
        override fun next(): Any? = get(position++)
        override fun remove() {
            removeAt(position--)
        }

        override fun set(element: Any?) {
            set(position, element)
        }
    }

    override fun remove(element: Any?): Boolean {
        val result = list.remove(element)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun removeAll(elements: Collection<Any?>): Boolean {
        val result = list.removeAll(elements)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun removeAt(index: Int): Any? {
        val result = list.removeAt(index)
        notifyDataSetChanged()
        return result
    }

    override fun retainAll(elements: Collection<Any?>): Boolean {
        val result = list.retainAll(elements)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun set(index: Int, element: Any?): Any? {
        val result = list.set(index, element)
        notifyDataSetChanged()
        return result
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<Any?> = ArrayList(list).subList(fromIndex, toIndex)
}

private class ListViewArrayAdapter(private val presenter: PresenterSelector) : BaseAdapter(), MutableSwapList<Any?> {
    private val list = arrayListOf<Any?>()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val view = convertView ?: run {
            val presenter = presenter.getPresenter(item)
            val view = presenter.onCreateView(LayoutInflater.from(parent.context), parent)
            view.apply {
                setTag(R.id.tag_presenter, presenter)
                addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                    override fun onViewAttachedToWindow(v: View?) = Unit
                    override fun onViewDetachedFromWindow(v: View?) {
                        presenter.onUnbindView(this@apply)
                    }
                })
            }
        }
        val presenter = view.getTag(R.id.tag_presenter) as Presenter
        presenter.onBindView(view, item, position)
        return view
    }

    override fun swap(sourceIndex: Int, targetIndex: Int) {
        Collections.swap(list, sourceIndex, targetIndex)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any? = get(position)

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = size

    override val size: Int get() = list.size

    override fun contains(element: Any?): Boolean = list.contains(element)

    override fun containsAll(elements: Collection<Any?>): Boolean = list.containsAll(elements)

    override fun get(index: Int): Any? = list[index]

    override fun indexOf(element: Any?): Int = list.indexOf(element)

    override fun iterator(): MutableIterator<Any?> = object : MutableIterator<Any?> {
        private var index = 0
        override fun hasNext(): Boolean = index < size - 1
        override fun next(): Any? = get(index++)
        override fun remove() {
            removeAt(index--)
        }
    }

    override fun lastIndexOf(element: Any?): Int = list.lastIndexOf(element)

    override fun add(element: Any?): Boolean {
        val result = list.add(element)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun add(index: Int, element: Any?) {
        list.add(index, element)
        notifyDataSetChanged()
    }

    override fun addAll(index: Int, elements: Collection<Any?>): Boolean {
        val result = list.addAll(index, elements)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun addAll(elements: Collection<Any?>): Boolean {
        val result = list.addAll(elements)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun clear() {
        val count = size
        list.clear()
        if (count > 0) notifyDataSetChanged()
    }

    override fun listIterator(): MutableListIterator<Any?> = listIterator(0)

    override fun listIterator(index: Int): MutableListIterator<Any?> = object : MutableListIterator<Any?> {
        private var position = index
        override fun hasPrevious(): Boolean = position > 0
        override fun nextIndex(): Int = position + 1
        override fun previous(): Any? = get(--position)
        override fun previousIndex(): Int = position - 1
        override fun add(element: Any?) = add(position, element)
        override fun hasNext(): Boolean = position < size - 1
        override fun next(): Any? = get(position++)
        override fun remove() {
            removeAt(position--)
        }

        override fun set(element: Any?) {
            set(position, element)
        }
    }

    override fun remove(element: Any?): Boolean {
        val result = list.remove(element)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun removeAll(elements: Collection<Any?>): Boolean {
        val result = list.removeAll(elements)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun removeAt(index: Int): Any? {
        val result = list.removeAt(index)
        notifyDataSetChanged()
        return result
    }

    override fun retainAll(elements: Collection<Any?>): Boolean {
        val result = list.retainAll(elements)
        if (result) notifyDataSetChanged()
        return result
    }

    override fun set(index: Int, element: Any?): Any? {
        val result = list.set(index, element)
        notifyDataSetChanged()
        return result
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<Any?> = ArrayList(list).subList(fromIndex, toIndex)
}

private class ViewHolder(val presenter: Presenter, parent: ViewGroup) : RecyclerView.ViewHolder(presenter.onCreateView(LayoutInflater.from(parent.context), parent))

private class RecyclerViewArrayAdapter(private val selector: PresenterSelector) : RecyclerView.Adapter<ViewHolder>(), MutableSwapList<Any?> {
    private val list = arrayListOf<Any?>()
    private val positionTypeMap = SparseIntArray()
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.presenter?.onBindView(holder.itemView, list[position], position)
    }

    override fun onViewRecycled(holder: ViewHolder?) {
        holder?.presenter?.onUnbindView(holder.itemView)
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val presenter = selector.getPresenter(list[positionTypeMap[viewType]])
        return ViewHolder(presenter, parent)
    }

    override fun swap(sourceIndex: Int, targetIndex: Int) {
        Collections.swap(list, sourceIndex, targetIndex)
        notifyItemMoved(sourceIndex, targetIndex)
    }

    override fun getItemViewType(position: Int): Int {
        val item = get(position)
        val type = item?.javaClass?.hashCode() ?: 0
        positionTypeMap.put(type, position)
        return type
    }

    override val size: Int get() = list.size

    override fun contains(element: Any?): Boolean = list.contains(element)

    override fun containsAll(elements: Collection<Any?>): Boolean = list.containsAll(elements)

    override fun get(index: Int): Any? = list[index]

    override fun indexOf(element: Any?): Int = list.indexOf(element)

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun iterator(): MutableIterator<Any?> = object : MutableIterator<Any?> {
        private var index = 0
        override fun hasNext(): Boolean = index < size - 1
        override fun next(): Any? = get(index++)
        override fun remove() {
            removeAt(index--)
        }
    }

    override fun lastIndexOf(element: Any?): Int = list.lastIndexOf(element)

    override fun add(element: Any?): Boolean {
        add(size, element)
        return true
    }

    override fun add(index: Int, element: Any?) {
        list.add(index, element)
        notifyItemInserted(index)
    }

    override fun addAll(index: Int, elements: Collection<Any?>): Boolean {
        val result = list.addAll(index, elements)
        if (result) notifyItemRangeInserted(index, elements.size)
        return result
    }

    override fun addAll(elements: Collection<Any?>): Boolean = addAll(size, elements)

    override fun clear() {
        val count = size
        list.clear()
        if (count > 0) notifyItemRangeRemoved(0, count)
    }

    override fun listIterator(): MutableListIterator<Any?> = listIterator(0)

    override fun listIterator(index: Int): MutableListIterator<Any?> = object : MutableListIterator<Any?> {
        private var position = index
        override fun hasPrevious(): Boolean = position > 0
        override fun nextIndex(): Int = position + 1
        override fun previous(): Any? = get(--position)
        override fun previousIndex(): Int = position - 1
        override fun add(element: Any?) = add(position, element)
        override fun hasNext(): Boolean = position < size - 1
        override fun next(): Any? = get(position++)
        override fun remove() {
            removeAt(position--)
        }

        override fun set(element: Any?) {
            set(position, element)
        }
    }

    override fun remove(element: Any?): Boolean {
        val index = indexOf(element)
        if (index < 0) return false
        removeAt(index)
        return true
    }

    override fun removeAll(elements: Collection<Any?>): Boolean {
        var changed = false
        elements.forEach { if (remove(it)) changed = true }
        return changed
    }

    override fun removeAt(index: Int): Any? {
        val result = list.removeAt(index)
        notifyItemRemoved(index)
        return result
    }

    override fun retainAll(elements: Collection<Any?>): Boolean {
        val oldSize = size
        val result = list.retainAll(elements)
        if (result) {
            val newSize = size
            val min = Math.min(oldSize, newSize)
            if (min > 0) notifyItemRangeChanged(0, min)
            if (oldSize > newSize) notifyItemRangeRemoved(min, oldSize - newSize)
            else if (oldSize < newSize) notifyItemRangeInserted(min, newSize - oldSize)
        }
        return result
    }

    override fun set(index: Int, element: Any?): Any? {
        val result = list.set(index, element)
        notifyItemChanged(index)
        return result
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<Any?> = ArrayList(list).subList(fromIndex, toIndex)
}

interface MutableSwapList<T> : MutableList<T> {
    fun swap(sourceIndex: Int, targetIndex: Int)
}