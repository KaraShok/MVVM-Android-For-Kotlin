package com.karashok.mvvm.main.ui.fgt

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.karashok.common.data.KnowledgeSystemTabChildren
import com.karashok.common.ui.fgt.CommonBaseFgt
import com.karashok.common.widget.MyDefaultRecycleItemDecoration
import com.karashok.mvvm.R
import com.karashok.mvvm.main.adapter.KnowledgeSystemFgtContentRecyclerAdapter
import com.karashok.mvvm.main.ui.aty.KnowledgeSystemContentListAty
import com.karashok.mvvm.main.ui.aty.KnowledgeSystemContentListAty.Companion.KNOWLEDGESYSTEMCONTENTLISTATY_TAB_DATA
import com.karashok.mvvm.main.viewmodel.KnowledgeSystemFgtViewModel
import kotlinx.android.synthetic.main.layout_list.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name KnowledgeSystemFgt
 * @date 2019/08/08 17:31
 **/
class KnowledgeSystemFgt : CommonBaseFgt() {

    private lateinit var mFgtViewModel: KnowledgeSystemFgtViewModel
    private val mAdapter: KnowledgeSystemFgtContentRecyclerAdapter = KnowledgeSystemFgtContentRecyclerAdapter()

    override fun onContentViewInit() {
        mFgtViewModel = ViewModelProviders.of(this).get(KnowledgeSystemFgtViewModel::class.java)
        layout_list_content_rv.layoutManager = LinearLayoutManager(context)
        layout_list_content_rv.adapter = mAdapter
        layout_list_content_rv.addItemDecoration(
            MyDefaultRecycleItemDecoration(
                MyDefaultRecycleItemDecoration.ITEM_VERTICAL,R.color.color_F5F5F5,1,0,0)
        )
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(context, KnowledgeSystemContentListAty::class.java)
            intent.putParcelableArrayListExtra(KNOWLEDGESYSTEMCONTENTLISTATY_TAB_DATA, ArrayList<KnowledgeSystemTabChildren>(mAdapter.data.get(position).children))
            startActivity(intent)
        }
    }

    override fun onInitData() {
        mFgtViewModel.mKnowledgeSystemTabLiveData.observe(this, Observer {
            mAdapter.setNewData(it)
        })
        mFgtViewModel.getKnowledgeSystemTab()
    }

    override fun onErrorClick() {

    }

    override fun onCreateContentView(): Int {
        return R.layout.layout_list
    }
}