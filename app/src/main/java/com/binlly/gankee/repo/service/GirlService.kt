package com.binlly.gankee.repo

import com.binlly.gankee.api.ApiConfig
import com.binlly.gankee.base.net.HttpResult
import com.binlly.gankee.business.PAGE_SIZE
import com.binlly.gankee.business.girl.FeedGirl
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GirlService {

    //    @FormUrlEncoded
    @GET("${ApiConfig.URL_FEED_GIRL}$PAGE_SIZE/{page}")
    fun requestGirls(@Path("page") page: Int): Observable<HttpResult<List<FeedGirl>>>
}
