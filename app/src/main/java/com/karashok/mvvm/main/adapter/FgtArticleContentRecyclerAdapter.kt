package com.karashok.mvvm.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.karashok.common.data.ArticleItem
import com.karashok.mvvm.R

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name FgtArticleContentRecyclerAdapter
 * @date 2019/08/08 14:10
 **/
class FgtArticleContentRecyclerAdapter: BaseQuickAdapter<ArticleItem,BaseViewHolder>(R.layout.layout_item_fgt_article_content) {

    override fun convert(helper: BaseViewHolder?, item: ArticleItem?) {
        helper?.let {
            it.setText(R.id.layout_item_fgt_article_content_title_tv,item?.title)
                .setText(R.id.layout_item_fgt_article_content_date_tv,item?.publishTime.toString())
        }
    }
}