package com.example.emo_graph_prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        button1.setOnClickListener {
            val intent = Intent(this, ResultprotoActivity::class.java)
            startActivity(intent)
        }

    }
}
