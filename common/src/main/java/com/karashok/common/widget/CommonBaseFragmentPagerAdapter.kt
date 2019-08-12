package com.karashok.common.widget

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.karashok.lib_base.ui.fgt.BaseChangeStatesFgt


/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name CommonBaseFragmentPagerAdapter
 * @date 2019/07/17 17:54
 **/
class CommonBaseFragmentPagerAdapter<T: BaseChangeStatesFgt>(fm: FragmentManager,
                                     private val titles: MutableList<String>,
                                     private val fgtList: MutableList<T>) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return fgtList.size
    }

    override fun getItem(p0: Int): Fragment {
        return fgtList.get(p0)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles?.get(position)
    }
}