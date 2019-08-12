package com.karashok.mvvm.main.data

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.karashok.common.data.ArticleItem
import com.karashok.common.data.Banner
import com.karashok.mvvm.main.data.FgtHomeContentEntity.FgtHomeContentEntity_Type_1
import com.karashok.mvvm.main.data.FgtHomeContentEntity.FgtHomeContentEntity_Type_2

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name FgtHomeContentEntity
 * @date 2019/08/05 16:38
 **/
object FgtHomeContentEntity {
    val FgtHomeContentEntity_Type_1 = 1
    val FgtHomeContentEntity_Type_2 = 2

    fun getHomeItemListData(articleItem: MutableList<ArticleItem>?): MutableList<MultiItemEntity>{
        val dataList: MutableList<MultiItemEntity> = mutableListOf()
        articleItem?.forEach {
            dataList.add(FgtHomeContentItemType2Entity(it))
        }
        return dataList
    }
}

data class FgtHomeContentItemType1Entity(var data: MutableList<Banner>) : MultiItemEntity{
    override fun getItemType(): Int {
        return FgtHomeContentEntity_Type_1
    }
}

data class FgtHomeContentItemType2Entity(var data: ArticleItem) : MultiItemEntity{
    override fun getItemType(): Int {
        return FgtHomeContentEntity_Type_2
    }
}