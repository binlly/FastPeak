package com.binlly.gankee.business.photo

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.binlly.gankee.ext.loadAuto
import com.github.chrisbanes.photoview.PhotoView


/**
 * Created by yy on 2017/11/15.
 */
class PhotoPagerAdapter(val activity: PhotoViewActivity, val list: List<String>): PagerAdapter() {

    override fun getCount(): Int = list.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val photoView = PhotoView(container.context)
        container.addView(photoView)
        photoView.setOnPhotoTapListener { view, x, y -> activity.onBackPressed() }
        //如果图片没有加载出来 onViewTap 不会调用,但会调用此处
        photoView.setOnClickListener { activity.onBackPressed() }
        photoView.loadAuto(list[position])
        return photoView
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean = view === obj

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}