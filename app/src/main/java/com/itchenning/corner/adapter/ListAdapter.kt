package com.itchenning.corner.adapter

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itchenning.corner.MainActivity

/**
 * Author: itchenning
 * Date: 2020-12-06 16:48
 * Comment:
 */
class ListAdapter(val activity : MainActivity) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : ViewHolder {
        val tv = TextView(activity)
        tv.gravity = Gravity.CENTER
        tv.setTextColor(Color.BLACK)
        return ViewHolder(tv)
    }

    override fun getItemCount() : Int {
        return 100
    }

    override fun onBindViewHolder(holder : ViewHolder , position : Int) {
        holder.tv.text = "RecyclerView item${position}"
    }

    class ViewHolder(view : TextView) : RecyclerView.ViewHolder(view) {
        val tv = view
    }
}