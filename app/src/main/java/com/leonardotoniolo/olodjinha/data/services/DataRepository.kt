package com.leonardotoniolo.olodjinha.data.services

import com.leonardotoniolo.olodjinha.model.BannerResponse
import com.leonardotoniolo.olodjinha.model.CategoriaResponse
import com.leonardotoniolo.olodjinha.model.ProdutoResponse
import com.leonardotoniolo.olodjinha.data.remote.DataInterface
import com.leonardotoniolo.olodjinha.data.remote.DataService
import io.reactivex.Single
import retrofit2.Response

class DataRepository(private var dataService : DataService) : DataInterface {
    private fun getService() = dataService.getService()

    override fun getBanners(): Single<BannerResponse> {
        return getService().getBanners()
    }

    override fun getCategorias(): Single<CategoriaResponse> {
        return getService().getCategorias()
    }

    override fun getProdutos(offset: Int, limit: Int, categoriaId: Int?): Single<ProdutoResponse> {
        return getService().getProdutos(offset,limit,categoriaId)
    }

    override fun getMaisVendidos(): Single<ProdutoResponse> {
        return  getService().getMaisVendidos()
    }

    override fun reservarProduto(produtoId: Int?): Single<Response<Void>> {
        return getService().reservarProduto(produtoId)
    }

}