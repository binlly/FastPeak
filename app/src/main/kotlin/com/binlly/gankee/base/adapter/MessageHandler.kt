package com.binlly.gankee.base.adapter;

import android.os.Bundle;

/**
 * 消息处理器
 *
 * @param <T> </T>
 */
interface MessageHandler<in T> {
    fun handlerMessage(action: Int, position: Int, item: T?, args: Bundle?)
}