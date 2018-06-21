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
abstract class UniversalAdapter(val context: Context, var data: MutableList<ParentListItem>) : RecyclerView.Adapter<UniversalAdapter.ViewHolder>() {

    val TITLE = 0
    private val BODY = 1
    private var titleIndexMap = hashMapOf<String, Int>()

    init {
        init()
    }

    private fun init() {
        titleIndexMap.clear()
        val tempList = data.filter { it.getBody().size != 0 }.toMutableList()
        data.clear()
        data.addAll(tempList)
        data.forEachIndexed { index, item ->
            var pos = 0
            repeat(index) {
                pos += data[it].getBody().size
            }
            pos += index
            titleIndexMap[item.getTitle()] = pos
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            if (viewType == TITLE) {
                ViewHolder(LayoutInflater.from(context).inflate(setTitleLayout(), parent, false))
            } else {
                ViewHolder(LayoutInflater.from(context).inflate(setBodyLayout(), parent, false))
            }

    override fun getItemViewType(position: Int) = if (isTitle(position)) TITLE else BODY

    fun isTitle(position: Int) = titleIndexMap.values.contains(position)

    abstract fun setTitleLayout(): Int

    abstract fun setBodyLayout(): Int

    abstract fun onTitleBindItemView(holder: ViewHolder, parentIndex: Int)

    abstract fun onBodyBindItemView(holder: ViewHolder, parentIndex: Int, childIndex: Int)

    override fun getItemCount() = data.sumBy { it.getBody().size } + data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var parentIndex = 0
        var childIndex = position + 1
        for (item in data) {
            if (childIndex > (item.getBody().size + 1)) {
                childIndex -= (item.getBody().size + 1)
                parentIndex++
            } else {
                break
            }
        }
        childIndex -= 2
        if (childIndex == -1) {
            onTitleBindItemView(holder, parentIndex)
        } else {
            onBodyBindItemView(holder, parentIndex, childIndex)
        }
    }

    //列表刷新
    fun refresh() {
        init()
        notifyDataSetChanged()
    }

    //列表移除某一项
    fun removeIndex(parentIndex: Int, childIndex: Int) {
        data[parentIndex].getBody().removeAt(childIndex)
        refresh()
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}