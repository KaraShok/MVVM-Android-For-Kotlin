package com.karashok.mvvm.main.ui.fgt

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.karashok.common.ui.fgt.CommonBaseFgt
import com.karashok.common.widget.MyDefaultRecycleItemDecoration
import com.karashok.common.widget.MyDefaultRecycleItemDecoration.Companion.ITEM_VERTICAL
import com.karashok.mvvm.R
import com.karashok.mvvm.main.adapter.FgtArticleContentRecyclerAdapter
import com.karashok.mvvm.main.viewmodel.ArticleFgtViewModel
import kotlinx.android.synthetic.main.layout_list_with_refresh.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ArticleContentFgt
 * @date 2019/08/08 11:40
 **/
class ArticleContentFgt(var userID: String) : CommonBaseFgt() {

    private lateinit var mFgtViewModel: ArticleFgtViewModel
    private val mAdapter: FgtArticleContentRecyclerAdapter = FgtArticleContentRecyclerAdapter()
    private var mPageNo: Int = 1

    override fun onContentViewInit() {
        mFgtViewModel = ViewModelProviders.of(this).get(ArticleFgtViewModel::class.java)
        layout_list_with_refresh_content_rv.layoutManager = LinearLayoutManager(context)
        layout_list_with_refresh_content_rv.adapter = mAdapter
        layout_list_with_refresh_content_rv.addItemDecoration(MyDefaultRecycleItemDecoration(ITEM_VERTICAL,R.color.color_F5F5F5,1,0,0))
        layout_list_with_refresh_srf.setOnRefreshListener {
            mPageNo = 1
            mFgtViewModel.getWXArticle(userID,mPageNo)
        }
        layout_list_with_refresh_srf.setOnLoadMoreListener {
            mFgtViewModel.getWXArticle(userID,mPageNo)
        }
    }

    override fun onInitData() {
        mFgtViewModel.mArticleListiveData.observe(this, Observer {
            if (mPageNo == 1){
                layout_list_with_refresh_srf.finishRefresh()
                mAdapter.setNewData(it.datas)
            }else{
                layout_list_with_refresh_srf.finishLoadMore()
                mAdapter.addData(it.datas)
            }
            if (it.curPage >= it.pageCount){
                layout_list_with_refresh_srf.finishLoadMoreWithNoMoreData()
            }
            mPageNo++
        })
        mFgtViewModel.getWXArticle(userID,mPageNo)
    }

    override fun onErrorClick() {

    }

    override fun onCreateContentView(): Int {
        return R.layout.layout_list_with_refresh
    }
}