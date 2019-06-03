package com.leonardotoniolo.olodjinha.view.viewHolders


import android.view.View
import com.leonardotoniolo.olodjinha.view.adapters.CategoriaAdapter

class SemProdutoViewHolder(
    val view: View
) : CategoriaAdapter.CategoryProductsViewHolder<Any>(view) {

    override fun onCategoryProductsBindViewHolder(item: Any) {
        super.onBindViewHolder(item)
    }

}