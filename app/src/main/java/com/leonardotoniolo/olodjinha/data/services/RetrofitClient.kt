package com.leonardotoniolo.olodjinha.data.services

import com.leonardotoniolo.olodjinha.data.remote.DataInterface
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://alodjinha.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun getClient() =retrofit.create(DataInterface::class.java)


}