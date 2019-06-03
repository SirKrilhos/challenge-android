package com.leonardotoniolo.olodjinha.view.activities.categoria

import com.leonardotoniolo.olodjinha.model.Produto
import com.leonardotoniolo.olodjinha.base.BaseView
import com.leonardotoniolo.olodjinha.base.BasePresenter

interface CategoriaContract {

    interface View : BaseView<Presenter> {
        fun setProducts(products: List<Produto>?)
    }

    interface Presenter : BasePresenter {
        fun getProductsFromCategory(offset: Int)
    }

}