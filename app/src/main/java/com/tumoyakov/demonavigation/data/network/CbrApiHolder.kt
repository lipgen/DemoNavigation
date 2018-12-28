package com.tumoyakov.demonavigation.data.network

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object CbrApiHolder {
    private const val URL = "https://www.cbr.ru/scripts/"
    //private const val URL = "https://www.cbr-xml-daily.ru/"

    var api: CbrApi = initApi()
        private set

    private fun initApi(): CbrApi {
        return Retrofit.Builder()
            .baseUrl(URL)
            .client(OkHttpClient())
            .addConverterFactory(
                TikXmlConverterFactory.create(
                    TikXml.Builder()
                        .exceptionOnUnreadXml(false)
                        .build()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(CbrApi::class.java)
    }
}
