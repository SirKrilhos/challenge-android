package com.leonardotoniolo.olodjinha.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leonardotoniolo.olodjinha.model.Produto
import com.leonardotoniolo.olodjinha.R
import com.leonardotoniolo.olodjinha.view.viewHolders.CategoriaViewHolderType
import com.leonardotoniolo.olodjinha.view.viewHolders.CarregandoProdutoViewHolder
import com.leonardotoniolo.olodjinha.view.viewHolders.SemProdutoViewHolder
import com.leonardotoniolo.olodjinha.view.viewHolders.ProdutoViewHolder

class CategoriaAdapter(
private val listener: (produto: Produto?) -> Unit
) : RecyclerView.Adapter<CategoriaAdapter.CategoryProductsViewHolder<Any>>() {

    private val itens = arrayListOf<Any>()

    fun setItens(itens: List<Any>?, loading: Boolean = false) {
        if (itemCount > 0 && itemCount == this.itens.size) {
            val lastItem = itens!!.size -1
            if (this.itens[lastItem] == CategoriaViewHolderType.LOADING.abreviation)
                this.itens.removeAt(lastItem)
        }

        itens?.let {
            this.itens.addAll(it)
            if (it.isNotEmpty() && loading) {
                if (this.itens.size == 20) {
                    this.itens.add(CategoriaViewHolderType.LOADING.abreviation)
                }
            }
        }

        if (this.itens.isEmpty()) {
            this.itens.add(CategoriaViewHolderType.NO_PRODUCT.abreviation)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductsViewHolder<Any> {
        val view: View

        if (viewType == CategoriaViewHolderType.PRODUCT.ordinal) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_produto, parent, false)
            return ProdutoViewHolder(view, listener)
        } else if (viewType == CategoriaViewHolderType.NO_PRODUCT.ordinal) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_no_products, parent, false)
            return SemProdutoViewHolder(view)
        }

        view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
        return CarregandoProdutoViewHolder(view)
    }

    override fun getItemCount() = itens.size

    override fun onBindViewHolder(holder: CategoryProductsViewHolder<Any>, position: Int) {
        val any = itens[position]
        holder.onCategoryProductsBindViewHolder(any)
    }

    override fun getItemViewType(position: Int): Int {
        val any = itens[position]
        return when {
            any is Produto -> CategoriaViewHolderType.PRODUCT.ordinal
            any.toString() == CategoriaViewHolderType.LOADING.abreviation -> CategoriaViewHolderType.LOADING.ordinal
            any.toString() == CategoriaViewHolderType.NO_PRODUCT.abreviation -> CategoriaViewHolderType.NO_PRODUCT.ordinal
            else -> -1
        }
    }

    abstract class CategoryProductsViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var item: T? = null

        protected fun onBindViewHolder(item: T) {
            this.item = item
        }

        abstract fun onCategoryProductsBindViewHolder(item: T)

    }

}