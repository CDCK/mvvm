package com.xlk.mvvm.viewmode

import android.view.View
import com.xlk.mvvm.util.LogUtil
import com.xlk.mvvm.util.sp2px

/**
 * Created by xlk on 2020/4/22.
 *
 */
class ReadConfigViewMode(main: MainViewMode) {
    val a = main

    public fun smallSize(view: View) {
        val px = a.context.sp2px(12f)
        LogUtil.d("cdck", "点击小字体 px=${px}");
        a.setTextSize(px)
    }

    public fun middleSize(view: View) {
        val px = a.context.sp2px(14f)
        LogUtil.d("cdck", "点击中字体 px=${px}");
        a.setTextSize(px)
    }

    public fun bigSize(view: View) {
        val px = a.context.sp2px(16f)
        LogUtil.d("cdck", "点击大字体 px=${px}");
        a.setTextSize(px)
    }
}