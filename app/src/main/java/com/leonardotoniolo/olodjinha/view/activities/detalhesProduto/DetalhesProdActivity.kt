package com.leonardotoniolo.olodjinha.view.activities.detalhesProduto

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.leonardotoniolo.olodjinha.model.Produto
import com.leonardotoniolo.olodjinha.R
import com.leonardotoniolo.olodjinha.base.BaseActivity
import com.leonardotoniolo.olodjinha.data.remote.DataService
import com.leonardotoniolo.olodjinha.data.services.DataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detalhes_prod.*
import java.text.NumberFormat
import java.util.*

class DetalhesProdActivity : AppCompatActivity() {

    private val mCompositeDisposable: CompositeDisposable
        get() = CompositeDisposable()

    protected lateinit var toolbar: Toolbar
    private lateinit var produto: Produto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_prod)

        toolbar = findViewById<View>(R.id.toolbar_normal) as Toolbar
        toolbar.title= ""
        setSupportActionBar(toolbar)

        with(supportActionBar) {
            this?.setDisplayHomeAsUpEnabled(true)
            this?.setDisplayShowHomeEnabled(true)
            this?.setDisplayShowTitleEnabled(true)
        }

        with(collapsing_toolbar) {
            setExpandedTitleColor(resources.getColor(android.R.color.transparent))
            setCollapsedTitleTextColor(resources.getColor(android.R.color.white))
        }

    }

    override fun onResume() {
        super.onResume()

        produto = intent.getSerializableExtra(EXTRA_PRODUCT) as Produto

        initUI()
        addListeners()
    }

    fun initUI() {
        produto.let {
            title = produto.category?.description
            Glide.with(this).load(produto.urlImage).error(R.drawable.logo_menu).into(img_product)
            txt_product_name.text = it.name
            txt_price_from.text = getString(R.string.preco_de) + " " + produto.priceFrom
            txt_price_to.text = getString(R.string.preco_por) + " " + produto.priceTo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                txt_product_description.text = Html.fromHtml(produto.description, Html.FROM_HTML_MODE_COMPACT)
            else txt_product_description.text = Html.fromHtml(produto.description)
        }
    }

    override fun onPause() {
        super.onPause()
        mCompositeDisposable.clear()
    }

    fun addListeners() {
        app_bar_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val icon = toolbar.navigationIcon
            val color = if (verticalOffset == 0) resources.getColor(R.color.colorPrimary)
            else resources.getColor(android.R.color.white)

            icon!!.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            toolbar.title = produto.name

        })

        fab_reserve.setOnClickListener {
            mCompositeDisposable.addAll(
                DataRepository(DataService()).reservarProduto(produto.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    reserveProductStatus(true)
                }, {
                    reserveProductStatus(false)
                })
            )
        }
    }

    fun reserveProductStatus(status: Boolean) {
        val message = if (status) getString(R.string.produto_reservado)
        else getString(R.string.produto_reservado_erro)

        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(message)
        dialog.setCancelable(false)
        dialog.setPositiveButton("OK") { d, _ ->
            d.dismiss()
            if (status) finish()
        }
        dialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val EXTRA_PRODUCT ="com.leonardotoniolo.activities.detalhesProduto.DetalhesProdActivity.EXTRA_PRODUCT"

        fun newInstance(context: Context, produto: Produto?): Intent {
            val intent = Intent(context, DetalhesProdActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, produto)
            return intent
        }

    }


}
