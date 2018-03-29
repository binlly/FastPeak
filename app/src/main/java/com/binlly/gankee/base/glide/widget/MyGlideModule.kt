package com.binlly.gankee.base.glide.widget

import android.content.Context
import android.os.Environment
import com.binlly.gankee.base.glide.progress.ProgressManager
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

@GlideModule
class MyGlideModule: AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //获取内存的默认配置
        //        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        //        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        //        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        //        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        //        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        //        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        //        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        //内存缓存相关,默认是24m
        //        val memoryCacheSizeBytes = 1024 * 1024 * 20 // 20mb
        //        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes))


        //设置磁盘缓存及其路径
        val MAX_CACHE_SIZE = 100 * 1024 * 1024L
        val CACHE_FILE_NAME = "FastPeakImageCache"
        builder.setDiskCache(ExternalPreferredCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE))
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val downloadDirectoryPath = Environment.getExternalStorageDirectory().absolutePath + "/" + CACHE_FILE_NAME
            builder.setDiskCache(DiskLruCacheFactory(downloadDirectoryPath, MAX_CACHE_SIZE))
        } else {
            //路径---->/sdcard/Android/data/<application package>/cache/xxx
            builder.setDiskCache(ExternalPreferredCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE))
        }
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.replace(GlideUrl::class.java, InputStream::class.java,
                OkHttpUrlLoader.Factory(ProgressManager.okHttpClient))
    }

    // @Override
    // public void registerComponents(Context context, Registry registry) {
    // 配置glide网络加载框架
    // registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    // }

    override fun isManifestParsingEnabled(): Boolean {
        //不使用清单配置的方式,减少初始化时间
        return false
    }
}