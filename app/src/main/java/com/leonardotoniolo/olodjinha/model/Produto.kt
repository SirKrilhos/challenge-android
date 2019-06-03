package com.leonardotoniolo.olodjinha.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

data class Produto(
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("nome") val name: String? = null,
    @field:SerializedName("urlImagem") val urlImage: String? = null,
    @field:SerializedName("descricao") val description: String? = null,
    @field:SerializedName("precoDe") val priceFrom: Double? = null,
    @field:SerializedName("precoPor") val priceTo: Double? = null,
    @field:SerializedName("categoria") val category: Categoria? = null
): Serializable

data class ProdutoResponse(
    @field:SerializedName("data") val productList: List<Produto>? = null
)

class MaisVendidoResponse{
    private var data: List<Produto> = ArrayList()

    fun getData(): List<Produto> {
        return data
    }

    fun setData(data: List<Produto>) {
        this.data = data
    }
}


class ProdutoPostResponse {

    var result: String? = null

}
