package com.karashok.common.net

import com.karashok.common.data.*
import com.karashok.common.utils.ToastU

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ApiService
 * @date 2019/08/03 14:28
 **/
class ApiService {

    val mIApiService: IApiService = RetrofitU.mRetrofit.create(IApiService::class.java)

    fun getHomeArticles(page: Int,liveData: BaseLiveData<BaseListData<ArticleItem>>){
        mIApiService.getHomeArticles(page)
            .enqueue(ApiCallBack{
                if (isSuccess){
                    liveData.postValue(data)
                }else{
                    ToastU.showShort(errorMsg)
                }
            })
    }

    fun getBanner(liveData: BaseLiveData<MutableList<Banner>>){
        mIApiService.getBanner()
            .enqueue(ApiCallBack{
                if (isSuccess){
                    liveData.postValue(data)
                }else{
                    ToastU.showShort(errorMsg)
                }
            })
    }

    fun getWxarticleTab(liveData: BaseLiveData<MutableList<WxarticleChapters>>){
        mIApiService.getWxarticleTab()
            .enqueue(ApiCallBack{
                if (isSuccess){
                    liveData.postValue(data)
                }else{
                    ToastU.showShort(errorMsg)
                }
            })
    }

    fun getWXArticle(userID: String, page: Int,liveData: BaseLiveData<BaseListData<ArticleItem>>){
        mIApiService.getWXArticle(userID, page)
            .enqueue(ApiCallBack{
                if (isSuccess){
                    liveData.postValue(data)
                }else{
                    ToastU.showShort(errorMsg)
                }
            })
    }

    fun getProjectTab(liveData: BaseLiveData<MutableList<WxarticleChapters>>){
        mIApiService.getProjectTab()
            .enqueue(ApiCallBack{
                if (isSuccess){
                    liveData.postValue(data)
                }else{
                    ToastU.showShort(errorMsg)
                }
            })
    }

    fun getProjectList(page: Int, cid: String,liveData: BaseLiveData<BaseListData<ArticleItem>>){
        mIApiService.getProjectList(page, cid)
            .enqueue(ApiCallBack{
                if (isSuccess){
                    liveData.postValue(data)
                }else{
                    ToastU.showShort(errorMsg)
                }
            })
    }

    fun getHotSearchList(liveData: BaseLiveData<MutableList<HotSearch>>){
        mIApiService.getHotSearchList()
            .enqueue(ApiCallBack{
                if (isSuccess){
                    liveData.postValue(data)
                }else{
                    ToastU.showShort(errorMsg)
                }
            })
    }

    fun getFriendList(liveData: BaseLiveData<MutableList<FriendList>>){
        mIApiService.getFriendList()
            .enqueue(ApiCallBack{
                if (isSuccess){
                    liveData.postValue(data)
                }else{
                    ToastU.showShort(errorMsg)
                }
            })
    }

    fun getKnowledgeSystemTab(liveData: BaseLiveData<MutableList<KnowledgeSystemTab>>){
        mIApiService.getKnowledgeSystemTab()
            .enqueue(ApiCallBack{
                if (isSuccess){
                    liveData.postValue(data)
                }else{
                    ToastU.showShort(errorMsg)
                }
            })
    }

    fun getKnowledgeArticleList(page: Int,cid: String,liveData: BaseLiveData<BaseListData<ArticleItem>>){
        mIApiService.getKnowledgeArticleList(page,cid)
            .enqueue(ApiCallBack{
                if (isSuccess){
                    liveData.postValue(data)
                }else{
                    ToastU.showShort(errorMsg)
                }
            })
    }
}