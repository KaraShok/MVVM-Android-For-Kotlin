package com.karashok.mvvm.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.karashok.common.data.ArticleItem
import com.karashok.mvvm.R

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name FgtCategoryContentRecyclerAdapter
 * @date 2019/08/08 15:12
 **/
class FgtCategoryContentRecyclerAdapter: BaseQuickAdapter<ArticleItem, BaseViewHolder>(R.layout.layout_item_fgt_category_content) {

    override fun convert(helper: BaseViewHolder?, item: ArticleItem?) {
        helper?.let {
            it.setText(R.id.layout_item_fgt_category_content_title_tv,item?.title)
                .setText(R.id.layout_item_fgt_category_content_name_tv,item?.author)
                .setText(R.id.layout_item_fgt_category_content_time_tv,item?.niceDate)

            val imageView: SimpleDraweeView = it.getView(R.id.layout_item_fgt_category_content_img_sdv)
            imageView.setImageURI(item?.envelopePic,mContext)
        }
    }

}