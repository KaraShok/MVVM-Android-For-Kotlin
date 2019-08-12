package com.karashok.mvvm.main.viewmodel

import android.app.Application
import com.karashok.common.data.FriendList
import com.karashok.common.data.HotSearch
import com.karashok.common.net.BaseAndroidViewModel
import com.karashok.common.net.BaseLiveData

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name HotSearchFgtViewModel
 * @date 2019/08/08 19:25
 **/
class HotSearchFgtViewModel(app: Application): BaseAndroidViewModel(app) {

    val mHotSearchLiveData: BaseLiveData<MutableList<HotSearch>> = BaseLiveData()
    val mFriendListLiveData: BaseLiveData<MutableList<FriendList>> = BaseLiveData()

    fun getHotSearchList(){
        mApiService.getHotSearchList(mHotSearchLiveData)
    }

    fun getFriendList(){
        mApiService.getFriendList(mFriendListLiveData)
    }
}