package com.example.emo_graph_prototype

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.my_item.*

class ResultActivity : AppCompatActivity(), ViewHolder.ItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        viewManager = LinearLayoutManager(this)
        viewAdapter =ViewAdapter( makeList(),this,this)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }
    private fun makeList() : List<ItemData> {
        var list = mutableListOf<ItemData>()
        for ( i in 1..20){
            var dat = ItemData().also {
                it.category = i.toString() + "冊目の本のタイトル"
                it.detail = i.toString() + "冊目の本のデータの詳細"
            }
            list.add(dat)
        }
        return list
    }
    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(applicationContext, "position ${position+1} was tapped", Toast.LENGTH_SHORT).show()
    }
}
