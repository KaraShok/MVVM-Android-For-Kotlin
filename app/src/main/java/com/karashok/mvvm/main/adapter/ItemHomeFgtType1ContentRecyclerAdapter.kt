package com.karashok.mvvm.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.karashok.common.data.Banner
import com.karashok.mvvm.R

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ItemHomeFgtType1ContentRecyclerAdapter
 * @date 2019/08/07 16:34
 **/
class ItemHomeFgtType1ContentRecyclerAdapter(dataList: MutableList<Banner>):
    BaseQuickAdapter<Banner,BaseViewHolder>(R.layout.layout_item_fgt_home_type_1_content,dataList) {

    override fun convert(helper: BaseViewHolder?, item: Banner?) {
        helper?.let {
            val imageView: SimpleDraweeView = it.getView(R.id.layout_item_fgt_home_type_1_content_img_sdv)
            imageView.setImageURI(item?.imagePath,mContext)
            it.setText(R.id.layout_item_fgt_home_type_1_content_title_tv,item?.title)
        }
    }
}