package com.karashok.mvvm.main.ui.fgt

import androidx.viewpager.widget.ViewPager
import com.karashok.common.ui.fgt.CommonBaseFgt
import com.karashok.common.widget.CommonBaseFragmentPagerAdapter
import com.karashok.lib_widget.tab_layout.listener.OnTabSelectListener
import com.karashok.mvvm.R
import kotlinx.android.synthetic.main.layout_fgt_main_main.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name MainFgt
 * @date 2019/08/08 17:09
 **/
class MainFgt : CommonBaseFgt() {

    override fun onContentViewInit() {
        val titles: MutableList<String> = mutableListOf("首页","大家都在搜","知识体系")
        layout_fgt_main_main_content_vp.adapter = CommonBaseFragmentPagerAdapter(requireFragmentManager(),titles,
            mutableListOf(HomeFgt(),HotSearchFgt(),KnowledgeSystemFgt()))
        layout_fgt_main_main_content_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                layout_fgt_main_main_tab.currentTab = position
            }

            override fun onPageSelected(position: Int) {

            }

        })
        layout_fgt_main_main_tab.setOnTabSelectListener(object: OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                layout_fgt_main_main_content_vp.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }

        })
        layout_fgt_main_main_tab.setTabData(titles.toTypedArray())
    }

    override fun onInitData() {

    }

    override fun onErrorClick() {

    }

    override fun onCreateContentView(): Int {
        return R.layout.layout_fgt_main_main
    }
}