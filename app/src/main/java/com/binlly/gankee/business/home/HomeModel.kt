package com.binlly.gankee.business.home

import com.binlly.gankee.base.model.IModel
import com.binlly.gankee.business.home.adapter.HomeAdapter.Companion.TYPE_GIRL
import com.binlly.gankee.business.home.adapter.HomeAdapter.Companion.TYPE_OTHER
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName


data class FeedAll(
        @SerializedName("_id") val id: String?, //59f463ff421aa90fe72535cf
        @SerializedName("createdAt") val createdAt: String?, //2017-10-28T19:03:27.978Z
        @SerializedName("desc") val desc: String?, //是时候客观评价Retrofit了，这几点你必须明白！
        @SerializedName("publishedAt") val publishedAt: String?, //2017-11-14T10:43:36.180Z
        @SerializedName("source") val source: String?, //web
        @SerializedName("type") val type: String?, //Android
        @SerializedName("url") val url: String?, //http://www.jianshu.com/p/1f10d5477566
        @SerializedName("used") val used: Boolean?, //true
        @SerializedName("who") val who: String?, //
        @SerializedName("images") val images: List<String>?
): MultiItemEntity, IModel {

    override fun getItemType(): Int {
        return if (type.equals("福利")) TYPE_GIRL else TYPE_OTHER
    }
}