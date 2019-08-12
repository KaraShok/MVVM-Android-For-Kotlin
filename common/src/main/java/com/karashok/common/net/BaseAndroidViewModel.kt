package com.karashok.common.net

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name BaseAndroidViewModel
 * @date 2019/08/03 15:48
 **/
open class BaseAndroidViewModel(app: Application) : AndroidViewModel(app) {

    protected val mApiService: ApiService = ApiService()

    override fun onCleared() {
        super.onCleared()

    }
}