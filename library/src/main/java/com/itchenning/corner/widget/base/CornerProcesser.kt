package com.itchenning.corner.widget.base

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.itchenning.corner.library.R
import com.itchenning.corner.widget.view.CImageView


/**
 * Author: itchenning
 * Date: 2020-12-05 11:57
 * Comment:
 */
class CornerProcesser {
    private val DIRECTION_HORIZONTAL = 0
    private val DIRECTION_DIAGONAL = 1
    private val DIRECTION_VERTICAL = 2
    private var mBgColor : Int = Color.TRANSPARENT
    private var mRadius : Float = 0F
    private var mLeftTopRadius : Float = 0F
    private var mLeftBottomRadius : Float = 0F
    private var mRightTopRadius : Float = 0F
    private var mRightBottomRadius : Float = 0F
    private var mStartColor = 0
    private var mEndColor = 0
    private var mGradientDirection = DIRECTION_HORIZONTAL
    private var mPath = Path()
    private var mRect = RectF()
    private var mPaint = Paint()

    fun parseAttrs(context : Context , attrs : AttributeSet) {
        val mTypedArray = context.obtainStyledAttributes(attrs , R.styleable.corner)
        mBgColor = mTypedArray.getColor(R.styleable.corner_corner_bg_color , Color.TRANSPARENT)
        mRadius = mTypedArray.getDimension(R.styleable.corner_corner_radius , 0F)
        mLeftTopRadius = mTypedArray.getDimension(R.styleable.corner_corner_left_top_radius , 0F)
        mLeftBottomRadius = mTypedArray.getDimension(R.styleable.corner_corner_left_bottom_radius , 0F)
        mRightTopRadius = mTypedArray.getDimension(R.styleable.corner_corner_right_top_radius , 0F)
        mRightBottomRadius = mTypedArray.getDimension(R.styleable.corner_corner_right_bottom_radius , 0F)
        mStartColor = mTypedArray.getColor(R.styleable.corner_corner_start_color , Color.TRANSPARENT)
        mEndColor = mTypedArray.getColor(R.styleable.corner_corner_end_color , Color.TRANSPARENT)
        mGradientDirection = mTypedArray.getInt(R.styleable.corner_corner_gradient_direction , DIRECTION_HORIZONTAL)
        mTypedArray.recycle()
        initPaint()
    }

    fun applySize(w : Int , h : Int) {
        mPath.reset()
        mRect.set(0f , 0f , w.toFloat() , h.toFloat())
        setCorner()
    }

    private fun setCorner() {
        mPath.reset()
        if (mRadius > 0) {
            mPath.addRoundRect(mRect , mRadius , mRadius , Path.Direction.CW)
            return
        }
        val array = floatArrayOf(mLeftTopRadius , mLeftTopRadius , mRightTopRadius , mRightTopRadius , //
            mRightBottomRadius , mRightBottomRadius , mLeftBottomRadius , mLeftBottomRadius)
        mPath.addRoundRect(mRect , array , Path.Direction.CW)
    }

    fun dispatchDraw(canvas : Canvas , corner : IViewGroupCorner) {
        canvas.save()
        drawbg(canvas , corner.getView())
        corner.dispatchSuperDraw(canvas)
        canvas.restore()
    }

    private fun drawbg(canvas : Canvas , view : View) {
        setPaint(view)
        canvas.setDrawFilter(PaintFlagsDrawFilter(0 , Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG))
        canvas.drawPath(mPath , mPaint)
    }

    private fun initPaint() {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.setShader(null)
    }

    private fun setPaint(view : View) {
        mPaint.reset()
        if (mBgColor != Color.TRANSPARENT) {
            mPaint.setColor(mBgColor)
        } else if (mStartColor != Color.TRANSPARENT || mEndColor != Color.TRANSPARENT) {
            val shader = getShader(view.width , view.height)
            mPaint.setShader(shader)
        } else {
            mPaint.setColor(Color.WHITE)
        }
    }

    private fun getShader(width : Int , height : Int) : Shader {
        val shader : Shader
        if (mGradientDirection == DIRECTION_HORIZONTAL) {
            shader = LinearGradient(0F , 0F , width.toFloat() , 0F , intArrayOf(mStartColor , mEndColor) , null , Shader.TileMode.CLAMP)
        } else if (mGradientDirection == DIRECTION_VERTICAL) {
            shader = LinearGradient(0f , 0f , 0F , height.toFloat() , intArrayOf(mStartColor , mEndColor) , null , Shader.TileMode.CLAMP)
        } else if (mGradientDirection == DIRECTION_DIAGONAL) {
            shader = LinearGradient(0f , 0f , width.toFloat() , height.toFloat() , intArrayOf(mStartColor , mEndColor) , null , Shader.TileMode.CLAMP)
        } else {
            shader = LinearGradient(0F , 0F , width.toFloat() , 0F , intArrayOf(mStartColor , mEndColor) , null , Shader.TileMode.CLAMP)
        }
        return shader
    }

    fun applyViewDraw(canvas : Canvas , corner : IViewCorner) {
        applySize(corner.getView().width , corner.getView().height)
        setPaint(corner.getView())
        if (! (corner is ImageView)) {
            canvas.setDrawFilter(PaintFlagsDrawFilter(0 , Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG))
            canvas.drawPath(mPath , mPaint)
        }
    }

    fun fixShape(canvas : Canvas , imageView : CImageView) {
        val drawable = imageView.drawable
        if (drawable == null) {
            return
        }
        canvas.drawColor(0 , PorterDuff.Mode.CLEAR)
        canvas.setDrawFilter(PaintFlagsDrawFilter(0 , Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG))
        canvas.drawPath(mPath , mPaint)
        val bitmap = Bitmap.createBitmap(imageView.width , imageView.height , Bitmap.Config.ARGB_8888)
        val newC = Canvas(bitmap)
        drawable.draw(newC)
        canvas.drawPath(mPath , mPaint)
        mPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(bitmap , 0F , 0F , mPaint)
        bitmap.recycle()
    }
}