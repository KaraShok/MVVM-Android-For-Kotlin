package com.karashok.mvvm.main.ui.fgt

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.karashok.common.ui.fgt.CommonBaseFgt
import com.karashok.common.utils.ToastU
import com.karashok.common.widget.MyDefaultRecycleItemDecoration
import com.karashok.mvvm.R
import com.karashok.mvvm.main.adapter.FgtHomeContentRecyclerAdapter
import com.karashok.mvvm.main.data.FgtHomeContentEntity
import com.karashok.mvvm.main.data.FgtHomeContentItemType2Entity
import com.karashok.mvvm.main.viewmodel.KnowledgeSystemContentListFgtViewModel
import kotlinx.android.synthetic.main.layout_list_with_refresh.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name KnowledgeSystemContentListFgt
 * @date 2019/08/09 10:40
 **/
class KnowledgeSystemContentListFgt(var userID: String) : CommonBaseFgt() {

    private lateinit var mFgtViewModel: KnowledgeSystemContentListFgtViewModel
    private val mAdapter: FgtHomeContentRecyclerAdapter = FgtHomeContentRecyclerAdapter()
    private var mPageNo: Int = 0

    override fun onContentViewInit() {
        mFgtViewModel = ViewModelProviders.of(this).get(KnowledgeSystemContentListFgtViewModel::class.java)
        layout_list_with_refresh_content_rv.layoutManager = LinearLayoutManager(context)
        mAdapter.setOnItemClickListener(object: BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val itemEntity = mAdapter.data.get(position)
                when(itemEntity.itemType){
                    FgtHomeContentEntity.FgtHomeContentEntity_Type_2 ->{
                        val itemType2Entity: FgtHomeContentItemType2Entity = itemEntity as FgtHomeContentItemType2Entity
                        ToastU.showShort(itemType2Entity.data.title)
                    }
                }
            }
        })
        layout_list_with_refresh_content_rv.adapter = mAdapter
        layout_list_with_refresh_content_rv.addItemDecoration(
            MyDefaultRecycleItemDecoration(
                MyDefaultRecycleItemDecoration.ITEM_VERTICAL,R.color.color_F5F5F5,1,0,0)
        )
        layout_list_with_refresh_srf.setOnRefreshListener {
            mPageNo = 0
            mFgtViewModel.getKnowledgeArticleList(mPageNo,userID)
        }
        layout_list_with_refresh_srf.setOnLoadMoreListener {
            mFgtViewModel.getKnowledgeArticleList(mPageNo,userID)
        }
    }

    override fun onInitData() {
        mFgtViewModel.mKnowledgeArticleListLiveData.observe(this, Observer {
            val homeItemListData = FgtHomeContentEntity.getHomeItemListData(it.datas)
            if (mPageNo == 0){
                layout_list_with_refresh_srf.finishRefresh()
                mAdapter.setNewData(homeItemListData)
            }else{
                layout_list_with_refresh_srf.finishLoadMore()
                mAdapter.addData(homeItemListData)
            }
            if (it.curPage >= it.pageCount){
                layout_list_with_refresh_srf.finishLoadMoreWithNoMoreData()
            }
            mPageNo++
        })
        mFgtViewModel.getKnowledgeArticleList(mPageNo,userID)
    }

    override fun onErrorClick() {

    }

    override fun onCreateContentView(): Int {
        return R.layout.layout_list_with_refresh
    }

}