package com.leonardotoniolo.olodjinha.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Categoria(
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("descricao") val description: String? = null,
    @field:SerializedName("urlImagem") val urlImage: String? = null
): Serializable

data class CategoriaResponse(
    @field:SerializedName("data") val categoryList: List<Categoria>? = null
)