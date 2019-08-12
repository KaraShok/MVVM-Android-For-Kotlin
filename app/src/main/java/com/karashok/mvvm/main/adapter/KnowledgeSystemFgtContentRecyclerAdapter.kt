package com.karashok.mvvm.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.karashok.common.data.KnowledgeSystemTab
import com.karashok.mvvm.R

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name KnowledgeSystemFgtContentRecyclerAdapter
 * @date 2019/08/08 21:10
 **/
class KnowledgeSystemFgtContentRecyclerAdapter: BaseQuickAdapter<KnowledgeSystemTab, BaseViewHolder>(R.layout.layout_item_fgt_knowledge_system_content) {

    override fun convert(helper: BaseViewHolder?, item: KnowledgeSystemTab?) {
        helper?.let {
            val stringBuffer: StringBuilder = StringBuilder()
            item?.children?.forEach {
                stringBuffer.append(it.name)
                stringBuffer.append("    ")
            }
            it.setText(R.id.layout_item_fgt_knowledge_system_content_title_1_tv,item?.name)
                .setText(R.id.layout_item_fgt_knowledge_system_content_title_2_tv,stringBuffer.toString())
        }
    }
}