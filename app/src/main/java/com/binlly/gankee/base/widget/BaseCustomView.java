package com.binlly.gankee.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * Created by yy on 2017/11/16
 */
public abstract class BaseCustomView extends RelativeLayout {

    public BaseCustomView(Context context) {
        this(context, null);
    }

    public BaseCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, getStyleable());
        initAttributes(a);
        initView(context);
        a.recycle();
    }

    private void initView(Context context) {
        View view = View.inflate(getContext(), getLayout(), this);
        initData(context, view);
    }

    protected abstract int[] getStyleable();

    protected abstract void initAttributes(TypedArray a);

    protected abstract int getLayout();

    protected abstract void initData(Context context, View view);
}
