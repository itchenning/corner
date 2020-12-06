package com.itchenning.corner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListView()
    }

    private fun setListView() {
        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = com.itchenning.corner.adapter.ListAdapter(this)
    }
}