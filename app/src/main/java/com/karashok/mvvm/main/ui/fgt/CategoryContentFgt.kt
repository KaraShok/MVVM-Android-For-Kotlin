package com.karashok.mvvm.main.ui.fgt

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.karashok.common.ui.fgt.CommonBaseFgt
import com.karashok.common.utils.ToastU
import com.karashok.common.widget.MyDefaultRecycleItemDecoration
import com.karashok.mvvm.R
import com.karashok.mvvm.main.adapter.FgtCategoryContentRecyclerAdapter
import com.karashok.mvvm.main.viewmodel.CategoryFgtViewModel
import kotlinx.android.synthetic.main.layout_list_with_refresh.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name CategoryContentFgt
 * @date 2019/08/08 14:52
 **/
class CategoryContentFgt(var userID: String) : CommonBaseFgt() {

    private lateinit var mFgtViewModel: CategoryFgtViewModel
    private var mPageNo: Int = 1
    private val mAdapter: FgtCategoryContentRecyclerAdapter = FgtCategoryContentRecyclerAdapter()

    override fun onContentViewInit() {
        mFgtViewModel = ViewModelProviders.of(this).get(CategoryFgtViewModel::class.java)
        layout_list_with_refresh_content_rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layout_list_with_refresh_content_rv.adapter = mAdapter
        layout_list_with_refresh_content_rv.addItemDecoration(
            MyDefaultRecycleItemDecoration(
                MyDefaultRecycleItemDecoration.ITEM_VERTICAL,R.color.color_F5F5F5,1,0,0)
        )
        layout_list_with_refresh_srf.setOnRefreshListener {
            mPageNo = 1
            mFgtViewModel.getProjectList(mPageNo,userID)
        }
        layout_list_with_refresh_srf.setOnLoadMoreListener {
            mFgtViewModel.getProjectList(mPageNo,userID)
        }
        mAdapter.setOnItemClickListener(object: BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                ToastU.showShort(mAdapter.data.get(position).title)
            }

        })
    }

    override fun onInitData() {
        mFgtViewModel.mProjectListLiveData.observe(this, Observer {
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
        mFgtViewModel.getProjectList(mPageNo,userID)
    }

    override fun onErrorClick() {

    }

    override fun onCreateContentView(): Int {
        return R.layout.layout_list_with_refresh
    }

}