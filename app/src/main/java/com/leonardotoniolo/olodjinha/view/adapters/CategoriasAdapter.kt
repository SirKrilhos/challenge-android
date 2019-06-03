package com.leonardotoniolo.olodjinha.view.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.leonardotoniolo.olodjinha.model.Categoria
import com.leonardotoniolo.olodjinha.R
import kotlinx.android.synthetic.main.item_category.view.*



class CategoriasAdapter (private val listener: (categoria: Categoria?) -> Unit
) : RecyclerView.Adapter<CategoriasAdapter.HomeCategoriesViewHolder>() {

    private var categoryList = arrayListOf<Categoria>()

    fun setCategories(categoryList: List<Categoria>) {
        this.categoryList.clear()
        this.categoryList.addAll(categoryList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return HomeCategoriesViewHolder(view)
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: HomeCategoriesViewHolder, position: Int) {
        holder.bindItem(categoryList[position])
    }

    inner class HomeCategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(categoria: Categoria) {
            with(itemView) {
                loadImage(this.context, categoria.urlImage, img_category)

                txt_category_name.text = categoria.description
                setOnClickListener {
                    listener.invoke(categoria)
                }
            }
        }

        fun loadImage(context: Context, file: String?, imgView: ImageView) {
            Glide.with(context)
                .load(file)
                .error(R.drawable.logo_menu)
                .into(imgView)
        }
    }
}