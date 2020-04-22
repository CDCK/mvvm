package com.xlk.mvvm.viewmode

import android.content.Context
import androidx.databinding.ObservableField
import com.xlk.mvvm.util.LogUtil
import com.xlk.readdemo.RData
import com.xlk.readdemo.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by xlk on 2020/4/20.
 */
class MainViewMode(context: Context) {

    val context = context

    /** **** **  data  ** **** **/
    val title = ObservableField<String>()
    val author = ObservableField<String>()
    val content = ObservableField<String>()
    val predate = ObservableField<String>()
    val currdatestr = ObservableField<String>()
    val predatestr = ObservableField<String>()
    var text_size = ObservableField<Float>(22f)

    /** **** **  binding  ** **** **/
    fun today() {
        request(RetrofitManager.readApi().getTodayContent("1"))
    }

    fun yesterday() {
        request(RetrofitManager.readApi().getCurrentDateContent(predate.get()!!))
    }

    fun random() {
        request(RetrofitManager.readApi().randomContent)
    }

    fun request(call: Call<RData>) {
        call.enqueue(object : Callback<RData> {
            override fun onResponse(call: Call<RData>, response: Response<RData>) {
                if (response.isSuccessful) {
                    val m = response.body()?.data
                    LogUtil.d("cdck", "获取数据：${m != null}")
                    m?.let {
                        update(m)
                    }
                } else {
                    LogUtil.e("cdck", "获取数据失败了")
                    preDay()
                }
            }

            override fun onFailure(call: Call<RData>, t: Throwable) {

            }
        })
    }

    //获取前一天数据
    private fun preDay() {
        val sdf = SimpleDateFormat("yyyyMMdd")
        val date = sdf.parse(predate.get())
        val instance = Calendar.getInstance()
        instance.time = date
        instance.add(Calendar.DAY_OF_YEAR, -1)
        val time = instance.time
        val format = sdf.format(time)
        LogUtil.d("cdck", "获取失败的前一天数据 -> $format")
        predate.set(format)
        yesterday()
    }

    private fun update(m: RData.DataBean) {
        title.set(m.title)
        author.set(m.author)
        val prev = m.date?.prev
        predate.set(prev)
        currdatestr.set(getDate(m.date?.curr!!))
        predatestr.set(getDate(prev!!))
        content.set(contentFormat(m.content!!))
    }

    private fun contentFormat(content: String): String {
        val replace = content.replace("<p>", "\u3000\u3000")
        return replace.replace("</p>", "\n\n")
    }

    private fun getDate(curr: String): String {
        val y = curr.substring(0, 4).plus("年")
        val m = curr.substring(4, 6).plus("月")
        val d = curr.substring(6, curr.length).plus("日")
        return y.plus(m).plus(d)
    }

    public fun setTextSize(px: Float) {
        text_size.set(px)
    }
}