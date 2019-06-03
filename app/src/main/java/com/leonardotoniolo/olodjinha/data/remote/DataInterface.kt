package com.leonardotoniolo.olodjinha.data.remote

import com.leonardotoniolo.olodjinha.model.BannerResponse
import com.leonardotoniolo.olodjinha.model.CategoriaResponse
import com.leonardotoniolo.olodjinha.model.ProdutoResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DataInterface {
    @GET("/banner")
    fun getBanners(): Single<BannerResponse>

    @GET("/categoria")
    fun getCategorias(): Single<CategoriaResponse>

    @GET("/produto")
    fun getProdutos(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("categoriaId") categoriaId: Int?
    ): Single<ProdutoResponse>

    @GET("/produto/maisvendidos")
    fun getMaisVendidos(): Single<ProdutoResponse>

    @POST("/produto/{produtoId}")
    fun reservarProduto(
        @Path("produtoId") produtoId: Int?
    ): Single<Response<Void>>

}