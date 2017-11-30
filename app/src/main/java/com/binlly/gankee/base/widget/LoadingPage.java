package com.binlly.gankee.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.binlly.gankee.service.Services;


public abstract class LoadingPage extends FrameLayout {
    //加载默认的状态
    private static final int STATE_UNLOADED = 1;
    //加载的状态
    private static final int STATE_LOADING = 2;
    //加载失败的状态
    private static final int STATE_ERROR = 3;
    //加载空的状态
    private static final int STATE_EMPTY = 4;
    //加载成功的状态
    private static final int STATE_SUCCEED = 5;

    private View mLoadingView;//转圈的view
    private View mErrorView;//错误的view
    private View mEmptyView;//空的view
    private View mSucceedView;//成功的view

    private int mState;//默认的状态


    public LoadingPage(Context context) {
        super(context);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        //初始化状态
        mState = STATE_UNLOADED;

        mLoadingView = createLoadingView();
        if (null != mLoadingView) {
            addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        mErrorView = createErrorView();
        if (null != mErrorView) {
            addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        mEmptyView = createEmptyView();
        if (null != mEmptyView) {
            addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        mSucceedView = createSucceedView();
        if (null != mSucceedView) {
            addView(mSucceedView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        showSafePagerView();
    }

    /**
     * 确保更新页面在主线程
     */
    private void showSafePagerView() {
        Services.INSTANCE.post(new Runnable() {
            @Override public void run() {
                showPagerView();
            }
        });
    }

    /**
     * 更新页面显示
     */
    private void showPagerView() {
        if (null != mLoadingView) {
            mLoadingView.setVisibility(mState == STATE_UNLOADED || mState == STATE_LOADING ? View.VISIBLE : View.GONE);
        }
        if (null != mErrorView) {
            mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE : View.GONE);
        }
        if (null != mEmptyView) {
            mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE : View.GONE);
        }

        if (mSucceedView != null) {
            mSucceedView.setVisibility(mState == STATE_SUCCEED ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 创建数据加载中时的页面
     *
     * @return
     */
    protected abstract View createLoadingView();

    /**
     * 创建加载数据为空时的页面
     *
     * @return
     */
    protected abstract View createEmptyView();

    /**
     * 创建加载数据失败时的页面
     *
     * @return
     */
    protected abstract View createErrorView();

    /**
     * 创建加载成功时的页面
     *
     * @return
     */
    protected abstract View createSucceedView();


    /**
     * 设置加载中
     */
    public void setLoading() {
        mState = STATE_LOADING;
        showSafePagerView();
    }

    /**
     * 设置加载失败
     */
    public void setError() {
        mState = STATE_ERROR;
        showSafePagerView();
    }

    /**
     * 设置数据为空
     */
    public void setEmpty() {
        mState = STATE_EMPTY;
        showSafePagerView();
    }

    /**
     * 设置加载成功
     */
    public void setSucceed() {
        mState = STATE_SUCCEED;
        showSafePagerView();
    }
}
