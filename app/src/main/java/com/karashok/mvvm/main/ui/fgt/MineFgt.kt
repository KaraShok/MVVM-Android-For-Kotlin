package com.karashok.mvvm.main.ui.fgt

import com.karashok.common.ui.fgt.CommonBaseFgt
import com.karashok.mvvm.R
import kotlinx.android.synthetic.main.layout_fgt_main_mine.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name MineFgt
 * @date 2019/07/17 18:09
 **/
class MineFgt : CommonBaseFgt() {

    override fun onContentViewInit() {
        val img: String = "http://ww1.sinaimg.cn/large/0065oQSqly1frepr2rhxvj30qo0yjth8.jpg"
        layout_fgt_main_mine_img_sdv.setImageURI(img,context)
    }

    override fun onInitData() {

    }

    override fun onErrorClick() {

    }

    override fun onCreateContentView(): Int {
        return R.layout.layout_fgt_main_mine
    }
}