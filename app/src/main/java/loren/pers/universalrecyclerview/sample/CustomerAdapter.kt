package loren.pers.universalrecyclerview.sample

import android.annotation.SuppressLint
import android.content.Context
import kotlinx.android.synthetic.main.universal_item_body.*
import kotlinx.android.synthetic.main.universal_item_title.*
import loren.pers.universalrecyclerview.R
import loren.pers.universalrecyclerview.universal.UniversalAdapter

/**
 * Copyright © 2018/6/20 by loren
 */
class CustomerAdapter(context: Context, data: MutableList<ParentListItem>) : UniversalAdapter(context, data) {

    /**
     * 此处为AutoRecyclerView控件的问题
     * 需要调用AutoRecyclerView.adapter!!.notifyDataSetChanged()
     * 普通RecyclerView不需要此回调,直接调用removeIndex()即可
     */
    var onRemoveListener: (() -> Unit)? = null

    override fun setTitleLayout() = R.layout.universal_item_title

    override fun setBodyLayout() = R.layout.universal_item_body

    override fun onTitleBindItemView(holder: ViewHolder, parentIndex: Int) {
        holder.title_tv.text = data[parentIndex].titleStr
    }

    @SuppressLint("SetTextI18n")
    override fun onBodyBindItemView(holder: ViewHolder, parentIndex: Int, childIndex: Int) {
        holder.itemView.setOnClickListener {
            //Toast.makeText(context, "$parentIndex - $childIndex", Toast.LENGTH_LONG).show()
            removeIndex(parentIndex, childIndex)
            onRemoveListener?.invoke()
        }
        holder.body_tv.text = "${data[parentIndex].childList[childIndex].cotent} - $parentIndex - $childIndex"
    }

}