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
 * @name CategoryFgtViewModel
 * @date 2019/08/08 14:49
 **/
class CategoryFgtViewModel(app: Application): BaseAndroidViewModel<ApiService>(app) {

    override fun getApiService(): ApiService {
        return ApiService()
    }

    val mProjectTreeListLiveData: BaseLiveData<MutableList<WxarticleChapters>> = BaseLiveData()
    val mProjectListLiveData: BaseLiveData<BaseListData<ArticleItem>> = BaseLiveData()

    fun getProjectTab(){
        mApiService.getProjectTab(mProjectTreeListLiveData)
    }

    fun getProjectList(page: Int, cid: String){
        mApiService.getProjectList(page, cid, mProjectListLiveData)
    }
}