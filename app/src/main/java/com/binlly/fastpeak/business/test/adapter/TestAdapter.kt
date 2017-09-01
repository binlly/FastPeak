package com.binlly.fastpeak.business.test.adapter

import android.content.Context
import com.binlly.fastpeak.business.test.model.TestModel
import com.fangxin.assessment.base.adapter.MultipleItemAdapter

/**
 * Created by binlly on 2017/5/13.
 */

class TestAdapter(context: Context, data: List<TestModel>?): MultipleItemAdapter<TestModel>(data) {

    init {
        addItemViewDelegate(TestModel.TYPE_SECTION, SectionDelegate(context))
        addItemViewDelegate(TestModel.TYPE_ENV, EnvDelegate(context))
        addItemViewDelegate(TestModel.TYPE_BUILD, BuildDelegate(context))
    }
}
