package loren.pers.universalrecyclerview.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import loren.pers.universalrecyclerview.R

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
        data.add(ParentListItem(child3, "TITLE - 2"))

//        universal_rv.adapter = CustomerAdapter(this, data)

        mAdapter = CustomerAdapter(this, data)
        mAdapter.onRemoveListener = { universal_arv.adapter!!.notifyDataSetChanged() }
        val layoutManager = GridLayoutManager(this, 1)
        //横向
        //layoutManager.orientation = GridLayoutManager.HORIZONTAL
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
            universal_arv.hasNextPage(data.size != 7)
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
