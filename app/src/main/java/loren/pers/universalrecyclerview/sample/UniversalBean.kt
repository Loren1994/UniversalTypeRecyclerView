package loren.pers.universalrecyclerview.sample

import loren.pers.universalrecyclerview.universal.OnTypeList


data class ParentListItem(val childList: MutableList<ChildListItem>,
                          val titleStr: String = "") : OnTypeList<ChildListItem> {
    override fun getBody() = childList

    override fun getTitle() = titleStr
}

data class ChildListItem(val cotent: String = "")


/** example json
 *
 * [
 *  {
 *      title:"ssss",
 *      child_list:[
 *          { xxx:"sss" }
 *      ]
 *  },
 *  {
 *      title:"ssss",
 *      child_list:[
 *         { xxx:"sss" }
 *      ]
 *  }
 *]
 */