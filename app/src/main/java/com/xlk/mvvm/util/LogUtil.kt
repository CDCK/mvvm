package com.xlk.mvvm.util

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.widget.Toast

/**
 * Created by xlk on 2020/4/20.
 *
 */
//日志打印工具类
object LogUtil {
    val log_enable = true
    val e_level = 0
    val d_level = 1
    val i_level = 2
    val w_level = 3
    val v_level = 4
    val current_level = 4

    fun e(tag: String, msg: String) {
        if (log_enable && current_level >= e_level) {
            Log.e(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (log_enable && current_level >= d_level) {
            Log.d(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (log_enable && current_level >= i_level) {
            Log.i(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (log_enable && current_level >= w_level) {
            Log.w(tag, msg)
        }
    }

    fun v(tag: String, msg: String) {
        if (log_enable && current_level >= v_level) {
            Log.v(tag, msg)
        }
    }
}




