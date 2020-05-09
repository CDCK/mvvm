package com.xlk.readdemo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by xlk on 2019/3/15.
 * 免费API地址: https://github.com/jokermonn/-Api/blob/master/OneArticle.md
 */
interface ReadApi {

    /**
     * https://interface.meiriyiwen.com/article/random?dev=1
     *
     * @return 获取随机一篇文章
     */
    @get:GET(value = "random")
    val randomContent: Call<ReadResult>

    /**
     * @return 每日一文
     * https://interface.meiriyiwen.com/article/today?dev=1
     * 网络请求的方法（区分大小写）
     * 网络请求地址路径
     * 是否有请求体
     */
    @GET("today")
    fun getTodayContent(@Query("dev") dev: String): Call<ReadResult>

    /**
     * https://interface.meiriyiwen.com/article/day?dev=1&date=20170216
     *
     * @param date eg: "20170216"
     * @return 获取指定日期内容
     */
    @GET("day")
    fun getCurrentDateContent(@Query("date") date: String): Call<ReadResult>

}
