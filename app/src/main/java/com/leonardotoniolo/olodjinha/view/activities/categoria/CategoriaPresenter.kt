package com.leonardotoniolo.olodjinha.view.activities.categoria

import android.util.Log
import com.leonardotoniolo.olodjinha.data.remote.DataService
import com.leonardotoniolo.olodjinha.data.services.DataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CategoriaPresenter(
    private val view: CategoriaContract.View,
    private val categoryId: Int? = 0
) : CategoriaContract.Presenter {

    private val TAG = CategoriaPresenter::class.java.name

    private val repository = DataRepository(DataService())

    override fun subscribe() {
        this.getProductsFromCategory(0)
    }

    override fun getProductsFromCategory(offset: Int) {
        addDisposable(repository.getProdutos(offset, 20, categoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.setProducts(it.productList)
            }, {
                Log.e(TAG, it.message)
            }))
    }

}