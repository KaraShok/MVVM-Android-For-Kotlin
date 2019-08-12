package com.karashok.mvvm.main.viewmodel

import android.app.Application
import com.karashok.common.data.ArticleItem
import com.karashok.common.data.Banner
import com.karashok.common.data.BaseListData
import com.karashok.common.net.BaseAndroidViewModel
import com.karashok.common.net.BaseLiveData

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name HomeFgtViewModel
 * @date 2019/08/03 16:04
 **/
class HomeFgtViewModel(app: Application): BaseAndroidViewModel(app) {

    val mArticlesListLiveData: BaseLiveData<BaseListData<ArticleItem>> = BaseLiveData()
    val mBannerListLiveData: BaseLiveData<MutableList<Banner>> = BaseLiveData()

    fun getHomeArticles(page: Int){
        mApiService.getHomeArticles(page,mArticlesListLiveData)
    }

    fun getBanner(){
        mApiService.getBanner(mBannerListLiveData)
    }
}