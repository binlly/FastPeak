package com.binlly.gankee.business.home.adapter

import android.widget.ImageView
import com.binlly.gankee.R
import com.binlly.gankee.business.girl.FeedGirl
import com.binlly.gankee.ext.loadAuto
import com.chad.library.adapter.base.BaseViewHolder
import com.fangxin.assessment.base.adapter.QuickAdapter

/**
 * Created by yy on 2017/11/16.
 */

class GirlAdapter: QuickAdapter<FeedGirl, BaseViewHolder>(R.layout.item_home_girl) {

    private var carePos = -1

    companion object {
        const val ACTION_TO_PREVIEW = 1
    }

    override fun convert(holder: BaseViewHolder, item: FeedGirl) {
        val image = holder.getView<ImageView>(R.id.image)
        image.loadAuto(item.url)

        holder.itemView.setOnClickListener {
            sendMessage(ACTION_TO_PREVIEW, holder.adapterPosition, null, null, holder.itemView)
        }
    }
}