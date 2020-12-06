package com.itchenning.corner.widget.base

import android.graphics.Canvas

/**
 * Author: itchenning
 * Date: 2020-12-05 12:08
 * Comment:
 */
interface IViewGroupCorner : ICorner {
    fun dispatchSuperDraw(canvas : Canvas)
}