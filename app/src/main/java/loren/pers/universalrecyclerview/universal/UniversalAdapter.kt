package loren.pers.universalrecyclerview.universal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import loren.pers.universalrecyclerview.sample.ParentListItem

/**
 * Copyright © 2018/6/20 by loren
 */
abstract class UniversalAdapter(val context: Context, val data: MutableList<ParentListItem>) : RecyclerView.Adapter<UniversalAdapter.ViewHolder>() {

    val TITLE = 0
    private val BODY = 1
    private var titleIndexMap = hashMapOf<String, Int>()

    init {
        init()
    }

    private fun init() {
        data.forEachIndexed { index, item ->
            var pos = 0
            repeat(index) {
                pos += data[it].childList.size
            }
            pos += index
            titleIndexMap[item.titleStr] = pos
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == TITLE) {
            ViewHolder(LayoutInflater.from(context).inflate(setTitleLayout(), parent, false))
        } else {
            ViewHolder(LayoutInflater.from(context).inflate(setBodyLayout(), parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isTitle(position)) TITLE else BODY
    }

    fun isTitle(position: Int): Boolean {
        return titleIndexMap.values.contains(position)
    }

    abstract fun setTitleLayout(): Int

    abstract fun setBodyLayout(): Int

    abstract fun onBindItemView(holder: ViewHolder, parentIndex: Int, childIndex: Int)

    override fun getItemCount() = data.sumBy { it.childList.size } + data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var parentIndex = 0
        var childIndex = position + 1
        for (item in data) {
            if (childIndex > (item.childList.size + 1)) {
                childIndex -= (item.childList.size + 1)
                parentIndex++
            } else {
                break
            }
        }
        onBindItemView(holder, parentIndex, childIndex - 2)
    }

    fun refresh() {
        init()
        notifyDataSetChanged()
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}