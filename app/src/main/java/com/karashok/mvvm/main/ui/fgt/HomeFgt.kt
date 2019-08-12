package com.karashok.mvvm.main.ui.fgt

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.karashok.common.ui.fgt.CommonBaseFgt
import com.karashok.common.utils.ToastU
import com.karashok.common.widget.MyDefaultRecycleItemDecoration
import com.karashok.common.widget.MyDefaultRecycleItemDecoration.Companion.ITEM_VERTICAL
import com.karashok.mvvm.R
import com.karashok.mvvm.main.adapter.FgtHomeContentRecyclerAdapter
import com.karashok.mvvm.main.data.FgtHomeContentEntity
import com.karashok.mvvm.main.data.FgtHomeContentItemType1Entity
import com.karashok.mvvm.main.data.FgtHomeContentItemType2Entity
import com.karashok.mvvm.main.viewmodel.HomeFgtViewModel
import kotlinx.android.synthetic.main.layout_list_with_refresh.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name HomeFgt
 * @date 2019/07/17 18:08
 **/
class HomeFgt : CommonBaseFgt() {

    private lateinit var mFgtViewModel: HomeFgtViewModel
    private lateinit var mAdapter: FgtHomeContentRecyclerAdapter
    private val mDataList: MutableList<MultiItemEntity> = mutableListOf()
    private var mPageNo: Int = 0

    override fun onContentViewInit() {
        mFgtViewModel = ViewModelProviders.of(this).get(HomeFgtViewModel::class.java)
        mAdapter = FgtHomeContentRecyclerAdapter()
        mAdapter.setOnItemClickListener(object: BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val itemEntity = mDataList.get(position)
                when(itemEntity.itemType){
                    FgtHomeContentEntity.FgtHomeContentEntity_Type_2 ->{
                        val itemType2Entity: FgtHomeContentItemType2Entity = itemEntity as FgtHomeContentItemType2Entity
                        ToastU.showShort(itemType2Entity.data.title)
                    }
                }
            }

        })
        layout_list_with_refresh_content_rv.layoutManager = LinearLayoutManager(context)
        layout_list_with_refresh_content_rv.adapter = mAdapter
        layout_list_with_refresh_content_rv.addItemDecoration(MyDefaultRecycleItemDecoration(ITEM_VERTICAL,R.color.color_F5F5F5,1,0,0))
        layout_list_with_refresh_srf.setOnRefreshListener {
            mFgtViewModel.getBanner()
        }
        layout_list_with_refresh_srf.setOnLoadMoreListener {
            mFgtViewModel.getHomeArticles(mPageNo)
        }
    }

    override fun onInitData() {
        onStateLoading()
        mFgtViewModel.mArticlesListLiveData.observe(this, Observer {
            onContentView()
            if (mPageNo == 0){
                layout_list_with_refresh_srf.finishRefresh()
            }else{
                layout_list_with_refresh_srf.finishLoadMore()
            }
            if (it.curPage >= it.pageCount){
                layout_list_with_refresh_srf.finishLoadMoreWithNoMoreData()
            }
            val homeItemListData = FgtHomeContentEntity.getHomeItemListData(it.datas)
            mDataList.addAll(homeItemListData)
            mAdapter.notifyDataSetChanged()
            mPageNo++
        })
        mFgtViewModel.mBannerListLiveData.observe(this, Observer {
            mDataList.clear()
            val bannerEntity: FgtHomeContentItemType1Entity = FgtHomeContentItemType1Entity(it)
            mPageNo = 0
            mFgtViewModel.getHomeArticles(mPageNo)
            mDataList.add(bannerEntity)
            mAdapter.setNewData(mDataList)
        })
        mFgtViewModel.getBanner()
    }

    override fun onErrorClick() {

    }

    override fun onCreateContentView(): Int {
        return R.layout.layout_list_with_refresh
    }

}