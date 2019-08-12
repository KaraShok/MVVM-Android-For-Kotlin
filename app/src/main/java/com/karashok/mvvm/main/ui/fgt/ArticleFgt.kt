package com.karashok.mvvm.main.ui.fgt

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.karashok.common.ui.fgt.CommonBaseFgt
import com.karashok.common.widget.CommonBaseFragmentPagerAdapter
import com.karashok.lib_widget.tab_layout.listener.OnTabSelectListener
import com.karashok.mvvm.R
import com.karashok.mvvm.main.viewmodel.ArticleFgtViewModel
import kotlinx.android.synthetic.main.layout_fgt_main_article.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ArticleFgt
 * @date 2019/07/17 18:09
 **/
class ArticleFgt : CommonBaseFgt() {

    private lateinit var mFgtViewModel: ArticleFgtViewModel

    override fun onContentViewInit() {
        mFgtViewModel = ViewModelProviders.of(this).get(ArticleFgtViewModel::class.java)
        layout_fgt_main_article_tab.setOnTabSelectListener(object : OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                layout_fgt_main_article_content_vp.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }

        })
    }

    override fun onInitData() {
        mFgtViewModel.mWxarticleChaptersListLiveData.observe(this, Observer {
            val titles: MutableList<String> = mutableListOf()
            val fgtList: MutableList<ArticleContentFgt>  = mutableListOf()
            it.forEach {
                titles.add(it.name)
                fgtList.add(ArticleContentFgt(it.id))
            }
            layout_fgt_main_article_content_vp.adapter = CommonBaseFragmentPagerAdapter(requireFragmentManager(),titles,fgtList)
            layout_fgt_main_article_tab.setViewPager(layout_fgt_main_article_content_vp)
        })
        mFgtViewModel.getWxarticleTab()
    }

    override fun onErrorClick() {

    }

    override fun onCreateContentView(): Int {
        return R.layout.layout_fgt_main_article
    }
}