package com.karashok.mvvm.main.ui.fgt

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.karashok.common.ui.fgt.CommonBaseFgt
import com.karashok.common.utils.ToastU
import com.karashok.mvvm.R
import com.karashok.mvvm.main.adapter.HotSearchFgtTagFlowLayoutContentAdapter
import com.karashok.mvvm.main.viewmodel.HotSearchFgtViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.layout_fgt_hot_search.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name HotSearchFgt
 * @date 2019/08/08 17:30
 **/
class HotSearchFgt : CommonBaseFgt() {

    private lateinit var mViewModel: HotSearchFgtViewModel
    private lateinit var mHotSearchAdapter: HotSearchFgtTagFlowLayoutContentAdapter
    private lateinit var mFriendListAdapter: HotSearchFgtTagFlowLayoutContentAdapter
    private val mHotSearchTagList: MutableList<String> = mutableListOf()
    private val mFriendListTagList: MutableList<String> = mutableListOf()

    override fun onContentViewInit() {
        mViewModel = ViewModelProviders.of(this).get(HotSearchFgtViewModel::class.java)
        mHotSearchAdapter = HotSearchFgtTagFlowLayoutContentAdapter(requireContext(),mHotSearchTagList)
        mFriendListAdapter = HotSearchFgtTagFlowLayoutContentAdapter(requireContext(),mFriendListTagList)
        layout_fgt_hot_search_hot_search_tfl.adapter= mHotSearchAdapter
        layout_fgt_hot_search_hot_search_tfl.setOnTagClickListener(object: TagFlowLayout.OnTagClickListener{
            override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
                ToastU.showShort(mHotSearchTagList.get(position))
                return true
            }

        })
        layout_fgt_hot_search_friend_list_tfl.adapter= mFriendListAdapter
        layout_fgt_hot_search_friend_list_tfl.setOnTagClickListener(object: TagFlowLayout.OnTagClickListener{
            override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
                ToastU.showShort(mHotSearchTagList.get(position))
                return true
            }

        })
    }

    override fun onInitData() {
        mViewModel.mHotSearchLiveData.observe(this, Observer {
            mHotSearchTagList.clear()
            it.forEach {
                mHotSearchTagList.add(it.name)
            }
            mHotSearchAdapter.notifyDataChanged()
        })
        mViewModel.mFriendListLiveData.observe(this, Observer {
            mFriendListTagList.clear()
            it.forEach {
                mFriendListTagList.add(it.name)
            }
            mFriendListAdapter.notifyDataChanged()
        })
        mViewModel.getHotSearchList()
        mViewModel.getFriendList()
    }

    override fun onErrorClick() {

    }

    override fun onCreateContentView(): Int {
        return R.layout.layout_fgt_hot_search
    }
}