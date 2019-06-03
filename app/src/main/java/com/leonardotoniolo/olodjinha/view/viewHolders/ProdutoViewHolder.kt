package com.leonardotoniolo.olodjinha.view.viewHolders


import android.view.View
import com.bumptech.glide.Glide
import com.leonardotoniolo.olodjinha.model.Produto
import com.leonardotoniolo.olodjinha.R
import com.leonardotoniolo.olodjinha.view.adapters.CategoriaAdapter
import kotlinx.android.synthetic.main.item_produto.view.*
import java.text.NumberFormat
import java.util.*

class ProdutoViewHolder(
    val view: View,
    private val listener: (produto: Produto?) -> Unit
) : CategoriaAdapter.CategoryProductsViewHolder<Any>(view) {

    override fun onCategoryProductsBindViewHolder(item: Any) {
        super.onBindViewHolder(item)

        if (item is Produto) {
            with(itemView) {
                Glide.with(context).load(item.urlImage).error(R.drawable.logo_menu).into(img_product)
                txt_product_name.text = item.name
                txt_price_from.text = context.getString(R.string.preco_de) + " " + item.priceFrom
                txt_price_to.text = context.getString(R.string.preco_por) + " " + item.priceTo

                setOnClickListener {
                    listener.invoke(item)
                }
            }
        }
    }

}