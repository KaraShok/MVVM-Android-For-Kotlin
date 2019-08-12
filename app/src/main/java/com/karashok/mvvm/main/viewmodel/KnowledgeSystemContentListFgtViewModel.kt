package com.karashok.mvvm.main.viewmodel

import android.app.Application
import com.karashok.common.data.ArticleItem
import com.karashok.common.data.BaseListData
import com.karashok.common.net.BaseAndroidViewModel
import com.karashok.common.net.BaseLiveData

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name KnowledgeSystemContentListFgtViewModel
 * @date 2019/08/09 10:52
 **/
class KnowledgeSystemContentListFgtViewModel(app: Application): BaseAndroidViewModel(app) {

    val mKnowledgeArticleListLiveData: BaseLiveData<BaseListData<ArticleItem>> = BaseLiveData()

    fun getKnowledgeArticleList(page: Int,cid: String){
        mApiService.getKnowledgeArticleList(page, cid, mKnowledgeArticleListLiveData)
    }
}