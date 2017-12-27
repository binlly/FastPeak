package com.binlly.gankee.repo;

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("browse_history",
                true,
                "id" to INTEGER + PRIMARY_KEY + UNIQUE,
                "_id" to TEXT,
                "createdAt" to TEXT,
                "desc" to TEXT,
                "publishedAt" to TEXT,
                "source" to TEXT,
                "type" to TEXT,
                "url" to TEXT,
                "who" to TEXT,
                "image_url" to TEXT,
                "browseAt" to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        // db.dropTable("User", true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)