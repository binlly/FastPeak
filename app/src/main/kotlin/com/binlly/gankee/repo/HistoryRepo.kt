package com.binlly.gankee.repo

import android.content.Context
import com.binlly.gankee.business.home.FeedAll
import io.reactivex.Observer
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread

/**
 * Created by yy on 2017/12/25.
 */
object HistoryRepo {

    fun requestHistory(context: Context, page: Int = 0, observer: Observer<List<FeedAll>?>) {
        val db = context.database.readableDatabase
        context.doAsync(exceptionHandler = { observer.onError(it) }) {
            try {
                db.select("browse_history").limit(page * 10, 10).orderBy("browseAt",
                        SqlOrderDirection.DESC).exec {
                    println("count is $count and page is $page")
                    if (count <= 0) {
                        context.runOnUiThread { observer.onNext(emptyList()) }
                    } else {
                        moveToFirst()
                        val list = ArrayList<FeedAll>()
                        while (!isLast) {
                            val feed = FeedAll(getString(1),
                                    getString(2),
                                    getString(3),
                                    getString(4),
                                    getString(5),
                                    getString(6),
                                    getString(7),
                                    false,
                                    getString(8),
                                    arrayListOf(getString(9)),
                                    getLong(10))
                            list.add(feed)
                            moveToNext()
                        }
                        context.runOnUiThread { observer.onNext(list) }
                    }
                }
            } catch (e: Exception) {
                context.runOnUiThread { observer.onError(e) }
            } finally {
                db.close()
            }
        }
    }
}