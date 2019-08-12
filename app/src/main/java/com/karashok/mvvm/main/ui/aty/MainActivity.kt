package com.karashok.mvvm.main.ui.aty

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.karashok.common.ui.aty.CommonBaseActivity
import com.karashok.common.widget.CommonBaseFragmentPagerAdapter
import com.karashok.mvvm.R
import com.karashok.mvvm.main.ui.fgt.ArticleFgt
import com.karashok.mvvm.main.ui.fgt.CategoryFgt
import com.karashok.mvvm.main.ui.fgt.MainFgt
import com.karashok.mvvm.main.ui.fgt.MineFgt
import kotlinx.android.synthetic.main.layout_aty_main.*
import org.jetbrains.anko.toast

class MainActivity : CommonBaseActivity() {

    override fun onErrorClick() {

    }

    override fun onContentViewInit() {

        aty_main_hvp.adapter = CommonBaseFragmentPagerAdapter(supportFragmentManager, mutableListOf(),mutableListOf(
            MainFgt(), ArticleFgt(), CategoryFgt(), MineFgt()))
        aty_main_hvp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                setBottomBarSelect(p0)
            }

        })
        mBottomViews.add(aty_main_home_rl)
        mBottomViews.add(aty_main_article_rl)
        mBottomViews.add(aty_main_category_rl)
        mBottomViews.add(aty_main_my_rl)

        for (view in mBottomViews) {
            view.setOnClickListener(mViewClick)
        }
        setBottomBarSelect(0)
    }

    override fun onInitData() {

    }

    override fun onCreateContentView(): Int {
        return R.layout.layout_aty_main
    }

    override fun onCreateTitleView(): Int {
        return 0
    }

    private var mBottomViews = mutableListOf<View>()


    private val mViewClick: View.OnClickListener = View.OnClickListener {
        for (bottomView in mBottomViews) {
            if (it == bottomView) {
                aty_main_hvp.setCurrentItem(mBottomViews.indexOf(bottomView), false)
                setBottomBarSelect(mBottomViews.indexOf(bottomView))
            }
        }
    }

    private fun setBottomBarSelect(index: Int) {
        for (view in mBottomViews) {
            view.isSelected = false
        }
        mBottomViews[index].isSelected = true
    }

    private var lastClickTime = 0L
    override fun onBackPressed() {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > 3000) {
            toast("再点就退出了...")
            lastClickTime = currentTimeMillis
        } else {
            super.onBackPressed()
        }
    }
}
