package loren.pers.universalrecyclerview.sample

import android.content.Context
import android.widget.Toast
import kotlinx.android.synthetic.main.universal_item_body.*
import kotlinx.android.synthetic.main.universal_item_title.*
import loren.pers.universalrecyclerview.R
import loren.pers.universalrecyclerview.universal.UniversalAdapter

/**
 * Copyright © 2018/6/20 by loren
 */
class CustomerAdapter(context: Context, data: MutableList<ParentListItem>) : UniversalAdapter(context, data) {
    override fun setTitleLayout() = R.layout.universal_item_title

    override fun setBodyLayout() = R.layout.universal_item_body

    override fun onTitleBindItemView(holder: ViewHolder, parentIndex: Int) {
        holder.title_tv.text = data[parentIndex].titleStr
    }

    override fun onBodyBindItemView(holder: ViewHolder, parentIndex: Int, childIndex: Int) {
        holder.itemView.setOnClickListener { Toast.makeText(context, "$parentIndex - $childIndex", Toast.LENGTH_LONG).show() }
        holder.body_tv.text = data[parentIndex].childList[childIndex].cotent
    }
}