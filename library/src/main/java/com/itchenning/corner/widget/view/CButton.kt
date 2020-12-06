package com.itchenning.corner.widget.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.itchenning.corner.widget.base.CornerProcesser
import com.itchenning.corner.widget.base.IViewCorner

/**
 * Author: itchenning
 * Date: 2020-12-02 14:53
 * Comment:
 */
class CButton : AppCompatButton , IViewCorner {
    private lateinit var mProcesser : CornerProcesser

    constructor(context : Context , attrs : AttributeSet) : super(context , attrs) {
        initProcesser(context , attrs)
    }

    constructor(context : Context , attrs : AttributeSet , style : Int) : super(context , attrs , style) {
        initProcesser(context , attrs)
    }

    override fun onDraw(canvas : Canvas) {
        mProcesser.applyViewDraw(canvas , this)
        super.onDraw(canvas)
    }

    override fun initProcesser(context : Context , attrs : AttributeSet) {
        mProcesser = CornerProcesser()
        mProcesser.parseAttrs(context , attrs)
    }

    override fun getView() : View {
        return this
    }
}