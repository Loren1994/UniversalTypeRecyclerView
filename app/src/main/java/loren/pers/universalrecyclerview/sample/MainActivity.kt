package loren.pers.universalrecyclerview.sample

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.universal_item_body.*
import kotlinx.android.synthetic.main.universal_item_title.*
import loren.pers.universalrecyclerview.R
import loren.pers.universalrecyclerview.universal.UniversalAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: CustomerAdapter
    val data = mutableListOf<ParentListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val child1 = mutableListOf<ChildListItem>()
        val child2 = mutableListOf<ChildListItem>()
        val child3 = mutableListOf<ChildListItem>()
        repeat(3) {
            child1.add(ChildListItem("CONTENT - $it"))
        }
        repeat(4) {
            child2.add(ChildListItem("CONTENT - $it"))
        }
        repeat(2) {
            child3.add(ChildListItem("CONTENT - $it"))
        }
        data.add(ParentListItem(child1, "TITLE - 0"))
        data.add(ParentListItem(child2, "TITLE - 1"))
        data.add(ParentListItem(child2, "TITLE - 2"))
        data.add(ParentListItem(child1, "TITLE - 3"))
//        data.add(ParentListItem(child3, "TITLE - 4"))

//        universal_rv.adapter = CustomerAdapter(this, data)

        mAdapter = CustomerAdapter(this, data)
        val layoutManager = GridLayoutManager(this, 1)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (mAdapter.isTitle(position)) layoutManager.spanCount else 1
            }
        }
        universal_arv.layoutManager = layoutManager
        universal_arv.adapter = mAdapter

        add_btn.setOnClickListener { addLastTitle() }
        new_btn.setOnClickListener { addNewTitle() }
        universal_arv.setOnLoadingListener {
            val temp = mutableListOf<ChildListItem>()
            repeat(2) {
                temp.add(ChildListItem("ADD - $it"))
            }
            val parentListItem = ParentListItem(temp, "NEW - ${System.currentTimeMillis()}")
            data.add(parentListItem)
            mAdapter.refresh()
            universal_arv.hasNextPage(true)
        }
    }

    //标题不能重复 - hashMap
    private fun addNewTitle() {
        val temp = mutableListOf<ChildListItem>()
        repeat(2) {
            temp.add(ChildListItem("ADD - $it"))
        }
        val parentListItem = ParentListItem(temp, "NEW - ${System.currentTimeMillis()}")
        data.add(parentListItem)
        mAdapter.refresh()
    }

    private fun addLastTitle() {
        val temp = mutableListOf<ChildListItem>()
        repeat(1) {
            temp.add(ChildListItem("ADD"))
        }
        data.last().childList.addAll(temp)
        mAdapter.refresh()
    }
}

class CustomerAdapter(context: Context, data: MutableList<ParentListItem>) : UniversalAdapter(context, data) {
    override fun setTitleLayout() = R.layout.universal_item_title

    override fun setBodyLayout() = R.layout.universal_item_body

    override fun onBindItemView(holder: ViewHolder, parentIndex: Int, childIndex: Int) {
        holder.itemView.setOnClickListener { Toast.makeText(context, "$parentIndex - $childIndex", Toast.LENGTH_LONG).show() }
        if (holder.itemViewType == TITLE) {
            holder.title_tv.text = data[parentIndex].titleStr
        } else {
            holder.body_tv.text = data[parentIndex].childList[childIndex].cotent
        }
    }

}
