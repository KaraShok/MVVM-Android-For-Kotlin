package com.karashok.mvvm.main.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.karashok.common.utils.ToastU
import com.karashok.mvvm.main.data.FgtHomeContentEntity.FgtHomeContentEntity_Type_1
import com.karashok.mvvm.main.data.FgtHomeContentEntity.FgtHomeContentEntity_Type_2
import com.karashok.mvvm.main.data.FgtHomeContentItemType1Entity
import com.karashok.mvvm.main.data.FgtHomeContentItemType2Entity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.karashok.mvvm.R


/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name FgtHomeContentRecyclerAdapter
 * @date 2019/08/03 17:39
 **/
class FgtHomeContentRecyclerAdapter: BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder>(null) {


    init {
        addItemType(FgtHomeContentEntity_Type_1, R.layout.layout_item_fgt_home_type_1)
        addItemType(FgtHomeContentEntity_Type_2, R.layout.layout_item_fgt_home_type_2)
    }

    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity?) {
        when(item?.itemType){
            FgtHomeContentEntity_Type_1->{
                val itemType1Entity: FgtHomeContentItemType1Entity = item as FgtHomeContentItemType1Entity
                helper?.let {
                    val recycler: RecyclerView = it.getView(R.id.layout_item_fgt_home_type_1_rv)
                    recycler.layoutManager = LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false)
                    val adapter = ItemHomeFgtType1ContentRecyclerAdapter(itemType1Entity.data)
                    adapter.setOnItemClickListener(object : OnItemClickListener{
                        override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                            ToastU.showShort(itemType1Entity.data.get(position).title)
                        }

                    })
                    recycler.adapter = adapter
                }
            }
            FgtHomeContentEntity_Type_2->{
                val itemType2Entity: FgtHomeContentItemType2Entity = item as FgtHomeContentItemType2Entity
                helper?.let {
                    it.setText(R.id.layout_item_fgt_home_type_2_title_tv, itemType2Entity.data.title)
                        .setText(R.id.layout_item_fgt_home_type_2_author_tv, itemType2Entity.data.author)
                        .setText(R.id.layout_item_fgt_home_type_2_type_tv, itemType2Entity.data.chapterName)
                        .setText(R.id.layout_item_fgt_home_type_2_date_tv, itemType2Entity.data.niceDate)
                }
            }
        }
    }
}
