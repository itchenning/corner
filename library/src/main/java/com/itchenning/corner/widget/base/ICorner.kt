package com.itchenning.corner.widget.base

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Author: itchenning
 * Date: 2020-12-05 12:08
 * Comment:
 */
interface ICorner {
    fun initProcesser(context : Context , attrs : AttributeSet)
    fun getView() : View
}