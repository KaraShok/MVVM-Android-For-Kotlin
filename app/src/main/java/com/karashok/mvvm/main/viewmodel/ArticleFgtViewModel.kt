package com.karashok.mvvm.main.viewmodel

import android.app.Application
import com.karashok.common.data.ArticleItem
import com.karashok.common.data.BaseListData
import com.karashok.common.data.WxarticleChapters
import com.karashok.common.net.BaseAndroidViewModel
import com.karashok.common.net.BaseLiveData
import com.karashok.mvvm.main.net.ApiService

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ArticleFgtViewModel
 * @date 2019/08/08 10:25
 **/
class ArticleFgtViewModel(app: Application): BaseAndroidViewModel<ApiService>(app) {

    override fun getApiService(): ApiService {
        return ApiService()
    }

    val mWxarticleChaptersListLiveData: BaseLiveData<MutableList<WxarticleChapters>> = BaseLiveData()
    val mArticleListiveData: BaseLiveData<BaseListData<ArticleItem>> = BaseLiveData()

    fun getWxarticleTab(){
        mApiService.getWxarticleTab(mWxarticleChaptersListLiveData)
    }

    fun getWXArticle(userID: String, page: Int){
        mApiService.getWXArticle(userID, page, mArticleListiveData)
    }
}