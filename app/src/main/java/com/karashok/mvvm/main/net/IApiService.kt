package com.karashok.mvvm.main.net

import com.karashok.common.data.*
import retrofit2.Call
import retrofit2.http.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name IApiService
 * @date 2019/08/03 11:51
 **/
interface IApiService {

    // 首页banner
    @GET("/banner/json")
    fun getBanner(): Call<BaseResult<MutableList<Banner>>>

    // 首页文章列表
    @GET("/article/list/{page}/json")
    fun getHomeArticles(@Path("page") page: Int): Call<BaseResult<BaseListData<ArticleItem>>>

    // 获取公众号分类列表
    @GET("/wxarticle/chapters/json")
    fun getWxarticleTab(): Call<BaseResult<MutableList<WxarticleChapters>>>

    // 查看某个公众号历史数据
    @GET("/wxarticle/list/{userID}/{page}/json")
    fun getWXArticle(@Path("userID") userID: String, @Path("page") page: Int): Call<BaseResult<BaseListData<ArticleItem>>>

    // 获取项目分类分类列表
    @GET("/project/tree/json")
    fun getProjectTab(): Call<BaseResult<MutableList<WxarticleChapters>>>

    // 获取项目
    @GET("/project/list/{page}/json")
    fun getProjectList(@Path("page") page: Int, @Query("cid") cid: String): Call<BaseResult<BaseListData<ArticleItem>>>

    // 大家都在搜
    @GET("/hotkey/json")
    fun getHotSearchList(): Call<BaseResult<MutableList<HotSearch>>>

    // 常用网站
    @GET("/friend/json")
    fun getFriendList(): Call<BaseResult<MutableList<FriendList>>>

    // 知识体系一级分类
    @GET("/tree/json")
    fun getKnowledgeSystemTab(): Call<BaseResult<MutableList<KnowledgeSystemTab>>>

    // 知识体系下的文章
    @GET("/article/list/{page}/json")
    fun getKnowledgeArticleList(@Path("page") page: Int,@Query("cid") cid: String): Call<BaseResult<BaseListData<ArticleItem>>>

    /****************************************************************************************************/

    //获取最新项目
    @GET("/article/listproject/{page}/json")
    fun getLatestProjects(@Path("page") page: Int): Call<BaseResult<BaseListData<ArticleItem>>>

    //登录
    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Call<BaseResult<UserResponse>>

    //注册
    @FormUrlEncoded
    @POST("/user/register")
    fun register(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("repassword") repassword: String): Call<BaseResult<UserResponse>>

    //退出登录
    @GET("/user/logout/json")
    fun logout(): Call<BaseResult<String>>

    //收藏文章列表
    @GET("/lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") page: Int): Call<BaseResult<BaseListData<ArticleItem>>>

    //收藏文章
    @POST("/lg/collect/{id}/json")
    fun collect(@Path("id") id: Int): Call<BaseResult<String>>

    //取消收藏文章
    @POST("/lg/uncollect_originId/{id}/json")
    fun uncollect(@Path("id") id: Int): Call<BaseResult<String>>

    //取消收藏文章--收藏页面
    @FormUrlEncoded
    @POST("/lg/uncollect/{id}/json")
    fun unCollectPage(@Path("id") id: Int, @Field("originId") originId: Int): Call<BaseResult<String>>
}