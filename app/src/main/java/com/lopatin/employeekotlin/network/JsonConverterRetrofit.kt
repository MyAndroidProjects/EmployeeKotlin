package com.lopatin.employeekotlin.network

import com.lopatin.employeekotlin.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * getting data from server replaced getting data from raw/all_employees.json
 */

object JsonConverterRetrofit {
    var retrofitApiJson: RetrofitApi
    private const val url: String = BuildConfig.URL_JSON_DATA

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        retrofitApiJson = retrofit.create(RetrofitApi::class.java)
    }
}