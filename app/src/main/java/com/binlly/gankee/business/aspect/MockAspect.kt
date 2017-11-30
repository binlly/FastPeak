package com.binlly.gankee.business.aspect

import com.binlly.gankee.base.net.Net
import com.binlly.gankee.ext.ToastUtils

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

/**
 * Created by yy on 16/10/16.
 */
@Aspect
class MockAspect {

    companion object {
        private const val AspectRootPath = "com.binlly.fastpeak.business.aspect"
        private const val POINTCUT_METHOD = "execution(@$AspectRootPath.MockAspect * *(..))"
        private const val POINTCUT_CONSTRUCTOR = "execution(@@$AspectRootPath.MockAspect *.new(..))"
    }

    @Pointcut(POINTCUT_METHOD)
    fun methodAnnotatedWithMock() {
    }

    //	@Pointcut(POINTCUT_CONSTRUCTOR)
    //	public void constructorAnnotatedMock() {}

    @Around("methodAnnotatedWithMock()")
    @Throws(Throwable::class)
    fun weaveAroundJoinPoint(joinPoint: ProceedingJoinPoint): Any? {
        return if (Net.isAvailable()) {
            joinPoint.proceed()
        } else {
            ToastUtils.showToast("无网络，请检查网络连接")
            null
        }
    }

    //	@Before("methodAnnotatedWithMock() || constructorAnnotatedMock()")
    //	public Object weaveBeforeJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    //		if (NetUtil.isNetAvailable(AppUtil.getContext())) {
    //			return joinPoint.proceed();
    //		} else {
    //			AppUtil.showToast("Before 无网络，请检查网络连接");
    //			return null;
    //		}
    //	}
}
