package com.karashok.common.net

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name StateDispatcher
 * @date 2019/08/03 15:51
 **/
interface StateDispatcher {

    fun postStateValue(state: State)
}