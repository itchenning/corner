package com.itchenning.corner.widget.viewgroup

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.itchenning.corner.widget.base.CornerProcesser
import com.itchenning.corner.widget.base.IViewGroupCorner

/**
 * Author: itchenning
 * Date: 2020-12-02 14:53
 * Comment:
 */
class CConstraintLayout : ConstraintLayout , IViewGroupCorner {
    private lateinit var mProcesser : CornerProcesser

    constructor(context : Context , attrs : AttributeSet) : super(context , attrs) {
        initProcesser(context , attrs)
    }

    constructor(context : Context , attrs : AttributeSet , style : Int) : super(context , attrs , style) {
        initProcesser(context , attrs)
    }

    override fun getView() : ViewGroup {
        return this
    }

    override fun initProcesser(context : Context , attrs : AttributeSet) {
        mProcesser = CornerProcesser()
        mProcesser.parseAttrs(context , attrs)
    }

    override fun onSizeChanged(w : Int , h : Int , oldw : Int , oldh : Int) {
        mProcesser.applySize(w , h)
        super.onSizeChanged(w , h , oldw , oldh)
    }

    override fun dispatchDraw(canvas : Canvas) {
        mProcesser.dispatchDraw(canvas , this)
    }

    override fun dispatchSuperDraw(canvas : Canvas) {
        super.dispatchDraw(canvas)
    }
}