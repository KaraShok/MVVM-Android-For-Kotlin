package com.karashok.common.net

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name State
 * @date 2019/08/02 16:00
 **/
class State {

    companion object{
        val STATE_START = 1
        val STATE_RESULT = 2
        val STATE_RESULT_NULL_DATA = 3
        val STATE_ERROR = -1
    }

    var state: Int = 0

    var extType: Int = 0


}