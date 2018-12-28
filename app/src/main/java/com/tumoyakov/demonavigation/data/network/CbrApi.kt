package com.tumoyakov.demonavigation.data.network

import com.tumoyakov.demonavigation.domain.entity.News
import com.tumoyakov.demonavigation.domain.entity.ValCurs
import com.tumoyakov.demonavigation.domain.entity.ValDyn
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CbrApi {
    @GET("XML_daily_eng.asp")
    fun getValute(
        @Query("date_req")
        date: String
    ): Single<ValCurs>

    @GET("XML_dynamic.asp")
    fun getDynamic(
        @Query("date_req1")
        date1: String,
        @Query("date_req2")
        date2: String,
        @Query("VAL_NM_RQ")
        valuteCode: String
    ): Single<ValDyn>

    @GET("XML_News.asp")
    fun getNews(): Single<News>
}
