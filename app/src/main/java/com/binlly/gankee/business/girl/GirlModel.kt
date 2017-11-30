package com.binlly.gankee.business.girl

import com.binlly.gankee.base.model.IModel
import com.google.gson.annotations.SerializedName


data class FeedGirl(
        @SerializedName("_id") val id: String?, //5a0a5141421aa90fef203525
        @SerializedName("createdAt") val createdAt: String?, //2017-11-14T10:13:21.137Z
        @SerializedName("desc") val desc: String?, //11-14
        @SerializedName("publishedAt") val publishedAt: String?, //2017-11-14T10:43:36.180Z
        @SerializedName("source") val source: String?, //chrome
        @SerializedName("type") val type: String?, //福利
        @SerializedName("url") val url: String?, //http://7xi8d6.com1.z0.glb.clouddn.com/20171114101305_NIAzCK_rakukoo_14_11_2017_10_12_58_703.jpeg
        @SerializedName("used") val used: Boolean?, //true
        @SerializedName("who") val who: String? //daimajia
): IModel