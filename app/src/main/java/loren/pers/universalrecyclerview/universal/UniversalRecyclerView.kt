package loren.pers.universalrecyclerview.universal

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Copyright © 2018/6/20 by loren
 */
class UniversalRecyclerView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    //TODO
    var SPAN_COUNT = 3

    init {
        init()
    }

    private fun init() {
        val layoutManager = GridLayoutManager(context, SPAN_COUNT)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if ((adapter as UniversalAdapter).isTitle(position)) layoutManager.spanCount else 1
            }
        }
        setLayoutManager(layoutManager)
    }
}