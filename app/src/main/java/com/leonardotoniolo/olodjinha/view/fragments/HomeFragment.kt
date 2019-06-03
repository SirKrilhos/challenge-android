package com.leonardotoniolo.olodjinha.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leonardotoniolo.olodjinha.R
import com.leonardotoniolo.olodjinha.view.activities.categoria.CategoriaActivity
import com.leonardotoniolo.olodjinha.view.activities.detalhesProduto.DetalhesProdActivity
import com.leonardotoniolo.olodjinha.view.adapters.BannerAdapter
import com.leonardotoniolo.olodjinha.view.adapters.CategoriaAdapter
import com.leonardotoniolo.olodjinha.view.adapters.CategoriasAdapter
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import com.leonardotoniolo.olodjinha.data.remote.DataService
import com.leonardotoniolo.olodjinha.data.services.DataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class HomeFragment : Fragment() {

    private lateinit var bannerAdapter : BannerAdapter
    private lateinit var categoriasAdapter: CategoriasAdapter
    private lateinit var maisVendidosAdapter: CategoriaAdapter

    private lateinit var timer : Timer
    var currentBanner = 0

    private val mCompositeDisposable: CompositeDisposable
        get() = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            showBanner()
            showCategorias()
            showMaisVendidos()

            pager_banner.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    currentBanner = position
                }
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageSelected(position: Int) {}
            })



    }

    override fun onPause() {
        super.onPause()

        timer.cancel()
        mCompositeDisposable.clear()
    }

    private fun showBanner() {
        mCompositeDisposable.addAll(DataRepository(DataService()).getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.bannerList?.let { banners ->
                    progress_banner.visibility = View.GONE
                    bannerAdapter.setBanners(banners)
                }
            }, {
                Log.e(HomeFragment::class.java.name, it.message)
            }))

        bannerAdapter = BannerAdapter(context) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        }
        pager_banner.adapter = bannerAdapter
        val handler = Handler()
        val timerTask = object: TimerTask() {
            override fun run() {
                handler.post {
                    if (currentBanner == pager_banner.adapter?.count) currentBanner = 0
                    pager_banner.setCurrentItem(currentBanner++, true)
                }
            }
        }
        timer = Timer()
        timer.schedule(timerTask, 3000, 3000)
    }

    private fun showCategorias() {
        mCompositeDisposable.addAll(DataRepository(DataService()).getCategorias()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.categoryList?.let { categories ->
                    progress_categories.visibility = View.GONE
                    categoriasAdapter.setCategories(categories)
                }
            }, {
                Log.e(HomeFragment::class.java.name, it.message)
            })
        )

        categoriasAdapter = CategoriasAdapter {
            startActivity(CategoriaActivity.newInstance(requireActivity(), it))
        }

        with(lista_categoria) {
            itemAnimator = DefaultItemAnimator()
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriasAdapter
        }
    }

    private fun showMaisVendidos() {
        mCompositeDisposable.addAll(DataRepository(DataService()).getMaisVendidos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.productList?.let { products ->
                    progress_best_sellers.visibility = View.GONE
                    maisVendidosAdapter.setItens(products)
                }
            }, {
                Log.e(HomeFragment::class.java.name, it.message)
            })
        )

        maisVendidosAdapter = CategoriaAdapter {
            startActivity(DetalhesProdActivity.newInstance(requireActivity(), it))
        }

        with(lista_mais_vendidos) {
            itemAnimator = DefaultItemAnimator()
            this.layoutManager = LinearLayoutManager(context)
            adapter = maisVendidosAdapter
        }
    }

}
