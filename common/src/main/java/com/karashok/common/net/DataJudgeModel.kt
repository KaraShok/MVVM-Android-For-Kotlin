package com.karashok.common.net

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name DataJudgeModel
 * @date 2019/08/03 15:53
 **/
interface DataJudgeModel<T> {

    /**
     * 空数据逻辑
     * @param data
     * @return
     */
    abstract fun nullData(data: T): Boolean
}