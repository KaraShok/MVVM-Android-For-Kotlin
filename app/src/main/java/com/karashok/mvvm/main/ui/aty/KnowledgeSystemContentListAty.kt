package com.karashok.mvvm.main.ui.aty

import com.karashok.common.data.KnowledgeSystemTabChildren
import com.karashok.common.ui.aty.CommonBaseActivity
import com.karashok.common.widget.CommonBaseFragmentPagerAdapter
import com.karashok.lib_widget.tab_layout.listener.OnTabSelectListener
import com.karashok.mvvm.R
import com.karashok.mvvm.main.ui.fgt.KnowledgeSystemContentListFgt
import kotlinx.android.synthetic.main.aty_knowledge_system_content_list.*

class KnowledgeSystemContentListAty : CommonBaseActivity() {

    companion object{
        val KNOWLEDGESYSTEMCONTENTLISTATY_TAB_DATA: String = "knowledgesystemcontentlistaty_tab_data"
    }
    override fun onContentViewInit() {
        val parcelableArrayListExtra = intent.getParcelableArrayListExtra<KnowledgeSystemTabChildren>(KNOWLEDGESYSTEMCONTENTLISTATY_TAB_DATA)
        val titles: MutableList<String> = mutableListOf()
        val fgtList: MutableList<KnowledgeSystemContentListFgt>  = mutableListOf()
        parcelableArrayListExtra.forEach {
            titles.add(it.name)
            fgtList.add(KnowledgeSystemContentListFgt(it.id))
        }
        aty_knowledge_system_content_list_content_vp.adapter = CommonBaseFragmentPagerAdapter(supportFragmentManager,titles,fgtList)
        aty_knowledge_system_content_list_tab.setViewPager(aty_knowledge_system_content_list_content_vp)
        aty_knowledge_system_content_list_tab.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                aty_knowledge_system_content_list_content_vp.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }

        })
    }

    override fun onInitData() {

    }

    override fun onErrorClick() {

    }

    override fun onCreateContentView(): Int {
        return R.layout.aty_knowledge_system_content_list
    }
}
