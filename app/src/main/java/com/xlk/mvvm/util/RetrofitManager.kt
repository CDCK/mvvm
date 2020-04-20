package com.xlk.readdemo

import android.annotation.SuppressLint
import androidx.databinding.library.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 *
 */
class RetrofitManager {
    init {
        init()
    }

    private fun initInterceptor(): Interceptor {
        return Interceptor { chain ->
            val builder = chain.request().newBuilder()
            val request = builder
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .addHeader("User-Agent", "")
                    .addHeader("chartset", "utf-8").build()
            chain.proceed(request)
        }
    }


    fun <T> createApi(clz: Class<T>): T {
        if (retrofit == null) {
            synchronized(RetrofitManager::class.java) {
                if (retrofit == null) {
                    val builder = Retrofit.Builder()
                    builder.baseUrl(BASE_URL)
                            .client(okHttpClient!!)
                            .addConverterFactory(GsonConverterFactory.create())
                    retrofit = builder.build()
                }
            }
        }
        return retrofit!!.create(clz)
    }

    private fun init() {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY//这里可以选择拦截级别
            builder.sslSocketFactory(createSSLSocketFactory()).hostnameVerifier(object : HostnameVerifier {

                override fun verify(hostname: String, session: SSLSession): Boolean {
                    return true
                }
            })
            builder.addInterceptor(loggingInterceptor)
        }
        builder.addInterceptor(initInterceptor())
        //错误重连
        builder.retryOnConnectionFailure(true)
        okHttpClient = builder.build()
    }

    private class TrustAllManager : X509TrustManager {

        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

        }

        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
        }
    }

    //KotLin伴生对象 companion object ：类似Java中的静态（static）变量
    companion object {
        private var okHttpClient: OkHttpClient? = null
        private var retrofit: Retrofit? = null
        private val BASE_URL = "https://interface.meiriyiwen.com/article/"
        private var instance: RetrofitManager? = null

        fun readApi(): ReadApi {
            return RetrofitManager.getInstance()!!.createApi(ReadApi::class.java)
        }

        fun getInstance(): RetrofitManager? {
            if (instance == null) {
                synchronized(RetrofitManager::class.java) {
                    if (instance == null) {
                        instance = RetrofitManager()
                    }
                }
            }
            return instance
        }

        @SuppressLint("TrulyRandom")
        fun createSSLSocketFactory(): SSLSocketFactory? {
            var sslf: SSLSocketFactory? = null
            try {
                val sc = SSLContext.getInstance("TLS")
                sc.init(null, arrayOf<TrustManager>(TrustAllManager()), SecureRandom())
                sslf = sc.socketFactory
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return sslf
        }
    }


}
