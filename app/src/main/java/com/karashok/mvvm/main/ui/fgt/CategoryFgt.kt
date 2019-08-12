package com.karashok.mvvm.main.ui.fgt

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.karashok.common.ui.fgt.CommonBaseFgt
import com.karashok.common.widget.CommonBaseFragmentPagerAdapter
import com.karashok.lib_widget.tab_layout.listener.OnTabSelectListener
import com.karashok.mvvm.R
import com.karashok.mvvm.main.viewmodel.CategoryFgtViewModel
import kotlinx.android.synthetic.main.layout_fgt_main_category.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name CategoryFgt
 * @date 2019/07/17 18:09
 **/
class CategoryFgt : CommonBaseFgt() {

    private lateinit var mFgtViewModel: CategoryFgtViewModel

    override fun onContentViewInit() {
        mFgtViewModel = ViewModelProviders.of(this).get(CategoryFgtViewModel::class.java)
        layout_fgt_main_category_tab.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                layout_fgt_main_category_content_vp.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }

        })
    }

    override fun onInitData() {
        mFgtViewModel.mProjectTreeListLiveData.observe(this, Observer {
            val titles: MutableList<String> = mutableListOf()
            val fgtList: MutableList<CategoryContentFgt>  = mutableListOf()
            it.forEach {
                titles.add(it.name)
                fgtList.add(CategoryContentFgt(it.id))
            }
            layout_fgt_main_category_content_vp.adapter = CommonBaseFragmentPagerAdapter(requireFragmentManager(),titles,fgtList)
            layout_fgt_main_category_tab.setViewPager(layout_fgt_main_category_content_vp)
        })
        mFgtViewModel.getProjectTab()
    }

    override fun onErrorClick() {

    }

    override fun onCreateContentView(): Int {
        return R.layout.layout_fgt_main_category
    }
}