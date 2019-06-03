package com.leonardotoniolo.olodjinha.base

interface BaseView<Activity> {

    var presenter: Activity

    fun initUI()
    fun addListeners()

}