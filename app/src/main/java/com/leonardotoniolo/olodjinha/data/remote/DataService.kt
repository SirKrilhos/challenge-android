package com.leonardotoniolo.olodjinha.data.remote

import com.leonardotoniolo.olodjinha.data.services.RetrofitClient

class DataService {
    fun getService(): DataInterface {
        return RetrofitClient.getClient()
    }
}