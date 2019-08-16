package com.karashok.mvvm.main.viewmodel

import android.app.Application
import com.karashok.common.data.KnowledgeSystemTab
import com.karashok.common.net.BaseAndroidViewModel
import com.karashok.common.net.BaseLiveData
import com.karashok.mvvm.main.net.ApiService

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name KnowledgeSystemFgtViewModel
 * @date 2019/08/08 21:05
 **/
class KnowledgeSystemFgtViewModel(app: Application): BaseAndroidViewModel<ApiService>(app) {

    override fun getApiService(): ApiService {
        return ApiService()
    }

    val mKnowledgeSystemTabLiveData: BaseLiveData<MutableList<KnowledgeSystemTab>> = BaseLiveData()

    fun getKnowledgeSystemTab(){
        mApiService.getKnowledgeSystemTab(mKnowledgeSystemTabLiveData)
    }
}