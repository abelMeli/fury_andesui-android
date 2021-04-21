package com.mercadolibre.android.andesui.demoapp.commons

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class AndesPagerAdapter(var views: List<View>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        container.addView(views[position])
        return views[position]
    }

    override fun getCount(): Int = views.size

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View?)
    }

    override fun isViewFromObject(view: View, other: Any): Boolean {
        return view == other
    }
}
