package com.binlly.gankee.business.debug.adapter

import android.content.Context
import com.binlly.gankee.business.debug.DebugFragment
import com.binlly.gankee.business.debug.model.DebugModel
import com.fangxin.assessment.base.adapter.MultipleItemAdapter

/**
 * Created by binlly on 2017/5/13.
 */

class TestAdapter(context: Context, data: List<DebugModel>?, fragment: DebugFragment):
        MultipleItemAdapter<DebugModel>(data) {

    init {
        addItemViewDelegate(DebugModel.TYPE_SECTION, SectionDelegate(context))
        addItemViewDelegate(DebugModel.TYPE_ENV, EnvDelegate(context))
        addItemViewDelegate(DebugModel.TYPE_BUILD, BuildDelegate(context))
        addItemViewDelegate(DebugModel.TYPE_MOCK, MockDelegate(context, fragment))
        addItemViewDelegate(DebugModel.TYPE_ROUTER, RouterDelegate(context))
    }
}
