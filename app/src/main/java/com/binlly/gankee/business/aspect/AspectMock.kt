package com.binlly.gankee.business.aspect

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by yy on 16/10/16.
 * 网络请求的是否mock接口的织入
 */
@Retention(RetentionPolicy.CLASS)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER) annotation class AspectMock
