package com.xlk.readdemo

/**
 * Created by xlk on 2019/3/15.
 * https://interface.meiriyiwen.com/article/today?dev=1
 */
class RData{

    var data: DataBean? = null

    inner class DataBean(var date: DateBean?, var author: String?, var title: String?, var digest: String?, var content: String?, var wc: Int = 0) {
        inner class DateBean(var curr: String?, var prev: String?, var next: String?)
    }
}
