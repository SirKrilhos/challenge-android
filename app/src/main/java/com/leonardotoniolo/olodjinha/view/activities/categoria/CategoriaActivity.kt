package com.leonardotoniolo.olodjinha.view.activities.categoria

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.leonardotoniolo.olodjinha.model.Categoria
import com.leonardotoniolo.olodjinha.model.Produto
import com.leonardotoniolo.olodjinha.R
import com.leonardotoniolo.olodjinha.base.BaseActivity
import com.leonardotoniolo.olodjinha.view.activities.detalhesProduto.DetalhesProdActivity
import com.leonardotoniolo.olodjinha.view.adapters.CategoriaAdapter
import com.leonardotoniolo.olodjinha.view.onScrollListeners.CategoryEndlessRecyclerOnScrollListener

import kotlinx.android.synthetic.main.activity_categoria.*

class CategoriaActivity : BaseActivity(), CategoriaContract.View {

    override lateinit var presenter: CategoriaContract.Presenter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var categoriaAdapter: CategoriaAdapter
    private lateinit var categoria: Categoria

    private var offset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria)

        toolbar = findViewById<View>(R.id.toolbar_normal) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        categoria = intent.getSerializableExtra(EXTRA_CATEGORY) as Categoria

        initUI()
        addListeners()
    }

    override fun initUI() {
        title = categoria.description

        presenter = CategoriaPresenter(this, categoria.id)
        presenter.subscribe()

        layoutManager = LinearLayoutManager(this)

        setupCategories()
    }

    override fun setProducts(products: List<Produto>?) {
        products.let {
            progress_products.visibility = View.GONE
            categoriaAdapter.setItens(products, true)
        }
    }


    override fun addListeners() {
        val onScrollListener = object : CategoryEndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(current_page: Int) {
                offset += 20
                presenter.getProductsFromCategory(offset)
            }
        }
        products_list.addOnScrollListener(onScrollListener)
    }

    private fun setupCategories() {
        categoriaAdapter = CategoriaAdapter {
            startActivity(DetalhesProdActivity.newInstance(this, it))
        }

        with(products_list) {
            itemAnimator = android.support.v7.widget.DefaultItemAnimator()
            this.layoutManager = layoutManager
            adapter = categoriaAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val EXTRA_CATEGORY = "com.leonardotoniolo.activities.CategoriaActivity.EXTRA_CATEGORY"

        fun newInstance(context: Context?, categoria: Categoria?): Intent {
            val intent = Intent(context, CategoriaActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY, categoria)
            return intent
        }

    }

}
