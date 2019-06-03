package com.leonardotoniolo.olodjinha.view.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.leonardotoniolo.olodjinha.model.Banner
import com.leonardotoniolo.olodjinha.R

class BannerAdapter (
    context: Context?, private val listener: (linkUrl: String?) -> Unit
) : PagerAdapter() {

    private val layoutInflater = LayoutInflater.from(context)

    private var banners = arrayListOf<Banner>()

    fun setBanners(banners: List<Banner>) {
        this.banners.clear()
        this.banners.addAll(banners)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflate = layoutInflater.inflate(R.layout.item_banner_pager, container, false)
        bindItem(inflate, position)
        container.addView(inflate)
        return inflate
    }

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = banners.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    private fun bindItem(inflate: View, position: Int): ImageView {
        val imgBanner = inflate.findViewById<ImageView>(R.id.img_pass)
        val banner = banners[position]
        banner.let {
            Glide.with(inflate.context)
                .load(it.urlImage)
                .error(R.drawable.logo_menu)
                .into(imgBanner)
        }
        imgBanner.setOnClickListener {
            listener.invoke(banner.linkUrl)
        }
        return imgBanner
    }
}