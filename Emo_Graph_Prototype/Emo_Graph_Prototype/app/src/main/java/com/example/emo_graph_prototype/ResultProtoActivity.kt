package com.example.emo_graph_prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_resultproto.*


class ResultProtoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultproto)

        recyclerView_resultproto.layoutManager= LinearLayoutManager(this)
        recyclerView_resultproto.adapter= MainAdapter()

                button2.setOnClickListener {
            val intent = Intent(this, GraphProtoActivity::class.java)
            startActivity(intent)
        }

    }

}
