package com.karashok.common.net

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name BaseLiveData
 * @date 2019/08/03 15:51
 **/
class BaseLiveData<T> : MutableLiveData<T>(), StateDispatcher {

    /**
     * 记录数据的状态
     */
    private val mState = MutableLiveData<State>()

    /**
     * 数据判定模型
     */
    private var mDataJudgeModel: DataJudgeModel<T>? = null

    /**
     * 是否代理处理<<错误>>提示消息，默认处理
    </错误> */
    private var stateProxyErrorMessage = true

    /**
     * 是否代理处理<<成功>>提示消息，默认不处理
    </成功> */
    private var stateProxySuccessMessage = false

    fun proxyErrorMessage(stateProxyErrorMessage: Boolean): BaseLiveData<T> {
        this.stateProxyErrorMessage = stateProxyErrorMessage
        return this
    }

    fun proxySuccessMessage(stateProxySuccessMessage: Boolean): BaseLiveData<T> {
        this.stateProxySuccessMessage = stateProxySuccessMessage
        return this
    }

    fun dataJudgeModel(dataJudgeModel: DataJudgeModel<T>): BaseLiveData<T> {
        this.mDataJudgeModel = dataJudgeModel
        return this
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, ObserverHandler(observer, this).proxy as Observer<in T>)
    }

    override fun setValue(value: T) {
        super.setValue(value)
        if (mDataJudgeModel != null && mDataJudgeModel!!.nullData(value)) {
            postStateValue(State())
        }
    }

    override fun postStateValue(state: State) {
        mState.postValue(state)
        onStateDispatch(state)
    }

    private fun onStateDispatch(state: State) {
        when (state.state) {
            State.STATE_ERROR -> if (isStateProxyErrorMessage()) {
//                DataU.proxyError(state)
            }
            State.STATE_RESULT -> if (isStateProxySuccessMessage()) {
//                DataU.proxySuccess(state)
            }
        }
    }



    private fun isStateProxyErrorMessage(): Boolean {
        return stateProxyErrorMessage
    }

    private fun isStateProxySuccessMessage(): Boolean {
        return stateProxySuccessMessage
    }

    fun getState(): MutableLiveData<State> {
        return mState
    }

    /**
     * 代理Observer[Observer]接口，用于捕获onChange()[Observer.onChanged]方法中发生的未知异常。
     * 当有异常发生时，状态器会将状态置为错误状态[State.STATE_ERROR]并标记异常类型为其他[ExtData.ERROR_TYPE_OTHER]。
     */
    private class ObserverHandler internal constructor(
        private val mTarget: Any,
        private val mStateDispatcher: StateDispatcher
    ) :
        InvocationHandler {

        //generator proxy object
        internal val proxy: Any
            get() {
                val loader = Thread.currentThread().contextClassLoader
                val interfaces = mTarget.javaClass.interfaces
                return Proxy.newProxyInstance(loader, interfaces, this)
            }

        override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
            var result: Any? = null
            try {
                result = method.invoke(mTarget, *args)
            } catch (e: Throwable) {
                Log.e("BaseLiveData","Observer的onChange()方法中发生了异常,被代理捕获！！！请检查您的onChange()方法。")
                e.printStackTrace()
                mStateDispatcher.postStateValue(State())
            }

            return result
        }

    }
}