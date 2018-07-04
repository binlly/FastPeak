package com.binlly.gankee.business.photo

import android.os.Bundle
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewPager
import android.view.View
import com.binlly.gankee.R
import com.binlly.gankee.base.mvp.BaseActivity
import com.binlly.gankee.business.girl.PageChangedEvent
import kotlinx.android.synthetic.main.activity_photo_view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by yy on 2017/11/17.
 */
class PhotoViewActivity: BaseActivity() {

    override fun getContentView(): Int = R.layout.activity_photo_view

    private var pos: Int = 0

    private lateinit var adapter: PhotoPagerAdapter

    override fun initView(savedInstanceState: Bundle?) {
        pos = intent.getIntExtra("position", 0)
        val photos = intent.getStringArrayListExtra("photo_list")
        photos ?: return
        if (photos.isEmpty()) return

        adapter = PhotoPagerAdapter(this, photos)
        photo_pager.transitionName = getString(R.string.transform_name_photo, pos)
        photo_pager.offscreenPageLimit = 2
        photo_pager.adapter = adapter
        photo_pager.currentItem = pos

        photo_pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                    position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                EventBus.getDefault().post(PageChangedEvent(position))
                photo_pager.transitionName = getString(R.string.transform_name_photo, position)
            }
        })
    }

    override fun finishAfterTransition() {
        val currentPos = photo_pager.currentItem
        if (currentPos != pos) {
            setSharedElementCallback(photo_pager)
        }
        super.finishAfterTransition()
    }

    private fun setSharedElementCallback(view: View) {
        setEnterSharedElementCallback(object: SharedElementCallback() {
            override fun onMapSharedElements(
                    names: MutableList<String>, sharedElements: MutableMap<String, View>
            ) {
                names.clear()
                sharedElements.clear()
                names.add(view.transitionName)
                sharedElements.put(view.transitionName, view)
            }
        })
    }

    //    override fun onBackPressed() {
    //        super.onBackPressed()
    //        overridePendingTransition(R.anim.stay, R.anim.fade_scale_large_small)
    //    }
}