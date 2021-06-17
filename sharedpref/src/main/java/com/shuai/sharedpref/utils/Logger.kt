package com.shuai.sharedpref.utils

import android.util.Log

/**
 * 日志简易管理类
 */
internal object Logger {
    private var isLogEnable = true
    private var tag = "SharedPref"

    fun debug(isEnable: Boolean, logTag: String = tag) {
        tag = logTag
        isLogEnable = isEnable
    }

    fun v(msg: String) {
        v(tag, msg)
    }

    fun v(tag: String, msg: String) {
        if (isLogEnable) Log.v(tag, msg)
    }

    fun d(msg: String) {
        d(tag, msg)
    }

    fun d(tag: String, msg: String) {
        if (isLogEnable) Log.d(tag, msg)
    }

    fun i(msg: String) {
        i(tag, msg)
    }

    fun i(tag: String, msg: String) {
        if (isLogEnable) Log.i(tag, msg)
    }

    fun w(msg: String) {
        w(tag, msg)
    }

    fun w(tag: String, msg: String) {
        if (isLogEnable) Log.w(tag, msg)
    }

    fun e(msg: String) {
        e(tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (isLogEnable) Log.e(tag, msg)
    }

    fun printStackTrace(t: Throwable?) {
        if (isLogEnable && t != null) t.printStackTrace()
    }
}